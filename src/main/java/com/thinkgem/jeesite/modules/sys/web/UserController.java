/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.fastweixin.company.api.QYUserAPI;
import com.fastweixin.company.api.config.QYAPIConfig;
import com.fastweixin.company.api.entity.QYUser;
import com.fastweixin.company.api.enums.QYResultType;
import com.fastweixin.company.api.response.GetQYUserInfo4DepartmentResponse;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.JQResultModel;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.SendWxqyMsgUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.data.service.WorkFlowUserService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.model.RoleModel;
import com.thinkgem.jeesite.modules.sys.model.SysParameterModel;
import com.thinkgem.jeesite.modules.sys.service.MemberService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.OfficeUtils;
import com.thinkgem.jeesite.modules.sys.utils.RoleUtils;
import com.thinkgem.jeesite.modules.sys.utils.SysParameterUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.pj_common.persistence.ns.dao.NsPartnerCompanyDao;

/**
 * 用户Controller
 * @author ThinkGem
 * @version 2013-8-29
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/user")
public class UserController extends BaseController {

	@Autowired
	private SystemService systemService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private NsPartnerCompanyDao nsPartnerCompanyDao;
	
	@Autowired
	private WorkFlowUserService workFlowUserService;
	
	@Autowired
	private ActTaskService actTaskService;
	
	@ModelAttribute
	public User get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return systemService.getUser(id);
		}else{
			return new User();
		}
	}

	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = {"index"})
	public String index(User user, Model model) {
		return "modules/sys/userIndex";
	}

	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = {"list", ""})
	public String list(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		// 2016-7-13 对于管理员，不预先设定公司和部门。。对于其他人员，则设定公司和部门。
		if(!UserUtils.isAdmin(UserUtils.getUser().getId())){
		
			if (user.getCompany()==null || user.getCompany().getId()==null){
				user.setCompany(UserUtils.getUser().getCompany());
			}
			if (user.getOffice()==null || user.getOffice().getId()==null){
				user.setOffice(UserUtils.getUser().getOffice());
			}
		}
		
		model.addAttribute("user", user);
		
		//2016-5-26 定制画面上显示的角色类表
		SysParameterModel sysParameterModel = new SysParameterModel();
		sysParameterModel = SysParameterUtils.findKeyword("systemName");
		if("hihunan".equals(sysParameterModel.getValue1())){
			//如果为巡检系统，则定制allRoles的获取功能。
			model.addAttribute("allRoles", getAllRolesForAtmInspect());
			
			// 2016-7-5  判断是否能显示用户编辑的按钮(链接)等。系统管理员和公司负责人允许编辑。
			if(UserUtils.isAdmin(UserUtils.getUser().getId()) ||
			   "1".equals(UserUtils.getUser().getPrimaryPersonSign())) {
				model.addAttribute("modifyUserSign", "1"); // "1"--可以编辑用户信息， "0"--不允许编辑
			}
			// 2016-7-13 是否为部门负责人。
			model.addAttribute("primaryPersonSign",UserUtils.getUser().getPrimaryPersonSign());    // 2016-7-13 是否为部门负责人。"1"--负责人   "0"-- 非负责人
			model.addAttribute("isAdmin",UserUtils.isAdmin(UserUtils.getUser().getId())?"1":"0" ); // 2016-7-13 是否为管理员。"1"--管理员  "0"-- 非管理员 
			
		}else{
			// 原框架的代码。
			model.addAttribute("allRoles", systemService.findAllRole());
		}
		// 20151209 增加会员列表。
		model.addAttribute("memberList",memberService.findAllMember());
		
		Page<User> page = systemService.findUser(new Page<User>(request, response), user);
        model.addAttribute("page", page);
         
		return "modules/sys/userList";
	}

	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "form")
	public String form(User user, Model model) {
		if (user.getCompany()==null || user.getCompany().getId()==null){
			user.setCompany(UserUtils.getUser().getCompany());
		}
		if (user.getOffice()==null || user.getOffice().getId()==null){
			user.setOffice(UserUtils.getUser().getOffice());
		}
		model.addAttribute("user", user);
		model.addAttribute("allRoles", systemService.findAllRole());
		// 20151209 增加会员列表。
		model.addAttribute("memberList",memberService.findAllMember());
		
		return "modules/sys/userForm";
	}
	
	/**[巡检系统专用函数]为巡检系统中外协公司管理员设可配置的角色。
	 * */
	private List<Role> getAllRolesForAtmInspect(){
		
		List<Role> list = new ArrayList<Role>();
		
		// 判断是否为超级管理员(super角色)
		if(UserUtils.isAdmin(UserUtils.getUser().getId())){
			return systemService.findAllRole();
		}
		
		// 判断登录者是否为某部门的负责人
		if("1".equals(UserUtils.getUser().getPrimaryPersonSign())){
			//获取登录者的部门，然后获取对应相关单位设置的角色key列表。
			String roleKeys = nsPartnerCompanyDao.getRoleKeysByOfficeId(UserUtils.getUser().getOffice().getId());
			if(StringUtils.isBlank(roleKeys)){
				return list; // 没有设定角色，返回空list.
			}
			//根据角色key，整理成List<Role>。即  net,repair,整理成 List<Role>
			String[] roleArray = roleKeys.split(",");
			for(String s:roleArray){
				if(s.length() > 0){ // 排除空元素。
					Role role = RoleUtils.getByEname(s);
					list.add(role);
				}
			}
		}
		
		return list;
		
	}
	
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "updateUser")
	@ResponseBody
	public String updateUser(@RequestParam(required=false) String id){
		User user = systemService.getUser(id);
		// 2016-7-8 删除。这些代码没用到。
		//if (user.getCompany()==null || user.getCompany().getId()==null){
		//	user.setCompany(UserUtils.getUser().getCompany());
		//}
		//if (user.getOffice()==null || user.getOffice().getId()==null){
		//	user.setOffice(UserUtils.getUser().getOffice());
		//} 2016-7-8
		List<Role> list = user.getRoleList();
		JQResultModel resultModel = new JQResultModel();
		resultModel.setRows(list);
		resultModel.setUserdata(user);
		return JSON.toJSONString(resultModel);
	}

	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "save")
	public String save(User user, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		// 修正引用赋值问题，不知道为何，Company和Office引用的一个实例地址，修改了一个，另外一个跟着修改。
		user.setCompany(new Office(request.getParameter("company.id")));
		user.setOffice(new Office(request.getParameter("office.id")));
		// 如果新密码为空，则不更换密码
		if (StringUtils.isNotBlank(user.getNewPassword())) {
			user.setPassword(SystemService.entryptPassword(user.getNewPassword()));
		}
		if (!beanValidator(model, user)){
			return form(user, model);
		}
		if (!"true".equals(checkLoginName(user.getOldLoginName(), user.getLoginName()))){
			addMessage(model, "保存用户'" + user.getLoginName() + "'失败，登录名已存在");
			return form(user, model);
		}
		if (!"true".equals(checkMobile(user.getOldMobile(), user.getMobile()))){
			addMessage(model, "保存用户'" + user.getLoginName() + "'失败，手机号已存在");
			return form(user, model);
		}
		// 角色数据有效性验证，过滤不在授权内的角色
		List<Role> roleList = Lists.newArrayList();
		List<String> roleIdList = user.getRoleIdList();
		// 判断是否巡检系统
		SysParameterModel sysParameterModel = new SysParameterModel();
		sysParameterModel = SysParameterUtils.findKeyword("systemName");
		if("atminspect".equals(sysParameterModel.getValue1())){
			//如果为巡检系统，则定制allRoles的获取功能.根据roleIdList中的RoleId来获取Role的列表。
			for (String roleId: roleIdList){
				roleList.add(RoleUtils.get(roleId));
			}
		}
		else{
			// 原框架的逻辑。
			for (Role r : systemService.findAllRole()){
				if (roleIdList.contains(r.getId())){
					roleList.add(r);
				}
			}
		}
		// 设置该用户拥有的角色列表。
		user.setRoleList(roleList);
		
		// 保存用户信息
		systemService.saveUser(user);
		// 新增时，并且，同步到微信企业号
//		String wxqyMsg = ""; // 微信企业号对应的信息。
//		// 当用户勾选了绑定，那么 自动添加到企业号的通讯录中，并且绑定。如果
//		if("1".equals(user.getAddToWxUserId())){
//			// 1)通过部门id获取对应的微信企业号中部门ID.
//			Office tmpOffice = OfficeUtils.get(user.getOffice().getId());
//			String wxqyDept = "";
//			if(tmpOffice == null){
//				wxqyDept = "";   }
//			else{
//				if(tmpOffice.getWxqyDept() == null){
//					wxqyDept = "";
//				}
//				else{
//					wxqyDept = tmpOffice.getWxqyDept().getId();
//				}
//			}
//			// 2)如果没有绑定，则自动绑定。
//			if(StringUtils.isNotBlank(wxqyDept)){ // 还未绑定，则自动添加到企业号通讯录。
//				// 设置部门的id(企业号用)
//				Integer[] dept = new Integer[1];
//				dept[0] = Integer.valueOf(wxqyDept); // 只增加到一个部门里。
//				QYResultType rst = addToWxqyUser(user.getWxUserId(), dept, user.getName(), user.getMobile());
//				if(QYResultType.SUCCESS == rst || QYResultType.USER_ID_MORE_THAN_ONE == rst
//						                       || QYResultType.MOBILE_MORE_THAN_ONE  == rst ){
//					  // 添加成功后(或者 UserId/手机号已存在于企业号的通讯录中)，更新绑定情况。
//					systemService.updateWxqyUserId(user.getId(), user.getWxUserId());
//				}
//				else{ // 其他场景,弹出错误信息。
//					wxqyMsg = "另外，添加到企业号通讯录中不成功。请联系管理员处理。错误：" + rst;
//				}
//			}
//			else{ // 成员所在的部门没有绑定到微信企业号
//				wxqyMsg = "另外，添加到企业号通讯录中不成功。请联系管理员处理。错误：成员所在的部门没有绑定到微信企业号。";
//			}
//			// 3)同步用户的姓名等基础信息。
//			QYUser qyUser = new QYUser();
//			qyUser.setUserId(user.getWxUserId()); // 选择企业号的成员。
//			// 2016-9-22去除更新部门的信息，因为用户可能归属多个部门。
//			//Integer[] department = {}; //归属部门
//			//department[0] = Integer.valueOf(wxqyDept); //目前只归属一个部门
//			//qyUser.setDepartment(department);
//			qyUser.setMobile(user.getMobile()); //手机号
//			qyUser.setName(user.getName());     //姓名
//			
//			QYResultType rst = updWxqyUser(qyUser);
//			if( rst != QYResultType.SUCCESS){ //禁用不成功。
//				wxqyMsg = "另外，企业号通讯录中更新成员不成功。请联系管理员处理。错误：" + rst;
//			}
//		}
//		// 当用户不勾选(注意值不为1，不一定是0)或者用户的状态变成不可用时，解除绑定。
//		if(!"1".equals(user.getAddToWxUserId()) || "0".equals(user.getLoginFlag())){
//			systemService.updateWxqyUserId(user.getId(), "");
//			// 如果用户的状态变成不可用，则从微信企业号的通讯录中设置禁止。
//			if("0".equals(user.getLoginFlag())){
//				QYUser qyUser = new QYUser();
//				qyUser.setUserId(user.getWxUserId()); // 选择企业号的成员。
//				// qyUser.setEnable("0");             // 0 ---- 禁用该成员。
//				QYResultType rst = delWxqyUser(qyUser);
//				if( rst != QYResultType.SUCCESS){ //禁用不成功。
//					wxqyMsg = "另外，企业号通讯录中删除此成员不成功。请联系管理员处理。错误：" + rst;
//				}
//			}
//		}
		// 清除当前用户缓存
		//if (user.getLoginName().equals(UserUtils.getUser().getLoginName())){
		//	UserUtils.clearCache();
		//	//UserUtils.getCacheMap().clear();
		//}
		UserUtils.clearCache(user); // 清除指定用户的缓存。
		
		//addMessage(redirectAttributes, "保存用户'" + user.getLoginName() + "'成功!" + wxqyMsg);
		
		addMessage(redirectAttributes, "保存用户'" + user.getLoginName());
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}
	
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "delete")
	public String delete(User user, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		
		if(actTaskService.isAssignee(user.getId())){
			addMessage(redirectAttributes, "删除用户失败，该用户存在待办任务，如要继续删除，请将该用户的待办任务委托出去后再进行操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		
		if (UserUtils.getUser().getId().equals(user.getId())){
			addMessage(redirectAttributes, "删除用户失败, 不允许删除当前用户");
		}else if (UserUtils.isAdmin(user.getId())){
			addMessage(redirectAttributes, "删除用户失败, 不允许删除超级管理员用户");
		}else{
			systemService.deleteUser(user);
//			String wxqyMsg = ""; // 微信企业号对应的信息。
//			//如果绑定了weixin 企业号,则从企业号通讯录中删除
//			if(StringUtils.isNotBlank(user.getWxUserId())){
//				QYUser qyUser = new QYUser();
//				qyUser.setUserId(user.getWxUserId()); // 选择企业号的成员。
//				QYResultType rst = delWxqyUser(qyUser);
//				if( rst != QYResultType.SUCCESS){ //禁用不成功。
//					wxqyMsg = "另外，企业号通讯录中删除此成员不成功。请联系管理员处理。错误：" + rst;
//				}
//			}
			
			//addMessage(redirectAttributes, "删除用户成功！" + wxqyMsg);
			
			addMessage(redirectAttributes, "删除用户成功！");
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}
	
	/**
	 * 导出用户数据
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:view")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(User user, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户数据"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<User> page = systemService.findUser(new Page<User>(request, response, -1), user);
    		new ExportExcel("用户数据", User.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
    }

	/**
	 * 导入用户数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:edit")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<User> list = ei.getDataList(User.class);
			for (User user : list){
				try{
					if ("true".equals(checkLoginName("", user.getLoginName()))){
						user.setPassword(SystemService.entryptPassword("123456"));
						BeanValidators.validateWithException(validator, user);
						systemService.saveUser(user);
						successNum++;
					}else{
						failureMsg.append("<br/>登录名 "+user.getLoginName()+" 已存在; ");
						failureNum++;
					}
				}catch(ConstraintViolationException ex){
					failureMsg.append("<br/>登录名 "+user.getLoginName()+" 导入失败：");
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("<br/>登录名 "+user.getLoginName()+" 导入失败："+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条用户，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条用户"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入用户失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
    }
	
	/**
	 * 下载导入用户数据模板
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:view")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户数据导入模板.xlsx";
    		List<User> list = Lists.newArrayList(); list.add(UserUtils.getUser());
    		new ExportExcel("用户数据", User.class, 2).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
    }
	
	/**
	 * 验证微信号是否重复
	 * @param wxUserId
	 * @return
	 */
	@RequestMapping(value = "checkWxUserId")
	@ResponseBody
	public String checkWxUserId(HttpServletRequest request,HttpServletResponse response) {
		String wxUserId = request.getParameter("wxUserId");
		JQResultModel resultModel = new JQResultModel();
		User user = systemService.getByWxUserId(wxUserId);
		if (user != null) {
			resultModel.setResult(0);
			resultModel.setMsg("微信账号重复，是否继续？");
		}else{
			resultModel.setResult(-1);
		}
		return JSON.toJSONString(resultModel);
	}

	/**
	 * 验证登录名是否有效
	 * @param oldLoginName
	 * @param loginName
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "checkLoginName")
	public String checkLoginName(String oldLoginName, String loginName) {
		if (loginName !=null && loginName.equals(oldLoginName)) {
			// 如果 原登录名和 修改后登录名一致，则认为不重复。
			return "true";
		} else if (loginName !=null && systemService.getUserByLoginName(loginName) == null) {
			// 如果修改后登录名不存在，则认为不重复。
			return "true";
		}
		return "false";
	}

	/**
	 * 验证手机号是否有效  2016-7-5
	 * @param oldMobile
	 * @param mobile
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "checkMobile")
	public String checkMobile(String oldMobile, String mobile) {
		if (mobile !=null && mobile.equals(oldMobile)) {
			// 如果 原手机号和 修改后手机号一致，则认为不重复。
			return "true";
		} else if (mobile !=null && systemService.getUserByLoginName(mobile) == null) {
			// 如果修改后手机号不存在，则认为不重复。
			return "true";
		}
		return "false";
	}
	
	/**
	 * 用户信息显示及保存
	 * @param user
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "info")
	public String info(User user, HttpServletResponse response, Model model) {
		User currentUser = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getName())){
			if(Global.isDemoMode()){
				model.addAttribute("message", "演示模式，不允许操作！");
				return "modules/sys/userInfo";
			}
			currentUser.setEmail(user.getEmail());
			currentUser.setPhone(user.getPhone());
			currentUser.setMobile(user.getMobile());
			currentUser.setRemarks(user.getRemarks());
			currentUser.setPhoto(user.getPhoto());
			systemService.updateUserInfo(currentUser);
			model.addAttribute("message", "保存用户信息成功");
		}
		model.addAttribute("user", currentUser);
		model.addAttribute("Global", new Global());
		return "modules/sys/userInfo";
	}

	/**
	 * 返回用户信息
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "infoData")
	public User infoData() {
		return UserUtils.getUser();
	}
	
	/**
	 * 修改个人用户密码
	 * @param oldPassword
	 * @param newPassword
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "modifyPwd")
	public String modifyPwd(String oldPassword, String newPassword, Model model) {
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword)){
			if(Global.isDemoMode()){
				model.addAttribute("message", "演示模式，不允许操作！");
				return "modules/sys/userModifyPwd";
			}
			if (SystemService.validatePassword(oldPassword, user.getPassword())){
				systemService.updatePasswordById(user.getId(), user.getLoginName(), newPassword);
				model.addAttribute("message", "修改密码成功");
			}else{
				model.addAttribute("message", "修改密码失败，旧密码错误");
			}
		}
		model.addAttribute("user", user);
		return "modules/sys/userModifyPwd";
	}
	
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String officeId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<User> list = systemService.findUserByOfficeId(officeId);
		for (int i=0; i<list.size(); i++){
			User e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", "u_"+e.getId());
			map.put("pId", officeId);
			map.put("name", StringUtils.replace(e.getName(), " ", ""));
			mapList.add(map);
		}
		return mapList;
	}
    
	/*** 增加成员到微信企业号
	 * @param userId 用户账号（唯一）
	 * @param dept   部门归属  例如 [1,9]
	 * @param name   姓名
	 * @param mobile 手机号（唯一)
	 * @return "0" --正常
	 *         "0"以外--错误消息
	 * */
	public QYResultType addToWxqyUser(String userId,Integer[] department,String name,String mobile){
		
		QYAPIConfig apiConfig = QYAPIConfig.getInstance();
		QYUserAPI api = new QYUserAPI(apiConfig);
		QYUser user   = new QYUser();
		
		user.setUserId(userId);
		user.setDepartment(department);
		user.setName(name);
		user.setMobile(mobile);
		
		return api.create(user);
	}
	
	/*** 删除微信企业号的成员信息
	 * @param userId 用户账号（唯一）
	 * @return "0" --正常
	 *         "0"以外--错误消息
	 * */
	public QYResultType delWxqyUser(QYUser user){
		
		QYAPIConfig apiConfig = QYAPIConfig.getInstance();
		QYUserAPI api = new QYUserAPI(apiConfig);
		
		return api.delete(user.getUserId());
	}
	/*** 更新微信企业号的成员信息
	 * @param userId 用户账号（唯一）
	 * @return "0" --正常
	 *         "0"以外--错误消息
	 * */
	public QYResultType updWxqyUser(QYUser user){
		
		QYAPIConfig apiConfig = QYAPIConfig.getInstance();
		QYUserAPI api = new QYUserAPI(apiConfig);
		
		return api.update(user);
	}
	/**
	 * 根据系统的机构id,来获取对应微信企业号中的部门下的用户列表。
	 * @param officeId：部门id
	 * @return ""  ---没值。
	 *         {:} ---微信企业号的UserList.
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "getWxQyUserListByOffice")
	public String getWxQyUserList(String officeId){
		
		List<QYUser> qyUserList = new ArrayList<QYUser>();
				
	    Office office = OfficeUtils.get(officeId);
	    if(office==null){  // officeId非法。
	    	qyUserList.add(new QYUser("","", null, "", "", "", "", "", null));
	    	return JSON.toJSONString(qyUserList);
	    }
	    // 获取对应的微信企业号中的部门
	    String wxdept = office.getWxDeptId();
	    if(StringUtils.isBlank(wxdept)){
	    	// 该部门没有对应微信企业号的部门
	    	qyUserList.add(new QYUser("","", null, "", "", "", "", "", null));
	    	return JSON.toJSONString(qyUserList);
	    }
	    
		QYAPIConfig apiConfig = QYAPIConfig.getInstance();
		QYUserAPI api = new QYUserAPI(apiConfig);
		
		GetQYUserInfo4DepartmentResponse resp = api.getList(Integer.valueOf(wxdept), true, 0);
		System.out.println(resp.getErrcode());
		System.out.println(resp.getErrmsg());
		
		qyUserList = resp.getUserList();
		// 增加一空行。
		qyUserList.add(new QYUser("","", null, "", "", "", "", "", null));
		
		return JSON.toJSONString(qyUserList);
	}
	
	/**
	 * 发送企业号信息
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "sendQyMsg")
	public String sendQyMsg(){
		
		String rst = SendWxqyMsgUtils.sendText("meng_linggang@xmhold.com", "撤机任务，请点击链接查看http://www.zhaojx.net:8090/XXX");
		
		//msg.setToUser();
		System.out.println(rst);
		//api.send(msg);
		
		return "";
	}
//	@InitBinder
//	public void initBinder(WebDataBinder b) {
//		b.registerCustomEditor(List.class, "roleList", new PropertyEditorSupport(){
//			@Autowired
//			private SystemService systemService;
//			@Override
//			public void setAsText(String text) throws IllegalArgumentException {
//				String[] ids = StringUtils.split(text, ",");
//				List<Role> roles = new ArrayList<Role>();
//				for (String id : ids) {
//					Role role = systemService.getRole(Long.valueOf(id));
//					roles.add(role);
//				}
//				setValue(roles);
//			}
//			@Override
//			public String getAsText() {
//				return Collections3.extractToString((List) getValue(), "id", ",");
//			}
//		});
//	}
	
	@RequestMapping(value = {"findRoleList"})
	@ResponseBody
	public String findRoleList(HttpServletRequest request,HttpServletResponse response){
		JQResultModel resultModel = new JQResultModel();
		String officeId = request.getParameter("officeId");
		List<RoleModel> list = workFlowUserService.findRoles(officeId);
		if(list != null && list.size() > 0){
			resultModel.setResult(0);
			resultModel.setRows(list);
		}else{
			resultModel.setResult(-1);
			resultModel.setMsg("获取部门角色列表失败,请确认该部门存在所属机构类别！");
		}
		return JSON.toJSONString(resultModel);
	}
}
