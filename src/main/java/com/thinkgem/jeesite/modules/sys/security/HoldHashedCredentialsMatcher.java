package com.thinkgem.jeesite.modules.sys.security;


import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.service.PermissionService;

/**
 * 覆盖Shiro框架的密码比较类，实现微信等自动登录。
 * @since 
 */
public class HoldHashedCredentialsMatcher extends HashedCredentialsMatcher {

	private static final Logger log = LoggerFactory.getLogger(HoldHashedCredentialsMatcher.class);
	
	public HoldHashedCredentialsMatcher(String hashAlgorithm) {
		
		super(hashAlgorithm);
	}

	/**
	 * 2016-1-19。覆盖密码检验类HashedCredentialsMatcher的父类的密码比较方法，实现微信等自动登录。
	 * */
	 @Override
	 public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		 // 1)微信免登陆的验证
		 if(   "wx".equals( ((UsernamePasswordToken)token).getLoginMethod())
		    && (StringUtils.isNotBlank(((UsernamePasswordToken)token).getOpenId())||
		        StringUtils.isNotBlank(((UsernamePasswordToken)token).getWxUserId()))
		   ){
			 // 如果登录方式为微信wx，并且openId(或wxUserId)已获取到.则系统认为密码验证通过。
			 return true;
		 } //2) Token登录的验证
		 else if(StringUtils.isNotBlank(((UsernamePasswordToken)token).getTokenKey())){
			 
			 return PermissionService.CheckLogin((UsernamePasswordToken)token);
		 }
		 else
		 {  // 3)其他的 用户名密码的验证。
	        Object tokenHashedCredentials = hashProvidedCredentials(token, info);
	        Object accountCredentials = getCredentials(info);
	        return equals(tokenHashedCredentials, accountCredentials);
		 }
	 }
}