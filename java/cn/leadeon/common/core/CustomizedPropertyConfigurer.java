package cn.leadeon.common.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 自定义的配置加载类
 * 
 * @author guofazhan
 * @version [1.0, 2016-8-12]
 * @see [相关类/方法]
 * @since [中国联通一固定流量池运营平台/模块版本]
 */
public class CustomizedPropertyConfigurer {
	/**
     * 
     */
	private static Map<String, Properties> propertiesMap;

	/**
     * 
     */
	private List<String> locations;

	static {
		propertiesMap = new HashMap<String, Properties>();
	}

	/**
	 * 
	 * @param name
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public Properties getProperty(String name) {
		if (propertiesMap.isEmpty()) {
			init();
		}

		return propertiesMap.get(name);
	}

	/**
	 * 初始化配置信息
	 * 
	 * @see [类、类#方法、类#成员]
	 */
	protected void init() {
		if (locations != null && !locations.isEmpty()) {
			for (String name : locations) {
				try {
					Properties prop = new Properties();
					prop.load(CustomizedPropertyConfigurer.class
							.getResourceAsStream("/" + name));
					propertiesMap.put(name, prop);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 获取 locations
	 * 
	 * @return 返回 locations
	 */
	public List<String> getLocations() {
		return locations;
	}

	/**
	 * 设置 locations
	 * 
	 * @param 对locations进行赋值
	 */
	public void setLocations(List<String> locations) {
		this.locations = locations;
	}

}
