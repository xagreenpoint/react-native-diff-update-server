
# RN增量更新Server端

## RN增量更新客户端

	<https://github.com/xagreenpoint/LDReactNativeDiffUpdate>

## 框架依赖

	IoC容器:spring

	web框架:springmvc

	orm框架:ibatis
		
	前端框架:easyui
	
	数据库:mysql
	
	文件上传下载服务：fastDfs

## 项目概述

	主要完成两个功能：

	1. RN包上传并比对出增量包；

		1>:运行项目，无登录页面，进入包上传界面(地址为：localhost:端口号/项目名/mngPackageUpload.do?appKey=23723021a0f92fb7bea8074a03222fb3)appKey必传且为固定值。

		2>:根据界面提示填写相关信息及上传包
		
    2. 获取最新的增量包信息接口。

	接口路径 项目名/RnPackageUpdate/getUpdateList

	请求body:

	```json
			{
			appKey: "",                   //app唯一标识
			appVersion: "",               //app当前版本
			rnVersion: "",                //react native集成版本
			platform: "",                 //平台ios android
			resBody: { 
					"LDBizName1": "1.2.0",  //业务名称: 版本号
					"LDBizName2": "1.1.0", 
					...
				} 
			}

			响应body:

			```json
			{
				retCode: '000000',      //响应码：000000代表成功，其他代表失败
				retDesc: 'xxx',         //失败原因描述
				rspBody: {
					patchs:[   
						{ 
							"loadType": "ReactNative",                                       //业务类型：ReactNative、HybridApp
							"zipPath": "https://xx.xx.com/patchzip/LDBizModuleName1.zip",   //下载路径
							"version": "1.3.0",                                               //业务版本号
							"moduleName": "LDBizName1",                                     //jsBundle名称
							"zipHash": "xxxxxx",                                            //zip文件md5值
							"jsbundleHash": "xxxxxx",                                       //差异合并后js文件md5值
							"downloadNow": "3",                                             //0：总是下载, 1:wifi下载，2: 4g和wifi下载
							"loadNow": "true",                                              //true:即刻更新，false:下次启动更新
							"needGoBack": "false",                                          //是否需要回退版本
						},

						...
					  ]
				}
			}
	```
			
	数据库设计：

	```js
	CREATE TABLE `mng_rnpackage_info` (
	  `ID` int(11) NOT NULL AUTO_INCREMENT,
	  `PLAT_FORM` int(11) NOT NULL DEFAULT '0',
	  `APP_VER` varchar(8) NOT NULL DEFAULT '',
	  `RN_PACKAGE_VER` varchar(8) NOT NULL DEFAULT '',
	  `RN_PACKAGE_TYPE` smallint(6) NOT NULL DEFAULT '0',
	  `RN_PACKAGE_NAME` varchar(128) NOT NULL DEFAULT '',
	  `DOWNLOAD_URL` varchar(128) NOT NULL DEFAULT '',
	  `ZIP_HASH` varchar(128) NOT NULL DEFAULT '',
	  `JSBUNDLE_HASH` varchar(128) NOT NULL DEFAULT '',
	  `CREATE_TIME` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
	  `BASE_VER` varchar(8) NOT NULL DEFAULT '',
	  `VERSION_CODE` int(11) NOT NULL DEFAULT '0',
	  PRIMARY KEY (`ID`)
	) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8
	```