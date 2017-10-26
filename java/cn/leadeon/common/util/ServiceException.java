package cn.leadeon.common.util;

/**
 * @author linxuchao 业务异常类，用于抛出并引起spring事务回滚
 * 
 */
public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = 2607967603611948559L;

	private String errorCode;

	public ServiceException() {
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(String message, String errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * 获取 errorCode
	 * 
	 * @return 返回 errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * 设置 errorCode
	 * 
	 * @param 对errorCode进行赋值
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
}
