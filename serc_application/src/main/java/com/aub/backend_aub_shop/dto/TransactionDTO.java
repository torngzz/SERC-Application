package com.aub.backend_aub_shop.dto;

import java.util.Date;

import com.aub.backend_aub_shop.util.LogAction;

public class TransactionDTO {
    private Long no;
    private String username;
    private LogAction action;
    private String status;
    private Date transactionDate;

    public Long getNo() {
        return no;
    }
    public void setNo(Long no) {
        this.no = no;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public LogAction getAction() {
        return action;
    }
    public void setAction(LogAction action) {
        this.action = action;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Date getTransactionDate() {
        return transactionDate;
    }
    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
}