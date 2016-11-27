package com.moneytransfer.rest.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "TRANSFER", uniqueConstraints = {@UniqueConstraint(columnNames = "TRANSFER_ID")})
public class TransferEntity {
    private int id;
    private AccountEntity accountFrom;
    private AccountEntity accountTo;
    private int sum;

    public TransferEntity() {
    }

    public TransferEntity(AccountEntity accountFrom, AccountEntity accountTo, int sum) {
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
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

    @ManyToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "ACCOUNT_FROM_ID", referencedColumnName = "ACCOUNT_ID")
    public AccountEntity getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(AccountEntity accountFrom) {
        this.accountFrom = accountFrom;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "ACCOUNT_TO_ID", referencedColumnName = "ACCOUNT_ID")
    public AccountEntity getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(AccountEntity accountTo) {
        this.accountTo = accountTo;
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
