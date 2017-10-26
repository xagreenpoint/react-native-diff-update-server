/**
 * 
 */
package cn.leadeon.business.update.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.leadeon.business.update.entity.RnPackageInfo;
import cn.leadeon.common.core.dao.AbstractGenericDao;

/**
 * RN包信息
 * company leadeon
 * @author linxuchao
 * @data 2017-10-19 下午6:46:55
 */
@Repository
public class RnPackageInfoDao extends AbstractGenericDao<RnPackageInfo>{

	/**
	 * 根据平台，基础包版本，业务名称查询出增量包
	 * @param params
	 * @return
	 */
	public RnPackageInfo getPatchs(Map<String, Object> params){
		return (RnPackageInfo) this.getSqlMapClientTemplate()
				.queryForObject(getNameSpace() + ".getPatchs", params);
	}
}
