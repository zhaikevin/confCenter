package com.kevin.confcenter.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @Author: kevin
 * @Description: springboot启动类
 * @Date: Created In 2018/3/24 17:15
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class SpringbootDemoApplication {

    public static void main(String args[]) {
        SpringApplication.run(SpringbootDemoApplication.class, args);
    }
}
