package com.fastweixin.model;

/**
 * 微信JS-SDK的签名Model。
 * */
public class WXSignModel{
	
	private String url;     // "http://www.holdbuild.com/XXX.html"
	private String appid;      
	private String timestamp;     
	private String nonceStr;      
	private String signature;
	
	public WXSignModel(){
	}
	
	public WXSignModel(String url,String  appid, long  timestamp, 
			           String nonceStr, String signature){
		this.url         = url;
		this.appid       = appid;
		this.timestamp   = Long.toString(timestamp);
		this.nonceStr    = nonceStr;
		this.signature   = signature;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	
}
