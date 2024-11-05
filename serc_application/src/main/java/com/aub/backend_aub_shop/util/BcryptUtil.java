package com.aub.backend_aub_shop.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptUtil {
    /**
     * 
     * @param rawString
     * @return
     */
    public static String encrypt(String rawString){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(rawString);
        System.out.println(hashedPassword);
        return hashedPassword;
    }

}
