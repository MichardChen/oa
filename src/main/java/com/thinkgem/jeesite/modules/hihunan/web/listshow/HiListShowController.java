/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hihunan.web.listshow;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.hihunan.entity.artical.HiArtical;
import com.thinkgem.jeesite.modules.hihunan.entity.listshow.HiListShow;
import com.thinkgem.jeesite.modules.hihunan.service.artical.HiArticalService;
import com.thinkgem.jeesite.modules.hihunan.service.listshow.HiListShowService;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 旅游Controller
 * @author lin
 * @version 2016-11-29
 */
@Controller
@RequestMapping(value = "${adminPath}/hihunan/listshow/hiListShow")
public class HiListShowController extends BaseController {

	@Autowired
	private HiListShowService hiListShowService;
	
	@Autowired
	private HiArticalService hiArticalService;
	
	@ModelAttribute
	public HiListShow get(@RequestParam(required=false) String id) {
		HiListShow entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hiListShowService.get(id);
		}
		if (entity == null){
			entity = new HiListShow();
		}
		return entity;
	}
	
	@RequiresPermissions("hihunan:listshow:hiListShow:view")
	@RequestMapping(value = {"list", ""})
	public String list(HiListShow hiListShow, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		Page<HiListShow> page = hiListShowService.findPage(new Page<HiListShow>(request, response), hiListShow); 
		model.addAttribute("page", page);
		
		List<Dict> listType = DictUtils.getDictList("hi_list_type");
		Iterator<Dict> listIterator = listType.iterator();
		while (listIterator.hasNext()) {//去掉空白的数据行
			Dict dict = listIterator.next();
			if ("top".equals(dict.getValue())){
				listIterator.remove();
			}
		}
		model.addAttribute("listType", listType);
		
		return "modules/hihunan/listshow/hiListShowList";
	}

	@RequiresPermissions("hihunan:listshow:hiListShow:view")
	@RequestMapping(value = "form")
	public String form(HiListShow hiListShow, Model model) {
		model.addAttribute("hiListShow", hiListShow);
		//hiListShow ID
		model.addAttribute("hiListS·howId", hiListShow.getId());
		//文章
		HiArtical hiArtical = new HiArtical();
		model.addAttribute("hiArtical", hiArtical);
		/*List<HiArtical> articalList = null;
		// 检测是否为修改模式。
		if(StringUtils.isNotBlank(hiListShow.getId())){
			//获取该主题的文章列表信息。
			HiArtical parm = new HiArtical();
			parm.setParentId(hiListShow.getId());
			articalList = hiArticalService.findList(parm);
			// 统一起见，用null标示没有数据。
			if(articalList.size() == 0){
				articalList = null;
			}
		}
		
		model.addAttribute("articalList",articalList);
		// 分类的下拉框
		List<Dict> listType = DictUtils.getDictList("hi_list_type");
		Iterator<Dict> listIterator = listType.iterator();
		while (listIterator.hasNext()) {//去掉空白的数据行
			Dict dict = listIterator.next();
			if ("local".equals(dict.getValue()) || "restaurant".equals(dict.getValue()) || "top".equals(dict.getValue())){
				listIterator.remove();
			}
		}
		model.addAttribute("listType", listType);
		// 设置保存后的信息。
		model.addAttribute("message", hiListShow.getMessage());*/
		return "modules/hihunan/listshow/hiListShowForm";
	}

	@RequiresPermissions("hihunan:listshow:hiListShow:edit")
	@RequestMapping(value = "save")
	public String save(HiListShow hiListShow, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, hiListShow)){
			return form(hiListShow, model);
		}
		/*String insertFlag = "0"; // 默认修改
		if(StringUtils.isBlank(hiListShow.getId())){
			insertFlag = "1";    // 新增
		}*/
		
		// 替换  /hihunan/userfiles  为 http://XXXXXX:XX/hihunan/userfiles
		hiListShow.setTitlePhoto(hiListShow.getTitlePhoto().replaceAll("/hihunan/userfiles", Global.getConfig("adminUrl")+"/hihunan/userfiles"));
		
		hiListShowService.save(hiListShow);
		addMessage(redirectAttributes, "保存成功");
		
		/*if("1".equals(insertFlag)){
			// 新增时，保存后进入修改页面以便增加文章。
			hiListShow.setMessage("保存成功,点击[添加文章]可以进行相应操作!");
			return form(hiListShow, model);
		}*/
		return "redirect:"+Global.getAdminPath()+"/hihunan/listshow/hiListShow/?repage";
	}
	
	@RequiresPermissions("hihunan:listshow:hiListShow:edit")
	@RequestMapping(value = "delete")
	public String delete(HiListShow hiListShow, RedirectAttributes redirectAttributes) {
		hiListShowService.delete(hiListShow);
		addMessage(redirectAttributes, "删除旅游成功");
		return "redirect:"+Global.getAdminPath()+"/hihunan/listshow/hiListShow/?repage";
	}

}