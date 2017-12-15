<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>

<head>
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <title>众建成本云</title>
    <meta name="keywords" content="河联 众建网 施工成本 成本云" />
    <meta name="description" content="众建成本云平台">
    <meta name="author" content="厦门河联信息科技有限公司">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Font CSS (Via CDN) -->
    <link rel='stylesheet' type='text/css' href="${ctxStatic}/common/openSans.css">
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/common/roboto.css">

    <!-- Theme CSS -->
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/assets/skin/default_skin/css/theme.css">
    
    <!-- Admin Forms CSS -->
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/assets/admin-tools/admin-forms/css/admin-forms.css">
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.css"/>
    
    <!-- bootstrap CSS -->
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/jqGrid5.0/css/bootstrap.min.css" />
    
    <!-- Favicon -->
    <link rel="shortcut icon" href="${ctxStatic}/assets/img/favicon.ico">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
   <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
   <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
   <![endif]-->
   <style>
    /* 链接的鼠标悬停 */
		a{
		  cursor:pointer;
		}
   </style>
</head>

<body class="external-page sb-l-c sb-r-c">

    <!-- Start: Main -->
    <div id="main" class="animated fadeIn">

        <!-- Start: Content-Wrapper -->
        <section id="content_wrapper">

            <!-- begin canvas animation bg -->
            <div id="canvas-wrapper">
                <canvas id="demo-canvas"></canvas>
            </div>

            <!-- Begin: Content -->
            <section id="content">

                <div class="admin-form theme-info" id="login1">

                    <div class="row mb15 table-layout">

                        <div class="col-xs-6 va-m pln">
                            <a href="dashboard.html" title="Return to Dashboard">
                                
                            </a>
                        </div>

                        <div class="col-xs-6 text-right va-b pr5">
                            

                        </div>

                    </div>

                    <div id="loginPanel" class="panel panel-info mt10 br-n">

                        <div class="panel-heading heading-border bg-white">
                            <span class="panel-title hidden"><i class="fa fa-sign-in"></i>Register</span>
                            <div class="section row mn">
                                <div class="col-sm-4">
                                    <a href="#" class="button btn-social facebook span-left mr5 btn-block">
                                        <span><i class="fa fa-phone-square"></i>
                                        </span> </a>
                                </div>
                                <div class="col-sm-4">
                                    <a href="#" class="button btn-social twitter span-left mr5 btn-block">
                                        <span><i class="fa fa-weixin"></i>
                                        </span> </a>
                                </div>
                                <div class="col-sm-4">
                                    <a href="#" class="button btn-social googleplus span-left btn-block">
                                        <span><i class="fa fa-qq"></i>
                                        </span> </a>
                                </div>
                            </div>
                        </div>

                        <!-- end .form-header section -->
                        <form method="post" action="${ctx}/login" id="loginForm">
                            <div class="panel-body bg-light p30">
                                <div class="row">
                                    <div class="col-sm-7 pr30">
										<div id="messageBox" class="alert alert-error ${empty message ? 'hide' : ''}" style="border:0px;">
											<label id="loginError" class="error">${message}</label>
										</div>
										<div class="section">
                                            <label for="username" class="field-label text-muted fs18 mb10" style="width:30%;">手机号/用户名 </label>
                                            <label for="username" class="field prepend-icon">
                                                <input type="text" name="username" id="username" autoComplete="on" class="gui-input" placeholder="请输入手机号/登录用户名">
                                                <label for="username" class="field-icon"><i class="fa fa-user"></i>
                                                </label>
                                            </label>
                                        </div>
                                        <!-- end section -->
                                        <div class="section">
                                            <label for="username" class="field-label text-muted fs18 mb10">密码</label>
                                            <label for="password" class="field prepend-icon">
                                                <input type="password" name="password" id="password" class="gui-input" placeholder="请输入密码">
                                                <label for="password" class="field-icon"><i class="fa fa-lock"></i>
                                                </label>
                                            </label>
                                        </div>
                                        <!-- end section -->
                                        <c:if test="${isValidateCodeLogin}"><div class="validateCode">
											<label class="field-label text-muted fs18 mb10" for="validateCode">验证码</label>
											<sys:validateCode name="validateCode" inputCssStyle="margin-bottom:0;"/>
										</div></c:if>
		                             
                                    </div>
                                    <div class="col-sm-5 br-l br-grey pl30">
                                        <div style="width:75%;">
                                         <img src="${ctxStatic}/images/zhongjianwang.jpg" title="众建网" class="img-responsive w250">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- end .form-body section -->
                            <div class="panel-footer clearfix p10 ph15">
                            	<div class="col-sm-4">
                                   <label class="switch block switch-primary pull-left input-align mt10">
                                    <input type="checkbox" name="rememberMe" id="rememberMe" unchecked>
                                    <label for="rememberMe" data-on="YES" data-off="NO"></label>
                                    <span>下次自动登录</span>
                                </label>
                                </div>
                                <div class="col-sm-4" style="text-align:left;">
                                   <span>建议使用IE10以上,谷歌,火狐或360, 搜狗等浏览器,1024x768以上分辨率</span>
                                </div>
                                <div class="col-sm-1" style="text-align:right;padding-top: 2%;">
                                   <a type="button" id="trial" data-toggle='modal' href="#trialDailog" title="">试用</a>
                                </div>
                                <div class="col-sm-1" style="text-align:right;padding-top: 2%;">
                                   <a href="${ctx}/member/register" class="" title="Register">注册</a>
                                </div>
                                <div class="col-sm-2">
                                   <button type="submit" class="button btn-primary mr10 pull-right">登录</button>
                                </div>
                            </div>
                            <!-- end .form-footer section -->
                        </form>
                    </div>
                </div>

            </section>
            <!-- End: Content -->

        </section>
        <!-- End: Content-Wrapper -->

    </div>
    
	<!--此处的iframe是为了预加载(缓存)静态资源用的，没有其他用途 -->
	<div style="display:none;">
		<iframe id="preLoadFrm">
		</iframe>
	</div>
    <!-- End: Main -->

    <!-- BEGIN: PAGE SCRIPTS -->
    <!-- jQuery -->
    <script type="text/javascript" src="${ctxStatic}/vendor/jquery/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="${ctxStatic}/vendor/jquery/jquery_ui/jquery-ui.min.js"></script>
	<script type="text/javascript" src="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.js"></script>
	
    <!-- Bootstrap -->
    <script type="text/javascript" src="${ctxStatic}/jqGrid5.0/js/bootstrap.min.js"></script>

    <!-- Page Plugins -->
    <script type="text/javascript" src="${ctxStatic}/assets/js/pages/login/EasePack.min.js"></script>
    <script type="text/javascript" src="${ctxStatic}/assets/js/pages/login/rAF.js"></script>
    <script type="text/javascript" src="${ctxStatic}/assets/js/pages/login/TweenLite.min.js"></script>
    <script type="text/javascript" src="${ctxStatic}/assets/js/pages/login/login.js"></script>

    <!-- Theme Javascript -->
    <script type="text/javascript" src="${ctxStatic}/assets/js/utility/utility.js"></script>
    <script type="text/javascript" src="${ctxStatic}/assets/js/main.js?version=201601071011"></script>
    <script type="text/javascript" src="${ctxStatic}/assets/js/demo.js?version=201601071011"></script>
	
    <!-- Page Javascript -->
    <script type="text/javascript">
        jQuery(document).ready(function() {

            "use strict";

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
            
            // 点击试用,跳转到试用页面（其他主机、端口)
            $("#trial").click(function(){
            	
            	var redirectUrl;
            	var newPort = "8080";
            	// 重定向到另一端口（试用）。
            	redirectUrl = "http://" + window.location.hostname + ":" + newPort + "${ctxStatic}/constructcost/sys/trial.html";
            	location.href = redirectUrl;
            	
            	return ;
            });
            
            $("#loginForm").validate({
				rules: {
					validateCode: {remote: "${pageContext.request.contextPath}/servlet/validateCodeServlet"}
				},
				messages: {
					username: {required: "请填写手机号/用户名."},
					password: {required: "请填写密码."},
					validateCode: {remote: "验证码不正确.", required: "请填写验证码."}
				},
				errorLabelContainer: "#messageBox",
				errorPlacement: function(error, element) {
					error.appendTo($("#loginError").parent());
				} 
			});
            
            // 加载完登录页面后，自动加载sysIndex中的js。放入浏览器缓存中。
    		setTimeout(function(){
    			// 给iframe赋值，让它自动加载。
    			$("#preLoadFrm").attr("src","${ctxStatic}/constructcost/sys/preLoadJs.html");
    			//$("#preLoadFrm").reload();
    			},500);
			
        });

        // 如果在框架或在对话框中，则弹出提示并跳转到首页
		if(self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0){
			alertx('未登录或登录超时。请重新登录，谢谢！');
			top.location = "${ctx}";
		};
		
    </script>

    <!-- END: PAGE SCRIPTS -->

</body>

</html>
