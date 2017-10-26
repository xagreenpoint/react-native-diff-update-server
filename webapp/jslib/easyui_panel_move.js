/**
 * 防止panel,dialog,window拖动超出边界
 * @author zyz
 * @date 2013年11月21日
 */
var easyuiPanelOnMove = function(left, top) {
        var parentObj = $(this).panel('panel').parent();
        if (left < 0) {
            $(this).window('move', {
                left : 1
            });
        }
        if (top < 0) {
            $(this).window('move', {
                top : 1
            });
        }
        var width = $(this).panel('options').width;
        var height = $(this).panel('options').height;
        var parentWidth = parentObj.width();
        var parentHeight = parentObj.height();
        if(parentObj.css("overflow")=="hidden"){
            if(left > parentWidth-width){
            	
            	if((parentWidth-width)>=0){
            		$(this).window('move', {
                        "left":parentWidth-width
                    });
            	}
        }
            if(top > parentHeight-$(this).parent().height()){
            	if((parentHeight-$(this).parent().height())>0){
            	
	                $(this).window('move', {
	                    "top":parentHeight-$(this).parent().height()
	                });
            	}
        }
        }
    };
$.fn.panel.defaults.onMove = easyuiPanelOnMove;
$.fn.window.defaults.onMove = easyuiPanelOnMove;
$.fn.dialog.defaults.onMove = easyuiPanelOnMove;