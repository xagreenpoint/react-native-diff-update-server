<!--
/**
 * RN包上传
 * 
 * @author linxuchao
 * 
 * @date 2017年06月30日
 */	
-->
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="/pages/taglib.jsp"%>

<jsp:include page="${ctx}/"></jsp:include>
<link href="${ctx}/jslib/fineuploader-3.7.0/fineuploader-3.7.0.min.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${ctx}/jslib/fineuploader-3.7.0/fineuploader-3.7.0.min.js"></script>
<script type="text/javascript" src="${ctx}/jslib/window.base.js"></script>
<script type="text/javascript" src="${ctx}/jslib/validate.js"></script>
<script src="${ctx}/jslib/jquery.blockUI.js"></script>
</head>
<body >
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;border-radius:5px;">
		<form id="tableForm" style="padding-top:40px;">
			<table>
				<tr height="40px;">
					<td align="right" width="150px">
						平台类型：<span style="font-weight: bolder;color: red;">*</span>
					</td>
					<td>
						<input id="platForm" name="platForm" class="easyui-combobox"/>
						<input name="id" id="id" hidden="hidden">	
					</td>
				</tr>
				<tr height="40px;" id="sysClnt">
					<td align="right" width="150px">
						包类型：<span style="font-weight: bolder;color: red;">*</span>
					</td>
					<td>
						<input id="rnPackageType" name="rnPackageType" class="easyui-combobox">
					</td>
				<tr>
				<tr height="40px;">
					<td align="right" width="150px">
						APP版本：<span style="font-weight: bolder;color: red;">*</span>
					</td>
					<td>
						<input name="appVer" id="appVer" class="easyui-validatebox" validType="length[1,20]" missingMessage="必填" required="true"/>
					</td>
				</tr>
				<tr height="40px;">
					<td align="right" width="150px">
						包版本：<span style="font-weight: bolder;color: red;">*</span>
					</td>
					<td>
						<input type="rnPackageVer" name="rnPackageVer" class="easyui-validatebox" validType="length[1,20]" missingMessage="必填" required="true">
					</td>
				</tr>
				<tr height="40px;">
					<td align="right" width="150px">
						上传文件：<span style="font-weight: bolder;color: red;">*</span>
					</td>
					<td>
						<div id="uploadSerialPackage"></div>
						<input type="hidden" name="downLoadUrl" id="downLoadUrl" />
						<input type="hidden" name="rnPackageName" id="rnPackageName" />
					</td>
				</tr>
			</table>
		</form>
		</div>
		<div data-options="region:'east',border:false" style="padding:10px; width: 150px;background-color:#EAF3FE">
		</div>
		<div data-options="region:'north',border:false" style="padding:20px;height: 80px;background-color:#EAF3FE">
			<span style="padding-left: 150px;font-size: 20px;color:#2779AA;font-weight: bolder;">RN包上传</span>
		</div>
		<div data-options="region:'west',border:false" style="padding:10px;width: 150px;background-color:#EAF3FE">
		</div>
		<div data-options="region:'south',border:false" style="text-align:center;padding:20px 0;height:80px;background-color:#EAF3FE">
			<a  class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:saveBtn();" onclick="">确定</a>
		</div>
	</div>
	<div id="wait_dialog" title="系统提示" align="center">
		<table align="center" width="100%" height="100%">
			<tr>
			  	<td align="center" id="wait_info"></td>
		  	</tr>
		 	<tr>
			 	<td align="center" valign="top" id="wait_img"></td>
		  	</tr>
		</table> 
	</div>
	<div id="uploadWin" title="文件上传"  class="easyui-window" data-options="shadow:false,modal:true,minimizable:false,cache:false,maximizable:false,collapsible:false,resizable:false,closed:true" style="width:450px;height:250px;margin: 0px;padding: 0px;overflow: auto;"> 
    	<iframe width="100%" height="100%" id="uploadFrame"></iframe>
	</div>
<script>

