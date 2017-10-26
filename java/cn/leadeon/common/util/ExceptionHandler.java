/*
 * 文 件 名:  ExceptionHandler.java
 * 版    权:  Xi'An Leadeon Technologies Co., Ltd. Copyright 2014-10-16,  All rights reserved  
 */
package cn.leadeon.common.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * 异常处理类
 * 
 * @author linxuchao
 * 
 * @version [1.0, 2017-10-19]
 * 
 * @since [中国移动手机营业厅运营支撑平台/异常处理]
 */
public class ExceptionHandler extends SimpleMappingExceptionResolver{

	private static final Logger log = Logger.getLogger(ExceptionHandler.class);

	/**
	 * 重载方法
	 * 
	 * @return
	 */
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object obj, Exception ex) {
		
		//日志记录
		log.error(ex.getMessage(), ex);
		//逻辑处理
		String viewName = determineViewName(ex, request);
		if (viewName != null) {
			// JSP格式返回
			if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request
					.getHeader("X-Requested-With")!= null && request
					.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
				// 如果不是异步请求
				Integer statusCode = determineStatusCode(request, viewName);
				if (statusCode != null) {
					applyStatusCodeIfPossible(request, response, statusCode);
				}
				return getModelAndView(viewName, ex, request);
			} else {// JSON格式返回
				try {
					response.setCharacterEncoding("UTF-8");
					PrintWriter writer = response.getWriter();
					writer.write(ex.getMessage());
					writer.flush();
				} catch (IOException e) {
					log.error(e.getMessage(),e);
				}
				return null;

			}
		} else {
			return null;
		}
	}
}
