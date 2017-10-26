/**
 * 
 */
package cn.leadeon.business.update.entity;

import cn.leadeon.common.annotion.StrVerify;
import net.sf.json.JSONObject;

/**
 * 获取更新包请求体
 * company leadeon
 * @author linxuchao
 * @data 2017-10-19 上午10:25:03
 */
public class RnPackageUpdateRequestEntity {
	
	/**
	 * app唯一标识
	 */
	@StrVerify(isNotNull = false, isNotIllegal = true)
	private String appKey;
	
	/**
	 * app版本
	 */
	@StrVerify(isNotNull = false, isNotIllegal = false)
	private String appVersion;
	
	/**
	 * react native集成版本
	 */
	@StrVerify(isNotNull = false, isNotIllegal = false)
	private String rnVersion;
	
	/**
	 * 平台
	 */
	@StrVerify(isNotNull = false, isNotIllegal = true)
	private String platForm;
	
	/**
	 * 请求体
	 */
	private JSONObject reqBody;

	/**
	 * @return the appKey
	 */
	public String getAppKey() {
		return appKey;
	}

	/**
	 * @param appKey the appKey to set
	 */
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	/**
	 * @return the appVersion
	 */
	public String getAppVersion() {
		return appVersion;
	}

	/**
	 * @param appVersion the appVersion to set
	 */
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	/**
	 * @return the rnVersion
	 */
	public String getRnVersion() {
		return rnVersion;
	}

	/**
	 * @param rnVersion the rnVersion to set
	 */
	public void setRnVersion(String rnVersion) {
		this.rnVersion = rnVersion;
	}

	/**
	 * @return the platForm
	 */
	public String getPlatForm() {
		return platForm;
	}

	/**
	 * @param platForm the platForm to set
	 */
	public void setPlatForm(String platForm) {
		this.platForm = platForm;
	}

	/**
	 * @return the reqBody
	 */
	public JSONObject getReqBody() {
		return reqBody;
	}

	/**
	 * @param reqBody the reqBody to set
	 */
	public void setReqBody(JSONObject reqBody) {
		this.reqBody = reqBody;
	}
	
}
