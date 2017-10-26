/**
 * 
 */
package cn.leadeon.business.update.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.leadeon.business.update.entity.RnPackageInfo;
import cn.leadeon.business.update.entity.RnPackageUpdateRequestEntity;
import cn.leadeon.business.update.service.RnPackageUpdateService;
import cn.leadeon.common.annotion.CodeMapping;
import cn.leadeon.common.core.ValidationTool;
import cn.leadeon.common.response.ResBody;
import cn.leadeon.common.response.ResponseEnum;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 获取增量包 company leadeon
 * 
 * @author linxuchao
 * @data 2017-10-19 上午10:45:01
 */
@Controller
@RequestMapping("RnPackageUpdate")
public class RnPackageUpdateController {

	private static Logger logger = Logger
			.getLogger(RnPackageUpdateController.class);

	@Autowired
	private RnPackageUpdateService rnPackageUpdateService;

	private CodeMapping cm;

	@RequestMapping(value = "getUpdateList")
	@ResponseBody
	public ResBody<Object> getUpdateList(HttpServletRequest request,
			@RequestBody JSONObject reqBody) throws IOException {

		ResBody<Object> result = new ResBody<>();
		logger.info("req_client:"+reqBody.toJSONString());
		// 获取流水号，用于唯一标识一次请求
		String trace = request.getHeader("Trace");
		long startTime = System.currentTimeMillis();
		try {
			RnPackageUpdateRequestEntity requestEntity = JSON.parseObject(
					reqBody.toString(), RnPackageUpdateRequestEntity.class);
			// 请求参数校验
			cm = ValidationTool.validationField(requestEntity);
			if (!ResponseEnum.RES_SUCCESS.getCode().equals(cm.getCodeNumber())) {
				result.setRspBody(null);
				result.setRetCode(cm.getCodeNumber());
				result.setRetDesc(cm.getCodeDesc());
				return result;
			}
			if(!"23723021a0f92fb7bea8074a03222fb3".equals(requestEntity.getAppKey())){
				result.setRspBody(null);
				result.setRetCode(ResponseEnum.ERR_APPKEY.getCode());
				result.setRetDesc(ResponseEnum.ERR_APPKEY.getDesc());
				return result;
			}
			JSONObject busJson = reqBody.getJSONObject("reqBody");
			List<RnPackageInfo> patchs = rnPackageUpdateService.getPatchs(requestEntity.getPlatForm(),
					busJson);
			JSONObject resData = new JSONObject();
			resData.put("patchs", patchs);
			result.setRetCode(ResponseEnum.RES_SUCCESS.getCode());
			result.setRetDesc(ResponseEnum.RES_SUCCESS.getDesc());
			result.setRspBody(resData);
		} catch (Exception e) {
			result.setRspBody(null);
			result.setRetCode(ResponseEnum.RES_ERROR.getCode());
			result.setRetDesc(ResponseEnum.RES_ERROR.getDesc());
			logger.error("获取rn更新包异常" + e.getMessage(), e);
		}finally{
			logger.info("Rsp-client:"+result.toString()+","+(System.currentTimeMillis()-startTime)+","+trace);
		}
		return result;
	}
}
