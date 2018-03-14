package com.kevin.confcenter.common.consts;

/**
 * @Author: kevin
 * @Description: 数据源改变类型
 * @Date: Created In 2018/3/10 14:51
 */
public enum DataChangeTypeEnum {
    /**
     * 枚举列表
     */
    ADD(0, "增加"), REDUCE(1, "减少"), UPDATE(2, "更新");

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
    DataChangeTypeEnum(int val, String label) {
        this.val = val;
        this.label = label;
    }

    /**
     * 按照枚举数值找到对应枚举
     *
     * @param val 枚举数值
     * @return 对应的枚举, 可能为null.
     */
    public static DataChangeTypeEnum valueOf(int val) {
        DataChangeTypeEnum[] ems = DataChangeTypeEnum.values();
        for (DataChangeTypeEnum em : ems) {
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
