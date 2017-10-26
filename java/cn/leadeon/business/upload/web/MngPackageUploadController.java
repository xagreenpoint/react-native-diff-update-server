/*
 * 文 件 名:  MngPackageUploadController.java
 * 版    权:  Xi'An Leadeon Technologies Co., Ltd. Copyright 2017-10-13,  All rights reserved  
 */
package cn.leadeon.business.upload.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.leadeon.business.upload.entity.MngPackageUploadEntity;
import cn.leadeon.business.upload.service.MngPackageUploadService;
import cn.leadeon.common.util.FileTools;
import cn.leadeon.common.util.JsonResult;
import cn.leadeon.common.util.fastdfs.FastDFSUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 包上传
 * 
 * @author linxuchao
 * @version [V1.1, 2017-10-13]
 * @see [相关类/方法]
 */
@Controller
@RequestMapping("/mngPackageUpload.do")
public class MngPackageUploadController {

	private static Logger log = Logger
			.getLogger(MngPackageUploadController.class);

	@Autowired
	private MngPackageUploadService mngPackageUploadService;

	@RequestMapping
	public String init(HttpServletRequest request) {
		String appKey = request.getParameter("appKey");
		if (StringUtils.isNotBlank(appKey)
				&& appKey.equals("23723021a0f92fb7bea8074a03222fb3")) {
			return "reactNative/mngPackageUpload";
		}
		return "";
	}

	/**
	 * 包上传
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "method=packageUpload")
	@ResponseBody
	public void packageUpload(MultipartHttpServletRequest request,
			HttpServletResponse response) {
		log.info("Enter MngPackageUploadController packageUpload action!");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			CommonsMultipartFile file = (CommonsMultipartFile) request
					.getFile("qqfile");
			String fileName = file.getOriginalFilename();
			// RN包上传
			String uploadFileName = UUID.randomUUID().toString()
					.replaceAll("-", "");
			// 获取临时目录
			String tempPath = FileTools.getTempPath(request);
			File targetFile = new File(tempPath, file.getOriginalFilename());
			file.transferTo(targetFile);
			String filePath = FastDFSUtils.upload(uploadFileName,
					FileTools.subFileExtName(file.getOriginalFilename()),
					FileTools.toByteArray(targetFile));
			JSONObject rtMsg = new JSONObject();
			rtMsg.put("success", true);
			rtMsg.put("serialPath", filePath);
			rtMsg.put("fileName", fileName);
			writer.print(rtMsg.toString());
			targetFile.delete();
		} catch (Exception e) {
			writer.print("{\"success\":false}");
			log.error("上传RN包异常" + e.getMessage(), e);
		} finally {
			writer.flush();
			writer.close();
		}
	}

	/**
	 * 保存并比对RN包
	 * 
	 * @param request
	 * @param entity
	 * @return
	 */
	@RequestMapping(params = "method=savePackage")
	@ResponseBody
	public JsonResult<?> savePackage(HttpServletRequest request,HttpServletResponse response,
			MngPackageUploadEntity entity) {
		JsonResult<?> result = JsonResult.getDefaultFailResult("");
		response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
		try {
			if (null != entity) {
				MngPackageUploadEntity basePackage = new MngPackageUploadEntity();
				// 获取程序部署目录以便下载RN包进行比对
				String tempPath = FileTools.getTempPath(request);
				if (null != entity.getRnPackageType()
						&& 1 == entity.getRnPackageType()) {// 如果为更新包，需先查询出基础包
					/**
					 * 以“.”分割版本号，截取前两部分，获取基础版本号
					 * （版本号规则：基础包由两部分组成，更新包由三部分组成，更新包对应的基础包的版本号强两部分是相同的）
					 * 比如：基础包版本 1.0 对应的更新包版本为 1.0.1, 1.0.2, 1.0.100
					 **/
					String[] baseVers = entity.getRnPackageVer().split("\\.");
					String baseVer = baseVers[0]+ "." + baseVers[1];
					// 根据包名及版本号查询出基础包
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("baseVer", baseVer);
					params.put("rnPackageName", entity.getRnPackageName());
					params.put("platForm", entity.getPlatForm());
					basePackage = mngPackageUploadService
							.getBasePackage(params);
					if (null == basePackage) {
						result = JsonResult
								.getDefaultFailResult("未找到基础包，请重新上传包");
						return result;
					}
					//根据业务名称及版本号查询该版本更新包是否存在
					Map<String, Object> updateParam = new HashMap<String, Object>();
					updateParam.put("rnPackageVer", entity.getRnPackageVer());
					updateParam.put("rnPackageName", entity.getRnPackageName());
					updateParam.put("platForm", entity.getPlatForm());
					MngPackageUploadEntity updatePackage = mngPackageUploadService
							.getBasePackage(updateParam);
					if(null!=updatePackage){
						result = JsonResult
								.getDefaultFailResult("该业务此版本更新包已存在，请重新上传包");
						return result;
					}
					entity.setVersionCode(Integer.parseInt(baseVers[2]));
				}else if(null != entity.getRnPackageType()
						&& 0 == entity.getRnPackageType()){//基础包
					String[] baseVers = entity.getRnPackageVer().split("\\.");
					String baseVer = baseVers[0]+ "." + baseVers[1];
					// 根据包名及版本号查询出基础包
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("baseVer", baseVer);
					params.put("rnPackageName", entity.getRnPackageName());
					params.put("platForm", entity.getPlatForm());
					basePackage = mngPackageUploadService
							.getBasePackage(params);
					if(null!=basePackage){
						result = JsonResult
								.getDefaultFailResult("该业务基础包已存在，请重新上传包");
						return result;
					}
					entity.setVersionCode(0);
				}
				mngPackageUploadService.save(entity, tempPath, basePackage);
				result = JsonResult.getDefaultSuccessResult("操作成功");
			}
		} catch (Exception e) {
			log.error("保存RN包失败" + e.getMessage(), e);
			result = JsonResult.getDefaultFailResult("系统异常");
		}
		return result;
	}

}
