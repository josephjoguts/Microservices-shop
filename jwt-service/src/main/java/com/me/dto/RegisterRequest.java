package com.me.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RegisterRequest {
    private String firstname;
    private String lastname;
    private String name;
    private String password;
}
