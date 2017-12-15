/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.act.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.bpmn.diagram.ProcessDiagramGenerator;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.persistence.JQResultModel;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.act.dao.ActDao;
import com.thinkgem.jeesite.modules.act.entity.Act;
import com.thinkgem.jeesite.modules.act.utils.ActUtils;
import com.thinkgem.jeesite.modules.act.utils.ProcessDefCache;
import com.thinkgem.jeesite.modules.sys.dao.CompanyRoleDao;
import com.thinkgem.jeesite.modules.sys.dao.SysWfAssignDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.SysWfAssign;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.model.SysParameterModel;
import com.thinkgem.jeesite.modules.sys.model.SysWfAssignModel;
import com.thinkgem.jeesite.modules.sys.service.SysWfAssignService;
import com.thinkgem.jeesite.modules.sys.utils.OfficeUtils;
import com.thinkgem.jeesite.modules.sys.utils.RoleUtils;
import com.thinkgem.jeesite.modules.sys.utils.SysParameterUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 流程定义相关Service
 * @author ThinkGem
 * @version 2013-11-03
 */
@Service
@Transactional(readOnly = true)
public class ActTaskService extends BaseService {

	@Autowired
	private ActDao actDao;
	
	@Autowired
	private CompanyRoleDao companyRoleDao;
	
	@Autowired
	private SysWfAssignDao sysWfAssignDao;
	
	@Autowired
	private SysWfAssignService sysWfAssignService;
	
	@Autowired
	private ProcessEngineFactoryBean processEngine;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private FormService formService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private IdentityService identityService;
	
	/**
	 * 获取待办列表
	 * @param procDefKey 流程定义标识
	 * @return
	 */
	public Page<Act> todoList(Page<Act> page,Act act,User user){
		List<Act> result = new ArrayList<Act>();
		// 1)获取登录用户的id. 
		String userId   = user.getId();
		// 2)获取登录用户的roleId(取一个角色)
		List<Role> roleList = UserUtils.get(userId).getRoleList();
		String roleId   = "";
		if(roleList != null && roleList.size() > 0){
			roleId = roleList.get(0).getId();	
		}
		// 3)获取登录用户的officeId。
		String officeId = "";
		if(user.getOffice() != null){
		    officeId =  user.getOffice().getId();
		}
		
		SysParameterModel sysParameterModel = SysParameterUtils.findKeyword("workFlowAssignee");
		
		List<String> assigneeCandidateList = new  ArrayList<String>();
		// if 系统参数(按用户设置)=true，则把"u_" + userId 加入列表。
		assigneeCandidateList.add("u_" + userId); //用户
		// if 系统参数(按角色设置)=true，则把"r_" + userId 加入列表。
		if("1".equals(sysParameterModel.getValue1()) && StringUtils.isNotBlank(roleId)){
		   assigneeCandidateList.add("r_" + roleId); //角色
		}
		//
		if("1".equals(sysParameterModel.getValue2()) && StringUtils.isNotBlank(officeId)){
		   assigneeCandidateList.add("o_" + officeId); //部门
		}
		
		long count = 0;
		
		for(String assigneeCandidate:assigneeCandidateList){
			// ===============(1) 已经签收的任务(assignee) ===============
			TaskQuery todoTaskQuery = taskService.createTaskQuery().taskAssignee(assigneeCandidate).active()
					.includeProcessVariables().orderByTaskCreateTime().desc();
			
			// 设置查询条件
			if (StringUtils.isNotBlank(act.getProcDefKey())){
				todoTaskQuery.processDefinitionKey(act.getProcDefKey());
			}
			if (act.getBeginDate() != null){
				todoTaskQuery.taskCreatedAfter(act.getBeginDate());
			}
			if (act.getEndDate() != null){
				todoTaskQuery.taskCreatedBefore(act.getEndDate());
			}
			//农商行新增按片区查询方法
			if(StringUtils.isNotBlank(act.getNsAreaId())){
				todoTaskQuery.processVariableValueEquals("ns_" + act.getNsAreaId());
			}
			count = count + todoTaskQuery.count();
			// 查询列表
			List<Task> todoList = todoTaskQuery.listPage((page.getPageNo()-1)*page.getPageSize(), page.getMaxResults());
			
			for (Task task : todoList) {
				Act e = new Act();
				e.setTask(task);
				e.setVars(task.getProcessVariables());
	//			e.setTaskVars(task.getTaskLocalVariables());
	//			System.out.println(task.getId()+"  =  "+task.getProcessVariables() + "  ========== " + task.getTaskLocalVariables());
				e.setProcDef(ProcessDefCache.get(task.getProcessDefinitionId()));
	//			e.setProcIns(runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult());
	//			e.setProcExecUrl(ActUtils.getProcExeUrl(task.getProcessDefinitionId()));
				e.setStatus("todo");
				
				HistoricProcessInstance hi = historyService.createHistoricProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
				User startUser = UserUtils.get(hi.getStartUserId().substring(2));// 从第三位开始截取。u_2323 取成2323.
				if (startUser != null){
					// 获取发起人姓名
					e.setInitiatorName(startUser.getName());
					e.setInitiatorUserId(hi.getStartUserId()); //发起人的用户id
				}
				result.add(e);
			}
			
			// ===============(2) 等待签收的任务(CandidateUser)  ===============
			TaskQuery toClaimQuery = taskService.createTaskQuery().taskCandidateUser(assigneeCandidate)
					.includeProcessVariables().active().orderByTaskCreateTime().desc();
			
			// 设置查询条件
			if (StringUtils.isNotBlank(act.getProcDefKey())){
				toClaimQuery.processDefinitionKey(act.getProcDefKey());
			}
			if (act.getBeginDate() != null){
				toClaimQuery.taskCreatedAfter(act.getBeginDate());
			}
			if (act.getEndDate() != null){
				toClaimQuery.taskCreatedBefore(act.getEndDate());
			}
			//农商行新增按片区查询方法
			if(StringUtils.isNotBlank(act.getNsAreaId())){
				toClaimQuery.processVariableValueEquals("ns_" + act.getNsAreaId());
			}
			count = count + toClaimQuery.count();
			// 查询列表
			List<Task> toClaimList = toClaimQuery.listPage((page.getPageNo()-1)*page.getPageSize(), page.getMaxResults());
			
			for (Task task : toClaimList) {
				Act e = new Act();
				e.setTask(task);
				e.setVars(task.getProcessVariables());
	//			e.setTaskVars(task.getTaskLocalVariables());
	//			System.out.println(task.getId()+"  =  "+task.getProcessVariables() + "  ========== " + task.getTaskLocalVariables());
				e.setProcDef(ProcessDefCache.get(task.getProcessDefinitionId()));
	//			e.setProcIns(runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult());
	//			e.setProcExecUrl(ActUtils.getProcExeUrl(task.getProcessDefinitionId()));
				e.setStatus("claim");
				
				HistoricProcessInstance hi = historyService.createHistoricProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
				User startUser = UserUtils.get(hi.getStartUserId().substring(2));// 从第三位开始截取。u_2323 取成2323.
				if (startUser != null){
					// 获取发起人姓名
					e.setInitiatorName(startUser.getName());
					e.setInitiatorUserId(hi.getStartUserId()); //发起人的用户id
				}
				result.add(e);
			}
		}
		for(Act actModel :result){
			//获取上一环节的节点id(如initConfirm)
			SysWfAssign sysWfAssign=new SysWfAssign();
			sysWfAssign.setProcDefId(actModel.getProcDef().getId());
			sysWfAssign.setTaskDefKey(actModel.getTask().getTaskDefinitionKey());
			SysWfAssignModel sysWfAssignModel= sysWfAssignService.findNextTaskInfo(sysWfAssign);
			if(sysWfAssignModel != null){
				List<HistoricActivityInstance> hais = historyService.createHistoricActivityInstanceQuery().processInstanceId(actModel.getTask().getProcessInstanceId())  // 流程实例id
					    .finished()     //即下个环节已经完成
					    .activityId(sysWfAssignModel.getTaskDefKey())   // 上一环节的TaskDefKey
					    .orderByHistoricActivityInstanceEndTime().desc() //按完成时间的倒序（即最新完成的）。
					    .list();
				if(hais.size() > 0 ){
					String taskId = hais.get(0).getTaskId();
					if(taskService.getTaskComments(taskId).size() > 0){
						String comment = taskService.getTaskComments(taskId).get(0).getFullMessage();
						actModel.setComment("撤回原因：" + comment);
					}
				}
			}
		}
		page.setCount(count);
		page.setList(result);
		return page;
	}
	
