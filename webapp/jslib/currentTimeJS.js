/**
*获得当前时间:格式例如1990-02-03 11:12:13
**/
function getNowTime()
{
	var myDate = new Date();
	var month = myDate.getMonth() + 1; //取月(从0开始需要加1)
    if (month < 10)
    {
    	month = "0" + month; //月份为一位时前面加0补成2位
    }
    var date = myDate.getDate(); //取日
    if (date < 10)
    {
    	date = "0" + date; //日期为一位时前面加0补成2位
    }
    var hours = myDate.getHours();
    if(hours < 10) {
    	hours = "0" + hours;
    }
    var minutes = myDate.getMinutes();
    if(minutes < 10) {
    	minutes = "0" + minutes;
    }
    var seconds = myDate.getSeconds();
    if(seconds < 10) {
    	seconds = "0" + seconds;
    }
    myDate = myDate.getFullYear()+"-"+month+"-"+date+" "+hours+":"+minutes+":"+seconds;
    return myDate;
}

/**
*获得当前时间:格式例如1990-02-03
**/
function getNowDay()
{
	var myDate = new Date();
	var month = myDate.getMonth() + 1; //取月(从0开始需要加1)
    if (month < 10)
    {
    	month = "0" + month; //月份为一位时前面加0补成2位
    }
    var date = myDate.getDate(); //取日
    if (date < 10)
    {
    	date = "0" + date; //日期为一位时前面加0补成2位
    }
    myDate=myDate.getFullYear()+"-"+month+"-"+date;
    return myDate;
}


function getNowDayaAfter(n) {
	
	var trans_day = "";
	var cur_date = new Date();
	var cur_year = new Date().getFullYear();
	var cur_month = cur_date.getMonth() + 1;
	var real_date = cur_date.getDate();
	cur_month = cur_month > 9 ? cur_month : ("0" + cur_month);
	real_date = real_date > 9 ? real_date : ("0" + real_date);
	eT = cur_year + "-" + cur_month + "-" + real_date;
	if(n >= 1) {
		trans_day = addByTransDate(eT, n);
	}else if (n <= -1) {
		trans_day = reduceByTransDate(eT, -n);
	}else {
		trans_day = eT;
	}
	//处理
	return trans_day;
}

function addByTransDate(dateParameter, num) {
	var translateDate = "", dateString = "", monthString = "", dayString = "";
	translateDate = dateParameter.replace("-", "/").replace("-", "/"); 
	var newDate = new Date(translateDate);
	newDate = newDate.valueOf();
	newDate = newDate + num * 24 * 60 * 60 * 1000;
	newDate = new Date(newDate);
	//如果月份长度少于2，则前加 0 补位 
	if ((newDate.getMonth() + 1).toString().length == 1) {
		monthString = 0 + "" + (newDate.getMonth() + 1).toString();
	} else {
		monthString = (newDate.getMonth() + 1).toString();
	}
	//如果天数长度少于2，则前加 0 补位 
	if (newDate.getDate().toString().length == 1) {
		dayString = 0 + "" + newDate.getDate().toString();
	} else {
		dayString = newDate.getDate().toString();
	}
	dateString = newDate.getFullYear() + "-" + monthString + "-" + dayString;
	return dateString;
}
function reduceByTransDate(dateParameter, num) {
	var translateDate = "", dateString = "", monthString = "", dayString = "";
	translateDate = dateParameter.replace("-", "/").replace("-", "/"); 
	var newDate = new Date(translateDate);
	newDate = newDate.valueOf();
	newDate = newDate - num * 24 * 60 * 60 * 1000;
	newDate = new Date(newDate);
	//如果月份长度少于2，则前加 0 补位 
	if ((newDate.getMonth() + 1).toString().length == 1) {
		monthString = 0 + "" + (newDate.getMonth() + 1).toString();
	} else {
		monthString = (newDate.getMonth() + 1).toString();
	}
	//如果天数长度少于2，则前加 0 补位 
	if (newDate.getDate().toString().length == 1) {
		dayString = 0 + "" + newDate.getDate().toString();
	} else {
		dayString = newDate.getDate().toString();
	}
	dateString = newDate.getFullYear() + "-" + monthString + "-" + dayString;
	return dateString;
} 


/**
*获得当前时间:格式例如1990-02-03
**/
function getFirstDay()
{
	var myDate = new Date();
	var month = myDate.getMonth() + 1; //取月(从0开始需要加1)
    if (month < 10)
    {
    	month = "0" + month; //月份为一位时前面加0补成2位
    }
    var date = "01"; //取日
    myDate=myDate.getFullYear()+"-"+month+"-"+date;
    return myDate;
}

/**
 * 获取上个月的年月:yyyy-mm
 * @returns {String}
 */
function getUpMonth(){
	var t = getNowDay();
    var tarr = t.split('-');
    var year = tarr[0];                //获取当前日期的年
    var month = tarr[1];            //获取当前日期的月
    var day = tarr[2];                //获取当前日期的日
    var days = new Date(year,month,0);   
    days = days.getDate();//获取当前日期中的月的天数

    var year2 = year;
    var month2 = parseInt(month)-1;
    if(month2==0) {
        year2 = parseInt(year2)-1;
        month2 = 12;
    }
    var day2 = day;
    var days2 = new Date(year2,month2,0);
    days2 = days2.getDate();
    if(day2>days2) {
        day2 = days2;
    }
    if(month2<10) {
        month2 = '0'+month2;
    }

    var t2 = year2+'-'+month2;
    return t2;
}

/**
*当前时间的前一天
**/
function getYestoday() {  
	var date = new Date();
    var yesterday_milliseconds = date.getTime()-1000*60*60*24;        
    var yesterday = new Date();        
    yesterday.setTime(yesterday_milliseconds);        
         
    var strYear = yesterday.getFullYear();     
    var strMonth = yesterday.getMonth()+1;   
    var strDay = yesterday.getDate();     
    if(strMonth<10) {     
        strMonth="0"+strMonth;     
    }   
    if(strDay<10) {
    	strDay="0"+strDay;
    }
    var datastr = strYear+"-"+strMonth+"-"+strDay+" "+"00:00:00";
    return datastr;   
} 

/**
*当前时间的前一天,格式yyyy-mm-dd
**/
function getYestoday1() {  
	var date = new Date();
    var yesterday_milliseconds = date.getTime()-1000*60*60*24;        
    var yesterday = new Date();        
    yesterday.setTime(yesterday_milliseconds);        
         
    var strYear = yesterday.getFullYear();     
    var strMonth = yesterday.getMonth()+1;   
    var strDay = yesterday.getDate();     
    if(strMonth<10) {     
        strMonth="0"+strMonth;     
    }   
    if(strDay<10) {
    	strDay="0"+strDay;
    }
    var datastr = strYear+"-"+strMonth+"-"+strDay;
    return datastr;   
} 
