package org.apache.ibatis.thread;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

	private static String filename = "/mybatis-refresh.properties";
	private static Properties pro = new Properties();
	static {
		InputStream is = null; // 20160803 关闭和释放资源的改定
		try {
			is = PropertiesUtil.class.getResourceAsStream(filename);
			pro.load(is);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Load mybatis-refresh “"+filename+"” file error.");
		}
		finally {
			try { // 20160803 关闭和释放资源
				if (is != null) {
					is.close();
				}
				is = null;
			} catch (IOException e) {}
		}
	}

	public static int getInt(String key) {
		int i = 0;
		try {
			i = Integer.parseInt(getString(key));
		} catch (Exception e) {
		}
		return i;
	}

	public static String getString(String key) {
		return pro == null ? null : pro.getProperty(key);
	}

}
