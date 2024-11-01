package com.serc.serc_application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.serc.serc_application.model.UserModel;
import com.serc.serc_application.repository.UserRepository;

public class UserService {
    @Autowired private UserRepository userRepo;

    public UserModel findUserByUsername(String username) throws UsernameNotFoundException{
      return userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
    }
}