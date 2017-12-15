package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.SysParameter;
import com.thinkgem.jeesite.modules.sys.model.SysParameterModel;

/**
 *  系统控制参数表对应数据连接层
 */
@MyBatisDao
public interface SysParameterDao extends CrudDao<SysParameter>{
	
	public SysParameterModel findByKeyword(@Param("keyword")String keyword);
	
	/**分页查询。
	 * */
	public List<SysParameterModel> findByPage(SysParameter sysParameter);
}
