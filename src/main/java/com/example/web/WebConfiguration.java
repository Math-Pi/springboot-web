package com.example.web;

import com.example.web.filter.MyFilter;
import org.apache.catalina.filters.RemoteIpFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//过滤器,添加 @Configuration 注解，将自定义Filter加入过滤链。
@Configuration
public class WebConfiguration {

    @Bean
    public RemoteIpFilter remoteIpFilter() {
        return new RemoteIpFilter();
    }

    @Bean
    public FilterRegistrationBean testFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new MyFilter()); //添加过滤器
        registration.addUrlPatterns("/*");      //设置过滤路径
        registration.addInitParameter("name", "Filter");//添加默认参数
        registration.setName("MyFilter");
        registration.setOrder(1);   //执行的顺序
        return registration;
    }
}
