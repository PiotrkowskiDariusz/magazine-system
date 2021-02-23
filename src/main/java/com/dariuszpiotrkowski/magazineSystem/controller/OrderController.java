package com.dariuszpiotrkowski.magazineSystem.controller;

import com.dariuszpiotrkowski.magazineSystem.entity.*;
import com.dariuszpiotrkowski.magazineSystem.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.*;
import java.util.*;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProjectsService projectsService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public String listOrders(Model model) {

        List<Order> orders = orderService.findAll();

        model.addAttribute("orders", orders);

        return "orders/list-orders";
    }

    @GetMapping("/addOrder")
    public String addOrder(Model model) {

        Order order = new Order();
        List<Project> projects = projectsService.findAll();
        List<Customer> customers = customerService.findAll();

        model.addAttribute("order", order);
        model.addAttribute("projects", projects);
        model.addAttribute("customers", customers);

        return "orders/order-form";
    }

    @GetMapping("/updateOrder")
    public String updateOrder(@ModelAttribute("orderId") int id, Model model) {

        Order order = orderService.findById(id);
        List<Project> projects = projectsService.findAll();
        List<Customer> customers = customerService.findAll();

        model.addAttribute("order", order);
        model.addAttribute("projects", projects);
        model.addAttribute("customers", customers);

        return "orders/order-form";
    }

    @PostMapping("/saveOrder")
    public String saveOrder(@Valid @ModelAttribute("order") Order order,
                            BindingResult bindingResult,
                            @RequestParam("projectId") int projectId,
                            @RequestParam("customerId") int customerId,
                            Model model) {

        if(bindingResult.hasErrors()) {
            model.addAttribute("projects", projectsService.findAll());
            model.addAttribute("customers", customerService.findAll());
            return "orders/order-form";
        }

        Order existing = orderService.findByNumber(order.getNumber());
        if (existing != null && existing.getId() != order.getId()) {
            model.addAttribute("order", order);
            model.addAttribute("orderError", "Zamówienie o tym numerze już istnieje.");
            model.addAttribute("projects", projectsService.findAll());
            model.addAttribute("customers", customerService.findAll());
            return "orders/order-form";
        }

        if (projectId != 0) {
            Project project = projectsService.findById(projectId);
            order.setProject(project);
        }
        else {
            model.addAttribute("projectError", "To pole nie może pozostać puste.");
            model.addAttribute("projects", projectsService.findAll());
            model.addAttribute("customers", customerService.findAll());
            return "orders/order-form";
        }

        if (customerId != 0) {
            Customer customer = customerService.findById(customerId);
            order.setCustomer(customer);
        }
        else {
            model.addAttribute("customerError", "To pole nie może pozostać puste.");
            model.addAttribute("projects", projectsService.findAll());
            model.addAttribute("customers", customerService.findAll());
            return "orders/order-form";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken) && order.getUser() == null) {
            String userName = authentication.getName();
            User user = userService.findByUserName(userName);
            order.setUser(user);
        }

        if (order.getBomPath().length() == 0) {
            order.setBomPath(null);
        }

        if (order.getToBuyPath().length() == 0) {
            order.setToBuyPath(null);
        }

        orderService.save(order);

        return "redirect:/orders/list";
    }

    @GetMapping("/addFile")
    public String addFile(@ModelAttribute("orderId") int id, Model model) {

        Order order = orderService.findById(id);

        model.addAttribute("order", order);

        return "/orders/file-form";
    }

    @PostMapping("/saveFile")
    public String saveFile(@RequestParam("orderId") int id, @RequestParam("file") MultipartFile multipartFile) {

        Order order = orderService.findById(id);
        orderService.saveFile(order, multipartFile);

        return "redirect:/orders/list";
    }

    @GetMapping("/downloadBom/{id}")
    public ResponseEntity<Resource> downloadBom(@PathVariable int id) {

        Order order = orderService.findById(id);
        String filePath = order.getBomPath();

        return orderService.downloadFile(filePath);
    }

    @GetMapping("/downloadToBuy/{id}")
    public ResponseEntity<Resource> downloadToBuy(@PathVariable int id) {

        Order order = orderService.findById(id);
        String filePath = order.getToBuyPath();

        return orderService.downloadFile(filePath);
    }

    @GetMapping("/deleteOrder")
    public String deleteOrder(@ModelAttribute("orderId") int id) {

        Order order = orderService.findById(id);

        if (order.getBomPath() != null) {
            File oldFile = new File(order.getBomPath());
            oldFile.delete();
        }

        orderService.deleteById(id);

        return "redirect:/orders/list";
    }

    @GetMapping("/search")
    public String search(@ModelAttribute("number") String number, Model model) {

        List<Order> orders = orderService.searchByNumber(number);

        model.addAttribute("orders", orders);

        return "orders/list-orders";
    }

    @GetMapping("/checkBom")
    public String checkBom(@ModelAttribute("orderId") int orderId) {

        try {
            orderService.checkBom(orderId);
        }
        catch (Exception e) {
            return "file-error";
        }

        return "redirect:/orders/list";
    }

}
