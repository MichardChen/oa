/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hihunan.service.news;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.support.json.JSONUtils;
import com.fastweixin.util.NetWorkCenter;
import com.fastweixin.util.NetWorkCenter.ResponseCallback;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.hihunan.dao.artical.HiArticalDao;
import com.thinkgem.jeesite.modules.hihunan.dao.news.HiNewsDao;
import com.thinkgem.jeesite.modules.hihunan.entity.artical.HiArtical;
import com.thinkgem.jeesite.modules.hihunan.entity.news.HiNews;
import com.thinkgem.jeesite.modules.hihunan.model.artical.HiArticalModel;
import com.thinkgem.jeesite.modules.hihunan.model.hinews.QqNewsListModel;
import com.thinkgem.jeesite.modules.hihunan.model.hinews.QqNewsModel;
import com.thinkgem.jeesite.modules.hihunan.model.news.HiNewsModel;
import com.thinkgem.jeesite.modules.sys.utils.SysParameterUtils;

/**
 * 新闻Service
 * 
 * @author yuyabiao
 * @version 2016-11-29
 */
@Service
@Transactional(readOnly = true)
public class HiNewsService extends CrudService<HiNewsDao, HiNews> {

	@Autowired
	private HiNewsDao hiNewsDao;

	@Autowired
	private HiArticalDao hiArticalDao;
	
	/**
	 * 根据类型查询新闻、教育
	 * 
	 * @param hiArtical
	 * @return
	 * @throws ValidationException
	 */
	@Transactional(readOnly = true)
	public List<HiNewsModel> findNews(HiNews hiNews) throws ValidationException {
		List<HiNewsModel> detailList = new ArrayList<HiNewsModel>();
		try {
			detailList = hiNewsDao.getNews(hiNews);

		} catch (Exception e) {

			throw new ValidationException(e.toString());
		}
		return detailList;
	}

