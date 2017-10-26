package cn.leadeon.common.annotion;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface IsDouble {
	boolean isNotNull() default true;
	boolean isLegal() default true;
	long maxLength() default 999999999;
}