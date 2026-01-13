package com.example.LMS_sb.security;

import com.example.LMS_sb.models.User;
import com.example.LMS_sb.models.UserSecurity;
import com.example.LMS_sb.repository.UserRepository;
import com.example.LMS_sb.repository.UserSecurityRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserSecurityRepository userSecurityRepository;

    public UserDetailsServiceImpl(UserRepository userRepository,
                                  UserSecurityRepository userSecurityRepository) {
        this.userRepository = userRepository;
        this.userSecurityRepository = userSecurityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(()->
                    new UsernameNotFoundException("User not found with email: "+ username)
                );
        UserSecurity security = userSecurityRepository.findUserSecurityByUser(user).orElseThrow(()->
                new UsernameNotFoundException("Security data not found")
        );
        return UserDetailsImpl.build(user,security);
    }
}
