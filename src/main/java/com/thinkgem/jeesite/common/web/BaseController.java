/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.common.web;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.Member;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.pj_common.Constants.PjConstants;

/**
 * 控制器支持类
 * @author ThinkGem
 * @version 2013-3-23
 */
@Controller
public abstract class BaseController {

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 管理基础路径
	 */
	@Value("${adminPath}")
	protected String adminPath;
	
	/**
	 * 前端基础路径
	 */
	@Value("${frontPath}")
	protected String frontPath;
	
	/**
	 * 前端URL后缀
	 */
	@Value("${urlSuffix}")
	protected String urlSuffix;
	
	/**
	 * 验证Bean实例对象
	 */
	@Autowired
	protected Validator validator;

	/**
	 * 服务端参数有效性验证
	 * @param object 验证的实体对象
	 * @param groups 验证组
	 * @return 验证成功：返回true；严重失败：将错误信息添加到 message 中
	 */
	protected boolean beanValidator(Model model, Object object, Class<?>... groups) {
		try{
			BeanValidators.validateWithException(validator, object, groups);
		}catch(ConstraintViolationException ex){
			List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
			list.add(0, "数据验证失败：");
			addMessage(model, list.toArray(new String[]{}));
			return false;
		}
		return true;
	}
	
	/**
	 * 服务端参数有效性验证
	 * @param object 验证的实体对象
	 * @param groups 验证组
	 * @return 验证成功：返回true；严重失败：将错误信息添加到 flash message 中
	 */
	protected boolean beanValidator(RedirectAttributes redirectAttributes, Object object, Class<?>... groups) {
		try{
			BeanValidators.validateWithException(validator, object, groups);
		}catch(ConstraintViolationException ex){
			List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
			list.add(0, "数据验证失败：");
			addMessage(redirectAttributes, list.toArray(new String[]{}));
			return false;
		}
		return true;
	}
	
	/**
	 * 服务端参数有效性验证
	 * @param object 验证的实体对象
	 * @param groups 验证组，不传入此参数时，同@Valid注解验证
	 * @return 验证成功：继续执行；验证失败：抛出异常跳转400页面。
	 */
	protected void beanValidator(Object object, Class<?>... groups) {
		BeanValidators.validateWithException(validator, object, groups);
	}
	
