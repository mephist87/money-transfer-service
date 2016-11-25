package com.moneytransfer.rest.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "TRANSFER", uniqueConstraints = {@UniqueConstraint(columnNames = "TRANSFER_ID")})
public class TransferEntity {
    private int id;
    private int accountFromId;
    private int accountToId;
    private int sum;

    public TransferEntity() {
    }

    public TransferEntity(int accountFromId, int accountToId, int sum) {
        this.accountFromId = accountFromId;
        this.accountToId = accountToId;
        this.sum = sum;
    }

    @Id
    @GenericGenerator(name = "tgen" , strategy = "increment")
    @GeneratedValue(generator = "tgen")
    @Column(name = "TRANSFER_ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "ACCOUNT_FROM_ID")
    public int getAccountFromId() {
        return accountFromId;
    }

    public void setAccountFromId(int accountFromId) {
        this.accountFromId = accountFromId;
    }

    @Basic
    @Column(name = "ACCOUNT_TO_ID")
    public int getAccountToId() {
        return accountToId;
    }

    public void setAccountToId(int accountToId) {
        this.accountToId = accountToId;
    }

    @Basic
    @Column(name = "SUM")
    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
