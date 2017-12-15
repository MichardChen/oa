/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hihunan.service.localactivity;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import  java.security.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.fastweixin.util.NetWorkCenter;
import com.fastweixin.util.NetWorkCenter.ResponseCallback;
import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.hihunan.entity.listshow.HiListShow;
import com.thinkgem.jeesite.modules.hihunan.entity.localactivity.HiLocalActivity;
import com.thinkgem.jeesite.modules.hihunan.model.listshow.HiListShowModel;
import com.thinkgem.jeesite.modules.hihunan.model.localactivity.HiLocalActivityModel;
import com.thinkgem.jeesite.modules.hihunan.dao.localactivity.HiLocalActivityDao;

/**
 * 签名计算Service
 * @author zfg
 * @version 2016-12-01
 */
@Service
@Transactional(readOnly = true)
public class HiLocalSignUtils  {


private static String byteArrayToHexString(byte b[]) {  
        StringBuffer resultSb = new StringBuffer();  
        for (int i = 0; i < b.length; i++)  
            resultSb.append(byteToHexString(b[i]));  
  
        return resultSb.toString();  
    }  
  
    private static String byteToHexString(byte b) {  
        int n = b;  
        if (n < 0)  
            n += 256;  
        int d1 = n / 16;  
        int d2 = n % 16;  
        return hexDigits[d1] + hexDigits[d2];  
    }  
  
    public static String MD5Encode(String origin, String charsetname) {  
        String resultString = null;  
        try {  
            resultString = new String(origin);  
            MessageDigest md = MessageDigest.getInstance("MD5");  
            if (charsetname == null || "".equals(charsetname))  
                resultString = byteArrayToHexString(md.digest(resultString  
                        .getBytes()));  
            else  
                resultString = byteArrayToHexString(md.digest(resultString  
                        .getBytes(charsetname)));  
        } catch (Exception exception) {  
        }  
        return resultString;  
    }  
  
    private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",  
        "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" }; 




    private static String Key = "8Eh033lLzul1iRrCdbqQcIglBF2fyIZqBavRq";  
  
    /** 
     * @param args 
     */  
    public static void main(String[] args) {  
        System.out.println(">>>模拟微信支付<<<");  
        System.out.println("==========华丽的分隔符==========");  
        //微信api提供的参数  
        String appid = "wxd930ea5d5a258f4f";  
        String mch_id = "10000100";  
        String device_info = "1000";  
        String body = "test";  
        String nonce_str = "ibuaiVcKdpRxkhJA";  
          
        SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();  
        parameters.put("appid", appid);  
        parameters.put("mch_id", mch_id);  
        parameters.put("device_info", device_info);  
        parameters.put("body", body);  
        parameters.put("nonce_str", nonce_str);  
          
        String characterEncoding = "UTF-8";  
        String weixinApiSign = "9A0A8659F005D6984697E2CA0A9CF3B7";  
        System.out.println("微信的签名是：" + weixinApiSign);  
        String mySign = createSign(characterEncoding,parameters);  
        System.out.println("我     的签名是："+mySign);  
          
        if(weixinApiSign.equals(mySign)){  
            System.out.println("恭喜你成功了~");  
        }else{  
            System.out.println("注定了你是个失败者~");  
        }  
          
        String userAgent = "Mozilla/5.0(iphone;CPU iphone OS 5_1_1 like Mac OS X) AppleWebKit/534.46(KHTML,like Geocko) Mobile/9B206 MicroMessenger/5.0";  
          
        char agent = userAgent.charAt(userAgent.indexOf("MicroMessenger")+15);  
          
        System.out.println("微信的版本号："+new String(new char[]{agent}));  
    }  
  
    /** 
     * 微信支付签名算法sign 
     * @param characterEncoding 
     * @param parameters 
     * @return 
     */  
    @SuppressWarnings("unchecked")  
    public static String createSign(String characterEncoding,SortedMap<Object,Object> parameters){  
        StringBuffer sb = new StringBuffer();  
        Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）  
        Iterator it = es.iterator();  
        while(it.hasNext()) {  
            Map.Entry entry = (Map.Entry)it.next();  
            String k = (String)entry.getKey();  
            Object v = entry.getValue();  
//            if(null != v   && !"".equals(v) 
//                    && !"sign".equals(k) && !"key".equals(k)) {  
//                sb.append(k + "=" + v + "&");  
//            }  
            //zfg 2016-12-2 改为不必填
            if(null != v   
                    && !"sign".equals(k) && !"key".equals(k)) {  
                sb.append(k + "=" + v + "&");  
            }  
        }  
        sb.append("key=" + Key);  
        String sign = MD5Encode(sb.toString(), characterEncoding).toUpperCase();  
        return sign;  
    } 
    
    /** 
     * 评价URL 
     * @param characterEncoding 
     * @param parameters 
     * @return 
     */  
    @SuppressWarnings("unchecked")  
    public static String createURL(String characterEncoding,SortedMap<Object,Object> parameters){  
        StringBuffer sb = new StringBuffer();  
        Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）  
        Iterator it = es.iterator();  
        while(it.hasNext()) {  
            Map.Entry entry = (Map.Entry)it.next();  
            String k = (String)entry.getKey();  
            Object v = entry.getValue();  
            if(null != v   && !"".equals(v) 
                    && !"sign".equals(k) && !"key".equals(k)) {  
                sb.append(k + "/" + v + "/");  
            }  
 
        }  
        sb.append("key/" + Key);  
        return sb.toString();  
    } 
	
}