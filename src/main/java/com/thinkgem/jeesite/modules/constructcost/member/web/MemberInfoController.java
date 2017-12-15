package com.thinkgem.jeesite.modules.constructcost.member.web;

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
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Member;
import com.thinkgem.jeesite.modules.sys.model.DictModel;
import com.thinkgem.jeesite.modules.sys.model.MemberModel;
import com.thinkgem.jeesite.modules.sys.service.AreaService;
import com.thinkgem.jeesite.modules.sys.service.MemberService;
import com.thinkgem.jeesite.pj_common.Constants.PjConstants;
/**
 * 会员信息Controller
 * @author yuyabiao
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/member/memberinfo")
public class MemberInfoController extends BaseController{
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private AreaService areaService;
	
	@RequestMapping(value ={"init",""})
	public String init (MemberModel memberModel,HttpServletRequest request,HttpServletResponse response,Model model){
		String companyId = (String) request.getSession().getAttribute(
				PjConstants.CUR_COMPANY_ID);// 获取登录会员的ID
		memberModel = memberService.getConcurrentPrjNum(companyId);
		List<DictModel> areaList = areaService.findCity();
		model.addAttribute("memberModel", memberModel);
		model.addAttribute("areaList", areaList);
		return "modules/constructcost/member/memberInfo";
	}
	
	@RequestMapping(value = {"save"})
	@ResponseBody
	private String save(MemberModel memberModel,HttpServletRequest request,HttpServletResponse response){
		Member member = new Member();
		member.setId(memberModel.getId());
		member.setMemberName(memberModel.getMemberName());
		member.setAreaId(memberModel.getAreaId());
		member.setAddr(memberModel.getAddr());
		member.setTel(memberModel.getTel());
		int stn = memberService.updateMemberInfo(member);
		JQResultModel resultModel = new JQResultModel();
		resultModel.setResult(stn);
		return JSON.toJSONString(resultModel);
	}
}
