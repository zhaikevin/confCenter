package com.kevin.confcenter.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * @Author: kevin
 * @Description: springboot启动类
 * @Date: Created In 2018/3/24 17:15
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class SpringbootAdminApplication extends SpringBootServletInitializer {

    public static void main(String args[]) {
        SpringApplication.run(SpringbootAdminApplication.class, args);
    }

    /**
     * 使用外部tomcat容器时，需继承SpringBootServletInitializer，重写configure方法
     * @param builder SpringApplicationBuilder
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        builder.sources(this.getClass());
        return super.configure(builder);
    }
}
