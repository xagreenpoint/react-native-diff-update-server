/**
 * 用于页面操作提示信息
 * @author zyz
 * @version 1.0
 */

/**
 * 操作成功
 */
function actionSuccess(msgInfo){
	if(msgInfo==""||null==msgInfo){
		msgInfo="操作成功！";
	}
	window.parent.$.messager.show( {
		title : "提示信息",
		msg :msgInfo,
		timeout : 3000,
		showType : 'show',
		style:{
			right:'',
			top:document.body.scrollTop+document.documentElement.scrollTop,
			bottom:''
		}
	});	
}

/**
 * 操作失败
 */
function actionFailure(msgInfo){
	if(msgInfo==""||null==msgInfo){
		msgInfo="操作失败！请与系统管理员联系。";
	}
	window.parent.$.messager.alert("错误信息",msgInfo,'error');
}

function actionFailure(XMLHttpRequest, textStatus, errorThrown){
	if(textStatus=="timeout"){
		window.parent.$.messager.alert("错误信息","请求超时！",'error');
	}else if(textStatus=="error"){
		window.parent.$.messager.alert("错误信息","网络连接异常！",'error');
	}
}


function actionSuccessChild(msgInfo){
	if(msgInfo==""||null==msgInfo){
		msgInfo="操作成功！";
	}
	$.messager.show( {
		title : "提示信息",
		msg : msgInfo,
		timeout : 3000,
		showType : 'show',
		style:{
			right:'',
			top:document.body.scrollTop+document.documentElement.scrollTop,
			bottom:''
		}
	});	
}

/**
 * 操作失败
 */
function actionFailureChild(msgInfo){
	if(msgInfo==""||null==msgInfo){
		msgInfo="操作失败！请与系统管理员联系。";
	}
	$.messager.alert("错误信息",msgInfo,'error');
}

function actionFailureChild(XMLHttpRequest, textStatus, errorThrown){
	if(textStatus=="timeout"){
		$.messager.alert("错误信息","请求超时！",'error');
	}else if(textStatus=="error"){
		$.messager.alert("错误信息","网络连接异常！",'error');
	}
}

