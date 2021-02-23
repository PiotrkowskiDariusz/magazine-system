package com.dariuszpiotrkowski.magazineSystem.service;

import com.dariuszpiotrkowski.magazineSystem.entity.Role;

import java.util.List;

public interface RoleService {

    public List<Role> findAll();

    public Role findById(int id);

    public void save(Role role);

    public void deleteById(int id);

}
