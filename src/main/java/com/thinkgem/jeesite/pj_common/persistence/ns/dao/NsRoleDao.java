package com.thinkgem.jeesite.pj_common.persistence.ns.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.model.RoleModel;

/**
 * 角色Dao
 * 针对农商行巡检获取部门角色
 * @author yuyabiao
 * @since 2016/09/15
 */
@MyBatisDao
public interface NsRoleDao {
	
	public List<RoleModel> getRolesByEnnames(String[] ennames);
}
