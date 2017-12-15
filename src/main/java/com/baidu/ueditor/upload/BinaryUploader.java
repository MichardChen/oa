package com.baidu.ueditor.upload;

import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.FileType;
import com.baidu.ueditor.define.State;
import com.thinkgem.jeesite.common.config.Global;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class BinaryUploader {
	/**
	 * 根据 http://www.cnblogs.com/vincent4code/p/5809858.html 进行改写。
	 * */
	public static final State save(HttpServletRequest request,
			Map<String, Object> conf) {
		 try {
	            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
	            MultipartFile multipartFile = multipartRequest.getFile(conf.get("fieldName").toString());

	            String savePath = (String) conf.get("savePath");
	            String originFileName = multipartFile.getOriginalFilename();
	            String suffix = FileType.getSuffixByFilename(originFileName);

	            originFileName = originFileName.substring(0,originFileName.length() - suffix.length());
	            savePath = savePath + suffix;

	            long maxSize = ((Long) conf.get("maxSize")).longValue();

	            if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
	                return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
	            }
	            /***********/
	            //自定义
	            savePath = PathFormat.parse(savePath, originFileName);
	            
	            String [] savePathBySplit_temp = savePath.split("/");
	            String temp = "";
	            String fileName = savePathBySplit_temp[savePathBySplit_temp.length-1];
	            for(int i = 0;i < savePathBySplit_temp.length-1; i++){
	                if(i!=savePathBySplit_temp.length-2){
	                    temp+=savePathBySplit_temp[i]+"/";
	                }else{
	                    temp+=savePathBySplit_temp[i];
	                }
	            }
	            // 2016-12-19 改变上传路径为 jeesite.properties中的userfiles.basedir的值，不是网站根路径
	            //String pathTemp = request.getSession().getServletContext().getRealPath(temp); 
	            String pathTemp = Global.getUserfilesBaseDir() + temp;
	            //System.out.println(pathTemp+","+fileName);
	            //System.out.println(new File(pathTemp).exists());
	            File targetFile = new File(pathTemp);
	            if(!targetFile.exists()){  
	                targetFile.mkdirs();  
	            }
	            //System.out.println(new File(pathTemp).exists());
	            /************/
	            String transform = "0"; //默认不转换
	            String fileNameWithoutExt = ""; // 文件名（不含扩展名)
	            // 把保存文件的扩展名改成jpg
	            if(fileName.toUpperCase().endsWith(".PNG") || fileName.toUpperCase().endsWith(".JPG")){
	            	fileName = fileName.substring(0,fileName.length()-4) + ".jpg";
	            	transform = "14"; // 设为转换
	            	fileNameWithoutExt = fileName.substring(0,fileName.length()-4);
	            }
	            if(fileName.toUpperCase().endsWith(".JPEG")){
	            	fileName = fileName.substring(0,fileName.length()-5) + ".jpg";
	            	transform = "15"; // 设为转换
	            	fileNameWithoutExt = fileName.substring(0,fileName.length()-5);
	            }
	            //State storageState = StorageManager.saveFileByInputStream(multipartFile.getInputStream(),savePath, maxSize);
	            State storageState = StorageManager.saveFileByInputStream(multipartFile.getInputStream(),suffix,pathTemp,pathTemp+"/"+fileName, 
	            		fileNameWithoutExt,maxSize);
	            // 把扩展名改成jpg 
	            if("14".equals(transform)){
		            suffix = ".jpg";
		            savePath= savePath.substring(0,savePath.length()-4) + ".jpg";
	            }
	            if("15".equals(transform)){
		            suffix = ".jpg";
		            savePath= savePath.substring(0,savePath.length()-5) + ".jpg";
	            }
	            
	            if (storageState.isSuccess()) {
	                storageState.putInfo("url", PathFormat.format(savePath));
	                storageState.putInfo("type", suffix);
	                storageState.putInfo("original", originFileName + suffix);
	            }

	            return storageState;

	        }catch (IOException e) {
	            e.printStackTrace();
	            System.out.println(e.getMessage());
	        }
		 
	        return new BaseState(false, AppInfo.IO_ERROR);
	}

	private static boolean validType(String type, String[] allowTypes) {
		List<String> list = Arrays.asList(allowTypes);

		return list.contains(type);
	}
}
