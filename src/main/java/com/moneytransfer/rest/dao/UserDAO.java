package com.moneytransfer.rest.dao;

import com.moneytransfer.rest.model.UserEntity;

import java.util.List;

public interface UserDAO {
    UserEntity create(UserEntity user);
    List<UserEntity> list();
    UserEntity get(int userId);
    boolean delete(UserEntity user);
}
