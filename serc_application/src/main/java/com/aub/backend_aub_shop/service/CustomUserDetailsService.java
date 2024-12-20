package com.aub.backend_aub_shop.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aub.backend_aub_shop.model.UserModel;
import com.aub.backend_aub_shop.repository.UserRepository;

@Service
@Primary
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository; // Inject your UserRepository

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserModel> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            // Convert UserModel to Spring Security's User
            UserModel userModel = user.get();
            return new org.springframework.security.core.userdetails.User(
                userModel.getUsername(), 
                userModel.getPassword(), 
                getAuthorities(userModel) // Set authorities (roles)
            );
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    private Collection<? extends GrantedAuthority> getAuthorities(UserModel userModel) {
        // Convert your roles or permissions into GrantedAuthority objects
        return AuthorityUtils.createAuthorityList("ROLE_USER"); // Example role
    }
}