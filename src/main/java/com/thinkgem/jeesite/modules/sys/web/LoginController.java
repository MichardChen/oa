/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.JQResultModel;
import com.thinkgem.jeesite.common.security.shiro.session.SessionDAO;
import com.thinkgem.jeesite.common.servlet.ValidateCodeServlet;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.CookieUtils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.dao.DictDao;
import com.thinkgem.jeesite.modules.sys.entity.Menu;
import com.thinkgem.jeesite.modules.sys.model.MenuModel;
import com.thinkgem.jeesite.modules.sys.security.FormAuthenticationFilter;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.thinkgem.jeesite.modules.sys.service.PermissionService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 登录Controller
 * @author ThinkGem
 * @version 2013-5-31
 */
@Controller
public class LoginController extends BaseController{
	
	private SystemService systemService;
	
	@Autowired
	private SessionDAO sessionDAO;
	
	@Autowired
	private DictDao dictDao;
	/**
	 * 管理登录
	 */
	@RequestMapping(value = "${adminPath}/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
		Principal principal = UserUtils.getPrincipal();
//		// 默认页签模式
//		String tabmode = CookieUtils.getCookie(request, "tabmode");
//		if (tabmode == null){
//			CookieUtils.setCookie(response, "tabmode", "1");
//		}
		
		if (logger.isDebugEnabled()){
			logger.debug("login, active session size: {}", sessionDAO.getActiveSessions(false).size());
		}
		
		// 如果已登录，再次访问主页，则退出原账号。
		if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))){
			CookieUtils.setCookie(response, "LOGINED", "false");
		}
		
		//如果单点登录（有跳转链接，则跳转到对应网址)--add by wyf 20160217
		String redirect_uri= PermissionService.GetRedirectUri(request);
		if(StringUtils.isNotBlank(redirect_uri)){
			return "redirect:"+redirect_uri;
		}
		// 如果已经登录，则跳转到管理首页(即转到 public String index(HttpServletRequest request)
		// 注意：在index的处理中，对于手机和APP登录（httpPost)，返回json串。
		////if(principal != null && !principal.isMobileLogin()){
		if(principal != null ){
			return "redirect:" + adminPath;
		}
		
