package com.example.shop.configurations;

import com.example.shop.dtos.requests.*;
import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.exceptions.MediaTypeNotSupportException;
import com.example.shop.exceptions.OutOfInStockException;
import com.example.shop.mappers.ProductPriceMapper;
import com.example.shop.models.*;
import com.example.shop.models.enums.*;
import com.example.shop.repositories.*;
import com.example.shop.service.impls.*;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//@Configuration
@RequiredArgsConstructor
public class FakeData {


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final String[] categories = {"Áo", "Quần", "Giày, dép", "Túi xách", "Nước hoa", "Linh tinh"};
    private final String[] providers = {"Nike", "Louis Vuitton", "Gucci", "Chanel", "Dior", "Prada"};
    private final CategoryRepository categoryRepository;
    private final ProviderRepository providerRepository;
    private final SizeRepository sizeRepository;
    private final ColorRepository colorRepository;
    private final ProductServiceImpl productServiceImpl;
    private final ProductImageRepository productImageRepository;
    private final ProductDetailServiceImpl productDetailServiceImpl;
    private final OrderServiceImpl orderServiceImpl;
    private final ProductPriceServiceImpl productPriceServiceImpl;
    private final ProductPriceMapper productPriceMapper;
    private final CommentServiceImpl commentServiceImpl;

//    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Faker faker = new Faker();
            Random random = new Random();
            List<User> users = fakeUser(faker);
//            fakeCategory();
//            fakeProvider(faker);
//            fakeColor();
//            fakeSize();
//            fakeProduct(faker, random);
//            fakeComment(faker, users, random);
//            fakeOrder(faker, users, random);
            fakeProductPrice(random);
        };
    };
    private void fakeProductPrice(Random random) throws DataNotFoundException {
        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            long productId = random.nextLong(500) + 1;
            if (ids.contains(productId)) {
                continue;
            }
            ProductPriceDto productPriceDto = ProductPriceDto.builder()
                    .productId(productId)
                    .discount((random.nextInt(9) + 1) / 10f)
                    .note("sale")
                    .expiryDate(LocalDateTime.of(2026, 12, 31, 23, 59, 59))
                    .build();

            productPriceServiceImpl
                    .save(productPriceMapper.productPriceDto2ProductPrice(productPriceDto));
            ids.add(productId);

        }
    }
    private List<User> fakeUser(Faker faker) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            User user = new User();
            user.setAddress(new Address(faker.address().streetAddress(),
                    faker.address().state(),
                    faker.address().city()));
            user.setEmail(faker.internet().emailAddress());
            user.setPassword(passwordEncoder.encode("123456"));
            user.setVerify(true);
            user.setPhoneNumber(faker.phoneNumber().subscriberNumber(10));
            user.setGender(faker.options().option(Gender.class));
            user.setName(faker.name().fullName());
            user.setRole(Role.ROLE_USER);
            users.add(userRepository.save(user));
        }
        return users;
    }

    private void fakeCategory() {
        for (String s : categories) {
            Category category = new Category();
            category.setCategoryName(s);
            category.setStatus(Status.ACTIVE);
            categoryRepository.save(category);
        }
    }
    private void fakeProvider(Faker faker) {
        for (String s : providers) {
            Provider provider = new Provider();
            provider.setProviderName(s);
            provider.setStatus(Status.ACTIVE);
            provider.setEmail(faker.internet().emailAddress());
            provider.setPhoneNumber(faker.phoneNumber().subscriberNumber(10));
            provider.setAddress(new Address(faker.address().streetAddress(),
                    faker.address().state(),
                    faker.address().city()));
            providerRepository.save(provider);
        }
    }

    private void fakeSize() {
        String[] textSize = {"S", "M", "L", "XL", "XXL"};
        for(int i = 30; i <= 45; i++) {
            Size size = new Size();
            size.setNumberSize((int) Short.parseShort(i +""));
            size.setSizeType(SizeType.NUMBER);
            sizeRepository.save(size);
        }
        for(int i = 0; i <= 4; i++) {
            Size size = new Size();
            size.setTextSize(textSize[i]);
            size.setSizeType(SizeType.TEXT);
            sizeRepository.save(size);
        }
    }
    private void fakeComment(Faker faker, List<User> users, Random random)
            throws DataNotFoundException, IOException, MediaTypeNotSupportException {
        User[] userArr = users.toArray(new User[0]);
        for (int i = 0; i < 5000; i++) {
            User user = userArr[random.nextInt(userArr.length)];
            CommentDto commentDto = CommentDto.builder()
                    .email(user.getEmail())
                    .content(faker.text().text())
                    .rating(Float.parseFloat((random.nextInt(1, 5) + 1) + ""))
                    .productId(random.nextLong(500) + 1)
                    .build();
            commentServiceImpl.addComment(commentDto);
        }
    }
    private void fakeColor() {
        String[] colors = {"red", "green", "blue", "yellow",
                "black", "white", "brown", "orange", "violet", "crimson",
                "grey", "pink", "aqua", "purple", "silver"
        };
        String[] colorsHex = {
                "#FF0000", "#008000", "#0000FF", "#FFFF00",
                "#000000", "#FFFFFF", "#A52A2A", "#FFA500",
                "#8F00FF", "#DC143C", "#808080", "#FFC0CB",
                "#00FFFF", "#800080", "#C0C0C0"
        };
        for(int i = 0; i < colors.length; i++) {
            Color color = new Color();
            color.setName(colors[i]);
            colorRepository.save(color);
        }
    }
    private void fakeProduct(Faker faker, Random random) {
        for (int i = 0; i < 1000; i++) {
            Product product = new Product();
            product.setProductName(faker.commerce().productName() + random.nextInt());
            product.setDescription(faker.restaurant().description());
            product.setProductStatus(Status.ACTIVE);
            product.setCategory(new Category(random.nextLong(categories.length) + 1));
            product.setProvider(new Provider(random.nextLong(providers.length) + 1));
            product.setPrice(Double.parseDouble(random.nextInt(50000, 2000001) + ""));
            productServiceImpl.save(product);
            for (int j = 0; j < 5; j++) {
                ProductImage productImage = new ProductImage();
                productImage.setProduct(product);
                String formattedNumber = String.format("%03d", random.nextInt(133) + 1);
                String path = String
                        .format("https://sales-app-bucket.s3.ap-southeast-1.amazonaws.com/fakeimages/%s.jpg",
                                formattedNumber);
                productImage.setPath(path);
                productImageRepository.save(productImage);
                if(j == 0) {
                    product.setThumbnail(path);
                    productServiceImpl.save(product);
                }
            }
            for (int j = 0; j <= 10; j++) {
                ProductDetail productDetail = new ProductDetail();
                productDetail.setProduct(product);
                productDetail.setQuantity(random.nextInt(100,1000));
                productDetail.setSize(new Size(random.nextLong(20) + 1));
                productDetail.setColor(new Color(random.nextLong(15) + 1));
                productDetailServiceImpl.save(productDetail);
            }
        }
    }
    private void fakeOrder(Faker faker, List<User> users, Random random)
            throws DataNotFoundException, OutOfInStockException {
        User[] userArr = users.toArray(new User[0]);
        for (int i = 0; i < 1000; i++) {
            User user = userArr[random.nextInt(userArr.length)];
            CreateAddressDto addressDto = CreateAddressDto.builder()
                    .street(user.getAddress().getStreet())
                    .city(user.getAddress().getCity())
                    .district(user.getAddress().getDistrict())
                    .build();
            OrderDto orderDto = new OrderDto();
            orderDto.setAddress(addressDto);
            orderDto.setEmail(user.getEmail());
            orderDto.setBuyerName(user.getName());
            orderDto.setPhoneNumber(user.getPhoneNumber());
            orderDto.setPaymentMethod(faker.options().option(PaymentMethod.class));
            List<ProductOrderDto> productOrderDtos = new ArrayList<>();
            for (int j = 0; j <= 4; j++) {
                ProductOrderDto productOrderDto = new ProductOrderDto();
                productOrderDto.setProductDetailId(random.nextLong(36000) + 1);
                productOrderDto.setQuantity(random.nextInt(5) + 1);
                productOrderDtos.add(productOrderDto);
            }
            orderDto.setProductOrders(productOrderDtos);
            orderDto.setNote(faker.coffee().notes());
            orderDto.setDeliveryMethod(faker.options().option(DeliveryMethod.class));
            orderServiceImpl.save(orderDto);
        }
    }
//    private void fakeComment(Faker faker, List<User> users, Random random)
//            throws DataNotFoundException, IOException {
//        User[] userArr = users.toArray(new User[0]);
//        for(int i = 0; i < 500; i++) {
//            User user = userArr[random.nextInt(userArr.length)];
//            CommentDto commentDto = CommentDto.builder()
//                    .email(user.getEmail())
//                    .content(faker.text().text())
//                    .rating(Float.parseFloat((random.nextInt(1, 5) + 1) + ""))
//                    .productId(random.nextLong(1000) + 1)
//                    .build();
//            commentServiceImpl.addComment(commentDto);
//        }
//    }
}
