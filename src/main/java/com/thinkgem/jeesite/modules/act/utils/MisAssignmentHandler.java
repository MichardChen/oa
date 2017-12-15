package com.thinkgem.jeesite.modules.act.utils;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.apache.commons.lang3.StringUtils;

import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.sys.entity.SysWfAssign;
import com.thinkgem.jeesite.modules.sys.service.SysWfAssignService;

/**
 * 工作流中的监听事件。适用于企业内部信息化。用于动态设置处理者 
 * @author HOLD
 * @version 2016-4-11
 */
public class MisAssignmentHandler  implements TaskListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2844370761532437832L;
	
	private SysWfAssignService sysWfAssignService;
	
	@Override
	public void notify(DelegateTask delegateTask) {
		// TODO Auto-generated method stub
		// 只有创建create事件才处理。
		String event = delegateTask.getEventName();
		if(!"create".equals(event)){
			return;
		}
		//获取节点id。
		String taskDefKey = delegateTask.getTaskDefinitionKey();
		//获取 流程定义id
		String processDefId = delegateTask.getProcessDefinitionId();
		//根据 流程定义id和节点id获取候选人员和候选角色。
		System.out.println(processDefId);
		System.out.println(taskDefKey);
		SysWfAssign sysWfAssign= getActTaskService().getCandidateUserAndGroup(processDefId, taskDefKey);
		//设置到工作流上。
		List<String> userList = sysWfAssign.getUserList();
		if(userList != null){
			userList = trimList(userList);

			int count = userList.size();
			if( count == 1){
				// 如果只有一名用户,则设到Assignee上，而不是CandidateUser。
				delegateTask.setAssignee("u_" + userList.get(0));
			}
			if( count > 1 ){
				for(int i=0; i< userList.size();i++){
					// 候选人员( u_3 , u_4 ) 为了避免id相同（如用户id和角色id均为3),增加前缀u来区别。
					delegateTask.addCandidateUser( "u_"+ userList.get(i));
				}
			}
			// 其他情况下，不设处理人
		}
        		
		List<String> roleList = sysWfAssign.getRoleList();
		if(roleList != null){
			roleList = trimList(roleList);
			
			int count = roleList.size();
			if( count == 1){
				// 如果只有一名用户,则设到Assignee上，而不是CandidateUser。
				delegateTask.setAssignee("r_" + roleList.get(0));
			}
			if( count > 1 ){
				for(int i=0; i< roleList.size();i++){
					// 候选角色( r_9, r_3等) 为了避免id相同（如用户id和角色id均为3),增加前缀r来区别。
					delegateTask.addCandidateUser("r_" + roleList.get(i));
				}
			}
			// 其他情况下，不设处理人
		}
		
		List<String> officeList = sysWfAssign.getOfficeList();
		if(officeList != null){
			officeList = trimList(officeList);
			//delegateTask.setAssignee(assignee); trimList
			int count = officeList.size();
			if( count == 1){
				// 如果只有一名用户,则设到Assignee上，而不是CandidateUser。
				delegateTask.setAssignee("o_" + officeList.get(0));
			}
			if( count > 1 ){
				for(int i=0; i< officeList.size();i++){
					// 候选部门(o_33,o_99等) 为了避免id相同（如部门id和角色id均为3),增加前缀o来区别。
					delegateTask.addCandidateUser("o_" + officeList.get(i));
				}
			}
			// 其他情况下，不设处理人
		}
		
	}
	
	/**
	 * 获取Service
	 */
	public SysWfAssignService getActTaskService() {
		if (sysWfAssignService == null){
			sysWfAssignService = SpringContextHolder.getBean(SysWfAssignService.class);
		}
		return sysWfAssignService;
	}
	
	/**
	 * 去除参数List的空白.如   ("","3","4",null) 转换成  ("3","4") 
	 * */
	public List<String> trimList(List<String> paramList){
		List<String> rst = new ArrayList<String>();
		for(String s:paramList){
			if(StringUtils.isNotBlank(s)){
				rst.add(s);
			}
		}
		return rst;
	}
}
