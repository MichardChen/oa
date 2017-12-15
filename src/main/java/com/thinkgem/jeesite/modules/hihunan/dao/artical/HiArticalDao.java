/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hihunan.dao.artical;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.hihunan.entity.artical.HiArtical;
import com.thinkgem.jeesite.modules.hihunan.entity.artical.HiArticalRef;
import com.thinkgem.jeesite.modules.hihunan.model.artical.HiArticalModel;
import com.thinkgem.jeesite.modules.hihunan.model.listshow.HiListShowModel;
import com.thinkgem.jeesite.modules.sys.entity.Dict;

/**
 * hi_articalDAO接口
 * @author yu
 * @version 2016-11-28
 */
@MyBatisDao
public interface HiArticalDao extends CrudDao<HiArtical> {
	/**
	 * 根据父类ID查询文章的标题
	 * @param hiArtical
	 * @return
	 */
	public HiArticalModel getByParentId(HiArtical hiArtical);
	
	/**
	 * 获取美食资讯
	 * @param hiArtical
	 * @return
	 */
	public List<HiArticalModel> getFoodInformation(HiArticalRef hiArticalRef);
	
	
	//获取所有文章的下拉列表集合
	public List<Dict> getHiArticalList();
	
	//添加文章
	public int addHiArtical(HiArtical hiArtical);
	
	//获取所有文章的下拉列表集合
	public List<HiArticalModel> getList(HiArtical hiArtical);
	
	
	public List<HiArticalModel> getZxList(HiArtical hiArtical);
	
	/**
	 * 获取美食资讯
	 * @param hiArtical
	 * @return
	 */
	public List<HiArticalModel> getNews(HiArtical hiArtical);
	
	//审批通过
	public int updateAuditState(@Param("articalIds") String[] articalIds,@Param("auditState") String auditState);
	
	public List<HiListShowModel> getHeadRestaunant(HiArtical hiArtical);
	
	public void addNews(List<HiArtical> list);
	
	/**
	 * 查询待删除的新闻资讯
	 * @param hiArtical
	 * @return
	 */
	public List<HiArticalModel> getWaitDeleteNews(HiArtical hiArtical);
	
	/**
	 * 删除资讯新闻
	 * @param hiArtical
	 */
	public void deleteNews(HiArtical hiArtical);
	
	/**
	 * 获取未下载图片的新闻
	 * @return
	 */
	public List<HiArticalModel> getUnLoadImage();
	
	/**
	 * 下载完图片后更新新闻信息
	 * @param hiArtical
	 */
	public void updateNewsInfo(HiArtical hiArtical);
}