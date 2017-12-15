<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<%@include file="/WEB-INF/views/include/commonCss.jsp"%>
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <title>众建成本云</title>
    <meta name="keywords" content="河联 众建网 施工成本 成本云" />
    <meta name="description" content="众建成本云平台"/>
    <meta name="author" content="厦门河联信息科技有限公司"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
	
    <!-- Font CSS (Via CDN) -->
    <!-- 
    <link rel='stylesheet' type='text/css' href="${ctxStatic}/common/openSans.css">
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/common/roboto.css">
     -->
    <!-- Theme CSS -->
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/assets/skin/default_skin/css/theme.css">

    <!-- Admin Forms CSS -->
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/assets/admin-tools/admin-forms/css/admin-forms.css">
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.css"/>

    <!-- Favicon -->
    <link rel="shortcut icon" href="${ctxStatic}/assets/img/favicon.ico">
	<link href="${ctxStatic}/jquery-select2/3.4/select2.min.css" rel="stylesheet" type="text/css"/>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
       <script src="${ctxStatic}/ie/html5shiv.js"></script>
       <script src="${ctxStatic}/ie/respond.min.js"></script>
   <![endif]-->
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
                <div class="admin-form theme-info" id="login1" >
                    <!-- 20160101 del mlg. <div class="row mb15 table-layout">
                        <div class="col-xs-6 va-m pln">
                            <a href="dashboard.html" title="Return to Dashboard">     
                            </a>
                        </div>
                    </div>  -->
                    <div class="panel panel-info mt10 br-n" >
                        <div class="panel-heading heading-border bg-white">
                            <div class="section row mn">
                                <div class="col-sm-12">
                                    <a href="#" class="button btn-social facebook span-left mr5 btn-block">
                                        <span><i class="fa fa-user"></i>
                                        </span>注册新公司</a>
                                </div>
                            </div>
                        </div>
                        <form:form modelAttribute="registerModel" id="inputForm">
                            <input id="token" name="token" type="hidden" value="gggsd9099as88asdaasd"/>
                            <div class="panel-body p15 bg-light">
                                <div class="section row">
                                        <div>公司名称</div>
                                        <div>
                                        <label for="memberName" class="field prepend-icon">
                                            <form:input path="memberName" class="gui-input required" placeholder="公司名(*)"/>
                                            <label for="memberName" class="field-icon"><i class="fa fa-user"></i>
                                            </label>
                                        </label>
                                        </div>
                                </div>
                                <div class="section row" style="display:none;">
                                        
                                        <label for="loginName" class="field prepend-icon" >
                                            <input id="loginName" name="loginName" class="gui-input required" placeholder="登录名(*)">
                                            <label for="loginName" class="field-icon"><i class="fa fa-user"></i>
                                            </label>
                                        </label>
                                </div>
                                <div class="section row">
                                       公司管理员密码
                                        <label for="password" class="field prepend-icon">
                                            <input id="password" name="password" type="password" class="gui-input required" placeholder="密码(*)">
                                            <label for="password" class="field-icon"><i class="fa fa-unlock-alt"></i>
                                            </label>
                                        </label>
                                </div>
                                <div class="section row">
                                       确认密码
                                        <label for="checkPassWord" class="field prepend-icon">
                                            <input id="checkPassWord" class="gui-input required" type="password" placeholder="确认密码(*)" equalTo="#password">
                                            <label for="checkPassWord" class="field-icon"><i class="fa fa-unlock-alt"></i>
                                            </label>
                                        </label>
                                </div>
                                 <div class="section row">
                                       手机号码
                                       <label for="mobile" class="field prepend-icon">
                                            <input id="mobile" name="mobile" class="gui-input required" placeholder="手机号码(*)">
                                            <label for="mobile" class="field-icon"><i class="fa fa-unlock-alt"></i>
                                            </label>
                                       </label>
                                </div>
                                <div class="section row">
                                      手机验证码     
                                       <label for="validateMobileCode" class="field prepend-icon">
                                            <input id="validateMobileCode" name="validateMobileCode" style="width:74%" class="gui-input required" >
                                            <input type="button" id="verifyBtn" class="btn btn-primary" value="获取验证码"/>
                                       </label>
                                </div>
                                <!--<div class="form-group" style="display:none;">
									<div class="col-sm-10" style="width:25%;">
										<form:select path="areaId">
											<form:options id="areaId" items="${cityList}" itemLabel="label" itemValue="value" htmlEscape="false" />
										</form:select>
									</div>
								</div>
                                <div class="section row" style="display:none;">
                                        <label for="name" class="field prepend-icon">
                                            <input id="name" name="name" class="gui-input" placeholder="联系人">
                                            <label for="name" class="field-icon"><i class="fa fa-unlock-alt"></i>
                                            </label>
                                        </label>
                                </div>
                                <div class="section row" style="display:none;">
                                        <label for="address" class="field prepend-icon">
                                            <input id="address" name="address" class="gui-input" placeholder="地址">
                                            <label for="address" class="field-icon"><i class="fa fa-unlock-alt"></i>
                                            </label>
                                        </label>
                                </div>
                                <div class="section row" style="display:none;">
                                        <label for="phone" class="field prepend-icon">
                                            <input id="phone" name="phone" class="gui-input" placeholder="固定电话">
                                            <label for="phone" class="field-icon"><i class="fa fa-unlock-alt"></i>
                                            </label>
                                        </label>
                                </div>
                                <div class="section row" style="display:none;">
                                        <label for="qq" class="field prepend-icon">
                                            <input id="qq" name="qq" class="gui-input" placeholder="QQ">
                                            <label for="qq" class="field-icon"><i class="fa fa-unlock-alt"></i>
                                            </label>
                                        </label>
                                </div> -->
                            </div>
                            <div class="validateCode">
								<label class="field-label text-muted fs18 mb10" for="validateCode">验证码</label>
								<sys:validateCode name="validateCode" inputCssStyle="margin-bottom:0;"/>
							</div>
                            <!-- end .form-body section -->
                            <div class="panel-footer clearfix">
                                <input id="btnSubmit" class="btn btn-primary" type="submit" value="注册"/>&nbsp;
								<input id="btnCancel" class="btn" type="button" value="返 回" onclick="returnLogin()"/>
                            </div>
                            <!-- end .form-footer section -->
                        </form:form>
                    </div>
                </div>

            </section>
            <!-- End: Content -->
        </section>
        <!-- End: Content-Wrapper -->
    </div>
</body>
<%@include file="/WEB-INF/views/include/commonJs.jsp"%>
<%@include file="/WEB-INF/views/modules/constructcost/member/registerJs.jsp"%>
</html>
