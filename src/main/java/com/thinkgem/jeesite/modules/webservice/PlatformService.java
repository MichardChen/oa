package com.thinkgem.jeesite.modules.webservice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
* 注意：这是REST风格的webservice.访问地址 ConstructCost/webservice/rest/cost/....
* token:为防止用户恶意访问。
*/
@Path(value="/platform")
public interface PlatformService {
	
	//获取下拉列表值，如项目ID，期间列表等 GetPopupList
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="GetToken")
	public String GetToken(@FormParam("code") String code,@Context HttpServletRequest servletRequest,@Context HttpServletResponse response );
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="FlushToken")
	public String FlushToken(@FormParam("TokenKey") String TokenKey,@Context HttpServletRequest servletRequest,@Context HttpServletResponse response);
}
