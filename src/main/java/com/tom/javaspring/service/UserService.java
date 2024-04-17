package com.tom.javaspring.service;

import com.tom.javaspring.dto.UserParams;
import com.tom.javaspring.entity.UserEntity;

import java.util.List;

public interface UserService {
    List<UserEntity> getUsers(UserParams userParams);

    int getUserCount(UserParams userParams);

    void saveUser(UserEntity userEntity);

    UserEntity getById(int id);

    void deleteUser(int id);

}
