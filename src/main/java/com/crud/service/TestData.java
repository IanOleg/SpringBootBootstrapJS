package com.crud.service;

import com.crud.model.Role;
import com.crud.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

@Component
public class TestData {

    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;

    @PostConstruct
    void testData(){

        userService.anyNativeQuery("drop table if exists ianoleg242.user_role");
        userService.anyNativeQuery("drop table if exists ianoleg242.users");
        userService.anyNativeQuery("drop table if exists ianoleg242.roles");

        userService.anyNativeQuery("create table if not exists ianoleg242.users(" +
                "id bigint not null AUTO_INCREMENT primary key," +
                "email varchar(255) not null," +
                "firstname varchar(255) not null," +
                "lastname varchar(255) not null," +
                "age tinyint not null," +
                "password varchar(255) null)");

        userService.anyNativeQuery("create table if not exists ianoleg242.roles(" +
                "role varchar(255) not null primary key)");

        userService.anyNativeQuery("create table if not exists ianoleg242.user_role(" +
                "id bigint , " +
                "role varchar(255) , " +
                "primary key (id, role), " +
                "constraint user_role_roles_role_fk foreign key (role) references ianoleg242.roles (role), " +
                "constraint user_role_users_id_fk foreign key (id) references ianoleg242.users (id))");

        Role roleAdmin   = new Role("ROLE_ADMIN");
        Role roleUser    = new Role("ROLE_USER");
        //Role roleManager = new Role("ROLE_MANAGER");

        roleService.saveRole(roleAdmin);
        roleService.saveRole(roleUser);
        //roleService.saveRole(roleManager);

        User userFirst = new User( "first@mail.com", "321", "First", "First", (byte) 20);
        User userSecond = new User("second@mail.com", "321", "Second", "Second", (byte) 30);

        userFirst.addRole(roleAdmin);
        userFirst.addRole(roleUser);

        userSecond.addRole(roleUser);

        userService.saveUser(userFirst);
        userService.saveUser(userSecond);
    }
}
