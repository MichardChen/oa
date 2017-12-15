package com.thinkgem.jeesite.modules.constructcost.member.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.constructcost.member.model.CcPrjUserMapModel;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.dao.RoleDao;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.dao.UserFavoriteDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.entity.UserFavorite;
import com.thinkgem.jeesite.modules.sys.model.OfficeModel;
import com.thinkgem.jeesite.modules.sys.model.RoleModel;
import com.thinkgem.jeesite.modules.sys.model.UserModel;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 成员信息service
 * 
 * @author chen_qiancheng
 * @since 2015/12/13
 * 
 */
@Service
@Transactional(readOnly = false)
public class MemberuserService extends CrudService<UserDao,User>{
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private OfficeDao officeDao;
	
	@Autowired
	private UserFavoriteDao userFavoriteDao;
	
	
	public String getEnnameByLoginId(String loginId){
		return roleDao.getEnnameByLoginId(loginId);
	}
	
	public List<UserModel> getMemberuserListByCompanyId(String companyId){
		return userDao.getMemberuserListByCompanyId(companyId);
	}
	
	public List<UserModel> getMemberuserListByLoginId(String loginId){
		return userDao.getMemberuserListByLoginId(loginId);
	}
	
	public OfficeModel getOfficeModelByLoginId(String loginId){
		return officeDao.getOfficeModelByLoginId(loginId);
	}
	
	public List<RoleModel> getRoleList(){
		return roleDao.getRoleList();
	}
	/*
	 * 获取角色列表，隐去系统管理员
	 */
	public List<RoleModel> getRoleListForMember(){
		
		return roleDao.getRoleListForMember();
	}
	
	public void deleteUser(User user){
		userDao.delete(user);
		UserUtils.clearCache(user);//清除被删用户的缓存
	}
	
	@Transactional(readOnly = false)
	public String saveUser(User user) throws ValidationException{
		if (user.getIsNewRecord()){
			user.preInsert();
			//新增时默认密码为123456 并加密
			String password = SystemService.entryptPassword("123456");
			user.setPassword(password);
			userDao.insert(user);
			UserModel userModel = userDao.findByAutoIncId(user.getAutoIncId());
			user.setId(userModel.getId());
			userDao.insertUserRole(user);
			UserFavorite userFavorite = new UserFavorite();
			userFavorite.setUserId(userModel.getId());
			userFavorite.preInsert();
			userFavoriteDao.insert(userFavorite);
			UserUtils.clearCache(user);
			return userModel.getIdFmDB();
		}else{
			// 更新用户数据
			user.preUpdate();
			user.setId(user.getIdFmDB());
			userDao.update(user);
			if (StringUtils.isNotBlank(user.getId())){
				// 更新用户与角色关联
				userDao.deleteUserRole(user);
				if (user.getRoleList() != null && user.getRoleList().size() > 0){
					userDao.insertUserRole(user);
				}else{
					throw new ServiceException(user.getLoginName() + "没有设置角色！");
				}
				// 清除用户缓存
				UserUtils.clearCache(user);
				// 清除权限缓存
				//systemRealm.clearAllCachedAuthorizationInfo();
			}
			return user.getIdFmDB();
		}
		
	}
	
	//通过会员ID 查询所有用户角色信息列表
	public List<UserModel> findByNameAndRole(String companyId){
		return userDao.findByNameAndRole(companyId);
	}
	
	//根据工程ID查询工程的所有用户和角色 
	//public List<CcPrjUserMapModel> findByProjectId(String projectId){
		//return userDao.findByProjectId(projectId);
	//}
	
	//根据userId查询该用户的角色英文名
	public String findSysRoleEnameByUserId(String userId){
		return roleDao.getEnnameByLoginId(userId);
	}
	
}
