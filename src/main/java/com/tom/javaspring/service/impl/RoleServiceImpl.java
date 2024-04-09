package com.tom.javaspring.service.impl;

import com.tom.javaspring.dao.CustomerDAO;
import com.tom.javaspring.dao.RoleDAO;
import com.tom.javaspring.dto.RoleParams;
import com.tom.javaspring.entity.Role;
import com.tom.javaspring.service.RoleService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleDAO roleDAO;

    public RoleServiceImpl(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }


    @Override
    @Transactional
    public List<Role> getRoles(RoleParams roleParams) {
        return roleDAO.getRoles(roleParams);
    }

    @Override
    @Transactional
    public int getRoleCount(RoleParams roleParams) {
        return roleDAO.getRoleCount(roleParams);
    }

    @Override
    @Transactional
    public void saveRole(Role role) {
        roleDAO.saveRole(role);
    }

    @Override
    @Transactional
    public Role getById(int id) {
        return roleDAO.getById(id);
    }

    @Override
    @Transactional
    public void deleteRole(int id) {
        roleDAO.deleteRole(id);
    }
}
