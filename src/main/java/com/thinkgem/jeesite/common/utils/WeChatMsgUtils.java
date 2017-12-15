package com.thinkgem.jeesite.common.utils;

import com.fastweixin.api.TemplateMsgAPI;
import com.fastweixin.api.config.ApiConfig;
import com.fastweixin.api.entity.TemplateMsg;
import com.fastweixin.api.response.SendTemplateResponse;

/**
 * 微信消息
 * @author yuyabiao
 *
 */
public class WeChatMsgUtils {
	
	public static SendTemplateResponse sendWeChatMsg(TemplateMsg msg){
		// 发送微信消息给项目经理。
		ApiConfig apiConfig = ApiConfig.getInstance();
		TemplateMsgAPI api = new TemplateMsgAPI(apiConfig);
		SendTemplateResponse result = api.send(msg);
		return result;
		
	}
}
