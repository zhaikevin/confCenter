package com.kevin.confcenter.admin.log;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * operation type
     *
     * @return
     */
    public OperationTypeEnum type();

    /**
     * handler
     *
     * @return
     */
    public String handler();

}
