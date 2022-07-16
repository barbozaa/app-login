package com.barboza.Login.service;

import com.barboza.Login.models.Role;
import com.barboza.Login.models.User;
import org.springframework.http.converter.json.MappingJacksonValue;

import java.util.List;

public interface UserService {
    User register(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    MappingJacksonValue getUser(String username);
    User getUserName(String username);
    List<User> getUsers();
}
