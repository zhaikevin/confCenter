package com.kevin.confcenter.admin.dao.project;

import com.kevin.confcenter.common.bean.po.project.Project;

import java.util.List;
import java.util.Map;

/**
 * @Author: kevin
 * @Description: 项目dao
 * @Date: Created In 2018/5/7 14:22
 */
public interface ProjectDao {

    /**
     * 获取分页列表
     *
     * @param params
     * @return
     */
    List<Project> getProjectList(Map<String, Object> params);

    /**
     * 获取数量
     *
     * @param params
     * @return
     */
    Long getProjectCount(Map<String, Object> params);

    /**
     * 插入
     *
     * @param project
     */
    void insert(Project project);

    /**
     * 修改
     *
     * @param project
     */
    void update(Project project);
}
