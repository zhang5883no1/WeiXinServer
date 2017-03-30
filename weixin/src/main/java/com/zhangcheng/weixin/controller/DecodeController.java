package com.zhangcheng.weixin.controller;  

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangcheng.base.util.StringUtil;
  
@Controller
@RequestMapping("controller/utli")
public class DecodeController {
	

	@RequestMapping(value="decode" ,method = RequestMethod.GET)
	@ResponseBody
	public String setMsg(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String utm_term=request.getParameter("utm_term");
		String utm_source=request.getParameter("utm_source");
		JSONObject json=new JSONObject();
		json.accumulate("utm_source", decode(utm_source));
		json.accumulate("utm_term", decode(utm_term));
		String callback=request.getParameter("callback");
		if(StringUtil.notEmpty(callback)){
			return callback+"("+json.toString()+")";
		}else{
			return json.toString();
		}
	}
	
	private static String decode(String s) throws UnsupportedEncodingException{
		if(StringUtil.notEmpty(s)){
			String gbkString = URLDecoder.decode(s,"GBK");
			String ss=new String(getUTF8BytesFromGBKString(gbkString),"UTF-8");
			return URLEncoder.encode(ss,"UTF-8");
		}else{
			return "";
		}
	}
	
	
	private static byte[] getUTF8BytesFromGBKString(String gbkStr) {  
        int n = gbkStr.length();  
        byte[] utfBytes = new byte[3 * n];  
        int k = 0;  
        for (int i = 0; i < n; i++) {  
            int m = gbkStr.charAt(i);  
            if (m < 128 && m >= 0) {  
                utfBytes[k++] = (byte) m;  
                continue;  
            }  
            utfBytes[k++] = (byte) (0xe0 | (m >> 12));  
            utfBytes[k++] = (byte) (0x80 | ((m >> 6) & 0x3f));  
            utfBytes[k++] = (byte) (0x80 | (m & 0x3f));  
        }  
        if (k < utfBytes.length) {  
            byte[] tmp = new byte[k];  
            System.arraycopy(utfBytes, 0, tmp, 0, k);  
            return tmp;  
        }  
        return utfBytes;  
    }  
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String a="%B0%CD%CA%F1%CE%A2%C5%CCapp";
		String gbkString = URLDecoder.decode(a,"GBK");
		String ss=new String(getUTF8BytesFromGBKString(gbkString),"UTF-8");
		System.out.println(gbkString);
		System.out.println(ss);
	}
	
}
