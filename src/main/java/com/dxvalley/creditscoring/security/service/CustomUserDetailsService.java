package com.dxvalley.creditscoring.security.service;

import com.dxvalley.creditscoring.userManager.user.Users;
import com.dxvalley.creditscoring.userManager.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("There is no user with this username")
        );

//        if (user.getUserStatus().equals(UserStatus.BANNED))
//            throw new BannedUserException("Your account has been temporarily banned. Please contact admins for further assistance.");

        Collection<SimpleGrantedAuthority> authorities = user.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                .toList();

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), authorities
        );
    }

    public void updateLastLogin(String username) {
        Optional<Users> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            Users adminUser = user.get();
            adminUser.setLastLogin(LocalDateTime.now().toString());
            userRepository.save(adminUser);
        }
    }
}