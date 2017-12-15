package com.thinkgem.jeesite.modules.webservice;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.persistence.JQResultModel;
import com.thinkgem.jeesite.common.servlet.ValidateCodeServlet;
import com.thinkgem.jeesite.common.utils.SmsValidateCodeUtils;
import com.thinkgem.jeesite.modules.constructcost.member.model.RegisterModel;
import com.thinkgem.jeesite.modules.constructcost.member.model.SmsValidationModel;
import com.thinkgem.jeesite.modules.constructcost.member.service.RegisterService;
import com.thinkgem.jeesite.modules.sys.entity.SmsValidation;
import com.thinkgem.jeesite.modules.sys.service.SmsValidationService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * 接口实现类 * *
 * 
 * @author *
 * @since 2015-08-12 * 注意：这是REST风格的webservice.访问地址
 *        ConstructCost/webservice/rest/regValidate/checkXXXX * * * * * * * * *
 *        * * * * * * * * * * * * * * *
 **/
@Path(value = "/regValidate")
public class RegValidateRestServiceImpl implements RegValidateRestService {

	@Autowired
	private RegisterService registerService;

	@Autowired
	private SmsValidationService smsValidationService;

	// 检查公司名重复
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path(value = "checkMemberName")
	public String checkMemberExists(@Context HttpServletRequest servletRequest) {

		// token检查，防止恶意访问。
		// if(token.equals("")){
		// return "";
		// }
		String memberName = servletRequest.getParameter("memberName");
		if (StringUtils.isEmpty(memberName)) {
			// 如果没有传参数，则直接返回False。这有可能是恶意调用引起的。
			return "false";
		}
		return registerService.checkMemberName(memberName);
	}

	// 检查登录名是否重复
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path(value = "checkLoginName")
	public String checkLoginName(@Context HttpServletRequest servletRequest) {
		String loginName = servletRequest.getParameter("loginName");
		if (StringUtils.isEmpty(loginName)) {
			// 如果没有传参数，则直接返回False。这有可能是恶意调用引起的。
			return "false";
		}
		return registerService.checkLoginName(loginName);
	}

	// 检查手机号码是否重复
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path(value = "checkMobile")
	public String checkMobile(@Context HttpServletRequest servletRequest) {
		String mobile = servletRequest.getParameter("mobile");
		if (StringUtils.isEmpty(mobile)) {
			// 如果没有传参数，则直接返回False。这有可能是恶意调用引起的。
			return "false";
		}
		return registerService.checkMobile(mobile);
	}

	// 检查QQ号码是否重复
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path(value = "checkQQ")
	public String checkQQ(@Context HttpServletRequest servletRequest) {
		String qq = servletRequest.getParameter("qq");
		if (StringUtils.isEmpty(qq)) {
			// 如果没有传参数，则直接返回False。这有可能是恶意调用引起的。
			return "false";
		}
		return registerService.checkQQ(qq);
	}
	
	//向第三方平台发送验证码和手机号码
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Path(value = "sendMsg")
	public String sendValidateCode(@Context HttpServletRequest request,
			@Context HttpServletResponse response) throws ValidationException {
		String mobileNo = request.getParameter("mobileNo");// 手机号码
		Random random = new Random();
		String validateCode = "";// 验证码
		for (int i = 0; i < 6; i++) {
			validateCode += random.nextInt(10);
		}
		HashMap<String, Object> result = null;
		JQResultModel resultModel = new JQResultModel();
		String[] datas = { validateCode, "1" };
		String templateId = DictUtils.getDictLabel("templateId",
				"sendMsg_data", "");
		result = SmsValidateCodeUtils.sendMoblieValidateCode(mobileNo, templateId, datas);// 发送短信模板请求
		if ("000000".equals(result.get("statusCode"))) {
			SmsValidation smsValidation = new SmsValidation();
			smsValidation.setMobileNo(mobileNo);
			SmsValidationModel smsValidationModel = smsValidationService.findCode(smsValidation);//判断该号码之前有无获取验证码
			if(smsValidationModel == null){
				smsValidation.setValidateCode(validateCode);
				String flag = smsValidationService.saveData(smsValidation);
				if ("success".equals(flag)) {
					resultModel.setResult(0);
				} else {
					resultModel.setResult(-1);
					resultModel.setMsg("生成验证码出现错误，请与系统管理员联系");
				}
			}
			if(smsValidationModel != null && !"-1".equals(smsValidationModel.getId())){
				smsValidation.setValidateCode(validateCode);
				int flag = smsValidationService.updateData(smsValidation);
				if(flag == 1){
					resultModel.setResult(0);	
				}else{
					resultModel.setResult(-1);
					resultModel.setMsg("生成验证码出现错误，请与系统管理员联系");
				}
			}
			
		} else {
			resultModel.setResult(-2);
			resultModel.setMsg("错误码=" + result.get("statusCode") + " 错误信息= "
					+ result.get("statusMsg"));
		}
		return JSON.toJSONString(resultModel);
	}
	
