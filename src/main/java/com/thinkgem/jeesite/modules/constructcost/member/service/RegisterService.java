package com.thinkgem.jeesite.modules.constructcost.member.service;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.persistence.BaseModel;
import com.thinkgem.jeesite.common.persistence.JQResultModel;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.constructcost.member.model.RegisterModel;
import com.thinkgem.jeesite.modules.constructcost.member.model.SmsValidationModel;
import com.thinkgem.jeesite.modules.sys.dao.MemberDao;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.dao.RoleDao;
import com.thinkgem.jeesite.modules.sys.dao.SmsValidationDao;
import com.thinkgem.jeesite.modules.sys.dao.SysTaskDao;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.dao.UserFavoriteDao;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.Member;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.SmsValidation;
import com.thinkgem.jeesite.modules.sys.entity.SysTask;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.entity.UserFavorite;
import com.thinkgem.jeesite.modules.sys.model.MemberModel;
import com.thinkgem.jeesite.modules.sys.model.SysTaskModel;
import com.thinkgem.jeesite.modules.sys.model.UserModel;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

@Service
public class RegisterService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private MemberDao memberDao;
	
	@Autowired
	private OfficeDao officeDao;
	
	@Autowired
	private RoleDao roleDao;
		
	@Autowired
	private UserFavoriteDao userFavoriteDao;
	
	@Autowired
	private SmsValidationDao smsValidationDao;
	
	@Autowired
	private SysTaskDao sysTaskDao;
	
	//检查用户名重复
	@Transactional(readOnly = true)
	public String checkLoginName(String loginName) throws ValidationException{
		String flag = "true";
		try {
			UserModel userModel = userDao.findLoginNameIsExist(loginName);
			if(userModel != null){
				flag = "false";
			}
		} catch (Exception e) {
			flag = "false";
			//throw new ValidationException(e.toString());
		}
		return flag;
	}
	
	//检查QQ是否重复
	@Transactional(readOnly = true)
	public String checkQQ(String qq) throws ValidationException{
		String flag = "true";
		try {
			UserModel userModel = userDao.findQQIsExist(qq);
			if(userModel != null){
				flag = "false";
			}
		} catch (Exception e) {
			flag = "false";
			//throw new ValidationException(e.toString());
		}
		return flag;
	}
	
	//检查手机是否重复
	@Transactional(readOnly = true)
	public String checkMobile(String mobile) throws ValidationException{
		String flag = "true";
		try {
			UserModel userModel = userDao.findMobileIsExist(mobile);
			if(userModel != null){
				flag = "false";
			}
		} catch (Exception e) {
			flag = "false";
			//throw new ValidationException(e.toString());
		}
		return flag;
	}
	
	//检查公司名是否重复
	@Transactional(readOnly = true)
	public String checkMemberName(String memberName) throws ValidationException{
		String flag = "true";
		try {
			MemberModel memberModel =  memberDao.findMemberNameIsExist(memberName);
			if(memberModel != null){
				flag = "false";
			}
		} catch (Exception e) {
			flag = "false";
			//throw new ValidationException(e.toString());
		}
		return flag;
	}
	
	//检查手机验证码是否正确或者超时
	@Transactional(readOnly = true)
	public JQResultModel checkValidateMobleCode(String validateMobileCode,String mobileNo) throws ValidationException{
		JQResultModel resultModel = new JQResultModel();
		SmsValidation smsValidation = new SmsValidation();
		smsValidation.setMobileNo(mobileNo);
		try {
			SmsValidationModel smsValidationModel = smsValidationDao.findByMobileNo(smsValidation);
			if(validateMobileCode.equals(smsValidationModel.getValidateCode())){
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				long time = formatter.parse(smsValidationModel.getSendTime()).getTime() + Long.parseLong(DictUtils.getDictLabel("timeOut",
						"sendMsg_data", ""));//设置验证码超时时间
				if(formatter.parse(smsValidationModel.getNowTime()).getTime() > time){//超时
					resultModel.setResult(-1);
					resultModel.setMsg("验证码超时,请重新获取");
				}else{
					resultModel.setResult(0);
				}
			}else{
				resultModel.setResult(-2);
				resultModel.setMsg("验证码错误");
			}
			
		} catch (Exception e) {
			resultModel.setResult(-3);
			resultModel.setMsg("存在异常,请与管理员联系");
		}
		return resultModel;
		
	}
	
	//注册新用户
	@Transactional(readOnly = false)
	public boolean save(RegisterModel registerModel) throws ValidationException{
		boolean flag;
		try {
			User createUser = new User();
			createUser.setId("system");//创建者ID默认设置为system
			Date createDate = new Date();//创建时间
			//1)向member表插入数据
			Member member = new Member();
			member.setId(IdGen.uuid());
			member.setCreateBy(createUser);
			member.setCreateDate(createDate);
			member.setUpdateBy(createUser);
			member.setUpdateDate(createDate);
			member.setAreaId(registerModel.getAreaId());//设置区域
			member.setMemberName(registerModel.getMemberName());//设置会员名
			member.setAddr(registerModel.getAddress());//设置地址
			member.setTel(registerModel.getPhone());//设置固话
			member.setConcurrentPrjNum(1); // 默认只能运作1个工程。
			member.setValidFlag("1");      // 默认设置有效标志为有效
			member.setMemberStatus("2");   // 表示已激活，给客户1个月的试用期；
			memberDao.insert(member);
			member = memberDao.findByAutoIncId(member.getAutoIncId());
			
			String companyId = member.getId();
			//2)向office表插入数据（公司）
			Office office = new Office();
			office.setId(IdGen.uuid());
			office.setCreateBy(createUser);
			office.setCreateDate(createDate);
			office.setUpdateBy(createUser);
			office.setUpdateDate(createDate);
			office.setAddress(registerModel.getAddress());
			office.setName(registerModel.getMemberName());
			office.setArea(new Area(registerModel.getAreaId()));
			office.setType("1");
			office.setGrade("1");
			office.setParent(new Office("0"));
			office.setParentIds("0,");
			office.setUseable("1");
			officeDao.insert(office);
			office = officeDao.findByAutoIncId(office.getAutoIncId());
			//3) 向office表插入数据（下属部门）
			Office childOffice = new Office();
			childOffice.setId(IdGen.uuid());
			childOffice.setCreateBy(createUser);
			childOffice.setCreateDate(createDate);
			childOffice.setUpdateBy(createUser);
			childOffice.setUpdateDate(createDate);
			childOffice.setName("工程部"); // 增加默认部门：工程部
			childOffice.setArea(new Area(registerModel.getAreaId()));
			childOffice.setType("2");
			childOffice.setGrade("2");
			childOffice.setParent(new Office(office.getId()));
			childOffice.setParentIds("0," + office.getId() + ",");
			childOffice.setUseable("1");
			officeDao.insert(childOffice);
			childOffice = officeDao.findByAutoIncId(childOffice.getAutoIncId());
			String officeId = childOffice.getId();
			
			//4) 向user表插入数据
			User user = new User();
			user.setId(IdGen.uuid());//设置ID
			user.setCreateBy(createUser);
			user.setCreateDate(createDate);
			user.setUpdateBy(createUser);
			user.setUpdateDate(createDate);
			user.setMember(member);//设置member
			user.setCompany(new Office(office.getId()));
			user.setOffice(new Office(officeId));
			user.setLoginName(registerModel.getLoginName());//设置登录名
			String password = registerModel.getPassword();
			password = SystemService.entryptPassword(password);
			user.setPassword(password);
			user.setName(registerModel.getName());
			user.setQq(registerModel.getQq());
			user.setPhone(registerModel.getPhone());
			user.setMobile(registerModel.getMobile());
			userDao.insert(user);
			
			UserModel userModel = userDao.findByAutoIncId(user.getAutoIncId());
			String roleId = roleDao.findRoleIdByEnname("company"); // company为会员的管理员权限的名称。
			
			//5)向用户权限表插入数据
			user.setId(userModel.getId());
			user.getRoleList().add(new Role(roleId));
			// 注意:此时不能调用preInsert(),因为这会更新id字段。
			userDao.insertUserRole(user);
			
			//6)清除缓存......
			UserUtils.clearCache(user); // 清除缓存(注意:是注册用户的缓存)，以便于能直接登录。
			
			if (user.getLoginName().equals(UserUtils.getUser().getLoginName())){
				UserUtils.clearCache();
				//UserUtils.getCacheMap().clear();
			}
			
			//7)向相关单位表插入添加
			//CcPartnerCompany ccPartnerCompany = new CcPartnerCompany();
			//ccPartnerCompany.setId(IdGen.uuid());
			//ccPartnerCompany.setCreateBy(createUser);
			//ccPartnerCompany.setCreateDate(createDate);
			//ccPartnerCompany.setUpdateBy(createUser);
			//ccPartnerCompany.setUpdateDate(createDate);
			//ccPartnerCompany.setCompanyId(companyId);
			//ccPartnerCompany.setCompanyName("本公司");
			//ccPartnerCompany.setBrief("本公司");
			//ccPartnerCompany.setRemarks("系统自动生成");
			//ccPartnerCompany.setFlag("1"); // "1":代表本条记录为本公司。"0":代表相关单位。
			//ccPartnerCompanyDao.insert(ccPartnerCompany);
			
			//8)向用户喜好表插入数据
			UserFavorite userFavorite = new UserFavorite();
			userFavorite.setId(IdGen.uuid());
			userFavorite.setCreateBy(createUser);
			userFavorite.setCreateDate(createDate);
			userFavorite.setUpdateBy(createUser);
			userFavorite.setUpdateDate(createDate);
			userFavorite.setUserId(userModel.getId());
			userFavoriteDao.insert(userFavorite);
			
			//9)向系统任务表插入用户数据 -- 不适用于HOLD 项目平台，已删除
			//10)向系统任务表插入会员数据-- 不适用于HOLD 项目平台，已删除
			
			flag = true;
		} catch (Exception e) {
			flag = false;
			throw new ValidationException(e.toString());
		}
		return flag;
	}

}
