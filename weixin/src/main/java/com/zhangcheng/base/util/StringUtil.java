package com.zhangcheng.base.util;

import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

import com.zhangcheng.base.redis.RedisUtil;
import com.zhangcheng.weixin.constant.WXConstant;
import com.zhangcheng.weixin.controller.WXLoginController;

public class StringUtil {
	private static Logger logger = Logger.getLogger(StringUtil.class);
	public static Map<String,String> requestReader(String sb){
		Map<String,String> map=new HashMap<String,String>();
		String ss[]=sb.toString().split("&");
		for(int i=0;i<ss.length;i++){
			String s[]=ss[i].split("=");
			try {
				map.put(s[0], s[1]);
			} catch (Exception e) {
				
			}
		}
		return map;
	}
	
	public static String str;
	public static final String EMPTY_STRING = "";

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	public static String MD5Encode(String origin) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString
					.getBytes()));
		} catch (Exception ex) {
		}
		return resultString;
	}
	
	public static String randomString(int length){
		String s="";
		String[] array={"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","0","1","2","3","4","5","6","7","8","9"};
		Random rd=new Random();
		for(int i=0;i<length;i++){
			s+=array[rd.nextInt(array.length)];
		}
		return s;
	}
	
	public static boolean notEmpty(String s){
		if(s!=null){
			s=s.replaceAll(" ", "");
			return !s.equals("");
		}else{
			return false;
		}
	}
	
	public static String replaceEmoji(String s){
		return s.replaceAll("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", "");
	}
	
	public static void main(String[] args) {
		System.out.println(StringUtil.replaceEmoji("<img src='http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/bc/fuyun_thumb.gif' alt='[浮云]' width='22' height='22'>"));
	}
	
	public static String replaceUrl(String url, String ownerId){
//		String appid=PropertiesConfig.readData(WXConstant.WX_PROPERTIES, WXConstant.WX_PROPERTIES_APPID);
//		logger.debug("token : "+appid);
		String accesstoken=RedisUtil.getString(PropertiesConfig.readData(WXConstant.WX_PROPERTIES, WXConstant.WX_PROPERTIES_APPID+ownerId)+WXConstant.WX_REDIS_ACCESSTOKEN);
		logger.debug("accesstoken : "+accesstoken);
		url=url.replace(WXConstant.WX_URL_ACCESS_TOKEN , accesstoken);
		return url;
	}


}
