package com.tom.javaspring.service.impl;

import com.tom.javaspring.dao.RoleDAO;
import com.tom.javaspring.dao.UserDAO;
import com.tom.javaspring.dto.UserParams;
import com.tom.javaspring.entity.Role;
import com.tom.javaspring.entity.UserEntity;
import com.tom.javaspring.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private final RoleDAO roleDAO;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDAO userDAO, RoleDAO roleDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public List<UserEntity> getUsers(UserParams userParams) {
        return userDAO.getUsers(userParams);
    }

    @Override
    public UserEntity getByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    @Override
    public UserEntity getByName(String name) {
        return userDAO.findByUserName(name);
    }

    @Override
    @Transactional
    public int getUserCount(UserParams userParams) {
        return userDAO.getUserCount(userParams);
    }

    @Override
    @Transactional
    public int saveUser(UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        if (userEntity.getRoles() == null) {
            Role role = roleDAO.findByName("ROLE_EMPLOYEE");

            Set<Role> roles = new HashSet<>();
            roles.add(role);
            userEntity.setRoles(roles);
        }


        return userDAO.saveUser(userEntity);
    }

    @Override   
    @Transactional
    public UserEntity getById(int id) {
        return userDAO.getById(id);
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        userDAO.deleteUser(id);
    }
}
