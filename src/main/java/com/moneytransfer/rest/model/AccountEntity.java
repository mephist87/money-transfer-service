package com.moneytransfer.rest.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "ACCOUNT", uniqueConstraints = {@UniqueConstraint(columnNames = "ACCOUNT_ID")})
public class AccountEntity {

    private int id;
    private UserEntity user;
    private int balance;

    public AccountEntity() {
    }

    public AccountEntity(UserEntity user, int balance) {
        this.user = user;
        this.balance = balance;
    }

    @Id
    @GenericGenerator(name = "agen" , strategy = "increment")
    @GeneratedValue(generator = "agen")
    @Column(name = "ACCOUNT_ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Basic
    @Column(name = "BALANCE")
    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "AccountEntity{" +
                "id=" + id +
                ", fk_user_id=" + user.getId() +
                ", balance=" + balance +
                '}';
    }
}
