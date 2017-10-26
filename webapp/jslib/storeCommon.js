/**
 * 
 */
var storeCommon = {
		
		goodsType:[
		 {
			"goodsTypeCode" : "",
			"goodsTypeName" : "---请选择---"
		 }
		 ,{
			"goodsTypeCode" : "70000",
			"goodsTypeName" : "终端"
		},
		{
			"goodsTypeCode" : "50000",
			"goodsTypeName" : "配件"
		},
		{
			"goodsTypeCode" : "30000",
			"goodsTypeName" : "套餐"
		},
		{
			"goodsTypeCode" : "30100",
			"goodsTypeName" : "资费套餐"
		},
		{
			"goodsTypeCode" : "30200",
			"goodsTypeName" : "4G自选套餐"
		},
		{
			"goodsTypeCode" : "30300",
			"goodsTypeName" : "4G套餐"
		},
		{
			"goodsTypeCode" : "40000",
			"goodsTypeName" : "增值业务"
		},
		{
			"goodsTypeCode" : "40100",
			"goodsTypeName" : "流量套餐"
		},
		{
			"goodsTypeCode" : "40200",
			"goodsTypeName" : "数据业务"
		},
		{
			"goodsTypeCode" : "40300",
			"goodsTypeName" : "优惠业务包"
		},
		{
			"goodsTypeCode" : "40400",
			"goodsTypeName" : "积分兑换"
		},
		{
			"goodsTypeCode" : "60000",
			"goodsTypeName" : "号卡"
		}],
		
		
		getGoodsTypeStr : function(value){
			for(var obj in storeCommon.goodsType){
				if(value == storeCommon.goodsType[obj].goodsTypeCode){
					return storeCommon.goodsType[obj].goodsTypeName;
				}
			}
			
			
			/*
			 * 10000 合约档
			 * 20000 普通号码
			 * 30000 套餐
			 * 30100 资费套餐
			 * 30200 4G自选套餐
			 * 30300 4G套餐
			 * 40000 增值业务
			 * 40100 流量套餐
			 * 40200 数据业务
			 * 40300 优惠业务包
			 * 40400 增值业务-积分兑换
			 * 50000 配件
			 * 60000 号卡
			 * 70000 终端及其他商品
			 
			if("70000" == value){
				return "终端";
			}
			//60000 号卡
			else if("60000" == value){
				return "号卡";
			}
			else if("50000" == value){
				return "配件";
			}
			//add by lixuming 2015-5-25 V2.2接入商城套餐、业务需要的商品类型
			else if("30000" == value){
				return "套餐";
			}
			else if("30100" == value){
				return "资费套餐";
			}
			else if("30200" == value){
				return "4G自选套餐";
			}
			//add by lixuming 205-8-26 增加30300 4G套餐
			else if("30300" == value){
				return "4G套餐";
			}
			else if("40000" == value){
				return "增值业务";
			}
			else if("40100" == value){
				return "流量套餐";
			}
			else if("40200" == value){
				return "数据业务";
			}
			else if("40300" == value){
				return "优惠业务包";
			}
			//add by lixuming 205-8-26 增加40400 增值业务-积分兑换
			else if("40400" == value){
				return "积分兑换";
			}*/
			
		}
		
}