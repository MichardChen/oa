/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hihunan.web.artical;

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
import com.thinkgem.jeesite.modules.hihunan.entity.artical.HiArticalDetail;
import com.thinkgem.jeesite.modules.hihunan.service.artical.HiArticalDetailService;
import com.thinkgem.jeesite.modules.hihunan.service.artical.HiArticalService;

/**
 * 文章明细管理Controller
 * @author llin
 * @version 2016-12-01
 */
@Controller
@RequestMapping(value = "${adminPath}/hihunan/artical/hiArticalDetail")
public class HiArticalDetailController extends BaseController {

	@Autowired
	private HiArticalDetailService hiArticalDetailService;
	
	@ModelAttribute
	public HiArticalDetail get(@RequestParam(required=false) String id) {
		HiArticalDetail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hiArticalDetailService.get(id);
		}
		if (entity == null){
			entity = new HiArticalDetail();
		}
		return entity;
	}
	
	@RequiresPermissions("hihunan:artical:hiArticalDetail:view")
	@RequestMapping(value = {"list", ""})
	public String list(HiArticalDetail hiArticalDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<HiArticalDetail> page = hiArticalDetailService.findPage(new Page<HiArticalDetail>(request, response), hiArticalDetail); 
		model.addAttribute("page", page);
		return "modules/hihunan/artical/hiArticalDetailList";
	}

	@RequiresPermissions("hihunan:artical:hiArticalDetail:view")
	@RequestMapping(value = "form")
	public String form(HiArticalDetail hiArticalDetail, Model model) {
		model.addAttribute("hiArticalDetail", hiArticalDetail);
		model.addAttribute("HiArticalList", hiArticalDetailService.getHiArticalList());
		return "modules/hihunan/artical/hiArticalDetailForm";
	}

	@RequiresPermissions("hihunan:artical:hiArticalDetail:edit")
	@RequestMapping(value = "save")
	public String save(HiArticalDetail hiArticalDetail, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, hiArticalDetail)){
			return form(hiArticalDetail, model);
		}
		hiArticalDetailService.save(hiArticalDetail);
		addMessage(redirectAttributes, "保存文章成功");
		return "redirect:"+Global.getAdminPath()+"/hihunan/artical/hiArticalDetail/?repage";
	}
	
	@RequiresPermissions("hihunan:artical:hiArticalDetail:edit")
	@RequestMapping(value = "delete")
	public String delete(HiArticalDetail hiArticalDetail, RedirectAttributes redirectAttributes) {
		hiArticalDetailService.delete(hiArticalDetail);
		addMessage(redirectAttributes, "删除文章成功");
		return "redirect:"+Global.getAdminPath()+"/hihunan/artical/hiArticalDetail/?repage";
	}

}