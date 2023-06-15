package com.haojunlcode.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {
    List<Customer> selectAllCustomers();
    Optional<Customer> selectCustomerById(Integer id);

    void insertCustomer(Customer customer);
    void deleteCustomerById(Integer id);
    void updateCustomerById(Customer update);

    boolean existPersonWithEmail(String email);
    boolean existPersonWithId(Integer id);




}
