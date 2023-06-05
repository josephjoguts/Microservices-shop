package com.me.productservice.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class Securities {
    public static User currentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
