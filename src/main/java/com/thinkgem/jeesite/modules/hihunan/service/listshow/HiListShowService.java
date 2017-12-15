package com.thinkgem.jeesite.modules.hihunan.service.listshow;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.hihunan.dao.listshow.HiListShowDao;
import com.thinkgem.jeesite.modules.hihunan.entity.listshow.HiListShow;
import com.thinkgem.jeesite.modules.hihunan.model.listshow.HiListShowModel;
import com.thinkgem.jeesite.modules.hihunan.service.localactivity.HiLocalActivityService;

@Service
@Transactional(readOnly = true)
public class HiListShowService extends CrudService<HiListShowDao, HiListShow>{
	
	@Autowired
	private HiListShowDao hiListShowDao;
	@Autowired
	private HiLocalActivityService hiLocalActivityService;
	
	public HiListShow get(String id) {
		return super.get(id);
	}
	
	public List<HiListShow> findList(HiListShow hiListShow) {
		return super.findList(hiListShow);
	}
	
	public Page<HiListShow> findPage(Page<HiListShow> page, HiListShow hiListShow) {
		return super.findPage(page, hiListShow);
	}
	
	@Transactional(readOnly = false)
	public void save(HiListShow hiListShow) {
		super.save(hiListShow);
	}
	
	@Transactional(readOnly = false)
	public void delete(HiListShow hiListShow) {
		super.delete(hiListShow);
	}
	
	/**
	 * 查询一览列表
	 * @param hiListShow
	 * @return
	 * @throws ValidationException
	 */
	@Transactional(readOnly = true)
	public List<HiListShowModel> findAll(HiListShow hiListShow) throws ValidationException{
		List<HiListShowModel> list = new ArrayList<HiListShowModel>();
		try {
			
				list = hiListShowDao.findAll(hiListShow);

		} catch (Exception e) {
			list = null;
			throw new ValidationException(e.toString());
		}
		return list;
	}
	
	

}
