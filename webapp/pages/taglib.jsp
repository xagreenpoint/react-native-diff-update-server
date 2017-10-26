<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<link rel="stylesheet" type="text/css" href="${ctx}/jslib/jquery-easyui/themes/cupertino/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/jslib/jquery-easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/athrty_ico.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/buttonhref.css">
<script type="text/javascript" src="${ctx}/jslib/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/jslib/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/jslib/easyui_panel_move.js"></script>
<script type="text/javascript" src="${ctx}/jslib/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>

<script src="${ctx}/jslib/jquery.blockUI.js"></script>
<script src="${ctx}/js/blockUI.js"></script>
<!--<script type="text/javascript" src="${ctx}/jslib/window.base.js"></script>-->
<script type="text/javascript">
	var ctx= "${ctx}";
	document.onkeydown = function () {   
		var wdEvent=arguments[0] || window.event;
	    if (wdEvent.keyCode == 8) {
	        if (document.activeElement.readOnly == undefined || document.activeElement.readOnly == true) {   
	           return event.returnValue = false;   
	       }    
	    }   
	   };
	   
	   
	   $.ajaxSetup({  
		    contentType : "application/x-www-form-urlencoded;charset=utf-8",  
		    complete : function(xhr, textStatus) {
		        if (xhr.status == 999) {  
		            window.location = "${ctx}/sessionTimeOut.jsp";//返回应用首页  
		            return;  
		        }  
		    }  
		});  
	   
</script>
<LINK href="${ctx}/css/admin.css" type="text/css" rel="stylesheet">