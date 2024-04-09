package com.tom.javaspring.dao;

import com.tom.javaspring.dto.RoleParams;
import com.tom.javaspring.entity.Role;

import java.util.List;

public interface RoleDAO {
    List<Role> getRoles(RoleParams roleParams);

    int getRoleCount(RoleParams roleParams);

    void saveRole(Role role);

    Role getById(int id);

    void deleteRole(int id);
}
