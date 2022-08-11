package com.security.securitytest.service;

import com.security.securitytest.dto.UserDTO;
import com.security.securitytest.repository.MysqlJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final MysqlJdbcRepository repository;

    @Autowired
    public UserService(MysqlJdbcRepository repository) {
        this.repository = repository;
    }

    public Boolean save(UserDTO userDTO) {
        return repository.save(userDTO);
    }
}
