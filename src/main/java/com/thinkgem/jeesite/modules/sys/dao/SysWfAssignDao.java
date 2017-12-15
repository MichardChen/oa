/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.SysWfAssign;
import com.thinkgem.jeesite.modules.sys.model.SysWfAssignModel;

/**
 * 流程人员分配DAO接口
 * @author mlg
 * @version 2016-4-16
 */
@MyBatisDao
public interface SysWfAssignDao extends CrudDao<SysWfAssign> {
	
	/**根据流程定义id和流程节点id获取候选人和候选角色
	 * */
	public SysWfAssign getCandidateUserAndGroup(@Param("processDefId") String processDefId, @Param("taskDefKey") String taskDefKey);
    /**获取流程人员分配 配置数据
     * */
	public List<SysWfAssignModel> findModels(SysWfAssign sysWfAssign);
	/**获取流程基础数据
     * */
	public List<SysWfAssignModel> findProcModels(SysWfAssign sysWfAssign);
	/**根据某流程，某一环节(如planXXX)，获取上一环节的信息(如start)。
	 * */
	public SysWfAssignModel findPrevTaskInfo(SysWfAssign sysWfAssign);
	
	/**根据某流程，某一环节(如planXXX)，获取下一环节的信息(如start)。
	 * */
	public SysWfAssignModel findNextTaskInfo(SysWfAssign sysWfAssign);
}
