package com.star;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan(basePackages = "com.star")
public class SpringSecurityAuthorizationApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityAuthorizationApplication.class, args);
    }

}
