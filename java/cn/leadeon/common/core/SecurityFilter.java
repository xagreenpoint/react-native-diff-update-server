package cn.leadeon.common.core;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.leadeon.common.util.SysConfiguration;
import cn.leadeon.common.util.SysUtil;
import ecuop.api.Auth2;
import ecuop.api.Config;
import ecuop.api.ReturnCode;

/**
 * 过滤器，控制session过期
 */
public class SecurityFilter implements Filter {
	

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = ((HttpServletRequest) request);
		HttpServletResponse httpResponse = ((HttpServletResponse) response);
		String oldUrl = httpRequest.getRequestURL()+"?";
		String ip=SysUtil.getHostIp(httpRequest);
		if(null!=httpRequest.getQueryString()){
			oldUrl+=httpRequest.getQueryString();
		}
		String url = httpRequest.getRequestURI();
		int index = url.lastIndexOf("/");
		url = url.substring(index + 1);
		index = url.lastIndexOf(".");
		String filefix = url.substring(index + 1);
		if(filefix.equalsIgnoreCase("js") 
			|| filefix.equalsIgnoreCase("jpg")
			|| filefix.equalsIgnoreCase("css")
			|| filefix.equalsIgnoreCase("png")
			|| filefix.equalsIgnoreCase("gif")
			|| filefix.equalsIgnoreCase("json")
			|| filefix.equalsIgnoreCase("ico")
			|| filefix.equalsIgnoreCase("htm")
			|| filefix.equalsIgnoreCase("html")) {
			chain.doFilter(request, response);
			return;
		} else {
			if(!url.equalsIgnoreCase("login.jsp")
				&&!url.equalsIgnoreCase("login1.jsp")
				&& !url.equalsIgnoreCase("sessionTimeOut.jsp")
				&& !url.equalsIgnoreCase("cmplt.ws")
				&& !url.equalsIgnoreCase("clogin.do")
				&& !url.equalsIgnoreCase("mngControl.do")
				&& !url.equalsIgnoreCase("userLogin.do")
				&& !url.equalsIgnoreCase("rtLoginMs.do")
				&& !url.equalsIgnoreCase("ssoMngUserInfo.do")
				&& !url.equalsIgnoreCase("getActiveUserFile.do")
				&& !url.equalsIgnoreCase("mngPayFileContent.do")
				&& !url.equalsIgnoreCase("mngPayFileContent2.do")
				&& !url.equalsIgnoreCase("mallPurchaseDtl.do")
				&& !url.equalsIgnoreCase("sendMsg.do")
				//更新用户信息
				&& !url.equalsIgnoreCase("ssoMngUserInfo_add_update.jsp")) {
				//对非法路劲拦截    去SSO平台进行url以及令牌的校验       duteng
				ReturnCode returnCode=Auth2.who(oldUrl, ip, httpResponse);
				int code=returnCode.getCode();//返回码 
				if(code==ReturnCode.RC_OK){
					//令牌有效  执行本地账号登录
					request.setAttribute("returnCode", returnCode);
					request.getRequestDispatcher("/userLogin.do?ssoUserLogin").forward(request, response);
					return;
				}
			}
		}
		chain.doFilter(request, httpResponse);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		//初始化api
		SysConfiguration sysConfig = new SysConfiguration();
		String url1 = sysConfig.getValue("url1");
		String url2 = sysConfig.getValue("url2");
		String sysId = sysConfig.getValue("sysId");
		String key = sysConfig.getValue("key");
		String serverKey = sysConfig.getValue("serverKey");
		Config.config(url1,url2,sysId,key,serverKey);
	}
}
