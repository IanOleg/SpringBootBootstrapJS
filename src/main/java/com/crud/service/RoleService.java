package com.crud.service;

import com.crud.model.Role;

import java.util.List;

public interface RoleService {

    void saveRole(Role role);

    void mergeRole(Role role);

    void removeRole(String role);

    Role getRole(String role);

    List<Role> getAllRoles();
}
