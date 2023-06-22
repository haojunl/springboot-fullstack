package com.haojunlcode.customer;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class CustomerJPADataAccessServiceTest {

    private CustomerJPADataAccessService underTest;
    private AutoCloseable autoCloseable;
    @Mock
    private CustomerRepository customerRepository;//Mock entire  class

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);//init Mock object
        underTest = new CustomerJPADataAccessService(customerRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void selectAllCustomers() {
        // When
        underTest.selectAllCustomers();
        // Then
        Mockito.verify(customerRepository)
                .findAll();
    }

    @Test
    void selectCustomerById() {
        // Given
        int id =1;
        // When
        underTest.selectCustomerById(id);
        // Then
        Mockito.verify(customerRepository)
                .findById(id);
    }

    @Test
    void existPersonWithEmail() {
        // Given
        String email = new String("test@gamil.com");
        // When
        underTest.existPersonWithEmail(email);
        // Then
        Mockito.verify(customerRepository).existsCustomerByEmail(email);
    }

    @Test
    void existPersonWithId() {
        // Given
        int id = 1;
        // When
        underTest.existPersonWithId(id);
        // Then
        Mockito.verify(customerRepository).existsCustomerById(id);
    }

    @Test
    void insertCustomer() {
        // Given
        Customer customer = new Customer(1,"Ali","Ali@gmail.com",2);
        // When
        underTest.insertCustomer(customer);
        // Then
        Mockito.verify(customerRepository).save(customer);
    }

    @Test
    void deleteCustomerById() {
        // Given
        int id =1;
        // When
        underTest.deleteCustomerById(id);
        // Then
        Mockito.verify(customerRepository).deleteById(id);
    }

    @Test
    void updateCustomer() {
        // Given
        Customer customer = new Customer(1,"Ali","Ali@gmail.com",2);
        // When
        underTest.updateCustomer(customer);
        // Then
        Mockito.verify(customerRepository).save(customer);
    }
}