package com.zhangcheng.weixin.entity;  

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class WXMenu {

	private List<Button> button=new ArrayList<Button>();
	
	public WXMenu(JSONObject json){
		//设置一级菜单
		JSONArray btnArray=json.getJSONArray("button");
		for(int i=0;i<btnArray.length();i++){
			JSONObject btnJson=(JSONObject)btnArray.get(i);
			Button button=new Button();
			button.setParam(btnJson);
			//设置二级菜单
			try{
				JSONArray subBtnArray=btnJson.getJSONArray("sub_button");
				for(int j=0;j<subBtnArray.length();j++){
					JSONObject subBtnJson=(JSONObject)subBtnArray.get(j);
					Button subButton=new Button();
					subButton.setParam(subBtnJson);
					button.getSub_button().add(subButton);
				}
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println(button.getName()+"没有下级菜单");
			}
			this.button.add(button);
		}
	}
	
	public JSONObject getJSON(){
		JSONObject json=new JSONObject();
		JSONArray btnArray=new JSONArray();
		//提取一级菜单
		for(Button btn:this.button){
			JSONObject btnJSON=btn.toJSON();
			JSONArray subBtnArray=new JSONArray();
			//提取二级菜单
			for(BaseButton subBtn:btn.getSub_button()){
				subBtnArray.put(subBtn.toJSON());
			}
			if(subBtnArray.length()>0){
				btnJSON.accumulate("sub_button", subBtnArray);
			}
			btnArray.put(btnJSON);
		}
		json.accumulate("button", btnArray);
		return json;
	}
	
	public String getJSONString(){
		return getJSON().toString().replaceAll("\\[\\[", "[").replaceAll("\\]\\]", "]");
	}

	public List<Button> getButton() {
		return button;
	}

	public void setButton(List<Button> button) {
		this.button = button;
	}
	
	
}
