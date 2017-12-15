package com.thinkgem.jeesite.common.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fastweixin.api.JsAPI;
import com.fastweixin.api.config.ApiConfig;
import com.fastweixin.api.response.SignatureResponse;
import com.fastweixin.company.api.QYJsAPI;
import com.fastweixin.company.api.config.QYAPIConfig;
import com.fastweixin.model.WXSignModel;

/**
 * 该项目中用到的签名工具类。
 * @author
 */
public class JsSignatureUtils {
	
	private static final  Logger   LOG         = LoggerFactory.getLogger(JsSignatureUtils.class);
	// 微信公众号的URL的签名
	private static Map wxModelMap   = new HashMap(); // key为 url, value为WXSignModel对象。
	// 微信企业号的URL的签名
	private static Map qywxModelMap = new HashMap();// key为 url, value为WXSignModel对象。
		
	// 定义静态实例。
    private static  JsSignatureUtils instance;  
    // 获取实例变量。单例模式。
    public  static  JsSignatureUtils getInstance() { 
    	
    	if(instance == null){
    		instance = new JsSignatureUtils();
    	}
    	return instance;  
    }
    
	/** 根据传过来的URL(http://)
	 * 获取JS-SDK的签名(此签名已经缓存)---微信公众号
	 * */
	public WXSignModel GetJsSignature(String url){
		return (WXSignModel)wxModelMap.get(url);
	}
	/** 根据传过来的URL(http://)
	 * 获取JS-SDK的签名(此签名已经缓存) ---微信企业号
	 * */
	public WXSignModel GetQywxJsSignature(String url){
		return (WXSignModel)qywxModelMap.get(url);
	}
	
	/** 仅用于微信公众号
	 * 刷新指定的页面(URL)的签名，并缓存在WXSignModel(wxSignModelStock/wxSignModelShip)中。
	 * */
	public void refreshJsSignature(String url, final long refreshTime){
      
		// （1）设定签名url。
	    // （2）时间戳
		long timestamp = System.currentTimeMillis() / 1000;
        // （3）随机串(使用UUID获取)
        String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");
        // 实例化JsAPI
        ApiConfig apiConfig = ApiConfig.getInstance();
        JsAPI jsApi= new JsAPI(apiConfig);
        // （4）调用JsAPI.getSignature()获取签名。注意：签名用jsApiTicket早已缓存到ApiConfig中。
        SignatureResponse signatureResponse= jsApi.getSignature(nonceStr, timestamp, url);
        String            signature = signatureResponse.getSignature();
        // （5）把js-sdk wx.config()用的4个参数缓存到WXSignModel中。
        
        WXSignModel model = new WXSignModel(url,apiConfig.getAppid(),timestamp,
		                        nonceStr,signature);
        wxModelMap.put(url, model);
        
        System.out.println("url:".concat(((WXSignModel)wxModelMap.get(url)).getUrl()));
        System.out.println("appId:".concat(((WXSignModel)wxModelMap.get(url)).getAppid()));
        System.out.println("timestamp:".concat(((WXSignModel)wxModelMap.get(url)).getTimestamp()));
        System.out.println("nonceStr:".concat(((WXSignModel)wxModelMap.get(url)).getNonceStr()));
        System.out.println("signature:".concat(((WXSignModel)wxModelMap.get(url)).getSignature()));
        
        //setSignModel(callFlag,refreshTime,apiConfig.getAppid(),timestamp,nonceStr,signature);
     }
	
	/**仅用于微信企业号
	 * 刷新指定的页面(URL)的签名，并缓存在WXSignModel(wxSignModelStock/wxSignModelShip)中。
	 * */
	public void refreshQywxJsSignature(String url, final long refreshTime){
      
		// （1）设定签名url。
	    // （2）时间戳
		long timestamp = System.currentTimeMillis() / 1000;
        // （3）随机串(使用UUID获取)
        String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");
        // 实例化JsAPI
        QYAPIConfig apiConfig = QYAPIConfig.getInstance();
        QYJsAPI jsApi= new QYJsAPI(apiConfig);
        // （4）调用JsAPI.getSignature()获取签名。注意：签名用jsApiTicket早已缓存到ApiConfig中。
        SignatureResponse signatureResponse= jsApi.getSignature(nonceStr, timestamp, url);
        String            signature = signatureResponse.getSignature();
        // （5）把js-sdk wx.config()用的4个参数缓存到WXSignModel中。
        
        WXSignModel model = new WXSignModel(url,apiConfig.getCorpid(),timestamp,
		                        nonceStr,signature);
        qywxModelMap.put(url, model);
        
        System.out.println("url:".concat(((WXSignModel)qywxModelMap.get(url)).getUrl()));
        System.out.println("appId:".concat(((WXSignModel)qywxModelMap.get(url)).getAppid()));
        System.out.println("timestamp:".concat(((WXSignModel)qywxModelMap.get(url)).getTimestamp()));
        System.out.println("nonceStr:".concat(((WXSignModel)qywxModelMap.get(url)).getNonceStr()));
        System.out.println("signature:".concat(((WXSignModel)qywxModelMap.get(url)).getSignature()));
        
        //setSignModel(callFlag,refreshTime,apiConfig.getAppid(),timestamp,nonceStr,signature);
     }
}