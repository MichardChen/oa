/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.dao.SysRptInfoDao;
import com.thinkgem.jeesite.modules.sys.entity.SysRptInfo;
import com.thinkgem.jeesite.modules.sys.model.SysRptInfoModel;

/**
 * 报表管理Service
 * @author 
 * @version 2015-12-08
 */
@Service
@Transactional(readOnly = true)
public class SysRptInfoService extends CrudService<SysRptInfoDao,SysRptInfo> {
	
	@Autowired
	private SysRptInfoDao sysRptInfoDao;
	
	//获取所有报表类型数据
	public List<SysRptInfoModel> getCcRptInfoList(){
		return sysRptInfoDao.getCcRptInfoList();
	}
	
	//按报表名称查询方法定义
	@Override
	public Page<SysRptInfo> findPage(Page<SysRptInfo> page, SysRptInfo ccRptInfo) {
		ccRptInfo.setPage(page);
		page.setList(sysRptInfoDao.findList(ccRptInfo));
		return page;
	}
	
	// 删除
	@Override
	@Transactional(readOnly = false)
	public void delete(SysRptInfo ccRptInfo) throws ValidationException {
		try {
			super.delete(ccRptInfo);
		} catch (Exception e) {
			throw new ValidationException(e.toString());
		}
	}
		
	// 根据自增长ID获得插入成功的数据的主键ID
	public String findId(int autoIncId) throws ValidationException {
		String stn;
		try {
			stn = sysRptInfoDao.findByAutoIncId(autoIncId);
		} catch (Exception e) {
			stn = "-1";
			throw new ValidationException(e.toString());
		}
		return stn;
	}	
	//判断插入或者更新
	@Override
	@Transactional(readOnly = false)
	public void save(SysRptInfo ccRptInfo) {
		if("0".equals(ccRptInfo.getInsertFlag())){
			ccRptInfo.setId("");
		}else{
			ccRptInfo.setId(ccRptInfo.getIdFmDB());
		}
		super.save(ccRptInfo);
	}
		
	//修改文件名和路径
	@Transactional(readOnly = false)
	public String updateRptFileName(SysRptInfo ccRptInfo) throws ValidationException{
		String flag;
		try {
			sysRptInfoDao.updateRptFileName(ccRptInfo);
			flag = "0";
		} catch (Exception e) {
			flag = "-1";
			throw new ValidationException(e.toString());
		}
		return flag;
		
	}
}

