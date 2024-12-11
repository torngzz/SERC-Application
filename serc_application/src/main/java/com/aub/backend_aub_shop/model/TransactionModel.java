package com.aub.backend_aub_shop.model;

import java.util.Date;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.aub.backend_aub_shop.util.LogAction;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "transaction")
public class TransactionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @ManyToOne
    @JoinColumn(name = "id", referencedColumnName = "id") // 'id' is the primary key of UserModel
    private UserModel username; // Change this from String to UserModel

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

    public UserModel getUsername() { // Update getter
        return username;
    }
    public void setUsername(UserModel username) { // Update setter
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

    public Date getTransanctionDate() {
        return transanctionDate;
    }
    public void setTransanctionDate(Date transanctionDate) {
        this.transanctionDate = transanctionDate;
    }
}
