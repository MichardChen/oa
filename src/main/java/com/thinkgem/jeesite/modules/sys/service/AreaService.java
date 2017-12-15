/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.service.TreeService;
import com.thinkgem.jeesite.modules.sys.dao.AreaDao;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.model.DictModel;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 区域Service
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class AreaService extends TreeService<AreaDao, Area> {
	
	@Autowired
	private AreaDao areaDao;

	public List<Area> findAll(){
		return UserUtils.getAreaList();
	}

	@Transactional(readOnly = false)
	public void save(Area area) {
		super.save(area);
		UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(Area area) {
		super.delete(area);
		UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
	}
	
	@Transactional(readOnly = true)
	public List<DictModel> findCity() throws ValidationException{
		List<DictModel> cityList = new ArrayList<DictModel>();
		try {
			cityList = areaDao.findCity();
		} catch (Exception e) {
			cityList = null;
			throw new ValidationException(e.toString());
		}
		return cityList;
	}
	
	@Transactional(readOnly = true)
	public List<DictModel> findProvince() throws ValidationException{
		List<DictModel> provinceList = new ArrayList<DictModel>();
		try {
			provinceList = areaDao.findProvince();
		} catch (Exception e) {
			provinceList = null;
			throw new ValidationException(e.toString());
		}
		return provinceList;
	}
}
