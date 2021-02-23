package com.dariuszpiotrkowski.magazineSystem.service;

import com.dariuszpiotrkowski.magazineSystem.dao.CustomerRepository;
import com.dariuszpiotrkowski.magazineSystem.entity.Customer;
import com.dariuszpiotrkowski.magazineSystem.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderService orderService;

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer findById(int id) {
        Optional<Customer> result = customerRepository.findById(id);

        Customer customer = null;

        if (result.isPresent()) {
            customer = result.get();
        }
        else {
            throw new RuntimeException("Nie znaleziono klienta o id = " + id);
        }

        return customer;
    }

    @Override
    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public boolean deleteById(int id) {

        Optional<Customer> customer = customerRepository.findById(id);

        if (customer.isPresent()){
            List<Order> orders = orderService.findAll();
            for (Order order : orders) {
                if(order.getCustomer().getId() == id){
                    return false;
                }
            }
        }

        customerRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Customer> searchByName(String name) {
        List<Customer> results = null;

        if (name != null && (name.trim().length() > 0)) {
            results = customerRepository.findByNameContainsAllIgnoreCase(name);
        }
        else {
            results = findAll();
        }

        return results;
    }
}
