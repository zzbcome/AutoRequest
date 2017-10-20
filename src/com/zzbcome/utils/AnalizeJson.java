package com.zzbcome.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;

import com.zzbcome.bean.CompanyEntity;
import com.zzbcome.domain.GetInfoFromNet;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONTokener;

public class AnalizeJson {

	/**
	 * Description： 解析json字符串
	 * 
	 * @param str
	 *            需要解析的字符串
	 */
	public static void readJson(String str, CompanyEntity ce, int urlIndex) {
		try {
			if(str==null||str.equals("")){return;}
			Object json = new JSONTokener(str).nextValue();
			if (json instanceof JSONObject) {
				JSONObject jsonObject = (JSONObject) json;
				readJsonObject(jsonObject, ce, urlIndex);
			} else if (json instanceof JSONArray) {
				JSONArray jsonArray = (JSONArray) json;
				int len = jsonArray.size();
				if (len > 0) {
					for (int i = 0; i < 1; i++) {
						JSONObject jso = (JSONObject) jsonArray.get(i);
						readJsonObject(jso, ce, urlIndex);
					}
				}
			} else {
				// System.out.println("-----------------"+json);
			}
		} catch (Exception e) {
			System.out.println(str);
			
			e.printStackTrace();
		}
	}

	/**
	 * @param jso
	 *            需要解析的JSONObject对象
	 */
	private static void readJsonObject(JSONObject jso, CompanyEntity ce, int urlIndex) {
		Iterator<?> iterator = jso.keys();
		while (iterator.hasNext()) {
			String key = iterator.next().toString();
			if (jso.get(key) instanceof JSONObject) { 
				System.out.println("取到了空的值？？？？？？？？？----" + jso.get(key));
				continue;
			}
			String value = (String) jso.get(key);
			// System.out.println("("+key+" = "+value+")");
			// 获取配置文件中
			String fields = GetInfoFromNet.config.get("field" + urlIndex);
			if (fields.toUpperCase().contains(key)) {
				try {
					OperateEntity.setEntityByParam(ce, key, value);
				} catch (NoSuchMethodException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
			readJson(value, ce, urlIndex);
		}
	}
}
