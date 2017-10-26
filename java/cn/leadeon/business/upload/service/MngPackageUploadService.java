package cn.leadeon.business.upload.service;

import cn.leadeon.business.upload.dao.MngPackageUploadDao;
import cn.leadeon.business.upload.entity.MngPackageUploadEntity;
import cn.leadeon.common.core.service.BaseEntityManager;
import cn.leadeon.common.util.FileTools;
import cn.leadeon.common.util.ServiceException;
import cn.leadeon.common.util.fastdfs.FastDFSUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.sigpipe.jbsdiff.Diff;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 更新包上传
 * 
 * @author linxuchao
 * @version [版本号, 2017-10-19]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service
public class MngPackageUploadService implements
		BaseEntityManager<MngPackageUploadEntity> {

	@Autowired
	private MngPackageUploadDao mngPackageUploadDao;

	@Override
	public void create(MngPackageUploadEntity entity) {

	}

	@Override
	public void delete(MngPackageUploadEntity entity) {

	}

	@Override
	public List<MngPackageUploadEntity> find(
			@SuppressWarnings("rawtypes") Map params, int... pageParams) {
		return null;
	}

	@Override
	public MngPackageUploadEntity get(Integer id) {
		return null;
	}

	@Override
	public void update(MngPackageUploadEntity entity) {

	}

	/**
	 * 保存信息
	 * 
	 * @param entity
	 * @param tempPath
	 * @param basePackage
	 */
	public void save(MngPackageUploadEntity entity, String tempPath,
			MngPackageUploadEntity basePackage) throws Exception {
		FileTools tools = new FileTools();
		if (null != entity.getPlatForm()) {
			// zip文件名称
			String zipfileName = FileTools.sbuFileName(entity.getDownLoadUrl());
			// 解压后文件名称
			String fileName = zipfileName.substring(0,
					zipfileName.lastIndexOf("."));
			// 基础包解压后名称
			String baseFileName = "";
			// 基础包名称
			String baseZipfileName = "";
			// 差异包名称
			String rnFileName = UUID.randomUUID().toString()
					.replaceAll("-", "");
			boolean downLoadBaseFlag = true;
			// 下载RN包到临时目录下
			boolean downLoadFlag = FastDFSUtils.downloadFile(
					entity.getDownLoadUrl(), tempPath+File.separator+zipfileName);
			if (1 == entity.getRnPackageType()) {// 如果上传的包类型为更新包，则需下载基础包
				// zip文件名称
				baseZipfileName = FileTools.sbuFileName(basePackage
						.getDownLoadUrl());
				// 解压后文件名称
				baseFileName = baseZipfileName.substring(0,
						baseZipfileName.lastIndexOf("."));
				// 下载基础包到base目录下
				downLoadBaseFlag = FastDFSUtils.downloadFile(
						basePackage.getDownLoadUrl(), tempPath+File.separator+baseZipfileName);
			}
			if (downLoadFlag && downLoadBaseFlag) {
				File zipFile = new File(tempPath, zipfileName);// 下载到的zip包
				if (entity.getRnPackageType() == 0) {// 基础包
					// 获取RN包md5
					entity.setZipHash(FileTools.MD5File(zipFile));
					// 解压文件到项目临时目录下
					FileTools.UnZIP(zipFile, tempPath);
					String jsPath = tempPath + File.separator + fileName;
					File jsBundleFile = new File(jsPath, "main.jsbundle");
					entity.setJsBundleHash(FileTools.MD5File(jsBundleFile));
					entity.setBaseVer(entity.getRnPackageVer());
					zipFile.delete();
					FileTools.deleteDir(new File(jsPath));
				} else if (1 == entity.getRnPackageType()) {// 更新包
					entity.setBaseVer(basePackage.getBaseVer());
					// 解压文件到项目临时目录下
					FileTools.UnZIP(zipFile, tempPath);// 更新包解压
					File baseZipFile = new File(tempPath, baseZipfileName);
					FileTools.UnZIP(baseZipFile, tempPath);// 基础包解压
					String jsPath = tempPath + File.separator + fileName;
					String basejsPath = tempPath + File.separator
							+ baseFileName;
					File jsBundleFile = new File(jsPath, "main.jsbundle");
					File basejsBundleFile = new File(basejsPath,
							"main.jsbundle");
					File pathFile = new File(jsPath, "patch.jsbundle");
					FileOutputStream out = new FileOutputStream(pathFile);
					// 差异算法，输出差异文件（差异文件目录为：和main.jsbundle同级目录）
					Diff.diff(FileTools.toByteArray(basejsBundleFile),
							FileTools.toByteArray(jsBundleFile), out);
					out.close();
					// 差异文件MD5摘要
					entity.setJsBundleHash(FileTools.MD5File(jsBundleFile));
					// 压缩差异包
					tools.zip(jsPath, tempPath + File.separator + rnFileName
							+ ".zip");
					File rnFile = new File(tempPath
							+ File.separator + rnFileName + ".zip");
					entity.setZipHash(FileTools.MD5File(rnFile));
					//差异包上传fastDfs
					String downLoadUrl = FastDFSUtils.upload(rnFileName,"zip",FileTools.toByteArray(rnFile));
					entity.setDownLoadUrl(downLoadUrl);
					// 删除掉更新包的main.jsbundle文件
					jsBundleFile.delete();
					zipFile.delete();
					baseZipFile.delete();
					rnFile.delete();
					FileTools.deleteDir(new File(tempPath
							+ File.separator + rnFileName));
					FileTools.deleteDir(new File(jsPath));
					FileTools.deleteDir(new File(basejsPath));
				}
			} else {
				throw new ServiceException("包上传异常");
			}
			mngPackageUploadDao.save(entity);
		}
	}

	/**
	 * 根据包名+版本号+平台查询出基础包
	 * 
	 * @param params
	 * @return
	 */
	public MngPackageUploadEntity getBasePackage(Map<String, Object> params) {
		return mngPackageUploadDao.getBasePackage(params);
	}
	
}
