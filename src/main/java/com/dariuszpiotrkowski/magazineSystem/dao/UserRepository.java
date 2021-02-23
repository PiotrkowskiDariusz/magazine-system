package com.dariuszpiotrkowski.magazineSystem.dao;

import com.dariuszpiotrkowski.magazineSystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    public User findByUserName(String name);

}
