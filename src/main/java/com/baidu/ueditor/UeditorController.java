package com.baidu.ueditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.web.BaseController;


@Controller
@RequestMapping(value = "${adminPath}/ueditor")
public class UeditorController extends BaseController {
	
	@RequestMapping(value = {"config"})
	public String config(Model model) {	
		return "modules/ueditor/controller";
	}
	
}