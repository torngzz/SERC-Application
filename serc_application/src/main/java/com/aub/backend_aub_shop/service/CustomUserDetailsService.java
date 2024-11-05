// package com.aub.backend_aub_shop.service;

// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.stereotype.Service;

// import com.aub.backend_aub_shop.model.UserModel;
// import com.aub.backend_aub_shop.repository.UserRepository;
// @Service
// public class CustomUserDetailsService implements UserDetailsService {

//     private final UserRepository userRepository;

//     // @Autowired
//     public CustomUserDetailsService(UserRepository userRepository) {
//         this.userRepository = userRepository;
//     }

//     @Override
//     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//         // Fetch user from your custom data source
//         UserModel user = userRepository.findByUsernameModel(username);
//         // Check if user exists and return UserDetails
//         if (user == null) {
//             throw new UsernameNotFoundException("User not found");
//         }

//         // Return user details
//         return new org.springframework.security.core.userdetails.User(
//             user.getUsername(),
//             user.getPassword(),
//             user.getAuthorities() // Ensure UserModel implements getAuthorities()
//         );
//     }
// }
