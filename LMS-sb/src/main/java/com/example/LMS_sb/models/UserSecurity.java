package com.example.LMS_sb.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserSecurity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            unique = true
    )
    private User user;


    @Column(name = "password_hash", nullable = false)
    private String passwordHash;


    @Column(name = "is_first_login", nullable = false)
    private boolean firstLogin = true;


    @Column(name = "password_reset_required", nullable = false)
    private boolean passwordResetRequired = true;


    @Column(name = "last_password_change")
    private LocalDateTime lastPasswordChange;


    @Column(name = "failed_login_attempts", nullable = false)
    private int failedLoginAttempts = 0;


    @Column(name = "account_locked", nullable = false)
    private boolean accountLocked = false;


    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;


    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}