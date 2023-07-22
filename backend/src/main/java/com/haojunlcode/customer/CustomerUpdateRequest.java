package com.haojunlcode.customer;

public record CustomerUpdateRequest(
        String name,
        String email,
        Integer age
) {
}
