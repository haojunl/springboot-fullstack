package com.haojunlcode;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
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
import java.util.Random;


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
            var faker = new Faker();
            Name name = faker.name();
            String firstName = name.firstName();
            String lastName = name.lastName();
            Random random = new Random();
            Customer customer = new Customer(
                    lastName + firstName,
                    firstName.toLowerCase() + "." + lastName +"@gmail.com",
                    random.nextInt(16,99)

            );
            customerRepository.save(customer);
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
