package com.zzbcome.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.zzbcome.domain.GetInfoFromNet;

/**
 * @author zzbcome Description 处理网络交互的类
 *
 */
public class OperateInternet {
	
	private static Map<String, String> config = GetInfoFromNet.config;
	
	/**
	 * Description 发送POST请求
	 * 
	 * @param url
	 *            发送POST请求的url地址
	 * @param formData
	 *            需要post的数据
	 * @param headerMap
	 *            需要添加进请求头的内容
	 * @return 请求结果
	 */
	public static HashMap<String, Object> doPost1(String url, String formData, HashMap<String, String> headerMap) {
		// 设置fiddler代理
		if (config!= null &&!config.get("http.proxySet").isEmpty()
				&& config.get("http.proxySet").toUpperCase().equals("TRUE") && !config.get("http.proxyHost").isEmpty()
				&& !config.get("http.proxyPort").isEmpty()) {
			System.setProperty("http.proxySet", config.get("http.proxySet"));
			System.setProperty("http.proxyHost", config.get("http.proxyHost"));
			System.setProperty("http.proxyPort", config.get("http.proxyPort"));
		}

		String result = "";
		boolean success = false;
		HashMap<String, Object> map = new HashMap<String, Object>();
		PrintWriter out = null;
		BufferedReader in = null;
		try {
			if (url == null || url.trim().equals("")) {
				result = "url地址不能为空！";
			} else {
				URL realUrl = new URL(url);
				// 打开和URL之间的连接
				HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
				 // 默认是 GET方式
		        conn.setRequestMethod("POST");
				// 设置通用的请求头属性
		        // 发送POST请求必须设置如下两行
		        conn.setDoOutput(true);
		        conn.setDoInput(true);
				if (headerMap != null && !headerMap.isEmpty()) {
					Iterator<?> iter = headerMap.entrySet().iterator();// 开始遍历请求头map
					while (iter.hasNext()) {
						Map.Entry entry = (Map.Entry) iter.next();
						String key = (String) entry.getKey();
						String val = (String) entry.getValue();
						conn.setRequestProperty(key, val);
					}
				}
				conn.setRequestProperty("Origin", "file");
				// 获取URLConnection对象对应的输出流
				out = new PrintWriter(conn.getOutputStream());
				// 发送请求参数
				out.print(formData);
				// flush输出流的缓冲
				out.flush();
				//获取返回的数据编码
				String encoding = conn.getContentEncoding();
				// 定义BufferedReader输入流来读取URL的响应
				if(encoding!=null&&encoding.equals("gzip")){
					in = new BufferedReader(new InputStreamReader(new GZIPInputStream(conn.getInputStream())));
				}else{
					in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				}
				String line;
				while ((line = in.readLine()) != null) {
					result += line;
				}
				int responseCode = conn.getResponseCode();
				if(responseCode!=200){
					result = "{error:\""+result+"\"}";
				}else{
					success = true;
				}
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
			result = e.toString();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
				result = ex.toString();
			}
			// 将结果map存入数据
			map.put("result", result);
			map.put("success", success);
		}

		return map;
	}
	public static HashMap<String, Object> doPost(String url, String formData, HashMap<String, String> headerMap){
		
		String result = "";
		boolean success = false;
		HashMap<String, Object> map = new HashMap<String, Object>();
		CloseableHttpClient httpclient;
		if (config!= null &&!config.get("http.proxySet").isEmpty()
				&& config.get("http.proxySet").toUpperCase().equals("TRUE") && !config.get("http.proxyHost").isEmpty()
				&& !config.get("http.proxyPort").isEmpty()) {
			//使用代理创建
			HttpHost proxy = new HttpHost(config.get("http.proxyHost"),Integer.parseInt(config.get("http.proxyPort")));
			RequestConfig requestConfig = RequestConfig.custom().setProxy(proxy).setConnectTimeout(10000).setSocketTimeout(15000).build();
			httpclient= HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
		}else{
			// 创建默认的httpClient实例.    
			httpclient = HttpClients.createDefault();
		}
        // 创建httppost    
        HttpPost httppost = new HttpPost(url);  
		if (headerMap != null && !headerMap.isEmpty()) {
			Iterator<?> iter = headerMap.entrySet().iterator();// 开始遍历请求头map
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				String key = (String) entry.getKey();
				String val = (String) entry.getValue();
				httppost.setHeader(key, val);
			}
		}
		//以下必须单独设置才能生效？
		httppost.setHeader("Origin", "file://");
        // 创建参数队列    
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        String []param = formData.split("&");
        UrlEncodedFormEntity uefEntity;  
        try {  
	        for(String k_v:param){
	        	formparams.add(new BasicNameValuePair(k_v.split("=")[0], URLDecoder.decode(k_v.split("=")[1])));
	        }
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");  
            httppost.setEntity(uefEntity);  
            System.out.println("executing request " + httppost.getURI()); 
            CloseableHttpResponse response = httpclient.execute(httppost);  
            try {
                HttpEntity entity = response.getEntity();
                int responseCode = response.getStatusLine().getStatusCode();
                if (responseCode==200&&entity != null) {
                	result = EntityUtils.toString(entity, "UTF-8");
                    System.out.println("Response content: " + result);  
                }else{
                	result = config.get("response_error_world");
                }
                success = true;
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();
            result = e.toString();
        } catch (UnsupportedEncodingException e1) {  
            e1.printStackTrace();
            result = e1.toString();
        } catch (IOException e) {  
            e.printStackTrace();
            result = e.toString();
        }  catch (Exception e) {  
            e.printStackTrace();
            result = e.toString();
        } finally {  
            // 关闭连接,释放资源    
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();
                result = e.toString();
            }finally{
            	map.put("result", result);
            	map.put("success", success);
            }
        }
		return map;  
	}
}
