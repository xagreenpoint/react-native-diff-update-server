var websocket = null;
//获取主机地址之后的目录
var pathName = window.document.location.pathname;
//获取带"/"的项目名
var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
var target='ws://' + window.location.host+projectName+'/WebSocketServer';
//判断当前浏览器是否支持WebSocket
if ('WebSocket' in window) {  
	websocket = new WebSocket(target);  
} else if ('MozWebSocket' in window) {  
	websocket = new MozWebSocket(target);  
} else {  
	//alert('请使用高版本浏览器');  
	//console.log("浏览器版本过低!")
	try{
		setInterval(function () {
			msgCenterInitialize();
		}, 5000); // 单位: 毫秒, 1000 = 1 秒
	} catch(err) {
		//console.log("刷新失败！"+err);
	}
}  	
//连接发生错误的回调方法
websocket.onerror = function(){
	//console.log("websocket connection error !");
	try{
		setInterval(function () {
			msgCenterInitialize();
		}, 5000); // 单位: 毫秒, 1000 = 1 秒
	} catch(err) {
		//console.log("刷新失败！"+err);
	}
}
//连接成功建立的回调方法
websocket.onopen = function(){
    // 发送一个初始化消息
	//console.log("websocket connection success !");
}
//连接关闭的回调方法
websocket.onclose = function(){
	//console.log("websocket connection close !");
}

//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
window.onbeforeunload = function(){
	websocket.close();
}