	@Transactional(readOnly = false)
	public int scrapyNewsFromQQ() throws ValidationException {
		int flag = 0;
		List<HiArtical> dataList = new ArrayList<HiArtical>();
		try {
			String regex1 = "<!--Added by nonysun at 2014/03/06-->([\\d\\D]*?)</div>";
			// 评论ID正则

			// 调用大湘提供的本地活动列表接口
			String url = String.format("http://openapi.inews.qq.com/getQQNewsIndexAndItems?chlid=news_news_hn&refer=mobilewwwqqcom&otype=jsonp");
			final StringBuilder rtn = new StringBuilder();
			// NetWorkCenter.p
			NetWorkCenter.post(url, "", new ResponseCallback() {
				@Override
				public void onResponse(int resultCode, String resultJson) {
					if (resultCode == 200) {
						rtn.append(resultJson);
					} else { // 没返回数据
						rtn.append("error");
					}
				}
			});

			String rtnList = rtn.toString();
			String JasonList = rtnList.substring(rtnList.indexOf("ids") + 5,rtnList.length() - 4);
			List<QqNewsModel> qqNewsList = (List<QqNewsModel>) JSONUtils.parse(JasonList);
			List<HiArticalModel> articalList = hiArticalDao.getNews(new HiArtical());

			List<String> parentIdList = new ArrayList<String>();
			for (HiArticalModel member : articalList) {
				parentIdList.add(member.getKeyword());
			}

			for (int i = 0; i < qqNewsList.size(); i++) {

				Map<String, String> memberMap = (Map<String, String>) qqNewsList.get(i);
				String ids = memberMap.get("id");
				if (hasLetter("[a-zA-Z]", ids.substring(ids.length() - 1)))
					continue;

				String newsUrl = "http://openapi.inews.qq.com/getQQNewsNormalContent?ids=" + ids + "&refer=mobilewwwqqcom&otype=jsonp";
				String html = openUrl(newsUrl, "UTF-8");
				String htmlStr = "";
				String ImgFromUrl = "";
				try {
					htmlStr = html.substring(html.indexOf("newslist") + 10,html.length() - 3);
				} catch (Exception e) {
					continue;
				}
				List<QqNewsListModel> newsListModel = (List<QqNewsListModel>) JSONUtils.parse(htmlStr);
				for (int j = 0; j < newsListModel.size(); j++) {
					Map<String, Object> newsListMap = (Map<String, Object>) newsListModel.get(j);

					//识别出含有视频的新闻就直接抛弃掉这条新闻，不要抓取。
					if(newsListMap.get("flag").toString().equals("0")){
						// 获取网页源代码
						String htmls = openUrl(newsListMap.get("url").toString(),"UTF-8");
						// 获取新闻正则
						String content = getContent(regex1, htmls);
						if (parentIdList.contains(newsListMap.get("id").toString())|| content == null|| content.equals("")|| (!content.substring(1, 3).equals("<p") && !content.substring(1, 3).equals("<P")))
						continue;
						HiArtical hiArtical = new HiArtical();
						hiArtical.preInsert();
						hiArtical.setTitle(newsListMap.get("title").toString());
						String titlePhoto = newsListMap.get("thumbnails").toString();
	
						if (titlePhoto.equals("[]") || titlePhoto.equals("")|| titlePhoto.equals(" "))
							hiArtical.setTitlePhoto("");
						else {
							String temp = newsListMap.get("thumbnails").toString().substring(1);
							hiArtical.setTitlePhoto(temp.substring(0,temp.length() - 1));
							//ImgFromUrl = temp.substring(0, temp.length() - 1);
						}
	
						//ImgFromUrl = "http://read.html5.qq.com/image?src=forum&q=5&r=0&imgflag=7&imageUrl="+ ImgFromUrl;
						//String photoParth = getImgFromUrl(ImgFromUrl, newsListMap.get("id").toString());
	
						// 去除结尾的无用字符 <div class="moreOperator"> <div class="share"
						// id="share">
						content = content.replace("<div class=\"moreOperator\">","");
						content = content.replace("<div class=\"share\" id=\"share\">", "");
						content = content.replace("<br>", "");
						content = content.trim();

						//hiArtical.setTitlePhoto(photoParth);
						hiArtical.setPublishDate(new Date());
						hiArtical.setContent(content);
						hiArtical.setArticalType("advisory");
						hiArtical.setKeyword(newsListMap.get("id").toString());
						hiArtical.setActor(newsListMap.get("source").toString());
						hiArtical.setAuditState("9");
						dataList.add(hiArtical);
					}
				}
			}
			hiArticalDao.addNews(dataList);
		} catch (Exception e) {
			flag = -1;
			throw new ValidationException(e.toString());
		}
		return flag;
	}
	
	
	/**
	 * 下载本地生活图片
	 * @return
	 * @throws ValidationException
	 */
	@Transactional(readOnly = false)
	public int loadImage() throws ValidationException{
		int flag = 0;
		try {
			List<HiArticalModel> list = hiArticalDao.getUnLoadImage();
			if(list.size() > 0){
				for(HiArticalModel hiArticalModel : list){
					HiArtical hiArtical = new HiArtical();
					hiArtical.setId(hiArticalModel.getId());
					String titlePhotoPath = "http://read.html5.qq.com/image?src=forum&q=5&r=0&imgflag=7&imageUrl="+ hiArticalModel.getTitlePhoto();
					String photoParth = getImgFromUrl(titlePhotoPath, hiArticalModel.getKeyword());
					hiArtical.setTitlePhoto(photoParth);
					String content = hiArticalModel.getContent();
					List<String> newUrlList = new ArrayList<String>();
					int fromIndex = 0; // 搜索开始的位置
					int pos = 0; // 搜索到的位置
									// (http://inews.gtimg.com/newsapp_match/)
					int posDelimiter = 0; // 搜索到的位置 双引号
					while (true) {
						pos = content.indexOf("http://inews.gtimg.com/newsapp_match/",fromIndex);
						if (pos > -1) {
							posDelimiter = content.indexOf("\"", pos);
							String orgImgUrl = content.substring(pos,posDelimiter);
							newUrlList.add(orgImgUrl);
							// newUrls
							fromIndex = posDelimiter;
						} else { // 没有找到，则退出
							break;
						}
					}
					for (String urlOld : newUrlList) {
						String urlOldImage = "http://read.html5.qq.com/image?src=forum&q=5&r=0&imgflag=7&imageUrl="+ urlOld;
						String newUrl = getImgFromUrl(urlOldImage, hiArticalModel.getKeyword());
						content = content.replace(urlOld, newUrl);
					}
					hiArtical.setContent(content);
					hiArtical.setAuditState("1");
					hiArticalDao.updateNewsInfo(hiArtical);
				}
			}
		} catch (Exception e) {
			flag = -1;
			throw new ValidationException(e.toString());
		}
		return flag;
	}

