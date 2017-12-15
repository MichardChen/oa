/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;



import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.SysLogin;
/**
 * 登陆信息记录接口
 * @author WYF
 * @version 2016-02-18
 */
@MyBatisDao
public interface SysLoginDao extends CrudDao<SysLogin> {

	public String getTokenKey(@Param("userId")String userId,@Param("code")String code);
	
	public String CheckTokenKey(String token);
	
	public String getUserIdByToken(String token);
}
