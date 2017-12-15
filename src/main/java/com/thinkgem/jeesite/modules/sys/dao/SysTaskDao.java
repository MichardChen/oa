/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.SysTask;
import com.thinkgem.jeesite.modules.sys.model.SysTaskModel;

/**
 * 系统任务DAO接口
 * @author rxf
 * @version 2016-04-21
 */
@MyBatisDao
public interface SysTaskDao extends CrudDao<SysTask> {
	
	//根据自增长ID查询数据
	public SysTaskModel findByAutoIncId(@Param("autoIncId")int autoIncId);
	
	public int insert(SysTask sysTask);
	/**更新任务状态
	 * */
	public int updateStatus(SysTask sysTask);
	/**根据任务类型获取数据
	 * */
	public SysTask getByTaskType(SysTask sysTask);
}
