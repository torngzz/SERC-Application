package com.aub.backend_aub_shop.service;

import java.util.Date;

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

    public Page<TransactionDTO> findAll(String username, int pageNumber, int pageSize){
        Page<TransactionModel> transaction = transactionRepository.findByUser_UsernameContaining(username, PageRequest.of(pageNumber, pageSize));

        return transaction.map(tran -> {
            TransactionDTO dto = new TransactionDTO();
            dto.setNo(tran.getNo());
            dto.setUsername(getUsernameById(tran.getUser()));
            if(tran.getAction() == LogAction.CREATE){
                dto.setAction(LogAction.CREATE);
            } else {
                dto.setAction(tran.getAction());
            }
            dto.setStatus(tran.getStatus());
            dto.setTransactionDate(tran.getTransanctionDate());
            return dto;
        });
    }

    public String getUsernameById(UserModel userModel) {
        LOGGER.info("getUsernameById function : User ID is : "  + userModel);
        UserModel user = userRepository.findById(userModel.getId())
            .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getUsername();
    }

    public void logTransaction(UserModel user, LogAction action, String status) {
        TransactionModel transaction = new TransactionModel();
        transaction.setUser(user);
        transaction.setAction(action);
        transaction.setStatus(status);
        transaction.setTransanctionDate(new Date());

        // Save transaction here (use repository directly)
        transactionRepository.save(transaction);
    }
}