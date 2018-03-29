package com.kevin.confcenter.common.bean.vo;

import com.kevin.confcenter.common.consts.SourceTypeEnum;

import java.io.Serializable;

/**
 * @Author: kevin
 * @Description: 数据源类
 * @Date: Created In 2018/3/10 14:56
 */
public class ClientDataSource implements Serializable {


    private static final long serialVersionUID = 1006411768675349316L;

    /**
     * 数据源类型 {@link com.kevin.confcenter.common.consts.SourceTypeEnum}
     */
    private SourceTypeEnum sourceType;

    /**
     * 数据源名称
     */
    private String sourceKey;

    /**
     * 数据源的值
     */
    private String sourceValue;

    public SourceTypeEnum getSourceType() {
        return sourceType;
    }

    public void setSourceType(SourceTypeEnum sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceKey() {
        return sourceKey;
    }

    public void setSourceKey(String sourceKey) {
        this.sourceKey = sourceKey;
    }

    public String getSourceValue() {
        return sourceValue;
    }

    public void setSourceValue(String sourceValue) {
        this.sourceValue = sourceValue;
    }

    @Override
    public String toString() {
        return "ClientDataSource{" +
                "sourceType=" + sourceType +
                ", sourceKey='" + sourceKey + '\'' +
                ", sourceValue='" + sourceValue + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (object instanceof ClientDataSource) {
            ClientDataSource dataSource = (ClientDataSource) object;
            if (sourceType.equals(dataSource.getSourceType()) && sourceKey.equals(dataSource.getSourceKey())
                    && sourceValue.equals(dataSource.getSourceValue())) {
                return true;
            }
        }
        return false;
    }
}
