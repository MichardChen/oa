/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.modules.sys.dao.MemberDao;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.entity.Member;
import com.thinkgem.jeesite.modules.sys.model.MemberModel;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 会员Service
 * @author 
 * @version 2015-12-08
 */
@Service
@Transactional(readOnly = true)
public class MemberService extends CrudService<MemberDao, Member> {
	
	@Autowired
	private MemberDao memberDao;
	
	/**
	 * 获取所有的会员。
	 * @return
	 */
	public List<Member> findAllMember(){
		return dao.findAllMember();
	}

	@Override
	@Transactional(readOnly = false)
	public void save(Member member) {
		super.save(member);
		/// Todo
		CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Member member) {
		super.delete(member);
		CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
	}
	
	@Transactional(readOnly = true)
	public MemberModel getConcurrentPrjNum(String companyId) throws ValidationException{
		MemberModel sysMemberModel = new MemberModel();
		try {
			sysMemberModel = memberDao.getConcurrentPrjNum(companyId);
		} catch (Exception e) {
			sysMemberModel = null;
			throw new ValidationException(e.toString());
		}
		return sysMemberModel;
	}
	
	//修改会员信息
	@Transactional(readOnly = false)
	public int updateMemberInfo(Member member) throws ValidationException{
		int stn;
		try {
			member.preUpdate();
			memberDao.updateMemberInfo(member);
			stn = 0;
		} catch (Exception e) {
			stn = 1;
			throw new ValidationException(e.toString());
		}
		return stn;
	}
	
	@Transactional(readOnly = true)
	public List<Dict> findAllCompany() throws ValidationException{
		List<Dict> list = new ArrayList<Dict>();
		try {
			list = memberDao.findAllCompany();
		} catch (Exception e) {
			list = null;
			throw new ValidationException(e.toString());
		}
		return list;
	}
}
