var footerView = $.extend({}, $.fn.datagrid.defaults.view, {
	renderFooter: function(target, container, frozen) {   
        var opts = $.data(target, "datagrid").options;   
        //获取footer数据   
        var rows = $.data(target, "datagrid").footer || [];   
        var columnsFields = $(target).datagrid("getColumnFields", frozen);   
        //生成footer区的table   
        var footerTable = ["<table class=\"datagrid-ftable\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tbody>"];   
        for(var i = 0; i < rows.length; i++) {   
            footerTable.push("<tr class=\"datagrid-row\" datagrid-row-index=\"" + i + "\">");   
            footerTable.push(this.renderRowTest.call(this, target, columnsFields, frozen, i, rows[i]));   
            footerTable.push("</tr>");   
        }   
        footerTable.push("</tbody></table>");   
        $(container).html(footerTable.join(""));   
   	},
    renderRowTest: function(target, fields, frozen, rowIndex, rowData) {   
        var opts = $.data(target, "datagrid").options;   
        //用于拼接字符串的数组   
        var cc = [];   
        if(frozen && opts.rownumbers) {   
            //rowIndex从0开始，而行号显示的时候是从1开始，所以这里要加1.   
            var rowNumber = rowIndex + 1;   
            //如果分页的话，根据页码和每页记录数重新设置行号   
            if(opts.pagination) {   
                rowNumber += (opts.pageNumber - 1) * opts.pageSize;   
            }   
            cc.push("<td class=\"datagrid-td-rownumber\"><div class=\"datagrid-cell-rownumber\">" + rowNumber + "</div></td>");   
        }
        var colWidth=0;
        //footer显示字段
        var footerShowField=$.data(target, "datagrid").options.footerShowField;
        //footer隐藏字段用于计算宽度
        var footerHideField=$.data(target, "datagrid").options.footerHideField;
        //合并列字段
        var clospanField=$.data(target, "datagrid").options.clospanField;
        //计算合并列宽度
        for(var i=0;i<footerHideField.length;i++){
        	for(var j = 0; j < fields.length; j++) {
        		 var field = fields[j];   
            	 var col = $(target).datagrid("getColumnOption", field);
            	 if(col.field==footerHideField[i]){
            	 	colWidth+=col.width;
            	 }
        	} 
        }
//	    //开始写数据      
	    for(var i = 0; i < fields.length; i++) { 
            var field = fields[i];   
            var col = $(target).datagrid("getColumnOption", field); 
            for(var j=0;j<footerShowField.length;j++){
            	if(col.field==footerShowField[j]){
            		if(col){
        	            var value = rowData[field];   
                        //获取用户定义的单元格样式，入参包括：单元格值，当前行数据，当前行索引(从0开始)   
                        var style = col.styler ? (col.styler(value, rowData, rowIndex) || "") : "";   
                        //如果是隐藏列直接设置display为none，否则设置为用户想要的样式   
                        var styler = col.hidden ? "style=\"display:none;" + style + "\"" : (style ? "style=\"" + style + "\"" : "");
                        if(col.field==clospanField){
                        	cc.push("<td width=\""+colWidth+"\" field=\"" + field + "\" " + styler + ">");
                        }else{
                        	cc.push("<td field=\"" + field + "\" " + styler + ">");   
                        }   
                        //如果当前列是datagrid组件保留的ck列时，则忽略掉用户定义的样式，即styler属性对datagrid自带的ck列是不起作用的。   
                        if(col.checkbox) {   
                            var styler = "";   
                        }else {   
                            var styler = "";   
                            //设置文字对齐属性   
                            if(col.align) {   
                                styler += "text-align:" + col.align + ";";   
                            }   
                            //设置文字超出td宽时是否自动换行(设置为自动换行的话会撑高单元格)   
                            if(!opts.nowrap) {   
                                styler += "white-space:normal;height:auto;";   
                            } else {   
                                if(opts.autoRowHeight) {   
                                    styler += "height:auto;";   
                                }   
                            }   
                        }   
                        if(col.field==clospanField){
                        	styler += "width:100%";    
                        }
                        //这个地方要特别注意，前面所拼接的styler属性并不是作用于td标签上，而是作用于td下的div标签上。   
                        cc.push("<div style=\"" + styler + "\" ");   
                        //如果是ck列，增加"datagrid-cell-check"样式类   
                        if(col.checkbox) {   
                            cc.push("class=\"datagrid-cell-check ");   
                        }   
                        //如果是普通列，增加"datagrid-cell-check"样式类   
                        else {   
                            cc.push("class=\"datagrid-cell " + col.cellClass);   
                        }   
                        cc.push("\">");   
                        /**  
                         * ck列光设置class是不够的，当突然还得append一个input进去才是真正的checkbox。此处未设置input的id，只设置了name属性。  
                         * 我们注意到formatter属性对datagird自带的ck列同样不起作用。  
                         */  
                        if(col.checkbox) {   
                            cc.push("<input type=\"checkbox\" name=\"" + field + "\" value=\"" + (value != undefined ? value : "") + "\"/>");   
                        }   
                        //普通列   
                        else {   
                            /**  
                             * 如果单元格有formatter，则将formatter后生成的DOM放到td>div里面  
                             * 换句话说，td>div就是如来佛祖的五指山，而formatter只是孙猴子而已，猴子再怎么变化翻跟头，始终在佛祖手里。  
                             */  
                            if(col.formatter) {   
                                cc.push(col.formatter(value, rowData, rowIndex));   
                            }   
                            //操，这是最简单的简况了，将值直接放到td>div里面。   
                            else {   
                                cc.push(value);   
                            }   
                        }   
                        cc.push("</div>");   
                        cc.push("</td>");   
        	          }
            	}
            }
	   }   
	   //返回单元格字符串，注意这个函数内部并未把字符串放到文档流中。   
	   return cc.join("");   
  }  
});