	/**
	 * 获取历史任务
	 * @param page
	 * @param procDefKey 流程定义标识
	 * @return
	 */
	public Page<Act> historicList(Page<Act> page, Act act,User user){
				
		// 1)获取登录用户的id. 
		String userId   = user.getId();
		// 2)获取登录用户的roleId(取一个角色)
		List<Role> roleList = UserUtils.get(userId).getRoleList();
		String roleId   = "";
		if(roleList != null && roleList.size() > 0){
			roleId = roleList.get(0).getId();	
		}
		// 3)获取登录用户的officeId。
		String officeId = "";
		if(user.getOffice() != null){
		    officeId =  user.getOffice().getId();
		}
		page.setCount(50);//查询50条
		SysParameterModel sysParameterModel = SysParameterUtils.findKeyword("workFlowAssignee");

		List<String> assigneeCandidateList = new  ArrayList<String>();
		assigneeCandidateList.add("u_" + userId);       // 用户
		if("1".equals(sysParameterModel.getValue1()) && StringUtils.isNotBlank(roleId) ){
			assigneeCandidateList.add("r_" + roleId);   // 角色
		}
		if("1".equals(sysParameterModel.getValue2()) && StringUtils.isNotBlank(officeId)){
			assigneeCandidateList.add("o_" + officeId); // 部门(保留)
		}
		
		HistoricTaskInstanceQuery histTaskQuery;
		List<HistoricTaskInstance> histList = new ArrayList<HistoricTaskInstance>();
		long count = 0; // 记录条数
		
		for(String assigneeCandidate:assigneeCandidateList){
			// (1)处理人
			histTaskQuery = historyService.createHistoricTaskInstanceQuery().taskAssignee(assigneeCandidate).processFinished()
				.includeProcessVariables().orderByHistoricTaskInstanceEndTime().desc();
		
			// 设置查询条件
			if (StringUtils.isNotBlank(act.getProcDefKey())){
				histTaskQuery.processDefinitionKey(act.getProcDefKey());
			}
			if (act.getBeginDate() != null){
				histTaskQuery.taskCompletedAfter(act.getBeginDate());
			}
			if (act.getEndDate() != null){
				histTaskQuery.taskCompletedBefore(act.getEndDate());
			}
			// 查询总数
			count = count +  histTaskQuery.count();
			// 查询列表
			histList = histTaskQuery.listPage((page.getPageNo()-1)*page.getPageSize(), page.getMaxResults());
			
			for (HistoricTaskInstance histTask : histList) {
				Act e = new Act();
				e.setHistTask(histTask);
				e.setVars(histTask.getProcessVariables());
	//			e.setTaskVars(histTask.getTaskLocalVariables());
	//			System.out.println(histTask.getId()+"  =  "+histTask.getProcessVariables() + "  ========== " + histTask.getTaskLocalVariables());
				e.setProcDef(ProcessDefCache.get(histTask.getProcessDefinitionId()));
	//			e.setProcIns(runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult());
	//			e.setProcExecUrl(ActUtils.getProcExeUrl(task.getProcessDefinitionId()));
				e.setStatus("finish");
				HistoricProcessInstance hi = historyService.createHistoricProcessInstanceQuery().processInstanceId(histTask.getProcessInstanceId()).singleResult();
				User startUser = UserUtils.get(hi.getStartUserId().substring(2));// 从第三位开始截取。u_2323 取成2323.
				if (startUser != null){
					// 获取发起人姓名
					e.setInitiatorName(startUser.getName());
					e.setInitiatorUserId(hi.getStartUserId()); //发起人的用户id
				}
				page.getList().add(e);
			}
			// (2) 候选人
			histTaskQuery = historyService.createHistoricTaskInstanceQuery().taskCandidateUser(assigneeCandidate).processFinished()
			   .includeProcessVariables().orderByHistoricTaskInstanceEndTime().desc();
	
			// 设置查询条件
			if (StringUtils.isNotBlank(act.getProcDefKey())){
				histTaskQuery.processDefinitionKey(act.getProcDefKey());
			}
			if (act.getBeginDate() != null){
				histTaskQuery.taskCompletedAfter(act.getBeginDate());
			}
			if (act.getEndDate() != null){
				histTaskQuery.taskCompletedBefore(act.getEndDate());
			}
			// 查询总数
			count = count +  histTaskQuery.count();
			// 查询列表
			histList = histTaskQuery.listPage((page.getPageNo()-1)*page.getPageSize(), page.getMaxResults());
			for (HistoricTaskInstance histTask : histList) {
				Act e = new Act();
				e.setHistTask(histTask);
				e.setVars(histTask.getProcessVariables());
	//			e.setTaskVars(histTask.getTaskLocalVariables());
	//			System.out.println(histTask.getId()+"  =  "+histTask.getProcessVariables() + "  ========== " + histTask.getTaskLocalVariables());
				e.setProcDef(ProcessDefCache.get(histTask.getProcessDefinitionId()));
	//			e.setProcIns(runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult());
	//			e.setProcExecUrl(ActUtils.getProcExeUrl(task.getProcessDefinitionId()));
				e.setStatus("finish");
				HistoricProcessInstance hi = historyService.createHistoricProcessInstanceQuery().processInstanceId(histTask.getProcessInstanceId()).singleResult();
				User startUser = UserUtils.get(hi.getStartUserId().substring(2));// 从第三位开始截取。u_2323 取成2323.
				if (startUser != null){
					// 获取发起人姓名
					e.setInitiatorName(startUser.getName());
					e.setInitiatorUserId(hi.getStartUserId()); //发起人的用户id
				}
				page.getList().add(e);
			}
		}
		// 设置总记录数
		page.setCount(count);
		
		return page;
	}
	
	
	/**
	 * 获取已办任务
	 * @param page
	 * @param procDefKey 流程定义标识
	 * @return
	 */
	public Page<Act> completeList(Page<Act> page, Act act,User user){
				
		// 1)获取登录用户的id. 
		String userId   = user.getId();
		// 2)获取登录用户的roleId(取一个角色)
		List<Role> roleList = UserUtils.get(userId).getRoleList();
		String roleId   = "";
		if(roleList != null && roleList.size() > 0){
			roleId = roleList.get(0).getId();	
		}
		// 3)获取登录用户的officeId。
		String officeId = "";
		if(user.getOffice() != null){
		    officeId =  user.getOffice().getId();
		}
		
		SysParameterModel sysParameterModel = SysParameterUtils.findKeyword("workFlowAssignee");

		List<String> assigneeCandidateList = new  ArrayList<String>();
		assigneeCandidateList.add("u_" + userId);       // 用户
		if("1".equals(sysParameterModel.getValue1()) && StringUtils.isNotBlank(roleId)){
			assigneeCandidateList.add("r_" + roleId);   // 角色
		}
		if("1".equals(sysParameterModel.getValue2()) && StringUtils.isNotBlank(officeId)){
			assigneeCandidateList.add("o_" + officeId); // 部门(保留)
		}
		
		HistoricProcessInstanceQuery histTaskQuery;
		List<HistoricProcessInstance> histList = new ArrayList<HistoricProcessInstance>();
		long count = 0; // 记录条数
		
		for(String assigneeCandidate:assigneeCandidateList){
			// (1)处理人
			histTaskQuery = historyService.createHistoricProcessInstanceQuery().involvedUser(assigneeCandidate).unfinished().includeProcessVariables().orderByProcessInstanceStartTime().desc();
		
			// 设置查询条件
			if (StringUtils.isNotBlank(act.getProcDefKey())){
				histTaskQuery.processDefinitionKey(act.getProcDefKey());
			}
			if (act.getBeginDate() != null){
				histTaskQuery.finishedAfter(act.getBeginDate());
			}
			if (act.getEndDate() != null){
				histTaskQuery.finishedBefore(act.getEndDate());
			}
			// 查询总数
			count = count +  histTaskQuery.count();
			// 查询列表
			histList = histTaskQuery.listPage((page.getPageNo()-1)*page.getPageSize(), page.getMaxResults());
			
			for (HistoricProcessInstance histTask : histList) {
				Act e = new Act();
				e.setHistProc(histTask);;
				e.setVars(histTask.getProcessVariables());
				e.setProcDef(ProcessDefCache.get(histTask.getProcessDefinitionId()));
				e.setStatus("unfinish");
				User startUser = UserUtils.get(histTask.getStartUserId().substring(2));// 从第三位开始截取。u_2323 取成2323.
				if (startUser != null){
					// 获取发起人姓名
					e.setInitiatorName(startUser.getName());
					e.setInitiatorUserId(histTask.getStartUserId()); //发起人的用户id
				}
				page.getList().add(e);
			}
		}
		// 设置总记录数
		page.setCount(count);
		
		return page;
	}
	
