package com.dariuszpiotrkowski.magazineSystem.service;

import com.dariuszpiotrkowski.magazineSystem.entity.Order;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface OrderService {

    public List<Order> findAll();

    public Order findById(int id);

    public Order findByNumber(String number);

    public void save(Order order);

    public void deleteById(int id);

    public List<Order> searchByNumber(String number);

    public void saveFile(Order order, MultipartFile multipartFile);

    public ResponseEntity<Resource> downloadFile(String path);

    public void checkBom(int orderId);

}
