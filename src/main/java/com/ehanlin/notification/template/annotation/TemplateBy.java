package com.ehanlin.notification.template.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface TemplateBy {

    String value() default "";

}