	/**
	 * 获取流转历史列表
	 * @param procInsId 流程实例
	 * @param startAct 开始活动节点名称
	 * @param endAct 结束活动节点名称
	 */
	public List<Act> histoicFlowList(String procInsId, String startAct, String endAct){
		List<Act> actList = Lists.newArrayList();
		List<HistoricActivityInstance> histList = historyService.createHistoricActivityInstanceQuery().processInstanceId(procInsId)
				.orderByHistoricActivityInstanceStartTime().asc().list();
		HistoricActivityInstance startActivity = historyService.createHistoricActivityInstanceQuery().processInstanceId(procInsId).activityType("startEvent").singleResult();
		List<HistoricActivityInstance> list = new ArrayList<HistoricActivityInstance>();
		Iterator<HistoricActivityInstance> listIterator = histList.iterator();
		for(HistoricActivityInstance taskHist : histList){
			if("startEvent".equals(taskHist.getActivityType())){
				list.add(taskHist);
			}
		}
//		for(HistoricActivityInstance taskHist : histList){
//			if(!"startEvent".equals(taskHist.getActivityType())){
//				if(taskHist.getStartTime().getTime() == startActivity.getStartTime().getTime() && taskHist.getEndTime().getTime() == startActivity.getEndTime().getTime()){
//					list.add(taskHist);
//				}
//			}
//		}
		while (listIterator.hasNext()) {//去掉空白的数据行
			HistoricActivityInstance taskHist = listIterator.next();
			if(!"startEvent".equals(taskHist.getActivityType()) && taskHist.getEndTime() != null){
				if(taskHist.getStartTime().getTime() == startActivity.getStartTime().getTime() && taskHist.getEndTime().getTime() == startActivity.getEndTime().getTime()){
					list.add(taskHist);
					listIterator.remove();
				}
			}
		}
		for(HistoricActivityInstance taskHistb : histList){
			if(!"startEvent".equals(taskHistb.getActivityType())){
				list.add(taskHistb);
			}
		}
		
		boolean start = false;
		Map<String, Integer> actMap = Maps.newHashMap();
		
		for (int i=0; i<list.size(); i++){
			
			HistoricActivityInstance histIns = list.get(i);
			
			// 过滤开始节点前的节点
			if (StringUtils.isNotBlank(startAct) && startAct.equals(histIns.getActivityId())){
				start = true;
			}
			if (StringUtils.isNotBlank(startAct) && !start){
				continue;
			}
			
			// 只显示开始节点和结束节点，并且执行人不为空的任务
			if (StringUtils.isNotBlank(histIns.getAssignee())
					 || "startEvent".equals(histIns.getActivityType())
					 || "endEvent".equals(histIns.getActivityType())){
				
				// 给节点增加一个序号
				Integer actNum = actMap.get(histIns.getActivityId());
				if (actNum == null){
					actMap.put(histIns.getActivityId(), actMap.size());
				}
				
				Act e = new Act();
				e.setHistIns(histIns);
				// 获取流程发起人名称
				if ("startEvent".equals(histIns.getActivityType())){
					List<HistoricProcessInstance> il = historyService.createHistoricProcessInstanceQuery().processInstanceId(procInsId).orderByProcessInstanceStartTime().asc().list();
//					List<HistoricIdentityLink> il = historyService.getHistoricIdentityLinksForProcessInstance(procInsId);
					if (il.size() > 0){
						if (StringUtils.isNotBlank(il.get(0).getStartUserId())){
							// 根据流程发起人id获取发起人信息。getStartUserId为发起人的userID,不是角色信息。
							User user = UserUtils.get(il.get(0).getStartUserId().substring(2));// 从第三位开始截取。u_2323 取成2323.
							if (user != null){
								e.setAssignee(histIns.getAssignee());
								// 获取发起人姓名
								e.setAssigneeName(user.getName());
							}
						}
					}
				}
				// 获取任务执行人名称
				if (StringUtils.isNotEmpty(histIns.getAssignee())){
					String name = ""; 
					if(histIns.getAssignee().startsWith("u_")){
						// 获取用户信息  // 从第三位开始截取。u_2323 取成2323.
						User user = UserUtils.get(histIns.getAssignee().substring(2));
						name = user.getName();
					}
					else if(histIns.getAssignee().startsWith("r_")){
						// 获取角色信息 // 从第三位开始截取。r_2 取成2.
						Role role = RoleUtils.get(histIns.getAssignee().substring(2));
						name = role.getName();
					}
					else if(histIns.getAssignee().startsWith("o_")){ 
						// 获取机构信息 // 从第三位开始截取。o_223 取成223.
				        Office office = OfficeUtils.get(histIns.getAssignee().substring(2));
						name = office.getName();
					}
					if (StringUtils.isNotBlank(name)){
						e.setAssignee(histIns.getAssignee());
						// 获取任务执行人名称
						e.setAssigneeName(name);
					}
				}
				// 获取意见评论内容
				if (StringUtils.isNotBlank(histIns.getTaskId())){
					List<Comment> commentList = taskService.getTaskComments(histIns.getTaskId());
					if (commentList.size()>0){
						e.setComment(commentList.get(0).getFullMessage());
					}
				}
				actList.add(e);
			}
			
			// 过滤结束节点后的节点
			if (StringUtils.isNotBlank(endAct) && endAct.equals(histIns.getActivityId())){
				boolean bl = false;
				Integer actNum = actMap.get(histIns.getActivityId());
				// 该活动节点，后续节点是否在结束节点之前，在后续节点中是否存在
				for (int j=i+1; j<list.size(); j++){
					HistoricActivityInstance hi = list.get(j);
					Integer actNumA = actMap.get(hi.getActivityId());
					if ((actNumA != null && actNumA < actNum) || StringUtils.equals(hi.getActivityId(), histIns.getActivityId())){
						bl = true;
					}
				}
				if (!bl){
					break;
				}
			}
		}
		return actList;
	}

