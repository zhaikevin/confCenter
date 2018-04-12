package com.kevin.confcenter.common.bean.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 分页查询结果
 * @param <T> 数据类型
 */
public class PaginationResult<T> implements Serializable {

    private static final long serialVersionUID = 1547312481740277599L;
    /**
     * 返回数据总数
     */
    private Long total;

    /**
     * 返回数据
     */
    private List<T> rows;

    /**
     * 创建分页返回数据
     * @param total 总数
     * @param rows 数据
     */
    public PaginationResult(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}

