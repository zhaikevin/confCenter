package com.kevin.confcenter.admin.service.Project;

import com.kevin.confcenter.common.bean.po.project.Project;
import com.kevin.confcenter.common.bean.vo.PaginationResult;
import com.kevin.confcenter.common.bean.vo.QueryParams;

/**
 * @Author: kevin
 * @Description: 项目service
 * @Date: Created In 2018/5/8 9:25
 */
public interface ProjectService {

    /**
     * 获取分页列表
     *
     * @param queryParams
     * @return
     */
    PaginationResult<Project> getPaginationList(QueryParams queryParams);

}
