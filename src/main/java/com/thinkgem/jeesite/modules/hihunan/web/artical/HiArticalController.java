/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hihunan.web.artical;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Iterator;
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
import com.thinkgem.jeesite.modules.hihunan.entity.artical.HiArticalRef;
import com.thinkgem.jeesite.modules.hihunan.model.artical.HiArticalModel;
import com.thinkgem.jeesite.modules.hihunan.service.artical.HiArticalService;
import com.thinkgem.jeesite.modules.hihunan.service.news.HiNewsService;
import com.thinkgem.jeesite.modules.hihunan.service.restaunant.HiRestaunantService;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.service.DictService;
import com.thinkgem.jeesite.modules.sys.utils.SysParameterUtils;

/**
 * hi_articalController
 * @author yu
 * @version 2016-11-28
 */
@Controller
@RequestMapping(value = "${adminPath}/hihunan/artical/hiArtical")
public class HiArticalController extends BaseController {

	@Autowired
	private HiArticalService hiArticalService;
	@Autowired
	private HiRestaunantService hiRestaunantService;
	@Autowired
	private HiNewsService hiNewsService;
	@Autowired
	private DictService dictService; 
	
	@ModelAttribute
	public HiArtical get(@RequestParam(required=false) String id) {
		HiArtical entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hiArticalService.get(id);
		}
		if (entity == null){
			entity = new HiArtical();
		}
		return entity;
	}
	
	@RequiresPermissions("hihunan:artical:hiArtical:view")
	@RequestMapping(value = {"list", ""})
	public String list(HiArtical hiArtical, HttpServletRequest request, HttpServletResponse response, Model model) {
		String auditState = hiArtical.getAuditState();
		if(auditState == null){
			hiArtical.setAuditState("1");
		}
		Page<HiArtical> page = hiArticalService.findPage(new Page<HiArtical>(request, response), hiArtical); 
		model.addAttribute("page", page);
		List<Dict> listType = dictService.getListByType("hi_list_type");
		Iterator<Dict> listIterator = listType.iterator();
		while (listIterator.hasNext()) {//去掉空白的数据行
			Dict dict = listIterator.next();
			if ("top".equals(dict.getValue())){
				listIterator.remove();
			}
		}
		model.addAttribute("listType", listType);
		
		return "modules/hihunan/artical/hiArticalList";
	}

	@RequiresPermissions("hihunan:artical:hiArtical:view")
	@RequestMapping(value = "form")
	public String form(HiArtical hiArtical, Model model) {
		model.addAttribute("hiArtical", hiArtical);
		model.addAttribute("content", hiArtical.getContent());
		List<Dict> listType = dictService.getListByType("hi_list_type");
		Iterator<Dict> listIterator = listType.iterator();
		while (listIterator.hasNext()) {//去掉空白的数据行
			Dict dict = listIterator.next();
			if ("top".equals(dict.getValue())){
				listIterator.remove();
			}
			//新闻资讯不可添加
			if ("advisory".equals(dict.getValue())){
				listIterator.remove();
			}
		}
		model.addAttribute("listType", listType);
		return "modules/hihunan/artical/hiArticalForm";
	}
	
	
	@RequestMapping(value = "formView")
	public String formView(HiArtical hiArtical, Model model) {
		
		return "modules/hihunan/artical/hiArticalFormView";
	}

	@RequiresPermissions("hihunan:artical:hiArtical:edit")
	@RequestMapping(value = "save")
	public String save(HiArtical hiArtical, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, hiArtical)){
			return form(hiArtical, model);
		}
		// 替换  /hihunan/userfiles  为 http://XXXXXX:XX/hihunan/userfiles
		hiArtical.setTitlePhoto(hiArtical.getTitlePhoto().replaceAll("/hihunan/userfiles", Global.getConfig("adminUrl")+"/hihunan/userfiles"));
		
		//TODO ueditor
		hiArtical.setContent(hiArtical.getContent().replaceAll("/hihunan/userfiles", Global.getConfig("adminUrl")+"/hihunan/userfiles"));
		
		hiArticalService.save(hiArtical);
		addMessage(redirectAttributes, "保存artical成功");
		return "redirect:"+Global.getAdminPath()+"/hihunan/artical/hiArtical/?repage";
	}
	
	@RequiresPermissions("hihunan:artical:hiArtical:edit")
	@RequestMapping(value = "delete")
	public String delete(HiArtical hiArtical, RedirectAttributes redirectAttributes) {
		hiArticalService.delete(hiArtical);
		addMessage(redirectAttributes, "删除artical成功");
		return "redirect:"+Global.getAdminPath()+"/hihunan/artical/hiArtical/?repage";
	}
	
	@RequestMapping(value = {"addHiArtical"})
	@ResponseBody
	public String addHiArtical(HiArtical hiArtical){
		JQResultModel resultModel = new JQResultModel();
		hiArtical.setTitlePhoto(hiArtical.getArticalPhoto());
		int flag = hiArticalService.addHiArtical(hiArtical);
		if(flag > 0){// 新增成功
			resultModel.setResult(0);
			resultModel.setMsg("添加成功");
		}else{
			resultModel.setResult(-2);
			resultModel.setMsg("添加失败,请与管理员联系");
		}
		return JSON.toJSONString(resultModel);
	}
	
	// 获取该商家相关联文章
	@RequestMapping(value = { "getListByReferenceId" })
	@ResponseBody
	public String getListByReferenceId(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		HiArticalRef hiArticalRef = (HiArticalRef) JsonUtils.getBean(request,
				HiArticalRef.class);
		String referenceId = request.getParameter("referenceId");
		hiArticalRef.setReferenceId(referenceId);
		List<HiArticalModel> list = hiArticalService.getListByReferenceId(hiArticalRef);
		JQResultModel resultModel = new JQResultModel(list, hiArticalRef.getPage());
		return JSON.toJSONString(resultModel);
	}
	
	// 根据状态查询对应的所有文章
	@RequestMapping(value = { "getListByCondition" })
	@ResponseBody
	public String getListByCondition(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		HiArtical hiArtical = (HiArtical) JsonUtils.getBean(request,
				HiArtical.class);
		
		String title = request.getParameter("title");
		String auditState = request.getParameter("auditState");
		String articalType = request.getParameter("articalType");
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
		hiArtical.setArticalType(articalType);
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