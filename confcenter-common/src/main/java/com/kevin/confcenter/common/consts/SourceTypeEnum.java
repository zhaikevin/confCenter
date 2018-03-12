package com.kevin.confcenter.common.consts;

/**
 * @Author: kevin
 * @Description: 数据源类型
 * @Date: Created In 2018/3/10 14:51
 */
public enum SourceTypeEnum {
    /**
     * 枚举列表
     */
    NORMAL(0, "普通数据源"), PUBLIC(1, "公共数据源");

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
    private SourceTypeEnum(int val, String label) {
        this.val = val;
        this.label = label;
    }

    /**
     * 按照枚举数值找到对应枚举
     *
     * @param val 枚举数值
     * @return 对应的枚举, 可能为null.
     */
    public static SourceTypeEnum valueOf(int val) {
        SourceTypeEnum[] ems = SourceTypeEnum.values();
        for (SourceTypeEnum em : ems) {
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
