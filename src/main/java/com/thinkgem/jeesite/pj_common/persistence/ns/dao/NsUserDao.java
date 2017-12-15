package com.thinkgem.jeesite.pj_common.persistence.ns.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.model.UserModel;
/**
 * 查询用户信息,主要针对查询同部门下的人员信息
 * @author yuyabiao
 * @since 2016/6/2
 */
@MyBatisDao
public interface NsUserDao {
	
	/**
	 * 单位唯一
	 * @param enname 角色英文标识
	 * @param type   单位类型
	 * @return 负责人信息
	 */
	public UserModel getContactsByTypeAndRole(@Param("enname")String enname,@Param("type")String type);
	
	/**
	 * 单位ID已知
	 * @param enname 角色英文标识
	 * @param companyId 公司ID
	 * @return 负责人信息
	 */
	public UserModel getContactsByCompanyIdAndRole(@Param("enname")String enname,@Param("partnerCompanyId")String partnerCompanyId);
	
	/**
	 * 装修故障负责人
	 * @param enname 角色英文标识
	 * @param macId 机具的ID
	 * @return 负责人信息
	 */
	public UserModel getDecorationContacts(@Param("enname")String enname,@Param("macId")String macId);
	
	/**
	 * 机器故障负责人
	 * @param enname 角色英文标识
	 * @param macId 机具的ID
	 * @return 负责人信息
	 */
	public UserModel getMachineContacts(@Param("enname")String enname,@Param("macId")String macId);
	
	/**
	 * 根据部门ID查找本部门下所有人员
	 * @param officeId 部门ID
	 * @return 
	 */
	public List<UserModel> getAllUser(@Param("officeId") String officeId,@Param("userId") String userId);
	
	/**
	 * 获取特定角色的所有用户
	 * @param enname
	 * @return
	 */
	public List<UserModel> getUserByRole(@Param("enname")String enname);
	
	public String getByMobile(@Param("mobile")String mobile);
	
	public void clearToken(User user);
	
	public String getUserId(@Param("name")String name,@Param("phone")String phone);
	
	/**
	 * 获取电子银行部的主负责人
	 * @return
	 */
	public UserModel getBankOperatorLeader();
	
	
	/**
	 * 查询农商行系统所有人员
	 * 
	 */
	public List<UserModel> getNsAllUser();
	
	/**
	 * 获取广电银通负责人
	 */
	public String getGdytLeader();
}
