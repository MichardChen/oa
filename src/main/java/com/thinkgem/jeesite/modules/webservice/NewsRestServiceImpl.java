package com.thinkgem.jeesite.modules.webservice;


import java.util.ArrayList;
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
import com.thinkgem.jeesite.modules.hihunan.entity.artical.HiArtical;
import com.thinkgem.jeesite.modules.hihunan.model.artical.HiArticalModel;
import com.thinkgem.jeesite.modules.hihunan.service.artical.HiArticalService;

/**
 * 资讯webservice
 * @author lin
 * @since 2016/11/28
 * 访问地址 hihunan/webservice/rest/newsRest/...
 */
@Path(value="/newsRest")
public class NewsRestServiceImpl implements NewsRestService{
	
	@Autowired
	private HiArticalService hiArticalService;
	
	/**
	 * 查询新闻，教育
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="findNewsAndEducate")
	public String findNewsAndEducate(@Context HttpServletRequest request,@Context HttpServletResponse response) {
		JQResultModel resultModel = new JQResultModel();
		List<HiArticalModel> list = new ArrayList<HiArticalModel>();
		String type = request.getParameter("type");
		HiArtical hiArtical = new HiArtical();
		hiArtical.setAuditState("1");
		if (!"all".equals(type)) {
			hiArtical.setArticalType(type);
			list = hiArticalService.getList(hiArtical);
		}else{
			list = hiArticalService.getZxList(hiArtical);
		}
		resultModel.setRows(list);
		return JSON.toJSONString(resultModel);
	}
	
	/**
	 * 查询新闻，教育 详情
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="findNewsAndEducateDetail")
	public String findNewsAndEducateDetail(@Context HttpServletRequest request,@Context HttpServletResponse response) {
		JQResultModel resultModel = new JQResultModel();
		String id = request.getParameter("id");
		HiArtical hiArtical = new HiArtical();
		hiArtical.setId(id);
		HiArticalModel hiArticalModel = hiArticalService
				.findByParentId(hiArtical);
		resultModel.setUserdata(hiArticalModel);
		return JSON.toJSONString(resultModel);
	}
	
	
}
