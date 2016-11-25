package com.moneytransfer.rest.dao;

import com.moneytransfer.rest.model.TransferEntity;

import java.util.List;

public class TransferDAOImpl implements TransferDAO {

    @Override
    public TransferEntity makeTransfer(int accountFromId, int accountToId, int sum) {
        return null;
    }

    @Override
    public boolean rollbackTransfer(TransferEntity transfer) {
        return false;
    }

    @Override
    public TransferEntity get(int transferId) {
        return null;
    }

    @Override
    public List<TransferEntity> list() {
        return null;
    }
}
