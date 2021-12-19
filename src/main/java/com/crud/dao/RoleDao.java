package com.crud.dao;

import com.crud.model.Role;

import java.util.List;

public interface RoleDao {

    void saveRole(Role role);

    void mergeRole(Role role);

    void removeRole(String role);

    Role getRole(String role);

    Role getReferenceRole(String role);

    List<Role> getAllRoles();
}
