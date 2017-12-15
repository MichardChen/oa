/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.fastweixin.company.api.QYDepartmentAPI;
import com.fastweixin.company.api.config.QYAPIConfig;
import com.fastweixin.company.api.entity.QYDepartment;
import com.fastweixin.company.api.response.GetDepartmentListResponse;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.SysWxqyDept;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.model.SysParameterModel;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.service.SysWxqyDeptService;
import com.thinkgem.jeesite.modules.sys.utils.SysParameterUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 机构Controller
 * @author ThinkGem
 * @version 2013-5-15
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/office")
public class OfficeController extends BaseController {

	@Autowired
	private OfficeService officeService;
	
	@Autowired
	private SysWxqyDeptService sysWxqyDeptService;
	
	@ModelAttribute("office")
	public Office get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return officeService.get(id);
		}else{
			return new Office();
		}
	}

	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = {""})
	public String index(Office office, Model model) {
//        model.addAttribute("list", officeService.findAll());
		return "modules/sys/officeIndex";
	}

	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = {"list"})
	public String list(Office office, Model model) {
		User user = UserUtils.getUser();
		if (office.getParent()==null || office.getParent().getId()==null){
			office.setParent(user.getOffice());
		}
		office.setParent(officeService.get(office.getParent().getId()));
		if (office.getArea()==null){
			office.setArea(user.getOffice().getArea());
		}
		// 自动获取排序号
		if (StringUtils.isBlank(office.getId())&&office.getParent()!=null){
			int size = 0;
			List<Office> list = officeService.findAll();
			for (int i=0; i<list.size(); i++){
				Office e = list.get(i);
				if (e.getParent()!=null && e.getParent().getId()!=null
						&& e.getParent().getId().equals(office.getParent().getId())){
					size++;
				}
			}
			office.setCode(office.getParent().getCode() + StringUtils.leftPad(String.valueOf(size > 0 ? size+1 : 1), 3, "0"));
		}
		model.addAttribute("office", office);
        model.addAttribute("list", officeService.findList(office));
        
        // 2016-5-13 增加从企业号后台获取的 机构列表
        //Integer parentId = null; // 含义：全部部门
        //List<QYDepartment> wxqyDeptList= syncWxQiyeOffcieInfo(parentId);
        // 增加一条空记录。
        //wxqyDeptList.add(new QYDepartment(0,"",0,0));
        //System.out.println(JSON.toJSONString(wxqyDeptList));
        //model.addAttribute("wxqyDeptList", wxqyDeptList);
        // 如果系统参数中需要 更新 数据库中的企业号部门数据，则调用。
        SysParameterModel sysParameterModel = SysParameterUtils.findKeyword("refreshWxqyDept");
        if("1".equals(sysParameterModel.getValue1())){
        	updWxQiyeDept();
        }
        
		return "modules/sys/officeList";
	}
	
	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = "form")
	public String form(Office office, Model model) {
		User user = UserUtils.getUser();
		if (office.getParent()==null || office.getParent().getId()==null){
			office.setParent(user.getOffice());
		}
		office.setParent(officeService.get(office.getParent().getId()));
		if (office.getArea()==null){
			office.setArea(user.getOffice().getArea());
		}
		// 自动获取排序号
		if (StringUtils.isBlank(office.getId())&&office.getParent()!=null){
			int size = 0;
			List<Office> list = officeService.findAll();
			for (int i=0; i<list.size(); i++){
				Office e = list.get(i);
				if (e.getParent()!=null && e.getParent().getId()!=null
						&& e.getParent().getId().equals(office.getParent().getId())){
					size++;
				}
			}
			office.setCode(office.getParent().getCode() + StringUtils.leftPad(String.valueOf(size > 0 ? size+1 : 1), 3, "0"));
		}
		model.addAttribute("office", office);
		return "modules/sys/officeForm";
	}
	
	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = "updateOffice")
	@ResponseBody
	public String  updateOffice(@RequestParam(required=false) String id){
		Office office = officeService.get(id);
		return JSON.toJSONString(office);
	}
	
	@RequiresPermissions("sys:office:edit")
	@RequestMapping(value = "save")
	public String save(Office office, Model model, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/office/";
		}
		if (!beanValidator(model, office)){
			return form(office, model);
		}
		if (!"true".equals(checkName(office.getOldName(), office.getName()))){
			addMessage(model, "保存用户'" + office.getName() + "'失败，机构名已存在");
			return form(office, model);
		}
		officeService.save(office);
		
		// 2016-6-30  去除“快速添加下级部门的功能” 开始
		//if(office.getChildDeptList()!=null){
		//	Office childOffice = null;
		//	for(String id : office.getChildDeptList()){
		//		childOffice = new Office();
		//		childOffice.setName(DictUtils.getDictLabel(id, "sys_office_common", "未知"));
		//		childOffice.setParent(office);
		//		childOffice.setArea(office.getArea());
		//		childOffice.setType("2");
		//		childOffice.setGrade(String.valueOf(Integer.valueOf(office.getGrade())+1));
		//		childOffice.setUseable(Global.YES);
		//		officeService.save(childOffice);
		//	}
		//}
		// 2016-6-30  去除“快速添加下级部门的功能” 结束
		
		addMessage(redirectAttributes, "保存机构'" + office.getName() + "'成功");
		//String id = "0".equals(office.getParentId()) ? "" : office.getParentId();
		return "redirect:" + adminPath + "/sys/office";
	}
	
	@RequiresPermissions("sys:office:edit")
	@RequestMapping(value = "delete")
	public String delete(Office office, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/office/list";
		}
