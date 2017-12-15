/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.fastweixin.api.OauthAPI;
import com.fastweixin.api.config.ApiConfig;
import com.fastweixin.api.response.OauthGetTokenResponse;
import com.fastweixin.company.api.QYOauthAPI;
import com.fastweixin.company.api.QYUserAPI;
import com.fastweixin.company.api.config.QYAPIConfig;
import com.fastweixin.company.api.response.GetOauthUserInfoResponse;
import com.thinkgem.jeesite.common.utils.StringUtils;

/**
 * 表单验证（包含验证码）过滤类
 * 
 * @author ThinkGem
 * @version 2014-5-19
 */
@Service
public class FormAuthenticationFilter extends
		org.apache.shiro.web.filter.authc.FormAuthenticationFilter {

	public static final String DEFAULT_CAPTCHA_PARAM = "validateCode";
	public static final String DEFAULT_MOBILE_PARAM = "mobileLogin";
	public static final String DEFAULT_MESSAGE_PARAM = "message";
	public static final String DEFAULT_CODE_PARAM    = "code"; // 微信企业号（公众号）的code.
	public static final String DEFAULT_STATE_PARAM   = "state"; // 微信企业号(qy)，公众号区分(mp)。没有传值时，当成公众号。
	public static final String DEFAULT_OPENID_PARAM  = "openId";
	public static final String DEFAULT_LOGINMETHOD_PARAM = "loginMethod";
	public static final String DEFAULT_TOKENKEY_PARAM = "tokenKey"; // add by wyf
																	// 20160218
																	// 单点登录token

	private String captchaParam = DEFAULT_CAPTCHA_PARAM;
	private String mobileLoginParam = DEFAULT_MOBILE_PARAM;
	private String messageParam = DEFAULT_MESSAGE_PARAM;
	private String openIdParam = DEFAULT_OPENID_PARAM;
	private String codeParam   = DEFAULT_CODE_PARAM;
	private String stateParam   = DEFAULT_STATE_PARAM;
	private String loginMethodParam = DEFAULT_LOGINMETHOD_PARAM;
	private String tokenKeyParam = DEFAULT_TOKENKEY_PARAM;

	protected AuthenticationToken createToken(ServletRequest request,
			ServletResponse response) {
		String username = getUsername(request);
		String password = getPassword(request);
		if (password == null) {
			password = "";
		}
		boolean rememberMe = isRememberMe(request);
		String host = StringUtils.getRemoteAddr((HttpServletRequest) request);
		String captcha = getCaptcha(request);
		boolean mobile = isMobileLogin(request); // 是否移动端。mobile

		// 2016-1-19 增加微信的openId
		String loginMethod = "pc";// 登录方式  pc: 用户名和密码 (默认方式),重定向到pc端的主页(sysIndex)。
		                          //       wx: 微信号。2种方式 免登陆 和 账号登录
		                          //     如果code参数有值,则根据wxtype(state的参数传值，有mp,qy 两种
		//https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxbdf8591cc4fce8a2&redirect_uri=http%3a%2f%2fwww.zhaojx.net%3a8090%2fatminspect%2fa%2flogin&response_type=code&scope=snsapi_base&state=qy#wechat_redirect
		                          //)来获取openId或wxUserId的值。重定向到移动端的主页(sysIndex)。
		                          //     如果code参数无值,则用用户名和密码登录，返回JSON。
		                          //  Andriod: 用户名和密码登录，返回JSON。
		                          //      IOS: 用户名和密码登录，返回JSON。
								  //    Token: Token登录
		String openId         = ""  ;  // 微信公众号的openid
		String wxUserId       = ""  ;  // 微信企业号的Userid
 		String tokenKey       = getTokenKey(request); // 单点登录token（从request）

		String loginMethodTmp = getLoginMethod(request); // 获取登录方式（从request）
		String codeWeixin     = getCodeWeixin(request);
		// 1.处理微信的登录及绑定事宜
		//   两种登录方式：当用微信回调时，request中只有code和state两个参数，
		//                当用用户名密码登录时，request中含 loginMethod(='wx'),username,password,rememberme等参数。
		if ("wx".equals(loginMethodTmp) || StringUtils.isNotBlank(codeWeixin)) {
			loginMethod = "wx"; // 登录方式：微信登录
			mobile = true;      // 手机登录：是
			// 如果参数code存在并且不为none，则获取openId或userid
			if(StringUtils.isNotBlank(codeWeixin) && !"none".equals(codeWeixin)){
				String wxtype   = getWxType(request); //从state中获取微信类型 qy , mp
				if("qy".equals(wxtype)){ //优先判断企业号
					//获取企业号的userid
					wxUserId = getUserIdFromQyWeixinAPI(codeWeixin);
				}
				else{
					// 获取公众号的openId.
					openId = getOpenIdFromWeixinAPI(codeWeixin);
				}
				// 如果获取失败，则 openId为 "-1"
				// 注意：无论获取到值与否，这里都有给openId赋值。因为在此之后SystemAuthorizingRealm的doGetAuthenticationInfo中，
				// 以openId(wxUserId)有值来判断微信回调还是用户名密码登录。
		    }
		}
		// 2.处理app的登录
		if("Android".equals(loginMethodTmp))
		{  loginMethod = "Android";
		   // 安卓的登录
		}
		if("IOS".equals(loginMethodTmp))
		{  loginMethod = "IOS";
		   // IOS的登录
		}
		// 3. 单点登录的TOKEN
		if(StringUtils.isNotBlank(tokenKey))
		{
		   loginMethod = "Token";
		}
		// 4.第三方授权登录(QQ,微信，微博等)
		//if(){
		//}
		
		return new UsernamePasswordToken(loginMethod, username,
				password.toCharArray(), rememberMe, host, captcha, mobile,
				openId,wxUserId,tokenKey,codeWeixin);
	}

	public String getCaptchaParam() {
		return captchaParam;
	}

	/**
	 * 根据微信的code调用微信API获取 公众号的OPENID。
	 * @param codeWeixin
	 * @return "-1":获取失败，其他情况为openId。 
	 */
	public String getOpenIdFromWeixinAPI(String codeWeixin){
		
		ApiConfig apiConfig = ApiConfig.getInstance();
		OauthAPI jsApi= new OauthAPI(apiConfig);
	    // 根据code获取 openid
		OauthGetTokenResponse oauthResponse = jsApi.getToken(codeWeixin);
	    
		System.out.println(" Form Filter,L134");
		if(StringUtils.isBlank(oauthResponse.getOpenid())){
			return "-1"; // 返回错误
		}
		// 返回微信服务器返回的openId。
		return oauthResponse.getOpenid();
	}
	
	/**
	 * 根据微信的code调用微信API获取 企业号的userid。
	 * @param codeWeixin
	 * @return "-1":获取失败，其他情况为userId。 
	 */
	public String getUserIdFromQyWeixinAPI(String codeWeixin){
		
		QYAPIConfig apiConfig = QYAPIConfig.getInstance();
		QYUserAPI jsApi= new QYUserAPI(apiConfig);
	    // 根据code获取 userId
		GetOauthUserInfoResponse oauthResponse = jsApi.getOauthUserInfo(codeWeixin);
	    
		System.out.println(" Form Filter,L154");
		if(StringUtils.isBlank(oauthResponse.getUserid())){
			return "-1"; // 返回错误
		}
		// 返回微信服务器返回的Userid。
		return oauthResponse.getUserid();
	}
	
	protected String getCaptcha(ServletRequest request) {
		return WebUtils.getCleanParam(request, getCaptchaParam());
	}

	public String getMobileLoginParam() {
		return mobileLoginParam;
	}

	// 2016-7-2 获取微信类型
	protected String getWxType(ServletRequest request) {
		return WebUtils.getCleanParam(request, stateParam);
	}
	
	// 2016-1-19 获取微信openId
	protected String getOpenId(ServletRequest request) {
		return WebUtils.getCleanParam(request, openIdParam);
	}

	// 2016-1-25 获取登录方式
	public String getLoginMethodParam() {
		return loginMethodParam;
	}

	protected String getUsername(ServletRequest request) {
        return WebUtils.getCleanParam(request, getUsernameParam());
    }

    protected String getPassword(ServletRequest request) {
        return WebUtils.getCleanParam(request, getPasswordParam());
    }
    
	// 2016-1-25 获取登录方式
	protected String getLoginMethod(ServletRequest request) {
		return WebUtils.getCleanParam(request, getLoginMethodParam());
	}

	public String getTokenKeyParam() {
		return tokenKeyParam;
	}

	protected String getCodeWeixin(ServletRequest request) {
		return WebUtils.getCleanParam(request, codeParam);
	}
	
	// add by wyf 20160218 获取单点登录token
	protected String getTokenKey(ServletRequest request) {
		return WebUtils.getCleanParam(request, getTokenKeyParam());
	}

	protected boolean isMobileLogin(ServletRequest request) {
		return WebUtils.isTrue(request, getMobileLoginParam());
	}

	public String getMessageParam() {
		return messageParam;
	}

	/**
	 * 登录成功之后跳转URL
	 */
	public String getSuccessUrl() {
		return super.getSuccessUrl();
	}

	@Override
	protected void issueSuccessRedirect(ServletRequest request,
			ServletResponse response) throws Exception {
		// Principal p = UserUtils.getPrincipal();
		// if (p != null && !p.isMobileLogin()){
		WebUtils.issueRedirect(request, response, getSuccessUrl(), null, true);
		// }else{
		// super.issueSuccessRedirect(request, response);
		// }
	}
	/**
	 * 登录失败调用事件
	 */
	@Override
	protected boolean onLoginFailure(AuthenticationToken token,
			AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		String className = e.getClass().getName(), message = "";
		if (IncorrectCredentialsException.class.getName().equals(className)
				|| UnknownAccountException.class.getName().equals(className)) {
			message = "用户或密码错误, 请重试.";
		} else if (e.getMessage() != null
				&& StringUtils.startsWith(e.getMessage(), "msg:")) {
			message = StringUtils.replace(e.getMessage(), "msg:", "");
		} else {
			message = "系统出现点问题，请稍后再试！";
			e.printStackTrace(); // 输出到控制台
		}

		request.setAttribute(getFailureKeyAttribute(), className);
		request.setAttribute(getMessageParam(), message);
		// 2016-7-23 对于loginMethod,mobile,因为不一定是从request中直接获取的。而是根据CodeWeixin设值的，所以此处设值为了loginFail()中使用。
		UsernamePasswordToken userToken = (UsernamePasswordToken) token;
		request.setAttribute(DEFAULT_LOGINMETHOD_PARAM, userToken.getLoginMethod());
		request.setAttribute(DEFAULT_MOBILE_PARAM , userToken.isMobileLogin());
		request.setAttribute(DEFAULT_CODE_PARAM , userToken.getCodeWeixin());
		
		// 2016-7-3 注意：如果是PC端或微信一键登录(Oauth2)，由于登录的URI配置为${adminPath}/login，当系统发现PC端或微信一键登录(地址为${adminPath}/login)失败时，自动跳转到登录页面${adminPath}/login。
		// 但是手机端或APP登录时，登陆页面为static/XXXX/login.html或Ajax(无登录页面),与配置的${adminPath}/login不相符，则系统不会跳转到${adminPath}/login，
		// 从而不会进入LoginController的LoginFail函数，而是返回null。
		return true;
	}

}