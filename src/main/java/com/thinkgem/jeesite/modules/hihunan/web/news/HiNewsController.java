/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hihunan.web.news;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.JQResultModel;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.JsonUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.hihunan.entity.artical.HiArtical;
import com.thinkgem.jeesite.modules.hihunan.entity.news.HiNews;
import com.thinkgem.jeesite.modules.hihunan.model.artical.HiArticalModel;
import com.thinkgem.jeesite.modules.hihunan.service.artical.HiArticalService;
import com.thinkgem.jeesite.modules.hihunan.service.news.HiNewsService;
import com.thinkgem.jeesite.modules.hihunan.service.restaunant.HiRestaunantService;
import com.thinkgem.jeesite.modules.sys.utils.SysParameterUtils;

/**
 * 新闻Controller
 * @author yuyabiao
 * @version 2016-11-29
 */
@Controller
@RequestMapping(value = "${adminPath}/hihunan/news/hiNews")
public class HiNewsController extends BaseController {

	@Autowired
	private HiNewsService hiNewsService;
	@Autowired
	private HiArticalService hiArticalService;
	@Autowired
	private HiRestaunantService hiRestaunantService;
	
	@ModelAttribute
	public HiNews get(@RequestParam(required=false) String id) {
		HiNews entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hiNewsService.get(id);
		}
		if (entity == null){
			entity = new HiNews();
		}
		return entity;
	}
	
	@RequiresPermissions("hihunan:news:hiNews:view")
	@RequestMapping(value = {"list",""})
	public String list(HiNews hiNews, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<HiNews> page = hiNewsService.findPage(new Page<HiNews>(request, response), hiNews); 
		model.addAttribute("page", page);
		return "modules/hihunan/news/hiNewsList";
	}

	@RequiresPermissions("hihunan:news:hiNews:view")
	@RequestMapping(value = "form")
	public String form(HiNews hiNews, Model model) {
		model.addAttribute("hiNews", hiNews);
		return "modules/hihunan/news/hiNewsForm";
	}

	@RequiresPermissions("hihunan:news:hiNews:edit")
	@RequestMapping(value = "save")
	public String save(HiNews hiNews, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, hiNews)){
			return form(hiNews, model);
		}
		hiNewsService.save(hiNews);
		addMessage(redirectAttributes, "保存新闻成功");
		return "redirect:"+Global.getAdminPath()+"/hihunan/news/hiNews/?repage";
	}
	
	@RequiresPermissions("hihunan:news:hiNews:edit")
	@RequestMapping(value = "delete")
	public String delete(HiNews hiNews, RedirectAttributes redirectAttributes) {
		hiNewsService.delete(hiNews);
		addMessage(redirectAttributes, "删除新闻成功");
		return "redirect:"+Global.getAdminPath()+"/hihunan/news/hiNews/?repage";
	}
	
	// 根据状态查询对应的所有文章
	@RequestMapping(value = { "getListByAuditState" })
	@ResponseBody
	public String getListByAuditState(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		HiArtical hiArtical = (HiArtical) JsonUtils.getBean(request,
				HiArtical.class);
		
		String title = request.getParameter("title");
		String auditState = request.getParameter("auditState");
		if(title!= null){
			try {
				hiArtical.setTitle(URLDecoder.decode(title,"UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//审批状态
		hiArtical.setAuditState(auditState);
		List<HiArticalModel> list = hiArticalService.getList(hiArtical);
		JQResultModel resultModel = new JQResultModel(list, hiArtical.getPage());
		return JSON.toJSONString(resultModel);
	}
	
	//审批 上架 下架
	@RequestMapping(value = {"updateAuditState"})
	@ResponseBody
	public String updateAuditState(HttpServletRequest request,
			HttpServletResponse response, Model model){
		JQResultModel resultModel = new JQResultModel();
		String ids = request.getParameter("ids");
		String auditState = request.getParameter("auditState");
		int length = ids.length();
		ids = ids.substring(1, length-1);
		ids = ids.replace("\"", "");
		String[] articalIds = ids.split(",");
		int flag = hiRestaunantService.updateAuditState(articalIds,auditState);
		if(flag > 0){// 新增成功
			resultModel.setResult(0);
			resultModel.setMsg("操作成功");
		}else{
			resultModel.setResult(-2);
			resultModel.setMsg("操作失败,请与管理员联系");
		}
		return JSON.toJSONString(resultModel);
	}
	
	@RequestMapping(value = {"scrapyNews"})
	@ResponseBody
	public String scrapyNews(HttpServletRequest request,HttpServletResponse response){
		JQResultModel resultModel = new JQResultModel();
		int flag = hiNewsService.scrapyNewsFromQQ();
		if(flag == 0){
			resultModel.setResult(0);
			resultModel.setMsg("获取新闻成功");
		}else{
			resultModel.setResult(-1);
			resultModel.setMsg("获取新闻失败");
		}
		return JSON.toJSONString(resultModel);
	}
	
	@RequestMapping(value = {"loadNewsPhoto"})
	@ResponseBody
	public String loadNewsPhoto(HttpServletRequest request,HttpServletResponse response){
		JQResultModel resultModel = new JQResultModel();
		int flag = hiNewsService.loadImage();
		if(flag == 0){
			resultModel.setResult(0);
			resultModel.setMsg("图片下载成功");
		}else{
			resultModel.setResult(-1);
			resultModel.setMsg("图片下载失败");
		}
		return JSON.toJSONString(resultModel);
	}
	
	@RequestMapping(value = {"deleteNews"})
	@ResponseBody
	public String deleteNews(HttpServletRequest request,HttpServletResponse response){
		JQResultModel resultModel = new JQResultModel();
		int validDay = Integer.parseInt(SysParameterUtils.findKeyword("newsValidDay").getValue1());
		HiArtical hiArtical = new HiArtical();
		hiArtical.setValidDay(validDay);
		int flag = hiNewsService.deleteNews(hiArtical);
		if(flag == 0){
			resultModel.setResult(0);
			resultModel.setMsg("成功删除过时新闻");
		}else{
			resultModel.setResult(-1);
			resultModel.setMsg("删除失败");
		}
		return JSON.toJSONString(resultModel);
	}
}