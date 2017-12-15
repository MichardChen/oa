<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html >
<head>
	<title>${fns:getConfig('productName')} 登录</title>
	<meta name="decorator" content="blank"/>
	<style type="text/css">
	body{background-image:url(../static/images/loginbg.png)}
      html,body,table{width:100%;text-align:center;}.form-signin-heading{font-family:Helvetica, Georgia, Arial, sans-serif, 黑体;font-size:36px;color: #daddde;}
      .form-signin{position:relative;text-align:left;width:300px;padding:25px 29px 29px;margin:0 auto 20px;background-color:#fff;border:1px solid #e5e5e5;
        	-webkit-border-radius:5px;-moz-border-radius:5px;border-radius:5px;-webkit-box-shadow:0 1px 2px rgba(0,0,0,.05);-moz-box-shadow:0 1px 2px rgba(0,0,0,.05);box-shadow:0 1px 2px rgba(0,0,0,.05);}
      .form-signin .checkbox{margin-bottom:10px;color:#0663a2;} .form-signin .input-label{font-size:16px;line-height:23px;color:#999;}
      .form-signin .input-block-level{font-size:16px;height:auto;margin-bottom:15px;padding:7px;*width:283px;*padding-bottom:0;_padding:7px 7px 9px 7px;}
      .form-signin .btn.btn-large{font-size:16px;} .form-signin #themeSwitch{position:absolute;right:15px;bottom:10px;}
      .form-signin div.validateCode {padding-bottom:15px;} .mid{vertical-align:middle;}
      .header{height:80px;padding-top:8%;} .alert{position:relative;width:300px;margin:0 auto;*padding-bottom:0px;}
      label.error{background:none;width:270px;font-weight:normal;color:inherit;margin:0;}
      input { background-color: #fff;border: 1px solid #1185d6;}
     .form-signin #themeSwitch {position: absolute;right: 10%; bottom: 0px;top: 82%;}
     .com-zoom-flash { height: 0px; }
     body {
   
   color: #dcd6d6;
    
}
    </style>
	<script type="text/javascript">
	    // 如果当前登录页面不是top(最顶层)的window,则把最顶层window切换成登录页面。
	    if(window != top.window){
	    	top.window.location = window.location.href;	
	    }
	    
		$(document).ready(function() {
			$("#loginForm").validate({
				rules: {
					validateCode: {remote: "${pageContext.request.contextPath}/servlet/validateCodeServlet"}
				},
				messages: {
					username: {required: "请填写用户名."},password: {required: "请填写密码."},
					validateCode: {remote: "验证码不正确.", required: "请填写验证码."}
				},
				errorLabelContainer: "#messageBox",
				errorPlacement: function(error, element) {
					error.appendTo($("#loginError").parent());
				} 
			});
		});
		// 如果在框架或在对话框中，则弹出提示并跳转到首页
		if(self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0){
			alert('未登录或登录超时。请重新登录，谢谢！');
			top.location = "${ctx}";
		}
	</script>
</head>
<body style="position: relative;color: #dcd6d6;">
	
	<!--[if lte IE 6]><br/><div class='alert alert-block' style="text-align:left;padding-bottom:10px;"><a class="close" data-dismiss="alert">x</a><h4>温馨提示：</h4><p>你使用的浏览器版本过低。为了获得更好的浏览体验，我们强烈建议您 <a href="http://browsehappy.com" target="_blank">升级</a> 到最新版本的IE浏览器，或者使用较新版本的 Chrome、Firefox、Safari 等。</p></div><![endif]-->
	<div class="header" style="position: relative;">
		<div id="messageBox" class="alert alert-error ${empty message ? 'hide' : ''}"><button data-dismiss="alert" class="close">×</button>
			<label id="loginError" class="error">${message}</label>
		</div>
	
	</div>
	
	<form id="loginForm" class="form-signin" action="${ctx}/login" method="post" style="width: 25%;background-color:rgba(253,253,253,0.2);border:none;">
		<img src="../static/images/mobile/logina.png" style="position: absolute;top:-12%;left: -3.2%;display: block; max-width:106%;">
		<!--20160722 删除 <h1 class="form-signin-heading">${fns:getConfig('productName')}</h1>  -->
		
		<span style="display: block;margin-top: 13%;">
		<label class="input-label" for="username" style="float: left;margin-right: 3%;margin-top: 2%;font-size: 1.5em;font-weight: 500;color: #5a5a5a;">账&nbsp;&nbsp;&nbsp;  号</label>
		<input type="text" id="username" name="username" class="input-block-level required" value="${username}" style="float: left;width: 75%;border: 1px solid #1084d4;">
		</span >
		<span style="display: block;">
		<label class="input-label" for="password" style="float: left;margin-right: 3%;margin-top: 2%;font-size: 1.5em;font-weight: 500;color: #5a5a5a;">密&nbsp;&nbsp;&nbsp;  码</label>
		<input type="password" id="password" name="password" class="input-block-level required" style="float: left;width:75%;border: 1px solid #1084d4;">
		
		<c:if test="${isValidateCodeLogin}">
		</span>
		<div class="validateCode">
			<label class="input-label mid" for="validateCode">验证码</label>
			<sys:validateCode name="validateCode" inputCssStyle="margin-bottom:0;"/>
		</div></c:if><%--
		<label for="mobile" title="手机登录"><input type="checkbox" id="mobileLogin" name="mobileLogin" ${mobileLogin ? 'checked' : ''}/></label> --%>
		<input class="btn btn-primary" type="submit" value="登 录" style="width: 75%;margin-left: 72px;height: 44px;text-align: center;font-size: 1.5em;margin-top:1%;">&nbsp;&nbsp;
		<label for="rememberMe" title="下次不需要再登录" style="margin-left: 18%;margin-top: 2.8%; color: #daddde;"><input type="checkbox" id="rememberMe" name="rememberMe" ${rememberMe ? 'checked' : ''}/> 记住账号</label>
		<!--  <a href="${ctx}/resetPwd">忘记密码?</a>&nbsp;&nbsp;&nbsp;-->
		<!--<a href="${ctx}/register">注册新会员</a>-->
		<div id="themeSwitch" class="dropdown">
			<a class="dropdown-toggle" data-toggle="dropdown" href="#" style="color: #daddde;">${fns:getDictLabel(cookie.theme.value,'theme','默认主题')}<b class="caret"></b></a>
			<ul class="dropdown-menu">
			  <c:forEach items="${fns:getDictList('theme')}" var="dict"><li><a href="#" onclick="location='${pageContext.request.contextPath}/theme/${dict.value}?url='+location.href">${dict.label}</a></li></c:forEach>
			</ul>
			<!--[if lte IE 6]><script type="text/javascript">$('#themeSwitch').hide();</script><![endif]-->
		</div>
	</form>
	
	<div class="footer" style="position: absolute;bottom: -35%; word-spacing: 6px;left: 20%;">
		Copyright &copy; 2011-${fns:getConfig('copyrightYear')} <a href="${pageContext.request.contextPath}${fns:getFrontPath()}">${fns:getConfig('productName')}</a>
	</div>
	<script src="${ctxStatic}/flash/zoom.min.js" type="text/javascript"></script>
	
</body>
</html>