package com.aub.backend_aub_shop.service;


import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.aub.backend_aub_shop.dto.TransactionDTO;
import com.aub.backend_aub_shop.model.TransactionModel;
import com.aub.backend_aub_shop.model.UserModel;
import com.aub.backend_aub_shop.repository.TransactionRepository;
import com.aub.backend_aub_shop.repository.UserRepository;


@Service
public class TransactionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionService transactionService;

    public Page<TransactionDTO> findAll(String username, int pageNumber, int pageSize){
        Page<TransactionModel> transaction = transactionRepository.findByUsernameContaining(username, PageRequest.of(pageNumber, pageSize));

        return transaction.map(tran -> {

            TransactionDTO dto = new TransactionDTO();
            dto.setId(tran.getId());
            dto.setUsername(transactionService.getUsernameById(tran.getId()));
            dto.setRole(tran.getRole());
            dto.setAction(tran.getAction());
            dto.setStatus(tran.getStatus());
            dto.setLoginDate(tran.getLoginDate());
            dto.setLogoutDate(tran.getLogoutDate());

            return dto;
        });
    }

    public String getUsernameById(Long userId) {
        LOGGER.info("User ID is : "  + userId);
        if(userId == 0L){
            return "System";
        }
        UserModel user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getUsername();
    }

     // Method to log an action
     public void logAction(String username, String action, String role, String status, LocalDateTime loginDate, LocalDateTime logoutDate) {
        TransactionModel log = new TransactionModel();
        log.setUsername(username);
        log.setAction(action);
        log.setRole(role);
        log.setStatus(status);
        log.setLoginDate(loginDate);
        log.setLogoutDate(logoutDate);
        
        // Save the log entry
        transactionRepository.save(log);
    }


    public void logLogout(String username, LocalDateTime logoutDate) {
        // Retrieve the latest transaction log for the user where action is "Login" and logoutDate is null
        TransactionModel lastLogin = transactionRepository.findTopByUsernameAndActionAndLogoutDateIsNullOrderByLoginDateDesc(username, "Login");
    
        if (lastLogin != null) {
            lastLogin.setLogoutDate(logoutDate);
            transactionRepository.save(lastLogin);
        }
    }
}
