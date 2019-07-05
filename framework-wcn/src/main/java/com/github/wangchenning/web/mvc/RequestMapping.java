package com.github.wangchenning.web.mvc;

import java.lang.annotation.*;

/**
 *
 * @date 2019/5/2
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequestMapping {
    String value();
}
