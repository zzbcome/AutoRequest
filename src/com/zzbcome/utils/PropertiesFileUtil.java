package com.zzbcome.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesFileUtil {

	// 根据key读取value
	public static String readValue(String filePath, String key) 
	{
		Properties props = new Properties();
		try 
		{
			InputStream in = new BufferedInputStream(new FileInputStream(filePath));
			props.load(in);
			String value = props.getProperty(key);
			System.out.println(key + value);
			return value;
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	// 读取properties的全部信�?
	public static Map<String, String> readProperties(String filePath) 
	{
		System.out.println("开始读取properties文件");
		Properties props = new Properties();
		Map<String, String> map = new HashMap<String, String>();
		try 
		{
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File(filePath)));
			BufferedReader bf = new BufferedReader(new    InputStreamReader(in)); 
			props.load(bf);
			Enumeration en = props.propertyNames();
			while (en.hasMoreElements()) 
			{
				String key = (String) en.nextElement();
				String value = props.getProperty(key);
				//System.out.println(key+"-----"+value);
				map.put(key, value);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return map;

	}

	// 写入properties信息

	public static void writeProperties(String filePath, String parameterName,String parameterValue) 
	{
		Properties prop = new Properties();
		try 
		{
			InputStream fis = new FileInputStream(filePath);
			// 从输入流中读取属性列表（键和元素对）
			prop.load(fis);
			// 调用 Hashtable 的方�?? put。使�?? getProperty 方法提供并行性�??

			// 强制要求为属性的键和值使用字符串。返回�?�是 Hashtable 调用 put 的结果�??
			OutputStream fos = new FileOutputStream(filePath);
			prop.setProperty(parameterName, parameterValue);
			// 以�?�合使用 load 方法加载�?? Properties 表中的格式，
			// 将此 Properties 表中的属性列表（键和元素对）写入输出�??
			prop.store(fos, "Update '" + parameterName + "' value");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args) 
	{
		try {
//			readValue(path+"system.properties","faultDealTimeOut1" );
			
			//String path = PropertiesFileUtil.class.getClassLoader().getResource("ftp.properties").toURI().getPath();
			readProperties("/config.properties");
		} catch (Exception e) {
			e.printStackTrace();
		}  
		
		//writeProperties("info.properties", "age", "22");
		//System.out.println("OK");
	}



}