//		if (Office.isRoot(id)){
//			addMessage(redirectAttributes, "删除机构失败, 不允许删除顶级机构或编号空");
//		}else{
			officeService.delete(office);
			addMessage(redirectAttributes, "删除机构成功");
//		}
//		return "redirect:" + adminPath + "/sys/office/list?id="+office.getParentId()+"&parentIds="+office.getParentIds();
			return "redirect:" + adminPath + "/sys/office";
	}

	/**
	 * 获取机构JSON数据。
	 * @param extId 排除的ID
	 * @param type	类型（1：公司；2：部门/小组/其它：3：用户）
	 * @param grade 显示级别
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, @RequestParam(required=false) String type,
			@RequestParam(required=false) Long grade, @RequestParam(required=false) Boolean isAll, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Office> list = officeService.findList(isAll);
		for (int i=0; i<list.size(); i++){
			Office e = list.get(i);
			if ((StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1))
					&& (type == null || (type != null && (type.equals("1") ? type.equals(e.getType()) : true)))
					&& (grade == null || (grade != null && Integer.parseInt(e.getGrade()) <= grade.intValue()))
					&& Global.YES.equals(e.getUseable())){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("pIds", e.getParentIds());
				map.put("name", e.getName());
				if (type != null && "3".equals(type)){
					map.put("isParent", true);
				}
				mapList.add(map);
			}
		}
		return mapList;
	}
	
	/**
	 * 获取机构JSON数据。(微信企业号)
	 * @param extId 排除的ID
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "wxqyTreeData")
	public List<Map<String, Object>> wxqyTreeData(@RequestParam(required=false) String extId,HttpServletResponse response) {
		
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<SysWxqyDept> list = sysWxqyDeptService.findAll();
		
		for (int i=0; i<list.size(); i++){
			SysWxqyDept e = list.get(i);
			if (StringUtils.isBlank(extId) ){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		
		return mapList;
	}
	
	/**
	 * 同步企业号的机构数据。
	 * http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E9%83%A8%E9%97%A8
	 * @param parentId为企业号中父部门的ID。如果传null,则代表获取根节点下的部门。
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "syncWxOffice")
	public List<QYDepartment> syncWxQiyeOffcieInfo(Integer parentId){
		
		GetDepartmentListResponse response = new GetDepartmentListResponse();
		
		try{
			QYAPIConfig apiConfig = QYAPIConfig.getInstance();
			QYDepartmentAPI departmentApi = new QYDepartmentAPI(apiConfig);
		
			response = departmentApi.getList(parentId);
		}
		catch(Exception ex){
			return null;
		}
		//for(QYDepartment dep:response.getDepartments()){
		//	System.out.println(dep.toJsonString());
		//	System.out.println(dep.getName());
		//}
		
		return response.getDepartments();
	}
	
	/**
	 * 获取企业号的最新数据，然后刷新到数据库(sys_wxqy_depart)。
	 * http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E9%83%A8%E9%97%A8
	 * @param parentId为企业号中父部门的ID。如果传null,则代表获取根节点下的部门。
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "updWxqyDept")
	public String updWxQiyeDept(){
		
		Integer parentId = null; // 含义：全部部门
        List<QYDepartment> wxqyDeptList= syncWxQiyeOffcieInfo(parentId);
        List<SysWxqyDept>  list = new ArrayList<SysWxqyDept>();
        // 获取到数据后，才进行以下工作。
        if(wxqyDeptList != null && wxqyDeptList.size()>0){
	        
        	for(QYDepartment dep:wxqyDeptList){
	        	SysWxqyDept item = new SysWxqyDept();
	        	item.setId(dep.getId().toString());
	        	item.setName(dep.getName());
	        	item.setSort(dep.getOrder());
	        	item.setParent(new SysWxqyDept(dep.getParentId().toString()));
	        	
	        	list.add(item);
	      	}
	        
	        sysWxqyDeptService.insertAll(list);
        }
        
		return "1";
	}
	
	/**
	 * 验证机构名称是否有效
	 * @param oldName
	 * @param name
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkName")
	public String checkName(String oldName, String name) {
		if (name !=null && name.equals(oldName)) {
			// 如果 原登录名和 修改后登录名一致，则认为不重复。
			return "true";
		} else if (name !=null && officeService.getByName(name) == null) {
			// 如果修改后登录名不存在，则认为不重复。
			return "true";
		}
		return "false";
	}
	
}
