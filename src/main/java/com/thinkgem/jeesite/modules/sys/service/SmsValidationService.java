package com.thinkgem.jeesite.modules.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.constructcost.member.model.SmsValidationModel;
import com.thinkgem.jeesite.modules.sys.dao.SmsValidationDao;
import com.thinkgem.jeesite.modules.sys.entity.SmsValidation;

@Service
public class SmsValidationService extends
		CrudService<SmsValidationDao, SmsValidation> {

	@Autowired
	private SmsValidationDao smsValidationDao;

	@Transactional(readOnly = false)
	public String saveData(SmsValidation smsValidation)
			throws ValidationException {
		String flag;
		try {
			smsValidation.preInsert();
			smsValidationDao.insert(smsValidation);
			flag = "success";
		} catch (Exception e) {
			flag = "error";
			throw new ValidationException(e.toString());
		}
		return flag;
	}
	
	@Transactional(readOnly = false)
	public int updateData(SmsValidation smsValidation) throws ValidationException{
		int flag;
		try {
			smsValidation.preUpdate();
			flag = smsValidationDao.update(smsValidation);
		} catch (Exception e) {
			flag = -1;
			throw new ValidationException(e.toString());
		}
		return flag;
	}
	
	@Transactional(readOnly = true)
	public SmsValidationModel findCode(SmsValidation smsValidation) throws ValidationException{
		SmsValidationModel smsValidationModel = new SmsValidationModel();
		try {
			smsValidationModel = smsValidationDao.findByMobileNo(smsValidation);
		} catch (Exception e) {
			smsValidationModel.setId("-1");
			throw new ValidationException(e.toString());
		}
		return smsValidationModel;
	}

}