	//验证手机验证码
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Path(value="checkMobileCode")
	public String checkMobileCode(@Context HttpServletRequest request,@Context HttpServletResponse response){
		String mobileNo = request.getParameter("mobileNo");//手机号码
		String validateCode = request.getParameter("validateCode");//验证码
		JQResultModel resultModel = new JQResultModel();
		SmsValidation smsValidation = new SmsValidation();
		smsValidation.setMobileNo(mobileNo);
		try {
			SmsValidationModel smsValidationModel = smsValidationService.findCode(smsValidation);
			if(validateCode.equals(smsValidationModel.getValidateCode())){
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				long time = formatter.parse(smsValidationModel.getSendTime()).getTime() + Long.parseLong(DictUtils.getDictLabel("timeOut",
						"sendMsg_data", ""));//设置验证码超时时间
				if(formatter.parse(smsValidationModel.getNowTime()).getTime() > time){//超时
					resultModel.setResult(-1);
					resultModel.setMsg("验证码超时,请重新获取");
				}else{
					resultModel.setResult(0);
				}
			}else{
				resultModel.setResult(-2);
				resultModel.setMsg("验证码错误");
			}
			
		} catch (Exception e) {
			resultModel.setResult(-3);
			resultModel.setMsg("存在异常,请与管理员联系");
		}
		return JSON.toJSONString(resultModel);
		
	}

	// 注册新账户
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Path(value = "save")
	public String save(@Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		JQResultModel resultModel = new JQResultModel();
		RegisterModel registerModel = new RegisterModel();
		String memberName = request.getParameter("memberName");
		String loginName = request.getParameter("loginName");
		String password = request.getParameter("password");
		String mobileNo = request.getParameter("mobile");
		String validateCode = request.getParameter("validateCode");//验证码
		String validateMobileCode = request.getParameter("validateMobileCode");//手机验证码
		String areaId = request.getParameter("areaId");
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		String qq = request.getParameter("qq");
		registerModel.setMemberName(memberName);
		registerModel.setLoginName(loginName);
		registerModel.setPassword(password);
		registerModel.setMobile(mobileNo);
		registerModel.setAreaId(areaId);
		registerModel.setName(name);
		registerModel.setAddress(address);
		registerModel.setPhone(phone);
		registerModel.setQq(qq);

		if ("false".equals(registerService.checkMemberName(registerModel
				.getMemberName()))) {
			resultModel.setResult(-2);
			resultModel.setMsg("公司名重复！");
			return JSON.toJSONString(resultModel);
		}

		// 1.35版本，增加对登录名输入与否的判断。
		if (StringUtils.isNotBlank(registerModel.getLoginName())) {
			if ("false".equals(registerService.checkLoginName(registerModel
					.getLoginName()))) {
				resultModel.setResult(-3);
				resultModel.setMsg("登录名重复！");
				return JSON.toJSONString(resultModel);
			}
		}

		if ("false".equals(registerService.checkMobile(registerModel
				.getMobile()))) {
			resultModel.setResult(-4);
			resultModel.setMsg("手机号码重复！");
			return JSON.toJSONString(resultModel);
		}
		
		//验证手机验证码正确与是否超时
		resultModel = registerService.checkValidateMobleCode(validateMobileCode, mobileNo);
		if(resultModel.getResult() == -2){
			resultModel.setResult(-5);
			resultModel.setMsg("手机验证码不正确！");
			return JSON.toJSONString(resultModel);
		}else if(resultModel.getResult() == -1){
			resultModel.setResult(-6);
			resultModel.setMsg("手机验证码超时,请重新获取！");
			return JSON.toJSONString(resultModel);
		}
		
		// 如果用户输入QQ，则检查QQ重复与否。
		if (StringUtils.isNotBlank(registerModel.getQq())) {
			if ("false".equals(registerService.checkQQ(registerModel.getQq()))) {
				resultModel.setResult(-7);
				resultModel.setMsg("QQ号码重复！");
				return JSON.toJSONString(resultModel);
			}
		}
		
		if(!(validateCode.toUpperCase()).equals(request.getSession().getAttribute(ValidateCodeServlet.VALIDATE_CODE))){
			resultModel.setResult(-8);
			resultModel.setMsg("图形验证码错误！");
			return JSON.toJSONString(resultModel);
		}
		
		boolean flag = registerService.save(registerModel);
		if (flag == true) {
			resultModel.setResult(0);
			resultModel.setMsg("注册成功,请使用手机号登陆");
		} else {
			resultModel.setResult(-1);
			resultModel.setMsg("注册失败");
		}
		return JSON.toJSONString(resultModel);
	}

}
