/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.utils;

import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * 组织机构工具类
 * @author ThinkGem
 * @version 2013-12-05
 */
public class OfficeUtils {

	private static OfficeDao officeDao = SpringContextHolder.getBean(OfficeDao.class);
	
	public static final String OFFICE_CACHE = "officeCache";
	public static final String OFFICE_CACHE_ID_ = "id_";

	public static final String CACHE_OFFICE_LIST = "officeList";

	
	/**
	 * 根据ID获取用户
	 * @param id
	 * @return 取不到返回null
	 */
	public static Office get(String id){
		Office office = (Office)CacheUtils.get(OFFICE_CACHE, OFFICE_CACHE_ID_ + id);
		if (office ==  null){
			office = officeDao.get(id);
			if (office == null){
				return null;
			}
			CacheUtils.put(OFFICE_CACHE, OFFICE_CACHE_ID_ + office.getId(), office);
		}
		return office;
	}
	
	
	
}
