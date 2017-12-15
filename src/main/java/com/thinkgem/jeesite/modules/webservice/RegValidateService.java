package com.thinkgem.jeesite.modules.webservice;

import javax.jws.WebService;

/**
* 注意：这是SOAP风格的webservice.访问地址 ConstructCost/webservice/regValidateService?wsdl
* webservice/* 是在 web.xml中配置的。
*/
@WebService
public interface RegValidateService {
	// PDA电商发货
	public String deliveryFromEC();
	// PDA称重接口
	public String weight(String shippingId, String weightStr);
	// PDA分拣完成确认
//	public String transportation(String fromColdstorageId, String toColdstorageId);
	public String transportation(String fromColdstorageId);
	// PDA目的冷库分拣
	public String sortingBySC(String fromColdstorageId, String toColdstorageId, String shippingIds);
}
