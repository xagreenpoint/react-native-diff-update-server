package cn.leadeon.common.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * 读写properties文件
 * 
 * @author linxuchao
 */
public class SysConfiguration {

	private final Logger log = Logger.getLogger(SysConfiguration.class
			.getName());

	private Properties propertie;

	private InputStream inputFile;

	private FileOutputStream outputFile;

	// app.properties加密之后的key集合
	private Set<String> encryptedProps = new HashSet<String>();

	/**
	 * 初始化Configuration类
	 */
	public SysConfiguration() {
		try {
			// 添加需要解密的字段 ，可以根据需要添加需要解密的字段
			// encryptedProps.add("FtpPassword");
			// encryptedProps.add("ComplaintFtpPassword");
			// encryptedProps.add("HuNanZhiFuFtpPassword");
			// encryptedProps.add("SMSFtpPassword");
			// 读取配置文件app.properties
			inputFile = SysConfiguration.class.getClassLoader()
					.getResourceAsStream("system.properties");
			propertie = new Properties();
			propertie.load(inputFile);
			inputFile.close();
		} catch (IOException e) {
			log.error("Configuration默认加载properties文件时出错！");
			e.printStackTrace();
		}
	}

	/**
	 * 重载函数，得到key的值
	 * 
	 * @param key取得其值的键
	 * @return key的值
	 */
	@SuppressWarnings("finally")
	public String getValue(String key) {
		String result = "";
		try {
			if (propertie.containsKey(key)) {
				// 得到某一属性的值
				String value = propertie.getProperty(key);
				// 如果该值在encryptedProps加密集合中，则解密
				if (encryptedProps.contains(key)) {
					// 解密
					result = RSAUtil.decrypt(value);
				} else {
					result = value;
				}
			}
		} catch (ServiceException e) {
			log.error("Configuration.getValue()在解密时候出错！");
			e.printStackTrace();
		} finally {
			return result;
		}
	}

	public int getIntValue(String key) {
		int retValue = -1;
		String value = getValue(key);
		if (!"".equals(value)) {
			retValue = Integer.parseInt(getValue(key));
		}
		return retValue;
	}

	/**
	 * 改变或添加一个key的值，当key存在于properties文件中时该key的值被value所代替， 当key不存在时，该key的值是value
	 * 
	 * @param key
	 *            要存入的键
	 * @param value
	 *            要存入的值
	 */
	public void setValue(String key, String value) {
		propertie.setProperty(key, value);
	}

	/**
	 * 将更改后的文件数据存入指定的文件中，该文件可以事先不存在。
	 * 
	 * @param fileName
	 *            文件路径+文件名称
	 * @param description
	 *            对该文件的描述
	 */
	public void saveFile(String description) {
		try {
			String fileName = SysConfiguration.class.getClassLoader()
					.getResource("system.properties").getFile();
			outputFile = new FileOutputStream(fileName);
			propertie.store(outputFile, description);
			outputFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

}
