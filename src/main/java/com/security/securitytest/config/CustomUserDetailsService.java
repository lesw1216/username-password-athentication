package com.security.securitytest.config;

import com.security.securitytest.domain.UserEntity;
import com.security.securitytest.repository.MysqlJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MysqlJdbcRepository repository;

    @Autowired
    public CustomUserDetailsService(MysqlJdbcRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity findUser = repository.findByUsername(username);
        if (findUser == null) {
            throw new UsernameNotFoundException("user not found username = " + username);
        }

        UserDetails user = User.builder()
                .username(findUser.getUsername())
                .password(findUser.getPassword())
                .authorities("USER").build();

        return user;
    }
}
