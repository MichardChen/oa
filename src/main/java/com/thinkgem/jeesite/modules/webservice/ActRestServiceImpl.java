package com.thinkgem.jeesite.modules.webservice;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.JQResultModel;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.modules.act.entity.Act;
import com.thinkgem.jeesite.modules.act.model.ActModel;
import com.thinkgem.jeesite.modules.act.service.ActProcessService;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.act.utils.ActUtils;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 工作流Act
 * @author yuyabiao
 * @since 2016/6/8
 * 访问地址 atminspect/webservice/rest/actRest/...
 */
@Path(value="/actRest")
public class ActRestServiceImpl implements ActRestService{
	
	@Autowired
	private ActTaskService actTaskService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private ActProcessService actProcessService;
	
	/**
	 * 初始化查询
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="findToDoList")
	public String findToDoList(@Context HttpServletRequest request,@Context HttpServletResponse response){
		// 检查webService的访问权限。
		String checkAccessAuth = UserUtils.checkWebSvrAccessAuth(request);
		if(!"0".equals(checkAccessAuth)){
			// 返回错误信息(JSON格式) 
			return checkAccessAuth;
		}
		JQResultModel resultModel = new JQResultModel();
		String userCode = request.getParameter("userCode");
		String macCode = request.getParameter("macCode");
		int maxResults = Integer.valueOf(Global.getConfig("page.pageSize"));
		int firstResult = (Integer.parseInt(request.getParameter("pageNo")) - 1) * maxResults;
		User user = UserUtils.getByLoginName(userCode);
		List<Act> list = actTaskService.mbTodoList(new Act(), user, firstResult, maxResults,macCode);
		if(list.size()>0){
			List<ActModel> resultList = new ArrayList<ActModel>();
			for(Act act : list){
				ActModel actModel = new ActModel();
				actModel.setTaskId(act.getTask().getId());
				actModel.setTaskName(act.getTask().getName());
				actModel.setTaskDefKey(act.getTask().getTaskDefinitionKey());
				actModel.setProcInsId(act.getTask().getProcessInstanceId());
				actModel.setProcDefId(act.getTask().getProcessDefinitionId());
				actModel.setInitiatorName(act.getInitiatorName());
				actModel.setInitiatorUserId(act.getInitiatorUserId()); //发起者UserId, u_343等。
				actModel.setTitle((String)act.getVars().getMap().get("title"));
				actModel.setStatus(act.getStatus());
				actModel.setAssignee(act.getTask().getAssignee());
				actModel.setBeginDate(act.getTask().getCreateTime());
				actModel.setUserId((String)act.getTask().getProcessVariables().get("curUserId"));
				actModel.setStartFlag((String)act.getTask().getProcessVariables().get("startFlag"));
				actModel.setComment(act.getComment());
				resultList.add(actModel);
			}
			resultModel.setResult(0);
			resultModel.setRows(resultList);
		}else{//无待办
			resultModel.setResult(-2);
		}
		return JSON.toJSONString(resultModel);
	}
	
	/**
	 * 查询已办
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="completeList")
	public String completeList(@Context HttpServletRequest request,@Context HttpServletResponse response){
		// 检查webService的访问权限。
		String checkAccessAuth = UserUtils.checkWebSvrAccessAuth(request);
		if(!"0".equals(checkAccessAuth)){
			// 返回错误信息(JSON格式) 
			return checkAccessAuth;
		}
		JQResultModel resultModel = new JQResultModel();
		String userCode = request.getParameter("userCode");
		User user = UserUtils.getByLoginName(userCode);
		Page<Act> page = actTaskService.completeList(new Page<Act>(request, response),new Act(), user);
		if(page.getList().size() > 0){
			List<ActModel> resultList = new ArrayList<ActModel>();
			for(Act act : page.getList()){
				ActModel actModel = new ActModel();
				actModel.setInitiatorName(act.getInitiatorName());
				actModel.setTitle((String)act.getVars().getMap().get("title"));
				actModel.setBeginDate(act.getHistProc().getStartTime());
				resultList.add(actModel);
			}
			resultModel.setResult(0);
			resultModel.setRows(resultList);
		}else{
			resultModel.setResult(-2);
		}
		return JSON.toJSONString(resultModel);
	}
	
	/**
	 * 查询历史已办
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="historyList")
	public String historyList(@Context HttpServletRequest request,@Context HttpServletResponse response){
		// 检查webService的访问权限。
		String checkAccessAuth = UserUtils.checkWebSvrAccessAuth(request);
		if(!"0".equals(checkAccessAuth)){
			// 返回错误信息(JSON格式) 
			return checkAccessAuth;
		}
		JQResultModel resultModel = new JQResultModel();
		String userCode = request.getParameter("userCode");
		User user = UserUtils.getByLoginName(userCode);
		Page<Act> page = actTaskService.historicList(new Page<Act>(request, response),new Act(), user);
		if(page.getList().size() > 0){
			List<ActModel> resultList = new ArrayList<ActModel>();
			for(Act act : page.getList()){
				ActModel actModel = new ActModel();
				actModel.setTaskId(act.getHistTask().getId());
				actModel.setTaskName(act.getHistTask().getName());
				actModel.setTaskDefKey(act.getHistTask().getTaskDefinitionKey());
				actModel.setProcInsId(act.getHistTask().getProcessInstanceId());
				actModel.setProcDefId(act.getHistTask().getProcessDefinitionId());
				actModel.setInitiatorName(act.getInitiatorName());
				actModel.setTitle((String)act.getVars().getMap().get("title"));
				actModel.setStatus(act.getStatus());
				actModel.setAssignee(act.getHistTask().getAssignee());
				actModel.setBeginDate(act.getHistTask().getEndTime());
				actModel.setUserId((String)act.getHistTask().getProcessVariables().get("curUserId"));
				actModel.setStartFlag((String)act.getHistTask().getProcessVariables().get("startFlag"));
				resultList.add(actModel);
			}
			resultModel.setResult(0);
			resultModel.setRows(resultList);
		}else{
			resultModel.setResult(-2);
		}
		return JSON.toJSONString(resultModel);
	}
	
	/**
	 * 签收办理任务
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="claimAndDo")
	public String claimAndDo(@Context HttpServletRequest request,@Context HttpServletResponse response) throws Exception{
		// 检查webService的访问权限。
		String checkAccessAuth = UserUtils.checkWebSvrAccessAuth(request);
		if(!"0".equals(checkAccessAuth)){
			// 返回错误信息(JSON格式) 
			return checkAccessAuth;
		}
		JQResultModel resultModel = new JQResultModel();
		Act act = new Act(); 
		String userCode = request.getParameter("userCode");
		User fromUser = UserUtils.getByLoginName(userCode);//当前用户
		String flag = request.getParameter("flag");//获取提交的标识,1:办理，2：签收
		String procInsId = request.getParameter("procInsId");
		String taskId = request.getParameter("taskId");
		String taskDefKey = request.getParameter("taskDefKey");
		String procDefId = request.getParameter("procDefId");
		String title     = request.getParameter("title");
		String initiatorUserId = request.getParameter("initiatorUserId");
		
		if("1".equals(flag)){//办理任务
			String taskName = URLDecoder.decode(request.getParameter("taskName"),"UTF-8");
			
			String status = request.getParameter("status");
			act.setTaskId(taskId);
			act.setTaskName(taskName);
			act.setTaskDefKey(taskDefKey);
			act.setProcInsId(procInsId);
			act.setProcDefId(procDefId);
			act.setStatus(status);
			// 获取流程XML上的表单KEY
			String formKey = actTaskService.getFormKey(act.getProcDefId(), act.getTaskDefKey());
			// 获取流程实例对象
			if (act.getProcInsId() != null){
				act.setProcIns(actTaskService.getProcIns(act.getProcInsId()));
			}
			String url = ActUtils.getFormUrl(formKey, act);
			if(url.indexOf("/static") != -1){//为手机页面
				resultModel.setResult(0);
				resultModel.setUserdata(url);
			}else{//电脑页面
				resultModel.setResult(-2);
				resultModel.setMsg("请登录电脑端办理");
			}
		}else if("3".equals(flag)){//流程回退
			//设置回退用参数
			act.setProcInsId(procInsId);
			act.setProcDefId(procDefId);
			act.setTaskDefKey(taskDefKey);
			act.setInitiatorUserId(initiatorUserId);
			act.setTitle(title);
			act.setTaskId(taskId);
			resultModel = actTaskService.recallTask(act, fromUser.getId());
		}
		return JSON.toJSONString(resultModel);
	}
	
	/**
	 * task信息
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="taskInfo")
	public String taskInfo(@Context HttpServletRequest request,@Context HttpServletResponse response){
		// 检查webService的访问权限。
		String checkAccessAuth = UserUtils.checkWebSvrAccessAuth(request);
		if(!"0".equals(checkAccessAuth)){
			// 返回错误信息(JSON格式) 
			return checkAccessAuth;
		}
		String procInsId = request.getParameter("procInsId");
		JQResultModel resultModel = new JQResultModel();
		TaskQuery todoTaskQuery = taskService.createTaskQuery().processInstanceId(procInsId).active().includeProcessVariables().orderByTaskCreateTime().desc();
		Task task = todoTaskQuery.list().get(0);
		String userId = task.getAssignee().substring(2);
		User user = UserUtils.get(userId);
		ActModel actModel = new ActModel();
		actModel.setActivityName(task.getName());
		actModel.setAssigneeName(user.getName());
		String title = (String)task.getProcessVariables().get("title");
		actModel.setTitle(title);
		resultModel.setResult(0);
		resultModel.setUserdata(actModel);
		return JSON.toJSONString(resultModel);
	}
	
	/**
	 * 获取可被委托人员
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="appointUser")
	public String appointUser(@Context HttpServletRequest request,@Context HttpServletResponse response){
		// 检查webService的访问权限。
		String checkAccessAuth = UserUtils.checkWebSvrAccessAuth(request);
		if (!"0".equals(checkAccessAuth)) {
			// 返回错误信息(JSON格式)
			return checkAccessAuth;
		}
		JQResultModel resultModel = new JQResultModel();
//		String userCode = request.getParameter("userCode");
//		String officeId;
//		User user = UserUtils.getByLoginName(userCode);
//		if(UserUtils.isAdmin(user.getId())){//如果为管理员,则查询所有人员的信息
//			officeId = "";
//		}else{
//			officeId = user.getOffice().getId();// 当前用户的部门ID
//		}
//		List<UserModel> list = workFlowUserService.findAllUser(officeId,user.getId());
//		if (list.size() > 0) {
//			resultModel.setResult(0);
//			resultModel.setRows(list);
//		} else {
//			resultModel.setResult(-2);
//			resultModel.setMsg("当前没有可供委托的人员,请与管理员联系");
//		}
		return JSON.toJSONString(resultModel);
	}
	
	/**
	 * 委托提交
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="sendAppoint")
	public String sendAppoint(@Context HttpServletRequest request,@Context HttpServletResponse response){
		// 检查webService的访问权限。
		String checkAccessAuth = UserUtils.checkWebSvrAccessAuth(request);
		if(!"0".equals(checkAccessAuth)){
			// 返回错误信息(JSON格式) 
			return checkAccessAuth;
		}
		JQResultModel resultModel = new JQResultModel();
		
		//获取被委托人的信息
		String toUserId = request.getParameter("userId");
		//User toUser = UserUtils.get(userId);
		String fromUserCode = request.getParameter("userCode");
		User fromUser = UserUtils.getByLoginName(fromUserCode);//获取当前用户
		String procInsId = request.getParameter("procInsId");
		String taskId = request.getParameter("taskId");
		
		resultModel = actTaskService.appointTask(taskId, procInsId, toUserId, fromUser.getId());
		
		return JSON.toJSONString(resultModel);
	}
	
	/**
	 * 删除流程
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="deleteProcIns")
	public String deleteProcIns(@Context HttpServletRequest request,@Context HttpServletResponse response){
		// 检查webService的访问权限。
		String checkAccessAuth = UserUtils.checkWebSvrAccessAuth(request);
		if(!"0".equals(checkAccessAuth)){
			// 返回错误信息(JSON格式) 
			return checkAccessAuth;
		}
		String procInsId = request.getParameter("procInsId");
		String reason = request.getParameter("reason");
		JQResultModel resultModel = new JQResultModel();
		int flag = actProcessService.deleteProcIns(procInsId, reason);
		if(flag == 0){
			resultModel.setResult(0);
		}else{
			resultModel.setResult(-2);
			resultModel.setMsg("删除失败");
		}
		return JSON.toJSONString(resultModel);
	}
	
	/**
	 * 回退流程
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="recall")
	public String recall(@Context HttpServletRequest request,@Context HttpServletResponse response){
		// 检查webService的访问权限。
		String checkAccessAuth = UserUtils.checkWebSvrAccessAuth(request);
		if(!"0".equals(checkAccessAuth)){
			// 返回错误信息(JSON格式) 
			return checkAccessAuth;
		}
		JQResultModel resultModel = new JQResultModel();
		Act act = new Act(); 
		String userCode = request.getParameter("userCode");
		User fromUser = UserUtils.getByLoginName(userCode);//当前用户
		String procInsId = request.getParameter("procInsId");
		String taskId = request.getParameter("taskId");
		String taskDefKey = request.getParameter("taskDefKey");
		String procDefId = request.getParameter("procDefId");
		String title     = request.getParameter("title");
		String initiatorUserId = request.getParameter("initiatorUserId");//流程回退
		String comment = request.getParameter("comment");
		//设置回退用参数
		act.setProcInsId(procInsId);
		act.setProcDefId(procDefId);
		act.setTaskDefKey(taskDefKey);
		act.setInitiatorUserId(initiatorUserId);
		act.setTitle(title);
		act.setTaskId(taskId);
		act.setComment(comment);
		resultModel = actTaskService.recallTask(act, fromUser.getId());
		return JSON.toJSONString(resultModel);
	}
	/**
	 * 查询待办条数
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="findToDoCount")
	public String findToDoCount(@Context HttpServletRequest request,@Context HttpServletResponse response){
		// 检查webService的访问权限。
		String checkAccessAuth = UserUtils.checkWebSvrAccessAuth(request);
		if(!"0".equals(checkAccessAuth)){
			// 返回错误信息(JSON格式) 
			return checkAccessAuth;
		}
		JQResultModel resultModel = new JQResultModel();
		String userCode = request.getParameter("userCode");
		User user = UserUtils.getByLoginName(userCode);
		long count = actTaskService.todoCount(user.getId());
		if(count>0){
			resultModel.setResult(0);
			resultModel.setUserdata(count);
		}else{//无待办
			resultModel.setResult(-2);
			resultModel.setUserdata(0);
		}
		return JSON.toJSONString(resultModel);
	}
	
	
}
