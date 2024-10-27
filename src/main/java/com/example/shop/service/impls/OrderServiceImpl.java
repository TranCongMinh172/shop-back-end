package com.example.shop.service.impls;

import com.example.shop.dtos.requests.OrderDto;
import com.example.shop.dtos.requests.ProductOrderDto;
import com.example.shop.dtos.responses.MessageResponse;
import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.exceptions.OutOfInStockException;
import com.example.shop.mappers.AddressMapper;
import com.example.shop.models.*;
import com.example.shop.models.enums.*;
import com.example.shop.models.idClass.UserVoucherId;
import com.example.shop.repositories.*;
import com.example.shop.service.interfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
    private final OrderVoucherRepository orderVoucherRepository;
    private final OrderRepository orderRepository;
    private final NotificationRepository notificationRepository;
    private final UserNotificationRepository userNotificationRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public OrderServiceImpl(BaseRepository<Order, String> repository,
                            OrderVoucherRepository orderVoucherRepository,
                            OrderRepository orderRepository,
                            NotificationRepository notificationRepository,
                            UserNotificationRepository userNotificationRepository, SimpMessagingTemplate messagingTemplate) {
        super(repository,Order.class);
        this.orderVoucherRepository = orderVoucherRepository;
        this.orderRepository = orderRepository;
        this.notificationRepository = notificationRepository;
        this.userNotificationRepository = userNotificationRepository;
        this.messagingTemplate = messagingTemplate;
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
    @Transactional(rollbackFor = {DataNotFoundException.class, OutOfInStockException.class})
    public Order save(OrderDto orderDto) throws DataNotFoundException, OutOfInStockException {
        List<ProductOrderDto> productOrders = orderDto.getProductOrders();
        User user = userRepository.findByEmail(orderDto.getEmail())
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        double originalAmount = 0;
        OrderStatus orderStatus = OrderStatus.PENDING;
        if(orderDto.getPaymentMethod().equals(PaymentMethod.CC)) {
            orderStatus = OrderStatus.UNPAID;
        }
        Order order = Order.builder()
                .id(UUID.randomUUID().toString())
                .user(user)
                .orderDate(LocalDateTime.now())
                .orderStatus(orderStatus)
                .originalAmount(originalAmount)
                .paymentMethod(orderDto.getPaymentMethod())
                .deliveryMethod(orderDto.getDeliveryMethod())
                .phoneNumber(orderDto.getPhoneNumber())
                .buyerName(orderDto.getBuyerName())
                .note(orderDto.getNote())
                .deliveryAmount(
                        orderDto.getDeliveryMethod().equals(DeliveryMethod.EXPRESS) ? expressPrice : economyPrice)
                .address(addressMapper.addressDto2Address(orderDto.getAddress()))
                .build();
        order = super.save(order);
        originalAmount = handleAmount(productOrders, order, originalAmount);

        List<Long> vouchers = orderDto.getVouchers();
        if(vouchers != null) {
            for (Long voucherId : vouchers) {
                Voucher voucher = voucherRepository.findById(voucherId)
                        .orElseThrow(() -> new DataNotFoundException("Voucher not found"));
                UserVoucherId userVoucherId = new UserVoucherId(
                        user, voucher
                );
                if(voucher.getScope().equals(Scope.FOR_USER)) {
                    UserVoucher userVoucher = userVoucherRepository.findById(userVoucherId)
                            .orElseThrow(() -> new DataNotFoundException("UserVoucher not found"));
                    if(!userVoucher.isUsed()) {
                        double discountPrice = addVoucherDeliveryToOrder(originalAmount, voucher);
                        if(discountPrice > 0) {
                            userVoucher.setUsed(true);
                        }
                        if(voucher.getVoucherType().equals(VoucherType.FOR_DELIVERY)) {
                            double discount = order.getDeliveryAmount() - discountPrice;
                            if(discount < 0) {
                                discount = 0;
                            }
                            order.setDeliveryAmount(discount);
                        } else {
                            order.setDiscountedPrice(
                                    (order.getDiscountedPrice() == null ? 0 : order.getDiscountedPrice())
                                            + discountPrice);

                        }
                        userVoucherRepository.save(userVoucher);
                        OrderVoucher orderVoucher = new OrderVoucher();
                        orderVoucher.setOrder(order);
                        orderVoucher.setVoucher(voucher);
                        orderVoucherRepository.save(orderVoucher);
                    }
                } else {
                    Optional<VoucherUsages> voucherUsages = voucherUsagesRepository.findById(userVoucherId);
                    double discountPrice = addVoucherDeliveryToOrder(originalAmount, voucher);
                    if(voucherUsages.isEmpty()) {
                        if(voucher.getVoucherType().equals(VoucherType.FOR_DELIVERY)) {
                            double discount = order.getDeliveryAmount() - discountPrice;
                            if(discount < 0) {
                                discount = 0;
                            }
                            order.setDeliveryAmount(discount);
                        } else {
                            order.setDiscountedPrice(
                                    (order.getDiscountedPrice() == null ? 0 : order.getDiscountedPrice())
                                            + discountPrice);
                        }
                        if(discountPrice > 0) {
                            VoucherUsages voucherUsages1 = new VoucherUsages();
                            voucherUsages1.setVoucher(voucher);
                            voucherUsages1.setUser(user);
                            voucherUsages1.setUsagesDate(LocalDateTime.now());
                            voucherUsagesRepository.save(voucherUsages1);
                            OrderVoucher orderVoucher = new OrderVoucher();
                            orderVoucher.setOrder(order);
                            orderVoucher.setVoucher(voucher);
                            orderVoucherRepository.save(orderVoucher);
                        }
                    }

                }
            }
        }

        order.setDiscountedAmount((originalAmount + order.getDeliveryAmount())
                - (order.getDiscountedPrice() == null ? 0 : order.getDiscountedPrice()));
        order.setOriginalAmount(originalAmount);
        return super.save(order);
    }

    @Override
    public Order updateStatusOrder(String id, OrderStatus status) throws DataNotFoundException {
        if(!status.equals(OrderStatus.PAID)) {
            Order order = orderRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Order not found"));
            order.setOrderStatus(status);
            orderRepository.save(order);
            switch (status) {
                case PROCESSING:
                    handleNotification(order, "Đơn hàng " + order.getId() + " đã được xác nhận");
                    break;
                case SHIPPED:
                    handleNotification(order, "Đơn hàng " + order.getId() + " đã được giao");
                    break;
                case CANCELLED:
                    List<OrderVoucher> orderVouchers = orderVoucherRepository.findAllByOrderId(order.getId());
                    for(OrderVoucher orderVoucher : orderVouchers) {
                        Voucher voucher = orderVoucher.getVoucher();
                        if(voucher.getScope().equals(Scope.FOR_USER)) {
                            UserVoucher userVoucher = userVoucherRepository.findById(
                                    new UserVoucherId(order.getUser(), voucher)
                            ).orElseThrow(() -> new DataNotFoundException("UserVoucher not found"));
                            userVoucher.setUsed(false);
                            userVoucherRepository.save(userVoucher);
                        } else {
                            VoucherUsages voucherUsages = voucherUsagesRepository.findById(
                                    new UserVoucherId(order.getUser(), voucher)
                            ).orElseThrow(() -> new DataNotFoundException("UserVoucherUsages not found"));
                            voucherUsagesRepository.delete(voucherUsages);
                        }
                    }
                    List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(order.getId());
                    for(OrderDetail orderDetail : orderDetails) {
                        ProductDetail productDetail = orderDetail.getProductDetail();
                        productDetail.setQuantity(productDetail.getQuantity() + orderDetail.getQuantity());
                        productDetailRepository.save(productDetail);
                        Product product = productDetail.getProduct();
                        product.setBuyQuantity(product.getBuyQuantity() - orderDetail.getQuantity());
                        product.setTotalQuantity(product.getTotalQuantity() + orderDetail.getQuantity());
                        productDetailRepository.save(productDetail);
                    }

                    break;
                default: break;
            }
            return order;
        }
        return null;
    }
    private void handleNotification(Order order, String text) {
        Notification notification = new Notification();
        notification.setContent(text);
        notification.setScope(Scope.FOR_USER);
        notification.setNotificationDate(LocalDateTime.now());
        notification.setRedirectTo("/orders/" + order.getId());
        notificationRepository.save(notification);
        UserNotification userNotification = new UserNotification();
        userNotification.setUser(order.getUser());
        userNotification.setNotification(notification);
        userNotification.setSeen(false);
        userNotificationRepository.save(userNotification);
        MessageResponse<Notification> messageResponse = new MessageResponse<>();
        messageResponse.setData(notification);
        messageResponse.setType("notification");
        messagingTemplate.convertAndSendToUser(order.getUser().getEmail(),
                "/queue/notifications", messageResponse);
    }
    @Override
    public List<OrderDetail> getOrderDetailsByOrderId(String orderId) {
        return orderDetailRepository.findByOrderId(orderId);
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
            int buyQuantity = product.getBuyQuantity() != null ? product.getBuyQuantity() : 0;
            product.setBuyQuantity(buyQuantity + productOrderDto.getQuantity());
            productDetailRepository.save(productDetail);
            productRepository.save(product);
            List<ProductPrice> productPrices = productPriceRepository.findAllByProductId(product.getId());
            double price = product.getPrice();
            double discountedPrice = 0;
            if (!productPrices.isEmpty()) {
                for (ProductPrice productPrice : productPrices) {
                    if (productPrice.getExpiredDate().isAfter(LocalDateTime.now())) {
                        if (productPrice.getDiscountedPrice() > discountedPrice) {
                            discountedPrice = productPrice.getDiscountedPrice();
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
