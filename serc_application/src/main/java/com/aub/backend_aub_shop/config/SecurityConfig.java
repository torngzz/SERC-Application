package com.aub.backend_aub_shop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.aub.backend_aub_shop.service.CustomUserDetailsService;
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Autowired
    private CustomUserDetailsService customUserDetailsService; // Your custom service
    
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final AccessDeniedHandler accessDeniedHandler;

    @Autowired
    public SecurityConfig(CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler, 
                          AccessDeniedHandler accessDeniedHandler) {
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/forgot-password").permitAll()
                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/loginProcess")
                .loginProcessingUrl("/loginProcess")
                .successHandler(customAuthenticationSuccessHandler) // Use DI-managed handler
                .permitAll()
            )
            .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .permitAll()
            )
            .sessionManagement(session -> session
                .maximumSessions(1) // Allow only one active session per user
                .sessionRegistry(sessionRegistry()) // Use the session registry
                .maxSessionsPreventsLogin(true) // Prevent new session from being created if user already has an active session
                .expiredUrl("/login?expired=true") // Redirect to login page with an expired message if the session is expired
                
            )
            .exceptionHandling(ex -> ex
                .accessDeniedHandler(accessDeniedHandler) // Use DI-managed handler
            );
        return http.build();
    }

    // @Bean
    // public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
    //     AuthenticationManagerBuilder authenticationManagerBuilder = 
    //         http.getSharedObject(AuthenticationManagerBuilder.class);
    //     authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    //     return authenticationManagerBuilder.build();
    // }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
            .userDetailsService(customUserDetailsService)
            .passwordEncoder(passwordEncoder()) // Ensure you have a password encoder
            .and()
            .build();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new CustomLogoutSuccessHandler();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}