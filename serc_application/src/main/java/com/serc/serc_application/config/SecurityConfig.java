package com.serc.serc_application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  @Bean
  public PasswordEncoder passwordEncode() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      http
          .authorizeHttpRequests(authz -> authz
              .anyRequest().permitAll()  // Allow all requests temporarily
          )
          .formLogin(form -> form
              .loginPage("/Login/login")  // Use your custom login page
              .loginProcessingUrl("/loging")  // Form submission URL for login
              .permitAll()  // Allow everyone to access login page
          )
          .logout(logout -> logout
              .logoutUrl("/logout")
              .logoutSuccessUrl("/Login/login")
              .invalidateHttpSession(true)
              .deleteCookies("JSESSIONID")
          );
      return http.build();
  }
}
