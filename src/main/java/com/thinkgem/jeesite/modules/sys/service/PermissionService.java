package com.thinkgem.jeesite.modules.sys.service;

import java.net.URL;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.thinkgem.jeesite.common.security.Digests;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.web.Servlets;
import com.thinkgem.jeesite.modules.sys.dao.SysLoginDao;
import com.thinkgem.jeesite.modules.sys.entity.SysLogin;
import com.thinkgem.jeesite.modules.sys.security.UsernamePasswordToken;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.pj_common.Constants.PjConstants;
//权限管理，包括登陆状态检查，接口权限检查，token登陆等
public class PermissionService extends CrudService<SysLoginDao, SysLogin> {
	private static SysLoginDao sysLoginDao = SpringContextHolder.getBean(SysLoginDao.class);
	
	//是否登陆
	public static boolean IsLogin(){
		return StringUtils.isNotBlank((String)UserUtils.getSession().getAttribute(PjConstants.CUR_COMPANY_ID))
				&& StringUtils.isNotBlank(UserUtils.getUser().getId());
	}
	
	public static boolean IsNull(String str){
		return str==null || str.equals("");
	}
	
	//退出登录
	public static void Logout(){
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();
	}
	
	//用户登陆
	public static boolean Login(String TokenKey){
		return Login(new UsernamePasswordToken(null,null,null,false,null,null,false,null,null,TokenKey,null));
	}
	
	public static boolean Login(UsernamePasswordToken Token){
		try{
			if(IsLogin()) return true;
		    Subject currentUser = SecurityUtils.getSubject();
		    currentUser.login(Token);
		    HttpServletRequest request =Servlets.getRequest();
		    BaseController.refreshSessionFunc(UserUtils.getSession());
		}
		catch(Exception e){			
		}
		return IsLogin();
	}
	//页面、方法、接口权限检查
	//未完成
	public static boolean CheckPermission(String KeyWord){
		if(!IsLogin()) return false;//未登录则返回false
		String[] sList=KeyWord.split(".");
		switch(sList.length){
			case 1://查询页面权限
				if(IsNull(KeyWord)){//无权限码，因此是查询整体权限，返回所有权限的菜单列表
					return true;
				}
				else{//查询页面权限
					if(CheckPagePermission(sList[0])){
						//返回用户在此页面所有下级权限信息（非菜单信息）
						return true;
					}
					else{
						return false;
					}
				}
			case 2://验证是否有某个按钮、方法、接口的权限
				return CheckFuncPermission(sList[0],sList[1]);
			default:return false;
		}
	}
	//检查是否有某个页面的权限
	//未完成
	private static boolean CheckPagePermission(String PageKey){
		return true;
	}
	//检查是否有某个按钮的权限
	private static boolean CheckFuncPermission(String PageKey,String ButtonKey){
		if(!CheckPagePermission(PageKey)) return false;
		return true;
	}
	
	//判断是否登陆动作并且携带了跳转url，如果是则返回跳转url并加上Token参数，否则返回null
	public static String GetRedirectUri(HttpServletRequest request){
		//判断是否登陆动作(由referer判断）并且携带了跳转url信息
		try{
			String redirect_uri=request.getParameter("redirect_uri");			
			if(StringUtils.isBlank(redirect_uri)){//登陆按钮点击时分支
				URL u=new URL(request.getHeader("referer"));
				if(u.getPath().equals("/ConstructCost/a/login") || u.getPath().equals("/ConstructCost/static/ccmobile/pages/login.html")){
					String[] sParam=u.getQuery().split("&");
					for(String s: sParam){
						if(s.contains("=")){
							String[] param=s.split("=");
							if(param[0].equals("redirect_uri")){//携带跳转url信息则将信息写入session
								redirect_uri=java.net.URLDecoder.decode(param[1],"UTF-8");
								if(u.getPath().equals("/ConstructCost/static/ccmobile/pages/login.html")){//手机登陆页面时，是登陆按钮动作
									URL u2 = new URL(redirect_uri);				
									String TokenKey = GetTempCodeKey();
									InsertCode(TokenKey);
									redirect_uri=redirect_uri+(StringUtils.isBlank(u2.getQuery())?"?":"&")+"code="+TokenKey;
								}
								return redirect_uri;
							}
						}
					}
				}
			}
			else{ //访问登陆页面时
				Enumeration<String> en=request.getParameterNames();
				String sTempQuery="";
				boolean IsLogout=false;
				while (en.hasMoreElements()) {
					String s= en.nextElement();
			        if(s.equals("logout")){
			        	Logout();
			        	IsLogout=true;
			        }
			        else{
			        	sTempQuery= sTempQuery + (sTempQuery.length()>0?"&":"")+ s+"="+java.net.URLEncoder.encode(request.getParameter(s),"UTF-8");
			        }
			    }
				if(IsLogout){
					redirect_uri=request.getRequestURI().replace("ConstructCost/", "")+(sTempQuery.length()>0?"?":"")+sTempQuery;
				}
				else if(IsLogin()){//如果已登录，返回
					URL u = new URL(redirect_uri);				
					String TokenKey = GetTempCodeKey();
					InsertCode(TokenKey);
					redirect_uri=redirect_uri+(StringUtils.isBlank(u.getQuery())?"?":"&")+"code="+TokenKey;
				}
				else{
					redirect_uri="";
				}
				return redirect_uri;
			}
		}
		catch(Exception e){
			String s=e.getMessage();
		}
		return null;
	}
	
