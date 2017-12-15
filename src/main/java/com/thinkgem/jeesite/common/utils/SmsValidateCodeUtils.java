package com.thinkgem.jeesite.common.utils;

import java.util.HashMap;

import com.thinkgem.jeesite.common.utils.CCPRestSDK.CCPRestSDK;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 手机验证码
 * @author yuyabiao
 *
 */
public class SmsValidateCodeUtils {

	public static HashMap<String, Object> sendMoblieValidateCode(String mobileNo,String templateId,String[] datas){
		CCPRestSDK ccpRestSDK = new CCPRestSDK();
		String serverIP = DictUtils.getDictLabel("serverIp", "sendMsg_data", "");
		String serverPort = DictUtils.getDictLabel("serverPort","sendMsg_data", "");
		ccpRestSDK.init(serverIP, serverPort);// 初始化服务地址和端口
		String accountSid = DictUtils.getDictLabel("accountSid","sendMsg_data", "");
		String accountToken = DictUtils.getDictLabel("accountToken","sendMsg_data", "");
		ccpRestSDK.setAccount(accountSid, accountToken);// 初始化主帐号信息
		String appId = DictUtils.getDictLabel("appId", "sendMsg_data", "");
		ccpRestSDK.setAppId(appId);// 初始化应用Id
		return ccpRestSDK.sendTemplateSMS(mobileNo, templateId, datas);
		
	}
}
