package com.kevin.confcenter.admin.log;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * handler
     *
     * @return
     */
    String handler();

}
