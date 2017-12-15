/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hihunan.service.localactivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.fastweixin.util.NetWorkCenter;
import com.fastweixin.util.NetWorkCenter.ResponseCallback;
import com.thinkgem.jeesite.common.exception.ValidationException;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.hihunan.dao.localactivity.HiLocalActivityDao;
import com.thinkgem.jeesite.modules.hihunan.entity.listshow.HiListShow;
import com.thinkgem.jeesite.modules.hihunan.entity.localactivity.HiLocalActivity;
import com.thinkgem.jeesite.modules.hihunan.model.listshow.HiListShowModel;
import com.thinkgem.jeesite.modules.hihunan.model.localactivity.HiLocalActivityListModel;
import com.thinkgem.jeesite.modules.hihunan.model.localactivity.HiLocalActivityModel;
import com.thinkgem.jeesite.modules.hihunan.model.localactivity.MemberModel;



/**
 * 本地生活Service
 * @author zfg
 * @version 2016-12-01
 */
@Service
@Transactional(readOnly = true)
public class HiLocalActivityService extends CrudService<HiLocalActivityDao, HiLocalActivity> {
	
	@Autowired
	private HiLocalActivityDao hiLocalActivityDao;

	public HiLocalActivity get(String id) {
		return super.get(id);
	}
	
	public List<HiLocalActivity> findList(HiLocalActivity hiLocalActivity) {
		return super.findList(hiLocalActivity);
	}
	
	public Page<HiLocalActivity> findPage(Page<HiLocalActivity> page, HiLocalActivity hiLocalActivity) {
		return super.findPage(page, hiLocalActivity);
	}
	
	@Transactional(readOnly = false)
	public void save(HiLocalActivity hiLocalActivity) {
		super.save(hiLocalActivity);
	}
	
