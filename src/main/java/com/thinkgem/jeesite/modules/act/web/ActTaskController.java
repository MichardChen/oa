/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.act.web;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.persistence.JQResultModel;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.act.entity.Act;
import com.thinkgem.jeesite.modules.act.service.ActProcessService;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.act.utils.ActUtils;
import com.thinkgem.jeesite.modules.sys.dao.CompanyRoleDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.model.UserModel;
import com.thinkgem.jeesite.modules.sys.service.SysParameterService;
import com.thinkgem.jeesite.modules.sys.service.SysWfAssignService;
import com.thinkgem.jeesite.modules.sys.utils.SysParameterUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 流程个人任务相关Controller
 * @author ThinkGem
 * @version 2013-11-03
 */
@Controller
@RequestMapping(value = "${adminPath}/act/task")
public class ActTaskController extends BaseController {

	@Autowired
	private ActTaskService actTaskService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private SysWfAssignService sysWfAssignService;
	
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private CompanyRoleDao companyRoleDao;
	
	@Autowired
	private SysParameterService sysParameterService;
		
	@Autowired
	private ActProcessService actProcessService;
	
	/**
	 * 获取待办列表
	 * @param procDefKey 流程定义标识
	 * @return
	 */
//	@RequestMapping(value = {"todo", ""})
//	public String todoList(Act act, UserModel userModel,HttpServletResponse response, Model model) throws Exception {
//		List<Act> list = actTaskService.todoList(act,UserUtils.getUser());
//		model.addAttribute("list", list);
//		if (UserUtils.getPrincipal().isMobileLogin()){
//			return renderString(response, list);
//		}
//		String officeId;
//		User user = UserUtils.getUser();
//		if(UserUtils.isAdmin(user.getId())){
//			officeId = "";
//		}else{
//			officeId = user.getOffice().getId();// 当前用户的部门ID
//		}
//		List<UserModel> userList = workFlowUserService.findAllUser(officeId,user.getId());
//		model.addAttribute("userList",userList);
//		model.addAttribute("userModel",userModel);
//		return "modules/act/actTaskTodoList";
//	}
	
	
	@RequestMapping(value = {"todo", ""})
	public String todoList(Act act, UserModel userModel,HttpServletRequest request,HttpServletResponse response, Model model) throws Exception {
		User user = UserUtils.getUser();
		Page<Act> page = actTaskService.todoList(new Page<Act>(request,response), act, user);
		for(Act actModel:page.getList()){
			String procInsId = actModel.getTask().getProcessInstanceId();
			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(procInsId).singleResult();
			String businessKey = processInstance.getBusinessKey();
			String businessTable = businessKey.split(":")[0];
			actModel.setProcInsId(procInsId);
			actModel.setBusinessTable(businessTable);
		}
		model.addAttribute("page", page);
		return "modules/act/actTaskTodoList";
	}
	
	/**
	 * 获取已办任务
	 * @param page
	 * @param procDefKey 流程定义标识
	 * @return
	 */
	@RequestMapping(value = "completeList")
	public String completeList(Act act, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Page<Act> page = new Page<Act>(request, response);
		page = actTaskService.completeList(page, act,UserUtils.getUser());
		model.addAttribute("page", page);
		if (UserUtils.getPrincipal().isMobileLogin()){
			return renderString(response, page);
		}
		return "modules/act/actTaskCompleteList";
	}
	
