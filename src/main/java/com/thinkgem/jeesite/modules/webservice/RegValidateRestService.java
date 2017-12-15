package com.thinkgem.jeesite.modules.webservice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
* 注意：这是REST风格的webservice.访问地址 ConstructCost/webservice/rest/regValidate/checkXXXX
* token:为防止用户恶意访问。
*/
@Path(value="/regValidate")
public interface RegValidateRestService {
	// 检查公司名重复
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="checkMemberName")
	public String checkMemberExists(@Context HttpServletRequest servletRequest);
	
	//检查登录名是否重复
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path(value="checkLoginName")
	public String checkLoginName(@Context HttpServletRequest servletRequest);
	
	//检查手机号码是否重复
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path(value="checkMobile")
	public String checkMobile(@Context HttpServletRequest servletRequest);
	
	//检查QQ号码是否重复
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path(value="checkQQ")
	public String checkQQ(@Context HttpServletRequest servletRequest);

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Path(value="sendMsg")
	public String sendValidateCode(@Context HttpServletRequest request,@Context HttpServletResponse response);
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Path(value="checkMobileCode")
	public String checkMobileCode(@Context HttpServletRequest request,@Context HttpServletResponse response);
	
	//注册新账户
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Path(value="save")
	public String save(@Context HttpServletRequest request,
			@Context HttpServletResponse response);
	
}
