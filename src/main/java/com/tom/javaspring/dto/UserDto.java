package com.tom.javaspring.dto;


import com.tom.javaspring.validation.FieldMatch;
import com.tom.javaspring.validation.ValidEmail;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldMatch.List({@FieldMatch(first = "password", second = "confirmPassword", message = "the password field must match")})
public class UserDto {
    private int id;

    @NotEmpty(message = "Username is required")
    private String userName;

    @NotEmpty(message = "Password is required")
    private String password;

    @NotEmpty(message = "Confirm password is required")
    private String confirmPassword;

    @ValidEmail
    private String email;

    private boolean enabled = true;

    private List<String> roles;

    private MultipartFile image;

    private String imageUrl = "default.jpg";
}
