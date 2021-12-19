package com.crud.controller;

import com.crud.model.Role;
import com.crud.model.User;
import com.crud.service.RoleService;
import com.crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping
public class UsersController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping(value = "/")
    public String loginPage() {

        return "redirect:/login";
    }

    @GetMapping(value = "/admin")
    public String getAllUsers(Model model, Principal principal) {

        model.addAttribute("newUser",  new User());
        model.addAttribute("allRoles", roleService.getAllRoles());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("user",  userService.getUserByName(principal.getName()));
        model.addAttribute("usersWithRoleUser", userService.getUsersByRole(roleService.getRole("ROLE_USER")));

        return "home";
    }

    @GetMapping(value = "/admin/getUser/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id) {

        User user = userService.getUser(id);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping(value = "/admin/allRoles")
    public ResponseEntity<List<Role>> allRoles() {

        List<Role> roles = roleService.getAllRoles();
        return ResponseEntity.ok().body(roles);
    }

    @PostMapping(value = "/admin/saveUser")
    public ResponseEntity<User> saveUser(@RequestBody User user) {

        userService.saveUser(user);
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping(value = "/admin/removeUser/{id}")
    public @ResponseBody Long removeUser(@PathVariable long id) {

        userService.removeUser(id);
        return id;
    }

    @PutMapping (value = "/admin/mergeUser")
    public @ResponseBody Long mergeUser(@RequestBody User user) {

        userService.mergeUser(user);
        return user.getId();
    }

    @GetMapping(value = "/adminJSON")
    public @ResponseBody List<User> getAllUsers() {

        return userService.getAllUsers();
    }

    @GetMapping(value = "/user")
    public String getUser(Model model, Principal principal) {

        model.addAttribute("usersWithRoleUser", userService.getAllUsers(principal.getName()));
        model.addAttribute("user",  userService.getUserByName(principal.getName()));

        return "home";
    }
}
