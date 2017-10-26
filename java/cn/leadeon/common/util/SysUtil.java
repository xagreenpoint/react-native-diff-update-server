package cn.leadeon.common.util;

import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

/**
 * 系统资源功能模块工具类
 * @author linxuchao
 *
 */
public class SysUtil {

	
	/**
	 * httpPost 表单提交
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            请求参数，将会作为表单提交，map的key为表单name，value为值。
	 * @return HttpEntity
	 * @throws Exception
	 * @author zyz 2013年12月25日9:15:14
	 */
	public static String httpPostAsForm(String url, Map<Object, Object> params)
			throws Exception {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpEntity entity = null;
		// 响应字符串
		String responseString = null;
		try {
			HttpPost httpost = new HttpPost(url);
			// 添加参数
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			if (params != null && params.keySet().size() > 0) {
				Iterator<Entry<Object, Object>> iterator = params.entrySet()
						.iterator();
				while (iterator.hasNext()) {
					Entry<?, ?> entry = (Entry<?, ?>) iterator.next();
					nvps.add(new BasicNameValuePair((String) entry.getKey(),
							(String) entry.getValue()));
				}
			}
			httpost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

			HttpResponse response;
			response = httpclient.execute(httpost);
			entity = response.getEntity();
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				BufferedInputStream bis = new BufferedInputStream(
						entity.getContent());
				byte[] bytes = new byte[1024];
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				int count = 0;
				while ((count = bis.read(bytes)) != -1) {
					bos.write(bytes, 0, count);
				}
				byte[] strByte = bos.toByteArray();
				responseString = new String(strByte, 0, strByte.length, "utf-8");
				bos.close();
				bis.close();
			}
		} finally {
			// 关闭请求
			httpclient.getConnectionManager().shutdown();
		}
		return responseString;
	}
	
	   public static String getHostIp(HttpServletRequest request){
			String ip = request.getHeader("x-forwarded-for");
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
			return ip;
	   }
}