//		String view;
//		view = "/WEB-INF/views/modules/sys/sysLogin.jsp";
//		view = "classpath:";
//		view += "jar:file:/D:/GitHub/jeesite/src/main/webapp/WEB-INF/lib/jeesite.jar!";
//		view += "/"+getClass().getName().replaceAll("\\.", "/").replace(getClass().getSimpleName(), "")+"view/sysLogin";
//		view += ".jsp";
		System.out.println("L108");
		
		return "modules/sys/sysLogin";
	}

	/**
	 * 登录失败，真正登录的POST请求由Filter完成(注意：登录失败可能是因为已经登录，也可能因为密码不对)
	 */
	@RequestMapping(value = "${adminPath}/login", method = RequestMethod.POST)
	public String loginFail(HttpServletRequest request, HttpServletResponse response, Model model) {
		Principal principal = UserUtils.getPrincipal();
		
		System.out.println("Login Fail");
		// 如果已经登录，则跳转到管理首页（PC端)，返回JSON(移动端)。
		if(principal != null){
			// 获取错误消息。
			String message = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM);
			boolean mobile = principal.isMobileLogin();
					// WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_MOBILE_PARAM); 2016721 改成从登录凭证(principal)中获取，确保有值。
			
			// 2016-1-25 added start.mlg.判断登录方式是否为微信或手机。
			String loginMethod = principal.getLoginMethod();
			if(mobile || "wx".equals(loginMethod)){
				Map<String,String> rtnMap = new HashMap<String,String>();
				// 判断登录是否成功。即FormAuthenticationFilter的onLoginFailure()函数中是否给message赋值。
				if (org.apache.commons.lang3.StringUtils.isBlank(message) || org.apache.commons.lang3.StringUtils.equals(message, "null")){
					rtnMap = getRtnMapForWx(true);
				}
				else{ // 登录失败
					rtnMap.put("error","1");
					rtnMap.put("errorCode","1001");
					rtnMap.put("errCode"  ,"1001");
					rtnMap.put("message",message); // 把onLoginFailure()函数中的message返回去。
					rtnMap.put("url",""); // 跳回登录页面
				}
				return renderString(response, rtnMap);
			}
			// 2016-1-25 added end. mlg.
			return "redirect:" + adminPath;
		}

		String username = WebUtils.getCleanParam(request, org.apache.shiro.web.filter.authc.FormAuthenticationFilter.DEFAULT_USERNAME_PARAM);
		boolean rememberMe = WebUtils.isTrue(request, org.apache.shiro.web.filter.authc.FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM);
		
		Boolean mobileLogin = (Boolean)request.getAttribute(FormAuthenticationFilter.DEFAULT_MOBILE_PARAM);
		boolean mobile = mobileLogin.booleanValue();
				// WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_MOBILE_PARAM); 2016721 改成从request的Attribute中获取，确保有值。
		String exception = (String)request.getAttribute(org.apache.shiro.web.filter.authc.FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		String message = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM);
		
		if (org.apache.commons.lang3.StringUtils.isBlank(message) || org.apache.commons.lang3.StringUtils.equals(message, "null")){
			message = "用户或密码错误, 请重试.";
		}

		model.addAttribute(org.apache.shiro.web.filter.authc.FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, username);
		model.addAttribute(org.apache.shiro.web.filter.authc.FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM, rememberMe);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_MOBILE_PARAM, mobile);
		model.addAttribute(org.apache.shiro.web.filter.authc.FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, exception);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM, message);
		
		if (logger.isDebugEnabled()){
			logger.debug("login fail, active session size: {}, message: {}, exception: {}", 
					sessionDAO.getActiveSessions(false).size(), message, exception);
		}
		
		// 非授权异常，登录失败，验证码加1。
		if (!UnauthorizedException.class.getName().equals(exception)){
			model.addAttribute("isValidateCodeLogin", isValidateCodeLogin(username, true, false));
		}
		
		// 验证失败清空验证码
		request.getSession().setAttribute(ValidateCodeServlet.VALIDATE_CODE, IdGen.uuid());
		
		// 2016-1-27 deleted start. mlg
		// 如果是手机登录，则返回JSON字符串
		//if (mobile){
	    //    return renderString(response, model);
		//}
		// 2016-1-27 deleted end.   mlg
		
		// 2016-1-25 added start.mlg.判断登录方式是否为微信或手机。返回JSON。
		String loginMethod = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_LOGINMETHOD_PARAM);
				// request.getParameter("loginMethod"); 2016721 改成从request的Attribute中获取，确保有值。
		
		if(mobile || "wx".equals(loginMethod)){
			
			if("wx".equals(loginMethod)){
				// 如果是从微信回调(GET)登录，则返回sysLogin页面。注意：移动端的sysIndex会转发到login.html
				String codeWeixin = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_CODE_PARAM);
				if( !StringUtils.equalsIgnoreCase(principal.getCodeWeixin(), "none") ){
					return "modules/sys/sysLogin";
				}
			}
			
			Map<String,String> rtnMap = new HashMap<String,String>();
			// 判断登录是否成功。即FormAuthenticationFilter的onLoginFailure()函数中是否给message赋值。
			if (org.apache.commons.lang3.StringUtils.isBlank(message) || org.apache.commons.lang3.StringUtils.equals(message, "null")){
				rtnMap = getRtnMapForWx(true);
			}
			else{ // 登录失败
				rtnMap.put("error","1");
			    rtnMap.put("errorCode","1001");
			    rtnMap.put("errCode"  ,"1001");
			    rtnMap.put("message", message); // 把onLoginFailure()函数中的message返回去。
			    rtnMap.put("url",""); // 跳回登录页面
			}
			return renderString(response, rtnMap);
		}
		// 2016-1-25 added end. mlg.
	    System.out.println("L217");
	    
		// 回到PC端的登录页面
		return "modules/sys/sysLogin";
	}

	/**
	 * 登录成功，进入管理首页
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "${adminPath}")
	public String index(HttpServletRequest request, HttpServletResponse response,Model model) {
		// 登录成功，principal一定有值。
		Principal principal = UserUtils.getPrincipal();
		
		System.out.println("Login OK");
		// 获取请求页面的地址
		String requestUrl = request.getHeader("REFERER");
		if(requestUrl==null){
			requestUrl="REFERER is empty";
		}
		System.out.println(requestUrl);
		
		// 登录成功后，验证码计算器清零
		isValidateCodeLogin(principal.getLoginName(), false, true);
		
		if (logger.isDebugEnabled()){
			logger.debug("show index, active session size: {}", sessionDAO.getActiveSessions(false).size());
		}
		
		// 如果已登录，再次访问主页，则退出原账号。(2016-1-27 目前没用到)
		if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))){
			String logined = CookieUtils.getCookie(request, "LOGINED");
			if (org.apache.commons.lang3.StringUtils.isBlank(logined) || "false".equals(logined)){
				CookieUtils.setCookie(response, "LOGINED", "true");
			}else if (org.apache.commons.lang3.StringUtils.equals(logined, "true")){
				UserUtils.getSubject().logout();
				
				System.out.println("L246");
				return "redirect:" + adminPath + "/login";
			}
		}
		
		String loginMethod = principal.getLoginMethod();
		// 1) 对于PC方式，才需要获取获取工程列表等信息,指向主页(sysIndex)
		if("pc".equals(loginMethod) && !principal.isMobileLogin()){
			// 每次登陆后，自动刷新session。
			super.refreshSession(request, response);
			
			// 3) XXXXXXXXX
			// 4)判断是否为测试环境。（从DICT的缓存中)。
			// 取值  "product","测试版本","试用版本"
			// 如果取值不是product，则在主页上显示 版本名
			String productSign = DictUtils.getDictLabel("productSign", "productSign", "测试版本");
			if(productSign!=null){
				if(!"product".equals(productSign)){
					model.addAttribute("productSign",productSign);
				}
			}
			// 5)获取页签用的菜单列表。
			List<Menu> menuListOrg = UserUtils.getMenuList();
			List<MenuModel> menuList = new ArrayList<MenuModel>();
			
			for(int i=0; i< menuListOrg.size();i++){
				MenuModel menuModel = new MenuModel();
				menuModel.setName(menuListOrg.get(i).getName());
				menuModel.setHref(menuListOrg.get(i).getHref());
				
				menuList.add(menuModel);
			}
			JQResultModel resultModel= new JQResultModel();
			resultModel.setRows(menuList);
			model.addAttribute("menuListForPC",JSON.toJSONString(resultModel));
			
			// 进入首页（PC登录时使用)
			return "modules/sys/sysIndex";
		}
		// request.getSession().setAttribute("aaa", "aa");
		// end xmhold 2015-11-19
		
		// 2016-1-27 deleted start. mlg
		// 如果是手机登录，则返回JSON字符串
		//if (principal.isMobileLogin()){
		//	if (request.getParameter("login") != null){
		//		return renderString(response, principal);
		//	}
		//	if (request.getParameter("index") != null){
		//		return "modules/sys/sysIndex";
		//	}
		//	return "redirect:" + adminPath + "/login";
		//}
		// 2016-1-27 deleted end.   mlg
		
		// 2016-1-25 added mlg.判断登录方式是否为微信或手机浏览器。返回JSON。
		// 2) 对于移动登录(pc端设成手机模式,手机浏览器)和微信登录(用户名登录,微信公众号&企业号免登陆)
		//   除了微信的免登陆返回sysindex外，其他形式返回JSON串。
		if(principal.isMobileLogin() || "wx".equals(loginMethod)){
			//1.如果微信登录,自动绑定微信openId到User上。
			if("wx".equals(loginMethod)){
			   // 如果是从微信回调(GET)登录成功，则返回sysIndex页面。注意：移动端的sysIndex会转发到menu.html
			   if( requestUrl.indexOf("login.html") == -1 ){
				   // 把微信(手机)页面上需要的用户信息(放在cookie中)
				   // 参数值false：无需重新获取accessToken.
				   model.addAttribute("mUserInfo",getRtnMapForWx(false));
				   if("true".equals(Global.getConfig("mobilePageWxLoginOnly"))){
				      // 增加一个标识，代表从微信的菜单登录("1"--微信端 ,"0"--其他手段 )
				      model.addAttribute("wxLoginSign","1");
				   }
				   
			       return "modules/sys/sysIndex";
			   }
			   
			   // 获取openId（从session)
			   String openId = (String)UserUtils.getSession().getAttribute("openId");
			   // 如果能获取到登录用户信息, 并且openId有值时。
			   if(StringUtils.isNotBlank(openId)){
				   //并且 未绑定(WechatOpenId为空)或openId不一致(当绑定发生变化时)，则自动绑定openId。
				   if(StringUtils.isBlank(UserUtils.getUser().getWechatOpenId())   || 
						   (!openId.equals(UserUtils.getUser().getWechatOpenId())) ){
					   // 自动绑定openId到用户信息
					   getSystemService().updateUserOpenId(UserUtils.getUser().getId(), openId);
					   // 清除openId的session.
					   UserUtils.getSession().setAttribute("openId","");
					   // 清除用户缓存。
					   UserUtils.clearCache(UserUtils.getUser());
				   }
			   }
			}
			// 获取JSON返回值(hashMap形式)
			Map rtnMap = getRtnMapForWx(true);
						
			return renderString(response, rtnMap);
		}
		
//		// 登录成功后，获取上次登录的当前站点ID
//		UserUtils.putCache("siteId", StringUtils.toLong(CookieUtils.getCookie(request, "siteId")));

//		System.out.println("==========================a");
//		try {
//			byte[] bytes = com.thinkgem.jeesite.common.utils.FileUtils.readFileToByteArray(
//					com.thinkgem.jeesite.common.utils.FileUtils.getFile("c:\\sxt.dmp"));
//			UserUtils.getSession().setAttribute("kkk", bytes);
//			UserUtils.getSession().setAttribute("kkk2", bytes);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
////		for (int i=0; i<1000000; i++){
////			//UserUtils.getSession().setAttribute("a", "a");
////			request.getSession().setAttribute("aaa", "aa");
////		}
//		System.out.println("==========================b");
		
		//3) Token登录方式
		if("Token".equals(loginMethod)){
			//如果单点登录（有跳转链接，则跳转到对应网址--add by wyf 20160217
			String redirect_uri= PermissionService.GetRedirectUri(request);
			if(StringUtils.isNotBlank(redirect_uri)){
				return "redirect:"+redirect_uri;
			}
		}
		// 4) APP登录
		if("Android".equals(loginMethod)||"IOS".equals(loginMethod)){
			// 获取JSON返回值(hashMap形式)
			Map rtnMap = getRtnMapForWx(true);
			return renderString(response, rtnMap);
		}
		
		// 进入首页（PC登录时使用)
		return "modules/sys/sysIndex";
	}
	
	/***生成移动网页用的Map
	 * @param 是否需要重新获取AccessToken。True:需要，False:不需要。
	 * */
	private Map getRtnMapForWx(boolean getAccessToken){
		
		Map rtnMap = new HashMap();
		// 1)返回值
		rtnMap.put("error","0");
		rtnMap.put("errorCode","4023");
		rtnMap.put("errCode"  ,"4023");
		rtnMap.put("message","登录成功!");
		rtnMap.put("url",""); //
		rtnMap.put("UserCode",UserUtils.getUser().getMobile());     // 用户手机号
		rtnMap.put("LoginName",UserUtils.getUser().getLoginName()); // 用户登录名
		rtnMap.put("UserId", UserUtils.getUser().getId());          // 用户id
		rtnMap.put("UserName",UserUtils.getUser().getName());       // 用户姓名
		rtnMap.put("wxUserId",UserUtils.getUser().getWxUserId());   // 企业号的用户账号
		rtnMap.put("roleNames",UserUtils.getUser().getRoleNames()); // 用户角色列表，用逗号隔开
		// 微信绑定的情况
		rtnMap.put("weixinBind",StringUtils.isBlank(UserUtils.getUser().getWechatOpenId())?"0":"1");
		rtnMap.put("currProjectId",UserUtils.getUser().getCurrProjectId());
		// 2) accessToken
		//  ** 如果从来没有获取过AccessToken，则必须获取。传参的值为true，也要获取。
		if(  StringUtils.isBlank(UserUtils.getUser().getWxAccessToken())
		   ||getAccessToken == true){
			String wxAccessToken = IdGen.uuid();
			getSystemService().updateWxAccessToken(UserUtils.getUser().getId(),wxAccessToken);
		}
		rtnMap.put("wxAccessToken",UserUtils.getUser().getWxAccessToken());
		// 3) 获取登录用户分配的菜单信息。
		List<Menu> menuList = UserUtils.getMenuList();
		List<Map> menuListRtn = new ArrayList<Map>();
		for(int i=0;i< menuList.size();i++){
			// 把PC端的菜单过滤掉,保留手机菜单(非隐藏)
			if("mb".equals(menuList.get(i).getMedia()) && "1".equals(menuList.get(i).getIsShow())){
			   Map menu = new HashMap();
			   menu.put("href",menuList.get(i).getHref());
			   menu.put("icon",menuList.get(i).getIcon());
			   menu.put("name",menuList.get(i).getName());
			   menuListRtn.add(menu);
			}
		}
		rtnMap.put("menuList", menuListRtn);
		// 4) 获取所有的手机菜单
		rtnMap.put("allMenuList", UserUtils.getAllMobileMenu());
		
		return rtnMap;
	}
		
	/**
	 * 获取主题方案
	 */
	@RequestMapping(value = "/theme/{theme}")
	public String getThemeInCookie(@PathVariable String theme, HttpServletRequest request, HttpServletResponse response){
		if (org.apache.commons.lang3.StringUtils.isNotBlank(theme)){
			CookieUtils.setCookie(response, "theme", theme);
		}else{
			theme = CookieUtils.getCookie(request, "theme");
		}
		return "redirect:"+request.getParameter("url");
	}
	
	/**
	 * 是否是验证码登录
	 * @param useruame 用户名
	 * @param isFail 计数加1
	 * @param clean 计数清零
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isValidateCodeLogin(String useruame, boolean isFail, boolean clean){
		Map<String, Integer> loginFailMap = (Map<String, Integer>)CacheUtils.get("loginFailMap");
		if (loginFailMap==null){
			loginFailMap = Maps.newHashMap();
			CacheUtils.put("loginFailMap", loginFailMap);
		}
		Integer loginFailNum = loginFailMap.get(useruame);
		if (loginFailNum==null){
			loginFailNum = 0;
		}
		if (isFail){
			loginFailNum++;
			loginFailMap.put(useruame, loginFailNum);
		}
		if (clean){
			loginFailMap.remove(useruame);
		}
		return loginFailNum >= 3;
	}
	
	/**
	 * 获取系统业务对象
	 */
	public SystemService getSystemService() {
		if (systemService == null){
			systemService = SpringContextHolder.getBean(SystemService.class);
		}
		return systemService;
	}
}
