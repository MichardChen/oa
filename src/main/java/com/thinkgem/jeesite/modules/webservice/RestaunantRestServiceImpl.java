package com.thinkgem.jeesite.modules.webservice;

import java.math.BigDecimal;
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
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.hihunan.entity.artical.HiArtical;
import com.thinkgem.jeesite.modules.hihunan.entity.artical.HiArticalRef;
import com.thinkgem.jeesite.modules.hihunan.entity.restaunant.HiRestaunant;
import com.thinkgem.jeesite.modules.hihunan.model.artical.HiArticalModel;
import com.thinkgem.jeesite.modules.hihunan.model.listshow.HiListShowModel;
import com.thinkgem.jeesite.modules.hihunan.model.restaunant.HiRestaunantModel;
import com.thinkgem.jeesite.modules.hihunan.service.artical.HiArticalService;
import com.thinkgem.jeesite.modules.hihunan.service.headershow.HiHeaderShowService;
import com.thinkgem.jeesite.modules.hihunan.service.restaunant.HiRestaunantService;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.model.DictModel;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 旅游webservice
 * @author yuyabiao  
 * @since 2016/11/29 访问地址 hihunan/webservice/rest/restaunantRest/... 
 */
@Path(value = "/restaunantRest")
public class RestaunantRestServiceImpl implements RestaunantRestService {

	@Autowired
	private HiRestaunantService hiRestaunantService;

	@Autowired
	private HiArticalService hiArticalService;
	
	@Autowired
	private HiHeaderShowService hiHeaderShowService;
	
	
	/**
	 * 动态生成美食餐厅的搜索条件
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="findSelectCondition")
	public String findSelectCondition(@Context HttpServletRequest request,@Context HttpServletResponse response){
		List<DictModel> list = new ArrayList<DictModel>();
		List<Dict> cookList = DictUtils.getDictList("hi_cooking_style");//菜系
		if(cookList.size() > 0){
			for(Dict dict:cookList){
				DictModel dictModel = new DictModel();
				dictModel.setType(dict.getType());
				dictModel.setValue(dict.getValue());
				dictModel.setLabel(dict.getLabel());
				list.add(dictModel);
			}
		}
		List<Dict> tradingList = DictUtils.getDictList("hi_trading_area");//商圈
		if(tradingList.size() > 0){
			for(Dict dict:tradingList){
				DictModel dictModel = new DictModel();
				dictModel.setType(dict.getType());
				dictModel.setValue(dict.getValue());
				dictModel.setLabel(dict.getLabel());
				list.add(dictModel);
			}
		}
		List<Dict> distanceList = DictUtils.getDictList("hi_distance");//距离
		if(distanceList.size() > 0){
			for(Dict dict : distanceList){
				DictModel dictModel = new DictModel();
				dictModel.setType(dict.getType());
				dictModel.setValue(dict.getValue());
				dictModel.setLabel(dict.getLabel());
				list.add(dictModel);
			}
		}
		JQResultModel resultModel = new JQResultModel();
		resultModel.setRows(list);
		return JSON.toJSONString(resultModel);
	}
	
	/**
	 * 推荐菜馆
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value = "findRecommondRestaunant")
	public String findRecommondRestaunant(@Context HttpServletRequest request,@Context HttpServletResponse response) {
		JQResultModel resultModel = new JQResultModel();
		HiArtical hiArtical = new  HiArtical();
		hiArtical.setArticalType("restaurant");
		List<HiListShowModel> List = hiRestaunantService.getRecomentRestaunant(hiArtical);
		resultModel.setRows(List);
		return JSON.toJSONString(resultModel);
	}

	/**
	 * 餐厅列表
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value = "findRestaunantList")
	public String findRestaunantList(@Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		JQResultModel resultModel = new JQResultModel();
		String tradingArea = request.getParameter("tradingArea");//商圈参数
		String cookingStyle = request.getParameter("cookingStyle");//菜系参数
		String distance = request.getParameter("distance");//距离参数
		String latitude = request.getParameter("latitude");
		String longitude = request.getParameter("longitude");
		HiRestaunant hiRestaunant = new HiRestaunant();
		hiRestaunant.setTradingArea(tradingArea);
		hiRestaunant.setCookingStyle(cookingStyle);
		if(StringUtils.isNotBlank(longitude) && StringUtils.isNotBlank(latitude)){
			hiRestaunant.setLatitude(new BigDecimal(latitude));
			hiRestaunant.setLongitude(new BigDecimal(longitude));
			if(StringUtils.isNotBlank(distance)){
				String [] disM = distance.split("-");
				hiRestaunant.setMinDistance(Integer.parseInt(disM[0]));
				if(disM.length > 1){
					hiRestaunant.setMaxDistance(Integer.parseInt(disM[1]));
				}
			}
		}
		List<HiRestaunantModel> List = hiRestaunantService.getAll(hiRestaunant);
		resultModel.setRows(List);
		return JSON.toJSONString(resultModel);
	}
	
	/**
	 * 查询餐厅
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value = "findRestaunant")
	public String findRestaunant(@Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		JQResultModel resultModel = new JQResultModel();
		String id = request.getParameter("id");
		String url = request.getParameter("url");
		String key = request.getParameter("key");
		HiRestaunant hiRestaunant = new HiRestaunant();
		hiRestaunant.setId(id);
		hiRestaunant.setUrl(url);
		HiRestaunantModel hiRestaunantModel = hiRestaunantService.findRestaunant(hiRestaunant,key);
		resultModel.setUserdata(hiRestaunantModel);
		return JSON.toJSONString(resultModel);
	}

	/**
	 * 查询美食资讯
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value = "findFoodInformation")
	public String findFoodInformation(@Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		JQResultModel resultModel = new JQResultModel();
		String id = request.getParameter("id");
		HiArticalRef hiArticalRef = new HiArticalRef();
		hiArticalRef.setReferenceId(id);
		List<HiArticalModel> list = hiArticalService
				.findFoodInformation(hiArticalRef);
		resultModel.setRows(list);
		return JSON.toJSONString(resultModel);
	}
	
	/**
	 * 查询美食资讯明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value = "findFoodInformationDetail")
	public String findFoodInformationDetail(@Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		JQResultModel resultModel = new JQResultModel();
		String id = request.getParameter("id");
		HiArtical hiArtical = new HiArtical();
		hiArtical.setId(id);
		HiArticalModel hiArticalModel = hiArticalService.findByParentId(hiArtical);
		resultModel.setUserdata(hiArticalModel);;
		return JSON.toJSONString(resultModel);
	}
	
	/**
	 * 查询美食关联文章
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value = "findListShowDetail")
	public String findListShowDetail(@Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		JQResultModel resultModel = new JQResultModel();
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		HiArtical hiArtical = new HiArtical();
		if("restaurant".equals(type)){
			hiArtical.setId(id);
		}else{
			hiArtical.setParentId(id);
		}
		HiArticalModel hiArticalModel = hiArticalService
				.findByParentId(hiArtical);
		resultModel.setUserdata(hiArticalModel);
		return JSON.toJSONString(resultModel);
	}
	
	
	

	
}
