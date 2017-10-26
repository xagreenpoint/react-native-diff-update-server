package cn.leadeon.common.annotion;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**   
* @Title: StrVerify.java 
*
* @Package cn.leadeon.action.annotion 
*
* @author gavin   
*
* @date 2015-1-7 下午2:02:59 
*
* @Description: 字符验证
*
* @version 1.0-SNAPSHOT   
*/ 
@Retention(RetentionPolicy.RUNTIME)
public @interface StrVerify {
	
	
	boolean isNotNull() default true;
	
	long maxLength() default 999999999;
	
	long minLength() default 0;
	
	boolean isNotIllegal() default true;
	
	String [] ignoreIllegal() default {};
	
	boolean isNumber() default false;
	
	boolean isPage() default false;
	
	boolean isBigStr() default false;

}
