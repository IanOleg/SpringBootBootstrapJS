package com.crud.service;

import com.crud.model.Role;
import com.crud.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    void saveUser(User user);

    void mergeUser(User user);

    void removeUser(long id);

    User getUser(long id);

    User getUserByName(String Name);

    List<User> getAllUsers();

    List<User> getAllUsers(String s);

    public Set<Role> getRoles(long id);

    void anyNativeQuery(String text);

    User getReferenceUser(long id);

    Set<User> getUsersByRole(Role role);
}
