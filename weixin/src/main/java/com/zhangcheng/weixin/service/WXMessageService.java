package com.zhangcheng.weixin.service;  

import org.json.JSONObject;
  
public interface WXMessageService {
	
	public String getMessage(String ownerId);
	
	public String replySubscribeMessage(String fromUser,String toUser, String ownerId);

	public void setMsg(String content,String ownerId);
	
	public JSONObject getTempMsg(String ownerId);

	public void sendTempMsg(String content, JSONObject userlistJson,String ownerId);

	public String replayMessage(String fromUserName, String toUserName,
			String event, String eventKey);

	public void setQuartzTempMsg(String content,String ownerId);
	
	public String getQuartzTempMsg(String ownerId);
}
