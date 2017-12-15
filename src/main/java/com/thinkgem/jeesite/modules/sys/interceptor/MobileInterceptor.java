/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.UserAgentUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 手机端视图拦截器
 * @author ThinkGem
 * @version 2014-9-1
 */
public class MobileInterceptor extends BaseService implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler) throws Exception {
	  //System.out.println("MobileInterceptor preHandle");
	  //PC用户登录超时的话，如果Ajax调用，则终止继续操作，返回response的Header设置
	  // sessionstatus的信息。浏览器的Ajax的complete中判断此sessionstatus，并转到登录页面
	  if (StringUtils.isBlank(UserUtils.getUser().getId())) { //未登录
            //Ajax调用
			if (request.getHeader("x-requested-with") != null && 
                request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){ 
				//如果是ajax请求响应头会有，x-requested-with  
                response.setHeader("sessionstatus", "timeout");//在响应头设置session状态
                return false;
            }
	  }
	  
	  return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, 
			ModelAndView modelAndView) throws Exception {		
		if (modelAndView != null){
			// 如果是手机或平板访问的话，则跳转到手机视图页面。
			if(UserAgentUtils.isMobileOrTablet(request) && !StringUtils.startsWithIgnoreCase(modelAndView.getViewName(), "redirect:")){
				modelAndView.setViewName("mobile/" + modelAndView.getViewName());
				
				logger.info("[mobile] URI:"+ request.getRequestURI() + ", ViewName: " + modelAndView.getViewName());
			}
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
			Object handler, Exception ex) throws Exception {
		
	}

}
