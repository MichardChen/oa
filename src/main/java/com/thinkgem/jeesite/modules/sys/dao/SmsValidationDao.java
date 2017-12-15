package com.thinkgem.jeesite.modules.sys.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.constructcost.member.model.SmsValidationModel;
import com.thinkgem.jeesite.modules.sys.entity.SmsValidation;

@MyBatisDao
public interface SmsValidationDao extends CrudDao<SmsValidation> {
	
	public SmsValidationModel findByMobileNo(SmsValidation smsValidation);

}
