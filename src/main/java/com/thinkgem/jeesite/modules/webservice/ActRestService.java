package com.thinkgem.jeesite.modules.webservice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
/**
 * 工作流Act
 * @author yuyabiao
 * @since 2016/6/8
 * 访问地址 atminspect/webservice/rest/actRest/...
 */

@Path(value="/actRest")
public interface ActRestService {
	
	/**
	 * 初始化查询待办
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="findToDoList")
	public String findToDoList(@Context HttpServletRequest request,@Context HttpServletResponse response);
	
	/**
	 * 查询已办
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="completeList")
	public String completeList(@Context HttpServletRequest request,@Context HttpServletResponse response);
	
	/**
	 * 查询历史已办
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="historyList")
	public String historyList(@Context HttpServletRequest request,@Context HttpServletResponse response);
	
	/**
	 * 签收办理任务
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="claimAndDo")
	public String claimAndDo(@Context HttpServletRequest request,@Context HttpServletResponse response)throws Exception;
	
	/**
	 * task信息
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="taskInfo")
	public String taskInfo(@Context HttpServletRequest request,@Context HttpServletResponse response);
	
	/**
	 * 获取可被委托人员
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="appointUser")
	public String appointUser(@Context HttpServletRequest request,@Context HttpServletResponse response);
	
	/**
	 * 委托提交
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="sendAppoint")
	public String sendAppoint(@Context HttpServletRequest request,@Context HttpServletResponse response);
	
	/**
	 * 删除流程
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="deleteProcIns")
	public String deleteProcIns(@Context HttpServletRequest request,@Context HttpServletResponse response);
	
	
	/**
	 * 回退流程
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="recall")
	public String recall(@Context HttpServletRequest request,@Context HttpServletResponse response);
	
	/**
	 * 查询待办条数
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="findToDoCount")
	public String findToDoCount(@Context HttpServletRequest request,@Context HttpServletResponse response);
	
	
}
