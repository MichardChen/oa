package com.thinkgem.jeesite.common.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;

import com.alibaba.fastjson.JSON;
import com.thinkgem.jeesite.common.persistence.BaseEntity;
import com.thinkgem.jeesite.common.persistence.Page;

public class JsonUtils {

	final private static String DEFAULT_PARAMETER_NAME = "condition";

	public static Object getBean(HttpServletRequest request, Class<?> c) {
		return getBean(request, DEFAULT_PARAMETER_NAME, c);
	}

	@SuppressWarnings("unchecked")
	public static Object getBean(HttpServletRequest request, String parameterName, Class<?> c) {
		String parameter = "";
		parameter = request.getParameter(parameterName);
		Object bean = null;
		
		ConvertUtilsBean convertUtils = new ConvertUtilsBean();
		DateConverter dateConverter = new DateConverter();
		convertUtils.register(dateConverter, Date.class);
		BeanUtilsBean beanUtils = new BeanUtilsBean(convertUtils, new PropertyUtilsBean());
		
		if (parameter == null) {
			Enumeration<String> paramNames = request.getParameterNames();
			Map<String, String> paramMap = new HashMap<String, String>();
			while (paramNames != null && paramNames.hasMoreElements()) {
				String param = paramNames.nextElement();
				paramMap.put(param, request.getParameter(param));
			}
			try {
				bean = c.newInstance();
				beanUtils.populate(bean, paramMap);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			//bean = JSONObject.toBean(JSONObject.fromObject(paramMap), c);
			if (bean instanceof BaseEntity) {
				((BaseEntity<Object>) bean).setPage(new Page<Object>(request));
			}
		} else {
			//Map<String, String> paramMap = (Map<String, String>) JSONObject.toBean(JSONObject.fromObject(parameter), Map.class);
			Map<String, String> paramMap = null;
			paramMap = JSON.parseObject(parameter, Map.class);
			
			try {
				bean = c.newInstance();
				beanUtils.populate(bean, paramMap);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			//bean = JSONObject.toBean(JSONObject.fromObject(parameter), c);
		}
		return bean;
	}
	
}
