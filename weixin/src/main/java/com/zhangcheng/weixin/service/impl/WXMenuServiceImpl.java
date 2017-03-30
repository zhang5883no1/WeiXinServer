package com.zhangcheng.weixin.service.impl;  

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.zhangcheng.base.redis.RedisUtil;
import com.zhangcheng.base.util.HttpUtil;
import com.zhangcheng.base.util.PropertiesConfig;
import com.zhangcheng.base.util.StringUtil;
import com.zhangcheng.weixin.constant.WXConstant;
import com.zhangcheng.weixin.entity.WXMenu;
import com.zhangcheng.weixin.service.WXMenuService;
  
@Service
public class WXMenuServiceImpl implements WXMenuService{
	private static Logger logger = Logger.getLogger(WXMenuServiceImpl.class);
	/**
	 * 自定义菜单查询接口
	 */
	public WXMenu getMenu(String ownerId) {
		// TODO Auto-generated method stub  
		WXMenu menu=null;
		String url=StringUtil.replaceUrl(WXConstant.WX_URL_GET_MENU,ownerId);
		String result=HttpUtil.httpGet(url);
		JSONObject menuJson=new JSONObject(result);
		try {
			JSONObject buttonJson=menuJson.getJSONObject("menu");
			menu=new WXMenu(buttonJson);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return menu;
	}
	
	/**
	 * 自定义菜单创建接口
	 */
	public boolean setMenu(WXMenu menu,String ownerId) {
		// TODO Auto-generated method stub  
		String url=StringUtil.replaceUrl(WXConstant.WX_URL_SET_MENU,ownerId);
		String result=HttpUtil.httpPost(url, menu.getJSONString());
		JSONObject resultJson=new JSONObject(result);
		int errorCode=resultJson.getInt("errcode"); 
		if(errorCode==0){
			return true;
		}else{
			logger.error("菜单设置失败，错误代码："+errorCode+",errmsg："+resultJson.getString("errmsg"));
			return false;
		}
	}
	
	public static void main(String[] args) {
		String s="{\"menu\":{\"button\":[{\"type\":\"click\",\"name\":\"1\",\"key\":\" 1\",\"sub_button\":[]},{\"type\":\"click\",\"name\":\"2\",\"key\":\" 2\",\"sub_button\":[]},{\"type\":\"click\",\"name\":\"3\",\"key\":\" \",\"sub_button\":[]}]}}";
		JSONObject menuJson=new JSONObject(s);
		JSONObject buttonJson=menuJson.getJSONObject("menu");
		WXMenu menu=new WXMenu(buttonJson);
		System.out.println(menu.getJSONString());
	}

}
