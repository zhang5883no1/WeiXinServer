package com.zhangcheng.base.redis;  

import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.zhangcheng.base.util.PropertiesConfig;
import com.zhangcheng.base.util.StringUtil;
  
public class RedisUtil {
	
	//Redis服务器IP
    private static String ADDR_ARRAY = PropertiesConfig.readData("redis.properties", "server");
    
    //Redis的端口号
    private static int PORT = Integer.parseInt(PropertiesConfig.readData("redis.properties", "port"));
    
    //访问密码
//    private static String AUTH = ReadConfigProperties.getPropertyValue("/redis.properties", "auth");
    
    //可用连接实例的最大数目，默认值为8；
    //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    private static int MAX_ACTIVE = Integer.parseInt(PropertiesConfig.readData("redis.properties", "max_active"));
    
    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int MAX_IDLE = Integer.parseInt(PropertiesConfig.readData("redis.properties", "max_idle"));
    
    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private static int MAX_WAIT = Integer.parseInt(PropertiesConfig.readData("redis.properties", "max_wait"));

    //超时时间
    private static int TIMEOUT = Integer.parseInt(PropertiesConfig.readData("redis.properties", "timeout"));
    
    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW = Boolean.getBoolean(PropertiesConfig.readData("redis.properties", "test_on_borrow"));
    
    private static JedisPool jedisPool = null;
    
    /**
     * redis过期时间,以秒为单位
     */
    public final static int EXRP_HOUR = 60*60;			//一小时
    public final static int EXRP_DAY = 60*60*24;		//一天
    public final static int EXRP_MONTH = 60*60*24*30;	//一个月
    
