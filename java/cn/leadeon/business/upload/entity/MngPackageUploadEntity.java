package cn.leadeon.business.upload.entity;

/**
 * 包上传实体类
 */
public class MngPackageUploadEntity {

    /**
     * id
     */
    private Integer id;
    /**
     * 平台类型
     */
    private Integer platForm;

    /**
     * 客户端版本
     */
    private String appVer;

    /**
     * 包版本
     */
    private String rnPackageVer;
    
    /**
     * 基础包版本
     */
    private String baseVer;

    /**
     * 包类型
     */
    private Integer rnPackageType;

    /**
     * 包名称
     */
    private String rnPackageName;

    /**
     * 包下载地址
     */
    private String downLoadUrl;

    /**
     * ZIP包MD5值
     */
    private String ZipHash;
    
    /**
     * 版本编码
     */
    private Integer versionCode;

    /**
     * jsbundle MD5值
     */
    private String jsBundleHash;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlatForm() {
        return platForm;
    }

    public void setPlatForm(Integer platForm) {
        this.platForm = platForm;
    }

    public String getAppVer() {
        return appVer;
    }

    public void setAppVer(String appVer) {
        this.appVer = appVer;
    }

    public String getRnPackageVer() {
        return rnPackageVer;
    }

    public void setRnPackageVer(String rnPackageVer) {
        this.rnPackageVer = rnPackageVer;
    }

    public Integer getRnPackageType() {
        return rnPackageType;
    }

    public void setRnPackageType(Integer rnPackageType) {
        this.rnPackageType = rnPackageType;
    }

    public String getRnPackageName() {
        return rnPackageName;
    }

    public void setRnPackageName(String rnPackageName) {
        this.rnPackageName = rnPackageName;
    }

    public String getDownLoadUrl() {
        return downLoadUrl;
    }

    public void setDownLoadUrl(String downLoadUrl) {
        this.downLoadUrl = downLoadUrl;
    }

    public String getZipHash() {
        return ZipHash;
    }

    public void setZipHash(String zipHash) {
        ZipHash = zipHash;
    }

    public String getJsBundleHash() {
        return jsBundleHash;
    }

    public void setJsBundleHash(String jsBundleHash) {
        this.jsBundleHash = jsBundleHash;
    }

	/**
	 * @return the baseVer
	 */
	public String getBaseVer() {
		return baseVer;
	}

	/**
	 * @param baseVer the baseVer to set
	 */
	public void setBaseVer(String baseVer) {
		this.baseVer = baseVer;
	}

	/**
	 * @return the versionCode
	 */
	public Integer getVersionCode() {
		return versionCode;
	}

	/**
	 * @param versionCode the versionCode to set
	 */
	public void setVersionCode(Integer versionCode) {
		this.versionCode = versionCode;
	}
    
}