	/**
	 * 获取流程列表
	 * @param category 流程分类
	 */
	public Page<Object[]> processList(Page<Object[]> page, String category) {
		/*
		 * 保存两个对象，一个是ProcessDefinition（流程定义），一个是Deployment（流程部署）
		 */
	    ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery()
	    		.latestVersion().active().orderByProcessDefinitionKey().asc();
	    
	    if (StringUtils.isNotEmpty(category)){
	    	processDefinitionQuery.processDefinitionCategory(category);
		}
	    
	    page.setCount(processDefinitionQuery.count());
	    
	    List<ProcessDefinition> processDefinitionList = processDefinitionQuery.listPage(page.getFirstResult(), page.getMaxResults());
	    for (ProcessDefinition processDefinition : processDefinitionList) {
	      String deploymentId = processDefinition.getDeploymentId();
	      Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
	      page.getList().add(new Object[]{processDefinition, deployment});
	    }
		return page;
	}
	
	/**
	 * 获取流程表单（首先获取任务节点表单KEY，如果没有则取流程开始节点表单KEY）
	 * @return
	 */
	public String getFormKey(String procDefId, String taskDefKey){
		String formKey = "";
		if (StringUtils.isNotBlank(procDefId)){
			if (StringUtils.isNotBlank(taskDefKey)){
				try{
					formKey = formService.getTaskFormKey(procDefId, taskDefKey);
				}catch (Exception e) {
					formKey = "";
				}
			}
			if (StringUtils.isBlank(formKey)){
				formKey = formService.getStartFormKey(procDefId);
			}
			if (StringUtils.isBlank(formKey)){
				formKey = "/404";
			}
		}
		logger.debug("getFormKey: {}", formKey);
		return formKey;
	}
	
	/**
	 * 获取流程实例对象
	 * @param procInsId
	 * @return
	 */
	@Transactional(readOnly = false)
	public ProcessInstance getProcIns(String procInsId) {
		return runtimeService.createProcessInstanceQuery().processInstanceId(procInsId).singleResult();
	}

	/**
	 * 启动流程
	 * @param procDefKey 流程定义KEY
	 * @param businessTable 业务表表名
	 * @param businessId	业务表编号
	 * @return 流程实例ID
	 */
	@Transactional(readOnly = false)
	public String startProcess(String procDefKey, String businessTable, String businessId) {
		return startProcess(procDefKey, businessTable, businessId, "");
	}
	
	/**
	 * 启动流程
	 * @param procDefKey 流程定义KEY
	 * @param businessTable 业务表表名
	 * @param businessId	业务表编号
	 * @param title			流程标题，显示在待办任务标题
	 * @return 流程实例ID
	 */
	@Transactional(readOnly = false)
	public String startProcess(String procDefKey, String businessTable, String businessId, String title) {
		Map<String, Object> vars = Maps.newHashMap();
		return startProcess(procDefKey, businessTable, businessId, title, vars);
	}
	
	/**
	 * 启动流程(发起人通过参数传入)
	 * @param userId 用户Id
	 * @param procDefKey 流程定义KEY
	 * @param businessTable 业务表表名
	 * @param businessId	业务表编号
	 * @param title			流程标题，显示在待办任务标题
	 * @param vars			流程变量
	 * @return 流程实例ID
	 */
	@Transactional(readOnly = false)
	public String startProcess(String userId,String procDefKey, String businessTable, String businessId, String title, Map<String, Object> vars) {
		
		userId = "u_" + userId; //发起人的id(参数)
		
		// 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
		identityService.setAuthenticatedUserId(userId);
		
		// 设置流程变量
		if (vars == null){
			vars = Maps.newHashMap();
		}
		
		// 设置流程标题
		if (StringUtils.isNotBlank(title)){
			vars.put("title", title);
		}
		
		// 启动流程
		ProcessInstance procIns = runtimeService.startProcessInstanceByKey(procDefKey, businessTable+":"+businessId, vars);
		
		// 更新业务表流程实例ID
		Act act = new Act();
		act.setBusinessTable(businessTable);// 业务表名
		act.setBusinessId(businessId);	// 业务表ID
		act.setProcInsId(procIns.getId());
		actDao.updateProcInsIdByBusinessId(act);
		
		return act.getProcInsId();
	}
	
	/**
	 * 启动流程(发起人为当前登录用户)
	 * @param procDefKey 流程定义KEY
	 * @param businessTable 业务表表名
	 * @param businessId	业务表编号
	 * @param title			流程标题，显示在待办任务标题
	 * @param vars			流程变量
	 * @return 流程实例ID
	 */
	@Transactional(readOnly = false)
	public String startProcess(String procDefKey, String businessTable, String businessId, String title, Map<String, Object> vars){
		
		String userId = UserUtils.getUser().getId();
		userId = "u_" + userId;
		
		// 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
		identityService.setAuthenticatedUserId(userId);
		
		// 设置流程变量
		if (vars == null){
			vars = Maps.newHashMap();
		}
		
		// 设置流程标题
		if (StringUtils.isNotBlank(title)){
			vars.put("title", title);
		}
		
		// 启动流程
		ProcessInstance procIns = runtimeService.startProcessInstanceByKey(procDefKey, businessTable+":"+businessId, vars);
		
		// 更新业务表流程实例ID
		Act act = new Act();
		act.setBusinessTable(businessTable);// 业务表名
		act.setBusinessId(businessId);	// 业务表ID
		act.setProcInsId(procIns.getId());
		actDao.updateProcInsIdByBusinessId(act);
		
		return act.getProcInsId();
	}

	/**
	 * 获取任务
	 * @param taskId 任务ID
	 */
	public Task getTask(String taskId){
		return taskService.createTaskQuery().taskId(taskId).singleResult();
	}
	
	/**
	 * 删除任务
	 * @param taskId 任务ID
	 * @param deleteReason 删除原因
	 */
	public void deleteTask(String taskId, String deleteReason){
		taskService.deleteTask(taskId, deleteReason);
	}
	
	/**
	 * 签收任务
	 * @param taskId 任务ID
	 * @param userId 签收用户ID（u_userId）
	 */
	@Transactional(readOnly = false)
	public void claim(String taskId, String userId){
		taskService.claim(taskId, userId);
	}
	
	/**
	 * 提交任务, 并保存意见
	 * @param taskId 任务ID
	 * @param procInsId 流程实例ID，如果为空，则不保存任务提交意见
	 * @param comment 任务提交意见的内容
	 * @param vars 任务变量
	 */
	@Transactional(readOnly = false)
	public void complete(String taskId, String procInsId, String comment, Map<String, Object> vars){
		complete(taskId, procInsId, comment, "", vars);
	}
	
