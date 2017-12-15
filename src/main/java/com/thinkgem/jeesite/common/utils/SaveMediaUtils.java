package com.thinkgem.jeesite.common.utils;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import it.sauronsoftware.jave.InputFormatException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fastweixin.api.MaterialAPI;
import com.fastweixin.api.config.ApiConfig;
import com.fastweixin.api.enums.MaterialType;
import com.fastweixin.api.response.DownloadMaterialResponse;
import com.fastweixin.company.api.QYMediaAPI;
import com.fastweixin.company.api.config.QYAPIConfig;
import com.fastweixin.company.api.response.DownloadMediaResponse;

/**
 * 该项目中用到的签名工具类。
 * @author
 */
public class SaveMediaUtils {
	
	private static final  Logger   LOG         = LoggerFactory.getLogger(SaveMediaUtils.class);
	
	// 定义静态实例。
    private static  SaveMediaUtils instance;  
    // 获取实例变量。单例模式。
    public  static  SaveMediaUtils getInstance() { 
    	
    	if(instance == null){
    		instance = new SaveMediaUtils();
    	}
    	return instance;  
    }
    
    /**
	 * 保存通过JS SDK的uploadImage接口上传到weixin服务器上的图片文件。
	 * */
    public boolean saveImages(String fileName,String mediaId,String path){
    	ApiConfig apiConfig = ApiConfig.getInstance();
    	MaterialAPI api = new MaterialAPI(apiConfig);
    	
    	DownloadMaterialResponse response = api.downloadTempMaterial(mediaId,MaterialType.IMAGE);
    	if(response.getErrcode().equals("0")){
    	    // 获取资源成功。
    		OutputStream os = null;
			try {
				File file = new File(path);
				file.mkdirs();
				os = new FileOutputStream(fileName);
				// 保存文件。(注:writeTo中已经关闭了os，此处无需显式关闭)
				response.writeTo(os);
				return true;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			finally {
				try { // 20160803 关闭和释放资源
					if (os != null) {
						os.close();
					}
					os = null;
				} catch (IOException e) {}
			}
    	}
    	else{
        // 获取失败。	
    		return false;
    	}
    	
    	return false;
    }
    /**
	 * 保存通过JS SDK的uploadVoice接口上传到weixin服务器上的语音文件。
	 * */
    public boolean saveVoice(String fileName,String mediaId,String path){
    	ApiConfig apiConfig = ApiConfig.getInstance();
    	MaterialAPI api = new MaterialAPI(apiConfig);
    	
    	DownloadMaterialResponse response = api.downloadTempMaterial(mediaId,MaterialType.VOICE);
    	if(response.getErrcode().equals("0")){
    	    // 获取资源成功。
    		OutputStream os = null;
			try {
				File file = new File(path);
				file.mkdirs();
				os = new FileOutputStream(fileName);
				// 保存文件。(注:writeTo中已经关闭了os，此处无需显式关闭)
				response.writeTo(os);
   				// 4)如果是录音文件，则把amr格式转成mp3格式保存.
   				if(response.getFileName().lastIndexOf(".amr") > 0){
   					File source = new File(fileName);
   	   				File target = new File(path + "\\" + mediaId + ".mp3");
   	   				AudioAttributes audio = new AudioAttributes();
   	   				Encoder encoder = new Encoder();
   	   				audio.setCodec("libmp3lame");
   	   				EncodingAttributes attrs = new EncodingAttributes();
   	   				attrs.setFormat("mp3");
   	   				attrs.setAudioAttributes(audio);
   	   				encoder.encode(source, target, attrs);
   				}
   				return true;
   			} catch (FileNotFoundException e) {
   				e.printStackTrace();
   			} catch (IllegalArgumentException e) {
   					e.printStackTrace();
   			} catch (InputFormatException e) {
   					e.printStackTrace();
   			} catch (EncoderException e) {
   					e.printStackTrace();
   			} catch (IOException e) {
   				e.printStackTrace();
   			}
			finally {
				try { // 20160803 关闭和释放资源
					if (os != null) {
						os.close();
					}
					os = null;
				} catch (IOException e) {}
			}
    	}
    	else{
        // 获取失败。	
    		return false;
    	}
    	
    	return false;
    }
    
    /**
   	 * 保存通过JS SDK的接口上传到weixin服务器上的多媒体文件。
   	 * @param fileName 保存的文件名(全路径)
   	 * @param mediaId  微信 媒体id
   	 * @param path     保存路径
   	 * */
       public boolean saveWxQyMedia(String fileName,String mediaId,String path){
       	
    	QYAPIConfig apiConfig = QYAPIConfig.getInstance();
       	QYMediaAPI api = new QYMediaAPI(apiConfig);
       	DownloadMediaResponse response = api.download(mediaId);
       	
       	if(StringUtils.isNotBlank(response.getFileName())){
       	    // 获取成功。
       		OutputStream os = null;
   			try {
   				// 1)创建路径
   				File file = new File(path);
   				file.mkdirs();
   				// 2)创建流对象
   				os = new FileOutputStream(fileName);
   				
   			    // 3)保存文件。(注:writeTo中已经关闭了os，此处无需显式关闭)
   				response.writeTo(os);
   				// 4)如果是录音文件，则把amr格式转成mp3格式保存.
   				if(response.getFileName().lastIndexOf(".amr") > 0){
   					File source = new File(fileName);
   	   				File target = new File(path + "\\" + mediaId + ".mp3");
   	   				AudioAttributes audio = new AudioAttributes();
   	   				Encoder encoder = new Encoder();
   	   				audio.setCodec("libmp3lame");
   	   				EncodingAttributes attrs = new EncodingAttributes();
   	   				attrs.setFormat("mp3");
   	   				attrs.setAudioAttributes(audio);
   	   				encoder.encode(source, target, attrs);
   				}
   				return true;
   			} catch (FileNotFoundException e) {
   				e.printStackTrace();
   			} catch (IllegalArgumentException e) {
   					e.printStackTrace();
   			} catch (InputFormatException e) {
   					e.printStackTrace();
   			} catch (EncoderException e) {
   					e.printStackTrace();
   			} catch (IOException e) {
   				e.printStackTrace();
   			}
   			finally {
				try { // 20160803 关闭和释放资源
					if (os != null) {
						os.close();
					}
					os = null;
				} catch (IOException e) {}
			}
       	}
       	else{
           // 获取失败。	
       		return false;
       	}
       	
       	return false;
       }
}