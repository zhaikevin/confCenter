package com.kevin.confcenter.admin.controller.project;

import com.kevin.confcenter.admin.service.Project.ProjectService;
import com.kevin.confcenter.common.bean.vo.QueryParams;
import com.kevin.confcenter.common.bean.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: kevin
 * @Description: 项目管理controller
 * @Date: Created In 2018/3/9 19:00
 */
@Controller
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    /**
     * 跳转到列表
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list() {
        return "project/list";
    }

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
