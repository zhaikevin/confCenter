package com.kevin.confcenter.admin.service.operation;

import com.kevin.confcenter.common.bean.po.operation.OperationLog;

/**
 * @Author: jizhong.zhai
 * @Description: 操作记录service
 * @Date: Created In 2018/6/11 18:16
 */
public interface OperationLogService {

    void insert(OperationLog log);
}
