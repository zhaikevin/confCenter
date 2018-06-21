package com.kevin.confcenter.admin.extend;

import com.kevin.confcenter.common.bean.vo.ResultInfo;
import com.kevin.confcenter.common.exception.ConfCenterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: kevin
 * @Description: 异常统一处理
 * @Date: Created In 2018/4/16 10:25
 */
@ControllerAdvice
public class ExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandler.class);

    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultInfo handler(Exception e) {
        if (e instanceof ConfCenterException) {
            return ResultInfo.errorMessage(e.getMessage());
        } else {
            LOGGER.error("exception:{}", e.getMessage(), e);
            return ResultInfo.errorMessage("服务器内部错误");
        }
    }
}
