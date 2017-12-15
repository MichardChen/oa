/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hihunan.web.hih5app;

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
import com.thinkgem.jeesite.modules.hihunan.service.h5app.HiH5appService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 应用Controller
 * @author lin
 * @version 2016-11-30
 */
@Controller
@RequestMapping(value = "${adminPath}/hihunan/hih5app/hiH5app")
public class HiH5appController extends BaseController {

	@Autowired
	private HiH5appService hiH5appService;
	
	@ModelAttribute
	public HiH5app get(@RequestParam(required=false) String id) {
		HiH5app entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hiH5appService.get(id);
		}
		if (entity == null){
			entity = new HiH5app();
		}
		return entity;
	}
	
	@RequiresPermissions("hihunan:hih5app:hiH5app:view")
	@RequestMapping(value = {"list", ""})
	public String list(HiH5app hiH5app, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<HiH5app> page = hiH5appService.findPage(new Page<HiH5app>(request, response), hiH5app); 
		model.addAttribute("page", page);
		return "modules/hihunan/hih5app/hiH5appList";
	}

	@RequiresPermissions("hihunan:hih5app:hiH5app:view")
	@RequestMapping(value = "form")
	public String form(HiH5app hiH5app, Model model) {
		model.addAttribute("hiH5app", hiH5app);
		model.addAttribute("listType", DictUtils.getDictList("hi_list_type"));
		return "modules/hihunan/hih5app/hiH5appForm";
	}

	@RequiresPermissions("hihunan:hih5app:hiH5app:edit")
	@RequestMapping(value = "save")
	public String save(HiH5app hiH5app, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, hiH5app)){
			return form(hiH5app, model);
		}
		// 替换  /hihunan/userfiles  为 http://XXXXXX:XX/hihunan/userfiles
		hiH5app.setIcon(hiH5app.getIcon().replaceAll("/hihunan/userfiles", Global.getConfig("adminUrl")+"/hihunan/userfiles"));
		
		hiH5appService.save(hiH5app);
		addMessage(redirectAttributes, "保存应用成功");
		return "redirect:"+Global.getAdminPath()+"/hihunan/hih5app/hiH5app/?repage";
	}
	
	@RequiresPermissions("hihunan:hih5app:hiH5app:edit")
	@RequestMapping(value = "delete")
	public String delete(HiH5app hiH5app, RedirectAttributes redirectAttributes) {
		hiH5appService.delete(hiH5app);
		addMessage(redirectAttributes, "删除应用成功");
		return "redirect:"+Global.getAdminPath()+"/hihunan/hih5app/hiH5app/?repage";
	}

}