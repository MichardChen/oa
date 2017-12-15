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
 * 访问地址 hihunan/webservice/rest/restaunantRest/...
 */

@Path(value="/restaunantRest")
public interface RestaunantRestService {
	
	
	/**
	 * 动态生成美食餐厅的搜索条件
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="findSelectCondition")
	public String findSelectCondition(@Context HttpServletRequest request,@Context HttpServletResponse response);
	
	/**
	 * 推荐菜馆
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="findRecommondRestaunant")
	public String findRecommondRestaunant(@Context HttpServletRequest request,@Context HttpServletResponse response);
	
	/**
	 * 查询餐厅列表
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="findRestaunantList")
	public String findRestaunantList(@Context HttpServletRequest request,@Context HttpServletResponse response);
	
	/**
	 * 查询餐厅详情
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="findRestaunant")
	public String findRestaunant(@Context HttpServletRequest request,@Context HttpServletResponse response);
	
	/**
	 * 查询美食资讯
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="findFoodInformation")
	public String findFoodInformation(@Context HttpServletRequest request,@Context HttpServletResponse response);
	
	/**
	 * 查询美食资讯明细
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="findFoodInformationDetail")
	public String findFoodInformationDetail(@Context HttpServletRequest request,@Context HttpServletResponse response);
	
	
	/**
	 * 查询美食关联文章
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="findListShowDetail")
	public String findListShowDetail(@Context HttpServletRequest request,@Context HttpServletResponse response);
	
	
	
	
	
	
	
}
