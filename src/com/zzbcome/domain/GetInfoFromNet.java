package com.zzbcome.domain;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.zzbcome.bean.CompanyEntity;
import com.zzbcome.utils.AnalizeJson;
import com.zzbcome.utils.FileUtil;
import com.zzbcome.utils.OperateEntity;
import com.zzbcome.utils.OperateInternet;

public class GetInfoFromNet {

	
	public static Map<String, String> config = MainMethod.config;
	/**
	 * @param keyword	需要搜索的企业信息关键词
	 * @param list		保存企业信息的list
	 * @return
	 * 		是否成功获取到对应企业信息
	 */
	public static boolean OnceLinkNet(String keyword, List<CompanyEntity> list){
		boolean success = false;
		try {
			// 获取需要请求url的次数
			int times = Integer.parseInt(config.get("url_times"));

			CompanyEntity ce = new CompanyEntity();
			for (int i = 1; i <= times; i++) {
				LinkNet(keyword, i, ce);
			}
			list.add(ce);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		success = true;
		return success;
	}

	public static boolean LinkNet(String keywords, int urlIndex, CompanyEntity ce) throws NoSuchMethodException,
			SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// TODO 自动生成的方法存根
		// String url = "http://www.zuanke8.com/api/mobile/index.php";
		// String urlData =
		// "charset=utf-8&module=hotthread&mobile=no&version=3";
		String url = config.get("url" + urlIndex);
		boolean flag = false;
		String formData = null;// 每次请求的post数据
		if (urlIndex == 1) {// 首次请求没有上一请求传下来的变量
			formData = config.get("param" + urlIndex).replaceAll("param1", keywords);
		} else {
			for (int i = 1; i < urlIndex; i++) {
				// 获取从第一次开始到第(urlIndex-1)次请求对本次请求提供的参数
				String field2targetparams = config.get("field" + i + "2param" + urlIndex);
				if (field2targetparams != null && !field2targetparams.isEmpty()) {
					String[] field2targetparam = field2targetparams.split(",");
					formData = config.get("param" + urlIndex);
					for (int j = 1; j <= field2targetparam.length; j++) {
						String paramValue = (String) OperateEntity.getEntityValueByParam(ce, field2targetparam[j - 1]);
						if(!paramValue.isEmpty()){
							formData = formData.replaceAll("param" + j,paramValue);
						}
					}
				}
			}
		}
		System.out.println("formData =  " + formData);
		HashMap<String, String> headerMap = new HashMap<>();
		headerMap.put("User-Agent", config.get("User-Agent"));
		headerMap.put("Content-Type", config.get("Content-Type"));
		headerMap.put("Accept-Encoding", config.get("Accept-Encoding"));
		headerMap.put("Accept-Language", config.get("Accept-Language"));
		headerMap.put("X-Requested-With", config.get("X-Requested-With"));
		//headerMap.put("Origin", config.get("Origin"));
		//headerMap.put("Refer", "file://");
		headerMap.put("Accept", config.get("Accept"));
		HashMap<String, Object> map = OperateInternet.doPost(url, formData, headerMap);
		if ((boolean) map.get("success")) {
			String result = (String) map.get("result");
			if (result.isEmpty()||result.equals("[]")) {
				//TODO 返回结果是空的的处理方法
				System.out.println("返回错误的结果："+result);
			}else{
				flag = true;
				AnalizeJson.readJson(result, ce, urlIndex);
			}
		}
		waitingMoment(config);
		return flag;
	}

	public static void showList(List<CompanyEntity> list) {
		for (int l = 0; l < list.size(); l++) {
			CompanyEntity ce = list.get(l);
			String[] savefield = config.get("savefield").split(",");
			String values = "";
			for (String field : savefield) {
				try {
					String value = (String) OperateEntity.getEntityValueByParam(ce, field);
					if (field.equals(savefield[savefield.length - 1])) {
						values += value;
					} else {
						values += value + "^_^";
					}
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
			System.out.println(values);
		}
	}
	public static void waitingMoment(Map<String, String> map){
		String timerange = map.get("timerange");
		if(timerange!=null&&!timerange.isEmpty()&&timerange.contains(",")){
			int _min = Integer.parseInt(timerange.split(",")[0]);
			int _max = Integer.parseInt(timerange.split(",")[1]);
			double time = new Random().nextDouble()*(_max-_min)+_min;
			System.out.println("沉睡时间："+Long.parseLong((time*1000+"").substring(0, 4))+"毫秒");
			try {
				Thread.sleep(Long.parseLong((time*1000+"").substring(0, 4)));
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}
}
