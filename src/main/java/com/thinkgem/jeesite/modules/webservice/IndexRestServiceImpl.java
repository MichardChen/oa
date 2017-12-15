package com.thinkgem.jeesite.modules.webservice;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.thinkgem.jeesite.common.persistence.JQResultModel;
import com.thinkgem.jeesite.modules.hihunan.entity.h5app.HiH5app;
import com.thinkgem.jeesite.modules.hihunan.entity.headershow.HiHeaderShow;
import com.thinkgem.jeesite.modules.hihunan.model.h5app.HiH5appModel;
import com.thinkgem.jeesite.modules.hihunan.model.herdershow.HiHeaderShowModel;
import com.thinkgem.jeesite.modules.hihunan.service.h5app.HiH5appService;
import com.thinkgem.jeesite.modules.hihunan.service.headershow.HiHeaderShowService;
import com.thinkgem.jeesite.modules.hihunan.service.listshow.HiListShowService;

/**
 * 旅游webservice
 * @author yuyabiao
 * @since 2016/11/26
 * 访问地址hihunan/webservice/rest/indexRest/...
 */
@Path(value="/indexRest")
public class IndexRestServiceImpl implements IndexRestService{
	
	@Autowired
	private HiHeaderShowService hiHeaderShowService;
	
	@Autowired
	private HiH5appService hiH5appService;
	
	@Autowired
	private HiListShowService hiListShowService;
	/**
	 * 查询轮播列表
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="findBanner")
	public String findBanner(@Context HttpServletRequest request,@Context HttpServletResponse response){
		JQResultModel resultModel = new JQResultModel();
/*		String ip = GetIpAddrUtils.getIpAddress(request);
		Date time = (Date)request.getSession().getAttribute(ip + "/" + Thread.currentThread() .getStackTrace()[1].getMethodName());
		if(time == null){
			request.getSession().setAttribute(ip + "/" + Thread.currentThread() .getStackTrace()[1].getMethodName(), new Date());
		}else{
			Date date = new Date();
			long shijian = (date.getTime() - time.getTime())/1000;
			if(shijian < 0.1){//访问间隔小于0.1秒将拦截
				resultModel.setResult(-1);
				return JSON.toJSONString(resultModel);
			}else{
				request.getSession().setAttribute(ip + "/" + Thread.currentThread() .getStackTrace()[1].getMethodName(), date);
			}
		}*/
		HiHeaderShow hiHeaderShow = new HiHeaderShow();
		String isEffect = "1";// 这里是要查询生效的全部轮播信息
		String listType = request.getParameter("listType");
		hiHeaderShow.setIsEffect(isEffect);
		hiHeaderShow.setListType(listType);
		List<HiHeaderShowModel> list = hiHeaderShowService
				.findAll(hiHeaderShow);
		resultModel.setRows(list);
		return JSON.toJSONString(resultModel);
	}
	
	
	/**
	 * 查询首页应用栏目列表
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="findHeaderItem")
	public String findHeaderItem(@Context HttpServletRequest request,@Context HttpServletResponse response){
		JQResultModel resultModel = new JQResultModel();
		HiH5app hiH5app = new HiH5app();
		String type1 = "top";// 查询首页的应用类型
		hiH5app.setIsEffect("1");
		hiH5app.setType1(type1);
		List<HiH5appModel> list = hiH5appService.findHeaderItem(hiH5app);
		resultModel.setRows(list);
		return JSON.toJSONString(resultModel);
	}
	
	/**
	 * 查询首页功能列表,除本地生活列表以外
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="findHeaderList")
	public String findHeaderList(@Context HttpServletRequest request,@Context HttpServletResponse response){
		JQResultModel resultModel = new JQResultModel();
		HiH5app hiH5app = new HiH5app();
		hiH5app.setType1("top");
		hiH5app.setType2("list");
		hiH5app.setIsEffect("1");
		List<HiH5appModel> list = hiH5appService.findListType(hiH5app);
		resultModel.setRows(list);
		return JSON.toJSONString(resultModel);
	}
}