$(function(){
	$('#saveBtnId').linkbutton({plain:false });//确定按钮初始化
	$('#cancelBtnId').linkbutton({plain:false });//取消按钮初始化
	$('#platForm').combobox({
		width:160,
		panelWidth:160,
		required:true,
		panelHeight:50,
		valueField:'id',//表示获取值的字段为id
		textField:'text',
		data:[
			{"id":"0","text":"ANDROID","selected":true},
			{"id":"1","text":"IOS"}
		],
		multiple:false,
		editable:false,
		loadMsg:'请稍等...'
	});
    $('#rnPackageType').combobox({
        width:160,
        panelWidth:160,
        required:true,
        panelHeight:50,
        valueField:'id',//表示获取值的字段为id
        textField:'text',
        data:[
            {"id":"0","text":"基础包","selected":true},
            {"id":"1","text":"更新包"}
        ],
        multiple:false,
        editable:false,
        loadMsg:'请稍等...'
    });
    uploader= new qq.FineUploader({
        element: document.getElementById("uploadSerialPackage"),
        request: {
            endpoint: "${ctx}/mngPackageUpload.do?method=packageUpload"
        },
		/*deleteFile: {
            enabled: true, // defaults to false
            endpoint: '${ctx}/mngPackageUpload.do?method=packageDelete'
        },*/
        autoUpload: true,//true是自动上传,并且不显示取消按钮
        multiple: false,
        validation:{
            sizeLimit: 50000000,
            allowedExtensions: ['zip']//设置上传的后缀
        },
        messages : {
            typeError: "文件错误：只允许{extensions}类型的文件。{file}. ",
            sizeError: "文件错误：文件大小不能超过{sizeLimit}字节。{file}.",
            emptyError: "文件错误：不能上传空文件。{file}.",
            onLeave: "文件正在上传，离开将终止上传操作？"
        },
        showMessage: function(message){
            $.messager.alert('提示',message,'info');
        },
        text: {
            uploadButton: '上传包文件',//按钮上的文本
            cancelButton:'取消',
            failUpload:'上传失败'
        },
        callbacks: {
            onComplete: function(id,fileName, responseJSON) {
                console.log(responseJSON);
                if(responseJSON.success==true){
                    //得到返回的路径赋值给隐藏表单域
                    $("#downLoadUrl").val(responseJSON.serialPath);
                    var fileName = responseJSON.fileName;
                    $("#rnPackageName").val(fileName.substring(0,fileName.lastIndexOf('.')));
                }
            },
            onSubmitDelete : function(){
                $("#serialPath").val("");
                $("#fileName").val("");
            }
        }
    });
});


/**
 *保存新增或修改
 */
function saveBtn() {
	if($('#tableForm').form('validate')){
		$.ajax({ url: "${ctx}/mngPackageUpload.do?method=savePackage",
		   	type: 'POST',
		   	cache: false,
		    data: $("#tableForm").serializeArray(),
		    dataType: 'json',
		    timeout: 60000,
		    global: false,
		    //弹出等待提示框
		    beforeSend: function(XMLHttpRequest){
		    	$("#wait_img").html("<img src='${ctx}/images/imp_wait.gif'>");
				$("#wait_info").html("正在提交数据，请稍等...");	   	
				 //提示信息框
				$('#wait_dialog').dialog({
					closable:false,
					modal: true,
					width:400,
					height:200
				});
			},
			//关闭等待提示框
			complete :function(XMLHttpRequest,textStatus){
				
			},
			success: function(data) {
				console.log(data);
				if(data.resultCode=="200"){
					location.reload();
					$.messager.alert("提示",data.resultMsg,'info');
		    	}else{
		    		$.messager.alert("提示",data.resultMsg,'info');
		    		$('#wait_dialog').dialog('close');
		    	}
			 },
			 error: function(XMLHttpRequest, textStatus, errorThrown) {
				 $('#wait_dialog').dialog('close');
			 }
		});
	}
}
</script>
</body>
</html>
