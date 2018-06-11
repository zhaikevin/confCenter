package com.kevin.confcenter.common.consts.web.operation;

/**
 * @Author: kevin
 * @Description: 操作对象类型枚举
 * @Date: Created In 2018/4/12 18:10
 */
public enum TargetTypeEnum {

    /**
     * 枚举列表
     */
    USER(1, "用户");

    /**
     * 对应数值
     */
    private final Integer val;
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
    TargetTypeEnum(Integer val, String label) {
        this.val = val;
        this.label = label;
    }

    /**
     * 按照枚举数值找到对应枚举
     *
     * @param val 枚举数值
     * @return 对应的枚举, 可能为null.
     */
    public static TargetTypeEnum valueOf(Integer val) {
        TargetTypeEnum[] ems = TargetTypeEnum.values();
        for (TargetTypeEnum em : ems) {
            if (em.val.equals(val)) {
                return em;
            }
        }
        return null;
    }

    /**
     * @return the val
     */
    public Integer getVal() {
        return val;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    public static String getLabel(Integer val) {
        TargetTypeEnum typeEnum = valueOf(val);
        if (typeEnum == null) {
            return null;
        }
        return typeEnum.getLabel();
    }
}
