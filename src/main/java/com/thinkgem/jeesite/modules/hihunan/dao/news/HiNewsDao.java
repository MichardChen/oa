/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hihunan.dao.news;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.hihunan.entity.news.HiNews;
import com.thinkgem.jeesite.modules.hihunan.model.news.HiNewsModel;

/**
 * 新闻DAO接口
 * @author yuyabiao
 * @version 2016-11-29
 */
@MyBatisDao
public interface HiNewsDao extends CrudDao<HiNews> {
	/**
	 * 获取美食资讯
	 * @param hiArtical
	 * @return
	 */
	public List<HiNewsModel> getNews(HiNews hiNews);
}