/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hihunan.web.restaunant;

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
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.JsonUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.hihunan.entity.artical.HiArtical;
import com.thinkgem.jeesite.modules.hihunan.entity.artical.HiArticalRef;
import com.thinkgem.jeesite.modules.hihunan.entity.restaunant.HiRestaunant;
import com.thinkgem.jeesite.modules.hihunan.model.artical.HiArticalModel;
import com.thinkgem.jeesite.modules.hihunan.service.restaunant.HiRestaunantService;
import com.thinkgem.jeesite.modules.hihunan.service.artical.HiArticalService;

import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.List;

/**
 * 美食Controller
 * @author lin
 * @version 2016-12-02
 */
@Controller
@RequestMapping(value = "${adminPath}/hihunan/restaunant/hiRestaunant")
public class HiRestaunantController extends BaseController {

	@Autowired
	private HiRestaunantService hiRestaunantService;
	@Autowired
	private HiArticalService hiArticalService;
	
	@ModelAttribute
	public HiRestaunant get(@RequestParam(required=false) String id) {
		HiRestaunant entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hiRestaunantService.get(id);
		}
		if (entity == null){
			entity = new HiRestaunant();
		}
		return entity;
	}
	
	@RequiresPermissions("hihunan:restaunant:hiRestaunant:view")
	@RequestMapping(value = {"list", ""})
	public String list(HiRestaunant hiRestaunant, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<HiRestaunant> page = hiRestaunantService.findPage(new Page<HiRestaunant>(request, response), hiRestaunant); 
		model.addAttribute("page", page);
		return "modules/hihunan/restaunant/hiRestaunantList";
	}

	@RequiresPermissions("hihunan:restaunant:hiRestaunant:view")
	@RequestMapping(value = "form")
	public String form(HiRestaunant hiRestaunant, Model model) {
		model.addAttribute("hiRestaunant", hiRestaunant);
		//商家id
		model.addAttribute("hiRestaunantId",hiRestaunant.getId());
		//文章
		HiArtical hiArtical = new HiArtical();
		model.addAttribute("hiArtical", hiArtical);
		return "modules/hihunan/restaunant/hiRestaunantForm";
	}

	@RequiresPermissions("hihunan:restaunant:hiRestaunant:edit")
	@RequestMapping(value = "save")
	public String save(HiRestaunant hiRestaunant, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, hiRestaunant)){
			return form(hiRestaunant, model);
		}
		// 替换  /hihunan/userfiles  为 http://XXXXXX:XX/hihunan/userfiles
		hiRestaunant.setTitlePhoto(hiRestaunant.getTitlePhoto().replaceAll("/hihunan/userfiles", Global.getConfig("adminUrl")+"/hihunan/userfiles"));
		
		hiRestaunantService.save(hiRestaunant);
		addMessage(redirectAttributes, "保存餐厅成功");
		return "redirect:"+Global.getAdminPath()+"/hihunan/restaunant/hiRestaunant/?repage";
	}
	
	@RequiresPermissions("hihunan:restaunant:hiRestaunant:edit")
	@RequestMapping(value = "delete")
	public String delete(HiRestaunant hiRestaunant, RedirectAttributes redirectAttributes) {
		hiRestaunantService.delete(hiRestaunant);
		addMessage(redirectAttributes, "删除餐厅成功");
		return "redirect:"+Global.getAdminPath()+"/hihunan/restaunant/hiRestaunant/?repage";
	}
	
	//添加
	@RequestMapping(value = {"addHiArticalMap"})
	@ResponseBody
	public String addHiArticalMap(HttpServletRequest request,
			HttpServletResponse response, Model model){
		JQResultModel resultModel = new JQResultModel();
		String referenceId = request.getParameter("referenceId");
		String articalId = request.getParameter("articalId");
		int length = articalId.length();
		articalId = articalId.substring(1, length-1);
		articalId = articalId.replace("\"", "");
		String[] articalIds = articalId.split(",");
		int flag = hiRestaunantService.addHiArticalMap(referenceId,articalIds);
		if(flag > 0){// 新增成功
			resultModel.setResult(0);
			resultModel.setUserdata(referenceId);
			resultModel.setMsg("添加成功");
		}else{
			resultModel.setResult(-2);
			resultModel.setMsg("添加失败,请与管理员联系");
		}
		return JSON.toJSONString(resultModel);
	}
	
	//删除引用文章关系
	@RequestMapping(value = {"deleteHiArticalMap"})
	@ResponseBody
	public String deleteHiArticalMap(HttpServletRequest request,
			HttpServletResponse response, Model model){
		JQResultModel resultModel = new JQResultModel();
		HiArticalRef hiArticalRef = new HiArticalRef();
		String id = request.getParameter("id");
		hiArticalRef.setId(id);
		int flag = hiRestaunantService.deleteHiArticalMap(hiArticalRef);
		if(flag > 0){// 新增成功
			resultModel.setResult(0);
			resultModel.setMsg("删除成功");
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
	
	// 获取所有文章
	@RequestMapping(value = { "getList" })
	@ResponseBody
	public String getList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		HiArtical hiArtical = (HiArtical) JsonUtils.getBean(request,
				HiArtical.class);
		
		String title = request.getParameter("title");
		String articalType = request.getParameter("articalType");
		if(title!= null){
			try {
				hiArtical.setTitle(URLDecoder.decode(title,"UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		hiArtical.setArticalType(articalType);
		List<HiArticalModel> list = hiArticalService.getList(hiArtical);
		JQResultModel resultModel = new JQResultModel(list, hiArtical.getPage());
		return JSON.toJSONString(resultModel);
	}
		

}