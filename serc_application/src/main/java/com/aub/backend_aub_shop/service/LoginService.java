package com.aub.backend_aub_shop.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aub.backend_aub_shop.model.TransactionModel;
import com.aub.backend_aub_shop.model.UserModel;
import com.aub.backend_aub_shop.repository.UserRepository;
import com.aub.backend_aub_shop.util.LogAction;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

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

  public Optional<UserModel> findEmail(String email) {
    return userRepo.findByEmail(email);
  }

  public void updatePassword(String email, String newPassword) {
    UserModel user = userRepo.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    user.setPassword(passwordEncoder.encode(newPassword));
    user.setUpdatedDate(new Date());
    userRepo.save(user);
  }

  public void logout(HttpServletRequest request, TransactionModel tran){
        HttpSession session = request.getSession();
        UserModel userId = (UserModel) session.getAttribute("User ID:");
        LogAction logAction = LogAction.LOGOUT;
        tran.setUser(userId); // Set the UserModel here
        tran.setAction(logAction);
        tran.setStatus("");
        tran.setTransanctionDate(new Date());
  }
}