    /**
     * 初始化Redis连接池
     */
    private static void initialPool(){
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            jedisPool = new JedisPool(config, ADDR_ARRAY.split(",")[0], PORT, TIMEOUT);
        } catch (Exception e) {
        	e.printStackTrace();
        	try{
	            //如果第一个IP异常，则访问第二个IP
	            JedisPoolConfig config = new JedisPoolConfig();
	            config.setMaxTotal(MAX_ACTIVE);
	            config.setMaxIdle(MAX_IDLE);
	            config.setMaxWaitMillis(MAX_WAIT);
	            config.setTestOnBorrow(TEST_ON_BORROW);
	            jedisPool = new JedisPool(config, ADDR_ARRAY.split(",")[1], PORT, TIMEOUT);
            }catch(Exception e2){
            	e2.printStackTrace();
            }
        }
    }
    
    
    /**
     * 在多线程环境同步初始化
     */
    private static synchronized void poolInit() {
    	if (jedisPool == null) {  
            initialPool();
        }
    }

    
    /**
     * 同步获取Jedis实例
     * @return Jedis
     */
    private synchronized static Jedis getJedis() {  
        if (jedisPool == null) {  
        	System.out.println("连接池为空 ");
        	poolInit();
        }
        Jedis jedis = null;
        try {  
            if (jedisPool != null) {  
            	jedis = jedisPool.getResource(); 
            }
        } catch (Exception e) {  
        	e.printStackTrace();
        }
        return jedis;
    }  
    
    
    /**
     * 释放jedis资源
     * @param jedis
     */
    private static void returnResource(final Jedis jedis) {
        if (jedis != null ) {
        	jedis.close();
        }
    }
    
	
	/**
	 * 设置 String
	 * @param key
	 * @param value
	 */
	public static void setString(String key ,String value){
		Jedis jedis=null;
    	try {
    		value = StringUtil.notEmpty(value) ? value : "";
    		jedis=getJedis();
    		jedis.set(key,value);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
        	returnResource(jedis);
        }
	}
	
	public static void setString(byte[] key ,byte[] value){
		Jedis jedis=null;
    	try {
    		jedis=getJedis();
    		if(value==null||value.length==0){
    			return ;
    		}
    		jedis.set(key,value);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
        	returnResource(jedis);
        }
	}
	
	/**
	 * 设置 过期时间
	 * @param key
	 * @param seconds 以秒为单位
	 * @param value
	 */
	public static void setString(String key ,int seconds,String value){
		Jedis jedis=null;
    	try {
    		value = StringUtil.notEmpty(value) ? value : "";
    		jedis=getJedis();
    		jedis.setex(key, seconds, value);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
        	returnResource(jedis);
        }
	}
	
	/**
	 * 获取String值
	 * @param key
	 * @return value
	 */
	public static String getString(String key){
		String result= null;
		Jedis jedis=null;
		try {
			jedis=getJedis();
			if(jedis == null || !jedis.exists(key)){
				return null;
			}
			result=jedis.get(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	
	public static byte[] getString(byte[] key){
		byte[] result= null;
		Jedis jedis=null;
		try {
			jedis=getJedis();
			if(jedis == null || !jedis.exists(key)){
				return null;
			}
			result=jedis.get(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	
	
	public static void putList(String key ,String value){
		Jedis jedis=null;
    	try {
    		value = StringUtil.notEmpty(value) ? value : "";
    		jedis=getJedis();
    		jedis.lpush(key,value);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
        	returnResource(jedis);
        }
	}
	
	public static void putList(byte[] key ,byte[] value){
		Jedis jedis=null;
    	try {
    		jedis=getJedis();
    		if(value==null||value.length==0){
    			return ;
    		}
    		jedis.lpush(key,value);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
        	returnResource(jedis);
        }
	}
	
	public static List<String> getAllList(String key ){
		Jedis jedis=null;
		List<String> result=null;
    	try {
    		jedis=getJedis();
    		result=jedis.lrange(key, 0, -1);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
        	returnResource(jedis);
        }
    	return result;
	}
	
	public static List<byte[]> getAllList(byte[] key ){
		Jedis jedis=null;
		List<byte[]> result=null;
    	try {
    		jedis=getJedis();
    		result=jedis.lrange(key, 0, -1);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
        	returnResource(jedis);
        }
    	return result;
	}
	
	public static void removeFirstFromList(String key){
		Jedis jedis=null;
		try {
    		jedis=getJedis();
    		jedis.rpop(key);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
        	returnResource(jedis);
        }
	}
	
	public static void removeFirstFromList(byte[] key){
		Jedis jedis=null;
		try {
    		jedis=getJedis();
    		jedis.rpop(key);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
        	returnResource(jedis);
        }
	}
	
	public static Long getListSize(String key){
		Jedis jedis=null;
		Long length=0L;
		try {
    		jedis=getJedis();
    		length=jedis.llen(key);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
        	returnResource(jedis);
        }
		return length;
	}
	
	public static Long getListSize(byte[] key){
		Jedis jedis=null;
		Long length=0L;
		try {
    		jedis=getJedis();
    		length=jedis.llen(key);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
        	returnResource(jedis);
        }
		return length;
	}




	public static void removeByIndexFromList(byte[] key,int i) {
		Jedis jedis=null;
		try {
    		jedis=getJedis();
    		jedis.lrem(key, 1, jedis.lindex(key, i));
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
        	returnResource(jedis);
        }
	}
	
	public static void removeByIndexFromList(String key,int i) {
		Jedis jedis=null;
		try {
    		jedis=getJedis();
    		jedis.lrem(key, 1, jedis.lindex(key, i));
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
        	returnResource(jedis);
        }
	}


	public static void removeValueFromList(byte[] key, byte[] value) {
		Jedis jedis=null;
		try {
    		jedis=getJedis();
    		jedis.lrem(key, 0, value);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
        	returnResource(jedis);
        }
	}


	public static void removeAll(String string) {
		Jedis jedis=null;
		try {
    		jedis=getJedis();
    		jedis.del(string);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
        	returnResource(jedis);
        }
	}
	
	public static void removeAll(byte[] string) {
		Jedis jedis=null;
		try {
    		jedis=getJedis();
    		jedis.del(string);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
        	returnResource(jedis);
        }
	}


	public static void setString(String key, String value, int i) {
		Jedis jedis=null;
    	try {
    		value = StringUtil.notEmpty(value) ? value : "";
    		jedis=getJedis();
//    		jedis.set(key,value);
    		jedis.setex(key, i, value);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
        	returnResource(jedis);
        }
	}
	
}
