package com.thinkgem.jeesite.modules.sys.service;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.delegate.DelegateTask;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.dao.SysWfAssignDao;
import com.thinkgem.jeesite.modules.sys.entity.SysWfAssign;
import com.thinkgem.jeesite.modules.sys.model.SysWfAssignModel;

@Service
public class SysWfAssignService extends
		CrudService<SysWfAssignDao, SysWfAssign> {

	@Autowired
	private SysWfAssignDao sysWfAssignDao;
	
	/**
	 *根据流程定义id和流程节点id获取候选人和候选角色 
	 *@param processDefinitionId 流程定义id.
	 *@param TaskDefinitionKey  流程节点的id. <userTask id="modify" name="员工薪酬档级修改" 中的 modify。
	 * */
	public SysWfAssign getCandidateUserAndGroup(String processDefId,String taskDefKey){
		return sysWfAssignDao.getCandidateUserAndGroup(processDefId, taskDefKey);
	}
	/**获取流程人员分配 配置数据
     * */
	public List<SysWfAssignModel> findModels(SysWfAssign sysWfAssign){
		return sysWfAssignDao.findModels(sysWfAssign);
	}
	
	/**获取流程基础数据
     * */
	public List<SysWfAssignModel> findProcModels(SysWfAssign sysWfAssign){
		return sysWfAssignDao.findProcModels(sysWfAssign);
	}
	
	/**获取某环节(如 planXXX)的上一环节的一条数据(start)
	 * 参数：sysWfAssign.setProcDefId(procDefId)
	 *     sysWfAssign.setTaskDefKey(taskDefKey);
	 * */
	public SysWfAssignModel findPrevTaskInfo(SysWfAssign sysWfAssign){
		return sysWfAssignDao.findPrevTaskInfo(sysWfAssign);
	}
	
	/**获取某环节(如 planXXX)的下一环节的一条数据(start)
	 * 参数：sysWfAssign.setProcDefId(procDefId)
	 *     sysWfAssign.setTaskDefKey(taskDefKey);
	 * */
	public SysWfAssignModel findNextTaskInfo(SysWfAssign sysWfAssign){
		return sysWfAssignDao.findNextTaskInfo(sysWfAssign);
	}
	
	public void setAssigneeUser(DelegateTask delegateTask,List<String> userIdList){
		if(userIdList != null){
			userIdList = trimList(userIdList);
			int count = userIdList.size();
			if( count == 1){
				// 如果只有一名用户,则设到Assignee上，而不是CandidateUser。
				delegateTask.setAssignee("u_" + userIdList.get(0));
			}
			if( count > 1 ){
				for(int i=0; i< userIdList.size();i++){
					// 候选人员( u_3 , u_4 ) 为了避免id相同（如用户id和角色id均为3),增加前缀u来区别。
					delegateTask.addCandidateUser( "u_"+ userIdList.get(i));
				}
			}
			// 其他情况下，不设处理人
		}
	}
	
	/**
	 * 去除参数List的空白.如   ("","3","4",null) 转换成  ("3","4") 
	 * */
	private List<String> trimList(List<String> paramList){
		List<String> rst = new ArrayList<String>();
		for(String s:paramList){
			if(StringUtils.isNotBlank(s)){
				rst.add(s);
			}
		}
		return rst;
	}

}
