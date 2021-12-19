package com.crud.dao;

import com.crud.model.Role;
import com.crud.model.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Repository
public class UserDaoImp implements UserDao {

    @PersistenceContext
    public EntityManager entityManager;

    @Override
    public void saveUser(User user) {

        RoleDao roleDao = new RoleDaoImp();
        if (user.getRoles() != null){
            for (Object role: user.getRoles()) {
                entityManager.merge(role);
            }
        }
        entityManager.persist(user);
    }

    @Override
    public void mergeUser(User user) {

        entityManager.merge(user);
    }

    @Override
    public void removeUser(long id) {

        entityManager.remove(entityManager.getReference(User.class, id));
    }

    @Override
    public User getReferenceUser(long id) {

        return entityManager.getReference(User.class, id);
    }

    @Override
    public User getUser(long id) {

        return entityManager.find(User.class, id);
    }

    @Override
    public User getUser(String email) {

        return getUserByName(email);
    }

    public String getPassword(long id){

        return entityManager.createQuery("select s.password from User s where s.id= :id", String.class)
                            .setParameter("id", id)
                            .getSingleResult();
    }

    public Set<Role> getRoles(long id) {

        List resultList = entityManager.createNativeQuery("select r.role " +
                                                             "from roles r " +
                                                             "inner join user_role ur " +
                                                             "on r.role = ur.role " +
                                                                "and ur.id= :id", Role.class)
                                        .setParameter("id", id)
                                        .getResultList();

        return new HashSet<Role>(resultList);
    }

    public Set<User> getUsersByRole(Role role) {

        List resultList = entityManager.createNativeQuery("select * " +
                                                             "from users usr " +
                                                             "inner join user_role ur " +
                                                             "on usr.id = ur.id " +
                                                             "and ur.role= :role", User.class)
                                                    .setParameter("role", role)
                                                    .getResultList();

        return new HashSet<User>(resultList);
    }

    @Override
    public User getUserByName(String s) {

        return entityManager.createQuery("select s from User s where s.email= :email", User.class)
                            .setParameter("email", s)
                            .getSingleResult();
    }

    @Override
    public List<User> getAllUsers() {

        return entityManager.createQuery("select s from User s").getResultList();
    }

    @Override
    public List<User> getAllUsers(String s) {

        return entityManager.createQuery("select s from User s where s.email =: email")
                            .setParameter("email", s)
                            .getResultList();
    }

    @Override
    public void anyNativeQuery(String text) {

        entityManager.createNativeQuery(text)
                     .executeUpdate();
    }
}