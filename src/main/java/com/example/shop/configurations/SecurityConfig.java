package com.example.shop.configurations;

import com.example.shop.oauth2.Oauth2SuccessLogin;
import com.example.shop.service.interfaces.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserService userService;
    private final PreFilter preFilter;
    private final Oauth2SuccessLogin oauth2SuccessLogin;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public UserDetailsService userDetailsService() {
        return userService;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HttpSession httpSession) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(author ->{
                    author.requestMatchers("/api/v1/test/admin").hasRole("ADMIN");
                    author.requestMatchers("/api/v1/test/users").hasRole("USER");
                    author.requestMatchers("/v3/api-docs/**",
                            "/swagger-ui/**",
                            "/swagger-ui.html").permitAll();
                    author.requestMatchers("/api/v1/auth/**").permitAll();
                    author.requestMatchers(HttpMethod.GET,
                            "/api/v1/products/**",
                            "/api/v1/addresses/**",
                            "/api/v1/categories/**",
                            "/api/v1/product-details/**",
                            "/api/v1/providers/**",
                            "/api/v1/colors/**",
                            "/api/v1/sizes/**").permitAll();
                    author.requestMatchers(HttpMethod.PATCH,"/api/v1/products/**").hasRole("USER");
                    author.requestMatchers(HttpMethod.PUT,"/api/v1/products/**").hasRole("USER");
                    author.requestMatchers(HttpMethod.POST, "api/v1/orders").authenticated();
                    author.requestMatchers("/api/v1/users/**", "/api/v1/comments/**").authenticated();
                    author.anyRequest().hasRole("ADMIN");
                })
                .oauth2Login(oauth2 -> oauth2.successHandler(oauth2SuccessLogin))
                .sessionManagement(httpSessionManager->
                        httpSessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(preFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration ) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
