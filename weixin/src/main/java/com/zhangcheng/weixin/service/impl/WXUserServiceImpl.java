package com.zhangcheng.weixin.service.impl;  

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.zhangcheng.base.redis.RedisUtil;
import com.zhangcheng.base.util.HttpUtil;
import com.zhangcheng.base.util.PropertiesConfig;
import com.zhangcheng.base.util.StringUtil;
import com.zhangcheng.weixin.constant.WXConstant;
import com.zhangcheng.weixin.service.WXUserService;

@Service
public class WXUserServiceImpl implements WXUserService{

	public JSONObject getUserList(String ownerId) {
		// TODO Auto-generated method stub  
		String url=StringUtil.replaceUrl(WXConstant.WX_URL_GET_USER,ownerId);
		String result=HttpUtil.httpGet(url);
		return new JSONObject(result);
//		String s="{'total':23000,'count':10000,'data':{'openid':['OPENID1','OPENID2','OPENID3','OPENID4']},'next_openid':'OPENID10000'}";
//		JSONObject json=new JSONObject(s);
//		return json;
	}

}
