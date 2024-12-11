package com.aub.backend_aub_shop.service;

import java.util.Date;
import java.util.UUID;

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
import com.aub.backend_aub_shop.util.LogAction;


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
        Page<TransactionModel> transaction = transactionRepository.findByUsername_UsernameContaining(username, PageRequest.of(pageNumber, pageSize));

        return transaction.map(tran -> {

            TransactionDTO dto = new TransactionDTO();
            dto.setId(tran.getNo());
            // dto.setUsername(transactionService.getUsernameById());
            // dto.setRole(tran.getRole());
            dto.setAction(tran.getAction());
            dto.setStatus(tran.getStatus());
            // dto.setLoginDate(tran.getLoginDate());
            // dto.setLogoutDate(tran.getLogoutDate());

            return dto;
        });
    }

    public String getUsernameById(UUID userId) {
        LOGGER.info("User ID is : "  + userId);
        UserModel user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getUsername();
    }

    public void logTransaction(UserModel user, LogAction action) {
        TransactionModel transaction = new TransactionModel();
        transaction.setUsername(user);
        transaction.setAction(action);
        transaction.setStatus("SUCCESS");
        transaction.setTransanctionDate(new Date());

        // Save transaction here (use repository directly)
        transactionRepository.save(transaction);
    }
}