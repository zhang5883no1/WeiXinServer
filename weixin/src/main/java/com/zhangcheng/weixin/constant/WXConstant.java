package com.zhangcheng.weixin.constant;  
  
public class WXConstant {
	//配置文件参数
	public static final String WX_PROPERTIES="wx.properties";
	public static final String WX_PROPERTIES_TOKEN="token";
	public static final String WX_PROPERTIES_APPID="appid";
	public static final String WX_PROPERTIES_APPSECRECT="appsecrect";
	public static final String WX_PROPERTIES_ALLNAMES="allName";
	//redis参数
	public static final String WX_REDIS_ACCESSTOKEN ="_ACCESSTOKEN";
	public static final String WX_REDIS_SUBMESSAGE="_RepSubMsg";
	public static final String WX_REDIS_QUARTZ_TEMP_MSG="_QTMsg";
	//url参数
	//accesstoken
	public static final String WX_URL_ACCESS_TOKEN="ACCESS_TOKEN";
	public static final String WX_URL_GET_ACCESSTOKEN="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	//ip
	public static final String WX_URL_GET_SERVER_IP="https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=ACCESS_TOKEN";
	//menu
	public static final String WX_URL_SET_MENU="https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	public static final String WX_URL_GET_MENU="https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	//template message
	public static final String WX_URL_GET_TEMPLATE_MESSAGE="https://api.weixin.qq.com/cgi-bin/template/get_all_private_template?access_token=ACCESS_TOKEN";
	public static final String WX_URL_SEND_TEMPLATE_MESSAGE="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
	//user
	public static final String WX_URL_GET_USER="https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=";
}
