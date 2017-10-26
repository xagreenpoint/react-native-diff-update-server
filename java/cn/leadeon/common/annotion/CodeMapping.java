package cn.leadeon.common.annotion;

/**   
* @Title: CodeMappingDb.java 
*
* @Package cn.leadeon.db.resultpojo 
*
* @author gavin   
*
* @date 2015-1-9 下午3:44:44 
*
* @Description: 动态响应码
*
* @version 1.0-SNAPSHOT   
*/ 
public class CodeMapping{

	/*业务场景码 */
	private String businessCode;
	
	/*集成域码 */
	private String IntegrationCode;
	
	/*响应客户端编码 */
	private String codeNumber;
	
	/*描述 */
	private String codeDesc;


	public String getBusinessCode() {
		return businessCode;
	}


	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}


	public String getIntegrationCode() {
		return IntegrationCode;
	}


	public void setIntegrationCode(String integrationCode) {
		IntegrationCode = integrationCode;
	}

	public String getCodeDesc() {
		return codeDesc;
	}


	public void setCodeDesc(String codeDesc) {
		this.codeDesc = codeDesc;
	}


	public String getCodeNumber() {
		return codeNumber;
	}


	public void setCodeNumber(String codeNumber) {
		this.codeNumber = codeNumber;
	}
	
}
