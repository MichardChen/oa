/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.thinkgem.jeesite.common.config.Global;
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
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealm.Principal;

/**
 * 用户工具类
 * @author ThinkGem
 * @version 2013-12-05
 */
public class RoleUtils {

	private static RoleDao roleDao = SpringContextHolder.getBean(RoleDao.class);
	
	public static final String ROLE_CACHE = "roleCache";
	public static final String ROLE_CACHE_ID_ = "id_";
	public static final String ROLE_CACHE_ENAME_ = "ename_";
	
	/**
	 * 根据ID获取用户
	 * @param id
	 * @return 取不到返回null
	 */
	public static Role get(String id){
		Role role = (Role)CacheUtils.get(ROLE_CACHE, ROLE_CACHE_ID_ + id);
		if (role ==  null){
			role = roleDao.get(id);
			if (role == null){
				return null;
			}
			CacheUtils.put(ROLE_CACHE, ROLE_CACHE_ID_ + role.getId(), role);
		}
		return role;
	}
	/**
	 * 根据英文名来获取Role
	 * @param ename: 角色的英文名
	 * @return 取不到返回null
	 */
	public static Role getByEname(String ename){
		Role role = (Role)CacheUtils.get(ROLE_CACHE, ROLE_CACHE_ENAME_ + ename);
		if (role ==  null){
			
			Role param = new Role();
			param.setEnname(ename);
			param.setDelFlag("0");
			
			role = roleDao.getByEnname(param);
			if (role == null){
				return null;
			}
			CacheUtils.put(ROLE_CACHE, ROLE_CACHE_ENAME_ + role.getEnname(), role);
		}
		return role;
	}
}
