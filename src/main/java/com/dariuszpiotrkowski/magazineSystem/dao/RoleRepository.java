package com.dariuszpiotrkowski.magazineSystem.dao;

import com.dariuszpiotrkowski.magazineSystem.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    public Role findByName(String name);

}
