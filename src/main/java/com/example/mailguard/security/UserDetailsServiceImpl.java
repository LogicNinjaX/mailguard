package com.example.mailguard.security;

import com.example.mailguard.entity.UserProfile;
import com.example.mailguard.repository.UserProfileRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserProfileRepository userRepository;

    public UserDetailsServiceImpl(UserProfileRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserProfile user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with uname: "+username));

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_"+user.getUserRole().name()));

        return new CustomUserDetails(user.getUserId(), user.getUsername(), user.getPassword(), user.getUserRole(), authorities);
    }
}
