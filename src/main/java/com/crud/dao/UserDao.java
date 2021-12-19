package com.crud.dao;

import com.crud.model.Role;
import com.crud.model.User;

import java.util.List;
import java.util.Set;

public interface UserDao {

    void saveUser(User user);

    void mergeUser(User user);

    void removeUser(long id);

    User getUser(long id);

    User getUser(String email);

    List<User> getAllUsers();

    List<User> getAllUsers(String s);

    User getUserByName(String email);

    Set<Role> getRoles(long id);

    String getPassword(long id);

    void anyNativeQuery(String text);

    User getReferenceUser(long id);

    Set<User> getUsersByRole(Role role);
}
