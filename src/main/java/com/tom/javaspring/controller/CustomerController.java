package com.tom.javaspring.controller;

import com.tom.javaspring.dto.CustomerParams;
import com.tom.javaspring.entity.Customer;
import com.tom.javaspring.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/list")
    public String listCustomers(@ModelAttribute CustomerParams customerParams, Model theModel) {
        List<Customer> theCustomers = customerService.getCustomers(customerParams);
        theModel.addAttribute("customerParams", customerParams);
        theModel.addAttribute("customers", theCustomers);

        int customersCount = customerService.getCustomerCount(customerParams);
        int totalPages = (int) Math.ceil((double) customersCount / customerParams.getPageSize());

        theModel.addAttribute("totalCount", customersCount);
        theModel.addAttribute("totalPages", totalPages);
        theModel.addAttribute("customers", theCustomers);

        return "/customer/list_customer";
    }

    @GetMapping("/new")
    public String showFormForAdd(Model theModel) {
        Customer theCustomer = new Customer();
        theModel.addAttribute("customer", theCustomer);
        theModel.addAttribute("isUpdate", false);

        return "/customer/form_customer";
    }

    @PostMapping("/save")
    public String saveCustomer(@ModelAttribute("customer") Customer theCustomer, RedirectAttributes attributes) {
        if (theCustomer.getId() != 0) {
            attributes.addFlashAttribute("message", "Update success");
        } else {
            attributes.addFlashAttribute("message", "Add new success");
        }

        customerService.saveCustomer(theCustomer);

        return "redirect:/customer/list";
    }

    @GetMapping("/edit/{id}")
    public String updateCustomer(Model theModel, @PathVariable int id) {
        Customer customer = customerService.getById(id);
        theModel.addAttribute("customer", customer);
        theModel.addAttribute("isUpdate", true);

        return "/customer/form_customer";
    }

    @PostMapping("/delete/{id}")
    public String deleteCustomer(Model theModel, @PathVariable int id, RedirectAttributes attributes) {
        customerService.deleteCustomer(id);
        attributes.addFlashAttribute("message", "Delete success");


        return "redirect:/customer/list";
    }

    @GetMapping("/view/{id}")
    public String viewCustomers(@ModelAttribute CustomerParams customerParams, Model theModel, @PathVariable int id) {
        Customer customer = customerService.getById(id);
        theModel.addAttribute("customer", customer);

        return "/customer/view_customer";
    }
}
