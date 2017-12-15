package com.thinkgem.jeesite.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fastweixin.api.enums.ResultType;
import com.fastweixin.company.api.QYMessageAPI;
import com.fastweixin.company.api.config.QYAPIConfig;
import com.fastweixin.company.api.response.GetQYSendMessageResponse;
import com.fastweixin.company.message.QYTextMsg;
import com.fastweixin.company.message.QYTextMsg.Text;
import com.thinkgem.jeesite.modules.sys.model.SysParameterModel;
import com.thinkgem.jeesite.modules.sys.utils.SysParameterUtils;

/**
 * 微信企业号的信息发送
 * @author mlg
 * @since 2016-5-16
 */
public class SendWxqyMsgUtils {

	/**
	 * 日志对象
	 */
	protected static Logger logger = LoggerFactory.getLogger(SendWxqyMsgUtils.class);
	
	/**
	 * @param userId: 接收者的企业号userId
	 * @param content   : 微信消息(可以带链接 )，如   请点击链接查看http://www.holdbuild.com/check/2332
	 * @return "ok"   -- 代表正常结束。
	 *         "ok"以外 -- 返回具体的错误信息。
	 * */
	public static String sendText(String userId,String content){
	
		QYAPIConfig apiConfig = QYAPIConfig.getInstance();
		
		QYMessageAPI api = new QYMessageAPI(apiConfig);
		
		QYTextMsg msg = new QYTextMsg();
		
		SysParameterModel sysParameterModel =SysParameterUtils.findKeyword("curAgentId");
		
		msg.setAgentId(sysParameterModel.getValue1()); // 微信企业号中的应用id.
		//msg.setConetnt("OKOKOK");  
		msg.setMsgType("text");         // 类型：文本
		msg.setText(new Text(content)); // 
		//msg.setToParty(toParty);
		//msg.setToTag(toTag);
		//msg.setToUser("meng_linggang@xmhold.com");
		msg.setToUser(userId);          // 接收消息的对象
		
		GetQYSendMessageResponse response = api.send(msg);
		
		logger.debug("userid:" + userId +" errMsg:"+response.getErrmsg()+"errorCode:"+response.getErrcode());
		
	    if(!response.getErrcode().equals(ResultType.SUCCESS.toString())){
	    	return response.getErrmsg();
	    }
	    
	    return ResultType.SUCCESS.toString();
	}
}
