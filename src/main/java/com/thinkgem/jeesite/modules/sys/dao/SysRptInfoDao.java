package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.SysRptInfo;
import com.thinkgem.jeesite.modules.sys.model.SysRptInfoModel;

/**
 * 对应cc_rpt_info表的Dao
 * @author lin
 *
 */
@MyBatisDao
public interface SysRptInfoDao extends CrudDao<SysRptInfo>{
	
		//获取所有报表类型数据
		public List<SysRptInfoModel> getCcRptInfoList();
		//根据自增长ID或者插入成功的数据主键ID
		public String findByAutoIncId(int autoIncId);
		//更新报表文件名和路径
		public void updateRptFileName(SysRptInfo sysRptInfo);
		
}
