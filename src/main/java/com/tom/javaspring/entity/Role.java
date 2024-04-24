package com.tom.javaspring.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name="roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name", length = 50)
    @NotEmpty(message = "Name is required")
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<UserEntity> userEntities;
}
