package com.thinkgem.jeesite.modules.sys.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.persistence.JQResultModel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.constructcost.member.service.MemberuserService;
import com.thinkgem.jeesite.modules.sys.entity.Member;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.SysWfAssign;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.model.OfficeModel;
import com.thinkgem.jeesite.modules.sys.model.RoleModel;
import com.thinkgem.jeesite.modules.sys.model.SysWfAssignModel;
import com.thinkgem.jeesite.modules.sys.model.UserModel;
import com.thinkgem.jeesite.modules.sys.service.SysWfAssignService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.pj_common.Constants.PjConstants;

/**
 * 工作流程人员(角色)分配Controller
 * 
 * @author chen_qiancheng
 * @since 2015-11-13
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/wfAssign")
public class WfAssignController extends BaseController {
	@Autowired
	private SysWfAssignService sysWfAssignService; 
	
	//初始化
	@RequestMapping(value = { "init",""})
	public String init(HttpServletRequest request,
			HttpServletResponse response, Model model){
		// 根据js文件名获取版本号。
		model.addAttribute("jsVersion", UserUtils.getVersionFmMenu("wfAssign.js"));
		return "modules/sys/wfAssign";
	}
	
	//删除数据
	@RequestMapping(value = { "deleteUser" })
	@ResponseBody
	public String deleteUser(@RequestParam("rowDataIdFmDB")String[] rowDataIdFmDB,HttpServletResponse response,User user)
		throws ValidationException {
		
		return "success";
	}
	
	//更新或者插入数据
	@RequestMapping(value = { "edit" })
	@ResponseBody
	public String save(User user, RedirectAttributes redirectAttributes,HttpServletRequest request,HttpSession httpSession)
			throws ValidationException {
		
			return "error";
	}
	
	//查询数据(流程定义信息)
	@RequestMapping(value = { "searchList" })
	@ResponseBody
	public String searchList(HttpServletRequest request, HttpServletResponse response,String id,Model model)
			throws ValidationException {
		
		SysWfAssign sysWfAssign = new SysWfAssign();
		
		List<SysWfAssignModel> list = sysWfAssignService.findProcModels(sysWfAssign);
		
		JQResultModel resultModel = new JQResultModel(list, UserUtils.getUser().getPage());
		
		return JSON.toJSONString(resultModel);
	}
	
	//查询数据（根据流程定义，流程人员配置信息）
	@RequestMapping(value = { "searchListWfAssign" })
	@ResponseBody
	public String searchListWfAssign(HttpServletRequest request, HttpServletResponse response,String id,Model model)
			throws ValidationException {
		
		SysWfAssign sysWfAssign = new SysWfAssign();
		String procDefId = request.getParameter("procDefId");
		sysWfAssign.setProcDefId(procDefId);
		
		List<SysWfAssignModel> list = sysWfAssignService.findModels(sysWfAssign);
		
		JQResultModel resultModel = new JQResultModel(list, UserUtils.getUser().getPage());
		
		return JSON.toJSONString(resultModel);
	}
}
