package com.moneytransfer.rest.dao;

import com.moneytransfer.rest.model.TransferEntity;

import java.util.List;

public interface TransferDAO {
    TransferEntity makeTransfer(int accountFromId, int accountToId, int sum);
    boolean rollbackTransfer(TransferEntity transfer);
    TransferEntity get(int transferId);
    List<TransferEntity> list();
}
