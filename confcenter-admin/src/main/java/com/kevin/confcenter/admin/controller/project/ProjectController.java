package com.kevin.confcenter.admin.controller.project;

import com.kevin.confcenter.admin.service.project.ProjectService;
import com.kevin.confcenter.common.bean.vo.QueryParams;
import com.kevin.confcenter.common.bean.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: kevin
 * @Description: 项目管理controller
 * @Date: Created In 2018/3/9 19:00
 */
@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    /**
     * 获取列表
     *
     * @param queryParams
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo list(QueryParams queryParams) {
        return ResultInfo.success(projectService.getPaginationList(queryParams));
    }

}
