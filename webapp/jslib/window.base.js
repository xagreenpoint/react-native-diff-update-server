/**
 * 继承window重写相关参数
 * 
 * @author tianyangyang
 * 
 * @date 2014-7-17
 */
$.extend($.fn.window.defaults,{   
      minimizable:false,//定义是否显示最小化按钮。
	  maximizable:false,//定义是否显示最大化按钮。
	  closable:true,//定义是否显示关闭按钮。
	  collapsible:false,//定义控制面板初始化时是否折叠。
	  draggable : true,//定义窗口是否可以被拖放。
	  inline:false,//定义如何布局窗口，如果设置为true，窗口将显示在它的父容器中，否则将显示在所有元素的上面。
	  modal:true,
	  onOpen:function(){
		var parentObj =$(this).panel('panel').parent();
		var width = $(this).panel('options').width;
	    var height = $(this).panel('options').height;
	    var parentWidth = parentObj.width();
	    var parentHeight = parentObj.height();
	    if(width>parentWidth||height>parentHeight){
	    	$(this).window('maximize');
	    }
	  },
	  //add by lixuming 2015-1-28 window弹出之前,让panel获得焦点。
	  onBeforeOpen:function(){
		  $(this).panel('panel').parent().focus();
		  return true;
	  }
});
/**
 * 继承datagrid重写相关参数
 * 
 * @author tianyangyang
 * 
 * @date 2014-7-21
 */
$.extend($.fn.datagrid.defaults, { 
	fit:true,
	fitColumns:true,//自动调整各列，用了这个属性，下面各列的宽度值就只是一个比例。
	nowrap: true,//True 就会把数据显示在一行里。
	striped: true,//奇偶行颜色不同 
	pagination:true,
	rownumbers:true,
	pageSize : 20,
	pageList : [ 20, 30, 50, 100 ]
});
