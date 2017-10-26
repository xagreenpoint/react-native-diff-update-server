/**
 * 记录页面左边菜单点击Log和登录、退出系统的Log的公用JS
 * @author wanglanglang
 * @date 2015-05-05
 * @version V2.1
 */
var systemLog = {
	addLog : function(moduleName, operation) {
		$.ajax({
		    type : "post",  
		    url : ctx + "/sysLog.do?method=add",
		    async : true,
		    data : {"moduleName":moduleName,"operation":operation}
		});
	}
}