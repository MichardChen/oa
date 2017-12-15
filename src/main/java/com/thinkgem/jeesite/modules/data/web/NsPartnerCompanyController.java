package com.thinkgem.jeesite.modules.data.web;

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
import com.thinkgem.jeesite.common.utils.JsonUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ns.data.model.NsPartnerCompanyModel;
import com.thinkgem.jeesite.modules.ns.data.service.NsPartnerCompanyService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.model.DictModel;
import com.thinkgem.jeesite.modules.sys.service.DictService;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.pj_common.persistence.ns.entity.NsPartnerCompany;
/**
 * 相关单位Controller,2016/5/10
 * @author yuyabiao
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/data/nsPartnerCompany")
public class NsPartnerCompanyController extends BaseController{
	
	@Autowired
	private NsPartnerCompanyService  nsPartnerCompanyService;
	
	@Autowired
	private OfficeService officeService;
	
	@Autowired
	private DictService dictService;
	
	@RequestMapping(value = { "init", "" })
	public String init(NsPartnerCompany nsPartnerCompany,Model model){
		List<DictModel> typeList = dictService.getListByCodeType("PartnerType");
		model.addAttribute("typeListString",JSON.toJSONString(typeList));
		model.addAttribute("typeList",typeList);
		List<Office> officeList = officeService.findAll();
		model.addAttribute("officeList", officeList);
		model.addAttribute("nsPartnerCompany", nsPartnerCompany);
		// 根据js文件名获取版本号。
		model.addAttribute("jsVersion", UserUtils.getVersionFmMenu("nsPartnerCompany.js"));
		return "modules/ns/data/nsPartnerCompany";
	}
	
	@RequestMapping(value = {"list"})
	@ResponseBody
	public String list(HttpServletRequest request, HttpServletResponse response){
		String type = request.getParameter("type");
		NsPartnerCompany nsPartnerCompany = (NsPartnerCompany)JsonUtils.getBean(request,NsPartnerCompany.class);
		nsPartnerCompany.setType(type);
		List<NsPartnerCompanyModel> list = nsPartnerCompanyService.findCompany(nsPartnerCompany);
		JQResultModel resultModel = new JQResultModel(list, nsPartnerCompany.getPage());
		return JSON.toJSONString(resultModel);
	}
	
	@RequestMapping(value = {"update"})
	@ResponseBody
	public String update(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("id");
		JQResultModel resultModel = new JQResultModel();
		NsPartnerCompanyModel nsPartnerCompanyModel = nsPartnerCompanyService.findById(id);
		if(!"-1".equals(nsPartnerCompanyModel.getId())){
			resultModel.setResult(0);
			resultModel.setUserdata(nsPartnerCompanyModel);
		}else{
			resultModel.setResult(-1);
			resultModel.setMsg("修改失败,请与管理员联系");
		}
		return JSON.toJSONString(resultModel);
	}
	
	@RequestMapping(value = {"edit"})
	@ResponseBody
	public String edit(NsPartnerCompany nsPartnerCompany){
		JQResultModel resultModel = new JQResultModel();
		if(!"true".equals(checkName(nsPartnerCompany.getOldName(), nsPartnerCompany.getName()))){
			resultModel.setResult(-1);
			resultModel.setMsg("单位名称重复");
		}else if(!"true".equals(checkOffice(nsPartnerCompany.getOfficeId(),nsPartnerCompany.getOldOffice()))){
			resultModel.setResult(-1);
			resultModel.setMsg("所属部门重复");
		}else{
			int flag = nsPartnerCompanyService.saveCompany(nsPartnerCompany);
			if(flag == 0){// 新增成功
				resultModel.setResult(0);
				resultModel.setMsg("保存成功");
			}else{
				resultModel.setResult(-2);
				resultModel.setMsg("保存失败,请与管理员联系");
			}
		}
		return JSON.toJSONString(resultModel);
	}
	
	@RequestMapping(value = {"delete"})
	@ResponseBody
	public String delete(HttpServletRequest request,HttpServletResponse response){
		JQResultModel resultModel = new JQResultModel();
		String id = request.getParameter("id");
		NsPartnerCompany nsPartnerCompany = new NsPartnerCompany();
		nsPartnerCompany.setId(id);
		int flag = nsPartnerCompanyService.delCompany(nsPartnerCompany);
		if(flag == 0){
			resultModel.setResult(0);
			resultModel.setMsg("删除成功");
		}else{
			resultModel.setResult(-1);
			resultModel.setMsg("删除失败,请与管理员联系");
		}
		return JSON.toJSONString(resultModel);
	}
	
	@RequestMapping(value = {"checkName"})
	@ResponseBody
	public String checkName(String oldName,String name){
		if (name !=null && name.equals(oldName)) {
			return "true";
		} else if (name !=null && nsPartnerCompanyService.getByName(name) == null) {
			return "true";
		}
		return "false";
	}
	
	@RequestMapping(value = {"checkOffice"})
	@ResponseBody
	public String checkOffice(String officeId,String oldOffice){
		if(officeId != null && officeId.equals(oldOffice)){
			return "true";
		}else if(officeId != null && nsPartnerCompanyService.getByOffice(officeId) == null){
			return "true";
		}
		return "false";
	}
}
