package com.kevin.confcenter.admin.bean;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: jizhong.zhai
 * @Description: 属性测试
 * @Date: Created In 2018/4/10 16:25
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PropertiesTest {

    @Autowired
    private Properties properties;

    @Test
    public void loadPropertiesTest() {
        System.out.println(properties.toString());
    }

}
