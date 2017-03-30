package com.zhangcheng.weixin.service;  

import com.zhangcheng.weixin.entity.WXMenu;
  
public interface WXMenuService {

	public WXMenu getMenu(String ownerId);
	
	public boolean setMenu(WXMenu menu, String ownerId);
}
