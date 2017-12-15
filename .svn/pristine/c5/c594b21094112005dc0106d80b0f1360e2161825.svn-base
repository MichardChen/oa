package com.baidu.ueditor.upload;

import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.State;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

public class StorageManager {
	public static final int BUFFER_SIZE = 8192;

	public StorageManager() {
	}

	public static State saveBinaryFile(byte[] data, String path) {
		File file = new File(path);

		State state = valid(file);

		if (!state.isSuccess()) {
			return state;
		}

		try {
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(file));
			bos.write(data);
			bos.flush();
			bos.close();
		} catch (IOException ioe) {
			return new BaseState(false, AppInfo.IO_ERROR);
		}

		state = new BaseState(true, file.getAbsolutePath());
		state.putInfo( "size", data.length );
		state.putInfo( "title", file.getName() );
		return state;
	}

	/**
	 * orgExtName 原扩展名
	 * path       保存路径(路径)
	 * filePath   保存文件路径(全路径)
	 * fileName   保存文件名（不含扩展名)
	 * */
	public static State saveFileByInputStream(InputStream is, String orgExtName, String path, String filePath,String fileName,
			long maxSize) {
		State state = null;

		File tmpFile = getTmpFile(orgExtName);

		byte[] dataBuf = new byte[ 2048 ];
		BufferedInputStream bis = new BufferedInputStream(is, StorageManager.BUFFER_SIZE);

		try {
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(tmpFile), StorageManager.BUFFER_SIZE);

			int count = 0;
			while ((count = bis.read(dataBuf)) != -1) {
				bos.write(dataBuf, 0, count);
			}
			bos.flush();
			bos.close();
			
			//2016-12-21 压缩图片文件
			if(orgExtName.toUpperCase().equals(".PNG")||orgExtName.toUpperCase().equals(".JPG")||
			   orgExtName.toUpperCase().equals(".JPEG")){
				Compress(path,fileName,tmpFile);
			}
			
			tmpFile.setReadable(true);
			tmpFile.setWritable(true);
			
			if (tmpFile.length() > maxSize) {
				if(tmpFile.exists()){
					tmpFile.delete();
				}
				return new BaseState(false, AppInfo.MAX_SIZE);
			}

			state = saveTmpFile(tmpFile, filePath);

			if (!state.isSuccess()) {
				if(tmpFile.exists()){
					tmpFile.delete();
				}
			}

			return state;
			
		} catch (IOException e) {
		}
		return new BaseState(false, AppInfo.IO_ERROR);
	}

	public static State saveFileByInputStream(InputStream is, String path) {
		State state = null;
		//TODO 此处的.png 为默认值。
		File tmpFile = getTmpFile(".png");

		byte[] dataBuf = new byte[ 2048 ];
		BufferedInputStream bis = new BufferedInputStream(is, StorageManager.BUFFER_SIZE);

		try {
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(tmpFile), StorageManager.BUFFER_SIZE);

			int count = 0;
			while ((count = bis.read(dataBuf)) != -1) {
				bos.write(dataBuf, 0, count);
			}
			bos.flush();
			bos.close();

			state = saveTmpFile(tmpFile, path);

			if (!state.isSuccess()) {
				tmpFile.delete();
			}

			return state;
		} catch (IOException e) {
		}
		return new BaseState(false, AppInfo.IO_ERROR);
	}

	/**图片压缩
	 * path       - 保存路径(不含\)
	 * photoName  - 文件名(不含扩展名)
	 * file       - 源文件
	 * @throws IOException 
	 */
	
	private static void Compress(String path,String photoName,File file) throws IOException{
		// 注意：转换前的file必须要设值扩展名。
		BufferedImage bufferedImage = ImageIO.read(file);
	    BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
	    newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
	    /* File newPath = new File(xkaFtpHistPath + "\\" + nsFaultXka.getInspDate());
	    if(!newPath.exists()  && !newPath .isDirectory()){
	  	  newPath.mkdir();
	    }*/
	    File newFile = new File(path + "\\" + photoName + ".jpg");
	    if(!newFile.exists()){//转移文件
	      newFile.setWritable(true);
		  newFile.setReadable(true);
	  	  ImageIO.write(newBufferedImage, "jpg", newFile);
	  	}
	    
	    bufferedImage = null;
	    newBufferedImage = null;	
	}
	
	private static File getTmpFile(String orgExtName) {
		File tmpDir = FileUtils.getTempDirectory();
		String tmpFileName = (Math.random() * 10000 + "").replace(".", "");
		tmpFileName = tmpFileName + orgExtName; //原扩展名 .png 等
		return new File(tmpDir, tmpFileName);
	}

	private static State saveTmpFile(File tmpFile, String path) {
		State state = null;
		File targetFile = new File(path);

		//if (targetFile.canWrite()) {
		//	return new BaseState(false, AppInfo.PERMISSION_DENIED);
		//}
		//try {
		//	FileUtils.copyFile(tmpFile, targetFile);
		//} catch (IOException e) {
		//	return new BaseState(false, AppInfo.IO_ERROR);
		//}

		state = new BaseState(true);
		state.putInfo( "size", targetFile.length() );
		state.putInfo( "title", targetFile.getName() );
		
		return state;
	}

	private static State valid(File file) {
		File parentPath = file.getParentFile();

		if ((!parentPath.exists()) && (!parentPath.mkdirs())) {
			return new BaseState(false, AppInfo.FAILED_CREATE_FILE);
		}

		if (!parentPath.canWrite()) {
			return new BaseState(false, AppInfo.PERMISSION_DENIED);
		}

		return new BaseState(true);
	}
}
