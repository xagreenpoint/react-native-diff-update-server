package cn.leadeon.common.util;

import java.util.Collections;
import java.util.Set;

import org.apache.avalon.framework.service.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * 解析jdbc加密文件类,继承PropertyPlaceholderConfigurer
 * 
 * @author linxuchao
 * @version 2.0
 * @date 2017-10-19
 */
public class EncryPropertyPlaceholderConfigurer extends
		PropertyPlaceholderConfigurer {

	private final static Logger LOG = Logger
			.getLogger(EncryPropertyPlaceholderConfigurer.class.getName());

	// 加密字段集合，在applicationContext.xml中配置
	private Set<String> encryptedProps = Collections.emptySet();

	/**
	 * 获取配置文件中解密字段集合
	 * 
	 * @param encryptedProps
	 */
	public void setEncryptedProps(Set<String> encryptedProps) {
		this.encryptedProps = encryptedProps;
	}

	/**
	 * 重载方法 解析Spring jdbc配置文件
	 * 
	 * @param propertyName
	 * @param propertyValue
	 * @return
	 * @throws ServiceException
	 */
	@Override
	protected String convertProperty(String propertyName, String propertyValue) {
		try {
			// 如果在加密属性名单中发现该属性
			if (encryptedProps.contains(propertyName)) {
				// 调用RSAUtils进行解密
				String decryptedPropValue = new String(
						RSAUtil.decrypt(propertyValue));
				// !=null说明正常
				if (decryptedPropValue != null) {
					// 设置解决后的值
					propertyValue = decryptedPropValue;
				}
			}
		} catch (Exception e) {
			LOG.error("解析Spring数据源配置文件出错!", e);
			throw new RuntimeException("解析Spring数据源配置文件出错！", e);
		}
		// 将处理过的值传给父类继续处理
		return super.convertProperty(propertyName, propertyValue);
	}
}
