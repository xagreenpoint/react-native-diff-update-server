/**
 * <description> 用户权限管理相关js </description>
 * 
 * <p> fartherId 指定查找的子权限列表的父权限ID </p>
 * <p> oper4Btn 权限对应的按钮ID属性对象，ID属性可以以英文逗号分隔“,”（必须是ID属性，不带选择器类型）。例：
 * 	{	
 * 		"add":"BUTTON ID" , 
 * 		"del":"BUTTON ID" , 
 * 		"mod":"BUTTON ID" , 
 * 		"sel":"BUTTON ID" , 
 * 		"chk":"BUTTON ID" , 
 * 		"imp":"BUTTON ID" , 
 * 		"exp":"BUTTON ID" ,
 * 		"release":"BUTTON ID" , 
 * 		"order":"BUTTON ID" ,
 * 		"up":"BUTTON ID" ,
 * 		"off":"BUTTON ID" ,
 * 		"delall":"BUTTON ID" 
 * 	}
 * </p>
 * <p> tbDiv 按钮域toolbar的选择器 </p>
 * <p> autoSwitch 自动显示按钮的开关 </p>
 * <p> custom 自定义回调事件，携带权限对象的参数 </p>
 * 
 */
var AuthConfig = function(){
	var fartherId = "";
	var tbDiv = "";
	var oper4Btn = new Object();
	var autoSwitch = true;
	var custom = null;
}

AuthConfig.prototype.init = function(authCon){
	this.fartherId = authCon.fartherId;
	this.tbDiv = authCon.tbDiv;
	this.oper4Btn = authCon.oper4Btn;
	if(typeof(authCon.autoSwitch) != "undefined" 
		&& authCon.autoSwitch != null 
		&& authCon.autoSwitch != ""
		&& !authCon.autoSwitch){
		this.autoSwitch = authCon.autoSwitch;
	}else{
		this.autoSwitch = true;
	}
	this.custom = authCon.custom;
	this.split=",";
}


AuthConfig.prototype.render = function(authCon){
	//初始化对象
	this.init(authCon);
	/*
	//获取包含按钮的容器
	var $selector = null;
	if(this.tbDiv==null || this.tbDiv==""){
		$selector = $;
	}else{
		$selector = $(this.tbDiv);
	}*/
	
	//隐藏掉所有按钮
	if(this.autoSwitch){
		for(var name in this.oper4Btn){
//			console.log($("#"+this.oper4Btn[name]).html());
			var ids = this.oper4Btn[name].split(this.split);
			if(null != ids && ids.length > 0){
				for(var id in ids){
					if(!$("#"+ids[id]).is(":hidden")){
						$("#"+ids[id]).css("display","none");
					}
					if($("#"+ids[id]).parent().prev().children(".datagrid-btn-separator")){
						$("#"+ids[id]).parent().prev().children(".datagrid-btn-separator").css("display","none");
					}
				}
				if(typeof(this.tbDiv) != "undefined" && this.tbDiv != null && this.tbDiv != ""){
					//alert(1);
					//$(this.tbDiv).css({ display: "none" });
					$(this.tbDiv).css("display","none");
					//alert($(this.tbDiv).is(":hidden"));
				}
			}
	    }
	}
	
	//根据fartherId获取权限对象
	var authData = new Array();
	if(typeof(this.fartherId) == "undefined" || this.fartherId == null || this.fartherId == ""){
		
	}else{
		$.ajax({
		    type: "post",  
		    url: ctx + "/athrtyConfig.do?method=getUserBtn",
		    data:{"fartherId":this.fartherId},
		    dataType: "json",
		    async : false,
		    timeout: 50000,
		    success: function(data){
		    	authData = data;
		    }, 
	        error: function (XMLHttpRequest, data, errorThrown) { 
	        	$.messager.alert('提示','服务器未知错误！'); 
	    	} 
		});
	}
	//根据开关状态，动态显示权限按钮
	if(this.autoSwitch){
		if(typeof(this.oper4Btn) == "object"){
//			console.log("show button list：");
			for(var i=0 ; i<authData.length ; i++){
				for(var name in this.oper4Btn){
					if(authData[i].authCode == name){
//						console.log($("#"+this.oper4Btn[name]).html());
						var ids = this.oper4Btn[name].split(this.split);
						if(null != ids && ids.length > 0){
							if(typeof(this.tbDiv) != "undefined" && this.tbDiv != null && this.tbDiv != ""){
								$(this.tbDiv).css("display","block");
							}
							for(var id in ids){
								$("#"+ids[id]).css("display","inline-block");
								if($("#"+ids[id]).parent().prev().children(".datagrid-btn-separator")){
									$("#"+ids[id]).parent().prev().children(".datagrid-btn-separator").css("display","block");
								}
							}
						}
					}
				}
			}
			
		}
	}
	//调用自定义函数
	if(typeof(this.custom) == "function"){
		this.custom(authData);
	}
}



//var AuthConfig = {
//		getAuth : function (fartherId){
//			var auth = null;
//			if(!fartherId){
//				return auth;
//			}else{
//				$.ajax({
//				    type: "post",  
//				    url: "athrtyConfig.do?method=getUserBtn",
//				    data:{"fartherId":fartherId},
//				    dataType: "json",
//				    async : false,
//				    timeout: 50000,
//				    success: function(data){
//				    	alert(auth);
//				    	auth=data;
//				    	alert(auth);
//				    }, 
//			        error: function (XMLHttpRequest, data, errorThrown) { 
//			        	$.messager.alert('提示','服务器未知错误！'); 
//			    	} 
//				});
//				return auth;
//			}
//		}
//	}

var cacheManage = {
		delCache:function(){
			if(arguments){
				var params = "";
				for(var a in arguments){
					params += arguments[a] + ",";
				}
				params = params.substring(0, params.lastIndexOf(","));
				
				$.messager.confirm('系统提示', '确认要清除该业务所有缓存吗？', function(r){
					if (r){
						//发送post请求
						$.ajax({ 
							url: ctx + '/cacheManage.do?method=delRedisCache',
						   	type: 'POST',
						   	cache: false,
						    data: {"namespace":params},
						    dataType: 'json',
						    timeout: 60000,
						    global: false,
							success: function(data) {
						       	if(data.returnMsg=='200'){
						       		$.messager.alert("系统提示","清理缓存成功！",'info');
						       		//loadDataCount();
						       		//actionSuccessChild();
						       		//$('#tableData').datagrid('reload'); //设置好查询参数 reload 一下就可以了  
						       	}else if(data.returnMsg=='299'){
						       		$.messager.alert("系统提示","清理缓存失败！请稍后重试！",'error');
						       	}else if(data.returnMsg=='201'){
						       		$.messager.alert("系统提示","清理缓存失败！缓存参数有误！",'warning');
						       	}else if(data.returnMsg=='202'){
						       		$.messager.alert("系统提示","清理缓存出错！",'error');
						       	}
							},
							error: function(XMLHttpRequest, textStatus, errorThrown) {
								actionFailureChild(XMLHttpRequest, textStatus, errorThrown);
							}
						});
					}
				});
				
			}else{
				$.messager.alert("系统提示","清理缓存参数有误！",'warning');
			}
		}
}


