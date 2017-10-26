package cn.leadeon.common.util.fastdfs;


import cn.leadeon.common.util.IOUtils;
import cn.leadeon.common.util.SysConfiguration;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * FastDFS 工具
 * 
 * @author linxuchao
 * @version [版本号, 2016-10-19]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class FastDFSUtils {

	private static Logger logger = Logger.getLogger(FastDFSUtils.class);

	private static SysConfiguration configur = null;
	static {
		configur = new SysConfiguration();
	}
	/**
	 * 文件上传
	 * 
	 * @param fileName
	 *            文件名
	 * @param extName
	 *            文件扩展名
	 * @param fileByte
	 *            文件byte数组
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static String upload(String fileName, String extName,
			byte[] fileByte) {
		String responseContext = null;
		try {
			Map<Object, Object> params = new HashMap<Object, Object>();
			params.put("ExtName", extName);
			params.put("FileName", fileName);
			params.put("FileByte", new String(fileByte, "ISO-8859-1"));
			logger.info("上传文件至FastDFS，组装上传参数 params: " + "ExtName is ["
					+ extName + "]," + "FileName is [" + fileName + "],"
					+ "FileByte's length is ["
					+ new String(fileByte, "ISO-8859-1").length() + "]");
			// 获取FastDFS接口URL发送请求
			String uploadFileUrl = configur.getValue("UPLOAD_FILE_URL");
			URL u = new URL(uploadFileUrl);
			HttpURLConnection conn = (HttpURLConnection) u.openConnection();
			logger.info("上传文件至FastDFS，连接服务器，上传接口地址为:" + uploadFileUrl);

			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			StringBuffer biSb = new StringBuffer();
			OutputStream output = conn.getOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(output);
			out.writeObject(params);
			output.flush();
			output.close();
			// 接收请求返回结果
			InputStream in = conn.getInputStream();
			BufferedReader bi = new BufferedReader(new InputStreamReader(in));
			String resultLine = null;
			while ((resultLine = bi.readLine()) != null) {
				biSb.append(resultLine);
			}
			responseContext = biSb.toString();
			logger.info("上传文件至FastDFS，上传返回参数为:" + responseContext);
			responseContext = convertOutNetUrl(responseContext);
			logger.info("上传文件至FastDFS，将上传返回的URL转换为外网URL:" + responseContext);
		} catch (Exception e) {
			logger.error("上传文件至FastDFS出现异常:" + e.getMessage());
		}
		return responseContext;
	}

	/**
	 * 下载文件
	 * 
	 * @param fileUrl
	 *            文件地址
	 * @param filePath
	 *            保存地址
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static boolean downloadFile(String fileUrl, String filePath) {
		boolean res = false;
		InputStream in = null;
		OutputStream temos = null;
		InputStream tempIn = null;
		BufferedReader bi = null;
		try {
			Map<Object, Object> params = new HashMap<Object, Object>();
			params.put("FileId", FastDFSUtils.subFileId(fileUrl));
			logger.info("从FastDFS下载文件，组装下载参数 params: " + "FileId is ["
					+ FastDFSUtils.subFileId(fileUrl) + "]");

			// 获取FastDFS接口URL发送请求
			URL u = new URL(configur.getValue("DOWNLOAD_FILE_URL"));
			HttpURLConnection conn = (HttpURLConnection) u.openConnection();
			logger.info("从FastDFS下载文件，连接服务器，下载接口地址为:"
					+ configur.getValue("DOWNLOAD_FILE_URL"));

			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			StringBuffer biSb = new StringBuffer();
			OutputStream output = conn.getOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(output);
			out.writeObject(params);
			output.flush();
			output.close();
			// 接收请求返回结果
			in = conn.getInputStream();

			File file = new File(filePath);
			if (!file.exists()) {
				file.createNewFile();
			}

			// 写入文件
			temos = new FileOutputStream(filePath);
			byte[] buf1 = new byte[1024];
			int len;
			while ((len = in.read(buf1)) > 0) {
				temos.write(buf1, 0, len);
			}

			// 读取文件流信息，判断文件是否正确
			tempIn = new FileInputStream(filePath);
			bi = new BufferedReader(new InputStreamReader(tempIn));
			String resultLine = null;
			while ((resultLine = bi.readLine()) != null) {
				biSb.append(resultLine);
			}
			String responseContext = biSb.toString();

			if (responseContext.startsWith("error")) {
				res = false;
				logger.info("从FastDFS下载文件出错，错误信息为:" + responseContext);
			} else {
				res = true;
			}
			logger.info("从FastDFS下载文件，下载返回参数长度为:" + responseContext.length());
		} catch (Exception e) {
			logger.error("从FastDFS下载文件出现异常:" + e.getMessage());
		} finally {
			IOUtils.close(in);
			IOUtils.close(temos);
			IOUtils.close(bi);
		}
		return res;
	}

	/**
	 * 
	 * 下载文件返回文件流
	 * 
	 * @param fileUrl
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static InputStream downloadFile(String fileUrl) {
		InputStream in = null;
		try {
			Map<Object, Object> params = new HashMap<Object, Object>();
			params.put("FileId", FastDFSUtils.subFileId(fileUrl));
			logger.info("从FastDFS下载文件，组装下载参数 params: " + "FileId is ["
					+ FastDFSUtils.subFileId(fileUrl) + "]");

			// 获取FastDFS接口URL发送请求
			String downloadFileUrl = configur.getValue("DOWNLOAD_FILE_URL");
			URL u = new URL(downloadFileUrl);
			HttpURLConnection conn = (HttpURLConnection) u.openConnection();
			logger.info("从FastDFS下载文件，连接服务器，下载接口地址为:" + downloadFileUrl);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			OutputStream output = conn.getOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(output);
			out.writeObject(params);
			output.flush();
			output.close();
			// 接收请求返回结果
			in = conn.getInputStream();

		} catch (Exception e) {
			logger.error("从FastDFS下载文件流信息出现异常:" + e.getMessage());
		}
		return in;
	}

	/**
	 * 删除文件
	 * 
	 * @param fileUrl
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static String deleteFile(String fileUrl) {
		String responseContext = null;
		try {
			Map<Object, Object> params = new HashMap<Object, Object>();
			params.put("FileId", FastDFSUtils.subFileId(fileUrl));
			logger.info("从FastDFS删除文件，组装删除参数 params: " + "FileId is ["
					+ FastDFSUtils.subFileId(fileUrl) + "]");
			// 获取FastDFS接口URL发送请求
			SysConfiguration configurer = new SysConfiguration();
			String deleteFileUrl = configurer.getValue("DELETE_FILE_URL");
			URL u = new URL(deleteFileUrl);
			HttpURLConnection conn = (HttpURLConnection) u.openConnection();
			logger.info("从FastDFS删除文件，连接服务器，删除接口地址为:" + deleteFileUrl);

			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			StringBuffer biSb = new StringBuffer();
			OutputStream output = conn.getOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(output);
			out.writeObject(params);
			output.flush();
			output.close();
			// 接收请求返回结果
			InputStream in = conn.getInputStream();
			BufferedReader bi = new BufferedReader(new InputStreamReader(in));
			String resultLine = null;
			while ((resultLine = bi.readLine()) != null) {
				biSb.append(resultLine);
			}
			responseContext = biSb.toString();
			logger.info("从FastDFS删除文件，删除返回参数为:" + responseContext);
		} catch (Exception e) {
			logger.error("从FastDFS删除文件出现异常:" + e);
		}
		return responseContext;
	}

	/**
	 * 根据fileUrl截取fileId
	 * 
	 * @param fileUrl
	 * @return
	 */
	public static String subFileId(String fileUrl) {
		String fileId = null;
		SysConfiguration configurer = new SysConfiguration();
		if (fileUrl.startsWith(configurer.getValue("FSAT_DFS_OUTNET_URL"))) {
			fileId = fileUrl.replace(configurer.getValue("FSAT_DFS_OUTNET_URL"), "");
		}
		return fileId;
	}

	/**
	 * 
	 * 将FastDFS返回的文件地址转换为外网地址 <功能详细描述>
	 * 
	 * @param fileUrl
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static String convertOutNetUrl(String fileUrl) {
		String outNetUrl = null;
		logger.info("DFSServer:" + configur.getValue("FAST_DFS_SERVER").trim());
		if (fileUrl.startsWith(configur.getValue("FAST_DFS_SERVER").trim())) {
			logger.info("Replace DFSServer:"
					+ configur.getValue("FSAT_DFS_OUTNET_URL").trim());
			outNetUrl = fileUrl.replace(
					configur.getValue("FAST_DFS_SERVER").trim(),
					configur.getValue("FSAT_DFS_OUTNET_URL").trim());
		} else {
			outNetUrl = fileUrl;
		}
		return outNetUrl;
	}

}
