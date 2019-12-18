package com.unict.riganozito.videomanagementservice;

import com.unict.riganozito.videomanagementservice.entity.*;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserVMSDetailsService implements UserDetailsService {

    @Autowired
    UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOpt = repository.findByUsername(username);
        if (userOpt == null || !userOpt.isPresent())
            return null;
        User user = userOpt.get();

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                getEncoder().encode(user.getPassword()), null);
    }

    @Bean
    public BCryptPasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }
}
