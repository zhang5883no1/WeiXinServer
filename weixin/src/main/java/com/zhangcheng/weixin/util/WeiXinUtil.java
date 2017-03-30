package com.zhangcheng.weixin.util;  

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.zhangcheng.base.quartz.QuartzTask;
import com.zhangcheng.base.redis.RedisUtil;
import com.zhangcheng.base.util.HttpUtil;
import com.zhangcheng.base.util.PropertiesConfig;
import com.zhangcheng.base.util.StringUtil;
import com.zhangcheng.weixin.constant.WXConstant;


  
public class WeiXinUtil {
	private static Logger logger = Logger.getLogger(WeiXinUtil.class);
	/**
	 * 
	* @Title: ValidSign 
	* @Description: 微信开发者模式服务器认证
	* @param @param token
	* @param @param nonce
	* @param @param timestamp
	* @param @return
	* @return String
	* @throws
	 */
	public static String ValidSign(String nonce,String timestamp,String ownerId){
		//新建排序队列
		ArrayList<String> list=new ArrayList<String>();
		list.add(nonce);
		list.add(timestamp);
		list.add(PropertiesConfig.readData(WXConstant.WX_PROPERTIES, WXConstant.WX_PROPERTIES_TOKEN+ownerId));
		//字典排序
		Collections.sort(list);
		//sha1加密
		String signature=DigestUtils.shaHex(list.get(0)+list.get(1)+list.get(2));
		return signature;
	}
	
	
	public static void refreshAccessToken(String ownid){
		String url=WXConstant.WX_URL_GET_ACCESSTOKEN;
		url=url.replace("APPID", PropertiesConfig.readData(WXConstant.WX_PROPERTIES, WXConstant.WX_PROPERTIES_APPID+ownid))
				.replace("APPSECRET", PropertiesConfig.readData(WXConstant.WX_PROPERTIES, WXConstant.WX_PROPERTIES_APPSECRECT+ownid));
		//发送请求获取accesstoken
		String jsonString=HttpUtil.httpGet(url);
		logger.debug("get accesstoekn return info : "+jsonString);
		JSONObject json=new JSONObject(jsonString);
		if(StringUtil.notEmpty(json.getString("access_token"))){
			//设置accesstoken过期时间位7000秒
			RedisUtil.setString(PropertiesConfig.readData(WXConstant.WX_PROPERTIES, WXConstant.WX_PROPERTIES_APPID+ownid)+WXConstant.WX_REDIS_ACCESSTOKEN, 3600, json.getString("access_token"));
		}else{
			logger.debug(jsonString);
		}
	}
	
	
	public static void getServerIp(String ownid){
		String url=StringUtil.replaceUrl(WXConstant.WX_URL_GET_SERVER_IP,ownid);
		String result=HttpUtil.httpGet(url);
		JSONObject json=new JSONObject(result);
		try {
			json.getJSONArray("ip_list");
		} catch (Exception e) {
			refreshAccessToken(ownid);
			// TODO: handle exception
		}
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String s="{\"errcod1e\":40013,\"errmsg\":\"invalid appid\"}";
		JSONObject json=new JSONObject(s);
		try {
			json.getInt("errcode");
		} catch (Exception e) {
			System.out.println("error");
			// TODO: handle exception
		}
	}
}
