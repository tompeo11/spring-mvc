package com.tom.javaspring.service;

import com.tom.javaspring.dto.RoleParams;
import com.tom.javaspring.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> getRoles(RoleParams roleParams);

    int getRoleCount(RoleParams roleParams);

    void saveRole(Role role);

    Role getById(int id);

    void deleteRole(int id);
}