	//获取Token，使用CodeKey或者
	public static String GetTokenKey(String CodeKey) {
		String Token="";
		Token=sysLoginDao.getTokenKey(StringUtils.toString(UserUtils.getUser().getId()),CodeKey);
		if(StringUtils.isBlank(Token) && StringUtils.isNotBlank(CodeKey)){
			Token=GetTempCodeKey();
		}
		FlushToken(Token,CodeKey);
		/*
		if(StringUtils.isNotBlank(CodeKey)){
			
			
		}
		else{
			try{
				Token=sysLoginDao.getTokenKey(UserUtils.getUser().getId(),"");
				if(StringUtils.isNotBlank(Token)) return Token;
			}
			catch(Exception e){
				System.out.println("Error");
			}
		}*/
		return Token;
	}
	
	//产生随机码（一次性）
	public static String GetTempCodeKey() {
		java.util.Date d=new java.util.Date();
		String TempKey=UserUtils.getUser().getId()+d.getTime();//用户ID加上当前时间作为计算码
		String CodeKey=Digests.getFormattedText(Digests.sha1(TempKey));
		return CodeKey;
	}
	
	//将新的一次性Code插入/更新数据库
	public static boolean InsertCode(String Code){
		Date d=new Date();	
		SysLogin sysLogin=new SysLogin();
		sysLogin.setId(UserUtils.getUser().getId());
		sysLogin.setUserId(UserUtils.getUser().getId());
		sysLogin.setCode(Code);
		sysLogin.setLoginDate(d);
		sysLogin.setMemberId((String) UserUtils.getSession().getAttribute(
				PjConstants.CUR_COMPANY_ID));
		int irtn = 0;
		try{
			irtn=sysLoginDao.update(sysLogin);		    
		    if(irtn==0){
		    	irtn = sysLoginDao.insert(sysLogin);	
		    }
		}
		catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		return irtn>0;
	}
	
	public static boolean FlushToken(String TokenKey){
		return FlushToken(TokenKey,"");
	}
	//刷新Token有效时间,必须TokenKey为新TokenKey或者该TokenKey有效时间在半小时内
	public static boolean FlushToken(String TokenKey,String CodeKey){
		Date d=new Date();	
		SysLogin sysLogin=new SysLogin();
		sysLogin.setId(UserUtils.getUser().getId());
		sysLogin.setUserId(UserUtils.getUser().getId());
		if(StringUtils.isNotBlank(CodeKey)){
			sysLogin.setCode(CodeKey);
			sysLogin.setLoginDate(d);
		}
		if(StringUtils.isNotBlank(TokenKey)){
			sysLogin.setToken(TokenKey);
			sysLogin.setTokenDate(d);
		}
		sysLogin.setMemberId((String) UserUtils.getSession().getAttribute(
				PjConstants.CUR_COMPANY_ID));
		int irtn = 0;
		try{
			irtn=sysLoginDao.update(sysLogin);		    
		    if(irtn==0){
		    	irtn = sysLoginDao.insert(sysLogin);	
		    }
		}
		catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		return irtn>0;
	}
	
	//检查tokenKey是否有效
	public static boolean CheckLogin(UsernamePasswordToken token) {
		return StringUtils.isNotBlank(sysLoginDao.CheckTokenKey(token.getTokenKey()));
	}
}
