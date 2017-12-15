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
import com.thinkgem.jeesite.modules.hihunan.entity.favorites.HiFavorites;
import com.thinkgem.jeesite.modules.hihunan.entity.h5app.HiH5app;
import com.thinkgem.jeesite.modules.hihunan.entity.headershow.HiHeaderShow;
import com.thinkgem.jeesite.modules.hihunan.entity.listshow.HiListShow;
import com.thinkgem.jeesite.modules.hihunan.entity.localactivity.HiLocalActivity;
import com.thinkgem.jeesite.modules.hihunan.entity.restaunant.HiRestaunant;
import com.thinkgem.jeesite.modules.hihunan.model.artical.HiArticalModel;
import com.thinkgem.jeesite.modules.hihunan.model.h5app.HiH5appModel;
import com.thinkgem.jeesite.modules.hihunan.model.herdershow.HiHeaderShowModel;
import com.thinkgem.jeesite.modules.hihunan.model.listshow.HiListShowModel;
import com.thinkgem.jeesite.modules.hihunan.model.localactivity.HiLocalActivityListModel;
import com.thinkgem.jeesite.modules.hihunan.model.restaunant.HiRestaunantModel;
import com.thinkgem.jeesite.modules.hihunan.service.artical.HiArticalService;
import com.thinkgem.jeesite.modules.hihunan.service.favorites.HiFavoritesService;
import com.thinkgem.jeesite.modules.hihunan.service.h5app.HiH5appService;
import com.thinkgem.jeesite.modules.hihunan.service.headershow.HiHeaderShowService;
import com.thinkgem.jeesite.modules.hihunan.service.listshow.HiListShowService;
import com.thinkgem.jeesite.modules.hihunan.service.localactivity.HiLocalActivityService;
import com.thinkgem.jeesite.modules.hihunan.service.news.HiNewsService;
import com.thinkgem.jeesite.modules.hihunan.service.restaunant.HiRestaunantService;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.model.DictModel;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 统一入口
 * 
 * @author yuyabiao
 * @since 2016/12/03 访问地址 hihunan/webservice/rest/baseRest/...
 */
@Path(value = "/baseRest")
public class BaseRestServiceImpl implements BaseRestService {

	@Autowired
	private HiListShowService hiListShowService;

	@Autowired
	private HiArticalService hiArticalService;

	@Autowired
	private HiNewsService hiNewsService;

	@Autowired
	private HiH5appService hiH5appService;

	@Autowired
	private HiHeaderShowService hiHeaderShowService;

	@Autowired
	private HiLocalActivityService hiLocalActivityService;

	@Autowired
	private HiRestaunantService hiRestaunantService;
	
