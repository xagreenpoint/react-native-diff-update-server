// 格式化日期
function toDate(str) {
	var sd = str.split("-");
	return new Date(sd[0], (sd[1]-1), sd[2]);
}
// 日期比较
function checkDate(startDate, endDate) {
	var d1 = toDate(startDate);
	var d2 = toDate(endDate);
	return d1 <= d2;
}
// 时间比较
function compareTime(startDate, endDate) {
	var startDateTemp = startDate.split(" ");
	var endDateTemp = endDate.split(" ");

	var arrStartDate = startDateTemp[0].split("-");
	var arrEndDate = endDateTemp[0].split("-");

	var arrStartTime = startDateTemp[1].split(":");
	var arrEndTime = endDateTemp[1].split(":");

	var allStartDate = new Date(arrStartDate[0], (arrStartDate[1]-1),
			arrStartDate[2], arrStartTime[0], arrStartTime[1],
			arrStartTime[2]);
	var allEndDate = new Date(arrEndDate[0], (arrEndDate[1]-1), arrEndDate[2],
			arrEndTime[0], arrEndTime[1], arrEndTime[2]);
	
	return allStartDate.getTime()<allEndDate.getTime()
	
}

//两字符串日期进行比较
function compareDate(DateOne,DateTwo){ 
	var OneMonth = DateOne.substring(5,DateOne.lastIndexOf ("-"));
	var OneDay = DateOne.substring(DateOne.length,DateOne.lastIndexOf ("-")+1);
	var OneYear = DateOne.substring(0,DateOne.indexOf ("-"));
	var TwoMonth = DateTwo.substring(5,DateTwo.lastIndexOf ("-"));
	var TwoDay = DateTwo.substring(DateTwo.length,DateTwo.lastIndexOf ("-")+1);
	var TwoYear = DateTwo.substring(0,DateTwo.indexOf ("-"));
	if ((Date.parse(OneMonth+"/"+OneDay+"/"+OneYear) >=Date.parse(TwoMonth+"/"+TwoDay+"/"+TwoYear) || (DateOne==""&&DateTwo==""))){
			return true;
	}else{
		return false;
	}
}


// 判断是否是中文
function isChinese(str) {
	var lst = /[^\u4E00-\u9FA5]/;
	return !lst.test(str);
}

function strlen(str) {
	var strlength = 0;
	for (i = 0; i < str.length; i++) {
		if (isChinese(str.charAt(i)) == true) {
			strlength = strlength + 3;
		} else {
			strlength = strlength + 1;
		}
	}
	return strlength;
}

$.extend($.fn.validatebox.defaults.rules, {
	maxLength : {
		validator : function(value, param) {
			return  value.length<=param[0];
		},
		message : '限{0}字符'
	}
});

$.extend($.fn.validatebox.defaults.rules, {   
    checkCombox: {   
	    validator: function(value,param){
	    	var flag =false;  
			$.each($(param[0]).combobox('getData'),function(index,obj){
				if(value==obj.text){
					flag = true;
					return false;
				}
			});
			if(!flag){
				return '';
			}
	        return value;   
	    },   
	    message: '请从下拉框中选择！'
    }   
});

$.extend($.fn.validatebox.defaults.rules, {   
    checkDataGrid: {   
    validator: function(value,param){
    	var textName=param[1];
    	var flag =false;  
    	var grid =$(param[0]).combogrid('grid');
    	var rows=grid.datagrid('getRows');
		$.each(grid.datagrid('getRows'),function(index,obj){
			if(value==obj[textName]){
				flag = true;
				return false;
			}
   		});
   		return flag;
    },   
    message: '请从下拉框中选择！'
}
}); 
//中文字数限制
$.extend($.fn.validatebox.defaults.rules, {
	chineseLength: {
		validator : function(value, param) {
			return  strlen(value)<=(param[0]*3);
		},
		message : '限{0}个汉字'
	},
	chinese: {// 验证中文
        validator: function (value) {
            return /^[\Α-\￥]+$/i.test(value);
        },
        message: '请输入汉字'
    },
	chineseAndLength: {// 验证中文
        validator: function (value, param) {
        	var boolean = /^[\Α-\￥]+$/i.test(value)
        	if(boolean){
        		boolean = strlen(value)<=(param[0]*3);
        	}
            return boolean;
        },
        message: '请输入{0}汉字'
    }
});
//11位手机号码
$.extend($.fn.validatebox.defaults.rules, {
	fixedLength: {
		validator: function(value, param){ 
			var flag = false;
			if((/^[0-9]\d{10}$/.test(value))){
				flag = true;
			}
			return flag;
		},
		message: '请正确输入11位手机号码'
	}
});

//邮箱格式校验
$.extend($.fn.validatebox.defaults.rules, {
	email: {
		validator: function(value, param){ 
			var flag = false;
			if((/^[A-Za-z0-9]+([-_.][A-Za-z0-9]+)*@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/.test(value)) && value.length <= 50){
				flag = true;
			}
			return flag;
		},
		message: '请输入50位以内的正确Email邮箱'
	}
});
