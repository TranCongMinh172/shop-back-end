package com.example.shop.service.impls;

import com.example.shop.dtos.requests.ChangePasswordRequest;
import com.example.shop.dtos.requests.UserUpdateDto;
import com.example.shop.dtos.responses.LoginResponse;
import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.mappers.AddressMapper;
import com.example.shop.models.Token;
import com.example.shop.models.User;
import com.example.shop.models.UserDetail;
import com.example.shop.models.enums.Role;
import com.example.shop.repositories.BaseRepository;
import com.example.shop.repositories.TokenRepository;
import com.example.shop.repositories.UserRepository;
import com.example.shop.service.interfaces.JwtService;
import com.example.shop.service.interfaces.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AddressMapper addressMapper;

    public UserServiceImpl(BaseRepository<User, Long> repository,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           TokenRepository tokenRepository,
                           JwtService jwtService, AddressMapper addressMapper) {
        super(repository, User.class);
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
        this.jwtService = jwtService;
        this.addressMapper = addressMapper;
    }


    @Override
    public LoginResponse changePassword(ChangePasswordRequest changePasswordRequest) throws DataNotFoundException {
        User user = userRepository.findByEmail(changePasswordRequest.getEmail())
                .orElseThrow(() -> new DataNotFoundException("user not found"));
        boolean isValidOldPassword = passwordEncoder.matches(changePasswordRequest.getOldPassword(),
                user.getPassword());
        if(!isValidOldPassword) {
            throw new DataNotFoundException("password does not match");
        }
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user);
        List<Token> tokens = tokenRepository.findAllByUserOrderByExpiredDateDesc(user);
        if(!tokens.isEmpty()) {
            tokenRepository.deleteAll(tokens);
        }
        UserDetail userDetail = new UserDetail(user);
        Token newToken = new Token();
        newToken.setUser(user);
        newToken.setAccessToken(jwtService.generateJwtToken(userDetail));
        newToken.setRefreshToken(jwtService.generateRefreshToken(new HashMap<>(), userDetail));
        newToken.setExpiredDate(LocalDateTime.now());
        tokenRepository.save(newToken);
        return LoginResponse.builder()
                .accessToken(newToken.getAccessToken())
                .refreshToken(newToken.getRefreshToken())
                .build();
    }

    @Override
    public User getUserByEmail(String email) throws DataNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("user not found"));
    }

    @Override
    public User updateUser(String email, UserUpdateDto userUpdateDto) throws DataNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("user not found"));
        user.setGender(userUpdateDto.getGender());
        user.setName(userUpdateDto.getName());
        user.setPhoneNumber(userUpdateDto.getPhoneNumber());
        user.setAddress(addressMapper.addressDto2Address(userUpdateDto.getAddressDto()));
        user.setDateOfBirth(userUpdateDto.getDateOfBirth());
        user.setAvatarUrl(userUpdateDto.getAvatarUrl());
        return userRepository.save(user);
    }

    @PostConstruct
    public void createAdminAccount() {
        User user = new User();
        user.setEmail("admin@gmail.com");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setName("admin");
        user.setRole(Role.ROLE_ADMIN);
        user.setVerify(true);
        user.setPhoneNumber("");
        if(!userRepository.existsByEmail(user.getEmail())) {
            userRepository.save(user);

        }
    }
}
