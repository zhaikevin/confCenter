package com.kevin.confcenter.common.bean.vo;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: kevin
 * @Description: 分页查询参数
 * @Date: Created In 2018/4/18 15:59
 */
public class QueryParams implements Serializable {

    private static final long serialVersionUID = -4014806283457580966L;

    /**
     * 当前页码
     */
    private int page;

    /**
     * 每页显示行数
     */
    private int rows;

    /**
     * 排序字段
     */
    private String sort;

    /**
     * 排序顺序
     */
    private String order;

    /**
     * 查询参数
     */
    private Map<String, Object> params = new HashMap<>();

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    /**
     * 获取sql查询参数
     *
     * @return 查询参数
     */
    public Map<String, Object> getParams() {
        params.put("start", this.getStartIndex());
        params.put("end", this.getEndIndex());
        params.put("size", this.getRows());
        if (StringUtils.isNotEmpty(this.getSort())) {
            params.put("sortName", this.getSort());
            if (!StringUtils.isNotEmpty(this.getOrder())) {
                params.put("sortOrder", this.getOrder());
            } else {
                params.put("sortOrder", "asc");
            }
        }
        return params;
    }

    /**
     * 增加一个条件
     *
     * @param key   键
     * @param value 值
     */
    public void append(String key, Object value) {
        params.put(key, value);
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    /**
     * 获取数据开始位置
     *
     * @return 开始位置
     */
    public int getStartIndex() {
        if (page < 1) {
            page = 1;
        }
        return (page - 1) * rows;
    }

    /**
     * 获取数据结束位置
     *
     * @return 结束位置
     */
    public int getEndIndex() {
        return page * rows;
    }
}
