/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hihunan.web.culture;

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
import com.thinkgem.jeesite.modules.hihunan.entity.culture.HiCulture;
import com.thinkgem.jeesite.modules.hihunan.service.culture.HiCultureService;

/**
 * 文化Controller
 * @author yuyabiao
 * @version 2016-11-29
 */
@Controller
@RequestMapping(value = "${adminPath}/hihunan/culture/hiCulture")
public class HiCultureController extends BaseController {

	@Autowired
	private HiCultureService hiCultureService;
	
	@ModelAttribute
	public HiCulture get(@RequestParam(required=false) String id) {
		HiCulture entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hiCultureService.get(id);
		}
		if (entity == null){
			entity = new HiCulture();
		}
		return entity;
	}
	
	@RequiresPermissions("hihunan:culture:hiCulture:view")
	@RequestMapping(value = {"list", ""})
	public String list(HiCulture hiCulture, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<HiCulture> page = hiCultureService.findPage(new Page<HiCulture>(request, response), hiCulture); 
		model.addAttribute("page", page);
		return "modules/hihunan/culture/hiCultureList";
	}

	@RequiresPermissions("hihunan:culture:hiCulture:view")
	@RequestMapping(value = "form")
	public String form(HiCulture hiCulture, Model model) {
		model.addAttribute("hiCulture", hiCulture);
		return "modules/hihunan/culture/hiCultureForm";
	}

	@RequiresPermissions("hihunan:culture:hiCulture:edit")
	@RequestMapping(value = "save")
	public String save(HiCulture hiCulture, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, hiCulture)){
			return form(hiCulture, model);
		}
		hiCultureService.save(hiCulture);
		addMessage(redirectAttributes, "保存文化成功");
		return "redirect:"+Global.getAdminPath()+"/hihunan/culture/hiCulture/?repage";
	}
	
	@RequiresPermissions("hihunan:culture:hiCulture:edit")
	@RequestMapping(value = "delete")
	public String delete(HiCulture hiCulture, RedirectAttributes redirectAttributes) {
		hiCultureService.delete(hiCulture);
		addMessage(redirectAttributes, "删除文化成功");
		return "redirect:"+Global.getAdminPath()+"/hihunan/culture/hiCulture/?repage";
	}

}