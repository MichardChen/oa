/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hihunan.service.restaunant;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.hihunan.dao.artical.HiArticalDao;
import com.thinkgem.jeesite.modules.hihunan.dao.artical.hiArticalRefDao;
import com.thinkgem.jeesite.modules.hihunan.dao.localactivity.HiLocalActivityDao;
import com.thinkgem.jeesite.modules.hihunan.dao.restaunant.HiRestaunantDao;
import com.thinkgem.jeesite.modules.hihunan.entity.artical.HiArtical;
import com.thinkgem.jeesite.modules.hihunan.entity.artical.HiArticalRef;
import com.thinkgem.jeesite.modules.hihunan.entity.restaunant.HiRestaunant;
import com.thinkgem.jeesite.modules.hihunan.model.listshow.HiListShowModel;
import com.thinkgem.jeesite.modules.hihunan.model.restaunant.HiRestaunantModel;

/**
 * 美食Service
 * @author lin
 * @version 2016-12-02
 */
@Service
@Transactional(readOnly = true)
public class HiRestaunantService extends CrudService<HiRestaunantDao, HiRestaunant> {

	@Autowired
	private HiRestaunantDao hiRestaunantDao;
	@Autowired
	private hiArticalRefDao hiArticalRefDao;
	@Autowired
	private HiArticalDao hiArticalDao;
	@Autowired
	private HiLocalActivityDao hiLocalActivityDao;
	
	public HiRestaunant get(String id) {
		return super.get(id);
	}
	
	public List<HiRestaunant> findList(HiRestaunant hiRestaunant) {
		return super.findList(hiRestaunant);
	}
	
	public Page<HiRestaunant> findPage(Page<HiRestaunant> page, HiRestaunant hiRestaunant) {
		return super.findPage(page, hiRestaunant);
	}
	
	@Transactional(readOnly = false)
	public void save(HiRestaunant hiRestaunant) {
		super.save(hiRestaunant);
	}
	
	@Transactional(readOnly = false)
	public void delete(HiRestaunant hiRestaunant) {
		super.delete(hiRestaunant);
	}
	
	@Transactional(readOnly = true)
	public List<HiRestaunantModel> getAll(HiRestaunant hiRestaunant) {
		List<HiRestaunantModel> list = new ArrayList<HiRestaunantModel>();
		try {
			list = hiRestaunantDao.getAll(hiRestaunant);
		} catch (Exception e) {
			list = null;
			throw new ValidationException(e.toString());
		}
		return list;
	}

	@Transactional(readOnly = true)
	public List<HiListShowModel> getRecomentRestaunant(HiArtical hiArtical) {
		List<HiListShowModel> list = new ArrayList<HiListShowModel>();
		try {
			list = hiArticalDao.getHeadRestaunant(hiArtical);
		} catch (Exception e) {
			list = null;
			throw new ValidationException(e.toString());
		}
		return list;
	}

	@Transactional(readOnly = true)
	public HiRestaunantModel getRestaunantDetail(HiRestaunant hiRestaunant) {
		HiRestaunantModel restaunantModel = new HiRestaunantModel();
		try {
			restaunantModel = hiRestaunantDao.getModel(hiRestaunant);
		} catch (Exception e) {
			restaunantModel = null;
			throw new ValidationException(e.toString());
		}
		return restaunantModel;
	}
	//餐厅
	public HiRestaunantModel findRestaunant(HiRestaunant hiRestaunant,String key) {
		HiRestaunantModel restaunantModel = new HiRestaunantModel();
		try {
			if(StringUtils.isNotBlank(key)){
				hiRestaunant.setMemberId(hiLocalActivityDao.getMemberInfo(key).getId());
				restaunantModel =  hiRestaunantDao.findRestaunantAndIsCollect(hiRestaunant);
			}else{
				restaunantModel =  hiRestaunantDao.findRestaunant(hiRestaunant);
			}
		} catch (Exception e) {
			restaunantModel = null;
			throw new ValidationException(e.toString());
		}
		
		return restaunantModel;
	}
	
	//添加引用文章关系表
	@Transactional(readOnly = false)
	public int addHiArticalMap (String referenceId,String[] articalIds) throws ValidationException{
		int flag = 0;
		try {
			for(String articalId :articalIds){
				HiArticalRef hiArticalRef  = new HiArticalRef();
				hiArticalRef.setArticalId(articalId);
				hiArticalRef.setReferenceId(referenceId);
				hiArticalRef.preInsert();
				flag = hiArticalRefDao.addHiArticalMap(hiArticalRef);
			}
		} catch (Exception e) {
			flag = -1;
			throw new ValidationException(e.toString());
		}
		return flag;
	}
	
	//删除引用文章关系
	@Transactional(readOnly = false)
	public int deleteHiArticalMap (HiArticalRef hiArticalRef) throws ValidationException{
		int flag = 0;
		try {
			flag = hiArticalRefDao.deleteHiArticalMap(hiArticalRef);
		} catch (Exception e) {
			flag = -1;
			throw new ValidationException(e.toString());
		}
		return flag;
	}
	
	//添加引用文章关系表
	@Transactional(readOnly = false)
	public int updateAuditState (String[] articalIds,String auditState) throws ValidationException{
		int flag = 0;
		try {
			flag = hiArticalDao.updateAuditState(articalIds,auditState);
			
		} catch (Exception e) {
			flag = -1;
			throw new ValidationException(e.toString());
		}
		return flag;
	}
}