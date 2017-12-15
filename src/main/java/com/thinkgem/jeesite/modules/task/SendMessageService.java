package com.thinkgem.jeesite.modules.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.modules.sys.dao.SysTaskDao;
import com.thinkgem.jeesite.modules.sys.entity.SysTask;
import com.thinkgem.jeesite.modules.sys.model.SysMessageHistModel;
import com.thinkgem.jeesite.modules.sys.model.SysParameterModel;
import com.thinkgem.jeesite.modules.sys.service.SysMessageHistService;
import com.thinkgem.jeesite.modules.sys.service.SysTaskService;
import com.thinkgem.jeesite.modules.sys.utils.SysParameterUtils;

/**
 * 定期任务(发送消息)
 * @author
 * @version
 * 去除 // @Lazy(false)
 */
@Service
@Transactional(readOnly = true)
public class SendMessageService extends BaseService{

	@Autowired
	private SysMessageHistService sysMessageHistService;
	
	@Autowired
	private SysTaskService sysTaskService;
	
	@Autowired
	private SysTaskDao sysTaskDao;
	
	/**定期发送微信消息
	 * */
	@Transactional(readOnly = false)
	public void sendWxMessage() {
		
		// 1)判断是否停止刷新..防止过量访问数据库。
		SysParameterModel sysParameterModel = new SysParameterModel();
		sysParameterModel = SysParameterUtils.findKeyword("sendWxqyMsg");
		if(sysParameterModel == null || "0".equals(sysParameterModel.getValue1())){
			return;
		}
		// 2)获取任务状态
		SysTask sysTaskParm = new SysTask();
		sysTaskParm.setTaskType("sendWxqyMsg");
		SysTask sysTask = sysTaskService.getByTaskType(sysTaskParm);
		if(sysTask != null && "2".equals(sysTask.getTaskStatus())){
			// 如果任务进行中(TaskStatus=2)，则退出。
			return;
		}
		// 3)
		List<SysMessageHistModel> list = sysMessageHistService.findUnsend();
		if(list.size() > 0){
			// 如果待机，则更新成 任务进行中的状态((TaskStatus=2))。
			sysTaskParm.setTaskStatus("2");
			sysTaskDao.updateStatus(sysTaskParm);
			try{
				sysMessageHistService.sendWxMsg(list);
			}
			catch(Exception e){
			}
			finally{
				// 更新完毕，则返回待机状态(TaskStatus=1)
				sysTaskParm.setTaskStatus("1");
				sysTaskDao.updateStatus(sysTaskParm);
			}
		}
	}
}
