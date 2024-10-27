package com.example.shop.service.impls;


import com.example.shop.models.User;
import com.example.shop.models.UserDetail;
import com.example.shop.repositories.UserRepository;
import com.example.shop.service.interfaces.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username)
                .orElseThrow(() ->  new UsernameNotFoundException("user not found"));
        return new UserDetail(user);
    }
}
