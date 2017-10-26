/**
 * 
 */
package cn.leadeon.business.update.entity;

/**
 * rn包信息
 * company leadeon
 * @author linxuchao
 * @data 2017-10-19 上午10:50:38
 */
public class RnPackageInfo {

	/**
	 * 业务类型
	 */
	private String loadType;
	
	/**
	 * 下载路径
	 */
	private String zipPath;
	
	/**
	 * 业务版本号
	 */
	private String version;
	
	/**
	 * jsBundle名称
	 */
	private String moduleName;
	
	/**
	 * zip文件MD5值
	 */
	private String zipHash;
	
	/**
	 * 差异文件合并后接收文件MD5
	 */
	private String jsbundleHash;
	
	/**
	 * 0：总是下载；1：wifi下载；2:4G和wifi下载
	 */
	private String downloadNow;
	
	/**
	 *  true：即刻更新；false：下次启动更新
	 */
	private String loadNow;
	
	/**
	 * 是否需要回退 
	 */
	private String needGoBack;

	/**
	 * @return the loadType
	 */
	public String getLoadType() {
		return loadType;
	}

	/**
	 * @param loadType the loadType to set
	 */
	public void setLoadType(String loadType) {
		this.loadType = loadType;
	}

	/**
	 * @return the zipPath
	 */
	public String getZipPath() {
		return zipPath;
	}

	/**
	 * @param zipPath the zipPath to set
	 */
	public void setZipPath(String zipPath) {
		this.zipPath = zipPath;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the moduleName
	 */
	public String getModuleName() {
		return moduleName;
	}

	/**
	 * @param moduleName the moduleName to set
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	/**
	 * @return the zipHash
	 */
	public String getZipHash() {
		return zipHash;
	}

	/**
	 * @param zipHash the zipHash to set
	 */
	public void setZipHash(String zipHash) {
		this.zipHash = zipHash;
	}

	/**
	 * @return the jsbundleHash
	 */
	public String getJsbundleHash() {
		return jsbundleHash;
	}

	/**
	 * @param jsbundleHash the jsbundleHash to set
	 */
	public void setJsbundleHash(String jsbundleHash) {
		this.jsbundleHash = jsbundleHash;
	}

	/**
	 * @return the downloadNow
	 */
	public String getDownloadNow() {
		return downloadNow;
	}

	/**
	 * @param downloadNow the downloadNow to set
	 */
	public void setDownloadNow(String downloadNow) {
		this.downloadNow = downloadNow;
	}

	/**
	 * @return the loadNow
	 */
	public String getLoadNow() {
		return loadNow;
	}

	/**
	 * @param loadNow the loadNow to set
	 */
	public void setLoadNow(String loadNow) {
		this.loadNow = loadNow;
	}

	/**
	 * @return the needGoBack
	 */
	public String getNeedGoBack() {
		return needGoBack;
	}

	/**
	 * @param needGoBack the needGoBack to set
	 */
	public void setNeedGoBack(String needGoBack) {
		this.needGoBack = needGoBack;
	}
	
}
