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
import com.thinkgem.jeesite.modules.hihunan.entity.listshow.HiListShow;
import com.thinkgem.jeesite.modules.hihunan.model.listshow.HiListShowModel;
import com.thinkgem.jeesite.modules.hihunan.service.artical.HiArticalService;
import com.thinkgem.jeesite.modules.hihunan.service.listshow.HiListShowService;

/**
 * 旅游webservice
 * @author yuyabiao
 * @since 2016/11/26
 * 访问地址hihunan/webservice/rest/listShowRest/...
 */
@Path(value="/listShowRest")
public class ListShowRestServiceImpl implements ListShowRestService{
	
	@Autowired
	private HiListShowService hiListShowService;
	
	@Autowired
	private HiArticalService hiArticalService;
	
	/**
	 * 查询列表
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="findListShow")
	public String findListShow(@Context HttpServletRequest request,@Context HttpServletResponse response){
		JQResultModel resultModel = new JQResultModel();
		String type = request.getParameter("type");
		HiListShow hiTravelList = new HiListShow();
		hiTravelList.setType(type);
		List<HiListShowModel> list = hiListShowService
				.findAll(hiTravelList);
		resultModel.setRows(list);
		return JSON.toJSONString(resultModel);
	}
	
	
	
}
