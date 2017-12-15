package com.thinkgem.jeesite.modules.data.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.model.RoleModel;
import com.thinkgem.jeesite.modules.sys.model.UserModel;
import com.thinkgem.jeesite.pj_common.persistence.ns.dao.NsPartnerCompanyDao;
import com.thinkgem.jeesite.pj_common.persistence.ns.dao.NsRoleDao;
import com.thinkgem.jeesite.pj_common.persistence.ns.dao.NsUserDao;

@Service
public class WorkFlowUserService {
	
	@Autowired
	private NsUserDao nsUserDao;
	
	@Autowired
	private NsRoleDao nsRoleDao;
	
	@Autowired
	private NsPartnerCompanyDao nsPartnerCompanyDao;
 	
	/**
	 * 单位唯一
	 * @param enname 角色英文标识
	 * @param type   单位类型
	 * @return 负责人信息
	 */
	@Transactional(readOnly = true)
	public UserModel findContactsByTypeAndRole(String enname,String type) throws ValidationException{
		UserModel userModel = new UserModel();
		try {
			userModel = nsUserDao.getContactsByTypeAndRole(enname, type);
		} catch (Exception e) {
			userModel.setId("-1");
			throw new ValidationException(e.toString());
		}
		return userModel;
	}
	
	/**
	 * 单位ID已知
	 * @param enname 角色英文标识
	 * @param companyId 公司ID
	 * @return 负责人信息
	 */
	@Transactional(readOnly = true)
	public UserModel  findContactsByCompanyIdAndRole(String enname,String partnerCompanyId) throws ValidationException{
		UserModel userModel = new UserModel();
		try {
			userModel = nsUserDao.getContactsByCompanyIdAndRole(enname, partnerCompanyId);
		} catch (Exception e) {
			userModel.setId("-1");
			throw new ValidationException(e.toString());
		}
		return userModel;
	}
	
	/**
	 * 查询部门下的所有人员
	 * @param officeId 部门ID，当为空时查询所有部门的人员
	 * @param userId 当前用户ID,如需查询包括自己在内的传“”或者NULL
	 * @return 
	 */
	@Transactional(readOnly = true)
	public List<UserModel>  findAllUser(String officeId,String userId) throws ValidationException{
		List<UserModel> list = new ArrayList<UserModel>();
		try {
			list = nsUserDao.getAllUser(officeId,userId);
		} catch (Exception e) {
			list = null;
			throw new ValidationException(e.toString());
		}
		return list;
	}
	
	/**
	 * 装修故障负责人
	 * @param enname 角色英文标识
	 * @param macId 机具的ID
	 * @return 负责人信息
	 */
	@Transactional(readOnly = true)
	public UserModel findDecorationContacts(String enname,String macId) throws ValidationException{
		UserModel userModel = new UserModel();
		try {
			userModel = nsUserDao.getDecorationContacts(enname, macId);
		} catch (Exception e) {
			userModel.setId("-1");
			throw new ValidationException(e.toString());
		}
		return userModel;
	}
	
	/**
	 * 机器故障负责人
	 * @param enname 角色英文标识
	 * @param macId 机具的ID
	 * @return 负责人信息
	 */
	@Transactional(readOnly = true)
	public UserModel findMachineContacts(String enname,String macId) throws ValidationException{
		UserModel userModel = new UserModel();
		try {
			userModel = nsUserDao.getMachineContacts(enname, macId);
		} catch (Exception e) {
			userModel.setId("-1");
			throw new ValidationException(e.toString());
		}
		return userModel;
	}
	
	/**
	 * 清除用户登录Token
	 * @param user
	 * @return
	 * @throws ValidationException
	 */
	@Transactional(readOnly = false)
	public int clearToken(User user) throws ValidationException{
		int flag = 0;
		try {
			nsUserDao.clearToken(user);
		} catch (Exception e) {
			flag = -1;
			throw new ValidationException(e.toString());
		}
		return flag;
	}
	
	/**
	 * 通过部门ID查询所匹配的角色列表
	 * @param officeId
	 * @return
	 * @throws ValidationException
	 */
	@Transactional(readOnly = true)
	public List<RoleModel> findRoles(String officeId) throws ValidationException{
		List<RoleModel> list = new ArrayList<RoleModel>();
		try {
			String enname = nsPartnerCompanyDao.getRoleKeysByOfficeId(officeId);
			if(enname != null && !"".equals(enname)){
				String[] ennames = enname.split(",");
				list = nsRoleDao.getRolesByEnnames(ennames);
			}
		} catch (Exception e) {
			list = null;
			throw new ValidationException(e.toString());
		}
		return list;
	}
	
	@Transactional(readOnly = true)
	public UserModel findBankOperatorLeader() throws ValidationException{
		UserModel userModel = new UserModel();
		try {
			userModel = nsUserDao.getBankOperatorLeader();
		} catch (Exception e) {
			userModel.setId("-1");
			throw new ValidationException(e.toString());
		}
		return userModel;
	}
	
	@Transactional(readOnly = true)
	public List<UserModel> findNsAllUser() throws ValidationException{
		List<UserModel> list = new ArrayList<UserModel>();
		try {
			list = nsUserDao.getNsAllUser();
		} catch (Exception e) {
			list = null;
			throw new ValidationException(e.toString());
		}
		return list;
	}
}
