package com.zhangcheng.weixin.entity;  

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.sf.json.JSONObject;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.zhangcheng.base.util.XMLUtil;
  
public class WXMessage {
	
	public Document toXML() {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();     
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.newDocument(); 
			Element element=doc.createElement("ToUserName");
			element.normalize();
			element.setTextContent("<![CDATA[justName]]>");
			System.out.println(element.getTextContent());
			doc.appendChild(element);
			System.out.println(doc.getElementsByTagName("ToUserName").item(0).getTextContent());
			doc.normalizeDocument();
			return doc;
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block  
			e.printStackTrace();
		}     
        return null;
	}
	
	public String toXMLString(){
		String result="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><xml>";
	    Class c=this.getClass();  
	        //得到对象中的所有方法  
	        Method[] ms=c.getMethods();  
	        //得到对象中所有的属性,虽然在这个里面就能获取所有的字段名，但不建议这么用，破坏类的封装性  
	        Field[]  fs=c.getDeclaredFields();  
	        //得到对象类的名字  
	        String cname=c.getName();  
	        //遍历方法  
	        for(Method m:ms){  
	             String methodName=m.getName();//获取每一个方法名  
	             //只得到具有get方法的属性，getClass除外  
	             if(methodName.startsWith("get")&&!methodName.startsWith("getClass")){  
	                 String fieldName = methodName.substring(3, methodName.length());  
	                 try{  
	                     Object value=m.invoke(this, null); 
	                     if("CreateTime".equals(fieldName)){
	                    	 result+="<"+fieldName+">"+value+"</"+fieldName+">";
	                     }else{
	                    	 result+="<"+fieldName+"><![CDATA["+value+"]]></"+fieldName+">";
	                     }
	                 }catch(Exception e){  
	                     e.printStackTrace();  
	                 }  
	                   
	             }  
	          
	        }  
		return result+"</xml>";
	}
	
	public static void main(String[] args) {
		WXMessageText text=new WXMessageText();
		text.setContent("is content");
		text.setCreateTime("1234566");
		text.setFromUserName("is name");
		text.setMsgType("is type");
		text.setToUserName("is to user");
		System.out.println(text.toXMLString());
	}
}
