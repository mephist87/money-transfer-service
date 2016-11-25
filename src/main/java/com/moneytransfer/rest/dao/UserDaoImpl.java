package com.moneytransfer.rest.dao;

import com.moneytransfer.rest.model.UserEntity;
import com.moneytransfer.rest.utils.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class UserDaoImpl implements UserDAO {

    @Override
    public UserEntity createOrUpdate(UserEntity user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
        return user;
    }

    @Override
    public List<UserEntity> list() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<UserEntity> list = session.createCriteria(UserEntity.class).list();
        session.close();
        return list;
    }

    @Override
    public UserEntity get(int userId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        UserEntity user = (UserEntity) session.get(UserEntity.class, userId);
        if (user == null) user = new UserEntity();
        session.close();
        return user;
    }

    @Override
    public boolean delete(UserEntity user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        boolean isDeleted = false;
        if (user != null && user.getId() != 0) {
            session.delete(user);
            isDeleted = true;
        }
        session.flush();
        session.close();
        return isDeleted;
    }
}
