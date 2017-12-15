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
import com.thinkgem.jeesite.modules.hihunan.dao.artical.HiArticalDao;
import com.thinkgem.jeesite.modules.hihunan.dao.artical.HiArticalDetailDao;
import com.thinkgem.jeesite.modules.hihunan.dao.artical.hiArticalRefDao;
import com.thinkgem.jeesite.modules.hihunan.entity.artical.HiArtical;
import com.thinkgem.jeesite.modules.hihunan.entity.artical.HiArticalRef;
import com.thinkgem.jeesite.modules.hihunan.model.artical.HiArticalModel;

/**
 * hi_articalService
 * 
 * @author yu
 * @version 2016-11-28
 */
@Service
@Transactional(readOnly = true)
public class HiArticalService extends CrudService<HiArticalDao, HiArtical> {

	@Autowired
	private HiArticalDao hiArticalDao;

	@Autowired
	private HiArticalDetailDao hiArticalDetailDao;

	@Autowired
	private hiArticalRefDao hiArticalRefDao;

	public HiArtical get(String id) {
		return super.get(id);
	}

	public List<HiArtical> findList(HiArtical hiArtical) {
		return super.findList(hiArtical);
	}

	public Page<HiArtical> findPage(Page<HiArtical> page, HiArtical hiArtical) {
		return super.findPage(page, hiArtical);
	}

	@Transactional(readOnly = false)
	public void save(HiArtical hiArtical) {
		super.save(hiArtical);
	}

	@Transactional(readOnly = false)
	public void delete(HiArtical hiArtical) {
		super.delete(hiArtical);
	}

	/**
	 * 根据所属的父类ID查询文章标题/内容等信息
	 * 
	 * @param hiArtical
	 * @return
	 * @throws ValidationException
	 */
	@Transactional(readOnly = true)
	public HiArticalModel findByParentId(HiArtical hiArtical)
			throws ValidationException {
		HiArticalModel hiArticalModel = new HiArticalModel();
		try {
			hiArticalModel = hiArticalDao.getByParentId(hiArtical);
		} catch (Exception e) {
			hiArticalModel.setId("-1");
			throw new ValidationException(e.toString());
		}
		return hiArticalModel;
	}

	/**
	 * 根据所属的ID 查询美食资讯 文章标题/内容等信息
	 * 
	 * @param hiArtical
	 * @return
	 * @throws ValidationException
	 */
	@Transactional(readOnly = true)
	public List<HiArticalModel> findFoodInformation(HiArticalRef hiArticalRef)
			throws ValidationException {
		List<HiArticalModel> detailList = new ArrayList<HiArticalModel>();
		try {
			detailList = hiArticalDao.getFoodInformation(hiArticalRef);

		} catch (Exception e) {

			throw new ValidationException(e.toString());
		}
		return detailList;
	}

	// 添加文章
	@Transactional(readOnly = false)
	public int addHiArtical(HiArtical hiArtical) throws ValidationException {
		int flag = 0;
		try {
			hiArtical.preInsert();
			flag = hiArticalDao.insert(hiArtical);
		} catch (Exception e) {
			flag = -1;
			throw new ValidationException(e.toString());
		}
		return flag;
	}

	// 获取商家关联的所有文章
	@Transactional(readOnly = true)
	public List<HiArticalModel> getListByReferenceId(HiArticalRef hiArticalRef)
			throws ValidationException {
		List<HiArticalModel> list = new ArrayList<HiArticalModel>();
		try {
			list = hiArticalRefDao.getListByReferenceId(hiArticalRef);
		} catch (Exception e) {
			list = null;
			throw new ValidationException(e.toString());
		}
		return list;
	}

	// 获取所有文章
	@Transactional(readOnly = true)
	public List<HiArticalModel> getList(HiArtical hiArtical)
			throws ValidationException {
		List<HiArticalModel> list = new ArrayList<HiArticalModel>();
		try {
			list = hiArticalDao.getList(hiArtical);
		} catch (Exception e) {
			list = null;
			throw new ValidationException(e.toString());
		}
		return list;
	}

	@Transactional(readOnly = true)
	public List<HiArticalModel> getZxList(HiArtical hiArtical)
			throws ValidationException {
		List<HiArticalModel> list = new ArrayList<HiArticalModel>();
		try {
			list = hiArticalDao.getZxList(hiArtical);
		} catch (Exception e) {
			list = null;
			throw new ValidationException(e.toString());
		}
		return list;
	}

	/**
	 * 根据类型查询新闻、教育
	 * 
	 * @param hiArtical
	 * @return
	 * @throws ValidationException
	 */
	@Transactional(readOnly = true)
	public List<HiArticalModel> findNews(HiArtical hiArtical)
			throws ValidationException {
		List<HiArticalModel> detailList = new ArrayList<HiArticalModel>();
		try {
			detailList = hiArticalDao.getNews(hiArtical);
		} catch (Exception e) {

			throw new ValidationException(e.toString());
		}
		return detailList;
	}

}