package com.zzbcome.bean;

public class CompanyEntity {

	//#企业名称、统一社会信用代码/注册号、法定代表人、营业期限至、注册资本、住所、经营范围、是否列入经营异常名录、是否列入严重违法失信企业名单（黑名单）
	public String entname;
	public String regno;
	public String name;
	public String enddate;
	public String opto;
	public String regcap;
	public String dom;
	public String opscope;
	public String busexceptcount;
	public String ceptcount;
	
	public String pripid;
	public String enttype;
	public String s_ext_nodenum;;
	
	public String getEntname() {
		return entname;
	}
	public void setEntname(String entname) {
		this.entname = entname;
	}
	public String getRegno() {
		return regno;
	}
	public void setRegno(String regno) {
		this.regno = regno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getOpto() {
		return opto;
	}
	public void setOpto(String opto) {
		this.opto = opto;
	}
	public String getRegcap() {
		return regcap;
	}
	public void setRegcap(String regcap) {
		this.regcap = regcap;
	}
	public String getDom() {
		return dom;
	}
	public void setDom(String dom) {
		this.dom = dom;
	}
	public String getOpscope() {
		return opscope;
	}
	public void setOpscope(String opscope) {
		this.opscope = opscope;
	}
	public String getBusexceptcount() {
		return busexceptcount;
	}
	public void setBusexceptcount(String busexceptcount) {
		this.busexceptcount = busexceptcount;
	}
	public String getCeptcount() {
		return ceptcount;
	}
	public void setCeptcount(String ceptcount) {
		this.ceptcount = ceptcount;
	}
	public String getPripid() {
		return pripid;
	}
	public void setPripid(String pripid) {
		this.pripid = pripid;
	}
	public String getEnttype() {
		return enttype;
	}
	public void setEnttype(String enttype) {
		this.enttype = enttype;
	}
	public String getS_ext_nodenum() {
		return s_ext_nodenum;
	}
	public void setS_ext_nodenum(String s_ext_nodenum) {
		this.s_ext_nodenum = s_ext_nodenum;
	}
	
}
