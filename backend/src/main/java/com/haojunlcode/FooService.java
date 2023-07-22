package com.haojunlcode;

import org.springframework.stereotype.Service;

@Service//find Foo in Application context, then instantiated FooService
public class FooService {
    private final Main.Foo foo;//Dependency Injection

    public FooService(Main.Foo foo) {
        this.foo = foo;
        System.out.println();
    }


    String getFooName(){
        return foo.name();
    }
}
