package com.kevin.confcenter.admin.controller.operation.user;

import com.kevin.confcenter.common.bean.vo.ResultInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: kevin
 * @Description: 用户管理controller
 * @Date: Created In 2018/4/12 16:31
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo list() {
        return null;
    }
}
