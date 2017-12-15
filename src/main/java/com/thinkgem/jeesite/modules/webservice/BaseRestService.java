package com.thinkgem.jeesite.modules.webservice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
/**
 * 统一入口
 * @author yuyabiao
 * @since 2016/12/03
 * 访问地址 hihunan/webservice/rest/baseRest/...
 */

@Path(value="/baseRest")
public interface BaseRestService {
	
	/**
	 * 统一入口
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="baseFind")
	public String baseFind(@Context HttpServletRequest request,@Context HttpServletResponse response);

}
