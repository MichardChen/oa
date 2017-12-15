/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.common.utils.excel.fieldtype;

import java.math.BigDecimal;

import com.thinkgem.jeesite.common.utils.StringUtils;

/**
 * 字段类型转换
 * @author ThinkGem
 * @version 2013-03-10
 */
public class BigDecimalType {

	/**
	 * 获取对象值（导入）
	 */
	public static Object getValue(String val) {
		
			if (StringUtils.isEmpty(val)){
				return new BigDecimal(0);
			}
		
		return new BigDecimal(val);
	}

	/**
	 * 获取对象值（导出）
	 */
	public static String setValue(Object val) {
		if (val != null ){
			return val.toString();
		}
		return "";
	}
}
