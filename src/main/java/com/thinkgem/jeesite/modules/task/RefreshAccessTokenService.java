/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.task;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fastweixin.api.config.ApiConfig;
import com.fastweixin.company.api.config.QYAPIConfig;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.JsSignatureUtils;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.entity.Menu;
import com.thinkgem.jeesite.modules.sys.service.DictService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 定期任务
 * @author
 * @version
 * 去除 // @Lazy(false)
 */
@Service
@Transactional(readOnly = true)
public class RefreshAccessTokenService extends BaseService{

	@Autowired
	private DictService dictService;
	
	////.. @Scheduled(fixedDelay = 7000000)
	/// 2015-12-27 mlg. 改成 spring-context-job.xml来配置。
	public void refreshAccessToken() {
		System.out.println(new Date().toString());
		
		try{ 
			//建议收集内存垃圾。
			System.gc();
			
			//调用刷新函数（公众号）。
			refreshForJsSDK();
			
			//调用刷新函数(企业号)。
			refreshForQyJsSDK();
		}
		catch(Exception ex){
			// 失败时，重新调用一次。
			refreshForJsSDK();
		}
	}
	
	private void refreshForJsSDK(){
		// 获取ApiConfig实例。
		ApiConfig apiConfig = ApiConfig.getInstance();
		// 检测是否设置了公众号，没有的话则退出。
		if(StringUtils.isBlank(apiConfig.getAppid())){
			return ;
		}
		// (1)刷新JsApiTicket和AccessToken
		apiConfig.getJsApiTicket();
	
	    long now = System.currentTimeMillis();
	    JsSignatureUtils jsSignatureUtils = JsSignatureUtils.getInstance();
	 // 从DB获取中获取 签名URL
	    List<Menu> menulist= UserUtils.getAllMenuList();
	    
	    for(int i = 0;i < menulist.size();i++){
	       if(StringUtils.isNotBlank(menulist.get(i).getWxSignUrl())){
		       // 刷新页面对应的Token
		       jsSignatureUtils.refreshJsSignature(menulist.get(i).getWxSignUrl(), now);
	       }
	    }
	    
	    // 释放内存。
	    apiConfig = null;
	    jsSignatureUtils =null;

	}
	
	private void refreshForQyJsSDK(){
		// 获取ApiConfig实例。
		QYAPIConfig apiConfig = QYAPIConfig.getInstance();
		// 检测是否设置了公众号，没有的话则退出。
		if(StringUtils.isBlank(apiConfig.getCorpid())){
			return ;
		}
		// (1)刷新JsApiTicket和AccessToken
		apiConfig.getJsApiTicket();
		
		long now = System.currentTimeMillis();
	    JsSignatureUtils jsSignatureUtils = JsSignatureUtils.getInstance();
	    // 从DB获取中获取 签名URL
	    List<Menu> menulist= UserUtils.getAllMenuList();
	    
	    for(int i = 0;i < menulist.size();i++){
	       if(StringUtils.isNotBlank(menulist.get(i).getWxSignUrl())){
		       // 刷新页面对应的Token
		       jsSignatureUtils.refreshQywxJsSignature(menulist.get(i).getWxSignUrl(), now);
	       }
	    }
	    // 释放内存。
	    apiConfig = null;
	    jsSignatureUtils =null;
	    
		System.out.println("微信企业号TOKEN刷新完成！");
	}
}
