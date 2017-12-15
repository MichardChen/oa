package com.thinkgem.jeesite.modules.webservice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
/**
 * 发现webservice
 * @author lin
 * @since 2016/11/28
 * 访问地址 hihunan/webservice/rest/discoverRest/...
 */

@Path(value="/discoverRest")
public interface DiscoverRestService {
	
	/**
	 * 首页 发现
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="findDiscoverList")
	public String findDiscoverList(@Context HttpServletRequest request,@Context HttpServletResponse response);
	
}
