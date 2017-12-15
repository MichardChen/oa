package com.thinkgem.jeesite.modules.sys.utils;

import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.sys.dao.SysParameterDao;
import com.thinkgem.jeesite.modules.sys.model.SysParameterModel;

/**
 * 系统参数工具类
 */
public class SysParameterUtils {
	private static SysParameterDao sysParameterDao = SpringContextHolder.getBean(SysParameterDao.class);
	
	public static final String SysParameter_CACHE = "sysParameterCache";
	public static final String SysParameter_CACHE_KEY_ = "key_";
	
	public static SysParameterModel findKeyword(String keyword){
		SysParameterModel sysParameterModel = (SysParameterModel)CacheUtils.get(SysParameter_CACHE, 
				SysParameter_CACHE_KEY_ + keyword);
		if (sysParameterModel ==  null){
			sysParameterModel = sysParameterDao.findByKeyword(keyword);
			if (sysParameterModel == null){
				return null;
			}
			CacheUtils.put(SysParameter_CACHE, SysParameter_CACHE_KEY_ + sysParameterModel.getKeyword(), sysParameterModel);
		}
		return sysParameterModel;
	
	}
}
