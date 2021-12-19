package com.crud.service;

import com.crud.dao.UserDao;
import com.crud.model.Role;
import com.crud.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Component
@Transactional
public class UserServiceImp implements UserService{

    @Autowired
    private UserDao userDao;

    @Override
    public void saveUser(User user) {
        userDao.saveUser(user);
    }

    @Override
    public void mergeUser(User user) {

        if (user.getPassword().isEmpty()){
            user.setPassword(userDao.getPassword(user.getId()));
        }
        userDao.mergeUser(user);
    }

    @Override
    public void removeUser(long id) {
        userDao.removeUser(id);
    }

    @Override
    public User getUser(long id) {
        return userDao.getUser(id);
    }

    @Override
    public User getUserByName(String Name) {
        return userDao.getUserByName(Name);
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = userDao.getAllUsers();
        return userList;
    }

    @Override
    public Set<Role> getRoles(long id) {
        return userDao.getRoles(id);
    }

    @Override
    public List<User> getAllUsers(String s) {
        return userDao.getAllUsers(s);
    }

    @Override
    public void anyNativeQuery(String text) {
        userDao.anyNativeQuery(text);
    }

    @Override
    public User getReferenceUser(long id) {
        return userDao.getReferenceUser(id);
    }

    @Override
    public Set<User> getUsersByRole(Role role){
        return userDao.getUsersByRole(role);
    }

}
