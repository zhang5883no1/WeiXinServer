package com.zhangcheng.weixin.controller;  

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangcheng.weixin.entity.WXTemplateMessage;
import com.zhangcheng.weixin.entity.WXTemplateMessage.DoGetInfo;
import com.zhangcheng.weixin.service.WXMessageService;
import com.zhangcheng.weixin.service.WXUserService;
  
@Controller
@RequestMapping("controller/wx/message/{ownerId}")
public class WXMessageController {
	private static Logger logger = Logger.getLogger(WXMessageController.class);
	
	@Autowired
	private WXMessageService msgService;
	@Autowired
	private WXUserService userService;
	
	@RequestMapping(value="getMsg" ,method = RequestMethod.GET)
	public String getMsg(HttpServletRequest request,Model model,@PathVariable String ownerId) throws Exception{
		String msg=msgService.getMessage(ownerId);
		model.addAttribute("msg", msg);
		return "/weixin/message/index";
	}

	@RequestMapping(value="setMsg" ,method = RequestMethod.POST)
	@ResponseBody
	public String setMsg(HttpServletRequest request, HttpServletResponse response,@PathVariable String ownerId) throws Exception{
		String content = URLDecoder.decode(request.getParameter("content"),"UTF-8");
		logger.debug("sub msg : "+content);
		msgService.setMsg(content,ownerId);
		return "success";
	}
	
	@RequestMapping(value="getTempMsg" ,method = RequestMethod.GET)
	public String getTempMsg(HttpServletRequest request,Model model,@PathVariable String ownerId) throws Exception{
		JSONObject msg=msgService.getTempMsg(ownerId);
//		String r="{'template_list':[{'template_id':'moban1','title':'领取奖金提醒1','primary_industry':'IT科技','deputy_industry':'互联网|电子商务','content':'{{result.DATA}}\n\n领奖金额:{{withdrawMoney.DATA}}\n领奖时间:{{withdrawTime.DATA}}\n银行信息:{{cardInfo.DATA}}\n到账时间:{{arrivedTime.DATA}}\n{{remark.DATA}}','example':'您已提交领奖申请\n\n领奖金额：xxxx元\n领奖时间：2013-10-1012:22:22\n银行信息：xx银行(尾号xxxx)\n到账时间：预计xxxxxxx\n\n预计将于xxxx到达您的银行卡'},{'template_id':'moban2','title':'领取奖金提醒2','primary_industry':'IT科技','deputy_industry':'互联网|电子商务','content':'{{result.DATA}}\n\n领奖金额:{{withdrawMoney.DATA}}\n领奖时间:{{withdrawTime.DATA}}\n银行信息:{{cardInfo.DATA}}\n到账时间:{{arrivedTime.DATA}}\n{{remark.DATA}}','example':'您已提交领奖申请\n\n领奖金额：xxxx元\n领奖时间：2013-10-1012:22:22\n银行信息：xx银行(尾号xxxx)\n到账时间：预计xxxxxxx\n\n预计将于xxxx到达您的银行卡'}]}";
//		JSONObject msg=new JSONObject(r.replaceAll("\n", "<br/>"));
		List<DoGetInfo> list=new WXTemplateMessage().doGetInfoList(msg);
		model.addAttribute("msg", list);
		return "/weixin/message/tempMsg";
	}
	
	@RequestMapping(value="sendTempMsg" ,method = RequestMethod.POST)
	@ResponseBody
	public String sendTempMsg(HttpServletRequest request, HttpServletResponse response,@PathVariable String ownerId) throws Exception{
		String content = URLDecoder.decode(request.getParameter("content"),"UTF-8");
		System.out.println("---------------------------------------------");
		System.out.println("in temp msg");
		System.out.println("---------------------------------------------");
		logger.debug("sub msg : "+content);
		JSONObject userlistJson=userService.getUserList(ownerId);
		msgService.sendTempMsg(content,userlistJson,ownerId);
		return "success";
	}
	
	@RequestMapping(value="setTempMsg" ,method = RequestMethod.POST)
	@ResponseBody
	public String setTempMsg(HttpServletRequest request, HttpServletResponse response,@PathVariable String ownerId) throws Exception{
		String content = URLDecoder.decode(request.getParameter("content"),"UTF-8");
		logger.debug("sub msg : "+content);
		return "success";
	}
	
	
	public static void main(String[] args) throws UnsupportedEncodingException {
//		String r="{	 'template_list': [{      'template_id': 'iPk5sOIt5X_flOVKn5GrTFpncEYTojx6ddbt8WYoV5s',      'title': '领取奖金提醒',      'primary_industry': 'IT科技',      'deputy_industry': '互联网|电子商务',      'content': '',      'example': '您已提交领奖申请\n\n'   }]}";
//		System.out.println(r.replaceAll("\n", "<br/>"));
//		JSONObject msg=new JSONObject(r.replaceAll("\n", "<br/>"));
//		System.out.println(r);
		String s="utm_source=%CB%D1%B9%B7%D2%C6%B6%AF&utm_term=%CE%A2%C5%CC";
		System.out.println(URLDecoder.decode(s,"GBK"));
		System.out.println(URLEncoder.encode(s));
	}
}
