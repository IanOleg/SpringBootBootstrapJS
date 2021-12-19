package com.crud.dao;

import com.crud.model.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class RoleDaoImp implements RoleDao{

    @PersistenceContext
    public EntityManager entityManager;

    @Override
    public void saveRole(Role role) {

        entityManager.persist(role);
    }

    @Override
    public void mergeRole(Role role) {

        entityManager.merge(role);
    }

    @Override
    public void removeRole(String role) {

        entityManager.remove(getReferenceRole(role));
    }

    @Override
    public Role getRole(String role) {

        return entityManager.find(Role.class, role);
    }

    @Override
    public Role getReferenceRole(String role) {

        return entityManager.getReference(Role.class, role);
    }

    @Override
    public List<Role> getAllRoles() {

        return entityManager.createQuery("select r from Role r").getResultList();
    }
}
