package com.haojunlcode.customer;

public record CustomerRegistrationRequest(
        String name,
        String email,
        Integer age
) {
}
