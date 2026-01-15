package com.example.LMS_sb.models;


import com.example.LMS_sb.models.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    @Email
    @Column(name = "email",nullable = false,unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role" ,nullable = false)
    private Role role;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToOne(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Student student;

    @OneToOne(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Teacher teacher;

    @OneToOne(
            mappedBy = "user",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true
    )
    private UserSecurity userSecurity;


    @PrePersist
    public void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

}