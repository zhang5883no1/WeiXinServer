package com.zhangcheng.base.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Properties;

/** 
* @ClassName: PropertiesConfig 
* @Description: properties文件夹下配置文件
* @author ZHANGCHENG
* @date 2016-9-5 下午2:45:43 
*  
*/ 
public class PropertiesConfig {

    /** 
    * @Title: readData 
    * @Description: 根据KEY，读取文件对应的值 
    * @param @param filename 
    * @param @param key
    * @param @return 
    * @return String
    * @throws 
    */ 
    public static String readData(String flieName,String key) {
        //获取绝对路径  
//    	String filePath=PropertiesConfig.class.getClass().getResource("/").getPath()+"properties/"+flieName;
        Properties props = new Properties();  
        try {  
            InputStream in = new BufferedInputStream(PropertiesConfig.class.getResourceAsStream("/properties/"+flieName));  
            props.load(in);  
            in.close();  
            String value = props.getProperty(key);  
            return value;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
    
    /** 
    * @Title: writeData 
    * @Description: 写key,value
    * @param @param key
    * @param @param value
    * @return void
    * @throws 
    */ 
    public static void writeData(String flieName ,String key, String value) {  
        //获取绝对路径  
    	String filePath=Object.class.getClass().getResource("/").getPath()+"properties/"+flieName;
        //截掉路径的”file:/“前缀  
        filePath = filePath.substring(6);  
        if(filePath.indexOf(":")==-1){
        	filePath="/"+filePath;
        }
        Properties prop = new Properties();  
        try {  
            File file = new File(filePath);  
            if (!file.exists())  
                file.createNewFile();  
            InputStream fis = new FileInputStream(file);  
            prop.load(fis);  
            //一定要在修改值之前关闭fis  
            fis.close();  
            OutputStream fos = new FileOutputStream(filePath);  
            prop.setProperty(key, value);  
            //保存，并加入注释  
            prop.store(fos, "Update '" + key + "' value");  
            fos.close();  
        } catch (IOException e) {  
            System.err.println("Visit " + filePath + " for updating " + value + " value error");  
        }  
    }  
      
}
