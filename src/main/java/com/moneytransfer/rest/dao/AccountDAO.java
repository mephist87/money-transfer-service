package com.moneytransfer.rest.dao;

import com.moneytransfer.rest.model.AccountEntity;

import java.util.List;

public interface AccountDAO {
    AccountEntity createOrUpdate(AccountEntity account);
    List<AccountEntity> list();
    AccountEntity get(int accountId);
    boolean delete(AccountEntity account);
}
