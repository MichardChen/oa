package com.thinkgem.jeesite.modules.constructcost.member.web;




import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.constructcost.member.service.MemberuserService;
import com.thinkgem.jeesite.modules.constructcost.member.service.RegisterService;
import com.thinkgem.jeesite.modules.sys.entity.Member;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.model.OfficeModel;
import com.thinkgem.jeesite.modules.sys.model.RoleModel;
import com.thinkgem.jeesite.modules.sys.model.UserModel;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.pj_common.Constants.PjConstants;

/**
 * 用户信息Controller
 * 
 * @author chen_qiancheng
 * @since 2015-11-13
 */
@Controller
@RequestMapping(value = "${adminPath}/member/memberuser")
public class MemberuserController extends BaseController {
	@Autowired
	private MemberuserService memberuserService; 
	@Autowired
	private RegisterService registerService;
	
	//初始化
	@RequestMapping(value = { "init",""})
	public String init(UserModel userModel,HttpServletRequest request,
			HttpServletResponse response, Model model,Role role){
		String loginId = UserUtils.getUser().getId();
		//得到所属部门的下拉框的值
		OfficeModel officeModel = memberuserService.getOfficeModelByLoginId(loginId);
		Map<String,String> officeMap = new HashMap<String,String>();
		officeMap.put(officeModel.getId(),officeModel.getName());
		String officeJsonString = JSON.toJSONString(officeMap);
		model.addAttribute("officeJsonString",officeJsonString);
		
		//获得角色下拉框的值
		List<RoleModel> roleList = new ArrayList<RoleModel>();
		if(UserUtils.isAdmin(UserUtils.getUser().getId())){
			// 获得角色下拉框的值（系统管理员）
			roleList = memberuserService.getRoleList();
		}
		else{ //获得角色下拉框的值（系统管理员除外）
			roleList = memberuserService.getRoleListForMember();	
		}
		
		Map<String,String> roleMap = new HashMap<String, String>();
		for(int i=0;i<roleList.size();i++){
			roleMap.put(roleList.get(i).getId(),roleList.get(i).getName());
		}
		model.addAttribute("roleList",JSON.toJSONString(roleMap));
		
		//获得当前登录用户的角色级别
		String userId = UserUtils.getUser().getId();
		String sysRoleEname = memberuserService.findSysRoleEnameByUserId(userId);
		model.addAttribute("sysRoleEname",sysRoleEname);
		return "modules/constructcost/member/memberList";
	}
	
	//删除数据
	@RequestMapping(value = { "deleteUser" })
	@ResponseBody
	public String deleteUser(@RequestParam("rowDataIdFmDB")String[] rowDataIdFmDB,HttpServletResponse response,User user)
		throws ValidationException {
		for(int i=0;i<rowDataIdFmDB.length;i++){
			user.setId(rowDataIdFmDB[i]);
			try {
				memberuserService.deleteUser(user);
			} catch (Exception e) {
				return "error";
			}
		}
		return "success";
	}
	
	//更新或者插入数据
	@RequestMapping(value = { "edit" })
	@ResponseBody
	public String save(User user, RedirectAttributes redirectAttributes,HttpServletRequest request,HttpSession httpSession)
			throws ValidationException {
		
		String memberId = (String)request.getSession().getAttribute(PjConstants.CUR_COMPANY_ID);
		String companyId = UserUtils.getUser().getCompany().getId();
		//String officeId  = UserUtils.getUser().getOffice().getId();
		String userId = request.getParameter("id");
		String name = request.getParameter("name");
		String loginName = request.getParameter("loginName");
		String officeId = request.getParameter("officeId");
		String roleId = request.getParameter("role");
		String mobile = request.getParameter("mobile");
		String loginFlag = request.getParameter("loginFlag");
		String idFmDB = request.getParameter("idFmDB");
		String qq = request.getParameter("qq");
		String insertFlag = request.getParameter("insertFlag");
		//String oldPassword = request.getParameter("oldPassword");
		//验证登录名是否重复
		String logflag=registerService.checkLoginName(loginName);
		if(StringUtils.endsWith("true", logflag)){
			String mbflag=registerService.checkMobile(mobile);
			if(StringUtils.endsWith("true", mbflag)){
				Role role = new Role();
				role.setId(roleId);
				user.getRoleList().add(role);
				
				user.setMember(new Member(memberId));
				user.setCompany(new Office(companyId));
				user.setName(name);
				user.setLoginName(loginName);
				//user.setPassword(oldPassword);
				user.setOffice(new Office(officeId));
				user.setMobile(mobile);
				user.setLoginFlag(loginFlag);
				user.setQq(qq);
				user.setId(userId);
				user.setIdFmDB(idFmDB);
				//插入时，将userID设置为空
				if("1".equals(insertFlag)){
					user.setId("");
				}
				try {
					String returnIdFmDB = memberuserService.saveUser(user);
					return returnIdFmDB;
				} catch (Exception e) {
					return "error";
				}
			}else{
				return "手机号重复,添加失败";
			}
		}else{
			return "登录名重复,添加失败";
		}
	}
	
	//查询数据
	@RequestMapping(value = { "searchList" })
	@ResponseBody
	public String searchList(HttpServletRequest request, HttpServletResponse response,String id,Model model)
			throws ValidationException {
		//权限控制
		java.util.List<UserModel> memberuserList = new ArrayList<UserModel>();
		String companyId = (String)request.getSession().getAttribute(PjConstants.CUR_COMPANY_ID);
		String loginId = UserUtils.getUser().getId();
		if("company".equals(memberuserService.getEnnameByLoginId(loginId))){
			 memberuserList = memberuserService.getMemberuserListByCompanyId(companyId);
		}else{
			memberuserList = memberuserService.getMemberuserListByLoginId(loginId);
		}
		
		for(int i= 0;i<memberuserList.size();i++){
			String passWord = memberuserList.get(i).getPassword();
			memberuserList.get(i).setOldPassword(passWord);
			if("".equals(memberuserList.get(i).getWechatOpenId()) || memberuserList.get(i).getWechatOpenId() == null ){
				memberuserList.get(i).setWechatOpenId("未绑定");
			}else{
				memberuserList.get(i).setWechatOpenId("已绑定");
			}
		}
		User user = UserUtils.getUser();
		JQResultModel resultModel = new JQResultModel(memberuserList, user
				.getPage());
		return JSON.toJSONString(resultModel);
	}
	
}