	/**
	 * 提交任务, 并保存意见
	 * @param taskId 任务ID
	 * @param procInsId 流程实例ID，如果为空，则不保存任务提交意见
	 * @param comment 任务提交意见的内容
	 * @param title			流程标题，显示在待办任务标题
	 * @param vars 任务变量
	 */
	@Transactional(readOnly = false)
	public void complete(String taskId, String procInsId, String comment, String title, Map<String, Object> vars){
		// 添加意见
		if (StringUtils.isNotBlank(procInsId) && StringUtils.isNotBlank(comment)){
			taskService.addComment(taskId, procInsId, comment);
		}
		
		// 设置流程变量
		if (vars == null){
			vars = Maps.newHashMap();
		}
		
		// 设置流程标题
		if (StringUtils.isNotBlank(title)){
			vars.put("title", title);
		}
		
		// 提交任务
		taskService.complete(taskId, vars);
	}
	/**
	 * 针对serviceTask，发送一个继续的信号。
	 * @param procInsId
	 */
	public void signal(String procInsId){
		runtimeService.signal(procInsId);
	}

	/**
	 * 完成第一个任务
	 * @param procInsId
	 */
	public void completeFirstTask(String procInsId){
		completeFirstTask(procInsId, null, null, null);
	}
	
	/**
	 * 完成第一个任务
	 * @param procInsId
	 * @param comment
	 * @param title
	 * @param vars
	 */
	public void completeFirstTask(String procInsId, String comment, String title, Map<String, Object> vars){
		
		User user = UserUtils.getUser();
		// 1)获取登录用户的id. 
		String userId   = user.getId();
		// 2)获取登录用户的roleId(取一个角色)
		List<Role> roleList = UserUtils.get(userId).getRoleList();
		String roleId   = "";
		if(roleList != null && roleList.size() > 0){
			roleId = roleList.get(0).getId();	
		}
		// 3)获取登录用户的officeId。
		String officeId = "";
		if(user.getOffice() != null){
		    officeId =  user.getOffice().getId();
		}
		
		userId = "u_" + userId; // 重新设定userId。
		SysParameterModel sysParameterModel = SysParameterUtils.findKeyword("workFlowAssignee");
		
		// 按用户查找
		Task task = taskService.createTaskQuery().processInstanceId(procInsId).active().singleResult();
		
		if (task != null && "confirmResult".equals(task.getTaskDefinitionKey())){
			task.setAssignee("u_099");
			
			complete(task.getId(), procInsId, comment, title, vars);
			return; 
		}
		
		if("1".equals(sysParameterModel.getValue1()) && StringUtils.isNotBlank(roleId)){
			roleId = "r_" + roleId;
			// 按角色查找
			task = taskService.createTaskQuery().taskAssignee(roleId).processInstanceId(procInsId).active().singleResult();
			if(task!=null){
				complete(task.getId(), procInsId, comment, title, vars);
				return; 
			}
		}
		if("1".equals(sysParameterModel.getValue2()) && StringUtils.isNotBlank(officeId)){
			officeId = "o_" + officeId;
			// 按组织机构查找
			task = taskService.createTaskQuery().taskAssignee(officeId).processInstanceId(procInsId).active().singleResult();
			if(task!= null){
				complete(task.getId(), procInsId, comment, title, vars);
				return;
			}
		}	
	}

	/**
	 * 委派任务
	 * @param taskId 任务ID
	 * @param userId 被委派人
	 */
	public void delegateTask(String taskId, String userId){
		taskService.delegateTask(taskId, userId);
	}
	
	/**
	 * 被委派人完成任务
	 * @param taskId 任务ID
	 */
	public void resolveTask(String taskId){
		taskService.resolveTask(taskId);
	}

	/**
	 * 回退任务
	 * @param Act
	 * @param userId 当前用户id，不加u_
	 */
	@Transactional(readOnly = false)
	public JQResultModel recallTask(Act act,String userId){
		
		JQResultModel resultModel = new JQResultModel();
		
		String procInsId = act.getProcInsId();
		
		//获取上一环节的节点id(如initConfirm)
		SysWfAssign sysWfAssign=new SysWfAssign();
		sysWfAssign.setProcDefId(act.getProcDefId());
		sysWfAssign.setTaskDefKey(act.getTaskDefKey());
		
		SysWfAssignModel sysWfAssignModel= sysWfAssignService.findPrevTaskInfo(sysWfAssign);
		if(sysWfAssignModel==null){
			// 无法获取到上一环节。
			resultModel.setResult(-2);
			resultModel.setMsg("在流程环节定义(syswfassign)中没有找到上一环节，无法回退！");
			return resultModel;
		}
		//获取已经结束的上一环节的信息。
		HistoricActivityInstance hais = historyService.createHistoricActivityInstanceQuery()
				    .processInstanceId(procInsId )  // 流程实例id
				    .finished()     //即上个环节已经完成
				    .activityId(sysWfAssignModel.getTaskDefKey())   // 上一环节的TaskDefKey
				    .orderByHistoricActivityInstanceEndTime().desc() //按完成时间的倒序（即最新完成的）。
				    .list().get(0);
		if(hais==null){
			resultModel.setResult(-3);
			resultModel.setMsg("没有找到上一环节的处理数据，无法回退！");
			return resultModel;	
		}
		
		String toUserId = ""; //获取上个环节的用户ID，如 u_234
		if("start".equals(sysWfAssignModel.getTaskDefKey())){
			//1）开始节点，使用从流程中获取的发起者id。(注意 从HistoricActivityInstance中获取不到Assignee)
			toUserId = act.getInitiatorUserId();
		}
		else{//2)其他节点，获取HistoricActivityInstance中的Assignee。
			toUserId = hais.getAssignee(); 
		}
		
		if(StringUtils.isBlank(toUserId)){
			resultModel.setResult(-4);
			resultModel.setMsg("没有找到上一环节的处理人，无法回退！");
			return resultModel;
		}
		
		try {
			// 执行回退操作。参数: taskId:当前任务的id,ActivityId:上一环节的ActivityId,如'initConfirm'
			if("start".equals(sysWfAssignModel.getTaskDefKey())){
				//如果退回的上一个节点为start,则强制终止流程。
				callBackProcess(act.getTaskId(), "end",act.getComment());
			}
			else{// 回退到其他节点。
				callBackProcess(act.getTaskId(), sysWfAssignModel.getTaskDefKey(),act.getComment());
			}
			// 判断是否巡检系统
			
			resultModel.setResult(1); // 撤回OK.
			return resultModel;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			resultModel.setResult(-5);
			resultModel.setMsg("处理异常,请联系管理员。消息"+e1.getMessage());
			return resultModel;
		}
	}
	/**
	 * 委托任务的处理
	 * @param taskId
	 * @param procInsId
	 * @param toUserId   委托用户id, 不加u_
	 * @param fromUserId 当前用户id，不加u_
	 */
	@Transactional(readOnly = false)
	public JQResultModel appointTask(String taskId,String procInsId,String toUserId,String fromUserId){
		
		JQResultModel resultModel = new JQResultModel();
		try {
			User fromUser = UserUtils.get(fromUserId);
			
			taskService.setAssignee(taskId, "u_" + toUserId);
			taskService.setOwner(taskId, "u_" + fromUser.getId());
			
			resultModel.setResult(0);
			resultModel.setMsg("委托成功");
		} catch (Exception e) {
			resultModel.setResult(1);
			resultModel.setMsg("委托失败,错误信息:"+e.getMessage());
		}
		return resultModel;
		
	}
	
