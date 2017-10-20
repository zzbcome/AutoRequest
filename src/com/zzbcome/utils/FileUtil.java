package com.zzbcome.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.zzbcome.bean.CompanyEntity;
import com.zzbcome.domain.GetInfoFromNet;

public class FileUtil {

	/**
	 * @param file	文件名，包含路径
	 * @param conent	要写入文件的信息
	 * @param addpend	是否是在尾部继续写入
	 * @param encoding	写文件的编码格式
	 */
	public static void writeTxt(String file, String conent, String encoding, boolean addpend) {
		BufferedWriter out = null;
		try {
			if(file.lastIndexOf("/")!=-1){
				File f = new File(file.substring(0, file.lastIndexOf("/")));
				if(!f.exists()||f.isDirectory()){
					f.mkdir();
				}
			}
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(file), addpend), encoding));
			out.write(conent + "\r\n");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 	jsp页面-广东详情页解析
	 * @param args
	 * @throws IOException
	 */
	public static void guangdongParse(String[] args) throws IOException {
		Map<String, String> config = GetInfoFromNet.config;
		// 设置fiddler代理
		if (config!= null &&!config.get("http.proxySet").isEmpty()
				&& config.get("http.proxySet").toUpperCase().equals("TRUE") && !config.get("http.proxyHost").isEmpty()
				&& !config.get("http.proxyPort").isEmpty()) {
			System.setProperty("http.proxySet", config.get("http.proxySet"));
			System.setProperty("http.proxyHost", config.get("http.proxyHost"));
			System.setProperty("http.proxyPort", config.get("http.proxyPort"));
		}
		String fileName = "resource/jsp.jsp";
		//String url = "http://www.gsxt.gov.cn/%7BCEpyBksV28gWFGCNdV9gplrIYIeKK6NHG_59P1-IxINf7QtysJ-HxtdFFVM7VHJKfEp-pjJKa61i4W1WJjvU_is6WmGrrkcLTSj3q5RZduEBra_X2i3FGeqRwwp7NmNbBva0CJRDZm7UMUtKQcTTsQ-1508375479401%7D";
		String url = "";
		Document doc = null;
		if(url.isEmpty()){
			File f = new File(fileName);
			doc = Jsoup.parse(f, "utf-8");
			
		}else {
			doc = Jsoup.connect(url).get();
		}
        Elements contents = doc.getElementsByClass("infoStyle");
        Element div = contents.get(0);
        Element table = div.getElementsByTag("table").get(0);
        Elements trs = table.getElementsByTag("tr");
        for (Element tr : trs) 
        {
            Elements td=tr.getElementsByTag("td");
            for (int i = 0; i<td.size(); i++) 
            {
                Elements spans = td.get(i).getElementsByTag("span");
                for(Element span:spans){
                	System.out.print(span.text());
                }
                System.out.println();
            }
        }
    }
	public static void main(String[] args) throws IOException {
		Map<String, String> config = GetInfoFromNet.config;
		// 设置fiddler代理
		if (config!= null &&!config.get("http.proxySet").isEmpty()
				&& config.get("http.proxySet").toUpperCase().equals("TRUE") && !config.get("http.proxyHost").isEmpty()
				&& !config.get("http.proxyPort").isEmpty()) {
			System.setProperty("http.proxySet", config.get("http.proxySet"));
			System.setProperty("http.proxyHost", config.get("http.proxyHost"));
			System.setProperty("http.proxyPort", config.get("http.proxyPort"));
		}
		String fileName = "resource/jsp.jsp";
		String url = "http://www.gsxt.gov.cn/%7BCEpyBksV28gWFGCNdV9gplrIYIeKK6NHG_59P1-IxINf7QtysJ-HxtdFFVM7VHJKfEp-pjJKa61i4W1WJjvU_is6WmGrrkcLTSj3q5RZduEBra_X2i3FGeqRwwp7NmNbBva0CJRDZm7UMUtKQcTTsQ-1508375479401%7D";
		Document doc = null;
		if(url.isEmpty()){
			File f = new File(fileName);
			doc = Jsoup.parse(f, "utf-8");
			
		}else {
			doc = Jsoup.connect(url).get();
		}
		Element div = doc.getElementsByClass("overview").get(0);
		Elements dls = div.getElementsByTag("dl");
		for (Element dl : dls) 
		{
			Elements dt=dl.getElementsByTag("dt");
			Elements dd=dl.getElementsByTag("dd");
			System.out.println(dt.text()+dd.text());
		}
	}
	/**
	 * 	将已知的实体实例ce写进文件filename中
	 * @param ce	实体实例
	 * @param filename	要写的文件名
	 */
	public static void writeEntity(CompanyEntity ce,String filename){
		String[] savefield = GetInfoFromNet.config.get("savefield").split(",");
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
		FileUtil.writeTxt(filename.replaceAll("bank_", "gov_"),values,GetInfoFromNet.config.get("txt-encode"),true);
	}
}
