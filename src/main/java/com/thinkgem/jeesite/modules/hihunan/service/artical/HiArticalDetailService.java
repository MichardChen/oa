/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hihunan.service.artical;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.hihunan.entity.artical.HiArticalDetail;
import com.thinkgem.jeesite.modules.hihunan.dao.artical.HiArticalDao;
import com.thinkgem.jeesite.modules.hihunan.dao.artical.HiArticalDetailDao;
import com.thinkgem.jeesite.modules.sys.entity.Dict;

/**
 * 文章明细管理Service
 * @author llin
 * @version 2016-12-01
 */
@Service
@Transactional(readOnly = true)
public class HiArticalDetailService extends CrudService<HiArticalDetailDao, HiArticalDetail> {

	@Autowired
	private HiArticalDao hiArticalDao;
	
	public HiArticalDetail get(String id) {
		return super.get(id);
	}
	
	public List<HiArticalDetail> findList(HiArticalDetail hiArticalDetail) {
		return super.findList(hiArticalDetail);
	}
	
	public Page<HiArticalDetail> findPage(Page<HiArticalDetail> page, HiArticalDetail hiArticalDetail) {
		return super.findPage(page, hiArticalDetail);
	}
	
	@Transactional(readOnly = false)
	public void save(HiArticalDetail hiArticalDetail) {
		super.save(hiArticalDetail);
	}
	
	@Transactional(readOnly = false)
	public void delete(HiArticalDetail hiArticalDetail) {
		super.delete(hiArticalDetail);
	}
	
	//获取所有文章的下拉列表集合
	@Transactional
	public List<Dict> getHiArticalList() throws ValidationException{
		List<Dict> list = new ArrayList<Dict>();
		try {
			list = hiArticalDao.getHiArticalList();
		} catch (Exception e) {
			list = null;
			throw new ValidationException(e.toString());
		}
		return list;
	}
}