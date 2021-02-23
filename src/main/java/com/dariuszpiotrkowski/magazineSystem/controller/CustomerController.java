package com.dariuszpiotrkowski.magazineSystem.controller;

import com.dariuszpiotrkowski.magazineSystem.entity.Customer;
import com.dariuszpiotrkowski.magazineSystem.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/list")
    public String listCustomers(Model model) {

        List<Customer> customers = customerService.findAll();

        model.addAttribute("customers", customers);

        return "/customers/list-customers";
    }

    @GetMapping("/addCustomer")
    public String addCustomer(Model model){

        Customer customer = new Customer();

        model.addAttribute("customer", customer);

        return "/customers/customer-form";
    }

    @GetMapping("/updateCustomer")
    public String updateCustomer(@ModelAttribute("customerId") int id, Model model) {

        Customer customer = customerService.findById(id);

        model.addAttribute("customer", customer);

        return "/customers/customer-form";
    }

    @PostMapping("/saveCustomer")
    public String saveCustomer(@ModelAttribute("customer") Customer customer) {

        customerService.save(customer);

        return "redirect:/customers/list";
    }

    @GetMapping("/deleteCustomer")
    public String deleteCustomer(@ModelAttribute("customerId") int id, Model model) {

        if(customerService.deleteById(id) == true) {
            return "redirect:/customers/list";
        }
        else {
            model.addAttribute("deleteError", "Nie można usunąć klienta. Istnieją powiązane elementy podrzędne (zlecenia).");
            model.addAttribute("customers", customerService.findAll());
            return "/customers/list-customers";
        }
    }

    @GetMapping("/search")
    public String search(Model model, String name) {

        List<Customer> customers = customerService.searchByName(name);

        model.addAttribute("customers", customers);

        return "/customers/list-customers";
    }

}
