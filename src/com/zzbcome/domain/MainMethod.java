package com.zzbcome.domain;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;

import com.zzbcome.biz.ReadKeyWords;
import com.zzbcome.utils.PropertiesFileUtil;

public class MainMethod {
	
	public static Map<String, String> config;

	static {
		config = PropertiesFileUtil.readProperties("config.properties");
	}

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class c= Class.forName(config.get("invokeclass")); 
        ReadKeyWords readkeywords = (ReadKeyWords)c.newInstance();  
        InvocationHandler invocationHandler = new MainHandler(readkeywords);  
        ReadKeyWords userServiceProxy = (ReadKeyWords)Proxy.newProxyInstance(readkeywords.getClass().getClassLoader(),  
                readkeywords.getClass().getInterfaces(), invocationHandler);  
        userServiceProxy.getKeyWord();
    }
}
