/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.security;

/**
 * 用户和密码（包含验证码）令牌类
 * @author ThinkGem
 * @version 2013-5-19
 */
public class UsernamePasswordToken extends org.apache.shiro.authc.UsernamePasswordToken {

	private static final long serialVersionUID = 1L;

	private String captcha;
	private boolean mobileLogin;
	
	private String openId;      // 2016-1-19 增加微信openId。
	private String wxUserId;    // 2016-7-3 增加微信企业号UserId
	private String loginMethod; // 2016-1-19 增加登录的方法。"user": 用户名登录 "wx":用openId登录，
	private String tokenKey;    //  add by wyf 20160218 增加单点登录tokenKey
	private String codeWeixin;  //  added 微信code。代表是从微信的回调而来。(企业号)
	
	public UsernamePasswordToken() {
		super();
	}

	public UsernamePasswordToken(String loginMethod,String username, char[] password,
			boolean rememberMe, String host, String captcha, boolean mobileLogin, String openId,
			String wxUserId,String tokenKey,String codeWeixin) {
		super(username, password, rememberMe, host);
		this.captcha = captcha;
		this.mobileLogin = mobileLogin;
		this.openId  = openId;          // 2016-1-19 增加微信openId。
		this.wxUserId= wxUserId;        // 2016-7-3 增加微信企业号UserId
		this.loginMethod = loginMethod; // 2016-1-19 增加登录的方法。
		this.tokenKey=tokenKey;         // add by wyf 20160218 增加单点登录tokenKey
		this.codeWeixin =codeWeixin;    // added 微信code。代表是从微信的回调而来。(企业号)
	}

	public String getWxUserId() {
		return wxUserId;
	}

	public void setWxUserId(String wxUserId) {
		this.wxUserId = wxUserId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public boolean isMobileLogin() {
		return mobileLogin;
	}

	public void setLoginMethod(String loginMethod) {
		this.loginMethod = loginMethod;
	}

	public String getLoginMethod() {
		return loginMethod;
	}
	
	public String getCodeWeixin() {
		return codeWeixin;
	}

	public void setCodeWeixin(String codeWeixin) {
		this.codeWeixin = codeWeixin;
	}

	public void setMobileLogin(boolean mobileLogin) {
		this.mobileLogin = mobileLogin;
	}

	public void setTokenKey(String tokenKey){
		this.tokenKey=tokenKey;
	}
	public String getTokenKey(){
		return this.tokenKey;
	}
}