package com.kevin.confcenter.common.bean.vo.web.operation;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: jizhong.zhai
 * @Description: 父亲结点
 * @Date: Created In 2018/7/3 14:22
 */
public class MenuParentNode implements Serializable{

    private static final long serialVersionUID = -5610327210818075254L;

    /**
     * id
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 孩子结点列表
     */
    private List<MenuChildNode> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<MenuChildNode> getChildren() {
        return children;
    }

    public void setChildren(List<MenuChildNode> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "MenuParentNode{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", children=" + children +
                '}';
    }
}
