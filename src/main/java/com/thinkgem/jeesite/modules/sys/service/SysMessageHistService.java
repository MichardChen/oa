package com.thinkgem.jeesite.modules.sys.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.SendWxqyMsgUtils;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.sys.dao.SysMessageHistDao;
import com.thinkgem.jeesite.modules.sys.entity.SysMessageHist;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.model.SysMessageHistModel;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.pj_common.persistence.ns.entity.SysMessageHistRecord;

/**
 * 消息表Service
 * 
 * @author yuyabiao
 * @since 2016/7/1
 */

@Service
public class SysMessageHistService extends
		CrudService<SysMessageHistDao, SysMessageHist> {

	@Autowired
	private SysMessageHistDao sysMessageHistDao;
	
	@Autowired
	private ActTaskService actTaskService;
	
	/**
	 * 通过主键ID查询消息信息
	 * 
	 * @param id
	 *            消息的ID
	 * @param userCode
	 *            当前用户登录信息
	 * @return
	 * @throws ValidationException
	 */

	@Transactional(readOnly = false)
	public SysMessageHistModel findMsgInfo(String id, String userCode)
			throws ValidationException {
		SysMessageHistModel sysMessageHistModel = new SysMessageHistModel();
		try {
			sysMessageHistModel = sysMessageHistDao.getById(id);
			if(sysMessageHistModel==null){
				sysMessageHistModel = new SysMessageHistModel();
				sysMessageHistModel.setId("-1");
				sysMessageHistModel.setContentDtl("没有取到消息明细,msgId:" + id);
				return sysMessageHistModel;
			}
			String contentDtl = sysMessageHistModel.getContentDtl().replaceAll("<p>", "").replaceAll("</p>","").replaceAll("<br>", ",");
			sysMessageHistModel.setContentDtl(contentDtl);
			String readFlag = sysMessageHistModel.getReadFlag();
			if (!"1".equals(readFlag)) {// 该信息之前未阅读，更新成已阅读 “1”
				SysMessageHist sysMessageHist = new SysMessageHist();
				sysMessageHist.preUpdate(userCode);
				sysMessageHist.setId(id);
				sysMessageHist.setReadFlag("1");
				sysMessageHist.setReadDate(sysMessageHist.getUpdateDate());
				sysMessageHistDao.update(sysMessageHist);
			}
		} catch (Exception e) {
			sysMessageHistModel.setId("-1");
			// 设置错误信息。
			sysMessageHistModel.setContentDtl(e.getMessage());
		}
		return sysMessageHistModel;

	}

	/**
	 * 修改消息的反馈
	 * 
	 * @param sysMessageHist
	 * @param userCode
	 * @return
	 * @throws ValidationException
	 */
	@Transactional(readOnly = false)
	public int changeReplyStatus(SysMessageHist sysMessageHist,
			String userCode, String taskId) throws ValidationException {
		User user = UserUtils.getByLoginName(userCode);
		int flag = 0;
		try {
			sysMessageHist.preUpdate(userCode);
			sysMessageHist.setReplyStatus("1");
			sysMessageHist.setReplyDate(sysMessageHist.getUpdateDate());
			sysMessageHistDao.update(sysMessageHist);
			Map<String, Object> vars = Maps.newHashMap();
			vars.put("startFlag", "N");
			vars.put("curUserId", user.getId());
			actTaskService.complete(taskId, sysMessageHist.getProcInsId(), "",
					vars);
		} catch (Exception e) {
			flag = -1;
			throw new ValidationException(e.toString());
		}
		return flag;
	}

	/**
	 * 委托，撤回发送微信消息
	 * 
	 * @param contentSend
	 *            Wx消息内容
	 * @param fromUser
	 *            发送者
	 * @param toUser
	 *            接收者
	 * @param contentType
	 *            消息类型
	 * @param contentDtl
	 *            消息的具体文本内容
	 * @param taskName
	 *            环节的名称
	 */
	@Transactional(readOnly = false)
	public void sendWxMsg(String contentSend, User fromUser, User toUser,
			String contentType, String contentDtl, String taskName)
			throws ValidationException {
		try {
			SysMessageHist sysMessageHist = new SysMessageHist();
			sysMessageHist.setSystem("atminspect");
			sysMessageHist.setSenderWxUserid(fromUser.getWxUserId());
			sysMessageHist.setSenderUserId(fromUser.getId());
			sysMessageHist.setSenderName(fromUser.getName());
			sysMessageHist.setContentDtl(contentDtl);
			sysMessageHist.setContentType(contentType);
			sysMessageHist.setContentSend(contentSend);
			sysMessageHist.preInsert(fromUser.getLoginName());
			sysMessageHist.setSendDate(sysMessageHist.getCreateDate());
			sysMessageHist.setReadFlag("0");
			sysMessageHist.setSendStatus("0");
			sysMessageHist.setTitle(contentType);
			sysMessageHist.setReceiverWxUserid(toUser.getWxUserId());
			sysMessageHist.setReceiverUserId(toUser.getId());
			sysMessageHist.setReceiverName(toUser.getName());
			sysMessageHistDao.insert(sysMessageHist);
		} catch (Exception e) {
			throw new ValidationException(e.toString());
		}
	}
	
	@Transactional(readOnly = true)
	public List<SysMessageHistModel> findUnread(String receiverUserId) throws ValidationException{
		List<SysMessageHistModel> list = new ArrayList<SysMessageHistModel>();
		try {
			list =  sysMessageHistDao.getUnread(receiverUserId);
		} catch (Exception e) {
			list = null;
		    throw new ValidationException(e.toString());
		}
		return list;
	}

	@Transactional(readOnly = true)
	public List<SysMessageHistModel> findMsgList(String receiverUserId)
			throws ValidationException {
		List<SysMessageHistModel> list = new ArrayList<SysMessageHistModel>();
		try {
			// 未读
			List<SysMessageHistModel> unreadList = sysMessageHistDao
					.getUnread(receiverUserId);
			if (unreadList != null && unreadList.size() > 0) {
				for (SysMessageHistModel sysMessageHistModel : unreadList) {
					Date date = sysMessageHistModel.getSendDate();
					String time = formatDateTime(date);
					sysMessageHistModel.setTime(time);
					list.add(sysMessageHistModel);
				}
			}
			// 已读
			List<SysMessageHistModel> readedList = sysMessageHistDao
					.getHaveRead(receiverUserId);
			if (readedList != null && readedList.size() > 0) {
				for (SysMessageHistModel sysMessageHistModel : readedList) {
					Date date = sysMessageHistModel.getReadDate();
					String time = formatDateTime(date);
					sysMessageHistModel.setTime(time);
					list.add(sysMessageHistModel);
				}
			}
		} catch (Exception e) {
			list = null;
			throw new ValidationException(e.toString());
		}
		return list;
	}

	/**
	 * 获取未发送的信息
	 * 
	 * @return
	 * @throws ValidationException
	 */
	@Transactional(readOnly = true)
	public List<SysMessageHistModel> findUnsend() throws ValidationException {
		List<SysMessageHistModel> list = new ArrayList<SysMessageHistModel>();
		try {
			list = sysMessageHistDao.getUnsend();
		} catch (Exception e) {
			list = null;
			throw new ValidationException(e.toString());
		}
		return list;
	}

	/**
	 * 获取未发送的信息
	 * 
	 * @param list
	 *            单位的idS集合
	 * @param procInsId
	 *            主表流程id
	 * @param processFlage
	 *            流程标识
	 * @return 相关单位对应的反馈信息内容
	 * @throws ValidationException
	 */
	@Transactional(readOnly = true)
	public List<SysMessageHistModel> getFeedback(
			List<SysMessageHistModel> list, String procInsId,
			String processFlage) throws ValidationException {
		List<SysMessageHistModel> List = new ArrayList<SysMessageHistModel>();
		try {
			List = sysMessageHistDao.getFeedback(list, procInsId, processFlage);
		} catch (Exception e) {
			List = null;
			throw new ValidationException(e.toString());
		}
		return List;
	}

	/**
	 * 发送微信消息
	 * 
	 * @param list
	 * @throws ValidationException
	 */
	@Transactional(readOnly = false)
	public void sendWxMsg(List<SysMessageHistModel> list)
			throws ValidationException {
		SysMessageHist sysMessageHist = new SysMessageHist();
		for (SysMessageHistModel sysMessageHistModel : list) {
			try {
				String receiverWxUserid = sysMessageHistModel
						.getReceiverWxUserid();
				String contentSend = sysMessageHistModel.getContentSend();
				String flag = SendWxqyMsgUtils.sendText(receiverWxUserid,
						contentSend);
				// 判断是否发送成功-- OK来判断。
				if ("ok".equals(flag)) {
					sysMessageHist.setId(sysMessageHistModel.getId());
					sysMessageHist.setSendStatus("1");
					sysMessageHist.setSendDate(new Date());
					sysMessageHist.setUpdateDate(new Date());
					sysMessageHist.setUpdateBy(new User("system"));
					sysMessageHistDao.update(sysMessageHist);
				}
			} catch (Exception e) {
				throw new ValidationException(e.toString());
			}
		}
	}

	private static String formatDateTime(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		Calendar current = Calendar.getInstance();
		Calendar today = Calendar.getInstance(); // 今天
		today.set(Calendar.YEAR, current.get(Calendar.YEAR));
		today.set(Calendar.MONTH, current.get(Calendar.MONTH));
		today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
		// Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		Calendar yesterday = Calendar.getInstance(); // 昨天
		yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
		yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
		yesterday.set(Calendar.DAY_OF_MONTH,
				current.get(Calendar.DAY_OF_MONTH) - 1);
		yesterday.set(Calendar.HOUR_OF_DAY, 0);
		yesterday.set(Calendar.MINUTE, 0);
		yesterday.set(Calendar.SECOND, 0);
		current.setTime(date);
		if (current.after(today)) {// 今天
			return "今天" + sdf.format(date);
		} else if (current.before(today) && current.after(yesterday)) {
			return "昨天" + sdf.format(date);
		} else {
			return new SimpleDateFormat("MM-dd HH:mm").format(date);
		}
	}

	/**
	 * 查询公告消息
	 * 
	 * @param page
	 * @param sysMessageHist
	 * @return
	 * @throws ValidationException
	 */
	@Transactional(readOnly = true)
	public Page<SysMessageHist> findAllNotice(Page<SysMessageHist> page,
			SysMessageHist sysMessageHist) throws ValidationException {
		sysMessageHist.setPage(page);
		try {
			page.setList(sysMessageHistDao.getNotice(sysMessageHist));
		} catch (Exception e) {
			page.setList(null);
			throw new ValidationException(e.toString());
		}
		return page;
	}

	/**
	 * 根据主键获取一条数据
	 * 
	 * @param id
	 * @return
	 * @throws ValidationException
	 */
	@Transactional(readOnly = true)
	public SysMessageHistModel getById(String id) throws ValidationException {
		SysMessageHistModel sysMessageHistModel = new SysMessageHistModel();
		try {
			sysMessageHistModel = sysMessageHistDao.getById(id);
		} catch (Exception e) {
			throw new ValidationException(e.toString());
		}
		return sysMessageHistModel;
	}

	/**
	 * 保存通告信息
	 * 
	 * @param sysMessageHist
	 * @param user
	 * @return
	 * @throws ValidationException
	 */
	@Transactional(readOnly = false)
	public int saveNotice(SysMessageHist sysMessage, User user) throws ValidationException {
		int flag = 0;
		//String serverUrl = SysParameterUtils.findKeyword("serverUrl").getValue1();
		Date noticeSendDate = new Date();// 通告创建时间
		try {
			for (SysMessageHistRecord sysMessageHistRecord : sysMessage.getSysMessageRecordList()) {
				String userId = sysMessageHistRecord.getUser().getId();// 获取接受通告人的ID
				User toUser = UserUtils.get(userId);
				SysMessageHist sysMessageHist = new SysMessageHist();
				sysMessageHist.setSystem("atminspect");
				sysMessageHist.setContentType("notice");
				sysMessageHist.setContentDtl(sysMessage.getContentDtl());
				sysMessageHist.setNoticeSendDate(noticeSendDate);
				sysMessageHist.setSenderUserId(user.getId());
				sysMessageHist.setSenderName(user.getName());
				sysMessageHist.setSenderWxUserid(user.getWxUserId());
				sysMessageHist.setReceiverName(toUser.getName());
				sysMessageHist.setReceiverUserId(toUser.getId());
				sysMessageHist.setReceiverWxUserid(toUser.getWxUserId());
				sysMessageHist.setTitle(sysMessage.getTitle());
				sysMessageHist.setSendStatus("");
				sysMessageHist.preInsert();
				sysMessageHistDao.insert(sysMessageHist);
				SysMessageHistModel sysMessageHistModel = sysMessageHistDao.findByAutoIncId(sysMessageHist.getAutoIncId());
				sysMessageHist.setContentSend("您有一条新的公告消息，请到我的消息页面查看；" + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(sysMessageHist.getCreateDate()));
				sysMessageHist.setId(sysMessageHistModel.getId());
				sysMessageHist.setSendStatus("0");
				sysMessageHist.preUpdate();
				sysMessageHistDao.update(sysMessageHist);
			}
		} catch (Exception e) {
			flag = -1;
			throw new ValidationException(e.toString());
		}
		return flag;
	}

	/**
	 * 删除公告
	 * 
	 * @param sysMessageHist
	 * @return
	 * @throws ValidationException
	 */
	@Transactional(readOnly = false)
	public int deleteNotice(SysMessageHist sysMessageHist)
			throws ValidationException {
		int flag = 0;
		try {
			sysMessageHistDao.delNotice(sysMessageHist);
		} catch (Exception e) {
			flag = -1;
			throw new ValidationException(e.toString());
		}
		return flag;
	}
	
	/**
	 * 公告详情
	 * @param time
	 * @return
	 * @throws ValidationException
	 */
	@Transactional(readOnly = true)
	public List<SysMessageHistModel> findDetail(String time) throws ValidationException{
		List<SysMessageHistModel> list = new ArrayList<SysMessageHistModel>();
		try {
			list = sysMessageHistDao.getNoticeDetail(time);
		} catch (Exception e) {
			list = null;
			throw new ValidationException(e.toString());
		}
		return list;
	}
	
	/**
	 * 查询我的消息
	 * 
	 * @param page
	 * @param sysMessageHist
	 * @return
	 * @throws ValidationException
	 */
//	@Transactional(readOnly = true)
//	public Page<SysMessageHist> findMyMsg(Page<SysMessageHist> page,
//			SysMessageHist sysMessageHist) throws ValidationException {
//		sysMessageHist.setPage(page);
//		try {
//			page.setList(sysMessageHistDao.getMyMsg(sysMessageHist));
//		} catch (Exception e) {
//			page.setList(null);
//			throw new ValidationException(e.toString());
//		}
//		return page;
//	}
}
