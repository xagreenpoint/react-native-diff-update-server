package cn.leadeon.common.response;

/**
 * 响应返回数据
 * 
 * @author linxuchao
 * @version [版本号, 2017-10-19]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public enum ResponseEnum {

	// 响应成功
	RES_SUCCESS("000000", "响应成功"),
	// 响应异常
	RES_ERROR("000001", "对不起，您的请求暂时无法受理"),
	// 响应失败
	RES_FAIL("000002", "响应失败"),
	// 响应失败
	ERR_APPKEY("000006", "appKey校验未通过"),
	// 参数错误
	RES_PARAM_ERROR("000003", "请求报文格式异常，请检查报文是否正确！"),
	// 请求方式错误
	RES_REQUSRE_ERROR("000004", "请求方式错误，请使用POST方式发起请求！"),
	NOTNULL_ERROR("310001", "参数为空"),
	LENGTH_ERROR("000005", "参数长度错误"),
	ISNUMBER_ERROR("000005", "参数不是数字"),
	ISNOTILLEGAL_ERROR("000005", "参数有非法字符"),
	ENUM_ERROR("000005", "参数不在枚举类型内"),
	IDDOUBLE_ERROR("000005", "参数不是double类型"),
	IDBIGSTR_ERROR("000005", "检验字符串中含有特殊字符"),
	;

	/**
	 * 响应编码
	 */
	private final String code;

	/**
	 * 响应描述
	 */
	private final String desc;

	private ResponseEnum(String code, String desc) {

		this.code = code;
		this.desc = desc;
	}

	/**
	 * 获取 code
	 * 
	 * @return 返回 code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 获取 desc
	 * 
	 * @return 返回 desc
	 */
	public String getDesc() {
		return desc;
	}

}
