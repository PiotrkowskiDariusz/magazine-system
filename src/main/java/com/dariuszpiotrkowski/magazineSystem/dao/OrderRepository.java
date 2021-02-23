package com.dariuszpiotrkowski.magazineSystem.dao;

import com.dariuszpiotrkowski.magazineSystem.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    public List<Order> findByNumberContainsAllIgnoreCase(String number);

    public Order findByNumber(String number);
}
