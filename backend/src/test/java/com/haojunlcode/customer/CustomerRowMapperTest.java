package com.haojunlcode.customer;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerRowMapperTest {

    @Test
    void mapRow() throws Exception {
        // Given
        CustomerRowMapper customerRowMapper = new CustomerRowMapper();

        ResultSet resultSet= Mockito.mock(ResultSet.class);//Mock resultSet
        Mockito.when(resultSet.getInt("id")).thenReturn(1);
        Mockito.when(resultSet.getInt("age")).thenReturn(19);
        Mockito.when(resultSet.getString("name")).thenReturn("Alex");
        Mockito.when(resultSet.getString("email")).thenReturn("Alex@gmail.com");
        // When
        Customer actual = customerRowMapper.mapRow(resultSet,1);
        // Then
        Customer expected = new Customer(1, "Alex","Alex@gmail.com",19);
        assertThat(expected).isEqualTo(actual);
    }
}