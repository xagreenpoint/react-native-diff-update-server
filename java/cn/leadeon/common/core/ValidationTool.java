package cn.leadeon.common.core;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import cn.leadeon.common.annotion.CodeMapping;
import cn.leadeon.common.annotion.IsInteger;
import cn.leadeon.common.annotion.StrVerify;
import cn.leadeon.common.response.ResponseEnum;

public  class  ValidationTool {

	
	
	/** 
     * 判断是否为浮点数或者整数 
     * @param str 
     * @return true Or false 
     */  
    public static boolean isNumeric(String str){  
          if( !str.matches("^(-?\\d+)(\\.\\d+)?$") ){  
                return false;  
          }  
          return true;  
    }  
      
    /** 
     * 判断是否为正确的邮件格式 
     * @param str 
     * @return boolean 
     */  
    public static boolean isEmail(String str){  
        if(StringUtils.isNotBlank(str))  
            return false;  
        return str.matches("^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$");  
    }  
      
    /** 
     * 判断是否为正确的邮件格式 (邮件可为空)
     * @param str 
     * @return boolean 
     */  
    public static boolean hasEmail(String str){  
        if(str.matches("^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$")){
        	return true; 
        }  
            return false;  
    }  
    /** 
     * 判断字符串是否为移动手机号 
     * @param str 
     * @return boolean 
     */  
    public static boolean isCMCCMobile(String str){  
        if(StringUtils.isNotBlank(str))  
            return false;  
        return str.matches("^0{0,1}(13[4-9]|15[7-9]|15[0-2]|18[7-8]|18[2])[0-9]{8}$");  
    }  
      
    
    
    /** 
     * 判断字符串是否为手机号 
     * @param str 
     * @return boolean 
     */  
    public static boolean isMobile(String str){  
        if(StringUtils.isBlank(str))  
            return false;  
        return str.matches("^1[3-8][0-9]{9}$");  
    }  
    
