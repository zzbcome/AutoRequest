package com.zzbcome.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.zzbcome.bean.CompanyEntity;

public class OperateEntity {
	/**
	 * Description 	根据参数设置entity实体的参数值
	 * @param ce	需要设置值的实体类实例
	 * @param param	实体类的具体参数
	 * @param value	实体类的参数值
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static void setEntityByParam(CompanyEntity ce,String param,String value) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Class<?> ceClass = ce.getClass();
			Method m = ceClass.getMethod("set"+param.substring(0,1)+param.toLowerCase().substring(1),String.class);
            if(param.toUpperCase().equals("ENTNAME")){
            	m.invoke(ce,value.replaceAll("<font color=red>", "").replaceAll("</font>", ""));
            }else if(param.toUpperCase().equals("BUSEXCEPTCOUNT")){
            	m.invoke(ce,value.equals("0")?"2":"1");
            }else{
            	m.invoke(ce,value);
            }
	}
	
	/**
	 * Description 	根据参数获取entity实体的某个参数值
	 * @param ce	需要设置值的实体类实例
	 * @param param	实体类的具体参数
	 * @return	对应的实体参数值
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static Object getEntityValueByParam(CompanyEntity ce,String param) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Class<?> ceClass = ce.getClass();
		Method m = ceClass.getMethod("get"+param.substring(0,1).toUpperCase()+param.toLowerCase().substring(1));
		Object value = m.invoke(ce);
		if(value==null)
			value="";
        return value;
	}
}
