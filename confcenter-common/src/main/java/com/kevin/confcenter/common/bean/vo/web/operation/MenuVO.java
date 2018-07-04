package com.kevin.confcenter.common.bean.vo.web.operation;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: jizhong.zhai
 * @Description:
 * @Date: Created In 2018/7/4 15:42
 */
public class MenuVO implements Serializable {

    /**
     * 序列化
     */
    private static final long serialVersionUID = -1478609789171384992L;

    /**
     * 主键id,对应数据表中的字段:conf_center_menu.id
     */
    private Long id;

    /**
     * 父节点id,对应数据表中的字段:conf_center_menu.parent_id
     */
    private Long parentId;

    /**
     * 路径,对应数据表中的字段:conf_center_menu.path
     */
    private String path;

    /**
     * 图标,对应数据表中的字段:conf_center_menu.icon
     */
    private String icon;

    /**
     * 名称,对应数据表中的字段:conf_center_menu.name
     */
    private String name;

    /**
     * 标题,对应数据表中的字段:conf_center_menu.title
     */
    private String title;

    /**
     * 组件,对应数据表中的字段:conf_center_menu.component
     */
    private String component;

    /**
     * 0：控制权限，1：不控制权限,对应数据表中的字段:conf_center_menu.access
     */
    private Byte access;

    /**
     * 子节点
     */
    List<MenuVO> children;

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public Byte getAccess() {
        return access;
    }

    public void setAccess(Byte access) {
        this.access = access;
    }

    public List<MenuVO> getChildren() {
        return children;
    }

    public void setChildren(List<MenuVO> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "MenuVO{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", path='" + path + '\'' +
                ", icon='" + icon + '\'' +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", component='" + component + '\'' +
                ", access=" + access +
                ", children=" + children +
                '}';
    }
}
