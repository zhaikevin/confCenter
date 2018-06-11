package com.kevin.confcenter.common.consts.web.operation;

/**
 * @Author: kevin
 * @Description: 操作结果枚举
 * @Date: Created In 2018/4/12 18:10
 */
public enum OperationResultEnum {

    /**
     * 枚举列表
     */
    SUCCESS((byte) 1, "成功"), FAIL((byte) 0, "失败");

    /**
     * 对应数值
     */
    private final Byte val;
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
    OperationResultEnum(Byte val, String label) {
        this.val = val;
        this.label = label;
    }

    /**
     * 按照枚举数值找到对应枚举
     *
     * @param val 枚举数值
     * @return 对应的枚举, 可能为null.
     */
    public static OperationResultEnum valueOf(Byte val) {
        OperationResultEnum[] ems = OperationResultEnum.values();
        for (OperationResultEnum em : ems) {
            if (em.val.equals(val)) {
                return em;
            }
        }
        return null;
    }

    /**
     * @return the val
     */
    public Byte getVal() {
        return val;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    public static String getLabel(Byte val) {
        OperationResultEnum typeEnum = valueOf(val);
        if (typeEnum == null) {
            return null;
        }
        return typeEnum.getLabel();
    }
}
