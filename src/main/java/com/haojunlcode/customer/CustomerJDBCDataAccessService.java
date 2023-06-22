package com.haojunlcode.customer;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class CustomerJDBCDataAccessService implements CustomerDao{
    private final JdbcTemplate jdbcTemplate;
    private final CustomerRowMapper customerRowMapper;

    public CustomerJDBCDataAccessService(JdbcTemplate jdbcTemplate, CustomerRowMapper customerRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = customerRowMapper;
    }


    @Override
    public List<Customer> selectAllCustomers() {
        var sql = """
                SELECT id, name, email, age
                FROM customer
                """;
        /*
        RowMapper<Customer> customerRowMapper = (rs, rowNum) -> {
            Customer customer = new Customer(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getInt("age")
        );
            return customer;
        };
        */
        List<Customer> customers = jdbcTemplate.query(sql, customerRowMapper);
        return customers;
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        var sql = """
                SELECT id, name, email,age
                FROM customer
                WHERE id = ?
                """;
        Optional<Customer> customers = jdbcTemplate.query(sql, customerRowMapper, id)
                .stream()
                .findFirst();
        return customers;
    }


    @Override
    public void insertCustomer(Customer customer) {
        var sql = """
                INSERT INTO customer(name, email, age)
                VALUES (?,?,?)
                """;//prepare statement
        int result = jdbcTemplate.update(sql,
                customer.getName(),
                customer.getEmail(),
                customer.getAge()
        );
        System.out.println("jdbcTemplate.update = " + result);
    }

    @Override
    public void deleteCustomerById(Integer id) {
        var sql = """
                DELETE
                FROM customer
                WHERE id = ?
                """;
        int result = jdbcTemplate.update(sql, id);
        System.out.println("deleteCustomerById result = " + result);
    }

    @Override
    public void updateCustomer(Customer update) {
        if(update.getName()!=null){
            var sql = """
                UPDATE customer
                SET name = ?
                WHERE id = ?
                """;
            int result = jdbcTemplate.update(sql, update.getName(), update.getId());
            System.out.println("update customer name result = " + result);
        }
        if(update.getEmail()!=null){
            var sql = """
                    UPDATE customer
                    SET email = ?
                    WHERE id = ?
                    """;
            int result = jdbcTemplate.update(sql, update.getEmail(), update.getId());
            System.out.println("update customer Email result = " + result);
        }
        if(update.getAge()!=null){
            var sql = """
                    UPDATE customer
                    SET age = ?
                    WHERE id = ?
                    """;
            int result = jdbcTemplate.update(sql, update.getAge(), update.getId());
            System.out.println("update customer age result = " + result);
        }
    }

    @Override
    public boolean existPersonWithEmail(String email) {
        var sql = """
                SELECT count(id)
                FROM customer
                WHERE email= ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;//count==0: email does not exist, ==1 exist
    }

    @Override
    public boolean existPersonWithId(Integer id) {
        var sql = """
                SELECT count(id)
                FROM customer
                WHERE id = ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count!=null && count>0;
    }
}
