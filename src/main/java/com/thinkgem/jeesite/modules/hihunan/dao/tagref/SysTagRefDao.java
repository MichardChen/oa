/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hihunan.dao.tagref;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.hihunan.entity.tagref.SysTagRef;

/**
 * 标签关系DAO接口
 * @author yuyabiao
 * @version 2016-11-29
 */
@MyBatisDao
public interface SysTagRefDao extends CrudDao<SysTagRef> {
	
}