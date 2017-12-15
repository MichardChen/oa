package com.thinkgem.jeesite.modules.sys.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.entity.UserFavorite;

@MyBatisDao
public interface UserFavoriteDao extends CrudDao<UserFavorite> {

	public int updateClose(@Param("lastProjectId") String lastProjectId,
			@Param("lastPrjStatus") String lastPrjStatus,
			@Param("user") User user, @Param("updateDate") Date updateDate);// 关闭工程修改数据
	
	//工程派员关系修改时更新用户喜好表
	public int updateByUserId(UserFavorite userFavorite);
	
	//修改工程信息时修改用户喜好表
	public void updatePrjInfo(UserFavorite userFavorite);
	
	//根据用户ID查询当前工程ID
	public String findByUserId(@Param("userId")String userId);
}
	
	