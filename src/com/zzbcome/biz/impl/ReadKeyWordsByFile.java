package com.zzbcome.biz.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zzbcome.bean.CompanyEntity;
import com.zzbcome.biz.ReadKeyWords;
import com.zzbcome.domain.GetInfoFromNet;
import com.zzbcome.domain.MainMethod;
import com.zzbcome.utils.FileUtil;

/**
 * @author zzbcome
 * Desription	读取txt文件中的公司名称或社会信用码查询资料
 *
 */
public class ReadKeyWordsByFile implements ReadKeyWords {

	@Override
	public void getKeyWord() {
		List<CompanyEntity> list = new ArrayList<CompanyEntity>();
		Map<String, String> map = GetInfoFromNet.config;
		String savePath = MainMethod.config.get("savepath")==null?"":MainMethod.config.get("savepath");
		String filename = savePath+MainMethod.config.get("save-file-name");
		if(filename.lastIndexOf("/")!=-1){
			File f = new File(filename.substring(0, filename.lastIndexOf("/")));
			if(!f.exists()||f.isDirectory()){
				f.mkdir();
			}
		}
		try{
			InputStreamReader reader = new InputStreamReader(new FileInputStream(new File(filename)));
			BufferedReader br = new BufferedReader(reader);
			
			String str = null;
			int i = 1;//数据文件行号
			while((str = br.readLine()) != null) {
				System.out.println("正在读取第"+i+"行数据！");
				String []key = str.trim().split(map.get("split_str"));
				//根据企业名称获取查询网上数据
				boolean success = GetInfoFromNet.OnceLinkNet(URLEncoder.encode(key[0]), list);
				if(!success){//如果企业名称没有查询到结果，再根据社会信用码进行查询
					success = GetInfoFromNet.OnceLinkNet(URLEncoder.encode(key[1]), list);
				}
				
				if(list.size()>0&&success){
					FileUtil.writeEntity(list.get(i-1), filename);
				}
				i++;
			}
			br.close();
			reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		GetInfoFromNet.showList(list);
	}

}
