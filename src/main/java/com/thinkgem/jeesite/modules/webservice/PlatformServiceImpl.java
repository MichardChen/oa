package com.thinkgem.jeesite.modules.webservice;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.thinkgem.jeesite.common.utils.ObjectUtils;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.model.UserModel;
import com.thinkgem.jeesite.modules.sys.service.PermissionService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;


/** * * * * * * * * * * * * * * * * * * * * * * * * *
 * 接口实现类										*
 *													*
 * @author 									*
 * @since 2015-08-12								*
 * 注意：这是REST风格的webservice.访问地址 ConstructCost/webservice/rest/test/helloWorld
 * * * * * * * * * * * * * * * * * * * * * * * * * **/
@Path(value="/platform")
public class PlatformServiceImpl implements PlatformService {	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="GetToken")
	@Override
	public String GetToken(@FormParam("code") String code,@Context HttpServletRequest servletRequest,@Context HttpServletResponse response){
		Map rtnMap = new HashMap();
		String Token=PermissionService.GetTokenKey(code);
		if(StringUtils.isNotBlank(Token)){
			if(PermissionService.Login(Token)){
				rtnMap.put("Token", Token);
				rtnMap.put("UserName",UserUtils.getUser().getName());
				UserModel user=new UserModel();
				User curUser=UserUtils.getUser();
				ObjectUtils.copyModel(curUser, user);
				user.setOfficeId(curUser.getOffice().getId());
				rtnMap.put("UserInfo", user);
			}
		}
		return JSON.toJSONString(rtnMap);		
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="FlushToken")
	@Override
	public String FlushToken(@FormParam("TokenKey") String TokenKey,@Context HttpServletRequest servletRequest,@Context HttpServletResponse response){
		Map rtnMap = new HashMap();
		boolean IsFlush=false;
		if(PermissionService.Login(TokenKey)){		
			UserUtils.getByToken(TokenKey);
			System.out.println("abc");
			System.out.println(UserUtils.getUser().getId());
			System.out.println(UserUtils.getByToken(TokenKey).getId());
			if(StringUtils.isNotBlank(TokenKey)){
				IsFlush=PermissionService.FlushToken(TokenKey);
			}
		}
		rtnMap.put("success", IsFlush);
		return JSON.toJSONString(rtnMap);		
	}
}
