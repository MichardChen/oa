package com.thinkgem.jeesite.modules.webservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.http.HttpStatus;

import com.alibaba.fastjson.JSON;
import com.fastweixin.api.OauthAPI;
import com.fastweixin.api.config.ApiConfig;
import com.fastweixin.api.response.OauthGetTokenResponse;
import com.fastweixin.model.WXSignModel;
import com.fastweixin.util.NetWorkCenter;
import com.fastweixin.util.NetWorkCenter.ResponseCallback;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.JsSignatureUtils;
import com.thinkgem.jeesite.common.utils.MessageUtil;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.Menu;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.PermissionService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.task.RefreshAccessTokenService;
import com.thinkgem.jeesite.pj_common.Constants.PjConstants;

/** * * * * * * * * * * * * * * * * * * * * * * * * *
 * 接口实现类										*
 *													*
 * @author 									*
 * @since 2015-08-12								*
 * 注意：这是REST风格的webservice.访问地址 ConstructCost/webservice/rest/test/helloWorld
 * * * * * * * * * * * * * * * * * * * * * * * * * **/
@Path(value="/login")
public class LoginRestServiceImpl implements LoginRestService {
	
	private SystemService systemService;
	
	private RefreshAccessTokenService refreshAccessTokenService;
	/** 从微信端、APP的登录验证
	 *  逻辑
	 *  1.当 servletRequest中包含code参数时，认为是从微信登录(微信回调的地址)
	 *     1.1 根据code获取到openid,并且根据openid从用户表获取到用户信息，则自动登录。
	 *     1.2 否则返回错误信息，提示用户登录。同时把openid放入session中。
	 *  2.当servletRequest中不包含code参数时,认为是输入用户名和密码登录。
	 *    用户名和密码验证成功后，判断session中是否还有openid。是的话，绑定和自动登录。
	 * @since 2016-1-15
	 * @return JSON
	//{"error":0,//0为成功，1为失败（出错）
	//	"errorCode":"4023",//情况编码，包括成功（4023），等等
	//              4023成功/4018出现异常/1001用户密码失败/1004微信未绑定用户
	//	"errCode":"4023",//同上，因某些情况暂时冗余
	//	"message":"登陆成功！",//操作结果的消息文本
	//	"url":"http://www.baidu.com" //跳转url，如果有该属性则会优先跳转该url，起到类似response的跳转效果
	//  "currProjectId":"1"//登陆者当前项目ID 
	//} */
	@SuppressWarnings("unchecked")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="Login")
	public String login(@Context final HttpServletRequest servletRequest,@Context HttpServletResponse response)
	{	
		String codeWeixin;
		String openId;
		final Map rtnMap = new HashMap(); //返回用
		
		codeWeixin = servletRequest.getParameter("code");
		System.out.println("code".concat(codeWeixin));
		// 通过授权页面登录
		if(StringUtils.isNotBlank(codeWeixin)){
			ApiConfig apiConfig = ApiConfig.getInstance();
			OauthAPI jsApi= new OauthAPI(apiConfig);
            // 根据code获取 openid
			OauthGetTokenResponse OauthResponse = jsApi.getToken(codeWeixin);
		    
			if(!"0".equals(OauthResponse.getErrcode())){
				rtnMap.put("error","1");
				rtnMap.put("errorCode","4018");
				rtnMap.put("errCode"  ,"4018");
				rtnMap.put("message","登陆异常!");
				rtnMap.put("url","http://www.holdbuild.com:8090/ConstructCost/static/test/test/login.html"); //跳回登录页面
				return JSON.toJSONString(rtnMap); // 返回错误
			}
			
			openId = OauthResponse.getOpenid();
			System.out.println("openId:".concat(openId));
			
			//通过OPENID来查询对应用户是否存在。
			final User user = UserUtils.getByOpenId(codeWeixin);
			if(user == null){
				rtnMap.put("error","1");
				rtnMap.put("errorCode","1001");
				rtnMap.put("errCode"  ,"1001");
				rtnMap.put("message","用户密码失败!");
				rtnMap.put("url","http://www.holdbuild.com:8090/ConstructCost/static/test/test/login.html"); //跳回登录页面
				servletRequest.getSession().setAttribute("openId", openId);
				return JSON.toJSONString(rtnMap);
			}
			// 自动登录
			String urlLogin = "http://www.holdbuild.com:8090/ConstructCost/a/login";
			
			StringBuilder paramData = new StringBuilder();
			paramData.append("{username:''" ); //微信登录时，没有用户
		    paramData.append(",password:''" ); //微信登录时，没有密码
		    paramData.append(",openId:"   + openId);
		    paramData.append(",validateCode:''");
		    paramData.append(",mobileLogin:true}");
		    
		    
			NetWorkCenter.post(urlLogin, paramData.toString(), new ResponseCallback(){
				@Override
				public void onResponse(int resultCode, String resultJson) {
					if(resultCode != HttpStatus.SC_OK){
						// 登录失败。
						rtnMap.put("error","1");
						rtnMap.put("errorCode","1001");
						rtnMap.put("errCode"  ,"1001");
						rtnMap.put("message","用户密码失败!");
						rtnMap.put("url","http://www.holdbuild.com:8090/ConstructCost/static/test/test/login.html"); //跳回登录页面
						return;
					}
					// 登录成功
					rtnMap.put("error","0");
					rtnMap.put("errorCode","4023");
					rtnMap.put("errCode"  ,"4023");
					rtnMap.put("message","登录成功!");
					rtnMap.put("url",PermissionService.GetRedirectUri(servletRequest)); //
					rtnMap.put("currProjectId",user.getCurrProjectId());
					return;
					
				}
			  }
			);
			return JSON.toJSONString(rtnMap);
			//data && data.sessionid
		}	
		else{
			String userid;
			String passord;
			String bindSign = "1";  // 自动绑定 
			userid = servletRequest.getParameter("account");
			passord= servletRequest.getParameter("password");
			
		}	
		//User user = UserUtils.getUser();
		//String userID = user.getId();
		String companyId = (String)servletRequest.getSession().getAttribute(PjConstants.CUR_COMPANY_ID);
		
		StringBuffer url = servletRequest.getRequestURL();
			
		System.out.println("url Login".concat(url.toString()));
			
		String ProjectID = servletRequest.getParameter("projectId");
		String RptCycleID = servletRequest.getParameter("rptCycleId");
		String RptID = servletRequest.getParameter("rptID");
		Object attr = servletRequest.getParameterNames();

		return JSON.toJSONString(rtnMap);
	}
	
	/** 获取微信用JSSDK。
	 * @since 2016-1-15
	 * @return JSON
	 "wxconfig":{
      "appId":"abccdef",//微信应用id
      "timestamp":123483496593,//长整形时间戳
      "nonceStr":"aaaaa",//临时产生的随机码
      "singnature":"aaaaaa"//计算后的授权码
     },
     "wxtype":"1"//当前微信服务类型，因为公众号和企业号有些前台请求地址不同，因此需要这个进行区分
	*/
	@SuppressWarnings("unchecked")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="GetJSSDK")
    public String GetJSSDK(@Context HttpServletRequest servletRequest,@Context HttpServletResponse response){
		
		Map rtnMap = new HashMap();
		rtnMap.put("wxtype","1"); // "1":服务号 
		Map rtnMapSub = new HashMap();
		// 通过http Header中的REFERER获取请求者所在页面。
		String url = servletRequest.getHeader("REFERER");
		
		WXSignModel wxSignModel= new WXSignModel();
		JsSignatureUtils jsSignatureUtils = JsSignatureUtils.getInstance();
		
		try{
		   // 先从微信公众号上获取签名信息
		   wxSignModel = jsSignatureUtils.GetJsSignature(url.toString());
		   if(wxSignModel==null){
			   // 取不到，则从微信企业号的配置上取签名信息。
			   wxSignModel = jsSignatureUtils.GetQywxJsSignature(url.toString());
		   }
		   // 如果能获取到签名信息，则返回。
		   if(wxSignModel!=null){
			   rtnMapSub.put("appId",      wxSignModel.getAppid()    );
			   rtnMapSub.put("timestamp",  wxSignModel.getTimestamp());
			   rtnMapSub.put("nonceStr",   wxSignModel.getNonceStr() );
			   rtnMapSub.put("signature",  wxSignModel.getSignature());
			   rtnMap.put("wxconfig",rtnMapSub); 
		   }
		   
		}
		catch(Exception ex){
		   rtnMap.put("wxconfig",rtnMapSub); 
		}
		
		return JSON.toJSONString(rtnMap);
	}
	
	/**当微信页面签名失败时,重新刷新AccessToken。
	*/
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="RefreshAccessToken")
    public String RefreshAccessToken(@Context HttpServletRequest servletRequest,@Context HttpServletResponse response){
		getRefreshAccessTokenService().refreshAccessToken();
		return "1";
	}
	
	/** 获取登录的用户信息
	 * @since 2016-1-15
	 * @return JSON
	 {
       "UserName":"吴云帆",//用户姓名
       "UserCode":"wyf"//用户登陆账号
      }
    */
	@SuppressWarnings("unchecked")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="GetUserInfo")
    public String GetUserInfo(@Context HttpServletRequest servletRequest,@Context HttpServletResponse response){
		Map rtnMap = new HashMap();
		
		User user = UserUtils.getUser();
		rtnMap.put("UserName",user.getName());
		// 微信绑定的情况
		rtnMap.put("weixinBind",StringUtils.isBlank(user.getWechatOpenId())?"0":"1");
		rtnMap.put("UserCode",user.getMobile());
		rtnMap.put("UserId", user.getId());
		// 获取菜单信息(注意这里包含PC和移动端的菜单)。只含有1级和对应的2级菜单，没有三级菜单。
		List<Menu> menuList = UserUtils.getMenuList();
		// 把PC端的菜单过滤掉
		List<Map> menuListRtn = new ArrayList<Map>();
		for(int i=0;i< menuList.size();i++){
			if("mb".equals(menuList.get(i).getMedia())){
			   Map menu = new HashMap();
			   menu.put("href",menuList.get(i).getHref());
			   menu.put("icon",menuList.get(i).getIcon());
			   menu.put("name",menuList.get(i).getName());
			   menuListRtn.add(menu);
			}
		}
		rtnMap.put("menuList", menuListRtn);
		
		return JSON.toJSONString(rtnMap);
	}
	
	/** 解除微信绑定
	 * @since 2016-1-15
	 * @return JSON
	 {
     
      }
    */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="UnBindWeixin")
    public String UnBindWeixin(@Context HttpServletRequest servletRequest,@Context HttpServletResponse response){
		Map rtnMap = new HashMap();
		
		User user = UserUtils.getUser();
        String openId = ""; // OPENID设成空
        try{
		    getSystemService().updateUserOpenId(user.getId(), openId);
        }
        catch(Exception ex){
        	rtnMap.put("error", 1);
     		rtnMap.put("errCode","5007");
     		rtnMap.put("message",MessageUtil.getMessage("5007"));
        	return JSON.toJSONString(rtnMap);
        }
        // 解绑成功
		UserUtils.clearCache(user);
        rtnMap.put("error", 0);
		rtnMap.put("errCode","");
		rtnMap.put("message","");
		
		return JSON.toJSONString(rtnMap);
	}
	
	/** 获取手机菜单.
    */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="getMobileMenu")
    public String getMobileMenu(@Context HttpServletRequest servletRequest,@Context HttpServletResponse response){
		String token = (String)servletRequest.getParameter("token");
		if(token == null || !"3434KSSIIS78JSSSSWEEEDDdddddddf23".equals(token)){
			Map rtnMap = new HashMap();
			rtnMap.put("result", "-1");
			rtnMap.put("msg", "token不正确");
			return JSON.toJSONString(rtnMap);
		}
		return JSON.toJSONString(UserUtils.getAllMobileMenu());
	}
	/**
	 * 获取系统业务对象
	 */
	public SystemService getSystemService() {
		if (systemService == null){
			systemService = SpringContextHolder.getBean(SystemService.class);
		}
		return systemService;
	}
	/**获取RefreshAccessTokenService
	 */
	public RefreshAccessTokenService getRefreshAccessTokenService(){
		if (refreshAccessTokenService == null){
			refreshAccessTokenService = SpringContextHolder.getBean(RefreshAccessTokenService.class);
		}
		return refreshAccessTokenService;
	}
}
