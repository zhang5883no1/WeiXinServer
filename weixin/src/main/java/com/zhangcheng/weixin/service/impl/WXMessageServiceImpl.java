package com.zhangcheng.weixin.service.impl;  

import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.zhangcheng.base.redis.RedisUtil;
import com.zhangcheng.base.util.HttpUtil;
import com.zhangcheng.base.util.PropertiesConfig;
import com.zhangcheng.base.util.StringUtil;
import com.zhangcheng.weixin.constant.WXConstant;
import com.zhangcheng.weixin.entity.WXMessageText;
import com.zhangcheng.weixin.service.WXMessageService;
  
@Service
public class WXMessageServiceImpl implements WXMessageService{

	/**
	 * 回复订阅消息
	 */
	public String replySubscribeMessage(String fromUser, String toUser, String ownerId) {
		// TODO Auto-generated method stub  
		WXMessageText text=new WXMessageText();
		//获取订阅回复内容
		String content=getMessage(ownerId);
		text.setContent(content);
		//获取时间
		text.setCreateTime(Calendar.getInstance().getTimeInMillis() / 1000+"");
		text.setMsgType("text");
		//返回消息的发送方和接收方名字互换
		text.setFromUserName(toUser);
		text.setToUserName(fromUser);
		System.out.println(text.toXMLString());
		return text.toXMLString();
	}

	public String getMessage(String ownerId) {
		// TODO Auto-generated method stub  
		try {
			String content=RedisUtil.getString(PropertiesConfig.readData(WXConstant.WX_PROPERTIES, WXConstant.WX_PROPERTIES_APPID+ownerId)+WXConstant.WX_REDIS_SUBMESSAGE);
			return content;
		} catch (Exception e) {
			// TODO: handle exception
			return "";
		}
	}
	
	public String replayMessage(String fromUser, String toUser,String type,String content){
		WXMessageText text=new WXMessageText();
		//获取订阅回复内容
		text.setContent(content);
		//获取时间
		text.setCreateTime(Calendar.getInstance().getTimeInMillis() / 1000+"");
		text.setMsgType(type);
		//返回消息的发送方和接收方名字互换
		text.setFromUserName(toUser);
		text.setToUserName(fromUser);
		System.out.println(text.toXMLString());
		return text.toXMLString();
	}

	public void setMsg(String content,String ownerId) {
		// TODO Auto-generated method stub  
		RedisUtil.setString(PropertiesConfig.readData(WXConstant.WX_PROPERTIES, WXConstant.WX_PROPERTIES_APPID+ownerId)+WXConstant.WX_REDIS_SUBMESSAGE, content);
	}

	public JSONObject getTempMsg(String ownerId) {
		// TODO Auto-generated method stub  
		String url=StringUtil.replaceUrl(WXConstant.WX_URL_GET_TEMPLATE_MESSAGE,ownerId);
		String result=HttpUtil.httpGet(url);
		return new JSONObject(result.replaceAll("\n", "<br/>"));
		
	}

	public void sendTempMsg(String content, JSONObject userlistJson,String ownerId) {
		// TODO Auto-generated method stub  
		String url=StringUtil.replaceUrl(WXConstant.WX_URL_SEND_TEMPLATE_MESSAGE,ownerId);
		JSONArray openids=userlistJson.getJSONObject("data").getJSONArray("openid");
		System.out.println("---------------------post data--------------------");
		System.out.println(openids.length());
		for(int i=0;i<openids.length();i++){
			JSONObject json=new JSONObject(content);
			json.accumulate("touser", openids.get(i));
//			System.out.println("is send : "+json.toString());
			HttpUtil.httpPost(url, json.toString());
		}
//		JSONObject json=new JSONObject(content);
//		json.accumulate("touser", PropertiesConfig.readData(WXConstant.WX_PROPERTIES, "zcopenid"));
//		System.out.println(json.toString());
//		HttpUtil.httpPost(url, json.toString());
	}
	
	public static void main(String[] args) {
		String s="{'total':23000,'count':10000,'data':{'openid':['OPENID1','OPENID2','OPENID3','OPENID4']},'next_openid':'OPENID10000'}";
		JSONObject json=new JSONObject(s);
		JSONArray opens=json.getJSONObject("data").getJSONArray("openid");
		System.out.println(opens);
		System.out.println(opens.get(2));
		System.out.println(json.toString());
	}

	public void setQuartzTempMsg(String content,String ownerId) {
		// TODO Auto-generated method stub  
		RedisUtil.setString(PropertiesConfig.readData(WXConstant.WX_PROPERTIES, WXConstant.WX_PROPERTIES_APPID+ownerId)+WXConstant.WX_REDIS_QUARTZ_TEMP_MSG, content);
	}

	public String getQuartzTempMsg(String ownerId) {
		// TODO Auto-generated method stub  
		String s=RedisUtil.getString(PropertiesConfig.readData(WXConstant.WX_PROPERTIES, WXConstant.WX_PROPERTIES_APPID+ownerId)+WXConstant.WX_REDIS_QUARTZ_TEMP_MSG);
		return s;
	}

}
