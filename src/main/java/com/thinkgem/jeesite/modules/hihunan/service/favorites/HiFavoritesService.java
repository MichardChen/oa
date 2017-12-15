package com.thinkgem.jeesite.modules.hihunan.service.favorites;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.hihunan.dao.favorites.HiFavoritesDao;
import com.thinkgem.jeesite.modules.hihunan.dao.localactivity.HiLocalActivityDao;
import com.thinkgem.jeesite.modules.hihunan.entity.favorites.HiFavorites;

@Service
public class HiFavoritesService extends CrudService<HiFavoritesDao, HiFavorites>{
	
	@Autowired
	private HiFavoritesDao hiFavoritesDao;
	
	@Autowired
	private HiLocalActivityDao hiLocalActivityDao;
	
	
	@Transactional(readOnly = false)
	public int enshrine(HiFavorites hiFavorites,String token) throws ValidationException{
		int flag = 0;
		try {
			hiFavorites.setMemberId(hiLocalActivityDao.getMemberInfo(token).getId());
			String id = hiFavoritesDao.getInfo(hiFavorites);
			if(StringUtils.isNotBlank(id)){
				flag = 0;
			}else{
				hiFavorites.setFavoritesId(IdGen.uuid());
				hiFavorites.setFavoritesDate(new Date());
				hiFavoritesDao.insert(hiFavorites);
			}
		} catch (Exception e) {
			flag = 1;
			throw new ValidationException(e.toString());
		}
		return flag;
	}
	
	@Transactional(readOnly = false)
	public int deleteCollect(HiFavorites hiFavorites,String key) throws ValidationException{
		int flag = 0;
		try {
			int memberId = hiLocalActivityDao.getMemberInfo(key).getId();
			hiFavorites.setMemberId(memberId);
			flag = hiFavoritesDao.delete(hiFavorites);
		} catch (Exception e) {
			flag = -1;
			throw new ValidationException(e.toString());
		}
		return flag;
	}
	
	@Transactional(readOnly = true)
	public String findIsCollect(HiFavorites hiFavorites,String key) throws ValidationException{
		String flag = "";
		try {
			if(StringUtils.isNotBlank(key)){
				hiFavorites.setMemberId(hiLocalActivityDao.getMemberInfo(key).getId());
				flag = hiFavoritesDao.getInfo(hiFavorites);
			}else{
				flag = "";
			}
		} catch (Exception e) {
			flag = "";
			throw new ValidationException(e.toString());
		}
		return flag;
	}

}
