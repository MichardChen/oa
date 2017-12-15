package com.thinkgem.jeesite.modules.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.modules.hihunan.entity.artical.HiArtical;
import com.thinkgem.jeesite.modules.hihunan.service.news.HiNewsService;
import com.thinkgem.jeesite.modules.sys.utils.SysParameterUtils;

/**
 * 日次任务(从湖南大湘网抓取最近1000条的信息)
 * @author
 * @version
 * 去除 // @Lazy(false)
 */
@Service
@Transactional(readOnly = true)
public class ScrapyNewsFromHNQQService extends BaseService{
 
	@Autowired
	private HiNewsService hiNewsService;
	
	/**抓取新闻
	 * */
	@Transactional(readOnly = false)
	public void scrapyNewsFromQQ() {
		int flag = hiNewsService.scrapyNewsFromQQ();
		if(flag == 0){
			hiNewsService.loadImage();
		}
	}
	
	/**
	 * 删除信息
	 */
	@Transactional(readOnly = false)
	public void deleteQQNews(){
		int validDay = Integer.parseInt(SysParameterUtils.findKeyword("newsValidDay").getValue1());
		HiArtical hiArtical = new HiArtical();
		hiArtical.setValidDay(validDay);
		hiNewsService.deleteNews(hiArtical);
	}
   
}
