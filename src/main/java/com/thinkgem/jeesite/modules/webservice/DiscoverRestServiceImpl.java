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
import com.thinkgem.jeesite.modules.hihunan.model.h5app.HiH5appModel;
import com.thinkgem.jeesite.modules.hihunan.service.h5app.HiH5appService;

/**
 * 发现webservice
 * @author lin
 * @since 2016/11/28
 * 访问地址 hihunan/webservice/rest/discoverRest/...
 */
@Path(value="/discoverRest")
public class DiscoverRestServiceImpl implements DiscoverRestService{
	
	@Autowired
	private HiH5appService hiH5appService;
	
	/**
	 * 查询首页发现
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="findDiscoverList")
	public String findDiscoverList(@Context HttpServletRequest request,@Context HttpServletResponse response) {
		JQResultModel resultModel = new JQResultModel();
		HiH5app hiH5app = new HiH5app();
		List<HiH5appModel> list = hiH5appService.findAll(hiH5app);
		resultModel.setRows(list);
		return JSON.toJSONString(resultModel);
	}
	
	
	
	
}
