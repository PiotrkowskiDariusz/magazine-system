package com.dariuszpiotrkowski.magazineSystem.dao;

import com.dariuszpiotrkowski.magazineSystem.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    public List<Customer> findByNameContainsAllIgnoreCase(String name);

}
