/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hihunan.dao.localactivity;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.hihunan.entity.localactivity.HiLocalActivity;
import com.thinkgem.jeesite.modules.hihunan.model.localactivity.MemberModel;

/**
 * 本地生活DAO接口
 * @author yuyabiao
 * @version 2016-11-29
 */
@MyBatisDao
public interface HiLocalActivityDao extends CrudDao<HiLocalActivity> {
	
	public MemberModel getMemberInfo(@Param("token") String token);
	
}