//得到系统日期
function getSysDate(format)
{
	 var s="";       
   var theDate=new Date();       
   s+=theDate.getYear()+"-";//获取年份
   s+=(theDate.getMonth()+1)+"-";//获取月份
   s+=theDate.getDate();//获取日 
   return s;
}

//两字符串日期进行比较
function compareDate(DateOne,DateTwo)
{ 
var OneMonth = DateOne.substring(5,DateOne.lastIndexOf ("-"));
var OneDay = DateOne.substring(DateOne.length,DateOne.lastIndexOf ("-")+1);
var OneYear = DateOne.substring(0,DateOne.indexOf ("-"));
var TwoMonth = DateTwo.substring(5,DateTwo.lastIndexOf ("-"));
var TwoDay = DateTwo.substring(DateTwo.length,DateTwo.lastIndexOf ("-")+1);
var TwoYear = DateTwo.substring(0,DateTwo.indexOf ("-"));
if (Date.parse(OneMonth+"/"+OneDay+"/"+OneYear) >
Date.parse(TwoMonth+"/"+TwoDay+"/"+TwoYear))
{
return true;
}
else
{
return false;
}
}


/*数字验证，只能输入数字*/
function intValidate(obj)
{ 
	if(obj!=null&&obj.value!="")
	{
	  if(!/(^\d+$)|(^\+?\d+$)/.test(obj.value))
	  {
	  				alert("格式不正确，只能输入数字！");
	  				obj.value="";
	  				obj.focus();	  	
	  }
	} 	
}

/*验证值是否为空*/
function chkblank(strvalue)
{
  var i,j,strTemp;
  if ( strvalue.length == 0) return 0;
  for (i=0;i<strvalue.length;i++)
  {
    j = strvalue.charAt(i);
    if (j!=" ")
    {
      return 1;
    }
  }
  return 0;
}
/*数字验证，只能输入正整数和负整数*/
function nanValidate(obj)
{ 
	if(obj!=null&&obj.value!="")
	{
	  if(!/(^-?|^\+?|\d)\d+$/.test(obj.value))
	  {
	  	if(!/^[-]\d+$/.test(obj.value))
	  	{
	  				alert("格式不正确，只能输入正整数和负整数！");
	  				obj.value="";
	  				obj.focus();
	  	}
	  }
	} 
}

//检查是否为空
function isEmpty(val)
{
	if((val=="undefined")||(val==null)||(val=="null")||(val==""))
	{return false;} else return true;
}

$.extend($.fn.validatebox.defaults.rules, {
	fixedLength: {
		validator: function(value, param){ 
			var flag = false;
			if((/^1[3|4|5|8][0-9]\d{8}$/.test(value))){
				flag = true;
			}
			return flag;
		},
		message: '请正确输入11位手机号码'
		}
});

//
$.extend($.fn.validatebox.defaults.rules, {
	numberLength: {
		validator: function(value, param){
		var flag = false;
		if (value.length >= param[0] && value.length<= param[1]){
		   if(/^[0-9]*$/.test(value)){
		      flag = true;
		   }
		}
			return flag;
		},
		message: '请输入4位以上手机号码'
		}
});  


