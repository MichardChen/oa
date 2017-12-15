/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.alibaba.fastjson.JSON;
import com.thinkgem.jeesite.common.persistence.JQResultModel;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.sys.dao.AreaDao;
import com.thinkgem.jeesite.modules.sys.dao.MenuDao;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.dao.RoleDao;
import com.thinkgem.jeesite.modules.sys.dao.SysLoginDao;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.Menu;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.model.UserModel;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealm.Principal;

/**
 * 用户工具类
 * @author mlg
 * @version 2013-12-05
 */
public class UserUtils {

	private static UserDao userDao = SpringContextHolder.getBean(UserDao.class);
	private static RoleDao roleDao = SpringContextHolder.getBean(RoleDao.class);
	private static MenuDao menuDao = SpringContextHolder.getBean(MenuDao.class);
	private static AreaDao areaDao = SpringContextHolder.getBean(AreaDao.class);
	private static OfficeDao officeDao = SpringContextHolder.getBean(OfficeDao.class);
	private static SysLoginDao sysloginDao = SpringContextHolder.getBean(SysLoginDao.class);

	public static final String USER_CACHE = "userCache";
	public static final String USER_CACHE_ID_ = "id_";
	public static final String USER_CACHE_LOGIN_NAME_ = "ln";
	public static final String USER_CACHE_OPENID = "oid";
	public static final String USER_CACHE_WXUSERID = "wxuid"; //2016-7-2 增加微信企业号的UserId.
	public static final String USER_CACHE_TOKENID = "token";
	public static final String USER_CACHE_LIST_BY_OFFICE_ID_ = "oid_";

	public static final String CACHE_ROLE_LIST = "roleList";
	public static final String CACHE_MENU_LIST = "menuList";     //旧平台用
	public static final String CACHE_MENU_LIST_PC  = "menuListPC";//新的平台(PC)用
	public static final String CACHE_MENU_LIST_MB  = "menuListMB";//新的平台(手机)用
	public static final String CACHE_MENU_LIST_ALL = "allMenuList"; //所有菜单的列表
	public static final String CACHE_AREA_LIST = "areaList";
	public static final String CACHE_OFFICE_LIST = "officeList";
	public static final String CACHE_OFFICE_ALL_LIST = "officeAllList";
	
	/**
	 * 根据ID获取用户
	 * @param id
	 * @return 取不到返回null
	 */
	public static User get(String id){
		User user = (User)CacheUtils.get(USER_CACHE, USER_CACHE_ID_ + id);
		if (user ==  null){
			user = userDao.get(id);
			if (user == null){
				return null;
			}
			user.setRoleList(roleDao.findList(new Role(user)));
			CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
			CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
			CacheUtils.put(USER_CACHE, USER_CACHE_OPENID + user.getWechatOpenId(), user);
			CacheUtils.put(USER_CACHE, USER_CACHE_WXUSERID + user.getWxUserId(), user);
		}
		return user;
	}
	
	/**
	 * 根据登录名(手机号)获取用户
	 * @param loginName
	 * @return 取不到返回null
	 */
	public static User getByLoginName(String loginName){
		User user = (User)CacheUtils.get(USER_CACHE, USER_CACHE_LOGIN_NAME_ + loginName);
		if (user == null){
			user = userDao.getByLoginName(new User(null, loginName));
			if (user == null){
				return null;
			}
			user.setRoleList(roleDao.findList(new Role(user)));
			CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
			CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
			CacheUtils.put(USER_CACHE, USER_CACHE_OPENID + user.getWechatOpenId(), user);
			CacheUtils.put(USER_CACHE, USER_CACHE_WXUSERID + user.getWxUserId(), user);
		}
		return user;
	}
	
	/**
	 * 根据OPENID获取用户
	 * @param openId
	 * @return 取不到返回null
	 */
	public static User getByOpenId(String openId){
		User user = (User)CacheUtils.get(USER_CACHE, USER_CACHE_OPENID + openId);
		if (user == null){
			User userParm = new User();
			userParm.setWechatOpenId(openId);
			user = userDao.getByOpenId(userParm);
			if (user == null){
				return null;
			}
			user.setRoleList(roleDao.findList(new Role(user)));
			CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
			CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
			CacheUtils.put(USER_CACHE, USER_CACHE_OPENID + user.getWechatOpenId(), user);
			CacheUtils.put(USER_CACHE, USER_CACHE_WXUSERID + user.getWxUserId(), user);
		}
		return user;
	}
	
