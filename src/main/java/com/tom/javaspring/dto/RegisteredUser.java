package com.tom.javaspring.dto;


import com.tom.javaspring.validation.FieldMatch;
import com.tom.javaspring.validation.ValidEmail;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@FieldMatch.List({@FieldMatch(first = "password", second = "confirmPassword", message = "the password field must match")})
public class RegisteredUser {
    @NotEmpty(message = "Username is required")
    @Size(min = 2, max = 50, message = "Username must be between 2 and 50 characters")
    private String userName;
    @NotEmpty(message = "Password is required")
    @Size(min = 2, max = 68, message = "Password must be between 2 and 68 characters")
    private String password;
    private String confirmPassword;
    @ValidEmail
    private String email;
}
