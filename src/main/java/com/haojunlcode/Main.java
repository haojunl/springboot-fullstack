package com.haojunlcode;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.annotation.RequestScope;


@SpringBootApplication
//@ComponentScan(basePackages = "com.haojunlcode" )
//@EnableAutoConfiguration
//@Configuration
public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext =
            SpringApplication.run(Main.class, args);

        //String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        //for(String beanDefinitionName : beanDefinitionNames){
        //    System.out.println(beanDefinitionName);
        //}
    }
    /*
    @Bean("Foo")
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)//change scope of bean
    @RequestScope()
    public Foo getFoo(){
        return new Foo("Bar");
    }
    record Foo(String name){}
    */
}
