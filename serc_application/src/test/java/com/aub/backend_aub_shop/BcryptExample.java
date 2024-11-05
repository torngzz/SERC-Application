package com.aub.backend_aub_shop;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptExample{
    public static void main(String[] args)
 {
        String passwordToHash = "123456";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(passwordToHash);
        System.out.println(hashedPassword);
    }
    
}