package com.thinkgem.jeesite.modules.hihunan.service.h5app;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.hihunan.dao.artical.HiArticalDao;
import com.thinkgem.jeesite.modules.hihunan.dao.h5app.HiH5appDao;
import com.thinkgem.jeesite.modules.hihunan.dao.listshow.HiListShowDao;
import com.thinkgem.jeesite.modules.hihunan.entity.artical.HiArtical;
import com.thinkgem.jeesite.modules.hihunan.entity.h5app.HiH5app;
import com.thinkgem.jeesite.modules.hihunan.entity.listshow.HiListShow;
import com.thinkgem.jeesite.modules.hihunan.model.h5app.HiH5appModel;
import com.thinkgem.jeesite.modules.hihunan.model.listshow.HiListShowModel;
import com.thinkgem.jeesite.modules.hihunan.service.localactivity.HiLocalActivityService;
import com.thinkgem.jeesite.modules.sys.dao.DictDao;
import com.thinkgem.jeesite.modules.sys.entity.Dict;

@Service
@Transactional(readOnly = true)
public class HiH5appService extends CrudService<HiH5appDao, HiH5app>{
	
	@Autowired
	private HiH5appDao hiH5appDao;
	
	@Autowired
	private DictDao dictDao;
	
	@Autowired
	private HiListShowDao hiListShowDao;
	
	
	@Autowired
	private HiLocalActivityService hiLocalActivityService;
	
	@Autowired
	private HiArticalDao hiArticalDao;
	
	public HiH5app get(String id) {
		return super.get(id);
	}
	
	public List<HiH5app> findList(HiH5app hiH5app) {
		return super.findList(hiH5app);
	}
	
	public Page<HiH5app> findPage(Page<HiH5app> page, HiH5app hiH5app) {
		return super.findPage(page, hiH5app);
	}
	
	@Transactional(readOnly = false)
	public void save(HiH5app hiH5app) {
		super.save(hiH5app);
	}
	
	@Transactional(readOnly = false)
	public void delete(HiH5app hiH5app) {
		super.delete(hiH5app);
	}
	
	@Transactional
	public List<HiH5appModel> findAll(HiH5app hiH5app) throws ValidationException{
		List<HiH5appModel> TypeList = new ArrayList<HiH5appModel>();
		try {
			//发现类别
			String type = "hi_h5_type";
			//一级分类 （1.首页 ：top 2.发现：discover ：）
			String type1="discover";
			TypeList = dictDao.getListByh5appType(type);
			for(HiH5appModel hiH5appModel : TypeList){
				hiH5app.setType1(type1);
				hiH5app.setType2(hiH5appModel.getValue());
				List<HiH5appModel> list = hiH5appDao.findTypeAll(hiH5app);
				hiH5appModel.setHiH5appModel(list);
			}
			
		} catch (Exception e) {
			TypeList = null;
			throw new ValidationException(e.toString());
		}
		return TypeList;
	}
	
	/**
	 * 
	 * @param hiH5app
	 * @return
	 * @throws ValidationException
	 */
	@Transactional
	public List<HiH5appModel> findHeaderItem(HiH5app hiH5app) throws ValidationException{
		List<HiH5appModel> list = new ArrayList<HiH5appModel>();
		try {
			list = hiH5appDao.getHeaderItem(hiH5app);
		} catch (Exception e) {
			list = null;
			throw new ValidationException(e.toString());
		}
		return list;
	}
	
	@Transactional(readOnly = true)
	public List<HiH5appModel> findListType(HiH5app hiH5app) throws ValidationException{
		List<HiH5appModel> list = new ArrayList<HiH5appModel>();
		try {
			list = hiH5appDao.getListType(hiH5app);
			if(list.size() > 0){
				for(HiH5appModel hiH5appModel : list){
					String type = hiH5appModel.getListType();
					if("local".equals(type)){//本地生活调接口
						HiListShow hiListShow = new HiListShow();
						hiListShow.setListMaxCount(1);
						List<HiListShowModel> listModel = hiLocalActivityService.findLocalList(hiListShow);
						hiH5appModel.setListModel(listModel);
					}else if("restaurant".equals(type)){//美食
						HiArtical hiArtical = new HiArtical();
						hiArtical.setListMaxCount(hiH5appModel.getListMaxCount());
						hiArtical.setArticalType("restaurant");
						List<HiListShowModel> listModel = hiArticalDao.getHeadRestaunant(hiArtical);
						hiH5appModel.setListModel(listModel);
					}else if("advisory".equals(type)){//资讯
						HiArtical hiArtical = new HiArtical();
						hiArtical.setListMaxCount(hiH5appModel.getListMaxCount());
						hiArtical.setArticalType("advisory");
						List<HiListShowModel> listModel = hiArticalDao.getHeadRestaunant(hiArtical);
						hiH5appModel.setListModel(listModel);
					}else{
						HiListShow hiListShow = new HiListShow();
						hiListShow.setType(type);
						hiListShow.setListMaxCount(hiH5appModel.getListMaxCount());
						List<HiListShowModel> listModel = hiListShowDao.findAll(hiListShow);
						hiH5appModel.setListModel(listModel);
					}
				}
			}
		} catch (Exception e) {
			list = null;
			throw new ValidationException(e.toString());
		}
		return list;
	}
	
	//获取所有应用的下拉列表集合
	@Transactional(readOnly = true)
	public List<Dict> getH5appList() throws ValidationException{
		List<Dict> list = new ArrayList<Dict>();
		try {
			list = hiH5appDao.getH5appList();
		} catch (Exception e) {
			list = null;
			throw new ValidationException(e.toString());
		}
		return list;
	}
}
