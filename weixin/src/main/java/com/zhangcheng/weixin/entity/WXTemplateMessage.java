package com.zhangcheng.weixin.entity;  

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
  
public class WXTemplateMessage {
	
	public List<DoGetInfo> doGetInfoList(JSONObject json){
		List<DoGetInfo> list=new ArrayList<DoGetInfo>();
		JSONArray tmpArray=json.getJSONArray("template_list");
		for(Object ob:tmpArray){
			JSONObject jsoninfo=(JSONObject)ob;
			DoGetInfo info=new DoGetInfo();
			info.setTemplate_id(jsoninfo.getString("template_id"));
			info.setTitle(jsoninfo.getString("title"));
			info.setContent(jsoninfo.getString("content"));
			info.setExample(jsoninfo.getString("example"));
			list.add(info);
		}
		return list;
	}
	
	public String test(String r){
		DoGetInfo info=new DoGetInfo();
		info.setContent(r);
		return info.getContent();
	}
	
	class DoPostInfo{
		private String touser;
		private String template_id;
		private String url;
		private JSONObject data;
		
		public JSONObject toJson(){
			JSONObject json=new JSONObject();
			json.accumulate("touser", this.touser);
			json.accumulate("template_id", this.template_id);
			json.accumulate("url", this.url);
			json.accumulate("data", this.data);
			return json;
		}
		
		public String getTouser() {
			return touser;
		}
		
		public void setTouser(String touser) {
			this.touser = touser;
		}
		
		public String getTemplate_id() {
			return template_id;
		}
		
		public void setTemplate_id(String template_id) {
			this.template_id = template_id;
		}
		
		public String getUrl() {
			return url;
		}
		
		public void setUrl(String url) {
			this.url = url;
		}
		
		public JSONObject getData() {
			return data;
		}
		
		public void setData(JSONObject data) {
			this.data = data;
		}
	}
	
	public class DoGetInfo{
		private String template_id;
		private String title;
		private String content;
		private String example;
		
		public String getTemplate_id() {
			return template_id;
		}
		public void setTemplate_id(String template_id) {
			this.template_id = template_id;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = formatContent(content);
		}
		public String getExample() {
			return example;
		}
		public void setExample(String example) {
			this.example = example;
		}
		
		private String formatContent(String content){
			content=content.replaceAll(" ", "");
			while(content.indexOf("{{")!=-1){
				int start=content.indexOf("{{");
				int end=content.indexOf("}}");
				System.out.println(content);
				System.out.println("content.length : "+content.length()+" , "+start+" , "+end);
				String info=content.substring(start+2, end);
				String name=info.split("\\.")[0];
				content=content.substring(0,start)+"<input type='text' data-dname='"+name+"' />"+content.substring(end+2);
			}
			return content;
		}
		
	}
	
	public static void main(String[] args) {
		String r="{ {result.DATA} }\n\n领奖金额:{ {withdrawMoney.DATA} }\n领奖  时间:{ {withdrawTime.DATA} }\n银行信息:{ {cardInfo.DATA} }\n到账时间:  { {arrivedTime.DATA} }\n{ {remark.DATA} }";
		System.out.println(new WXTemplateMessage().test(r));
	}
	
}
