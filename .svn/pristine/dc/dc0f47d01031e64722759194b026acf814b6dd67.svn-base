package com.thinkgem.jeesite.modules.webservice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * 首页webservice
 * @author yuyabiao
 * @since 2016/11/28
 * 访问地址 hihunan/webservice/rest/indexRest/...
 */

@Path(value="/indexRest")
public interface IndexRestService {	
	/**
	 * 查询轮播列表
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="findBanner")
	public String findBanner(@Context HttpServletRequest request,@Context HttpServletResponse response);
	
	/**
	 * 查询首页应用栏目列表
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="findHeaderItem")
	public String findHeaderItem(@Context HttpServletRequest request,@Context HttpServletResponse response);
	
	
	/**
	 * 查询首页功能列表,除本地生活列表以外
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="findHeaderList")
	public String findHeaderList(@Context HttpServletRequest request,@Context HttpServletResponse response);

}
