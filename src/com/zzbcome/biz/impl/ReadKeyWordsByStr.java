package com.zzbcome.biz.impl;

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
 * Description	根据str读取搜索关键词keyword
 *
 */
public class ReadKeyWordsByStr implements ReadKeyWords {

	@Override
	public void getKeyWord() {
		Map<String, String> map = GetInfoFromNet.config;
		String filename = map.get("savepath")+"bank_20171016.txt";
		String keywords = map.get("keywords");
		String []keyword = keywords.split(",");
		List<CompanyEntity> list = new ArrayList<CompanyEntity>();
		for(int i=0;i<keyword.length;i++){
			GetInfoFromNet.OnceLinkNet(URLEncoder.encode(keyword[i]), list);
			FileUtil.writeEntity(list.get(i), filename);
		}
		GetInfoFromNet.showList(list);
	}

}
