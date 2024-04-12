package com.tom.javaspring.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="username", length = 50)
    @NotEmpty(message = "Username is required")
    @Size(min = 2, max = 50, message = "Username must be between 2 and 50 characters")
    private String userName;

    @Column(name="password", length = 68)
    @NotEmpty(message = "Password is required")
    @Size(min = 2, max = 68, message = "Password must be between 2 and 68 characters")
    private String password;

    @Column(name="email", length = 250)
    @Email(message = "Invalid email format")
    private String email;

    @Column(name="enabled")
    @NotEmpty(message = "Enabled is required")
    private boolean enabled;
}
