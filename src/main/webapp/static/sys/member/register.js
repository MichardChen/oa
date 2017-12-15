"use strict";

function returnLogin(){
	window.location.href = "${ctx}/login";
}

function alertLogin(mess, closed){//注册成功跳转提示框
	$.jBox.info(mess, '提示', {closed:function(){
		if (typeof closed == 'function') {
			closed();
		}
	window.location.href="${ctx}/login";
	}});
	$('.jbox-body .jbox-icon').css('top','55px');
}

$(function(){
	// Init Theme Core      
	Core.init();

	// Init Demo JS
	Demo.init();

	// Init CanvasBG and pass target starting location
	CanvasBG.init({
	    Loc: {
	        x: window.innerWidth / 2,
	        y: window.innerHeight / 3.3
	    },
	});

	// 对于下拉框，设成select2风格。
	$("select").select2();
	
	//发送验证码实现
	var button = document.getElementById("verifyBtn");
	var times = 60;
	var timer = null;
	button.onclick = function(){
		var mobileNo = $("#mobile").val().trim();
		if(mobileNo == '' || mobileNo == null){
			alerts("请先输入手机号码","#mobile");
			return;
		}
		var that = this;
		that.disabled = true;
		timer = setInterval(function(){
			times --;
			that.value = times + "秒后重试";
			if(times <= 0){
				that.disabled = false;
				that.value = "获取验证码";
				clearInterval(timer);
				times = 60;
			}
		},1000);
	};
	
	$("#verifyBtn").click(function(){
		var mobileNo = $("#mobile").val().trim();
		if(mobileNo == '' || mobileNo == null){
			return;
		}
		$.ajax({
			 async:false,
			 type:"post",
			 data:{"mobileNo":mobileNo
				 },
		     url:ctxRoot + "/webservice/rest/regValidate/sendMsg",
		     success:function(data){
		    	 var flag = eval("(" + data + ")");
		    	 if(flag.result != 0){
		    		 alertx(flag.msg);
		    	 }
		     }
		});
	});

	var ajax_option={
			url:ctxRoot + "/webservice/rest/regValidate/save",
			success:function(data){
				var flag = eval("(" + data + ")");
				if(flag.result == 0){//注册成功返回登录页面
					alertLogin(flag.msg);
				}else{
					alertx(flag.msg);
					return;
				}
			}
	};

	// 验证中的remote,访问的是REST风格的WebService。注意:${ctxRoot}
	// 注意:这里不能用平时用的Controller,因为使它的前提是登录完毕。
	$("#inputForm").validate({
		rules: {
			memberName: {
		       required:true,  
		       remote:{
		            url:ctxRoot + "/webservice/rest/regValidate/checkMemberName"
				}
			},
			loginName:{
				required:true,
				remote:{
					url:ctxRoot + "/webservice/rest/regValidate/checkLoginName"
				}
			},
			password:{
				required:true
			},
			mobile:{
				required:true,
				remote:{
					url:ctxRoot + "/webservice/rest/regValidate/checkMobile"
				}
			},
			qq:{
				remote:{
					url:ctxRoot + "/webservice/rest/regValidate/checkQQ"
				}
			},
			validateMobileCode:{
				required:true
			}
		},
		messages: {
			memberName: {
				remote:"公司名已存在",
				required:"请输入公司名"
			},
			loginName:{
				remote:"登录名重复",
				required:"请输入登录名"
			},
			password:{
				required:"请输入密码"
			},
			checkPassWord:{
				equalTo: "密码输入不一致"
			},	
			mobile:{
				remote:"手机号重复",
				required:"请输入手机号码"
			},
			qq:{
				remote:"QQ号码重复"
			},
			validateMobileCode:{
				required:"请输入手机验证码"
			}
		},
		submitHandler: function(){
			$('#inputForm').ajaxSubmit(ajax_option);
			return false;
		},
		errorContainer: "#messageBox",
		errorPlacement: function(error, element) {
			$("#messageBox").text("输入有误，请先更正。");
			if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
				error.appendTo(element.parent().parent());
			} else {
				error.insertAfter(element);
			}
		}
	});
});
