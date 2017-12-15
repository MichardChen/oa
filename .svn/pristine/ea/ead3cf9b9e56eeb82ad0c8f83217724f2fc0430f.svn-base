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
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.modules.sys.dao.DictDao;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.model.DictModel;
import com.thinkgem.jeesite.modules.sys.model.SysCodeListLevelThreeModel;
import com.thinkgem.jeesite.modules.sys.model.SysCodeListLevelTwoModel;
import com.thinkgem.jeesite.modules.sys.model.SysCodeListModel;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 字典Service
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class DictService extends CrudService<DictDao, Dict> {
	
	@Autowired
	private DictDao dictDao;
	/**
	 * 查询字段类型列表
	 * @return
	 */
	public List<Dict> findTypeList(){
		return dao.findTypeList(new Dict());
	}

	@Override
	@Transactional(readOnly = false)
	public void save(Dict dict) {
		super.save(dict);
		CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Dict dict) {
		super.delete(dict);
		CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
	}
	
	//根据数据类型查询数据
	public SysCodeListModel getDataByType(String codeType){
		SysCodeListModel result = new SysCodeListModel();
		SysCodeListLevelTwoModel numTwoData = new SysCodeListLevelTwoModel();
		List<SysCodeListLevelThreeModel> dataList = dictDao.getDataByType(codeType);
		for(int i=0;i<dataList.size();i++){
			numTwoData.getData().add(dataList.get(i));
		}
		
		result.setData(numTwoData);
		return result;
	}
	
	@Transactional(readOnly = true)
	public List<DictModel> getListByCodeType(String codeType) throws ValidationException{
		List<DictModel> list = new ArrayList<DictModel>();
		try {
			list = dictDao.getListByCodeType(codeType);
		} catch (Exception e) {
			list = null;
			throw new ValidationException(e.toString());
		}
		return list;
	}
	//根据type查询字典表数据集合
	@Transactional(readOnly = true)
	public List<Dict> getListByType(String type) throws ValidationException{
		List<Dict> list = new ArrayList<Dict>();
		try {
			list = dictDao.getListByType(type);
		} catch (Exception e) {
			list = null;
			throw new ValidationException(e.toString());
		}
		return list;
	}
}