	/**
	 * 委托任务的处理
	 * @param taskDataArray 流程ID与taskId拼接数据组
	 * @param toUserId   委托用户id, 不加u_
	 * @param fromUserId 当前用户id，不加u_
	 */
	@Transactional(readOnly = false)
	public int pcAppointTask(String[] taskDataArray, String toUserId,String fromUserId){
		int flag = 0;
		try {
			User fromUser = UserUtils.get(fromUserId);
			String msgDtl = "";
			for(int i = 0;i < taskDataArray.length;i++){
				String[] procInsAndTask = taskDataArray[i].split("/");
				String procInsId = procInsAndTask[0];
				String taskId = procInsAndTask[1];
				taskService.setAssignee(taskId, "u_" + toUserId);
				taskService.setOwner(taskId, "u_" + fromUser.getId());
				TaskQuery todoTaskQuery = taskService.createTaskQuery().processInstanceId(procInsId).active().includeProcessVariables().orderByTaskCreateTime().desc();
				Task task = todoTaskQuery.list().get(0);
				msgDtl = msgDtl + task.getProcessVariables().get("title");
			}
		} catch (Exception e) {
			flag = -1;
		}
		return flag;
	}
	/** 
     * 取回流程 
     *  参考网址: http://blog.csdn.net/rosten/article/details/38300267
     * @param taskId 
     *            当前任务ID 
     * @param activityId 
     *            取回节点ID (如 initConfirm)
     * @throws Exception 
     */  
    public void callBackProcess(String taskId, String activityId,String message)  
            throws Exception {
    	
        if (StringUtils.isBlank(activityId)) {  
            throw new Exception("目标节点ID(activityId)为空！");  
        }  
        
        String processInstanceId = "";
        processInstanceId = findProcessInstanceByTaskId(taskId).getId();
        
        // 查找所有并行任务节点，同时取回  
        List<Task> taskList = findTaskListByKey(processInstanceId, findTaskById(taskId).getTaskDefinitionKey());  
        for (Task task : taskList) {  
            commitProcess(processInstanceId,task.getId(), null, activityId,message);  
        }  
    }  
    /** 
     * @param taskId 
     *            当前任务ID 
     * @param variables 
     *            流程变量 
     * @param activityId 
     *            流程转向执行任务节点ID<br> 
     *            此参数为空，默认为提交操作 
     * @throws Exception 
     */  
    private void commitProcess(String processInstanceId,String taskId, Map<String, Object> variables,  
            String activityId,String message) throws Exception {  
        if (variables == null) {  
            variables = new HashMap<String, Object>();  
        }  
        // 跳转节点为空，默认提交操作  
        if (StringUtils.isBlank(activityId)) {  
            taskService.complete(taskId, variables);  
        } else {// 流程转向操作  
            turnTransition(processInstanceId,taskId, activityId, variables,message);  
        }  
    } 
    /** 
     * 流程转向操作 
     * @param ProcessInstance 
     *            流程实例ID  
     * @param taskId 
     *            当前任务ID 
     * @param activityId 
     *            目标节点任务ID 
     * @param variables 
     *            流程变量 
     * @throws Exception 
     */  
    private void turnTransition(String processInstanceId,String taskId, String activityId,  
            Map<String, Object> variables,String message) throws Exception {  
        // 当前节点  
        ActivityImpl currActivity = findActivitiImpl(taskId, null);  
        // 清空当前流向  
        List<PvmTransition> oriPvmTransitionList = clearTransition(currActivity);  
  
        // 创建新流向  
        TransitionImpl newTransition = currActivity.createOutgoingTransition();  
        // 目标节点  
        ActivityImpl pointActivity = findActivitiImpl(taskId, activityId);  
        // 设置新流向的目标节点  
        newTransition.setDestination(pointActivity);  
        taskService.addComment(taskId, processInstanceId, message);
        
        // 执行转向任务  
        taskService.complete(taskId, variables); 
    
        // 删除目标节点新流入  
        pointActivity.getIncomingTransitions().remove(newTransition);  
  
        // 还原以前流向  
        restoreTransition(currActivity, oriPvmTransitionList);  
    } 
    /** 
     * 清空指定活动节点流向 
     *  
     * @param activityImpl 
     *            活动节点 
     * @return 节点流向集合 
     */  
    private List<PvmTransition> clearTransition(ActivityImpl activityImpl) {  
        // 存储当前节点所有流向临时变量  
        List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>();  
        // 获取当前节点所有流向，存储到临时变量，然后清空  
        List<PvmTransition> pvmTransitionList = activityImpl  
                .getOutgoingTransitions();  
        for (PvmTransition pvmTransition : pvmTransitionList) {  
            oriPvmTransitionList.add(pvmTransition);  
        }  
        pvmTransitionList.clear();  
  
        return oriPvmTransitionList;  
    }  
    /** 
     * 根据任务ID和节点ID获取活动节点 <br> 
     *  
     * @param taskId 
     *            任务ID 
     * @param activityId 
     *            活动节点ID <br> 
     *            如果为null或""，则默认查询当前活动节点 <br> 
     *            如果为"end"，则查询结束节点 <br> 
     *  
     * @return 
     * @throws Exception 
     */  
    private ActivityImpl findActivitiImpl(String taskId, String activityId)  
            throws Exception {  
        // 取得流程定义  
        ProcessDefinitionEntity processDefinition = findProcessDefinitionEntityByTaskId(taskId);  
  
        // 获取当前活动节点ID  
        if (StringUtils.isBlank(activityId)) {  
            activityId = findTaskById(taskId).getTaskDefinitionKey();  
        }  
  
        // 根据流程定义，获取该流程实例的结束节点  
        if (activityId.toUpperCase().equals("END")) {  
            for (ActivityImpl activityImpl : processDefinition.getActivities()) {  
                List<PvmTransition> pvmTransitionList = activityImpl  
                        .getOutgoingTransitions();  
                if (pvmTransitionList.isEmpty()) {  
                    return activityImpl;  
                }  
            }  
        }  
  
        // 根据节点ID，获取对应的活动节点  
        ActivityImpl activityImpl = ((ProcessDefinitionImpl) processDefinition)  
                .findActivity(activityId);  
  
        return activityImpl;  
    } 
    /** 
     * 根据任务ID获取流程定义 
     *  
     * @param taskId 
     *            任务ID 
     * @return 
     * @throws Exception 
     */  
    private ProcessDefinitionEntity findProcessDefinitionEntityByTaskId(  
            String taskId) throws Exception {  
        // 取得流程定义  
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)  
                .getDeployedProcessDefinition(findTaskById(taskId)  
                        .getProcessDefinitionId());  
  
        if (processDefinition == null) {  
            throw new Exception("流程定义未找到!");  
        }  
  
