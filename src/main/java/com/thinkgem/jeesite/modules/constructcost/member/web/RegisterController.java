package com.thinkgem.jeesite.modules.constructcost.member.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.constructcost.member.model.RegisterModel;
import com.thinkgem.jeesite.modules.sys.model.DictModel;
import com.thinkgem.jeesite.modules.sys.service.AreaService;


/**
 * 注册用户的controller层
 * 
 * @author yuyabiao
 * 
 */
@Controller
@RequestMapping(value = "${adminPath}/member/register")
public class RegisterController extends BaseController {

	@Autowired
	private AreaService areaService;

	@RequestMapping(value = { "init", "" })
	public String init(RegisterModel registerModel, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		List<DictModel> cityList = areaService.findCity();
		model.addAttribute("registerModel", registerModel);
		model.addAttribute("cityList", cityList);
		return "modules/constructcost/member/register";
	}
	
}
