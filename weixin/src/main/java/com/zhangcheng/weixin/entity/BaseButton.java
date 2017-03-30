package com.zhangcheng.weixin.entity;  

import org.json.JSONObject;
  
public class BaseButton {
		private String name;
		private String type;
		private String key;
		private String url;
		private String media_id;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getMedia_id() {
			return media_id;
		}
		public void setMedia_id(String media_id) {
			this.media_id = media_id;
		}
		
		public void setParam(JSONObject json){
			try {
				this.setKey(json.getString("key"));
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				this.setUrl(json.getString("url"));
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				this.setMedia_id(json.getString("media_id"));			
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				this.setType(json.getString("type"));
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				this.setName(json.getString("name"));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		public JSONObject toJSON(){
			JSONObject json=new JSONObject();
			json.accumulate("key", this.key);
			json.accumulate("name", this.name);
			json.accumulate("type", this.type);
			json.accumulate("url", this.url);
			json.accumulate("media_id", this.media_id);
			return json;
		}
}
