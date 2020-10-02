package com.example.web.controller;

import com.example.web.dao.UserDao;
import com.example.web.util.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.web.bean.User;

import javax.security.auth.login.Configuration;


//用户Controller类
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserDao userDao;
    @Autowired
    private ConfigProperties configProperties;
    @RequestMapping("/getUser")
    @Cacheable(value = "user-key")
    public User getUser() {
        User user = userDao.findByUserName("zhangsan");
        System.out.println("若下面没出现“无缓存的时候调用”字样且能打印出数据表示测试成功");
        return user;
    }
    /**
    * 返回信息配置类*/
    @RequestMapping("/config")
    public String getConfig(){
        StringBuffer result=new StringBuffer();
        String title = configProperties.getTitle();
        String description = configProperties.getDescription();
        result.append(title).append(",").append(description);
        return result.toString();
    }
}
