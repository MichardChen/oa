/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.thinkgem.jeesite.common.persistence.JQResultModel;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.JsonUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.data.service.WorkFlowUserService;
import com.thinkgem.jeesite.modules.sys.entity.SysParameter;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.model.SysParameterModel;
import com.thinkgem.jeesite.modules.sys.service.SysParameterService;
import com.thinkgem.jeesite.modules.sys.utils.SysParameterUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 系统参数Controller
 * @author ljx
 * @version 2016-05-10
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysParameter")
public class SysParameterController extends BaseController {

	@Autowired
	private SysParameterService sysParameterService;
	
	@Autowired
	private WorkFlowUserService workFlowUserService;
	
	@RequestMapping(value = { "init", "" })
	public String init(SysParameter sysParameter,Model model){
		// 根据js文件名获取版本号。
		model.addAttribute("jsVersion", UserUtils.getVersionFmMenu("sysParameter.js"));
		return "modules/sys/sysParameter";
	}
	
	@RequestMapping(value = {"list"})
	@ResponseBody
	public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
		SysParameter sysParameter = (SysParameter)JsonUtils.getBean(request,SysParameter.class);
		List<SysParameterModel> list = sysParameterService.findByPage(sysParameter);
		JQResultModel resultModel = new JQResultModel(list, sysParameter.getPage());
		return JSON.toJSONString(resultModel);
	}
	
	//通过关键字查询对应数据信息
	@RequestMapping(value = {"getByKeyword"})
	@ResponseBody
	public String getBykeyword(HttpServletRequest request,HttpServletResponse response){
		String keyword = request.getParameter("keyword");
		JQResultModel resultModel = new JQResultModel();
		SysParameterModel sysParameterModel = sysParameterService.findByKeyword(keyword);
		if(sysParameterModel != null){
			resultModel.setResult(0);
			resultModel.setUserdata(sysParameterModel);
		}else{
			resultModel.setResult(-1);
			resultModel.setMsg("获取详细数据失败,请与管理员联系");
		}
		return JSON.toJSONString(resultModel);
	}
	
	/**更新参数值**/
	@RequestMapping(value = {"edit"})
	@ResponseBody
	public String edit(SysParameter sysParameter){
		JQResultModel resultModel = new JQResultModel();
		
		try{
			sysParameterService.save(sysParameter);
			// 关键：清除此关键字的缓存。
			CacheUtils.remove(SysParameterUtils.SysParameter_CACHE, SysParameterUtils.SysParameter_CACHE_KEY_ + sysParameter.getKeyword());
			
			resultModel.setResult(0);
			resultModel.setMsg("保存成功");
		}
		catch(Exception ex){
			resultModel.setResult(-2);
			resultModel.setMsg("保存失败,请与管理员联系");
		}
		
		return JSON.toJSONString(resultModel);
	}
	
	@RequestMapping(value={"clearUserToken"})
	@ResponseBody
	public String clearUserToken(HttpServletRequest request,HttpServletResponse response){
		JQResultModel resultModel = new JQResultModel();
		String loginName = request.getParameter("loginName");
		User user = new User();
		user.setLoginName(loginName);
		int flag = workFlowUserService.clearToken(user);
		if(flag == 0){
			resultModel.setResult(0);
			resultModel.setMsg("操作成功");
		}else{
			resultModel.setResult(-1);
			resultModel.setMsg("操作失败，请稍后重试");
		}
		return JSON.toJSONString(resultModel);
	}
	
}
