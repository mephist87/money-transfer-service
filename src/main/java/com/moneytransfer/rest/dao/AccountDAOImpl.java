package com.moneytransfer.rest.dao;

import com.moneytransfer.rest.model.AccountEntity;
import com.moneytransfer.rest.utils.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class AccountDAOImpl implements AccountDAO {

    @Override
    public AccountEntity createOrUpdate(AccountEntity account) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(account);
        session.getTransaction().commit();
        session.close();
        return account;
    }

    @Override
    public List<AccountEntity> list() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<AccountEntity> list = session.createCriteria(AccountEntity.class).list();
        session.close();
        return list;
    }

    @Override
    public AccountEntity get(int accountId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        AccountEntity user = (AccountEntity) session.get(AccountEntity.class, accountId);
        if (user == null) user = new AccountEntity();
        session.close();
        return user;
    }

    @Override
    public boolean delete(AccountEntity account) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        boolean isDeleted = false;
        if (account != null && account.getId() != 0) {
            session.delete(account);
            isDeleted = true;
        }
        session.flush();
        session.close();
        return isDeleted;
    }
}
