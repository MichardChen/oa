package com.thinkgem.jeesite.modules.webservice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
/**
 * 旅游webservice
 * @author yuyabiao
 * @since 2016/11/26
 * 访问地址 hihunan/webservice/rest/listShowRest/...
 */

@Path(value="/listShowRest")
public interface ListShowRestService {
	
	/**
	 * 查询列表
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="findListShow")
	public String findListShow(@Context HttpServletRequest request,@Context HttpServletResponse response);
	
	
	
}
