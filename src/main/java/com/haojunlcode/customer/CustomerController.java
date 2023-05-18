package com.haojunlcode.customer;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {//injected from application context(Bean)
        this.customerService = customerService;
    }


    /*
    @RequestMapping(
            path = "api/v1/customers",
            method = RequestMethod.GET
    )//same as @GetMapping("api/v1/customer") */

    @GetMapping("api/v1/customers")
    public List<Customer> getCustomers(){
        return customerService.getAllCustomers();
    }

    /*
    @RequestMapping(
            path = "api/v1/customers/{customerId}",//{ } path variable
            method = RequestMethod.GET
    )//same as @GetMapping("api/v1/customer")*/
    @GetMapping("api/v1/customers/{customerId}")
    public Customer getCustomer(@PathVariable("customerId") Integer customerId){//capture path variable
        return customerService.getCustomer(customerId);
    }
}
