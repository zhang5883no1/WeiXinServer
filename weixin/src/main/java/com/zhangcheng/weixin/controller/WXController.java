package com.zhangcheng.weixin.controller;  

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.zhangcheng.base.util.XMLUtil;
import com.zhangcheng.weixin.entity.WXMessageText;
import com.zhangcheng.weixin.service.WXMessageService;
import com.zhangcheng.weixin.util.WeiXinUtil;

  
@Controller
@RequestMapping("/weixin/server/{ownerId}")
public class WXController{
	private static Logger logger = Logger.getLogger(WXController.class);
	@Autowired
	private WXMessageService msgService;
	/**
	 * 
	* @Title: init 
	* @Description: 微信开发模式服务器认证
	* @param @param request
	* @param @param response
	* @param @return
	* @param @throws IOException
	* @return String
	* @throws
	 */
	@RequestMapping(value="service" ,method = RequestMethod.GET)
	@ResponseBody
	public String init(HttpServletRequest request, HttpServletResponse response,@PathVariable String ownerId) throws Exception{
//		String url=request.getServletPath();
		String signature=request.getParameter("signature");
		String timestamp=request.getParameter("timestamp");
		String nonce=request.getParameter("nonce");
		String echostr=request.getParameter("echostr");
		String validSignature=WeiXinUtil.ValidSign(nonce, timestamp,ownerId);
		if(signature.equals(validSignature)){
			return echostr;
		}
		return "1";
	}
	
	@RequestMapping(value="service" ,method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String todo(HttpServletRequest request, HttpServletResponse response,@PathVariable String ownerId){
		//解析xml
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();     
			DocumentBuilder db = dbf.newDocumentBuilder();     
			Document doc = db.parse(request.getInputStream()); 
			logger.debug(XMLUtil.getXmlString(doc));
			logger.debug(doc.getElementsByTagName("ToUserName").item(0).getTextContent());
			String msgType=XMLUtil.getValueByTagName(doc, "MsgType");
			logger.debug("msgType : "+msgType);
			//判断消息类型为订阅
			if("event".equals(msgType)){
				String event=XMLUtil.getValueByTagName(doc, "Event");
				logger.debug("event : "+event);
				if("subscribe".equals(event)){
					String FromUserName=XMLUtil.getValueByTagName(doc, "FromUserName");
					String ToUserName=XMLUtil.getValueByTagName(doc, "ToUserName");
					return msgService.replySubscribeMessage(FromUserName, ToUserName,ownerId);
				}else if("CLICK".equals(event)){
					String FromUserName=XMLUtil.getValueByTagName(doc, "FromUserName");
					String ToUserName=XMLUtil.getValueByTagName(doc, "ToUserName");
					String EventKey=XMLUtil.getValueByTagName(doc, "EventKey");
					return msgService.replayMessage(FromUserName, ToUserName, "text", EventKey);
				}else {
					return "success";
				}
			}
			return "success";
		} catch (Exception e) {
			// TODO: handle exception
			return "";
		}
	}
	
	
}
