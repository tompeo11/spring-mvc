package com.tom.javaspring.service.impl;

import com.tom.javaspring.dao.UserDAO;
import com.tom.javaspring.dto.UserParams;
import com.tom.javaspring.entity.UserEntity;
import com.tom.javaspring.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserEntity> getUsers(UserParams userParams) {
        return userDAO.getUsers(userParams);
    }

    @Override
    public int getUserCount(UserParams userParams) {
        return userDAO.getUserCount(userParams);
    }

    @Override
    @Transactional
    public void saveUser(UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userDAO.saveUser(userEntity);
    }

    @Override
    public UserEntity getById(int id) {
        return userDAO.getById(id);
    }

    @Override
    public void deleteUser(int id) {
        userDAO.deleteUser(id);
    }
}