	/**
	 * 访问url返回url的html代码
	 */
	public static String openUrl(String currentUrl, String charset) {
		InputStream is = null;
		BufferedReader br = null;
		URL url;
		StringBuffer html = new StringBuffer();
		try {
			url = new URL(currentUrl);
			URLConnection conn = url.openConnection();
			conn.setReadTimeout(5000);
			conn.connect();
			is = conn.getInputStream();
			br = new BufferedReader(new InputStreamReader(is, charset));
			String str;
			while (null != (str = br.readLine())) {
				html.append(str).append("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return html.toString();
	}

	private static String getContent(String regex, String text) {
		String content = "";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);
		while (matcher.find()) {
			content = matcher.group(1).toString();
		}
		return content;
	}

	private static boolean hasLetter(String regex, String text) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);

		if (matcher.matches())
			return true;
		else
			return false;
	}

	public static String getImgFromUrl(String urlstr, String savepath) {
		int num = urlstr.indexOf('/', 8);
		int extnum = urlstr.lastIndexOf('.');
		String u = urlstr.substring(0, num);
		String ext = urlstr.substring(extnum + 1, urlstr.length());
		try {
			long curTime = System.currentTimeMillis();
			Random random = new Random();
			String fileName = String.valueOf(curTime) + "_" + random.nextInt(100000000) + ".jpg";
			urlstr = urlstr + ".jpg";
			URL url = new URL(urlstr);
			URLConnection connection = url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestProperty("referer", u); // 通过这个http头的伪装来反盗链
			BufferedImage image = ImageIO.read(connection.getInputStream());// 读取连接的流，赋值给BufferedImage对象
			String path0 = SysParameterUtils.findKeyword("userfile").getValue1();
			path0 = path0 + "/" + "news" + "/" + savepath;
			String path1 = path0 + "/" + fileName;
			File file = new File(path0);
			if (!file.exists()) {
				file.mkdirs();
			}
			file = new File(path1);
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			FileOutputStream fout = new FileOutputStream(file);
			if ("gif".equals(ext) || "png".equals("ext") || "jpg".equals("ext")) {
				ImageIO.write(image, ext, fout);
			}
			ImageIO.write(image, "jpg", fout);
			fout.flush();
			fout.close();
			String realPath = Global.getConfig("adminUrl") + "/hihunan/userfiles/images/news/" + savepath + "/" + fileName;
			return realPath;
		} catch (Exception e) {
			System.out.print(e.getMessage().toString());
		}
		return "";
	}

	/**
	 * 删除资讯新闻
	 * @param hiArtical
	 * @throws ValidationException
	 */
	@Transactional(readOnly = false)
	public int deleteNews(HiArtical hiArtical) throws ValidationException {
		int flag = 0;
		Date date = new Date();
		hiArtical.setPublishDate(date);
		hiArtical.setArticalType("advisory");
		try {
			List<HiArticalModel> list = hiArticalDao.getWaitDeleteNews(hiArtical);
			if (list.size() > 0) {
				//删除资讯文章
				hiArticalDao.deleteNews(hiArtical);
				//删除资讯图片
				for(HiArticalModel hiArticalModel : list){
					String filePath = hiArticalModel.getKeyword();
					filePath = SysParameterUtils.findKeyword("userfile").getValue1() + "/news/" + filePath;
					File photoFile = new File(filePath);
					if(photoFile.exists()){
						for(File fi:photoFile.listFiles()){
							fi.delete();
						}
						photoFile.setExecutable(true);
						photoFile.delete();
					}
				}
			}
		} catch (Exception e) {
			flag = -1;
			throw new ValidationException(e.toString());
		}
		return flag;
	}

}