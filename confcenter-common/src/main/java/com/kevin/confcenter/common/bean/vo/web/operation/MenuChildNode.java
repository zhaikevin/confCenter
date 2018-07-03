package com.kevin.confcenter.common.bean.vo.web.operation;

import java.io.Serializable;

/**
 * @Author: jizhong.zhai
 * @Description: 孩子结点
 * @Date: Created In 2018/7/3 14:20
 */
public class MenuChildNode implements Serializable {

    private static final long serialVersionUID = -5207080061825879173L;

    /**
     * id
     */
    private Long id;

    /**
     * 父节点id
     */
    private Long parentId;

    /**
     * 标题
     */
    private String title;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "MenuChildNode{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", title='" + title + '\'' +
                '}';
    }
}
