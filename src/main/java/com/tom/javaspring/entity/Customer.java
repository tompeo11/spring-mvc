package com.tom.javaspring.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name="customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="first_name", length = 50)
    @NotEmpty(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @Column(name="last_name", length = 68)
    @NotEmpty(message = "Last name is required")
    @Size(min = 2, max = 68, message = "Last name must be between 2 and 68 characters")
    private String lastName;

    @Column(name="email", length = 250)
    private String email;
}
