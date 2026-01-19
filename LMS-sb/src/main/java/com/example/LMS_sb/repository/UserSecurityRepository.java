package com.example.LMS_sb.repository;

import com.example.LMS_sb.models.User;
import com.example.LMS_sb.models.UserSecurity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSecurityRepository extends JpaRepository<UserSecurity,Long> {
    Optional<UserSecurity> findUserSecurityByUser(User user);
    void deleteByUserId(Long id);
}
