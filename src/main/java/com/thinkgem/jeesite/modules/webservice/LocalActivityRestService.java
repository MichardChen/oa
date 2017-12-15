package com.thinkgem.jeesite.modules.webservice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
/**
 * 本地生活webservice
 * @author zfg
 * @since 2016/12/1
 * 访问地址 hihunan/webservice/rest/LocalActivityRest/...
 */

@Path(value="/localActivityRest")
public interface LocalActivityRestService {
	
	/**
	 * 查询列表
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="findLocalList")
	public String findLocalList(@Context HttpServletRequest request,@Context HttpServletResponse response);
	
	/**
	 * 查询详情
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="findLocalDetail")
	public String findLocalDetail(@Context HttpServletRequest request,@Context HttpServletResponse response);
	
	/**
	 * 活动报名
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="joinActivity")
	public String joinActivity(@Context HttpServletRequest request,@Context HttpServletResponse response);
	
	/**
	 * 收藏
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="toCollect")
	public String toCollect(@Context HttpServletRequest request,@Context HttpServletResponse response);
	
	/**
	 * 取消收藏
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="deleteCollect")
	public String deleteCollect(@Context HttpServletRequest request,@Context HttpServletResponse response);
	
	/**
	 * 获取本地活动是否收藏
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="findLocalIsCollect")
	public String findLocalIsCollect(@Context HttpServletRequest request,@Context HttpServletResponse response);
	
	/**
	 * 本地生活列表
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="findList")
	public String findList(@Context HttpServletRequest request,@Context HttpServletResponse response);

}
