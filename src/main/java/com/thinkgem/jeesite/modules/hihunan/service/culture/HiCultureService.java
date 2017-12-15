/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hihunan.service.culture;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.hihunan.entity.culture.HiCulture;
import com.thinkgem.jeesite.modules.hihunan.dao.culture.HiCultureDao;

/**
 * 文化Service
 * @author yuyabiao
 * @version 2016-11-29
 */
@Service
@Transactional(readOnly = true)
public class HiCultureService extends CrudService<HiCultureDao, HiCulture> {

	public HiCulture get(String id) {
		return super.get(id);
	}
	
	public List<HiCulture> findList(HiCulture hiCulture) {
		return super.findList(hiCulture);
	}
	
	public Page<HiCulture> findPage(Page<HiCulture> page, HiCulture hiCulture) {
		return super.findPage(page, hiCulture);
	}
	
	@Transactional(readOnly = false)
	public void save(HiCulture hiCulture) {
		super.save(hiCulture);
	}
	
	@Transactional(readOnly = false)
	public void delete(HiCulture hiCulture) {
		super.delete(hiCulture);
	}
	
}