package com.kevin.confcenter.example;

import com.alibaba.fastjson.JSONObject;
import com.kevin.confcenter.client.api.ConfCenterApi;
import com.kevin.confcenter.client.api.impl.DefaultConfCenterApi;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: kevin
 * @Description: demo Controller
 * @Date: Created In 2018/3/24 17:17
 */
@Controller
public class DemoController {

    @RequestMapping(value = "/demo", method = RequestMethod.GET)
    @ResponseBody
    public String demo() {
        ConfCenterApi confCenterApi = DefaultConfCenterApi.getInstance();
        return JSONObject.toJSONString(confCenterApi.getDataSource("testKey"));
    }

}
