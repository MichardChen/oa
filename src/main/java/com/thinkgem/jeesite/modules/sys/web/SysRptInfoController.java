package com.thinkgem.jeesite.modules.sys.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.thinkgem.jeesite.common.persistence.JQResultModel;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.JsonUtils;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.SysRptInfo;
import com.thinkgem.jeesite.modules.sys.model.SysParameterModel;
import com.thinkgem.jeesite.modules.sys.model.SysRptInfoModel;
import com.thinkgem.jeesite.modules.sys.service.SysParameterService;
import com.thinkgem.jeesite.modules.sys.service.SysRptInfoService;
import com.thinkgem.jeesite.modules.sys.utils.SysParameterUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 *	报表管理Controller
 * @author ruanxuefei
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysRptInfo")
public class SysRptInfoController extends BaseController{
		
	@Autowired
	private SysRptInfoService sysRptInfoService;
	@Autowired
	private SysParameterService sysParameterService;
	
	//初始化页面
	@RequestMapping(value ={"init",""})
	public String init(SysRptInfoModel ccRptInfoModel,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		
		model.addAttribute("CcRptInfoModel",ccRptInfoModel);
		// 根据js文件名获取版本号。
		model.addAttribute("jsVersion", UserUtils.getVersionFmMenu("sysRptInfo.js"));
		return "modules/sys/sysRptInfo"; 
	}
	// 查询报表信息
		@RequestMapping(value = { "list" })
		@ResponseBody
		public String list(HttpServletRequest request, HttpServletResponse response) {
			String rptName=request.getParameter("rptName");//通过报表名称查询报表信息
			SysRptInfo sysRptInfo = (SysRptInfo)JsonUtils.getBean(request,SysRptInfo.class);
			sysRptInfo.setRptName(rptName);
			//List<CcRptInfoModel> list = ccRptInfoService.getCcRptInfo();
			//return JSON.toJSONString(list);
			//模糊查询方法调用，并将结果返回前台
			Page<SysRptInfo> page = sysRptInfoService.findPage(new Page<SysRptInfo>(request, response),sysRptInfo);
			JQResultModel resultModel = new JQResultModel(page.getList(),
					sysRptInfo.getPage());
			return JSON.toJSONString(resultModel);
		}
	
		
		//删除行
		@RequestMapping(value = { "delete" })
		@ResponseBody
		public String delete(SysRptInfo sysRptInfo,
				RedirectAttributes redirectAttributes,HttpServletRequest request) {
			try {
				String id=request.getParameter("id");
				sysRptInfo.setId(id);
				sysRptInfoService.delete(sysRptInfo);
				} catch (Exception e) {
					return "error";
				}
		
				return "success";
			}	
		
		//编辑行
		@RequestMapping(value = { "edit" })
		@ResponseBody
		public String save(HttpServletRequest request, HttpServletResponse response,SysRptInfo ccRptInfo, Model model,
				RedirectAttributes redirectAttributes) {
			
			sysRptInfoService.save(ccRptInfo);
			
			String rtn;
			if(ccRptInfo.getInsertFlag().equals("0")){
				rtn = sysRptInfoService.findId(ccRptInfo.getAutoIncId());
			}else{
				rtn = ccRptInfo.getId();
			}
			return rtn;
			
		}
		
		
		/**
		 * 解析导入Excel的结构,
		 * 
		 */
		@RequestMapping(value = { "excelResolve" })
		@ResponseBody
		public String excelResolve(MultipartFile file, HttpServletRequest request,
				HttpServletResponse response) {
				
			ImportExcel ei;
			try {
				File Excelfile = new File("C:\\");
				Excelfile.mkdirs();
				FileOutputStream fos = new FileOutputStream("C:\\" + file.getOriginalFilename());
				fos.write(file.getBytes());
				fos.flush();
				fos.close();
				//设值到数据库
				String id = request.getParameter("id");
				SysRptInfo ccRptInfo = new SysRptInfo();
				
				ccRptInfo.setId(id);
				ccRptInfo.setReportFileName(file.getOriginalFilename());
				//从sys_parameter动态获取rptFileBasePat的值
				SysParameterModel sysParameterModel = SysParameterUtils.findKeyword("rptFileBasePath");
				if(!"-1".equals(sysParameterModel.getId())){
					ccRptInfo.setReportFullPath(sysParameterModel.getValue1()+file.getOriginalFilename());
				}else{
					ccRptInfo.setReportFullPath(file.getOriginalFilename());
				}
				sysRptInfoService.updateRptFileName(ccRptInfo);
				
			
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

				JQResultModel resultModel = new JQResultModel();
				resultModel.setResult(-1);
				resultModel.setMsg("读取文件出错！");
				return JSON.toJSONString(resultModel);

			}
			
			// 正常时，返回解析结果。
			JQResultModel resultModel = new JQResultModel();
			resultModel.setMsg(file.getOriginalFilename());
			resultModel.setResult(0);
			return JSON.toJSONString(resultModel);
		}

}