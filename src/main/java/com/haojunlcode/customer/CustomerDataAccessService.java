package com.haojunlcode.customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDataAccessService implements CustomerDao{

    private static final List<Customer> customers;//db

    static{
        customers = new ArrayList<>();
        Customer alex = new Customer(
                1,
                "Alex",
                "alex@gmail.com",
                21

        );
        customers.add(alex);

        Customer jamila = new Customer(
                2,
                "jamila",
                "jamila@gmail.com",
                19

        );
        customers.add(jamila);
    }



    @Override
    public List<Customer> selectAllCustomers() {
        return customers;
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        return customers.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }
}