/**
 * 用于页面限制富文本编辑器长度
 * @author zyz
 * @version 1.0
 * @date 2014年1月22日
 */
/*
txt:要限制的文本框
show:要展示剩余可输入的数量的文本框
limit:限制数量
isbyte:true：按字节限制；false：按字符限制
*/
function lim(txt,show,limit,isbyte){
	var t=$("#"+txt);
	
	
	
	var count=getLen(t,isbyte);
	if(count>limit){
		$("#"+show).html('<span style="color:red;">已超出'+(count-limit)+'个字，请删除多余的字！</span>');
		return false;
	}else{
		$("#"+show).html('<span style="color:red;">还可以再输入'+(limit-count)+'个字</span>');
		return true;
	}
}
/*
txt:要限制的文本框
isbyte:true：按字节限制；false：按字符限制
*/
function getLen(txt,isbyte){
	var c=(isbyte)?$(txt).val().replace(/[^\u0000-\u00ff]/g,"aaa").length:$(txt).val().length;
return c;
}

/**
 * 用于页面限制富文本编辑器长度
 * @author tianyangyang
 * @version 1.0
 * @date 2014年1月22日
 */
/*
txt:要检验的内容
show:要展示剩余可输入的数量的文本框
limit:限制数量
isbyte:true：按字节限制；false：按字符限制
*/
function lims(txt,show,limit,isbyte){	
	var count=getLens(txt,isbyte);
	if(count>limit){
		$("#"+show).html('<span style="color:red;">已超出'+(count-limit)+'个字，请删除多余的字！</span>');
		return false;
	}else{
		$("#"+show).html('<span style="color:red;">还可以再输入'+(limit-count)+'个字</span>');
		return true;
	}
}
/*
txt:要检验的内容
isbyte:true：按字节限制；false：按字符限制
*/
function getLens(txt,isbyte){
	var c=(isbyte)?txt.replace(/[^\u0000-\u00ff]/g,"aaa").length:txt.length;
	return c;
}
