package com.barboza.Login.service.Impl;

import com.barboza.Login.models.Role;
import com.barboza.Login.models.User;
import com.barboza.Login.repository.RoleRepository;
import com.barboza.Login.repository.UserRepository;
import com.barboza.Login.service.UserService;
import com.barboza.Login.utils.UtilsService;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final RoleRepository roleRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final UtilsService utilsService;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, UtilsService utilsService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.utilsService = utilsService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.info("User not found in DB");
            throw new UsernameNotFoundException("User not found in DB");
        }
        log.info("User found in DB {} ", user);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public User register(User user) {
        log.info("Saving new user {}", user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        ArrayList<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setName("ROLE_USER");
        role.setId(UUID.randomUUID().toString());
        roles.add(role);
        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} ", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info(">> addRoleToUser");
        log.info("Adding role {} to user {}", roleName, username);
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
        log.info("<< addRoleToUser");
    }

    @Override
    public MappingJacksonValue getUser(String username) {
        log.info("Fetching user {} ", username);
        return utilsService.mapDTO(getUserName(username));
    }

    @Override
    public User getUserName(String username) {
        log.info("Fetching user name {} ", username);
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        log.info("Fetching all user");
        return userRepository.findAll();
    }
}
