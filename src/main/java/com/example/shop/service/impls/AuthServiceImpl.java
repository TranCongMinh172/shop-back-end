package com.example.shop.service.impls;

import com.example.shop.dto.requests.LoginRequestDto;
import com.example.shop.dto.requests.ResetPasswordRequest;
import com.example.shop.dto.requests.UserRegisterDto;
import com.example.shop.dto.requests.VerifyEmailDto;
import com.example.shop.dto.responses.LoginResponse;
import com.example.shop.exceptions.DataExistsException;
import com.example.shop.exceptions.DataNotFoundException;
import com.example.shop.models.Token;
import com.example.shop.models.User;
import com.example.shop.models.UserDetail;
import com.example.shop.models.enums.Role;
import com.example.shop.repositories.TokenRepository;
import com.example.shop.repositories.UserRepository;
import com.example.shop.service.interfaces.AuthService;
import com.example.shop.service.interfaces.EmailService;
import com.example.shop.service.interfaces.JwtService;
import com.example.shop.utils.EmailDetails;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final TokenRepository tokenRepository;

    @Override
    public void register(UserRegisterDto userRegisterDto) throws DataExistsException, MessagingException {
        User user = mapToUser(userRegisterDto);
        userRepository.save(user);
        EmailDetails emailDetails = EmailDetails
                .builder()
                .msgBody(user.getOtp())
                .subject("Ma xac thuc")
                .recipient(user.getEmail())
                .build();
        emailService.sendHtmlMail(emailDetails);


    }

    @Override
    public LoginResponse verifyEmail(VerifyEmailDto verifyEmailDto) throws DataNotFoundException {
        User user = userRepository.findByEmail(verifyEmailDto.getEmail())
                .orElseThrow(()-> new DataNotFoundException("email not exists"));
        if(user.getOtp().equals(verifyEmailDto.getOtp())){
            user.setVerify(true);
            userRepository.save(user);
        }else {
            throw new DataNotFoundException("OTP is not correct");
        }
        UserDetail userDetail = new UserDetail(user);
        Token newToken = new Token();
            newToken.setAccessToken(jwtService.generateJwtToken(userDetail));
            newToken.setRefreshToken(jwtService.generateRefreshToken(new HashMap<>(),userDetail));
            newToken.setExpiredDate(LocalDateTime.now()); // ngay tAO
            newToken.setUser(user);
            saveToken(user,newToken);
        return LoginResponse.builder()
                .accessToken(newToken.getAccessToken())
                .refreshToken(newToken.getRefreshToken())
                .build();
    }



    @Override
    public LoginResponse login(LoginRequestDto loginRequestDto) throws DataNotFoundException {
        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(),
                loginRequestDto.getPassword()));
        UserDetail userDetail = new UserDetail(user);
        Token token = new Token();
        token.setUser(user);
        token.setAccessToken(jwtService.generateJwtToken(userDetail));
        token.setRefreshToken(jwtService.generateRefreshToken(new HashMap<>(),userDetail));
        token.setExpiredDate(LocalDateTime.now());
        saveToken(user,token);
        return LoginResponse.builder()
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .build();
    }

    @Override
    public void sendVerificationEmail(String email) throws DataNotFoundException, MessagingException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new DataNotFoundException("email not exits"));
        user.setOtpResetPassword(getOtp());
        userRepository.save(user);
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setSubject("Mã xác nhận otp đặt lại mật khẩu");
        emailDetails.setMsgBody(user.getOtpResetPassword());
        emailDetails.setRecipient(user.getEmail());
        emailService.sendHtmlMail(emailDetails);

    }

    @Override
    public void verificationEmailForResetPassword(VerifyEmailDto verifyEmailDto) throws DataNotFoundException {
        User user = userRepository.findByEmail(verifyEmailDto.getEmail())
                .orElseThrow(()-> new DataNotFoundException("email not exits"));
        if(verifyEmailDto.getOtp().equals(user.getOtpResetPassword())){
            user.setVerify(true);
            userRepository.save(user);
        }else {
            throw new DataNotFoundException("OTP is not correct");
        }
    }

    @Override
    public LoginResponse resetPassword(ResetPasswordRequest resetPasswordRequest) throws DataNotFoundException {
        User user = userRepository.findByEmail(resetPasswordRequest.getEmail())
                .orElseThrow(()-> new  DataNotFoundException("email not exist"));
        if(user.getOtpResetPassword() != null &&
            user.getOtpResetPassword().equals(resetPasswordRequest.getOtpResetPassword())
        ){
            user.setPassword(passwordEncoder.encode(resetPasswordRequest.getPassword()));
            user.setOtpResetPassword(null);
            userRepository.save(user);
            List<Token> tokens = tokenRepository.findAllByUserOrderByExpiredDateDesc(user);
           if(!tokens.isEmpty()){
               tokenRepository.deleteAll(tokens);
           }
           UserDetail userDetail = new UserDetail(user);
           Token token = Token.builder()
                   .accessToken(jwtService.generateJwtToken(userDetail))
                   .refreshToken(jwtService.generateRefreshToken(new HashMap<>(),userDetail))
                   .expiredDate(LocalDateTime.now())
                   .user(user)
                   .build();
           tokenRepository.save(token);
           return LoginResponse.builder()
                   .accessToken(token.getAccessToken())
                   .refreshToken(token.getRefreshToken())
                   .build();
        }
        else {
            throw new DataNotFoundException("OTP is not correct");
        }
    }

    private User mapToUser(UserRegisterDto userRegisterDto) throws  DataExistsException {
        Optional<User> user = userRepository.findByEmail(userRegisterDto.getEmail());
        User userExists = new User();
        if(user.isPresent()){
            if(user.get().isVerify()){
                throw new DataExistsException("Email already exists");
            }
            userExists = user.get();
        }
        String otpCode = getOtp();
        User userRs =  User.builder()
                .email(userRegisterDto.getEmail())
                .password(passwordEncoder.encode(userRegisterDto.getPassword()))
                .phone(userRegisterDto.getPhoneNumber())
                .userName(userRegisterDto.getName())
                .roles(Role.USER)
                .otp(otpCode)
                .build();
        userRs.setUserId(userExists.getUserId());
        return userRs;
    }
    private String getOtp() {
        Random random = new Random();
        int randomNumber = 100000 + random.nextInt(900000);
        return String.valueOf(randomNumber);
    }
    private void saveToken(User user, Token token){
        List<Token> tokens = tokenRepository.findAllByUserOrderByExpiredDateDesc(user);
        if(!tokens.isEmpty() && tokens.size()>=2){
            Token tokenDelete = tokens.get(tokens.size()-1);
            tokenRepository.delete(tokenDelete);
        }
        tokenRepository.save(token);
    }

}