	/**
	 * 获取历史任务画面，
	 * 默认给出一周的查询范围
	 */
	@RequestMapping(value = "historic")
	public String historic(Act act, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Date endDate = new Date();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -7);
		Date beginDate = c.getTime();
		act.setBeginDate(beginDate);
		act.setEndDate(endDate);
		model.addAttribute("act", act);
		return "modules/act/actTaskHistoricList";
	}
	
	/**
	 * 获取历史任务列表
	 * @param page
	 * @param procDefKey 流程定义标识
	 * @return
	 */
	@RequestMapping(value = "historicList")
	public String historicList(Act act, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Page<Act> page = new Page<Act>(request, response);
		page = actTaskService.historicList(page, act,UserUtils.getUser());
		model.addAttribute("page", page);
		if (UserUtils.getPrincipal().isMobileLogin()){
			return renderString(response, page);
		}
		return "modules/act/actTaskHistoricList";
	}

	/**
	 * 获取流转历史列表
	 * @param procInsId 流程实例
	 * @param startAct 开始活动节点名称
	 * @param endAct 结束活动节点名称
	 */
	@RequestMapping(value = "histoicFlow")
	public String histoicFlow(Act act, String startAct, String endAct, Model model){
		if (StringUtils.isNotBlank(act.getProcInsId())){
			List<Act> histoicFlowList = actTaskService.histoicFlowList(act.getProcInsId(), startAct, endAct);
			model.addAttribute("histoicFlowList", histoicFlowList);
		}
		return "modules/act/actTaskHistoricFlow";
	}
	
	/**
	 * 获取流程列表
	 * @param category 流程分类
	 */
	@RequestMapping(value = "process")
	public String processList(String category, HttpServletRequest request, HttpServletResponse response, Model model) {
	    Page<Object[]> page = new Page<Object[]>(request, response);
	    page = actTaskService.processList(page, category);
		model.addAttribute("page", page);
		model.addAttribute("category", category);
		return "modules/act/actTaskProcessList";
	}
	
	/**
	 * 获取流程表单
	 * @param taskId	任务ID
	 * @param taskName	任务名称
	 * @param taskDefKey 任务环节标识
	 * @param procInsId 流程实例ID
	 * @param procDefId 流程定义ID
	 */
	@RequestMapping(value = "form")
	public String form(Act act, HttpServletRequest request, Model model){
		
		// 获取流程XML上的表单KEY
		String formKey = actTaskService.getFormKey(act.getProcDefId(), act.getTaskDefKey());

		// 获取流程实例对象
		if (act.getProcInsId() != null){
			act.setProcIns(actTaskService.getProcIns(act.getProcInsId()));
		}
		
		return "redirect:" + ActUtils.getFormUrl(formKey, act);
		
//		// 传递参数到视图
//		model.addAttribute("act", act);
//		model.addAttribute("formUrl", formUrl);
//		return "modules/act/actTaskForm";
	}
	
	/**
	 * 启动流程
	 * @param procDefKey 流程定义KEY
	 * @param businessTable 业务表表名
	 * @param businessId	业务表编号
	 */
	@RequestMapping(value = "start")
	@ResponseBody
	public String start(Act act, String table, String id, Model model) throws Exception {
		
		Map<String, Object> vars = Maps.newHashMap();
		
		// 根据登录用户，获取此用户的所在公司的权限列表。(从表sys_role_company)
		//String userId = UserUtils.getUser().getId();
		//List<CompanyRole> roleList = companyRoleDao.getCompanyRoleList(userId);
		// 存入工作流定义的变量中。
		//for(CompanyRole role:roleList){
			// 1.公司管理员权限。
		//	if(PjConstants.ROLE_COMPANY_ADMIN.equals(role.getRoleId())){
		//		vars.put("company_admin",role.getId());
		//	}
			// 2.公司普通员工权限。
		//	if(PjConstants.ROLE_COMPANY_USER.equals(role.getRoleId())){
		//		vars.put("company_user" ,role.getId());
		//	}
			// 3.该工作流中如果还有其他的权限,则写在后面。
		//}
		
		actTaskService.startProcess(act.getProcDefKey(), act.getBusinessTable(), act.getBusinessId(), act.getTitle(),vars);
		
		return "true"; //adminPath + "/act/task";
	}

	/**
	 * 签收任务
	 * @param taskId 任务ID
	 */
	@RequestMapping(value = "claim")
	@ResponseBody
	public String claim(Act act) {
		
		String userId = UserUtils.getUser().getId();
		userId = "u_" + userId;
		// 根据登录用户，获取此用户的权限ID(sys_company_role中的id字段)
		//List<String> roleList = companyRoleDao.getCompanyRoleListByUser(userId);
		// 目前，每一个公司的user,只分配一种权限。	
		//String roleId = "";
		//if(roleList == null || roleList.size() == 0){
		//	// 如果该公司没有分配权限。
		//	roleId = "";
		//}
		//else{
		//	// 目前，每一个公司的user,只分配一种权限。
		//	roleId = roleList.get(0);
		//}
		// 签收信息(签到自己的userid下)
		actTaskService.claim(act.getTaskId(), userId);
		return "true";//adminPath + "/act/task";
	}
	
	/**
	 * 办理任务
	 * @param act
	 * @return
	 */
	@RequestMapping(value = "handle")
	@ResponseBody
	public String handle(Act act,HttpServletRequest request, Model model) {
		JQResultModel resultModel = new JQResultModel();
		// 获取流程XML上的表单KEY
		String formKey = actTaskService.getFormKey(act.getProcDefId(), act.getTaskDefKey());
		// 获取流程实例对象
		if (act.getProcInsId() != null){
			act.setProcIns(actTaskService.getProcIns(act.getProcInsId()));
		}
		String url = ActUtils.getFormUrl(formKey, act);
		if(url.indexOf("/static") != -1){//为手机页面
			resultModel.setResult(-1);
			resultModel.setMsg("请登录手机端操作");
		}else{//电脑页面
			url = "/" + SysParameterUtils.findKeyword("systemName").getValue1() + url;
			resultModel.setResult(0);
			resultModel.setUserdata(url);
		}
		return JSON.toJSONString(resultModel);
	}
	
	
	/**
	 * 撤回任务
	 * @param act
	 * @return
	 * @throws IOException 
	 * @throws Exception 
	 */
	@RequestMapping(value="recall")
	public String recall(Act act,RedirectAttributes redirectAttributes) throws IOException{
		if (StringUtils.isBlank(act.getComment())){
			addMessage(redirectAttributes, "请填写撤回原因");
		}else{
			act.setComment(URLDecoder.decode(act.getComment(),"UTF-8"));
			JQResultModel resultModel = actTaskService.recallTask(act, UserUtils.getUser().getId());
			if(resultModel.getResult() == 1){
				addMessage(redirectAttributes, "任务撤回成功！");
			}else{
				addMessage(redirectAttributes, "撤回失败" + resultModel.getMsg());
			}
		}
		return "redirect:" + adminPath + "/act/task";
	}
	
	/**
	 * 委托任务
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="appoint")
	@ResponseBody
	public String appoint(HttpServletRequest request,HttpServletResponse response){
		JQResultModel resultModel = new JQResultModel();
		String toUserId = request.getParameter("toUserId"); //被委托者
		if(StringUtils.isBlank(toUserId)){
			resultModel.setResult(-1);
			resultModel.setMsg("请选择任务被委托者后再进行提交！");
			return JSON.toJSONString(resultModel);
		}
		String taskData = request.getParameter("taskData");
		if(StringUtils.isBlank(taskData)){
			resultModel.setResult(-1);
			resultModel.setMsg("请选择任务后再进行提交！");
			return JSON.toJSONString(resultModel);
		}
		User fromUser = UserUtils.getUser(); //委托者
		String[] taskDataArray = taskData.split(",");
		int flag = actTaskService.pcAppointTask(taskDataArray, toUserId, fromUser.getId());
		if(flag == 0){
			resultModel.setResult(0);
			resultModel.setMsg("委托成功");
		}else{
			resultModel.setResult(-1);
			resultModel.setMsg("委托失败，请稍后重试！");
		}
		return JSON.toJSONString(resultModel);
	}
	
	/**
	 * 完成任务
	 * @param taskId 任务ID
	 * @param procInsId 流程实例ID，如果为空，则不保存任务提交意见
	 * @param comment 任务提交意见的内容
	 * @param vars 任务流程变量，如下
	 * 		vars.keys=flag,pass
	 * 		vars.values=1,true
	 * 		vars.types=S,B  @see com.thinkgem.jeesite.modules.act.utils.PropertyType
	 */
	@RequestMapping(value = "complete")
	@ResponseBody
	public String complete(Act act) {
		actTaskService.complete(act.getTaskId(), act.getProcInsId(), act.getComment(), act.getVars().getVariableMap());
		return "true";//adminPath + "/act/task";
	}
	
	/**
	 * 读取带跟踪的图片
	 */
	@RequestMapping(value = "trace/photo/{procDefId}/{execId}")
	public void tracePhoto(@PathVariable("procDefId") String procDefId, @PathVariable("execId") String execId, HttpServletResponse response) throws Exception {
		InputStream imageStream = actTaskService.tracePhoto(procDefId, execId);
		
		// 输出资源内容到相应对象
		byte[] b = new byte[1024];
		int len;
		while ((len = imageStream.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
	}
	
	/**
	 * 输出跟踪流程信息
	 * 
	 * @param processInstanceId
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "trace/info/{proInsId}")
	public List<Map<String, Object>> traceInfo(@PathVariable("proInsId") String proInsId) throws Exception {
		List<Map<String, Object>> activityInfos = actTaskService.traceProcess(proInsId);
		return activityInfos;
	}

	/**
	 * 显示流程图
	 
	@RequestMapping(value = "processPic")
	public void processPic(String procDefId, HttpServletResponse response) throws Exception {
		ProcessDefinition procDef = repositoryService.createProcessDefinitionQuery().processDefinitionId(procDefId).singleResult();
		String diagramResourceName = procDef.getDiagramResourceName();
		InputStream imageStream = repositoryService.getResourceAsStream(procDef.getDeploymentId(), diagramResourceName);
		byte[] b = new byte[1024];
		int len = -1;
		while ((len = imageStream.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
	}*/
	
	/**
	 * 获取跟踪信息
	 
	@RequestMapping(value = "processMap")
	public String processMap(String procDefId, String proInstId, Model model)
			throws Exception {
		List<ActivityImpl> actImpls = new ArrayList<ActivityImpl>();
		ProcessDefinition processDefinition = repositoryService
				.createProcessDefinitionQuery().processDefinitionId(procDefId)
				.singleResult();
		ProcessDefinitionImpl pdImpl = (ProcessDefinitionImpl) processDefinition;
		String processDefinitionId = pdImpl.getId();// 流程标识
		ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(processDefinitionId);
		List<ActivityImpl> activitiList = def.getActivities();// 获得当前任务的所有节点
		List<String> activeActivityIds = runtimeService.getActiveActivityIds(proInstId);
		for (String activeId : activeActivityIds) {
			for (ActivityImpl activityImpl : activitiList) {
				String id = activityImpl.getId();
				if (activityImpl.isScope()) {
					if (activityImpl.getActivities().size() > 1) {
						List<ActivityImpl> subAcList = activityImpl
								.getActivities();
						for (ActivityImpl subActImpl : subAcList) {
							String subid = subActImpl.getId();
							System.out.println("subImpl:" + subid);
							if (activeId.equals(subid)) {// 获得执行到那个节点
								actImpls.add(subActImpl);
								break;
							}
						}
					}
				}
				if (activeId.equals(id)) {// 获得执行到那个节点
					actImpls.add(activityImpl);
					System.out.println(id);
				}
			}
		}
		model.addAttribute("procDefId", procDefId);
		model.addAttribute("proInstId", proInstId);
		model.addAttribute("actImpls", actImpls);
		return "modules/act/actTaskMap";
	}*/
	
	/**
	 * 删除任务
	 * @param taskId 流程实例ID
	 * @param reason 删除原因
	 */
	@RequiresPermissions("act:process:edit")
	@RequestMapping(value = "deleteTask")
	public String deleteTask(String taskId, String reason, RedirectAttributes redirectAttributes) {
		if (StringUtils.isBlank(reason)){
			addMessage(redirectAttributes, "请填写删除原因");
		}else{
			actTaskService.deleteTask(taskId, reason);
			addMessage(redirectAttributes, "删除任务成功，任务ID=" + taskId);
		}
		return "redirect:" + adminPath + "/act/task";
	}
	
	/**
	 * 删除流程实例
	 * @param procInsId 流程实例ID
	 * @param reason 删除原因
	 */
	@RequestMapping(value = "deleteProcIns")
	public String deleteProcIns(String procInsId, String reason, RedirectAttributes redirectAttributes) {
		if (StringUtils.isBlank(reason)){
			addMessage(redirectAttributes, "请填写删除原因");
		}else{
			int flag = actProcessService.deleteProcIns(procInsId, reason);
			if(flag == 0){
				addMessage(redirectAttributes, "删除流程实例成功，实例ID=" + procInsId);
			}else{
				addMessage(redirectAttributes, "删除流程实例失败");
			}
		}
		return "redirect:" + adminPath + "/act/task";
	}

}
