package com.example.web.dao;

import com.example.web.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;

//DAO 数据处理类
public interface UserDao extends JpaRepository<User, Long> {
    User findByUserName(String userName);
}
