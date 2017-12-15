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
import com.sun.tools.internal.ws.wsdl.framework.ValidationException;
import com.thinkgem.jeesite.common.persistence.JQResultModel;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.hihunan.dao.h5app.HiH5appDao;
import com.thinkgem.jeesite.modules.hihunan.entity.favorites.HiFavorites;
import com.thinkgem.jeesite.modules.hihunan.entity.h5app.HiH5app;
import com.thinkgem.jeesite.modules.hihunan.entity.listshow.HiListShow;
import com.thinkgem.jeesite.modules.hihunan.entity.localactivity.HiLocalActivity;
import com.thinkgem.jeesite.modules.hihunan.model.h5app.HiH5appModel;
import com.thinkgem.jeesite.modules.hihunan.model.listshow.HiListShowModel;
import com.thinkgem.jeesite.modules.hihunan.model.localactivity.HiLocalActivityListModel;
import com.thinkgem.jeesite.modules.hihunan.service.favorites.HiFavoritesService;
import com.thinkgem.jeesite.modules.hihunan.service.localactivity.HiLocalActivityService;
import com.thinkgem.jeesite.modules.sys.utils.SysParameterUtils;
import com.thinkgem.jeesite.modules.webservice.LocalActivityRestService;

/**
 * 本地生活webservice
 * @author zfg
 * @since 2016/12/2
 * 访问地址hihunan/webservice/rest/localActivityRest/...
 */
@Path(value="/localActivityRest")
public class LocalActivityRestServiceImpl implements LocalActivityRestService{
	
	@Autowired
	private HiLocalActivityService hiLocalActivityService;
	@Autowired
	private HiH5appDao hiH5appDao;
	@Autowired
	private HiFavoritesService hiFavoritesService;
	
	/**
	 * 查询本地活动列表
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value = "findLocalList")
	//@Transactional(readOnly = true)
	public String findLocalList(@Context HttpServletRequest request,@Context HttpServletResponse response) throws ValidationException{
		JQResultModel resultModel = new JQResultModel();
		List<HiH5appModel> list = new ArrayList<HiH5appModel>();
		try {
			HiH5app hiH5app = new HiH5app();
			hiH5app.setType1("top");
			hiH5app.setType2("list");
			hiH5app.setIsEffect("1");
			list = hiH5appDao.getLocalListTitle(hiH5app);
				//本地生活调接口
				HiListShow hiListShow = new HiListShow();
				hiListShow.setListMaxCount(1);
				List<HiListShowModel> listModel = hiLocalActivityService.findLocalList(hiListShow);
				list.get(0).setListModel(listModel);
					
		} catch (Exception e) {
			list = null;
			throw new ValidationException(e.toString());
		}
		resultModel.setRows(list);
		return JSON.toJSONString(resultModel);
		
	}

	/**
	 * 查询本地活动详情
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="findLocalDetail")
	public String findLocalDetail(@Context HttpServletRequest request,@Context HttpServletResponse response){
		JQResultModel resultModel = new JQResultModel();
		String id = request.getParameter("id");
		HiLocalActivity hiLocalActivity = new HiLocalActivity();
		hiLocalActivity.setId(id);
		String hiLocal = hiLocalActivityService.findByParentId(hiLocalActivity);
		resultModel.setUserdata(hiLocal);
		//return hiLocal;
		return JSON.toJSONString(resultModel);
	}

	/**
	 * 报名
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="joinActivity")
	public String joinActivity(@Context HttpServletRequest request,@Context HttpServletResponse response) {
		String id = request.getParameter("id");
		String token = request.getParameter("token");
		return hiLocalActivityService.joinActivity(token, id);
	}

	/**
	 * 收藏
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="toCollect")
	public String toCollect(@Context HttpServletRequest request,@Context HttpServletResponse response) {
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
	}

	/**
	 * 删除收藏接口
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="deleteCollect")
	public String deleteCollect(@Context HttpServletRequest request,@Context HttpServletResponse response) {
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
	}

	/**
	 * 获取本地活动是否收藏
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="findLocalIsCollect")
	public String findLocalIsCollect(@Context HttpServletRequest request,@Context HttpServletResponse response) {
		JQResultModel resultModel = new JQResultModel();
		String token = request.getParameter("token");
		String url = request.getParameter("url");
		HiFavorites hiFavorites = new HiFavorites();
		hiFavorites.setFavoritesUrl(url);
		String flag = hiFavoritesService.findIsCollect(hiFavorites, token);
		resultModel.setMsg(flag);
		return JSON.toJSONString(resultModel);
	}
	
	/**
	 * 本地生活列表
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value="findList")
	public String findList(@Context HttpServletRequest request,@Context HttpServletResponse response){
		JQResultModel resultModel = new JQResultModel();
		String type = request.getParameter("type");
		String userFilePath = SysParameterUtils.findKeyword("userfile").getValue1();//鑾峰彇澶氬獟浣撴枃浠舵牴鐩綍
		HiLocalActivityListModel hiLocalActivityList = new HiLocalActivityListModel();
		hiLocalActivityList.setSort(type);
		List<HiLocalActivityListModel> list = hiLocalActivityService.findLocalListFull(hiLocalActivityList);
		resultModel.setUserdata(userFilePath);
		resultModel.setRows(list);
		return JSON.toJSONString(resultModel);
	}

}