	/**
	 * 根据wxUserid获取用户
	 * @param wxUserId
	 * @return 取不到返回null
	 */
	public static User getByWxUserId(String wxUserId){
		User user = (User)CacheUtils.get(USER_CACHE, USER_CACHE_WXUSERID + wxUserId);
		if (user == null){
			User userParm = new User();
			userParm.setWxUserId(wxUserId);
			user = userDao.getByWxUserId(userParm);
			if (user == null){
				return null;
			}
			user.setRoleList(roleDao.findList(new Role(user)));
			CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
			CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
			CacheUtils.put(USER_CACHE, USER_CACHE_OPENID + user.getWechatOpenId(), user);
			CacheUtils.put(USER_CACHE, USER_CACHE_WXUSERID + user.getWxUserId(), user);
		}
		return user;
	}
	
	/**
	 * 根据Token获取用户
	 * @param token
	 * @return 取不到返回null
	 */
	public static User getByToken(String token){
		User user = (User)CacheUtils.get(USER_CACHE, USER_CACHE_TOKENID + token);
		if (user == null){
			
			String user_id = sysloginDao.getUserIdByToken(token);
			if(StringUtils.isNotBlank(user_id)){
				user=userDao.get(user_id);
			}
			if (user == null){
				return null;
			}
			user.setRoleList(roleDao.findList(new Role(user)));
			CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
			CacheUtils.put(USER_CACHE, USER_CACHE_TOKENID + user.getToken(), user);
			CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
			CacheUtils.put(USER_CACHE, USER_CACHE_OPENID + user.getWechatOpenId(), user);
		}
		return user;
	} 
	
	/**
	 * 清除当前用户缓存
	 */
	public static void clearCache(){
		removeCache(CACHE_ROLE_LIST);
		removeCache(CACHE_MENU_LIST);
		removeCache(CACHE_AREA_LIST);
		removeCache(CACHE_OFFICE_LIST);
		removeCache(CACHE_OFFICE_ALL_LIST);
		UserUtils.clearCache(getUser());
	}
	
