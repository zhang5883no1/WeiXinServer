package com.zhangcheng.weixin.entity;  
  
public enum WXButtonTypeEnum {

    CLICK("click"),VIEW("view");
    
    private String name;
    
    private WXButtonTypeEnum(String name){
    	this.name=name;
    }
    
 // 覆盖方法
    @Override
    public String toString() {
        return  this.name;
    }


	public String getName() {
		return name;
	}
    
}
