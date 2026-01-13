package com.example.LMS_sb.repository;

import com.example.LMS_sb.models.User;
import com.example.LMS_sb.models.UserSecurity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserSecurityRepository extends JpaRepository<UserSecurity,Long> {
    Optional<UserSecurity> findUserSecurityByUser(User user);
}
