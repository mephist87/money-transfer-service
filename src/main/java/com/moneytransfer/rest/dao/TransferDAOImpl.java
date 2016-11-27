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
    public TransferEntity create(TransferEntity transfer) {
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
        return create(transfer);
    }

    @Override
    public boolean rollbackTransfer(TransferEntity transfer) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        AccountEntity accountFrom = transfer.getAccountFrom();
        AccountEntity accountTo = transfer.getAccountTo();
        if (accountFrom != null && accountTo != null) {
            int transferSum = transfer.getSum();
            int accountFromCurrentBalance = accountFrom.getBalance();
            int accountToCurrentBalance = accountTo.getBalance();
            accountFrom.setBalance(accountFromCurrentBalance + transferSum);
            accountTo.setBalance(accountToCurrentBalance - transferSum);
            delete(transfer);
            session.close();
            return true;
        }
        session.close();
        return false;
    }

    @Override
    public TransferEntity get(int transferId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        TransferEntity transfer = (TransferEntity) session.get(TransferEntity.class, transferId);
        if (transfer == null) transfer = new TransferEntity();
        session.close();
        return transfer;
    }

    @Override
    public List<TransferEntity> list() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<TransferEntity> list = session.createCriteria(TransferEntity.class).list();
        session.close();
        return list;
    }
}
