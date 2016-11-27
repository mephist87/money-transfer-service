package com.moneytransfer.rest.dao;

import com.moneytransfer.rest.model.AccountEntity;
import com.moneytransfer.rest.model.TransferEntity;
import com.moneytransfer.rest.utils.HibernateUtil;
import org.hibernate.Session;

import javax.inject.Inject;
import java.util.List;

public class TransferDAOImpl implements TransferDAO {

    @Inject
    private AccountDAO accountDAO;

    @Override
    public TransferEntity createOrUpdate(TransferEntity transfer) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        AccountEntity accountFrom = transfer.getAccountFrom();
        AccountEntity accountTo = transfer.getAccountTo();
        int sum = transfer.getSum();

        int accountFromBalance = accountFrom.getBalance();
        int accountToBalance = accountTo.getBalance();
        accountFromBalance -= sum;
        accountToBalance += sum;
        accountFrom.setBalance(accountFromBalance);
        accountTo.setBalance(accountToBalance);
        accountDAO.createOrUpdate(accountFrom);
        accountDAO.createOrUpdate(accountTo);

        session.save(transfer);
        session.getTransaction().commit();
        session.close();
        return transfer;
    }

    @Override
    public boolean delete(TransferEntity transfer) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        boolean isDeleted = false;
        if (transfer != null && transfer.getId() != 0) {
            session.delete(transfer);
            isDeleted = true;
        }
        session.flush();
        session.close();
        return isDeleted;
    }

    @Override
    public TransferEntity makeTransfer(AccountEntity accountFrom, AccountEntity accountTo, int sum) {
        TransferEntity transfer = new TransferEntity(accountFrom, accountTo, sum);
        return createOrUpdate(transfer);
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
