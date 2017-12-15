package com.thinkgem.jeesite.modules.task;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fastweixin.company.api.QYUserAPI;
import com.fastweixin.company.api.config.QYAPIConfig;
import com.fastweixin.company.api.entity.QYUser;
import com.fastweixin.company.api.response.GetQYUserInfo4DepartmentResponse;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.modules.sys.dao.SysWxqyDeptDao;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.SysWxqyDept;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.model.SysParameterModel;
import com.thinkgem.jeesite.modules.sys.utils.SysParameterUtils;

/**
 * 定期任务(微信企业信息同步)
 * @author
 * @version
 * 去除 // @Lazy(false)
 */
@Service
@Transactional(readOnly = true)
public class RefreshWxqyService extends BaseService{

	@Autowired
	private SysWxqyDeptDao sysWxqyDeptDao;
	
	@Autowired
	private UserDao userDao;
	
	/**定期刷新微信企业号的账号状态到用户表
	 * */
	@Transactional(readOnly = false)
	public void refreshWxUserStatus() {
		
		// 判断是否停止刷新..防止过量访问数据库。
		SysParameterModel sysParameterModel = new SysParameterModel();
		sysParameterModel = SysParameterUtils.findKeyword("refreshWxUserStatus");
		if(sysParameterModel == null || "0".equals(sysParameterModel.getValue1())){
			return;
		}
		
		// 1) 获取微信部门的列表
		List<SysWxqyDept> list = sysWxqyDeptDao.findAllList();
		
		// 2) 更新用户表中的 微信企业号的账号状态数据。 
		if(list.size() > 0){
			QYAPIConfig apiConfig = QYAPIConfig.getInstance();
			QYUserAPI api = new QYUserAPI(apiConfig);
			
			List<QYUser> qyUserList = new ArrayList();
			GetQYUserInfo4DepartmentResponse resp = new GetQYUserInfo4DepartmentResponse();
			for(SysWxqyDept sysWxqyDept:list){
				// 1) 根据部门id, 获取部门内的成员列表。
				resp = api.getList(Integer.valueOf(sysWxqyDept.getId()), true, 0);
				qyUserList = resp.getUserList();
				// 2) 刷新企业号成员的状态信息。
				for(QYUser qyUser:qyUserList){
					// 获取微信企业号的账号和状态信息，同步到系统中。
					User user = new User();
					user.setWxUserId(qyUser.getUserId());
					user.setWxUserStatus(Integer.toString(qyUser.getStatus()));
					// 同步到系统中。
					userDao.updateWxUserStatus(user);
				}
			}
		}
	}

}
