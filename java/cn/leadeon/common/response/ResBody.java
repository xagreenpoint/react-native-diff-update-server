package cn.leadeon.common.response;


/**
 * 响应体
 * 
 * @author guofazhan
 * @version [版本号, 2016-11-3]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ResBody<T extends Object>{

	/**
	 * 返回码
	 */
	private String retCode;

	/**
	 * 返回描述
	 */
	private String retDesc;

	/**
	 * 返回的内容
	 */
	private T rspBody;

	/**
	 * 获取 retCode
	 * 
	 * @return 返回 retCode
	 */
	public String getRetCode() {
		return retCode;
	}

	/**
	 * 设置 retCode
	 * 
	 * @param 对retCode进行赋值
	 */
	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	/**
	 * 获取 retDesc
	 * 
	 * @return 返回 retDesc
	 */
	public String getRetDesc() {
		return retDesc;
	}

	/**
	 * 设置 retDesc
	 * 
	 * @param 对retDesc进行赋值
	 */
	public void setRetDesc(String retDesc) {
		this.retDesc = retDesc;
	}

	/**
	 * 获取 rspBody
	 * 
	 * @return 返回 rspBody
	 */
	public T getRspBody() {
		return rspBody;
	}

	/**
	 * 设置 rspBody
	 * 
	 * @param 对rspBody进行赋值
	 */
	public void setRspBody(T rspBody) {
		this.rspBody = rspBody;
	}

	/**
	 * 获取请求方式错误返回数据
	 * 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static ResBody<String> getRequestMehtodError() {
		ResBody<String> res = new ResBody<String>();
		res.setRetCode(ResponseEnum.RES_REQUSRE_ERROR.getCode());
		res.setRetDesc(ResponseEnum.RES_REQUSRE_ERROR.getDesc());
		return res;
	}

	/**
	 * 获取参数校验返回数据
	 * 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static ResBody<String> getRequestParamError() {
		return getRequestParamError(null);
	}

	/**
	 * 获取参数校验失败返回数据
	 * 
	 * @param error
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static ResBody<String> getRequestParamError(Throwable error) {
		ResBody<String> res = new ResBody<String>();
		res.setRetCode(ResponseEnum.RES_PARAM_ERROR.getCode());
		res.setRetDesc(ResponseEnum.RES_PARAM_ERROR.getDesc());
		if (null != error) {
			res.setRspBody(error.getMessage());
		}

		return res;
	}

	/**
	 * 获取异常返回数据
	 * 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static ResBody<String> getExceptionError() {
		return getExceptionError(null);
	}

	/**
	 * 获取异常返回数据
	 * 
	 * @param error
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static ResBody<String> getExceptionError(Throwable error) {
		ResBody<String> res = new ResBody<String>();
		res.setRetCode(ResponseEnum.RES_ERROR.getCode());
		res.setRetDesc(ResponseEnum.RES_ERROR.getDesc());
		if (null != error) {
			res.setRspBody(error.getMessage());
		}

		return res;
	}

	/**
	 * 获取失败返回数据
	 * 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static ResBody<String> getResponseFail() {
		return getResponseFail(ResponseEnum.RES_FAIL.getDesc());
	}

	/**
	 * 获取失败返回数据
	 * 
	 * @param desc
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static ResBody<String> getResponseFail(String desc) {
		ResBody<String> res = new ResBody<String>();
		if (null == desc || "".equals(desc)) {
			desc = ResponseEnum.RES_FAIL.getDesc();
		}
		res.setRetCode(ResponseEnum.RES_FAIL.getCode());
		res.setRetDesc(ResponseEnum.RES_FAIL.getDesc());
		return res;
	}

	/**
	 * 获取成功响应
	 * 
	 * @param data
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static <T> ResBody<T> getResponseSuccess(T data) {
		ResBody<T> res = new ResBody<T>();
		res.setRetCode(ResponseEnum.RES_SUCCESS.getCode());
		res.setRetDesc(ResponseEnum.RES_SUCCESS.getDesc());
		res.setRspBody(data);
		return res;
	}

	/**
	 * 重载方法
	 * 
	 * @return
	 */
	@Override
	public String toString() {
		return "ResBody [retCode=" + retCode + ", retDesc=" + retDesc
				+ ", rspBody=" + rspBody + "]";
	}
}
