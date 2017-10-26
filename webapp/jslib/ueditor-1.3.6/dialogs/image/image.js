/**
 * Created by JetBrains PhpStorm.
 * User: taoqili
 * Date: 12-01-08
 * Time: 下午2:52
 * To change this template use File | Settings | File Templates.
 */
var imageUploader = {};

(function () {
    var g = $G,
        ajax = parent.baidu.editor.ajax,
        maskIframe = g("maskIframe"); //tab遮罩层,用来解决flash和其他dom元素的z-index层级不一致问题
       // flashObj;                   //flash上传对象

    var flagImg = null;
    imageUploader.init = function (opt, callbacks) {
        switchTab("imageTab");
        createAlignButton(["localFloat"]);
        //createFlash(opt, callbacks);
        addOKListener();
        
        
        if(typeof(editor.options.custImgWidth)!= 'undefined'&&typeof(editor.options.custImgHeight)!= 'undefined'){
        	 //var note="<span style='color:red'>备注：&nbsp;&nbsp;1、图片尺寸"+editor.options.custImgWidth+"×"+editor.options.custImgHeight+"；&nbsp;&nbsp;2、图片大小小于200KB；</span>";
        	var note="<span style='color:red'>备注：图片大小小于200KB；</span>";
        	 $("#msgTips").html(note);
        }else if (typeof(editor.options.custImgMaxWidth)!= 'undefined'&&typeof(editor.options.custImgMaxHeight)!= 'undefined'){//设置图片的最大长宽
        	 var note="<span style='color:red'>备注：图片大小小于200KB；</span>";
        	 $("#msgTips").html(note);
        }
        
        initFineUploader(editor.options.custImgWidth,editor.options.custImgHeight,
        		editor.options.custImgMaxWidth,editor.options.custImgMaxHeight);
    };
    
    //初始化上传组件
    function initFineUploader(custImgWidth,custImgHeight,custImgMaxWidth,custImgMaxHeight){
    	var uploader = new qq.FineUploader({
    		   element: document.getElementById("fileId"),
    		   request: {
	 	          endpoint: "../../../../mngAttachInfo.do?method=uploadFile",
	 	          params: {
	 	        	"moduleCode":"7001",
	 	          	"tableName":"MNG_UEDITOR_ATTACH",
	 	          	"columnName":"ATTACH_ID",
	 	          	"width":'',
	 	          	"height":'',
	 	          	"maxSize":200000,
	 	          	"maxWdith":custImgMaxWidth,
	 	          	"maxHeight":custImgMaxHeight
	 	          }
    		   },
    		   multiple: false,
    		   autoUpload: true,//true是自动上传,并且不显示取消按钮
    		   validation: {
    		     	allowedExtensions: ['jpg', 'jpeg', 'png', 'gif'],//设置上传的后缀
    		     	sizeLimit : 200000	//1000等于1KB，100KB
    		   },
    		   messages : {
    		        typeError : "文件错误：只允许{extensions}类型的文件。{file}. ",  
    		        sizeError : "文件错误：文件大小不能超过{sizeLimit}字节。{file}.",  
    		        emptyError : "文件错误：不能导入空文件。{file}.",  
    		        onLeave : "文件正在导入，离开将终止导入操作？"
    		   },
    		   showMessage: function(message){
    		        alert(message);
    		   },
    		   text:{
    		       	uploadButton: '上传',//按钮上的文本
    		       	cancelButton:'取消',
    		       	failUpload:'上传失败'
    		   },
    		   callbacks:{
    			   onComplete: function(id,fileName, responseJSON){
    				   if(responseJSON.success){
    					   //responseJSON.attachId
    					   //responseJSON.url
    					   var s = "<img src=\""+responseJSON.url+"\" width='150px' height='150px' />";
    					   
    					   $("#imgContainer").html(s);
    					   
    					   //回调，目前只支持插入一张图片
    					   imageUrls = new Array();
    					   
    					   imageUrls.push({
    						   "attachId":responseJSON.attachId,
    						   "url":responseJSON.url
    					   });
    				   }else{
		  	        		if(responseJSON.errorCode=='202'){
		  	        			alert("文件错误：文件大小不能超过200KB。"+fileName);
		  	        		}
		  	        		if(responseJSON.errorCode=='203'){
		  	        			alert("文件错误：文件尺寸不符合要求。"+fileName);
		  	        		}
    				   }
   		   		   }
    		   } 
     	});
    }
    
    
    /*ue.IssueInfo = {
    	"issueId" : "", //issueType为新增时，置为null或空字符串
    	"issueTable" : "", //
    	"issueColumn" : "", //
    	"moduleCode" : "" //
    };*/
    var issueInfo;
    
    /**
     * 绑定确认按钮
     */
    function addOKListener() {
        dialog.onok = function () {
            var currentTab = findFocus("tabHeads", "tabSrc");
            /*//后续新增或修改富文本图片附件信息
            if(editor.IssueInfo){
            	issueInfo = editor.IssueInfo;
            	//如果issueId不为null或""，则为修改
            	if(issueInfo.issueId && null != issueInfo.issueId && "" != issueInfo.issueId){
            		
            	}
            	//否则为新增
            	else{
            		
            	}
            }*/
            
            //插入图片
            insertBatch();
        };
        dialog.oncancel = function () {
        	//alert(editor.getContent());
        }
    }


    /**
     * 检测传入的所有input框中输入的长宽是否是正数
     * @param nodes input框集合，
     */
    function checkNum(nodes) {
        for (var i = 0, ci; ci = nodes[i++];) {
            if (!isNumber(ci.value) || ci.value < 0) {
                alert(lang.numError);
                ci.value = "";
                ci.focus();
                return false;
            }
        }
        return true;
    }

    /**
     * 数字判断
     * @param value
     */
    function isNumber(value) {
        return /(0|^[1-9]\d*$)/.test(value);
    }

    /**
     * 插入多张图片
     */
    function insertBatch() {
        if (imageUrls.length < 1) return;
        var imgObjs = [],
            align = findFocus("localFloat", "name");
        for (var i = 0, ci; ci = imageUrls[i++];) {
            var tmpObj = {};
            //tmpObj.title = ci.title;
            tmpObj.floatStyle = align;
            //修正显示时候的地址数据,如果后台返回的是图片的绝对地址，那么此处无需修正
            //tmpObj._src = tmpObj.src = editor.options.imagePath + ci.url;
            tmpObj.src=ci.url;
            tmpObj.id = ci.attachId;  
//            tmpObj.style="width:100%;height:auto;";
            imgObjs.push(tmpObj);
           
        }
        insertImage(imgObjs);
    }
    
    function insertImage(imgObjs) {
        editor.fireEvent('beforeInsertImage', imgObjs);
        editor.execCommand("insertImage", imgObjs);
    }

    /**
     * 找到id下具有focus类的节点并返回该节点下的某个属性
     * @param id
     * @param returnProperty
     */
    function findFocus(id, returnProperty) {
        var tabs = g(id).children,
            property;
        for (var i = 0, ci; ci = tabs[i++];) {
            if (ci.className == "focus") {
                property = ci.getAttribute(returnProperty);
                break;
            }
        }
        return property;
    }


    /**
     * 绑定图片等比缩放事件
     * @param percent  缩放比例
     */
    function addSizeChangeListener(percent) {
        var width = g("width"),
            height = g("height"),
            lock = g('lock');
        width.onkeyup = function () {
            if (!isNaN(this.value) && lock.checked) {
                height.value = Math.round(this.value / percent) || this.value;
            }
        };
        height.onkeyup = function () {
            if (!isNaN(this.value) && lock.checked) {
                width.value = Math.round(this.value * percent) || this.value;
            }
        }
    }

    

    /**
     * 依据传入的align值更新按钮信息
     * @param align
     */
    function updateAlignButton(align) {
        var aligns = g("remoteFloat").children;
        for (var i = 0, ci; ci = aligns[i++];) {
            if (ci.getAttribute("name") == align) {
                if (ci.className != "focus") {
                    ci.className = "focus";
                }
            } else {
                if (ci.className == "focus") {
                    ci.className = "";
                }
            }
        }
    }

    /**
     * 创建图片浮动选择按钮
     * @param ids
     */
    function createAlignButton(ids) {
        for (var i = 0, ci; ci = ids[i++];) {
            var floatContainer = g(ci),
                nameMaps = {"none":lang.floatDefault, "left":lang.floatLeft, "right":lang.floatRight, "center":lang.floatCenter};
            for (var j in nameMaps) {
                var div = document.createElement("div");
                div.setAttribute("name", j);
                //add by tianyangyang 2015-3-14 默认选中居中
                if (j == "center") div.className = "focus";

                div.style.cssText = "background:url(images/" + j + "_focus.jpg);";
                div.setAttribute("title", nameMaps[j]);
                floatContainer.appendChild(div);
            }
            switchSelect(ci);
        }
    }

    /**
     * tab点击处理事件
     * @param tabHeads
     * @param tabBodys
     * @param obj
     */
    function clickHandler(tabHeads, tabBodys, obj) {
        //head样式更改
        for (var k = 0, len = tabHeads.length; k < len; k++) {
            tabHeads[k].className = "";
        }
        obj.className = "focus";
        //body显隐
        var tabSrc = obj.getAttribute("tabSrc");
        for (var j = 0, length = tabBodys.length; j < length; j++) {
            var body = tabBodys[j],
                id = body.getAttribute("id");
            body.onclick = function () {
                this.style.zoom = 1;
            };
            if (id != tabSrc) {
                body.style.zIndex = 1;
            } else {
                body.style.zIndex = 200;
                //当切换到本地图片上传时，隐藏遮罩用的iframe
                if (id == "local") {
                    maskIframe.style.display = "none";
                } else {
                    maskIframe.style.display = "";
                    dialog.buttons[0].setDisabled(false);
                }
            }
        }

    }

    /**
     * TAB切换
     * @param tabParentId  tab的父节点ID或者对象本身
     */
    function switchTab(tabParentId) {
        var tabElements = g(tabParentId).children,
            tabHeads = tabElements[0].children,
            tabBodys = tabElements[1].children;

        for (var i = 0, length = tabHeads.length; i < length; i++) {
            var head = tabHeads[i];
            if (head.className === "focus")clickHandler(tabHeads, tabBodys, head);
            head.onclick = function () {
                clickHandler(tabHeads, tabBodys, this);
            }
        }
    }

    /**
     * 改变o的选中状态
     * @param o
     */
    function changeSelected(o) {
        if (o.getAttribute("selected")) {
            o.removeAttribute("selected");
            o.style.cssText = "filter:alpha(Opacity=100);-moz-opacity:1;opacity: 1;border: 2px solid #fff";
        } else {
            o.setAttribute("selected", "true");
            o.style.cssText = "filter:alpha(Opacity=50);-moz-opacity:0.5;opacity: 0.5;border:2px solid blue;";
        }
    }

    /**
     * 选择切换，传入一个container的ID
     * @param selectParentId
     */
    function switchSelect(selectParentId) {
        var select = g(selectParentId),
            children = select.children;
        domUtils.on(select, "click", function (evt) {
            var tar = evt.srcElement || evt.target;
            for (var j = 0, cj; cj = children[j++];) {
                cj.className = "";
                cj.removeAttribute && cj.removeAttribute("class");
            }
            tar.className = "focus";

        });
    }

})();
