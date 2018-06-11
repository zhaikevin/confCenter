package com.kevin.confcenter.admin.service.operation.impl;

import com.kevin.confcenter.admin.dao.operation.OperationLogDao;
import com.kevin.confcenter.admin.service.operation.OperationLogService;
import com.kevin.confcenter.common.bean.po.operation.OperationLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: jizhong.zhai
 * @Description:
 * @Date: Created In 2018/6/11 18:17
 */
@Service(value = "operationLogService")
public class OperationLogServiceImpl implements OperationLogService {

    @Autowired
    private OperationLogDao operationLogDao;

    @Override
    public void insert(OperationLog log) {
        operationLogDao.insert(log);
    }
}
