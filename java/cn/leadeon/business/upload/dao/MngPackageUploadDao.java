package cn.leadeon.business.upload.dao;

import java.util.Map;

import cn.leadeon.business.upload.entity.MngPackageUploadEntity;
import cn.leadeon.common.core.dao.AbstractGenericDao;
import org.springframework.stereotype.Repository;

/**
 * 更新包上传
 * 
 * @author linxuchao
 * @version [版本号, 2017-10-19]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Repository
public class MngPackageUploadDao extends
		AbstractGenericDao<MngPackageUploadEntity> {

	/**
	 * 根据版本号名+基础包版本+平台查询出
	 * 
	 * @param params
	 * @return
	 */
	public MngPackageUploadEntity getBasePackage(Map<String, Object> params) {
		return (MngPackageUploadEntity) this.getSqlMapClientTemplate()
				.queryForObject(getNameSpace() + ".getBasePackage", params);
	}
}