        return processDefinition;  
    }  
    /** 
     * 根据任务ID获取对应的流程实例 
     *  
     * @param taskId 
     *            任务ID 
     * @return 
     * @throws Exception 
     */  
    private ProcessInstance findProcessInstanceByTaskId(String taskId)  
            throws Exception {  
        // 找到流程实例  
        ProcessInstance processInstance = runtimeService  
                .createProcessInstanceQuery().processInstanceId(  
                        findTaskById(taskId).getProcessInstanceId())  
                .singleResult();  
        if (processInstance == null) {  
            throw new Exception("流程实例未找到!");  
        }  
        return processInstance;  
    } 
    /** 
     * 还原指定活动节点流向 
     *  
     * @param activityImpl 
     *            活动节点 
     * @param oriPvmTransitionList 
     *            原有节点流向集合 
     */  
    private void restoreTransition(ActivityImpl activityImpl,  
            List<PvmTransition> oriPvmTransitionList) {  
        // 清空现有流向  
        List<PvmTransition> pvmTransitionList = activityImpl  
                .getOutgoingTransitions();  
        pvmTransitionList.clear();  
        // 还原以前流向  
        for (PvmTransition pvmTransition : oriPvmTransitionList) {  
            pvmTransitionList.add(pvmTransition);  
        }  
    }  
    /** 
     * 根据任务ID获得任务实例 
     *  
     * @param taskId 
     *            任务ID 
     * @return 
     * @throws Exception 
     */  
    private TaskEntity findTaskById(String taskId) throws Exception {  
        TaskEntity task = (TaskEntity) taskService.createTaskQuery().taskId(  
                taskId).singleResult();  
        if (task == null) {  
            throw new Exception("任务实例未找到!");  
        }  
        return task;  
    }  
    /** 
     * 根据流程实例ID和任务key值查询所有同级任务集合 
     *  
     * @param processInstanceId 
     * @param key 
     * @return 
     */  
    public List<Task> findTaskListByKey(String processInstanceId, String key) {  
        return taskService.createTaskQuery().processInstanceId(  
                processInstanceId).taskDefinitionKey(key).list();  
    }  
	////////////////////////////////////////////////////////////////////
	
	/**
	 * 读取带跟踪的图片
	 * @param executionId	环节ID
	 * @return	封装了各种节点信息
	 */
	public InputStream tracePhoto(String processDefinitionId, String executionId) {
		// ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(executionId).singleResult();
		BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
		
		List<String> activeActivityIds = Lists.newArrayList();
		if (runtimeService.createExecutionQuery().executionId(executionId).count() > 0){
			activeActivityIds = runtimeService.getActiveActivityIds(executionId);
		}
		
		// 不使用spring请使用下面的两行代码
		// ProcessEngineImpl defaultProcessEngine = (ProcessEngineImpl)ProcessEngines.getDefaultProcessEngine();
		// Context.setProcessEngineConfiguration(defaultProcessEngine.getProcessEngineConfiguration());

		// 使用spring注入引擎请使用下面的这行代码
		Context.setProcessEngineConfiguration(processEngine.getProcessEngineConfiguration());

		return ProcessDiagramGenerator.generateDiagram(bpmnModel, "png", activeActivityIds);
	}
	
	/**
	 * 流程跟踪图信息
	 * @param processInstanceId		流程实例ID
	 * @return	封装了各种节点信息
	 */
	public List<Map<String, Object>> traceProcess(String processInstanceId) throws Exception {
		Execution execution = runtimeService.createExecutionQuery().executionId(processInstanceId).singleResult();//执行实例
		Object property = PropertyUtils.getProperty(execution, "activityId");
		String activityId = "";
		if (property != null) {
			activityId = property.toString();
		}
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId)
				.singleResult();
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(processInstance.getProcessDefinitionId());
		List<ActivityImpl> activitiList = processDefinition.getActivities();//获得当前任务的所有节点

		List<Map<String, Object>> activityInfos = new ArrayList<Map<String, Object>>();
		for (ActivityImpl activity : activitiList) {

			boolean currentActiviti = false;
			String id = activity.getId();

			// 当前节点
			if (id.equals(activityId)) {
				currentActiviti = true;
			}

			Map<String, Object> activityImageInfo = packageSingleActivitiInfo(activity, processInstance, currentActiviti);

			activityInfos.add(activityImageInfo);
		}

		return activityInfos;
	}

	/**
	 * 封装输出信息，包括：当前节点的X、Y坐标、变量信息、任务类型、任务描述
	 * @param activity
	 * @param processInstance
	 * @param currentActiviti
	 * @return
	 */
	private Map<String, Object> packageSingleActivitiInfo(ActivityImpl activity, ProcessInstance processInstance,
			boolean currentActiviti) throws Exception {
		Map<String, Object> vars = new HashMap<String, Object>();
		Map<String, Object> activityInfo = new HashMap<String, Object>();
		activityInfo.put("currentActiviti", currentActiviti);
		setPosition(activity, activityInfo);
		setWidthAndHeight(activity, activityInfo);

		Map<String, Object> properties = activity.getProperties();
		vars.put("节点名称", properties.get("name"));
		vars.put("任务类型", ActUtils.parseToZhType(properties.get("type").toString()));

		ActivityBehavior activityBehavior = activity.getActivityBehavior();
		logger.debug("activityBehavior={}", activityBehavior);
		if (activityBehavior instanceof UserTaskActivityBehavior) {

			Task currentTask = null;

			// 当前节点的task
			if (currentActiviti) {
				currentTask = getCurrentTaskInfo(processInstance);
			}

			// 当前任务的分配角色
			UserTaskActivityBehavior userTaskActivityBehavior = (UserTaskActivityBehavior) activityBehavior;
			TaskDefinition taskDefinition = userTaskActivityBehavior.getTaskDefinition();
			Set<Expression> candidateGroupIdExpressions = taskDefinition.getCandidateGroupIdExpressions();
			if (!candidateGroupIdExpressions.isEmpty()) {

				// 任务的处理角色
				setTaskGroup(vars, candidateGroupIdExpressions);

				// 当前处理人
				if (currentTask != null) {
					setCurrentTaskAssignee(vars, currentTask);
				}
			}
		}

		vars.put("节点说明", properties.get("documentation"));

		String description = activity.getProcessDefinition().getDescription();
		vars.put("描述", description);

		logger.debug("trace variables: {}", vars);
		activityInfo.put("vars", vars);
		return activityInfo;
	}

	/**
	 * 设置任务组
	 * @param vars
	 * @param candidateGroupIdExpressions
	 */
	private void setTaskGroup(Map<String, Object> vars, Set<Expression> candidateGroupIdExpressions) {
		String roles = "";
		for (Expression expression : candidateGroupIdExpressions) {
			String expressionText = expression.getExpressionText();
			String roleName = identityService.createGroupQuery().groupId(expressionText).singleResult().getName();
			roles += roleName;
		}
		vars.put("任务所属角色", roles);
	}

	/**
	 * 设置当前处理人信息
	 * @param vars
	 * @param currentTask
	 */
	private void setCurrentTaskAssignee(Map<String, Object> vars, Task currentTask) {
		String assignee = currentTask.getAssignee();
		if (assignee != null) {
			org.activiti.engine.identity.User assigneeUser = identityService.createUserQuery().userId(assignee).singleResult();
			String userInfo = assigneeUser.getFirstName() + " " + assigneeUser.getLastName();
			vars.put("当前处理人", userInfo);
		}
	}

	/**
	 * 获取当前节点信息
	 * @param processInstance
	 * @return
	 */
	private Task getCurrentTaskInfo(ProcessInstance processInstance) {
		Task currentTask = null;
		try {
			String activitiId = (String) PropertyUtils.getProperty(processInstance, "activityId");
			logger.debug("current activity id: {}", activitiId);

			currentTask = taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskDefinitionKey(activitiId)
					.singleResult();
			logger.debug("current task for processInstance: {}", ToStringBuilder.reflectionToString(currentTask));

		} catch (Exception e) {
			logger.error("can not get property activityId from processInstance: {}", processInstance);
		}
		return currentTask;
	}

	/**
	 * 设置宽度、高度属性
	 * @param activity
	 * @param activityInfo
	 */
	private void setWidthAndHeight(ActivityImpl activity, Map<String, Object> activityInfo) {
		activityInfo.put("width", activity.getWidth());
		activityInfo.put("height", activity.getHeight());
	}

	/**
	 * 设置坐标位置
	 * @param activity
	 * @param activityInfo
	 */
	private void setPosition(ActivityImpl activity, Map<String, Object> activityInfo) {
		activityInfo.put("x", activity.getX());
		activityInfo.put("y", activity.getY());
	}
	
	/**
	 * 判断流程是否为删除结束
	 * @param procInsId
	 * @return true:是，false：否
	 */
	public boolean isEndByDel(String procInsId){
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(procInsId).singleResult();
        List<Act> histoicFlowList = histoicFlowList(procInsId, "start", "end");
        Act act = histoicFlowList.get(histoicFlowList.size() -1);
        boolean isEnd = !"end".equals(act.getHistIns().getActivityId());
        if(processInstance == null && isEnd){
        	return true;
        }
        return false;
	}
	
	/**
	 * 判断当前用户是否存在待处理的待办任务
	 * @param userId
	 * @return true：是，false：否
	 */
	public boolean isAssignee(String userId){
		TaskQuery todoTaskQuery = taskService.createTaskQuery().taskAssignee("u_" + userId).active().includeProcessVariables().orderByTaskCreateTime().desc();
		List<Task> list = todoTaskQuery.list();
		if(list != null && list.size() > 0){
			return true;
		}else{
			return false;
		}
	}
	
	public long todoCount(String userId){
		TaskQuery todoTaskQuery = taskService.createTaskQuery().taskAssignee("u_" + userId).active().includeProcessVariables().orderByTaskCreateTime().desc();
		return todoTaskQuery.count();

	}
	
	/**
	 * 手机端获取待办
	 */
	
	public List<Act> mbTodoList(Act act,User user,int firstResult,int maxResults,String macCode){
		List<Act> result = new ArrayList<Act>();
		// 1)获取登录用户的id. 
		String userId   = user.getId();
		// 2)获取登录用户的roleId(取一个角色)
		List<Role> roleList = UserUtils.get(userId).getRoleList();
		String roleId   = "";
		if(roleList != null && roleList.size() > 0){
			roleId = roleList.get(0).getId();	
		}
		// 3)获取登录用户的officeId。
		String officeId = "";
		if(user.getOffice() != null){
		    officeId =  user.getOffice().getId();
		}
		
		SysParameterModel sysParameterModel = SysParameterUtils.findKeyword("workFlowAssignee");
		
		List<String> assigneeCandidateList = new  ArrayList<String>();
		// if 系统参数(按用户设置)=true，则把"u_" + userId 加入列表。
		assigneeCandidateList.add("u_" + userId); //用户
		// if 系统参数(按角色设置)=true，则把"r_" + userId 加入列表。
		if("1".equals(sysParameterModel.getValue1()) && StringUtils.isNotBlank(roleId)){
		   assigneeCandidateList.add("r_" + roleId); //角色
		}
		//
		if("1".equals(sysParameterModel.getValue2()) && StringUtils.isNotBlank(officeId)){
		   assigneeCandidateList.add("o_" + officeId); //部门
		}
		
		for(String assigneeCandidate:assigneeCandidateList){
			// ===============(1) 已经签收的任务(assignee) ===============
			TaskQuery todoTaskQuery = taskService.createTaskQuery().taskAssignee(assigneeCandidate).active()
					.includeProcessVariables().orderByTaskCreateTime().desc();
			
			// 设置查询条件
			if (StringUtils.isNotBlank(act.getProcDefKey())){
				todoTaskQuery.processDefinitionKey(act.getProcDefKey());
			}
			if (act.getBeginDate() != null){
				todoTaskQuery.taskCreatedAfter(act.getBeginDate());
			}
			if (act.getEndDate() != null){
				todoTaskQuery.taskCreatedBefore(act.getEndDate());
			}
			//农商行新增按机具编码查询方法
			if(StringUtils.isNotBlank(macCode)){
				todoTaskQuery.processVariableValueEquals("mac_" + macCode);
			}
			// 查询列表
			List<Task> todoList = todoTaskQuery.listPage(firstResult, maxResults);
			
			for (Task task : todoList) {
				Act e = new Act();
				e.setTask(task);
				e.setVars(task.getProcessVariables());
	//			e.setTaskVars(task.getTaskLocalVariables());
	//			System.out.println(task.getId()+"  =  "+task.getProcessVariables() + "  ========== " + task.getTaskLocalVariables());
				e.setProcDef(ProcessDefCache.get(task.getProcessDefinitionId()));
	//			e.setProcIns(runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult());
	//			e.setProcExecUrl(ActUtils.getProcExeUrl(task.getProcessDefinitionId()));
				e.setStatus("todo");
				
				HistoricProcessInstance hi = historyService.createHistoricProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
				User startUser = UserUtils.get(hi.getStartUserId().substring(2));// 从第三位开始截取。u_2323 取成2323.
				if (startUser != null){
					// 获取发起人姓名
					e.setInitiatorName(startUser.getName());
					e.setInitiatorUserId(hi.getStartUserId()); //发起人的用户id
				}
				result.add(e);
			}
			
			// ===============(2) 等待签收的任务(CandidateUser)  ===============
			TaskQuery toClaimQuery = taskService.createTaskQuery().taskCandidateUser(assigneeCandidate)
					.includeProcessVariables().active().orderByTaskCreateTime().desc();
			
			// 设置查询条件
			if (StringUtils.isNotBlank(act.getProcDefKey())){
				toClaimQuery.processDefinitionKey(act.getProcDefKey());
			}
			if (act.getBeginDate() != null){
				toClaimQuery.taskCreatedAfter(act.getBeginDate());
			}
			if (act.getEndDate() != null){
				toClaimQuery.taskCreatedBefore(act.getEndDate());
			}
			//农商行新增按机具编码查询方法
			if(StringUtils.isNotBlank(macCode)){
				toClaimQuery.processVariableValueEquals("mac_" + macCode);
			}
			// 查询列表
			List<Task> toClaimList = toClaimQuery.listPage(firstResult, maxResults);
			
			for (Task task : toClaimList) {
				Act e = new Act();
				e.setTask(task);
				e.setVars(task.getProcessVariables());
	//			e.setTaskVars(task.getTaskLocalVariables());
	//			System.out.println(task.getId()+"  =  "+task.getProcessVariables() + "  ========== " + task.getTaskLocalVariables());
				e.setProcDef(ProcessDefCache.get(task.getProcessDefinitionId()));
	//			e.setProcIns(runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult());
	//			e.setProcExecUrl(ActUtils.getProcExeUrl(task.getProcessDefinitionId()));
				e.setStatus("claim");
				
				HistoricProcessInstance hi = historyService.createHistoricProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
				User startUser = UserUtils.get(hi.getStartUserId().substring(2));// 从第三位开始截取。u_2323 取成2323.
				if (startUser != null){
					// 获取发起人姓名
					e.setInitiatorName(startUser.getName());
					e.setInitiatorUserId(hi.getStartUserId()); //发起人的用户id
				}
				result.add(e);
			}
		}
		for(Act actModel :result){
			//获取上一环节的节点id(如initConfirm)
			SysWfAssign sysWfAssign=new SysWfAssign();
			sysWfAssign.setProcDefId(actModel.getProcDef().getId());
			sysWfAssign.setTaskDefKey(actModel.getTask().getTaskDefinitionKey());
			SysWfAssignModel sysWfAssignModel= sysWfAssignService.findNextTaskInfo(sysWfAssign);
			if(sysWfAssignModel != null){
				List<HistoricActivityInstance> hais = historyService.createHistoricActivityInstanceQuery().processInstanceId(actModel.getTask().getProcessInstanceId())  // 流程实例id
					    .finished()     //即下个环节已经完成
					    .activityId(sysWfAssignModel.getTaskDefKey())   // 上一环节的TaskDefKey
					    .orderByHistoricActivityInstanceEndTime().desc() //按完成时间的倒序（即最新完成的）。
					    .list();
				if(hais.size() > 0 ){
					String taskId = hais.get(0).getTaskId();
					if(taskService.getTaskComments(taskId).size() > 0){
						String comment = taskService.getTaskComments(taskId).get(0).getFullMessage();
						actModel.setComment("撤回原因：" + comment);
					}
				}
			}
		}
		return result;
	}
}
