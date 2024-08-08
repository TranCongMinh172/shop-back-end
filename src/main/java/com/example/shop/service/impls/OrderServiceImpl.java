package com.example.shop.service.impls;

import com.example.shop.dto.requests.OrderDto;
import com.example.shop.dto.requests.ProductOrderDto;
import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.mappers.AddressMapper;
import com.example.shop.models.*;
import com.example.shop.models.enums.DeliveryMethod;
import com.example.shop.models.enums.ScopeType;
import com.example.shop.models.enums.StatusOrder;
import com.example.shop.models.enums.VoucherType;
import com.example.shop.models.idClass.UserVoucherId;
import com.example.shop.repositories.*;
import com.example.shop.service.interfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl extends BaseServiceImpl<Order, String> implements OrderService {
    private UserRepository userRepository;
    @Value("${delivery-price.express}")
    private Double expressPrice;
    @Value("${delivery-price.economy}")
    private Double economyPrice;
    private AddressMapper addressMapper;
    private ProductDetailRepository productDetailRepository;
    private ProductRepository productRepository;
    private ProductPriceRepository productPriceRepository;
    private OrderDetailRepository orderDetailRepository;
    private VoucherRepository voucherRepository;
    private UserVoucherRepository userVoucherRepository;
    private VoucherUsagesRepository voucherUsagesRepository;

    public OrderServiceImpl(JpaRepository<Order, String> repository) {
        super(repository);
    }

    @Autowired
    public void setVoucherUsagesRepository(VoucherUsagesRepository voucherUsagesRepository) {
        this.voucherUsagesRepository = voucherUsagesRepository;
    }

    @Autowired
    public void setUserVoucherRepository(UserVoucherRepository userVoucherRepository) {
        this.userVoucherRepository = userVoucherRepository;
    }

    @Autowired
    public void setProductPriceRepository(ProductPriceRepository productPriceRepository) {
        this.productPriceRepository = productPriceRepository;
    }

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setAddressMapper(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    @Autowired
    public void setOrderDetailRepository(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }

    @Autowired
    public void setVoucherRepository(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Autowired
    public void setProductDetailRepository(ProductDetailRepository productDetailRepository) {
        this.productDetailRepository = productDetailRepository;
    }

    @Override
    @Transactional(rollbackFor = {DataNotFoundException.class})
    public Order save(OrderDto orderDto) throws DataNotFoundException {
        List<ProductOrderDto> productOrderDtos = orderDto.getProductOrderDtos();
        User user = userRepository.findById(orderDto.getUserId())
                .orElseThrow(() -> new DataNotFoundException("user not found"));
        double originalAmount = 0;
        Order order = Order
                .builder()
                .id(UUID.randomUUID().toString())
                .user(user)
                .orderDate(LocalDateTime.now())
                .status(StatusOrder.PENDING)
                .originalAmount(originalAmount)
                .paymentMethod(orderDto.getPaymentMethod())
                .deliveryMethod(orderDto.getDeliveryMethod())
                .phone(orderDto.getPhone())
                .buyerName(orderDto.getBuyerName())
                .note(orderDto.getNote())
                .deliveryAmount(
                        orderDto.getDeliveryMethod().equals(DeliveryMethod.EXPRESS) ? expressPrice : economyPrice
                )
                .address(addressMapper.addressDto2Address(orderDto.getCreateAddressDto()))
                .build();
        order = super.save(order);
        originalAmount = handleAmount(productOrderDtos, order, originalAmount);

        List<Long> vouchers = orderDto.getVouchers();
        if (vouchers != null) {
            for (Long voucherId : vouchers) {
                Voucher voucher = voucherRepository.findById(voucherId)
                        .orElseThrow(() -> new DataNotFoundException("voucher not found"));
                UserVoucherId userVoucherId = new UserVoucherId(
                        user, voucher
                );
                if (voucher.getScope().equals(ScopeType.FOR_USER)) {
                    UserVoucher userVoucher = userVoucherRepository.findById(userVoucherId)
                            .orElseThrow(() -> new DataNotFoundException("userVoucher not found"));
                    if (!userVoucher.isVoucherUsed()) {
                        double discountPrice = addVoucherDeliveryToOrder(originalAmount, voucher);
                        if (discountPrice > 0) {
                            userVoucher.setVoucherUsed(true);
                        }
                        if (voucher.getType().equals(VoucherType.FOR_DELIVERY)) {
                            order.setDeliveryAmount(order.getDeliveryAmount() - discountPrice);
                        } else {
                            order.setDeliveryAmount(
                                    (order.getDiscountedPrice() == null ? 0 : order.getDiscountedPrice())
                                            + discountPrice
                            );
                        }
                        if (discountPrice > 0) {
                            VoucherUsages voucherUsages1 = new VoucherUsages();
                            voucherUsages1.setVoucher(voucher);
                            voucherUsages1.setUser(user);
                            voucherUsages1.setUsagesDate(LocalDateTime.now());
                            voucherUsagesRepository.save(voucherUsages1);
                        }
                        userVoucherRepository.save(userVoucher);
                    }
                } else {
                    Optional<VoucherUsages> voucherUsages = voucherUsagesRepository.findById(userVoucherId);
                    double discountPrice = addVoucherDeliveryToOrder(originalAmount, voucher);
                    if (voucherUsages.isEmpty()) {
                        if (voucher.getType().equals(VoucherType.FOR_DELIVERY)) {
                            order.setDeliveryAmount(order.getDeliveryAmount() - discountPrice);
                        } else {
                            order.setDiscountedPrice(
                                    (order.getDiscountedPrice() == null ? 0 : order.getDiscountedPrice())
                                            + discountPrice);
                        }
                        if (discountPrice > 0) {
                            VoucherUsages voucherUsages1 = new VoucherUsages();
                            voucherUsages1.setVoucher(voucher);
                            voucherUsages1.setUser(user);
                            voucherUsages1.setUsagesDate(LocalDateTime.now());
                            voucherUsagesRepository.save(voucherUsages1);
                        }
                    }
                }
            }
        }
        order.setDiscountedAmount((originalAmount + order.getDeliveryAmount()) // gia cuoi cung
                - (order.getDiscountedPrice() == null ? 0 : order.getDiscountedPrice()));
        order.setOriginalAmount(originalAmount);
        return super.save(order);

    }

    private double addVoucherDeliveryToOrder(double originalAmount, Voucher voucher) {
        if (voucher.getExpiredDate().isAfter(LocalDateTime.now()) &&
                originalAmount >= voucher.getMinAmount()) {
            double discountPrice = originalAmount * voucher.getDiscount();
            if (discountPrice >= voucher.getMaxPrice()) {
                discountPrice = voucher.getMaxPrice();
            }
            return discountPrice;
        }
        return 0;

    }

    private double handleAmount(List<ProductOrderDto> productOrderDtos, Order order, double originalAmount) throws DataNotFoundException {
        for (ProductOrderDto productOrderDto : productOrderDtos) {
            ProductDetail productDetail = productDetailRepository.findById(productOrderDto.getProductDetailId())
                    .orElseThrow(() -> new DataNotFoundException("product detail not found"));
            Product product = productDetail.getProduct();
            int quantity = productDetail.getQuantity() - productOrderDto.getQuantity();
            if (quantity < 0) {
                throw new DataNotFoundException("product out of in stock");
            }
            productDetail.setQuantity(quantity);
            product.setTotalQuantity(product.getTotalQuantity() - productOrderDto.getQuantity());
            productDetailRepository.save(productDetail);
            productRepository.save(product);
            List<ProductPrice> productPrices = productPriceRepository.findAllByProductId(product.getId());
            double price = product.getProductPrice();
            double discountedPrice = 0;
            if (!productPrices.isEmpty()) {
                for (ProductPrice productPrice : productPrices) {
                    if (productPrice.getExpiredDate().isAfter(LocalDateTime.now())) {
                        if (productPrice.getDiscountPrice() > discountedPrice) {
                            discountedPrice = productPrice.getDiscountPrice();
                        }
                    }
                }
            }
            price = price - discountedPrice;
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setQuantity(productOrderDto.getQuantity());
            orderDetail.setAmount(price * orderDetail.getQuantity());
            orderDetail.setOrder(order);
            orderDetail.setProductDetail(productDetail);
            orderDetailRepository.save(orderDetail);
            originalAmount += orderDetail.getAmount();

        }
        return originalAmount;
    }
}
