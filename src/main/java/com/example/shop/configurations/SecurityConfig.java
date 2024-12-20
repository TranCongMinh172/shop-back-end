package com.example.shop.configurations;


import com.example.shop.oauth2.Oauth2SuccessLogin;
import com.example.shop.service.interfaces.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserDetailService userDetailService;
    private final PreFilter preFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final Oauth2SuccessLogin oauth2SuccessLogin;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(author -> {
                    author.requestMatchers("/api/v1/test/**").permitAll();
                    author.requestMatchers("/v3/api-docs/**",
                            "/swagger-ui/**",
                            "/swagger-ui.html",
                            "/ws/**").permitAll();
                    author.requestMatchers("/api/v1/auth/**").permitAll();
                    author.requestMatchers(HttpMethod.GET,
                            "/api/v1/products/**",
                            "/api/v1/addresses/**",
                            "/api/v1/categories/**",
                            "/api/v1/product-details/**",
                            "/api/v1/providers/**",
                            "/api/v1/colors/**",
                            "/api/v1/sizes/**",
                            "/api/v1/comments/**").permitAll();
                    author.requestMatchers("/api/v1/users/**", "/api/v1/comments/**",
                            "/api/v1/notifications/**",
                            "api/v1/payment/vnp/**",
                            "/api/v1/order-details/**",
                            "/api/v1/messages/**",
                            "/api/v1/rooms/**").authenticated();
                    author.requestMatchers(HttpMethod.POST, "api/v1/orders/**").authenticated();
                    author.requestMatchers(HttpMethod.GET, "api/v1/orders/**").authenticated();
                    author.requestMatchers(HttpMethod.GET, "api/v1/vouchers/**").authenticated();
                    author.requestMatchers(HttpMethod.PUT, "api/v1/orders/**").authenticated();
                    author.anyRequest().hasRole("ADMIN");
                })
                .oauth2Login(oauth2 -> oauth2.successHandler(oauth2SuccessLogin))
                .sessionManagement(httpSessionManager ->
                        httpSessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(preFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exHandler -> exHandler.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .build();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
            throws Exception {
        return configuration.getAuthenticationManager();
    }
}