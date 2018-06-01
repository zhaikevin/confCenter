package com.kevin.confcenter.admin.extend;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Author: kevin
 * @Description: 一些配置，登录拦截，跨域等
 * @Date: Created In 2018/4/17 15:47
 */
@Configuration
public class LoginInterceptorConfig extends WebMvcConfigurerAdapter {

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
//        super.addResourceHandlers(registry);
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new StaticLoginInterceptor()).addPathPatterns("/**/*.html").addPathPatterns("/index")
//                .addPathPatterns("/user/logout");
//        registry.addInterceptor(new ActionLoginInterceptor()).addPathPatterns("/**")
//                .excludePathPatterns("/user/login").excludePathPatterns("/user/logout").excludePathPatterns("/**/*.html")
//                .excludePathPatterns("/**/*.js").excludePathPatterns("/**/*.css").excludePathPatterns("/index");
//        super.addInterceptors(registry);
//    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .maxAge(3600);
    }
}