    /** 
     * 判断是否为数字 
     * @param str 
     * @return 
     */  
    public static boolean isNumber(String str) {
    	if(StringUtils.isNumeric(str))
    		return true;
    	return false;
    }  
      
  
    /**
	 * 判断是否为UTF8的中文字符
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isUTF8Chinese(String str) {
		if (str == null) {
			return false;
		}
		char UTR8_MAX_VALUE = '\u9fa5';
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (str.charAt(i) > UTR8_MAX_VALUE) {
				return false;
			}
		}
		return true;
	}
	
	 /** 
     * 判断字符串是否非法字符
     * @param str 
     * @return boolean 
     */  
    public static boolean isIllegal(String str){
    	
    	String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx); 
		 Matcher m = p.matcher(str);
		 if (m.find()) {
			 return true;
		 }else {
			 return false;
		 }
    }
    
	 /** 
     * 判断字符串是否非法字符(忽略给定字符)
     * @param str 
     * @return boolean 
     */  
    public static boolean isIllegal(String str,String [] ignoreStr){
    	
    	String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
    	if(null!=ignoreStr&&ignoreStr.length>0){
    		for(int i=0;i<ignoreStr.length;i++){
        		regEx=regEx.replace(ignoreStr[i],"");
        	}
    	}
		Pattern p = Pattern.compile(regEx); 
		 Matcher m = p.matcher(str);
		 if (m.find()) {
			 return true;
		 }else {
			 return false;
		 }
    }
    /** 
     * 判断字符串是否为double类型 
     * @param str 
     * @return boolean 
     */  
    public static boolean isDoubleStr(String str){  
        if(StringUtils.isBlank(str))  
            return false;  
        return str.matches("[-+]?[0-9]+(\\.[0-9]+)?");   
    }  
	public static CodeMapping validationField(Object obj){
		CodeMapping res = new CodeMapping();
		Field[] fields =  obj.getClass().getDeclaredFields();
		for(Field field : fields){
			field.setAccessible(true);
			Object o=null;
			try {
				o = field.get(obj);
			} catch (Exception e1) {
			}
			
			try{
				IsInteger isint = field.getAnnotation(IsInteger.class);
				if(isint!=null){
					
					//modify start by xuexiaohua at 2015-05-26 兼容当枚举类型字段值转为int类型值连续，且值允许为空时的判断校验逻辑
					if(isint.isNull() && null == o){
						
					}
					else{
						if (StringUtils.isEmpty(o.toString())) {
							res.setCodeNumber(ResponseEnum.NOTNULL_ERROR.getCode());
							res.setCodeDesc("字段名: " + field.getName() + " 不能为空!");
							return res;
						}
						
						if(!isNumber(o.toString())){
							res.setCodeNumber(ResponseEnum.ISNUMBER_ERROR.getCode());
							res.setCodeDesc("字段名: " + field.getName() + " 不是数字!");
							return res;
						}
						
						
						if(Integer.parseInt(o.toString())<isint.min()){
							res.setCodeNumber(ResponseEnum.LENGTH_ERROR.getCode());
							res.setCodeDesc("字段名: " + field.getName() + " 取值非法! ");
							return res;
						}
						if(Integer.parseInt(o.toString())>isint.max()){
							res.setCodeNumber(ResponseEnum.LENGTH_ERROR.getCode());
							res.setCodeDesc("字段名: " + field.getName() + " 取值非法! ");
							return res;
						}
					}
				}
				StrVerify sv = field.getAnnotation(StrVerify.class);
				if(sv!=null){
					if(sv.isNotNull()){
						if(StringUtils.isBlank(o.toString())){
							res.setCodeNumber(ResponseEnum.NOTNULL_ERROR.getCode());
							res.setCodeDesc("字段名: " + field.getName() + " 不能为空!");
							return res;
						}
					}
					
					if(o!=null &&  StringUtils.isNotBlank(o.toString())){
						if(sv.isNotIllegal()){
							if(null!=sv.ignoreIllegal()&&sv.ignoreIllegal().length>0 ){//忽略给定非法字符
								if(isIllegal(o.toString(),sv.ignoreIllegal())){
									res.setCodeNumber(ResponseEnum.ISNOTILLEGAL_ERROR.getCode());
									res.setCodeDesc("字段名: " + field.getName() + " 含有非法字符! ");
									return res;
								}
							}else{
								if(isIllegal(o.toString())){
									res.setCodeNumber(ResponseEnum.ISNOTILLEGAL_ERROR.getCode());
									res.setCodeDesc("字段名: " + field.getName() + " 含有非法字符! ");
									return res;
								}
							}
						}
						if(o.toString().length() < sv.minLength()){
							res.setCodeNumber(ResponseEnum.LENGTH_ERROR.getCode());
							res.setCodeDesc("字段名: " + field.getName() + " 长度非法! ");
							return res;
						}
						if(o.toString().length() > sv.maxLength()){
							res.setCodeNumber(ResponseEnum.LENGTH_ERROR.getCode());
							res.setCodeDesc("字段名: " + field.getName() + " 长度非法! ");
							return res;
						}
						
						if(sv.isNumber() && !StringUtils.isNumeric(o.toString())){
							res.setCodeNumber(ResponseEnum.ISNUMBER_ERROR.getCode());
							res.setCodeDesc("字段名: " + field.getName() + " 不是数字!");
							return res;
						}
						if(sv.isNumber() && !o.toString().matches("^[0-9]*[1-9][0-9]*$") && sv.isPage()){
							res.setCodeNumber(ResponseEnum.ISNUMBER_ERROR.getCode());
							res.setCodeDesc("字段名: " + field.getName() + " 不能为小数或者零!");
							return res;
						}
						if(o.toString().contains(",")&&o.toString().contains("[")&&o.toString().contains("]")&&sv.isBigStr()==true){//检验字符串中含有特殊字符，比如["","",""...]
							
							String subStr = o.toString().substring(1, o.toString().length()-1);
							String[] splitStr = subStr.split(",");
							for(int i=0;i<splitStr.length;i++){
									if(Pattern.compile("[0-9]*").matcher(splitStr[i]).matches() ==false||sv.maxLength()>1024){
										res.setCodeNumber(ResponseEnum.IDBIGSTR_ERROR.getCode());
										res.setCodeDesc("字段名: " + field.getName() + "中第"+(i+1)+"个数据不合法!");
										break; 
									}else{
										res.setCodeNumber(ResponseEnum.RES_SUCCESS.getCode());
										res.setCodeDesc("SUCCESS");
								}
							}
							return res;
						}
					}
				}
				
			}catch(Exception e){
				res.setCodeNumber(ResponseEnum.RES_PARAM_ERROR.getCode());
				res.setCodeDesc("请合理请求!");
				e.printStackTrace();
				return res;
			}
		}
		res.setCodeNumber(ResponseEnum.RES_SUCCESS.getCode());
		res.setCodeDesc("SUCCESS");
		return res;
	}
	
	

    private ValidationTool(){} 
    
}