	/**
	 * 清除指定用户缓存
	 * @param user
	 */
	public static void clearCache(User user){
		CacheUtils.remove(USER_CACHE, USER_CACHE_ID_ + user.getId());
		CacheUtils.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName());
		CacheUtils.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getOldLoginName());
		CacheUtils.remove(USER_CACHE, USER_CACHE_OPENID + user.getWechatOpenId());
		CacheUtils.remove(USER_CACHE, USER_CACHE_WXUSERID + user.getWxUserId());
		CacheUtils.remove(USER_CACHE, USER_CACHE_WXUSERID + user.getOldWxUserId());
		
		if (user.getOffice() != null && user.getOffice().getId() != null){
			CacheUtils.remove(USER_CACHE, USER_CACHE_LIST_BY_OFFICE_ID_ + user.getOffice().getId());
		}
	}
	
	/**
	 * 判断用户是否为管理员(super角色)
	 * @param userId:用户id。
	 * @return true:是管理员  false:非管理员
	 */
	public static boolean isAdmin(String userId){
		// 传参为空，则返回非管理员。
		if(StringUtils.isBlank(userId)){
			return false;
		}
		
		User user = get(userId);
		for(Role role:user.getRoleList()){
			if("super".equals(role.getEnname())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断用户是否为银行巡保业务经办人(bankOperator角色)
	 * @param userId:用户id。
	 * @return true:是 false:否
	 */
	public static boolean isBankOperator(String userId){
		// 传参为空，则返回非管理员。
		if(StringUtils.isBlank(userId)){
			return false;
		}
		
		User user = get(userId);
		for(Role role:user.getRoleList()){
			if("bankOperator".equals(role.getEnname())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 获取当前用户
	 * @return 取不到返回 new User()
	 */
	public static User getUser(){
		Principal principal = getPrincipal();
		if (principal!=null){
			User user = get(principal.getId());
			if (user != null){
				return user;
			}
			return new User();
		}
		// 如果没有登录，则返回实例化空的User对象。
		return new User();
	}
	
	/**
	 * 更新登录信息(启动一个线程)
	 * */
	public static void updateUserLoginInfo(User user) {
		new updateUserLoginInfoThread(user).start();
	}
	/** 启动线程来更新登录信息。
	 */
	public static class updateUserLoginInfoThread extends Thread{
		
		private User user;
		
		public updateUserLoginInfoThread(User user){
			super(updateUserLoginInfoThread.class.getSimpleName());
			this.user = user;
		}
		
		@Override
		public void run() {
			// 保存上次登录信息
			user.setOldLoginIp(user.getLoginIp());
			user.setOldLoginDate(user.getLoginDate());
			// 更新本次登录信息
			user.setLoginIp(UserUtils.getSession().getHost());
			user.setLoginDate(new Date());
			userDao.updateLoginInfo(user);
		}
	}
	
	/**
	 * 检查WebService的访问权限.
	 * request：webservice中的HttpServletRequest
	 * 返回： 0 --正常
	 *    非0 --异常的JSON信息，可以直接返回前端。 
	 * */
	public static String checkWebSvrAccessAuth(HttpServletRequest request){
		String loginMethod = request.getParameter("loginMethod"); // 
		String accessToken = request.getParameter("accessToken"); // 获取accessToken.
		String userCode    = request.getParameter("userCode");    // 用户Code. 登录名或手机号。
		
		JQResultModel resultModel = new JQResultModel();
		
		if(StringUtils.isBlank(loginMethod)){
			// accessToken为空。
			resultModel.setResult(-1);
			resultModel.setMsg("提交失败,loginMethod不能为空");
			return JSON.toJSONString(resultModel);
		}
		if(StringUtils.isBlank(accessToken)){
			// accessToken为空。
			resultModel.setResult(-1);
			resultModel.setMsg("提交失败,accessToken不能为空");
			return JSON.toJSONString(resultModel);
		}
		if(StringUtils.isBlank(userCode)){
			// accessToken为空。
			resultModel.setResult(-1);
			resultModel.setMsg("提交失败,userCode不能为空");
			return JSON.toJSONString(resultModel);
		}
		if("wx".equals(loginMethod)){
			// 微信等移动网页
			User user = getByLoginName(userCode);
			if(user == null){
				// 用户Code找不到。
				resultModel.setResult(-1);
				resultModel.setMsg("提交失败,用户账号找不到");
				return JSON.toJSONString(resultModel);
			}
			if(!accessToken.equals(user.getWxAccessToken())){
				// accessToken不对。
				resultModel.setResult(-1);
				resultModel.setMsg("提交失败,accessToken不正确");
				return JSON.toJSONString(resultModel);
			}
			// 检查通过
			return "0";
		}
		// 默认失败。
		resultModel.setResult(-1);
		resultModel.setMsg("提交失败");
		return JSON.toJSONString(resultModel);
	}
	
	/**
	 * 获取当前用户角色列表
	 * @return
	 */
	public static List<Role> getRoleList(){
		@SuppressWarnings("unchecked")
		List<Role> roleList = (List<Role>)getCache(CACHE_ROLE_LIST);
		if (roleList == null){
			User user = getUser();
			if ( UserUtils.isAdmin(user.getId())){
				roleList = roleDao.findAllList(new Role());
			}else{
				Role role = new Role();
				role.getSqlMap().put("dsf", BaseService.dataScopeFilter(user.getCurrentUser(), "o", "u"));
				roleList = roleDao.findList(role);
			}
			putCache(CACHE_ROLE_LIST, roleList);
		}
		return roleList;
	}
	
	/**
	 * 获取当前用户授权菜单
	 * @return
	 */
	public static List<Menu> getMenuList(){
		@SuppressWarnings("unchecked")
		List<Menu> menuList = (List<Menu>)getCache(CACHE_MENU_LIST);
		if (menuList == null){
			User user = getUser();
			if (UserUtils.isAdmin(user.getId())){
				menuList = menuDao.findAllList(new Menu());
			}else{
				Menu m = new Menu();
				m.setUserId(user.getId());
				menuList = menuDao.findByUserId(m);
			}
						
			putCache(CACHE_MENU_LIST, menuList);
		}
		return menuList;
	}
	
	/**
	 * 获取所有菜单的List,不管PC端还是手机端。
	 * @return
	 */
	public static List<Menu> getAllMenuList(){
		
		List<Menu> menuList = (List<Menu>)CacheUtils.get(CACHE_MENU_LIST_ALL);
		if (menuList == null){
			menuList = menuDao.findAllList(new Menu());
			//获取完菜单，放入缓存。
			CacheUtils.put(CACHE_MENU_LIST_ALL, menuList);
		}
		return menuList;
	}
	
	/**
	 * 获取所有手机菜单。
	 * @return
	 */
	public static List<Map> getAllMobileMenu(){
		Map rtnMap = new HashMap();
		
		// 获取所有的手机菜单
		List<Menu> allMenuList = UserUtils.getAllMenuList();
		List<Map> allMenuListRtn = new ArrayList<Map>();
		for(int i=0;i< allMenuList.size();i++){
			// 把PC端的菜单过滤掉,保留手机菜单
			if("mb".equals(allMenuList.get(i).getMedia())){
			   Map menu = new HashMap();
			   menu.put("href",allMenuList.get(i).getHref());
			   allMenuListRtn.add(menu);
			}
		}
		return allMenuListRtn;
	}
	
	/**
	 * 从菜单表中获取PC端页面对应的JS版本号信息。如 UserIndex的版本号为20160713
	 * @param menuFile 菜单文件名  如  area.js
	 * @return 版本号，如20160723
	 */
	public static String getVersionFmMenu(String menuFile){
		// 获取所有菜单的信息。
		List<Menu> menuList = getAllMenuList();
		
		for(Menu menu:menuList){
			//匹配到菜单，则获取版本号
			// 处理JsFileName为null的情况。
			String jsFileName = (menu.getJsFileName()==null)? "" : menu.getJsFileName();
			if(jsFileName.equalsIgnoreCase(menuFile)){
				return menu.getVersion();
			}
		}
		// 默认版本号.当前日期
		return String.format("yyyymmdd", new Date());
	}
	/**
	 * 根据手机页面名(msgDetail.html),获取版本号。
	 * @ html页面名，如 msgDetail.html 
	 * @return 版本号 如 20160712
	 * */
	public static String getWxPageVersion(String wxPageName){
		String version = "20160723"; //缺省版本号
		List<Menu> menuList = UserUtils.getAllMenuList();
		for(Menu menu:menuList){
			// 在菜单表中查询此页面，找到后获取版本信息。
			if("mb".equals(menu.getMedia()) && menu.getHref().indexOf(wxPageName) > -1 ){
				int pos = menu.getHref().lastIndexOf("="); // msgDetail.html?v=20160712
				if(pos > -1){
					version = menu.getHref().substring( pos + 1);
				}
				break;
			}
		}
		return version;
	}
	
	/**
	 * 获取当前用户授权菜单(当从login时获取菜单信息 PC)
	 * 注意：此函数为众建成本专用。
	 * @return
	 */
	public static List<Menu> getMenuListForPC(){
		@SuppressWarnings("unchecked")
		
		//从缓存中获取菜单列表。
		List<Menu> menuList = (List<Menu>)getCache(CACHE_MENU_LIST_PC);
		if (menuList == null){
			User user = getUser();
			if (UserUtils.isAdmin(user.getId())){
				menuList = menuDao.findAllList(new Menu());
			}else{
				Menu m = new Menu();
				m.setUserId(user.getId());
				menuList = menuDao.findByUserId(m);
			}
			
			menuList = sortMenuList(menuList);
			
			putCache(CACHE_MENU_LIST_PC, menuList);
		}
		return menuList;
	}
	
	/**
	 * 对菜单数据进行排序(例如,1级菜单A,1级菜单B,B的二级菜单C,B的二级菜单D，1级菜单E的顺序 )
	 * 注意：此函数为众建成本专用。
	 * @return
	 */
	private static List<Menu> sortMenuList(List<Menu> menuList){
		
		List<Menu> newMenuList = new ArrayList<Menu>();
		// 注意：目前最多支持4级菜单(含权限字符串)。
		for(int i=0;i<menuList.size();i++){
			if("1".equals(menuList.get(i).getParent().getId())){
				// 增加一级菜单到list
				Menu menu = new Menu();
				menu = menuList.get(i);
				newMenuList.add(menu);
				// 获取1级菜单的id.
				String id = menuList.get(i).getId();
				for(int j=0;j<menuList.size();j++){
					// 二级菜单或一级菜单下的“查询”“编辑”等按钮
					if(id.equals(menuList.get(j).getParent().getId())){
						
						Menu menuL2 = new Menu();
						menuL2 = menuList.get(j);
						newMenuList.add(menuL2);
						
						String idL2 = menuList.get(j).getId();
						// 三级菜单或二级菜单下的“查询”“编辑”等按钮
						for(int k=0;k<menuList.size();k++){
							if(idL2.equals(menuList.get(k).getParent().getId())){
							   Menu menu3L = new Menu();
							   menu3L = menuList.get(k);
							   newMenuList.add(menu3L);
							   // 增加4级菜单到list.
							   String idL3 = menuList.get(k).getId();
							   for(int l=0;l<menuList.size();l++){
								   if(idL3.equals(menuList.get(l).getParent().getId())){
									   Menu menu4L = new Menu();
									   menu4L = menuList.get(l);
									   newMenuList.add(menu4L);
								   }
							   }
							   
							}
						}
					}
				}
			}
		}
		
		return newMenuList;
	}
	/**
	 * 获取当前用户授权的区域
	 * @return
	 */
	public static List<Area> getAreaList(){
		@SuppressWarnings("unchecked")
		List<Area> areaList = (List<Area>)getCache(CACHE_AREA_LIST);
		if (areaList == null){
			areaList = areaDao.findAllList(new Area());
			putCache(CACHE_AREA_LIST, areaList);
		}
		return areaList;
	}
	
	/**
	 * 获取当前用户有权限访问的部门
	 * @return
	 */
	public static List<Office> getOfficeList(){
		@SuppressWarnings("unchecked")
		List<Office> officeList = (List<Office>)getCache(CACHE_OFFICE_LIST);
		if (officeList == null){
			User user = getUser();
			if (UserUtils.isAdmin(user.getId())){
				officeList = officeDao.findAllList(new Office());
			}else{
				Office office = new Office();
				office.getSqlMap().put("dsf", BaseService.dataScopeFilter(user, "a", ""));
				officeList = officeDao.findList(office);
			}
			putCache(CACHE_OFFICE_LIST, officeList);
		}
		return officeList;
	}

	/**
	 * 获取当前用户有权限访问的部门
	 * @return
	 */
	public static List<Office> getOfficeAllList(){
		@SuppressWarnings("unchecked")
		List<Office> officeList = (List<Office>)getCache(CACHE_OFFICE_ALL_LIST);
		if (officeList == null){
			officeList = officeDao.findAllList(new Office());
		}
		return officeList;
	}
	
	/**
	 * 获取授权主要对象
	 */
	public static Subject getSubject(){
		return SecurityUtils.getSubject();
	}
	
	/**
	 * 获取当前登录者对象
	 */
	public static Principal getPrincipal(){
		try{
			Subject subject = SecurityUtils.getSubject();
			Principal principal = (Principal)subject.getPrincipal();
			if (principal != null){
				return principal;
			}
//			subject.logout();
		}catch (UnavailableSecurityManagerException e) {
			
		}catch (InvalidSessionException e){
			
		}
		return null;
	}
	
	public static Session getSession(){
		try{
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession(false);
			if (session == null){
				session = subject.getSession();
			}
			if (session != null){
				return session;
			}
//			subject.logout();
		}catch (InvalidSessionException e){
			
		}
		return null;
	}
	
	// ============== User Cache ==============
	
	public static Object getCache(String key) {
		return getCache(key, null);
	}
	
	public static Object getCache(String key, Object defaultValue) {
//		Object obj = getCacheMap().get(key);
		Object obj = getSession().getAttribute(key);
		return obj==null?defaultValue:obj;
	}

	public static void putCache(String key, Object value) {
//		getCacheMap().put(key, value);
		getSession().setAttribute(key, value);
	}

	public static void removeCache(String key) {
//		getCacheMap().remove(key);
		getSession().removeAttribute(key);
	}
	
//	public static Map<String, Object> getCacheMap(){
//		Principal principal = getPrincipal();
//		if(principal!=null){
//			return principal.getCacheMap();
//		}
//		return new HashMap<String, Object>();
//	}
	
	/**
	 * 获取管理员
	 * @return
	 */
	public static List<UserModel> getAdminUser(){
		return userDao.getAdmin("super");
	}
	
}
