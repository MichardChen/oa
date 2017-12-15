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
import com.thinkgem.jeesite.modules.sys.dao.SysParameterDao;
import com.thinkgem.jeesite.modules.sys.entity.SysParameter;
import com.thinkgem.jeesite.modules.sys.model.SysParameterModel;
/**
 * 报表管理Service
 * @author 
 * @version 2015-12-08
 */
@Service
@Transactional(readOnly = true)
public class SysParameterService extends CrudService<SysParameterDao,SysParameter> {
	
	@Autowired
	private SysParameterDao sysParameterDao;
	
	//获取微信消息的固定参数值
	public SysParameterModel findByKeyword(String keyword) throws ValidationException{
		SysParameterModel sysParameterModel = new SysParameterModel();
		try {
			sysParameterModel = sysParameterDao.findByKeyword(keyword);
		} catch (Exception e) {
			sysParameterModel.setId("-1");
			throw new ValidationException(e.toString());
		}
		return sysParameterModel;
	}

	/*查询机器(分页)*/
	@Transactional(readOnly = true)
	public List<SysParameterModel> findByPage(SysParameter sysParameter) throws ValidationException{
		List<SysParameterModel> list = new ArrayList<SysParameterModel>();
		try {
			list = sysParameterDao.findByPage(sysParameter);
		} catch (Exception e) {
			list = null;
			throw new ValidationException(e.toString());
		}
		return list;
	}
	
}

