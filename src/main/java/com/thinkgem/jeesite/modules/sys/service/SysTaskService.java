package com.thinkgem.jeesite.modules.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.dao.SysTaskDao;
import com.thinkgem.jeesite.modules.sys.entity.SysTask;

/**
 * 任务Service
 * @author 
 * @version 2016-05-16
 */
@Service
@Transactional(readOnly = true)
public class SysTaskService extends CrudService<SysTaskDao, SysTask> {
	
	@Autowired
	private SysTaskDao sysTaskDao;

	@Override
	@Transactional(readOnly = false)
	public void save(SysTask sysTask) {
		super.save(sysTask);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(SysTask sysTask) {
		super.delete(sysTask);
	}
	/**根据任务类型获取1条数据
	 * */
	public SysTask getByTaskType(SysTask sysTask) {
		return sysTaskDao.getByTaskType(sysTask);
	}
    /**更新任务状态
     * */
	@Transactional(readOnly = false)
	public void updateStatus(SysTask sysTask) {
		sysTaskDao.updateStatus(sysTask);
	}
}
