package com.kevin.confcenter.common.consts.web;

/**
 * @Author: kevin
 * @Description: 通用状态枚举
 * @Date: Created In 2018/4/12 18:10
 */
public enum CommonStatusEnum {

    /**
     * 枚举列表
     */
    ENABLED(1, "有效"), DISABLED(0, "无效");

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
    CommonStatusEnum(int val, String label) {
        this.val = val;
        this.label = label;
    }

    /**
     * 按照枚举数值找到对应枚举
     *
     * @param val 枚举数值
     * @return 对应的枚举, 可能为null.
     */
    public static CommonStatusEnum valueOf(int val) {
        CommonStatusEnum[] ems = CommonStatusEnum.values();
        for (CommonStatusEnum em : ems) {
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

    public static String getLabel(int val) {
        CommonStatusEnum typeEnum = valueOf(val);
        if (typeEnum == null) {
            return null;
        }
        return typeEnum.getLabel();
    }
}
