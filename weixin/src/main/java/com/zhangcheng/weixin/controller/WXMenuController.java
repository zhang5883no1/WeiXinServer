package com.zhangcheng.weixin.controller;  

import java.net.URLDecoder;

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

import com.zhangcheng.weixin.entity.WXMenu;
import com.zhangcheng.weixin.service.WXMenuService;
  
@Controller
@RequestMapping("controller/wx/menu/{ownerId}")
public class WXMenuController {
	private static Logger logger = Logger.getLogger(WXMenuController.class);
	@Autowired
	private WXMenuService menuService;
	
	@RequestMapping(value="getMenu" ,method = RequestMethod.GET)
	public String getMenu(HttpServletRequest request,Model model,@PathVariable String ownerId) throws Exception{
		WXMenu menu=menuService.getMenu(ownerId);
//		String s="{\"button\":[{\"name\":\"1\",\"sub_button\":[{\"type\":\"click\",\"name\":\"11\",\"key\":\" 11\",\"sub_button\":[]}]},{\"name\":\"2\",\"sub_button\":[{\"type\":\"click\",\"name\":\"22\",\"key\":\" 22\",\"sub_button\":[]}]},{\"name\":\"3\",\"sub_button\":[{\"type\":\"click\",\"name\":\"33\",\"key\":\" 33\",\"sub_button\":[]}]}]}";
//		WXMenu menu=new WXMenu(new JSONObject(s));
		model.addAttribute("menu", menu);
		return "/weixin/menu/index";
	}

	@RequestMapping(value="setMenu" ,method = RequestMethod.POST)
	@ResponseBody
	public String setMenu(HttpServletRequest request, HttpServletResponse response,@PathVariable String ownerId) throws Exception{
		String content = URLDecoder.decode(request.getParameter("content"),"UTF-8");
		WXMenu menu=new WXMenu(new JSONObject(content));
		logger.debug("sub msg : "+menu.getJSONString());
		Boolean result = menuService.setMenu(menu,ownerId);
		return result.toString();
//		return "true";
	}
	
	public static void main(String[] args) {
		String s="{\"button\":[{\"name\":\"菜单名称\",\"type\":\"click\",\"key\":\"华\"},{\"name\":\"菜单名称\",\"type\":\"view\",\"url\":\"http://www.baidu.com\"},{\"name\":\"菜单名称\",\"sub_button\":[{\"name\":\"子菜单名称\",\"type\":\"click\",\"key\":\"\"}]}]}";
		WXMenu menu=new WXMenu(new JSONObject(s));
		System.out.println(menu.getJSONString());
	}
}
