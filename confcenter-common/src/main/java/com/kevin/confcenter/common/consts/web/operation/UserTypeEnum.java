package com.kevin.confcenter.common.consts.web.operation;

/**
 * @Author: kevin
 * @Description: 用户类型枚举
 * @Date: Created In 2018/4/12 18:10
 */
public enum UserTypeEnum {

    /**
     * 枚举列表
     */
    ADMIN(1, "管理员账户"), NORMAL(2, "普通账户");

    /**
     * 对应数值
     */
    private final int val;
    /**
     * 显示值
     */
    private final String label;

    /**
     * 构造函数
     *
     * @param val   枚举数值
     * @param label 显示值
     */
    UserTypeEnum(int val, String label) {
        this.val = val;
        this.label = label;
    }

    /**
     * 按照枚举数值找到对应枚举
     *
     * @param val 枚举数值
     * @return 对应的枚举, 可能为null.
     */
    public static UserTypeEnum valueOf(int val) {
        UserTypeEnum[] ems = UserTypeEnum.values();
        for (UserTypeEnum em : ems) {
            if (em.val == val) {
                return em;
            }
        }
        return null;
    }

    /**
     * @return the val
     */
    public int getVal() {
        return val;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }
}
