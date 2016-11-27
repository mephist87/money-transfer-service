package com.moneytransfer.rest.dao;

import com.moneytransfer.rest.model.AccountEntity;
import com.moneytransfer.rest.utils.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class AccountDAOImpl implements AccountDAO {

    @Override
    public AccountEntity create(AccountEntity account) {
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
        session.getTransaction().commit();
        session.close();
        return list;
    }

    @Override
    public AccountEntity get(int accountId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        AccountEntity account = (AccountEntity) session.get(AccountEntity.class, accountId);
        session.getTransaction().commit();
        session.close();
        return account;
    }

    @Override
    public boolean delete(AccountEntity account) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        boolean isDeleted = false;
        if (account != null) {
            session.delete(account);
            isDeleted = true;
        }
        session.flush();
        session.getTransaction().commit();
        session.close();
        return isDeleted;
    }
}
