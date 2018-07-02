package com.kevin.confcenter.admin.service.project.impl;

import com.kevin.confcenter.admin.dao.project.ProjectDao;
import com.kevin.confcenter.admin.service.project.ProjectService;
import com.kevin.confcenter.common.bean.po.project.Project;
import com.kevin.confcenter.common.bean.vo.PaginationResult;
import com.kevin.confcenter.common.bean.vo.QueryParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: kevin
 * @Description: 项目管理service实现
 * @Date: Created In 2018/5/8 9:29
 */
@Service(value = "projectService")
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectDao projectDao;

    @Override
    public PaginationResult<Project> getPaginationList(QueryParams queryParams) {
        Map<String, Object> params = queryParams.getParams();
        List<Project> list = projectDao.getProjectList(params);
        Long count = projectDao.getProjectCount(params);
        return new PaginationResult<>(count, list);
    }

    @Override
    public void create(Project project) {
        projectDao.insert(project);
    }

    @Override
    public void modify(Project project) {
        projectDao.update(project);
    }
}
