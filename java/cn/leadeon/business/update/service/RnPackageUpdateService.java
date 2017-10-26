/**
 * 
 */
package cn.leadeon.business.update.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.leadeon.business.update.dao.RnPackageInfoDao;
import cn.leadeon.business.update.entity.RnPackageInfo;
import cn.leadeon.common.core.service.BaseEntityManager;

/**
 * 描述
 * company leadeon
 * @author linxuchao
 * @data 2017-10-19 下午6:44:39
 */
@Service
public class RnPackageUpdateService implements BaseEntityManager<RnPackageInfo>{

	@Autowired
	private RnPackageInfoDao rnPackageInfoDao;

	/* (non-Javadoc)
	 * @see cn.leadeon.common.core.service.BaseEntityManager#create(java.lang.Object)
	 */
	@Override
	public void create(RnPackageInfo entity) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see cn.leadeon.common.core.service.BaseEntityManager#delete(java.lang.Object)
	 */
	@Override
	public void delete(RnPackageInfo entity) {
		
	}

	/* (non-Javadoc)
	 * @see cn.leadeon.common.core.service.BaseEntityManager#find(java.util.Map, int[])
	 */
	@Override
	public List<RnPackageInfo> find(@SuppressWarnings("rawtypes") Map params, int... pageParams) {
		return null;
	}

	/* (non-Javadoc)
	 * @see cn.leadeon.common.core.service.BaseEntityManager#get(java.lang.Integer)
	 */
	@Override
	public RnPackageInfo get(Integer id) {
		return null;
	}

	/* (non-Javadoc)
	 * @see cn.leadeon.common.core.service.BaseEntityManager#update(java.lang.Object)
	 */
	@Override
	public void update(RnPackageInfo entity) {
		
	}
	
	public List<RnPackageInfo> getPatchs(String platForm,JSONObject businesses){
		List<RnPackageInfo> list = new ArrayList<>();
		Map<String, Object> params = new HashMap<>();
		if(platForm.equals("android")){
			params.put("platForm", "0");
		}else{
			params.put("platForm", "1");
		}
		Set<String> keys = businesses.keySet();  
        for(String key:keys){
        	String nowVer = businesses.getString(key);
        	params.put("rnVerLength",nowVer.length());
        	params.put("nowVer", nowVer);
        	if(nowVer.split(".").length>2){//如果请求的版本号为三部分，则为更新包获取最新包
        		params.put("baseVer", nowVer.substring(0, nowVer.lastIndexOf(".")));
        	}else{//如果请求版本号为两部分，则为基础包获取最新包
        		params.put("baseVer", nowVer);
        	}
        	params.put("rnPackageName", key);
        	RnPackageInfo info = rnPackageInfoDao.getPatchs(params);
        	if(null!=info){
        		info.setLoadType("ReactNative");
        		info.setLoadNow("false");
        		info.setNeedGoBack("false");
        		info.setDownloadNow("3");
        		list.add(info);
        	}
        }  
		return list;
	}
}
