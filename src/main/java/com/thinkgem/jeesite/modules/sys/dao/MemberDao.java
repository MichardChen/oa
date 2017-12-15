/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.entity.Member;
import com.thinkgem.jeesite.modules.sys.model.MemberModel;
import com.thinkgem.jeesite.modules.sys.model.UserModel;

/**
 * 会员DAO接口
 * @author 
 * @version 2015-12-09
 */
@MyBatisDao
public interface MemberDao extends CrudDao<Member> {

	public List<Member> findAllMember();
	
	public MemberModel getConcurrentPrjNum(@Param("companyId")String companyId);//根据会员ID查询数据获取用户最大工程的权限
	
	public MemberModel findMemberNameIsExist(@Param("memberName")String memberName);//注册用户时检查公司名是否有重复
	
	public Member findByAutoIncId(@Param("autoIncId")int autoIncId);//根据自增长ID查询该条数据的主键ID
	
	public MemberModel findByAutoIncIdForModel(@Param("autoIncId")int autoIncId);//根据自增长ID查询该条数据的主键ID
	
	public void updateMemberInfo(Member member);//修改会员信息
	
	public List<Dict> findAllCompany();
	
}
