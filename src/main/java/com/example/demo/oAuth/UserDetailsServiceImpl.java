package com.example.demo.oAuth;

import com.example.demo.Domain.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.oAuth.model.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        // TODO
        // userDao - > userRepository로 바꾸기
        User user = userRepository.findUserByEmail(userEmail);
        if (user == null){
            throw new UsernameNotFoundException(userEmail);
        }
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(user.getRole()));
        return AuthUser.builder()
                .username(user.getUserEmail())
                .password(user.getUserPassword())
                .authorities(roles)
                .build();
    }
}
