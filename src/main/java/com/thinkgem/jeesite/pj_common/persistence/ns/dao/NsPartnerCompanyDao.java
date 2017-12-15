package com.thinkgem.jeesite.pj_common.persistence.ns.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.ns.data.model.NsPartnerCompanyModel;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.pj_common.persistence.ns.entity.NsPartnerCompany;
/**
 * 相关单位Dao,2016/5/10
 * @author yuyabiao
 *
 */

@MyBatisDao
public interface NsPartnerCompanyDao extends CrudDao<NsPartnerCompany>{
	//查询所有相关单位
	public List<NsPartnerCompanyModel> findAllCompany(@Param("types") List<NsPartnerCompanyModel> types);
	
	//查询所有外协公司的单位
	public List<Dict> findoutsourcingCompany();
	
	public List<NsPartnerCompanyModel> getCompany(NsPartnerCompany nsPartnerCompany);
	
	public NsPartnerCompany getByName(@Param("name") String name);
	
	public NsPartnerCompanyModel findById(@Param("id") String id);
	//根据用户id查询所属相关单位Id
	public NsPartnerCompanyModel findByUserId(@Param("userId") String userId);
	
	/***根据OfficeID查询负责人*/
	public NsPartnerCompanyModel findHeadById(@Param("partnerCompanyId") String partnerCompanyId);
	
	public NsPartnerCompanyModel findByType(@Param("type") String type);
	
	/**根据OfficeIDs查询负责人集合
	 * */
	public List<NsPartnerCompanyModel> findHeadsByIds(@Param("ids") List<NsPartnerCompanyModel> ids);
	/**
	 * 根据部门id来关联相关单位，获取此单位类型下的角色列表。
	 * @param
	 * @return 角色key的列表(逗号隔开)。如   net,repair,
	 * */
	public String getRoleKeysByOfficeId(@Param("officeId") String officeId);
	
	//获取广电银通的部门ID
	public String getGdytId(@Param("name") String name);
	
	public NsPartnerCompanyModel getByOfficeId(@Param("officeId") String officeId);
}
