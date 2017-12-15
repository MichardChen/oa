package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.SysMessageHist;
import com.thinkgem.jeesite.modules.sys.model.SysMessageHistModel;

@MyBatisDao
public interface SysMessageHistDao extends CrudDao<SysMessageHist>{
	
	//根据自增长ID或者插入成功的数据主键ID
	public SysMessageHistModel findByAutoIncId(int autoIncId);
	
	//根据主键获取数据
	public SysMessageHistModel getById(@Param("id") String id);
	
	//通过流程id、流程标识、单位id查询消息反馈内容
	public List<SysMessageHistModel> getFeedback(@Param("list") List<SysMessageHistModel> list,@Param("procInsId") String procInsId,@Param("processFlage") String processFlage);
	
	//查询未读
	public List<SysMessageHistModel> getUnread(@Param("receiverUserId") String receiverUserId);
	
	//查询已读,按已读时间倒序查询20条
	public List<SysMessageHistModel> getHaveRead(@Param("receiverUserId") String receiverUserId);
	
	//获取未发送的消息
	public List<SysMessageHistModel> getUnsend();
	
	//查询公告消息
	public List<SysMessageHist> getNotice(SysMessageHist sysMessageHist);
	
	//删除公告
	public int delNotice(SysMessageHist sysMessageHist);
	
	//公告详情
	public List<SysMessageHistModel> getNoticeDetail(@Param("noticeSendDateString") String noticeSendDateString);
	
}
