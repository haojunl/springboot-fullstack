package com.haojunlcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
//@ComponentScan(basePackages = "com.haojunlcode" )
//@EnableAutoConfiguration
//@Configuration
@RestController
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping("/greet")//control by @RestController
    public GreetResponse greet(@RequestParam(value = "name", required = false) String name){
        String greetMessage = name==null || name.isBlank() ? "Hello" : "Hello" + name;//obj.isBlank()需要obj不为null
        GreetResponse response = new GreetResponse(
                "Hello",
                List.of("Java","Golan","JS"),
                new Person("Alex", 28, 30_000)
        );
        return response;
    }
    record Person (String name, int age, double savings){

    }
    record GreetResponse(
            String greet,//key or instance : value
            List<String> favProgrammingLanguages,
            Person person
    ){}
    /*
    class GreetResponse{
        private final String greet;//key/instance
        public GreetResponse(String greet) {
            this.greet = greet;
        }

        public String getGreet() {
            return greet;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GreetResponse that)) return false;

            return getGreet() != null ? getGreet().equals(that.getGreet()) : that.getGreet() == null;
        }
        @Override
        public int hashCode() {
            return getGreet() != null ? getGreet().hashCode() : 0;
        }





    }
    */
}
