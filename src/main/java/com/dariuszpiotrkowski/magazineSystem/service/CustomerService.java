package com.dariuszpiotrkowski.magazineSystem.service;

import com.dariuszpiotrkowski.magazineSystem.entity.Customer;

import java.util.List;

public interface CustomerService {

    public List<Customer> findAll();

    public Customer findById(int id);

    public void save(Customer customer);

    public boolean deleteById(int id);

    public List<Customer> searchByName(String name);

}
