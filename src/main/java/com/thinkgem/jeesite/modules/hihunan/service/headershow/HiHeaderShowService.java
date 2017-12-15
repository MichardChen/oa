/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hihunan.service.headershow;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.hihunan.entity.headershow.HiHeaderShow;
import com.thinkgem.jeesite.modules.hihunan.model.herdershow.HiHeaderShowModel;
import com.thinkgem.jeesite.modules.hihunan.dao.headershow.HiHeaderShowDao;

/**
 * 头部轮播Service
 * @author yuyabiao
 * @version 2016-11-30
 */
@Service
@Transactional(readOnly = true)
public class HiHeaderShowService extends CrudService<HiHeaderShowDao, HiHeaderShow> {
	
	@Autowired
	public HiHeaderShowDao hiHeaderShowDao;

	public HiHeaderShow get(String id) {
		return super.get(id);
	}
	
	public List<HiHeaderShow> findList(HiHeaderShow hiHeaderShow) {
		return super.findList(hiHeaderShow);
	}
	
	public Page<HiHeaderShow> findPage(Page<HiHeaderShow> page, HiHeaderShow hiHeaderShow) {
		return super.findPage(page, hiHeaderShow);
	}
	
	@Transactional(readOnly = false)
	public void save(HiHeaderShow hiHeaderShow) {
		super.save(hiHeaderShow);
	}
	
	@Transactional(readOnly = false)
	public void delete(HiHeaderShow hiHeaderShow) {
		super.delete(hiHeaderShow);
	}
	
	/**
	 * 查询全部轮播
	 * @param hiHeaderShow
	 * @return
	 * @throws ValidationException
	 */
	@Transactional(readOnly = true)
	public List<HiHeaderShowModel> findAll(HiHeaderShow hiHeaderShow) throws ValidationException{
		List<HiHeaderShowModel> list = new ArrayList<HiHeaderShowModel>();
		try {
			list = hiHeaderShowDao.getAll(hiHeaderShow);
		} catch (Exception e) {
			list = null;
			throw new ValidationException(e.toString());
		}
		return list;
	}
	
}