	@Autowired
	private HiFavoritesService hiFavoritesService;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value = "baseFind")
	public String baseFind(@Context HttpServletRequest request,@Context HttpServletResponse response) {
		String action = request.getParameter("action");// 获取请求的访问名称
		if ("findListShow".endsWith(action)) {// 查询list_show列表
			JQResultModel resultModel = new JQResultModel();
			String type = request.getParameter("type");
			HiListShow hiTravelList = new HiListShow();
			hiTravelList.setType(type);
			List<HiListShowModel> list = hiListShowService
					.findAll(hiTravelList);
			resultModel.setRows(list);
			return JSON.toJSONString(resultModel);
		} else if ("findListShowDetail".equals(action)) {// 查询list_show详情
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
		} else if ("findNewsAndEducate".equals(action)) {// 查询新闻，教育
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
		} else if("findNewsAndEducateDetail".equals(action)){
			JQResultModel resultModel = new JQResultModel();
			String id = request.getParameter("id");
			HiArtical hiArtical = new HiArtical();
			hiArtical.setId(id);
			HiArticalModel hiArticalModel = hiArticalService
					.findByParentId(hiArtical);
			resultModel.setUserdata(hiArticalModel);
			return JSON.toJSONString(resultModel);
		}else if ("findDiscoverList".equals(action)) {// 查询发现
			JQResultModel resultModel = new JQResultModel();
			HiH5app hiH5app = new HiH5app();
			List<HiH5appModel> list = hiH5appService.findAll(hiH5app);
			resultModel.setRows(list);
			return JSON.toJSONString(resultModel);
		} else if ("findBanner".equals(action)) {// 查询轮播列表
			JQResultModel resultModel = new JQResultModel();
			HiHeaderShow hiHeaderShow = new HiHeaderShow();
			String isEffect = "1";// 这里是要查询生效的全部轮播信息
			String listType = request.getParameter("listType");
			hiHeaderShow.setIsEffect(isEffect);
			hiHeaderShow.setListType(listType);
			List<HiHeaderShowModel> list = hiHeaderShowService
					.findAll(hiHeaderShow);
			resultModel.setRows(list);
			return JSON.toJSONString(resultModel);
		} else if ("findHeaderItem".equals(action)) {// 查询首页应用栏目列表
			JQResultModel resultModel = new JQResultModel();
			HiH5app hiH5app = new HiH5app();
			String type1 = "top";// 查询首页的应用类型
			hiH5app.setIsEffect("1");
			hiH5app.setType1(type1);
			List<HiH5appModel> list = hiH5appService.findHeaderItem(hiH5app);
			resultModel.setRows(list);
			return JSON.toJSONString(resultModel);
		} else if ("findHeaderList".equals(action)) {// 查询首页功能列表
			JQResultModel resultModel = new JQResultModel();
			HiH5app hiH5app = new HiH5app();
			hiH5app.setType1("top");
			hiH5app.setType2("list");
			hiH5app.setIsEffect("1");
			List<HiH5appModel> list = hiH5appService.findListType(hiH5app);
			resultModel.setRows(list);
			return JSON.toJSONString(resultModel);
		} else if ("findLocalList".equals(action)) {// 查询本地生活列表
			JQResultModel resultModel = new JQResultModel();
			String type = request.getParameter("type");
			HiLocalActivityListModel hiLocalActivityList = new HiLocalActivityListModel();
			hiLocalActivityList.setSort(type);
			List<HiLocalActivityListModel> list = hiLocalActivityService
					.findLocalListFull(hiLocalActivityList);
			resultModel.setRows(list);
			return JSON.toJSONString(resultModel);
		} else if ("findLocalDetail".equals(action)) {// 查询本地生活详情
			JQResultModel resultModel = new JQResultModel();
			String id = request.getParameter("id");
			HiLocalActivity hiLocalActivity = new HiLocalActivity();
			hiLocalActivity.setId(id);
			String hiLocal = hiLocalActivityService.findByParentId(hiLocalActivity);
			resultModel.setUserdata(hiLocal);
			return JSON.toJSONString(resultModel);
		} else if ("findSelectCondition".equals(action)) {// 动态生成美食餐厅的搜索条件
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
		} else if("findRestaunantList".equals(action)){//餐厅列表
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
		}else if ("findFoodInformation".equals(action)) {// 美食资讯
			JQResultModel resultModel = new JQResultModel();
			String id = request.getParameter("id");
			HiArticalRef hiArticalRef = new HiArticalRef();
			hiArticalRef.setReferenceId(id);
			List<HiArticalModel> list = hiArticalService
					.findFoodInformation(hiArticalRef);
			resultModel.setRows(list);
			return JSON.toJSONString(resultModel);
		} else if ("findFoodInformationDetail".equals(action)) {// 美食资讯明细
			JQResultModel resultModel = new JQResultModel();
			String id = request.getParameter("id");
			HiArtical hiArtical = new HiArtical();
			hiArtical.setId(id);
			HiArticalModel hiArticalModel = hiArticalService.findByParentId(hiArtical);
			resultModel.setUserdata(hiArticalModel);;
			return JSON.toJSONString(resultModel);
		} else if ("findRecommondRestaunant".equals(action)) {// 推荐菜馆
			JQResultModel resultModel = new JQResultModel();
			HiArtical hiArtical = new  HiArtical();
			hiArtical.setArticalType("restaurant");
			List<HiListShowModel> List = hiRestaunantService.getRecomentRestaunant(hiArtical);
			resultModel.setRows(List);
			return JSON.toJSONString(resultModel);
		} else if("findRestaunant".equals(action)){
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
		}else if("joinActivity".equals(action)){//活动报名
			String id = request.getParameter("id");
			String token = request.getParameter("token");
			return hiLocalActivityService.joinActivity(token, id);
		}else if("toCollect".equals(action)){//收藏
			JQResultModel resultModel = new JQResultModel();
			String token = request.getParameter("token");
			String type = request.getParameter("type");
			String title = request.getParameter("title");
			String comment = request.getParameter("comment");
			String imgUrl = request.getParameter("img");
			String url = request.getParameter("url");
			HiFavorites hiFavorites = new HiFavorites();
			hiFavorites.setFavoritesTitle(title);
			hiFavorites.setFavoritesType(Short.parseShort(type));
			hiFavorites.setFavoritesImage(imgUrl);
			hiFavorites.setFavoritesUrl(url);
			if(StringUtils.isNotBlank(comment)){
				hiFavorites.setFavoritesComment(comment);
			}
			hiFavoritesService.enshrine(hiFavorites,token);
			resultModel.setMsg("收藏成功");
			return JSON.toJSONString(resultModel);
		}else if("deleteCollect".equals(action)){//删除收藏接口
			JQResultModel resultModel = new JQResultModel();
			String id = request.getParameter("id");
			String key = request.getParameter("key");
			String url = request.getParameter("url");
			HiFavorites hiFavorites = new HiFavorites();
			hiFavorites.setFavoritesId(id);
			hiFavorites.setFavoritesUrl(url);
			int flag = hiFavoritesService.deleteCollect(hiFavorites,key);
			resultModel.setResult(flag);
			resultModel.setMsg("取消成功");
			return JSON.toJSONString(resultModel);
		} else if("findLocalIsCollect".equals(action)){//获取本地活动是否收藏
			JQResultModel resultModel = new JQResultModel();
			String token = request.getParameter("token");
			String url = request.getParameter("url");
			HiFavorites hiFavorites = new HiFavorites();
			hiFavorites.setFavoritesUrl(url);
			String flag = hiFavoritesService.findIsCollect(hiFavorites, token);
			resultModel.setMsg(flag);
			return JSON.toJSONString(resultModel);
		}else {
			JQResultModel resultModel = new JQResultModel();
			resultModel.setMsg("方法名错误！");
			return JSON.toJSONString(resultModel);
		}
	}
}
