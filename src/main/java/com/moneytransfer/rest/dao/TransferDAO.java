package com.moneytransfer.rest.dao;

import com.moneytransfer.rest.model.AccountEntity;
import com.moneytransfer.rest.model.TransferEntity;

import java.util.List;

public interface TransferDAO {
    TransferEntity createOrUpdate(TransferEntity transfer);
    boolean delete(TransferEntity transfer);
    TransferEntity makeTransfer(AccountEntity accountFrom, AccountEntity accountTo, int sum);
    boolean rollbackTransfer(TransferEntity transfer);
    TransferEntity get(int transferId);
    List<TransferEntity> list();
}
