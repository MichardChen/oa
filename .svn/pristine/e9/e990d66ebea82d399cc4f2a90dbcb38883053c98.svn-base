/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hihunan.web.headershow;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.hihunan.entity.h5app.HiH5app;
import com.thinkgem.jeesite.modules.hihunan.entity.headershow.HiHeaderShow;
import com.thinkgem.jeesite.modules.hihunan.service.h5app.HiH5appService;
import com.thinkgem.jeesite.modules.hihunan.service.headershow.HiHeaderShowService;

/**
 * 头部轮播Controller
 * @author yuyabiao
 * @version 2016-11-30
 */
@Controller
@RequestMapping(value = "${adminPath}/hihunan/headershow/hiHeaderShow")
public class HiHeaderShowController extends BaseController {

	@Autowired
	private HiHeaderShowService hiHeaderShowService;
	@Autowired
	private HiH5appService hiH5appService;
	
	@ModelAttribute
	public HiHeaderShow get(@RequestParam(required=false) String id) {
		HiHeaderShow entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hiHeaderShowService.get(id);
		}
		if (entity == null){
			entity = new HiHeaderShow();
		}
		return entity;
	}
	
	@RequiresPermissions("hihunan:headershow:hiHeaderShow:view")
	@RequestMapping(value = {"list", ""})
	public String list(HiHeaderShow hiHeaderShow, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<HiHeaderShow> page = hiHeaderShowService.findPage(new Page<HiHeaderShow>(request, response), hiHeaderShow); 
		model.addAttribute("page", page);
		return "modules/hihunan/headershow/hiHeaderShowList";
	}

	@RequiresPermissions("hihunan:headershow:hiHeaderShow:view")
	@RequestMapping(value = "form")
	public String form(HiHeaderShow hiHeaderShow, Model model) {
		model.addAttribute("hiHeaderShow", hiHeaderShow);
		model.addAttribute("H5appList", hiH5appService.getH5appList());
		return "modules/hihunan/headershow/hiHeaderShowForm";
	}

	@RequiresPermissions("hihunan:headershow:hiHeaderShow:edit")
	@RequestMapping(value = "save")
	public String save(HiHeaderShow hiHeaderShow, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, hiHeaderShow)){
			return form(hiHeaderShow, model);
		}
		// 跟据H5appId获取对应的listType
		HiH5app hiH5app = hiH5appService.get(hiHeaderShow.getH5appId());
		if(hiH5app != null){
			hiHeaderShow.setListType(hiH5app.getListType());
		}
		
		// 替换  /hihunan/userfiles  为 http://XXXXXX:XX/hihunan/userfiles
		hiHeaderShow.setTitlePhoto(hiHeaderShow.getTitlePhoto().replaceAll("/hihunan/userfiles", Global.getConfig("adminUrl")+"/hihunan/userfiles"));
				
		hiHeaderShowService.save(hiHeaderShow);
		addMessage(redirectAttributes, "保存头部轮播成功");
		return "redirect:"+Global.getAdminPath()+"/hihunan/headershow/hiHeaderShow/?repage";
	}
	
	@RequiresPermissions("hihunan:headershow:hiHeaderShow:edit")
	@RequestMapping(value = "delete")
	public String delete(HiHeaderShow hiHeaderShow, RedirectAttributes redirectAttributes) {
		hiHeaderShowService.delete(hiHeaderShow);
		addMessage(redirectAttributes, "删除头部轮播成功");
		return "redirect:"+Global.getAdminPath()+"/hihunan/headershow/hiHeaderShow/?repage";
	}

}