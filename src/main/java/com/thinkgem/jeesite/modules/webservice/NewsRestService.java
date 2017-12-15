package com.thinkgem.jeesite.modules.webservice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
/**
 * 资讯webservice
 * @author lin
 * @since 2016/11/28
 * 访问地址 hihunan/webservice/rest/newsRest/...
 */

@Path(value="/newsRest")
public interface NewsRestService {
	
	/**
	 *  查询新闻，教育
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="findNewsAndEducate")
	public String findNewsAndEducate(@Context HttpServletRequest request,@Context HttpServletResponse response);
	
	/**
	 *  查询新闻，教育详情
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="findNewsAndEducateDetail")
	public String findNewsAndEducateDetail(@Context HttpServletRequest request,@Context HttpServletResponse response);
}
