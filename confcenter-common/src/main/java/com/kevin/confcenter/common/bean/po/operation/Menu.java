package com.kevin.confcenter.common.bean.po.operation;

import java.io.Serializable;

/**
 * 菜单
 */
public class Menu implements Serializable {

    /**
     * 序列化
     */
    private static final long serialVersionUID = 2769147928971261634L;

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
     * 状态
     */
    private Byte status;

    /**
     * 备注,对应数据表中的字段:conf_center_menu.remark
     */
    private String remark;

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
        this.path = path == null ? null : path.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component == null ? null : component.trim();
    }

    public Byte getAccess() {
        return access;
    }

    public void setAccess(Byte access) {
        this.access = access;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", path='" + path + '\'' +
                ", icon='" + icon + '\'' +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", component='" + component + '\'' +
                ", access=" + access +
                ", status=" + status +
                ", remark='" + remark + '\'' +
                '}';
    }
}