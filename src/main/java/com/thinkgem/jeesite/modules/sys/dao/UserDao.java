/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
//import com.thinkgem.jeesite.modules.constructcost.member.model.CcPrjUserMapModel;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.model.UserModel;

/**
 * 用户DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface UserDao extends CrudDao<User> {
	
	/**
	 * 根据登录名称查询用户
	 * @param loginName
	 * @return
	 */
	public User getByLoginName(User user);

	/**
	 * 通过OfficeId获取用户列表，仅返回用户id和name（树查询用户时用）
	 * @param user
	 * @return
	 */
	public List<User> findUserByOfficeId(User user);
	
	/**
	 * 查询全部用户数目
	 * @return
	 */
	public long findAllCount(User user);
	
	/**
	 * 更新用户密码
	 * @param user
	 * @return
	 */
	public int updatePasswordById(User user);
	
	/**
	 * 更新登录信息，如：登录IP、登录时间
	 * @param user
	 * @return
	 */
	public int updateLoginInfo(User user);

	/**
	 * 绑定微信OpenId
	 * @param user
	 * @return
	 */
	public int updateUserOpenId(User user);
	
	/**
	 * 更新移动页面的AccessToken
	 * @param user
	 * @return
	 */
	public int updateWxAccessToken(User user);
	
	/**
	 * 更新用户对应的 微信企业号UserId.
	 * @param user
	 * @return
	 */
	public int updateWxqyUserId(User user);
	
	/**
	 * 删除用户角色关联数据
	 * @param user
	 * @return
	 */
	public int deleteUserRole(User user);
	
	/**
	 * 插入用户角色关联数据
	 * @param user
	 * @return
	 */
	public int insertUserRole(User user);
	
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	public int updateUserInfo(User user);
	
	//根据memberId查询登录信息
	public List<UserModel> findByMemberId(@Param("memberId")String memberId);
	
	//通过会员ID查询用户角色关系表信息 
	public List<UserModel> findByNameAndRole(String companyId);
	
	//根据工程ID查询工程的所有用户和角色 
//	public List<CcPrjUserMapModel> findByProjectId(String projectId);
	
	//根据用户ID查询角色ID
	public String findRoleId(@Param("userID")String userId);
	
	//根据登录者的companyId查询sys_user表中公司id等于登录者companyid的数据
	public List<UserModel> getMemberuserListByCompanyId(String companyId);

	//根据登录者的id查询数据
	public List<UserModel> getMemberuserListByLoginId(String loginId);
	
	//注册用户时检查登录名是否重复
	public UserModel findLoginNameIsExist(@Param("loginName") String loginName);
	
	//用户注册时检查QQ是否重复
	public UserModel findQQIsExist(@Param("qq")String qq);
	
	//用户注册时检查手机是否重复
	public UserModel findMobileIsExist(@Param("mobile")String mobile);
	
	//根据自增长ID查询数据
	public UserModel findByAutoIncId(@Param("autoIncId")int autoIncId);
	
	//根据OPENID来获取用户信息。
	public User getByOpenId(User user);
	/**根据微信企业的userid来获取用户信息。
	 * */
	public User getByWxUserId(User user);
	//插入方法
	int insert(User user);
	
	//根据用户id查询用户信息
	public UserModel getUserById(String id);
	/** 更新微信企业号的账号状态
	 * */
	public int updateWxUserStatus(User user);
	
	//获取管理员信息
	
	public List<UserModel> getAdmin(@Param("enname")String enname);
}
