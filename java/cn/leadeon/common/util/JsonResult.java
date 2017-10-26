package cn.leadeon.common.util;

import java.io.Serializable;
import java.util.Collection;

/**
 * 
 * view返回数据
 * 
 * @author linxuchao
 * @version [版本号, 2016-10-12]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class JsonResult<T> implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8911913203693199943L;

	/**
	 * 默认成功返回码
	 */
	public static final int DEFAULT_SUCCESS_CODE = 200;

	/**
	 * 默认失败返回码
	 */
	public static final int DEFAULT_FAIL_CODE = 300;

	/**
	 * 默认session失效返回码
	 */
	public static final int DEFAULT_SESSION_INVALID_CODE = 400;

	/**
	 * 默认参数校验失效返回码
	 */
	public static final int DEFAULT_PARAM_INVALID_CODE = 500;

	/**
	 * 默认的系统异常返回值
	 */
	public static final int DEFAULT_ERROR_CODE = 999;

	/**
	 * 返回码
	 */
	private int resultCode;

	/**
	 * 返回消息
	 */
	private String resultMsg;

	/**
	 * 返回数据
	 */
	private T data;

	/**
	 * 总记录数，分页返回使用
	 */
	private long total;

	/**
	 * 数据，分页返回使用
	 */
	private Collection<?> rows;

	protected JsonResult() {

	}

	protected JsonResult(int resultCode, String resultMsg, T data) {
		this.resultCode = resultCode;
		this.resultMsg = resultMsg;
		this.data = data;
	}

	/**
	 * 获取默认的返回结果
	 * 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static <T> JsonResult<T> getDefaultResult() {
		return new JsonResult<T>();
	}

	/**
	 * 获取默认的成功返回结果
	 * 
	 * @param data
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static <T> JsonResult<T> getDefaultSuccessResult(T data) {
		return new JsonResult<T>(DEFAULT_SUCCESS_CODE, "执行成功", data);
	}

	/**
	 * 获取默认的失败返回结果
	 * 
	 * @param message
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static <T> JsonResult<T> getDefaultFailResult(String message) {
		return getFailResult(DEFAULT_FAIL_CODE, message);
	}

	/**
	 * 
	 * 获取失败返回结果
	 * 
	 * @param resultCode
	 * @param message
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static <T> JsonResult<T> getFailResult(int resultCode, String message) {
		return new JsonResult<T>(resultCode, message, null);
	}

	/**
	 * 获取session失效返回结果
	 * 
	 * @param message
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static <T> JsonResult<T> getSessionInvalidResult(String message) {
		if (null == message || "".equals(message.trim())) {
			message = "Session is Invalid";
		}

		return new JsonResult<T>(DEFAULT_SESSION_INVALID_CODE, message, null);
	}

	/**
	 * 获取 resultCode
	 * 
	 * @return 返回 resultCode
	 */
	public int getResultCode() {
		return resultCode;
	}

	/**
	 * 设置 resultCode
	 * 
	 * @param 对resultCode进行赋值
	 */
	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	/**
	 * 获取 resultMsg
	 * 
	 * @return 返回 resultMsg
	 */
	public String getResultMsg() {
		return resultMsg;
	}

	/**
	 * 设置 resultMsg
	 * 
	 * @param 对resultMsg进行赋值
	 */
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	/**
	 * 获取 data
	 * 
	 * @return 返回 data
	 */
	public T getData() {
		return data;
	}

	/**
	 * 设置 data
	 * 
	 * @param 对data进行赋值
	 */
	public void setData(T data) {
		this.data = data;
	}

	/**
	 * 获取 total
	 * 
	 * @return 返回 total
	 */
	public long getTotal() {
		return total;
	}

	/**
	 * 设置 total
	 * 
	 * @param 对total进行赋值
	 */
	public void setTotal(long total) {
		this.total = total;
	}

	/**
	 * 获取 rows
	 * 
	 * @return 返回 rows
	 */
	public Collection<?> getRows() {
		return rows;
	}

	/**
	 * 设置 rows
	 * 
	 * @param 对rows进行赋值
	 */
	public void setRows(Collection<?> rows) {
		this.rows = rows;
	}

	/**
	 * 重载方法
	 * 
	 * @return
	 */
	@Override
	public String toString() {
		return "JsonResult [resultCode=" + resultCode + ", resultMsg="
				+ resultMsg + ", data=" + data + "]";
	}

}
