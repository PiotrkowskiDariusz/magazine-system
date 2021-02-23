package com.dariuszpiotrkowski.magazineSystem.service;

import com.dariuszpiotrkowski.magazineSystem.Custom.SystemUser;
import com.dariuszpiotrkowski.magazineSystem.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    public List<User> findAll();

    public User findById(int id);

    public void save(SystemUser systemUser);

    public void update(User user);

    public void deleteById(int id);

    public User findByUserName(String name);

    public UserDetails loadUserByUsername(String userName);

}
