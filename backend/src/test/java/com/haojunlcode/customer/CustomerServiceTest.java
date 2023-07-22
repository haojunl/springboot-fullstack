package com.haojunlcode.customer;

import com.haojunlcode.exception.DuplicateResourceException;
import com.haojunlcode.exception.RequestValidationException;
import com.haojunlcode.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)//same as openMocks+close
class CustomerServiceTest {

    private CustomerService underTest;
    @Mock
    private CustomerDao customerDao;

    @BeforeEach
    void setUp() {
        //AutoCloseable autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CustomerService(customerDao);
    }

    @Test
    void getAllCustomers() {
        // When
        underTest.getAllCustomers();
        // Then
        Mockito.verify(customerDao).selectAllCustomers();
    }

    @Test
    void getCustomer() {
        // Given
        int id = 1;
        Customer customer = new Customer(id,"Test","Test@gmail.com",19);
        Mockito.when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));
        // When
        Customer actual = underTest.getCustomer(id);
        // Then
        assertThat(actual).isEqualTo(customer);
    }
    @Test
    void getCustomer_Throw() {
        // Given
        int id = 1;
        Mockito.when(customerDao.selectCustomerById(id)).thenReturn(Optional.empty());
        // When
        // Then
        assertThatThrownBy(() -> underTest.getCustomer(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("customer with id [%s] not found" .formatted(id));
    }

    @Test
    void addCustomer() {
        // Given
        String email = "test@gmail.com";
        Mockito.when(customerDao.existPersonWithEmail(email)).thenReturn(false);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest("test", email, 200);
        // When
        underTest.addCustomer(request);//perform method
        // Then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);//capture Customer class
        Mockito.verify(customerDao).insertCustomer(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getId()).isNull();
        assertThat(capturedCustomer.getName()).isEqualTo(request.name());
        assertThat(capturedCustomer.getEmail()).isEqualTo(request.email());
        assertThat(capturedCustomer.getAge()).isEqualTo(request.age());
    }

    @Test
    void addCustomer_Throw() {
        // Given
        String email = "test@gmail.com";
        Mockito.when(customerDao.existPersonWithEmail(email)).thenReturn(true);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest("Alex", email, 19);
        // When
        assertThatThrownBy( () -> underTest.addCustomer(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("email already taken");
        // Then
        Mockito.verify(customerDao, Mockito.never()).insertCustomer( Mockito.any() );
    }

    @Test
    void deleteCustomerById() {
        // Given
        int id=1;
        Mockito.when(customerDao.existPersonWithId(id)).thenReturn(true);
        // When
        underTest.deleteCustomerById(id);
        // Then
        Mockito.verify(customerDao).deleteCustomerById(id);
    }
    @Test
    void deleteCustomerById_Throw() {
        // Given
        int id=1;
        Mockito.when(customerDao.existPersonWithId(id)).thenReturn(false);
        // When
        assertThatThrownBy( () -> underTest.deleteCustomerById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("customer with id [%s] not found" .formatted(id));
        // Then
        Mockito.verify(customerDao, Mockito.never()).deleteCustomerById(id);
    }

    @Test
    void updateCustomer_AllCustomer() {
        // Given
        int id=1;
        Customer customer = new Customer(id,"Test","test@gamil.com",10);
        Mockito.when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));//Mock result for getCustomer(id)
        String newEmail = new String("update@gamil.com");
        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest("update", newEmail, 200);

        Mockito.when(customerDao.existPersonWithEmail(newEmail)).thenReturn(false);//not throw
        // When
        underTest.updateCustomer(id, updateRequest);
        // Then
        ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);
        Mockito.verify(customerDao).updateCustomer(argumentCaptor.capture());
        Customer capturedCustomer = argumentCaptor.getValue();

        assertThat(capturedCustomer.getEmail()).isEqualTo(updateRequest.email());
        assertThat(capturedCustomer.getName()).isEqualTo(updateRequest.name());
        assertThat(capturedCustomer.getAge()).isEqualTo(updateRequest.age());
    }
    @Test
    void updateCustomer_Email() {
        // Given
        int id=1;
        String newEmail = "update@gmail.com";
        Customer customer = new Customer(id,"Test","test@gamil.com",10);
        Mockito.when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));//Mock result for getCustomer(id)
        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(null, newEmail, null);

        Mockito.when(customerDao.existPersonWithEmail(newEmail)).thenReturn(false);//not throw
        // When
        underTest.updateCustomer(id, updateRequest);
        // Then
        ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);
        Mockito.verify(customerDao).updateCustomer(argumentCaptor.capture());
        Customer capturedCustomer = argumentCaptor.getValue();

        assertThat(capturedCustomer.getEmail()).isEqualTo(updateRequest.email());
        assertThat(capturedCustomer.getName()).isEqualTo(customer.getName());
        assertThat(capturedCustomer.getAge()).isEqualTo(customer.getAge());
    }
    @Test
    void updateCustomer_Name() {
        // Given
        int id=1;
        String newName = "update";
        Customer customer = new Customer(id,"Test","test@gamil.com",10);
        Mockito.when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));//Mock result for getCustomer(id)
        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(newName, null, null);
        // When
        underTest.updateCustomer(id, updateRequest);
        // Then
        ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);
        Mockito.verify(customerDao).updateCustomer(argumentCaptor.capture());
        Customer capturedCustomer = argumentCaptor.getValue();

        assertThat(capturedCustomer.getEmail()).isEqualTo(customer.getEmail());
        assertThat(capturedCustomer.getName()).isEqualTo(updateRequest.name());
        assertThat(capturedCustomer.getAge()).isEqualTo(customer.getAge());
    }
    @Test
    void updateCustomer_Age() {
        // Given
        int id=1;
        int newAge = 200;
        Customer customer = new Customer(id,"Test","Test@gmail.com",19);
        Mockito.when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));//Mock result for getCustomer(id)
        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(null, null, newAge);
        // When
        underTest.updateCustomer(id, updateRequest);
        // Then
        ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);
        Mockito.verify(customerDao).updateCustomer(argumentCaptor.capture());
        Customer capturedCustomer = argumentCaptor.getValue();

        assertThat(capturedCustomer.getEmail()).isEqualTo(customer.getEmail());
        assertThat(capturedCustomer.getName()).isEqualTo(customer.getName());
        assertThat(capturedCustomer.getAge()).isEqualTo(updateRequest.age());
    }
    @Test
    void updateCustomer_throwExistPersonWithEmail(){
        //Given
        int id=1;
        String newEmail = "update@gmail.com";
        Customer customer = new Customer(id,"test", "test@gmail.com",10);
        Mockito.when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));
        CustomerUpdateRequest update = new CustomerUpdateRequest("update",newEmail,200);
        Mockito.when(customerDao.existPersonWithEmail(newEmail)).thenReturn(true);
        //When
        assertThatThrownBy( () -> underTest.updateCustomer(id,update))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("email already taken");
        //Then
        Mockito.verify(customerDao, Mockito.never()).updateCustomer(customer);
    }
    @Test
    void updateCustomer_throwNoChange(){
        //Given
        int id=1;
        Customer customer = new Customer(id,"test", "test@gmail.com",10);
        Mockito.when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));
        CustomerUpdateRequest update = new CustomerUpdateRequest(null,null,null);
        //When
        assertThatThrownBy( ()-> underTest.updateCustomer(id,update))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage("no data changes found");
        //Then
        Mockito.verify(customerDao, Mockito.never()).updateCustomer(customer);
    }
}