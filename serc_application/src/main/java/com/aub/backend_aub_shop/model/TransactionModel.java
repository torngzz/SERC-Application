package com.aub.backend_aub_shop.model;

import java.util.Date;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.aub.backend_aub_shop.util.LogAction;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "transaction_log")
public class TransactionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id") // Ensure 'id' matches UserModel's primary key
    private UserModel user; // Rename this from `user_id` to `user`

    @Enumerated(EnumType.STRING)
    private LogAction action;
    private String status;
    private Date transanctionDate;

    // Getters and setters
    public Long getNo() {
        return no;
    }
    public void setNo(Long no) {
        this.no = no;
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

    public Date getTransanctionDate() {
        return transanctionDate;
    }
    public void setTransanctionDate(Date transanctionDate) {
        this.transanctionDate = transanctionDate;
    }

    public UserModel getUser() {
        return user;
    }
    public void setUser(UserModel user) {
        this.user = user;
    }
}
