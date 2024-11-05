package com.aub.backend_aub_shop.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aub.backend_aub_shop.model.UserModel;
import com.aub.backend_aub_shop.repository.UserRepository;

@Service
public class LoginService {
  @Autowired private UserRepository userRepo;
  @Autowired private PasswordEncoder passwordEncoder;

  // public void getSession(HttpServletRequest request, UserModel user) {
  //     HttpSession session = request.getSession();
  //     // Assuming user has been authenticated and you have user details.
  //     session.setAttribute("UserSessionManager", new UserSessionManager(user.getId(), user.getUsername()));
  // }

  public int checkStatus(String username){
    int status = 0;
    status = userRepo.checkStatus(username);

    return status;
  }

  public void changePassword(String username, String oldPassword, String newPassword, String confirmPassword) {
      // Check if user exists
      UserModel user = userRepo.findByUsername(username)
              .orElseThrow(() -> new UsernameNotFoundException("User not found"));

      // Verify that the old password is correct
      if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
          throw new IllegalArgumentException("Old password is incorrect");
      }

      // Check if the new password matches the confirm password
      if (!newPassword.equals(confirmPassword)) {
          throw new IllegalArgumentException("New password and confirm password do not match");
      }

      // Update the user's password and set the updated date
      user.setPassword(passwordEncoder.encode(newPassword));
      user.setUpdatedDate(new Date());

      // Save the updated user object
      userRepo.save(user);
  }
}
