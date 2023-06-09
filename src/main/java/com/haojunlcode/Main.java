package com.haojunlcode;

import com.haojunlcode.customer.Customer;
import com.haojunlcode.customer.CustomerDao;
import com.haojunlcode.customer.CustomerRepository;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.annotation.RequestScope;

import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
//@ComponentScan(basePackages = "com.haojunlcode" )
//@EnableAutoConfiguration
//@Configuration
public class Main {
    public static void main(String[] args){
        ConfigurableApplicationContext applicationContext =
                SpringApplication.run(Main.class, args);

        //printBeans(applicationContext);
    }

    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository){
        return args -> {
            Customer alex = new Customer(
                    "Alex",
                    "alex@gmail.com",
                    21

            );
            Customer jamila = new Customer(
                    "jamila",
                    "jamila@gmail.com",
                    19

            );
            List<Customer> customers = List.of(alex, jamila);
            customerRepository.saveAll(customers);
        };
    }


    //
    public static void printBeans(ConfigurableApplicationContext ctx) {
       String[] beanDefinitionNames = ctx.getBeanDefinitionNames();
        for(String beanDefinitionName : beanDefinitionNames){
            System.out.println(beanDefinitionName);
        }
    }


    @Bean("Foo")  //create own bean"Foo" in Application context
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)//change scope of bean
    @RequestScope()
    public Foo getFoo(){
        return new Foo("Bar");
    }
    record Foo(String name){}


}
