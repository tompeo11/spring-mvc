package com.tom.javaspring.dao;

import com.tom.javaspring.dto.UserParams;
import com.tom.javaspring.entity.UserEntity;

import java.util.List;

public interface UserDAO {
    List<UserEntity> getUsers(UserParams userParams);
    int getUserCount(UserParams userParams);
    int saveUser(UserEntity userEntity);
    UserEntity getById(int id);
    void deleteUser(int id);
    UserEntity findByUserName(String userName);
    UserEntity findByEmail(String email);
}
