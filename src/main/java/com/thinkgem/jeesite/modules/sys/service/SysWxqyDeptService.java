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
import com.thinkgem.jeesite.modules.sys.dao.SysWxqyDeptDao;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.SysWxqyDept;
import com.thinkgem.jeesite.modules.sys.model.DictModel;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 微信企业号的组织机构Service
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class SysWxqyDeptService extends TreeService<SysWxqyDeptDao, SysWxqyDept> {
	
	@Autowired
	private SysWxqyDeptDao sysWxqyDeptDao;
	
	public List<SysWxqyDept> findAll(){
		return sysWxqyDeptDao.findAllList();
	}
	
	@Transactional(readOnly=false)
	public int insertAll(List<SysWxqyDept> list) {
		sysWxqyDeptDao.deleteAll();
		return sysWxqyDeptDao.insertAll(list);
	}
	
}
