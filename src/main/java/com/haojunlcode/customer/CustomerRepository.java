package com.haojunlcode.customer;

import org.springframework.data.jpa.repository.JpaRepository;

//@Repository //default
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    boolean existsCustomerByEmail(String email);//JPA construct sql query
}
