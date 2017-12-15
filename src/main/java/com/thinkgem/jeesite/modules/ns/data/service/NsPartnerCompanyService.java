package com.thinkgem.jeesite.modules.ns.data.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.ns.data.model.NsPartnerCompanyModel;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.pj_common.persistence.ns.dao.NsPartnerCompanyDao;
import com.thinkgem.jeesite.pj_common.persistence.ns.entity.NsPartnerCompany;
/**
 * 相关单位Service, 2016/5/10
 * @author yuyabiao
 *
 */
@Service
public class NsPartnerCompanyService extends CrudService<NsPartnerCompanyDao, NsPartnerCompany>{
	
	@Autowired
	private NsPartnerCompanyDao nsPartnerCompanyDao;
	
	/**
	 * 根据单位类型查询单位
	 * @param List<NsPartnerCompanyModel> types 单位类型的list集合
	 * @return 对应类型的单位信息
	 */
	@Transactional(readOnly = true)
	public List<NsPartnerCompanyModel> findAllCompany(List<NsPartnerCompanyModel> types) throws ValidationException{
		List<NsPartnerCompanyModel> list = new ArrayList<NsPartnerCompanyModel>();
		try {
			list = nsPartnerCompanyDao.findAllCompany(types);
		} catch (Exception e) {
			list = null;
			throw new ValidationException(e.toString());
		}
		return list;
	}
	
	/**
	 * 查询所有外协公司的单位
	 * @return 对应类型的单位信息
	 */
	@Transactional(readOnly = true)
	public List<Dict> findoutsourcingCompany() throws ValidationException{
		List<Dict> list = new ArrayList<Dict>();
		try {
			list = nsPartnerCompanyDao.findoutsourcingCompany();
		} catch (Exception e) {
			list = null;
			throw new ValidationException(e.toString());
		}
		return list;
	}
	
	@Transactional(readOnly = true)
	public List<NsPartnerCompanyModel> findCompany(NsPartnerCompany nsPartnerCompany) throws ValidationException{
		List<NsPartnerCompanyModel> list = new ArrayList<NsPartnerCompanyModel>();
		try {
			list = nsPartnerCompanyDao.getCompany(nsPartnerCompany);
		} catch (Exception e) {
			list = null;
			throw new ValidationException(e.toString());
		}
		return list;
	}
	
	@Transactional(readOnly = true)
	public NsPartnerCompany getByName(String name) throws ValidationException{
		NsPartnerCompany nsPartnerCompany = new NsPartnerCompany();
		try {
			nsPartnerCompany = nsPartnerCompanyDao.getByName(name);
		} catch (Exception e) {
			nsPartnerCompany = null;
			throw new ValidationException(e.toString());
		}
		return nsPartnerCompany;
	}
	
	@Transactional(readOnly = true)
	public NsPartnerCompanyModel findById(String id) throws ValidationException{
		NsPartnerCompanyModel nsPartnerCompanyModel = new NsPartnerCompanyModel();
		try {
			nsPartnerCompanyModel = nsPartnerCompanyDao.findById(id);
		} catch (Exception e) {
			nsPartnerCompanyModel.setId("-1");
		}
		return nsPartnerCompanyModel;
	}
	
	@Transactional(readOnly = true)
	public NsPartnerCompanyModel findByType(String type) throws ValidationException{
		NsPartnerCompanyModel nsPartnerCompanyModel = new NsPartnerCompanyModel();
		try {
			nsPartnerCompanyModel = nsPartnerCompanyDao.findByType(type);
		} catch (Exception e) {
			nsPartnerCompanyModel.setId("-1");
		}
		return nsPartnerCompanyModel;
	}
	
	@Transactional(readOnly = false)
	public int saveCompany (NsPartnerCompany nsPartnerCompany) throws ValidationException{
		int flag = 0;
		try {
			if(StringUtils.isEmpty(nsPartnerCompany.getId())){
				nsPartnerCompany.preInsert();
				nsPartnerCompanyDao.insert(nsPartnerCompany);
			}else{
				nsPartnerCompany.preUpdate();
				nsPartnerCompanyDao.update(nsPartnerCompany);
			}
		} catch (Exception e) {
			flag = -1;
			throw new ValidationException(e.toString());
		}
		return flag;
	}
	
	@Transactional(readOnly = false)
	public int delCompany(NsPartnerCompany nsPartnerCompany) throws ValidationException{
		int flag = 0;
		try {
			nsPartnerCompanyDao.delete(nsPartnerCompany);
		} catch (Exception e) {
			flag = -1;
			throw new ValidationException(e.toString());
		}
		return flag;
	}
	
	@Transactional(readOnly = true)
	public NsPartnerCompanyModel getByOffice(String officeId) throws ValidationException{
		NsPartnerCompanyModel nsPartnerCompanyModel = new NsPartnerCompanyModel();
		try {
			nsPartnerCompanyModel = nsPartnerCompanyDao.getByOfficeId(officeId);
		} catch (Exception e) {
			nsPartnerCompanyModel.setId("-1");
			throw new ValidationException(e.toString());
		}
		return nsPartnerCompanyModel;
	}

}
