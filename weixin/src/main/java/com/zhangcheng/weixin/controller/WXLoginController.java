package com.zhangcheng.weixin.controller;  

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
  
@Controller
@RequestMapping("controller/wx/login/{ownerId}")
public class WXLoginController {
	private static Logger logger = Logger.getLogger(WXLoginController.class);
	
	@RequestMapping(value="init" ,method = RequestMethod.GET)
	public String getMenu(HttpServletRequest request,Model model,@PathVariable String ownerId) throws Exception{
		model.addAttribute("ownerId", ownerId);
		return "/weixin/index";
	}
}
