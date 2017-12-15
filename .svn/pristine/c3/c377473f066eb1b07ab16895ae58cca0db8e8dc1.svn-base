package com.thinkgem.jeesite.modules.webservice;



import javax.jws.WebService;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/** * * * * * * * * * * * * * * * * * * * * * * * * *
 * 接口实现类										*
 *													*
 * @author 									*
 * @since 2015-08-12								*
 * 注意：这是SOAP风格的webservice.访问地址 ConstructCost/webservice/regValidateService?wsdl
 * * * * * * * * * * * * * * * * * * * * * * * * * **/
@WebService(endpointInterface = "com.thinkgem.jeesite.modules.webservice.RegValidateService", serviceName = "regValidateService")
@Service("RegValidateService")
@Transactional(readOnly = true)
public class RegValidateServiceImpl implements RegValidateService {
	
	@Override
	@Transactional(readOnly = false)
	public String deliveryFromEC() {
		
		return "";
		
	}

	@Override
	@Transactional(readOnly = false)
	public String weight(String shippingId, String weightStr) {
		return "";
	}

	@Override
	@Transactional(readOnly = false)
//	public String transportation(String fromColdstorageId, String toColdstorageId) {
	public String transportation(String fromColdstorageId) {
		return "";
	}

	@Override
	@Transactional(readOnly = false)
	public String sortingBySC(String fromColdstorageId, String toColdstorageId, String shippingIds) {
		return "";
	}

}