	/**
	 * 添加Model消息
	 * @param message
	 */
	protected void addMessage(Model model, String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages){
			sb.append(message).append(messages.length>1?"<br/>":"");
		}
		model.addAttribute("message", sb.toString());
	}
	
	/**
	 * 添加Flash消息
	 * @param message
	 */
	protected void addMessage(RedirectAttributes redirectAttributes, String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages){
			sb.append(message).append(messages.length>1?"<br/>":"");
		}
		redirectAttributes.addFlashAttribute("message", sb.toString());
	}
	
	/**
	 * 客户端返回JSON字符串
	 * @param response
	 * @param object
	 * @return
	 */
	protected String renderString(HttpServletResponse response, Object object) {
		return renderString(response, JsonMapper.toJsonString(object), "application/json");
	}
	
	/**
	 * 客户端返回字符串
	 * @param response
	 * @param string
	 * @return
	 */
	protected String renderString(HttpServletResponse response, String string, String type) {
		try {
			response.reset();
	        response.setContentType(type);
	        response.setCharacterEncoding("utf-8");
			response.getWriter().print(string);
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * 参数绑定异常
	 */
	@ExceptionHandler({BindException.class, ConstraintViolationException.class, ValidationException.class})
    public String bindException() {  
        return "error/400";
    }
	
	/**
	 * 授权登录异常
	 */
	@ExceptionHandler({AuthenticationException.class})
    public String authenticationException() {  
        return "error/403";
    }
	
	/**
	 * 初始化数据绑定
	 * 1. 将所有传递进来的String进行HTML编码，防止XSS攻击
	 * 2. 将字段中Date类型转换为String类型
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
		binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
			}
			@Override
			public String getAsText() {
				Object value = getValue();
				return value != null ? value.toString() : "";
			}
		});
		// Date 类型转换
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(DateUtils.parseDate(text));
			}
//			@Override
//			public String getAsText() {
//				Object value = getValue();
//				return value != null ? DateUtils.formatDateTime((Date)value) : "";
//			}
		});
	}
	public static void refreshSessionFunc(Session session){
		String companyId = (String)session.getAttribute(PjConstants.CUR_COMPANY_ID);
		// 判断Session内容是否为空。
		if(StringUtils.isBlank(companyId)){
			// 获取会员id。
			// 如果没获取到(例如 在未登录时)
			if( null == UserUtils.getUser() || StringUtils.isBlank(UserUtils.getUser().getId())){
				session.setAttribute(PjConstants.CUR_COMPANY_ID     , "");
				// 获取工程信息
				session.setAttribute(PjConstants.CUR_PROJECT_ID     , "");
				session.setAttribute(PjConstants.CUR_PROJECT_NAME   , "");
				session.setAttribute(PjConstants.CUR_PROJECT_STATUS , "");
				session.setAttribute(PjConstants.CUR_PROJECT_IS_DEMO, "");	
			}
			else{
				session.setAttribute(PjConstants.CUR_COMPANY_ID    , UserUtils.getUser().getMember().getId());
				
				// 调用缓存类CCPrjInfoUtils的函数，获取工程信息。（注:先从缓存获取，取不到则从DB中检索）
				//CcPrjInfo ccPrjInfo = CcPrjInfoUtils.getByUserId(UserUtils.getUser().getId());
				System.out.println("refreshSession");
				//if(ccPrjInfo != null){
				//	session.setAttribute(PjConstants.CUR_PROJECT_ID    , ccPrjInfo.getId());
				//	session.setAttribute(PjConstants.CUR_PROJECT_NAME  , ccPrjInfo.getPrjName()); 
				//	session.setAttribute(PjConstants.CUR_PROJECT_STATUS, ccPrjInfo.getStatus());
				//	session.setAttribute(PjConstants.CUR_PROJECT_IS_DEMO,ccPrjInfo.getDemoPrjSign());
				//}
				//else{
				//	session.setAttribute(PjConstants.CUR_PROJECT_ID     , "");
				//	session.setAttribute(PjConstants.CUR_PROJECT_NAME   , "");
				//	session.setAttribute(PjConstants.CUR_PROJECT_STATUS , "");
				//	session.setAttribute(PjConstants.CUR_PROJECT_IS_DEMO, "");
				//}
			}
			//System.out.println("Session projectStatus:" .concat((String)session.getAttribute(PjConstants.CUR_PROJECT_STATUS)));
			//System.out.println("Session projectId:".concat((String)session.getAttribute(PjConstants.CUR_PROJECT_ID)));
			//System.out.println("Session projectName:".concat((String)session.getAttribute(PjConstants.CUR_PROJECT_NAME)));
			//System.out.println("Session memberId:".concat((String)session.getAttribute(PjConstants.CUR_COMPANY_ID)));
		}	
		return;
	}
	public static void refreshSessionFunc(HttpSession session){
		String companyId = (String)session.getAttribute(PjConstants.CUR_COMPANY_ID);
		// 判断Session内容是否为空。
		if(StringUtils.isBlank(companyId)){
			// 获取会员id。
			// 如果没获取到(例如 在未登录时)
			if( null == UserUtils.getUser() || StringUtils.isBlank(UserUtils.getUser().getId())){
				session.setAttribute(PjConstants.CUR_COMPANY_ID     , "");
				// 获取工程信息
				session.setAttribute(PjConstants.CUR_PROJECT_ID     , "");
				session.setAttribute(PjConstants.CUR_PROJECT_NAME   , "");
				session.setAttribute(PjConstants.CUR_PROJECT_STATUS , "");
				session.setAttribute(PjConstants.CUR_PROJECT_IS_DEMO, "");
			}
			else{
				// 2016-7-2 重新设置companyId。注意:用户的memberId不一定有，所以需要判断是否为null. 
				Member member = UserUtils.getUser().getMember();
				if(member==null){
					companyId = "";
				}
				else{
					companyId = member.getId();
				}
				session.setAttribute(PjConstants.CUR_COMPANY_ID, companyId);
				
				// 调用缓存类CCPrjInfoUtils的函数，获取工程信息。（注:先从缓存获取，取不到则从DB中检索）
				//CcPrjInfo ccPrjInfo = CcPrjInfoUtils.getByUserId(UserUtils.getUser().getId());
				System.out.println("refreshSession");
				//if(ccPrjInfo != null){
				//	session.setAttribute(PjConstants.CUR_PROJECT_ID    , ccPrjInfo.getId());
				//	session.setAttribute(PjConstants.CUR_PROJECT_NAME  , ccPrjInfo.getPrjName()); 
				//	session.setAttribute(PjConstants.CUR_PROJECT_STATUS, ccPrjInfo.getStatus());
				//	session.setAttribute(PjConstants.CUR_PROJECT_IS_DEMO,ccPrjInfo.getDemoPrjSign());
				//}
				//else{
				//	session.setAttribute(PjConstants.CUR_PROJECT_ID     , "");
				//	session.setAttribute(PjConstants.CUR_PROJECT_NAME   , "");
				//	session.setAttribute(PjConstants.CUR_PROJECT_STATUS , "");
				//	session.setAttribute(PjConstants.CUR_PROJECT_IS_DEMO, "");
				//}
			}
			//System.out.println("Session projectStatus:" .concat((String)session.getAttribute(PjConstants.CUR_PROJECT_STATUS)));
			//System.out.println("Session projectId:".concat((String)session.getAttribute(PjConstants.CUR_PROJECT_ID)));
			//System.out.println("Session projectName:".concat((String)session.getAttribute(PjConstants.CUR_PROJECT_NAME)));
			//System.out.println("Session memberId:".concat((String)session.getAttribute(PjConstants.CUR_COMPANY_ID)));
		}	
		return;
	}
	
	/**
	 * 判断session是否过期，过期则自动重新获取session（从缓存中)。注意：每次调用Controller均执行。
	 * @author mlg
	 * @since 2016-1-23
	 */
	public void refreshSession(HttpServletRequest request, HttpServletResponse response){
		refreshSessionFunc(request.getSession());		
	}
	/** 检查是否已经登录   1:已登录， 0:未登录
	 *  用于Controller中Ajax调用时判断是否已登录。注意:非Ajax时,shiro会自动判断是否已登录。
	 * */
	public String checkLogin(){
		if(StringUtils.isBlank(UserUtils.getUser().getId())){
			return "0"; // 没登录
		}
		else{
			return "1"; // 已登录
		}
	}
}
