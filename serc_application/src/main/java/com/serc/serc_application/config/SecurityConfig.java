package com.serc.serc_application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

    @Configuration
    // @EnableWebSecurity
    // @EnableMethodSecurity
    public class SecurityConfig {

        // @Bean
        // public PasswordEncoder passwordEncode() {
        //     return new BCryptPasswordEncoder();
        // }

        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            return http
                .authorizeHttpRequests(authz -> authz
                    .requestMatchers("/log", "/error").permitAll()  // Allow public access to login and error pages
                    .anyRequest().authenticated()  // Require authentication for all other requests
                )
                .formLogin(form -> form
                    .loginPage("/log")               // Custom login page
                    .loginProcessingUrl("/login_process") // URL for login form submission
                    .defaultSuccessUrl("/main", true)   // Redirect here after successful login
                    .permitAll()
                )
                .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login_process")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                )
                .build();
        }
        
        

    @Bean
    UserDetailsService userDetailsService(){
        var user  = User.withUsername("van")
                        .password("{noop}password")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }
}