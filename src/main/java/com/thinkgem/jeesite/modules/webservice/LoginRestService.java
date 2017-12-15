package com.thinkgem.jeesite.modules.webservice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
* 注意：这是REST风格的webservice.访问地址 ConstructCost/webservice/rest/cost/....
* token:为防止用户恶意访问。
*/
@Path(value="/login")
public interface LoginRestService {
	
	/** 从微信端、APP的登录验证
	 * @since 2016-1-15
	 * @return JSON
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="Login")
    public String login(@Context HttpServletRequest servletRequest,@Context HttpServletResponse response);
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="GetJSSDK")
    public String GetJSSDK(@Context HttpServletRequest servletRequest,@Context HttpServletResponse response);
	
	/**当微信页面签名失败时,重新刷新AccessToken。
	*/
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="RefreshAccessToken")
    public String RefreshAccessToken(@Context HttpServletRequest servletRequest,@Context HttpServletResponse response);
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="GetUserInfo")
    public String GetUserInfo(@Context HttpServletRequest servletRequest,@Context HttpServletResponse response);
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="UnBindWeixin")
    public String UnBindWeixin(@Context HttpServletRequest servletRequest,@Context HttpServletResponse response);
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="getMobileMenu")
    public String getMobileMenu(@Context HttpServletRequest servletRequest,@Context HttpServletResponse response);
	
}
