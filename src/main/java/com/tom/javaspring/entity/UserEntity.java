package com.tom.javaspring.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name="users")
public class UserEntity {
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
    private String enabled;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<Role> roles;
}
