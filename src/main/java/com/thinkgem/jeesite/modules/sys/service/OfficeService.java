/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.TreeService;
import com.thinkgem.jeesite.modules.ns.data.model.NsPartnerCompanyModel;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.pj_common.persistence.ns.dao.NsPartnerCompanyDao;
import com.thinkgem.jeesite.pj_common.persistence.ns.entity.NsPartnerCompany;

/**
 * 机构Service
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class OfficeService extends TreeService<OfficeDao, Office> {
	
	@Autowired
	private OfficeDao officeDao;
	
	@Autowired
	private NsPartnerCompanyDao nsPartnerCompanyDao;

	public List<Office> findAll(){
		return UserUtils.getOfficeList();
	}

	public List<Office> findList(Boolean isAll){
		if (isAll != null && isAll){
			return UserUtils.getOfficeAllList();
		}else{
			return UserUtils.getOfficeList();
		}
	}
	
	@Transactional(readOnly = true)
	public List<Office> findList(Office office){
		office.setParentIds(office.getParentIds()+"%");
		return dao.findByParentIdsLike(office);
	}
	
	@Transactional(readOnly = false)
	public void save(Office office) {
		if(office.getId()== null || "".equals(office.getId())){//新增
			String businessType = office.getBusinessType();
			super.save(office);
			UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
			if(!"1".equals(office.getType())){//如果添加机构类型非公司类型
				office = officeDao.findByAutoIncId(office.getAutoIncId());
				//农商行专用，添加一个机构对应添加一个相关单位
				NsPartnerCompany nsPartnerCompany = new NsPartnerCompany();
				nsPartnerCompany.setName(office.getName());
				nsPartnerCompany.setType(businessType);
				nsPartnerCompany.setOfficeId(office.getId());
				nsPartnerCompany.preInsert();
				nsPartnerCompanyDao.insert(nsPartnerCompany);
			}
		}else{//修改
			super.save(office);
			UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
			if(!"1".equals(office.getType())){//如果修改机构不为公司类型
				NsPartnerCompany nsPartnerCompany = new NsPartnerCompany();
				nsPartnerCompany.setName(office.getName());
				nsPartnerCompany.setOfficeId(office.getId());
				nsPartnerCompany.setType(office.getBusinessType());
				NsPartnerCompanyModel nsPartnerCompanyModel = nsPartnerCompanyDao.getByOfficeId(office.getId());
				if(nsPartnerCompanyModel != null){
					nsPartnerCompany.preUpdate();
					nsPartnerCompanyDao.update(nsPartnerCompany);
				}else{
					nsPartnerCompany.preInsert();
					nsPartnerCompanyDao.insert(nsPartnerCompany);
				}
			}else{//如果修改机构为公司类型
				NsPartnerCompanyModel nsPartnerCompanyModel = nsPartnerCompanyDao.getByOfficeId(office.getId());
				if(nsPartnerCompanyModel != null){
					NsPartnerCompany nsPartnerCompany = new NsPartnerCompany();
					nsPartnerCompany.setOfficeId(office.getId());
					nsPartnerCompanyDao.delete(nsPartnerCompany);
				}
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(Office office) {
		super.delete(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
		//农商行专用，删除机构的同时删除相关单位
		NsPartnerCompany nsPartnerCompany = new NsPartnerCompany();
		nsPartnerCompany.setOfficeId(office.getId());
		nsPartnerCompanyDao.delete(nsPartnerCompany);
	}
	
	/**
	 * 根据机构名称获取
	 * @param loginName
	 * @return
	 */
	public Office getByName(String name) {
		return officeDao.getByName(name);
	}
	
}
