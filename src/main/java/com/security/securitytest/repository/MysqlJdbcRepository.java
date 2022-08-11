package com.security.securitytest.repository;

import com.security.securitytest.domain.UserEntity;
import com.security.securitytest.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.NoSuchElementException;

@Slf4j
@Repository
public class MysqlJdbcRepository {

    private final DataSource dataSource;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MysqlJdbcRepository(DataSource dataSource, PasswordEncoder passwordEncoder) {
        this.dataSource = dataSource;
        this.passwordEncoder = passwordEncoder;
    }



    private void close(Connection con, Statement stmt, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        JdbcUtils.closeConnection(con);
    }

    private Connection getConnection() throws SQLException {
        Connection con = dataSource.getConnection();
        log.info("get Connection = {}, class ={}", con, con.getClass());
        return con;
    }

    public Boolean save(UserDTO userDTO) {
        String sql = "insert into users(username, password) values(?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null;

        // userDTO -> userEntity
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setPassword(userDTO.getPassword());

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userEntity.getUsername());
            pstmt.setString(2, passwordEncoder.encode(userEntity.getPassword()));
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(con, pstmt, null);
        }
    }

    public UserEntity findByUsername(String username) {
        String sql = "select * from users where username = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, username);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                UserEntity userEntity = new UserEntity();
                userEntity.setUsername(rs.getString("username"));
                userEntity.setPassword(rs.getString("password"));
                return userEntity;
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(con, pstmt, null);
        }
    }
}
