package com.zhangcheng.base.quartz;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zhangcheng.base.redis.RedisUtil;
import com.zhangcheng.base.util.HttpUtil;
import com.zhangcheng.base.util.PropertiesConfig;
import com.zhangcheng.base.util.StringUtil;
import com.zhangcheng.weixin.constant.WXConstant;
import com.zhangcheng.weixin.util.WeiXinUtil;

/** 
* @ClassName: RsyncCrmInfoTask 
* @Description: 定时任务测试
* @author ZHANGCHENG
* @date 2016-9-2 下午2:05:31 
*  
*/ 
@Component
public class QuartzTask {
	private static Logger logger = Logger.getLogger(QuartzTask.class);
	 /** 
	* @Title: myTest 
	* @Description: 每5秒钟打印
	* @param 
	* @return void
	* @throws 
	*/ 
	@Scheduled(cron="0/5 * *  * * ? ")   //每5秒执行一次  
     public void myTest(){  
           //System.out.println("进入测试");  
     } 
	 
	/** 
	* @Title: rsyncRegCrmInfo 
	* @Description: 导入已注册数据
	* @param 
	* @return void
	* @throws 
	*/ 
	@Scheduled(cron="0 0/5 *  * * ? ")
    public void rsyncRegCrmInfo(){  
    } 
	
	/** 
	* @Title: rsyncAccessToken 
	* @Description: 判断accesstoken是否过期
	* @param 
	* @return void
	* @throws 
	*/ 
	@Scheduled(cron="0 * *  * * ? ")
    public void rsyncAccessToken(){
    	String ownids=PropertiesConfig.readData(WXConstant.WX_PROPERTIES, WXConstant.WX_PROPERTIES_ALLNAMES);
    	if(StringUtil.notEmpty(ownids)){
    		String[] ownid=ownids.split("\\|");
    		for(int i=0;i<ownid.length;i++){
    			WeiXinUtil.getServerIp(ownid[i]);
    			//从缓存数据库中获取accesstoken
    			String access_token=RedisUtil.getString(PropertiesConfig.readData(WXConstant.WX_PROPERTIES, WXConstant.WX_PROPERTIES_APPID+ownid[i])+WXConstant.WX_REDIS_ACCESSTOKEN);
    			logger.debug("redis acccesstoken : "+access_token);
    			//如果accesstoken为空
    			if(!StringUtil.notEmpty(access_token)){
    				//url参数设置
    				WeiXinUtil.refreshAccessToken(ownid[i]);
//    				System.out.println(ownid[i]);
    			}
    		}
    	}
    } 
	
	public static void main(String[] args) {
	}
}