	@Transactional(readOnly = false)
	public void delete(HiLocalActivity hiLocalActivity) {
		super.delete(hiLocalActivity);
	}
	

	
	 /** 
     * @param args 
     */  
    public static String getSign() {   
 //String[] args
        //活动列表接口提供的参数  
        String page = "";  
        String key = "8Eh033lLzul1iRrCdbqQcIglBF2fyIZqBavRq";  
        Date d =new Date();
        String pudate = String.valueOf(d.getTime()); 
        //pudate ="1466068407";
        String sort = "";   
          
        SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();  
        parameters.put("page", page);  
        parameters.put("key", key);  
        parameters.put("pudate", pudate);  
        parameters.put("sort", sort);   
          
        String characterEncoding = "UTF-8";  
        
        //计算得到签名
        String mySign = HiLocalSignUtils.createSign(characterEncoding,parameters);  
        return mySign;

    }  
	
	
    /**
	 * 查询本地活动列表
	 * @param hiListShow
	 * @return
	 * @throws ValidationException
	 */
	@Transactional(readOnly = true)
	public List<HiListShowModel> findLocalList(HiListShow hiListShow) throws ValidationException{
		List<HiListShowModel> list = new ArrayList<HiListShowModel>();
		try {
			final StringBuilder rtn = new StringBuilder(""); //返回用(注意：必须用StringBuilder，不能用String)

			
			//计算得到签名
	        String key = "8Eh033lLzul1iRrCdbqQcIglBF2fyIZqBavRq";  
	        long d = System.currentTimeMillis()/1000;
	        String pudate = String.valueOf(d); 
	        //pudate ="1480684581452";
	        String sort = ""; 
	          
	        SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();  
	        parameters.put("page", hiListShow.getListMaxCount());  
	        parameters.put("key", key);  
	        parameters.put("pudate", pudate);  
	        parameters.put("sort", sort);   
	          
	        String characterEncoding = "UTF-8";  
	        
	        //计算得到签名
	        String mySign = HiLocalSignUtils.createSign(characterEncoding,parameters);  
			
	        String url= "http://webox.7lab.qq.com/benefit/activity/cface/"+HiLocalSignUtils.createURL(characterEncoding, parameters)+"/sign/"+mySign;
	        
	      //调用大湘提供的本地活动列表接口

			NetWorkCenter.post(url, "" , new ResponseCallback(){
				@Override
				public void onResponse(int resultCode, String resultJson) {
					if(resultCode == 200){
						// 正常返回
						//List<HiLocalActivityModel>  localActivityList = JsonConvert.Deserializeobject<List<HiLocalActivityModel>>(resultJson);
								
						
						rtn.append(resultJson);
					}
					else{ // 没返回数据
						rtn.append("error");
					}
				}
				}
			);
			
			//接口返回的数据量偏多，含有富文本，先转接到一览页所需的列表信息。
			List<HiLocalActivityModel>  localActivityList = JSON.parseArray(rtn.toString(), HiLocalActivityModel.class);
			
			// 排序规则：结束时间
			Collections.sort(localActivityList, new Comparator<HiLocalActivityModel>() {  
				  
	            @Override  
	            public int compare(HiLocalActivityModel o1, HiLocalActivityModel o2) {
	                
	            	SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	            	Date d1 = new Date();
	            	Date d2 = new Date();
	            	
					try {
						d1 = sdf.parse(o1.getEnd_time());
						d2 = sdf.parse(o2.getEnd_time());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
	            	long diff  = d2.getTime() - d1.getTime();
	            	int rtnVal = 0 ;
	            	if(diff > 0){ 
	            	    rtnVal = 1;
	            	}
	            	if(diff == 0){ 
	            		rtnVal = 0;
		            }
	            	if(diff < 0){ 
	            		rtnVal = -1;
		            }
	            	return rtnVal;
	            }  
	        });  
						
			for(HiLocalActivityModel hiLocalActivityModel : localActivityList){
				HiListShowModel hiListShowModel = new HiListShowModel();
				hiListShowModel.setId(hiLocalActivityModel.getId());
				hiListShowModel.setTitle(hiLocalActivityModel.getName());
				hiListShowModel.setTitlePhoto(hiLocalActivityModel.getPicUrl());
				hiListShowModel.setAddress(hiLocalActivityModel.getAwardAddress());
				hiListShowModel.setType("local");
				
				list.add(hiListShowModel);
			}

			//list = hiListShowDao.findAll(hiListShow);
		} catch (Exception e) {
			list = null;
			throw new ValidationException(e.toString());
		}
		return list;
	}
    
    
	/**
	 * 查询本地活动列表2, 返回完整的接口取得数据
	 * @param hiLocalActivity
	 * @return
	 * @throws ValidationException
	 */
	@Transactional(readOnly = true)
	public List<HiLocalActivityListModel> findLocalListFull(HiLocalActivityListModel hiLocalActivityListModel) throws ValidationException{
		List<HiLocalActivityListModel> list = new ArrayList<HiLocalActivityListModel>();
		try {
			final StringBuilder rtn = new StringBuilder(""); //返回用(注意：必须用StringBuilder，不能用String)
			
			
			//计算得到签名
			String page = "";  
	        String key = "8Eh033lLzul1iRrCdbqQcIglBF2fyIZqBavRq";  
	        long d = System.currentTimeMillis()/1000;
	        String pudate = String.valueOf(d); 
	        //pudate ="1480684581452";
	        String sort = ""; 
	          
	        SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();  
	        parameters.put("page", page);  
	        parameters.put("key", key);  
	        parameters.put("pudate", pudate);  
	        parameters.put("sort", sort);   
	          
	        String characterEncoding = "UTF-8";  
	        
	        //计算得到签名
	        String mySign = HiLocalSignUtils.createSign(characterEncoding,parameters);  
			
	        String url= "http://webox.7lab.qq.com/benefit/activity/cface/"+HiLocalSignUtils.createURL(characterEncoding, parameters)+"/sign/"+mySign;
	        
	      //调用大湘提供的本地活动列表接口

			NetWorkCenter.post(url, "" , new ResponseCallback(){
				@Override
				public void onResponse(int resultCode, String resultJson) {
					if(resultCode == 200){
						// 正常返回
						//List<HiLocalActivityModel>  localActivityList = JsonConvert.Deserializeobject<List<HiLocalActivityModel>>(resultJson);
								
						
						rtn.append(resultJson);
					}
					else{ // 没返回数据
						rtn.append("error");
					}
				}
				}
					);
			
			//接口返回的数据量偏多，含有富文本，先转接到一览页所需的列表信息。
			list = JSON.parseArray(rtn.toString(), HiLocalActivityListModel.class);

			
			// 排序规则：结束时间
			Collections.sort(list, new Comparator<HiLocalActivityListModel>() {  
				  
	            @Override  
	            public int compare(HiLocalActivityListModel o1, HiLocalActivityListModel o2) {        
	            	SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	            	Date d1 = new Date();
	            	Date d2 = new Date();
	            	
					try {
						d1 = sdf.parse(o1.getEnd_time());
						d2 = sdf.parse(o2.getEnd_time());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
	            	long diff  = d2.getTime() - d1.getTime();
	            	int rtnVal = 0 ;
	            	if(diff > 0){ 
	            	    rtnVal = 1;
	            	}
	            	if(diff == 0){ 
	            		rtnVal = 0;
		            }
	            	if(diff < 0){ 
	            		rtnVal = -1;
		            }
	            	return rtnVal;
	            }  
	        });  
			
			
			//list = hiListShowDao.findAll(hiListShow);
		} catch (Exception e) {
			list = null;
			throw new ValidationException(e.toString());
		}
		return list;
	}
	
	/**
	 * 查询本地活动明细
	 * @param hiLocalActivity
	 * @return
	 * @throws ValidationException
	 */
	@Transactional(readOnly = true)
	public String findByParentId(HiLocalActivity hiLocalActivity) throws ValidationException{
		String rtnJson="";
		try {
			final StringBuilder rtn = new StringBuilder(""); //返回用(注意：必须用StringBuilder，不能用String)
			
			//计算得到签名
	        String key = "8Eh033lLzul1iRrCdbqQcIglBF2fyIZqBavRq"; 
	        long d = System.currentTimeMillis()/1000;
	        String pudate = String.valueOf(d); 
 
	        SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();  
	        parameters.put("id", hiLocalActivity.getId());  
	        parameters.put("pudate", pudate); 
	        parameters.put("key", key);  
  
	        String characterEncoding = "UTF-8";  
	        
	        //计算得到签名
	        String mySign = HiLocalSignUtils.createSign(characterEncoding,parameters);  
			
	        String url= "http://webox.7lab.qq.com/benefit/activity/cdetail/"+HiLocalSignUtils.createURL(characterEncoding, parameters)+"/sign/"+mySign;
	        
	      //调用大湘提供的本地活动明细接口

			NetWorkCenter.post(url, "" , new ResponseCallback(){
				@Override
				public void onResponse(int resultCode, String resultJson) {
					if(resultCode == 200){
						// 正常返回
						//List<HiLocalActivityModel>  localActivityList = JsonConvert.Deserializeobject<List<HiLocalActivityModel>>(resultJson);

						rtn.append(resultJson);
					}
					else{ // 没返回数据
						rtn.append("error");
					}
				}
				}
					);
			
			//接口返回的数据
			rtnJson = rtn.toString();
			//List<HiLocalActivityModel> list = JSON.parseArray(rtnJson,HiLocalActivityModel.class);
			
			//hiLocalActivityModel =  (HiLocalActivityModel)JSONUtils.parse(rtn.toString());
			//list = JSON.parseArray(rtn.toString(), HiLocalActivityModel.class);

			
		} catch (Exception e) {
			rtnJson = null;
			throw new ValidationException(e.toString());
		}
		return rtnJson;
	}
	
	
	/**
	 * 参加活动
	 * @param token
	 * @param id
	 * @return
	 * @throws ValidationException
	 */
	@Transactional
	public String joinActivity(String token,String id) throws ValidationException{
		String rtnJson="";
		try {
			final StringBuilder rtn = new StringBuilder(""); //返回用(注意：必须用StringBuilder，不能用String)
			MemberModel memberModel = hiLocalActivityDao.getMemberInfo(token);
			//计算得到签名
	        String key = "8Eh033lLzul1iRrCdbqQcIglBF2fyIZqBavRq"; 
	        long d = System.currentTimeMillis()/1000;
	        String pudate = String.valueOf(d);
	        SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();  
	        parameters.put("activityID", id);  
	        parameters.put("name", memberModel.getName());
	        parameters.put("contact", memberModel.getMobile());
	        parameters.put("key", key);  
	        parameters.put("pudate", pudate);
	        parameters.put("occupation", "");
	        parameters.put("identificationCard", "");
	        parameters.put("educationLevel", "");
	        parameters.put("sex", "");
	        parameters.put("wechat", "");
	        parameters.put("nation", "");
	        parameters.put("age", "");
	        parameters.put("homeAddress", "");
	        parameters.put("qq", "");
	        parameters.put("origin", "");
	        parameters.put("dateBirth", "");
	        parameters.put("constellation", "");
	        parameters.put("email", "");
	        parameters.put("marital", "");
	        parameters.put("political", "");
	        parameters.put("income", "");
	        parameters.put("message", "");
	        parameters.put("photo", "");
	        
  
	        String characterEncoding = "UTF-8";  
	        
	        //计算得到签名
	        String mySign = HiLocalSignUtils.createSign(characterEncoding,parameters);
	        parameters.remove("activityID");
	        parameters.put("id", id);
	        String url= "http://webox.7lab.qq.com/benefit/activity/added/"+HiLocalSignUtils.createURL(characterEncoding, parameters)+"/sign/"+mySign;
	        url = url + "/occupation/" + "/identificationCard/" + "/educationLevel/" + "/sex/" + "/wechat/" + "/nation/" + "/age/" + "/homeAddress/" + "/qq/"
	        		+ "/origin/" + "/dateBirth/" + "/constellation/" + "/email/" + "/marital/" + "/political/" + "/income/" + "/message/" + "/photo/";
	        
	        
	        System.out.println(url);
	      //调用大湘提供的本地活动明细接口
			NetWorkCenter.post(url, "" , new ResponseCallback(){
				@Override
				public void onResponse(int resultCode, String resultJson) {
					if(resultCode == 200){
						rtn.append(resultJson);
					}
					else{ 
						rtn.append("error");
					}
				}
				}
					);
			rtnJson = rtn.toString();
			
		} catch (Exception e) {
			rtnJson = null;
			throw new ValidationException(e.toString());
		}
		return rtnJson;
	}
	
	
}