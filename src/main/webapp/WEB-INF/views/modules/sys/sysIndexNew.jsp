<%@ page contentType="text/html;charset=UTF-8"%>
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
    <!-- <link rel='stylesheet' type='text/css' href="${ctxStatic}/common/openSans.css">
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/common/roboto.css">
     -->
    <!-- Theme CSS -->
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/assets/skin/default_skin/css/theme.css">

    <!-- Admin Panels CSS -->
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/assets/admin-tools/admin-plugins/admin-panels/adminpanels.css">

    <!-- Admin Forms CSS -->
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/assets/admin-tools/admin-forms/css/admin-forms.css">
     
    <!-- 浏览器图标 -->
    <link rel="shortcut icon" href="${ctxStatic}/images/favicon.ico">
    
    <!-- TAB页签的CSS -->
    <link rel="Stylesheet" href="${ctxStatic}/jerichotab/css/jquery.jerichotab.css" />

    <!-- JBox的CSS调整(为了应对Jbox在 adminDesign框架下的按钮不整齐的问题)-->
	<style>
		#jbox-content{float:left;}
		.jbox-button-panel{position:relative;}
		.jbox-button{position:relative;bottom:5px;}
		.jbox-title-panel{padding-top:0 !important;}
		.jbox-close{top:3px !important;}
		
		/* 链接的鼠标悬停 */
		a{
		  cursor:pointer;
		}
	</style>

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="${ctxStatic}/ie/html5shiv.js"></script>
    <script src="${ctxStatic}/ie/respond.min.js"></script>
    <![endif]-->

</head>
<!-- 20151214 加上 sb-l-m，使左侧的菜单条缩回去.  -->
<body class="dashboard-page sb-l-o sb-r-c sb-l-m">

    <!-- Start: Theme Preview Pane。-->
    <div id="skin-toolbox" class="hidden">
        <div class="panel">
            <div class="panel-heading">
                <span class="panel-icon"><i class="fa fa-gear text-primary"></i>
                </span>
                <span class="panel-title"> 换肤</span>
            </div>
        </div>
    </div>
    <!-- End: Theme Preview Pane -->

    <!-- Start: Main -->
    <div id="main">

        <!-- Start: Header -->
        <header class="navbar navbar-fixed-top bg-light">
            <div class="navbar-branding">
                <!--  <a class="navbar-brand" href="dashboard.html">众建成本云
                 </a>
                <span id="toggle_sidemenu_l" class="glyphicons glyphicons-show_lines"></span> -->
                <!-- 20151214 增加众建网的logo。Logo设成60是为了把logo铺满 -->
                <img src="${ctxStatic}/images/zhongjianwang.jpg" style="height:60px;width:60px;" />
                <ul class="nav navbar-nav pull-right hidden">
                    <li>
                        <a href="#" class="sidebar-menu-toggle">
                            <span class="octicon octicon-ruby fs20 mr10 pull-right "></span>
                        </a>
                    </li>
                </ul>
            </div>
            <ul class="nav navbar-nav navbar-left">
                <li>
                    <span id="toggle_sidemenu_l2" class="glyphicon glyphicon-log-in fa-flip-horizontal hidden"></span>
                </li>
                <li class="dropdown hidden">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <span class="glyphicons glyphicons-settings fs14"></span>
                    </a>
                    <ul class="dropdown-menu" role="menu">
                        <li>
                            <a href="javascript:void(0);">
                                <span class="fa fa-times-circle-o pr5 text-primary"></span> Reset LocalStorage </a>
                        </li>
                        <li>
                            <a href="javascript:void(0);">
                                <span class="fa fa-slideshare pr5 text-info"></span> Force Global Logout </a>
                        </li>
                        <li class="divider mv5"></li>
                        <li>
                            <a href="javascript:void(0);">
                                <span class="fa fa-tasks pr5 text-danger"></span> Run Cron Job </a>
                        </li>
                        <li>
                            <a href="javascript:void(0);">
                                <span class="fa fa-wrench pr5 text-warning"></span> Maintenance Mode </a>
                        </li>
                    </ul>
                </li>
                
                <li class="hidden-xs menu">
                    <c:if test="${sessionScope.curProjectName!=''}">
	                    <a class="menu topbar-menu-toggle" data-href="${ctx}/projectinfo/ccPrjInfo?updateFlag=${sessionScope.curProjectName}" target="mainFrame" data-descp = "工程信息" title="当前工程">
	                        <span id="curProjectName" style="color:white;font-size:16px;">${sessionScope.curProjectName}</span>
	                    </a>
                    </c:if>
                    <c:if test="${sessionScope.curProjectName==''}">
                      <a class="menu topbar-menu-toggle" data-href="${ctx}/projectinfo/ccPrjInfo" target="mainFrame" data-descp = "新建工程" title="新建工程">
                        <span>没有当前工程，点击可添加工程 </span>
                      </a>
                    </c:if>
                </li>
                <li class="hidden-xs menu">
                    <a class="menu topbar-menu-toggle" data-href="${ctx}/projectinfo/ccPrjInfo" target="mainFrame" data-descp = "新建工程" title="新建工程">
                        <span id="newPrj" class="fa fa-plus fa-lg">新建</span>
                    </a>
                </li>
                <li class="hidden-xs">
                    <a id="closePrj" class="topbar-menu-toggle" title="关闭工程">
                        <span class="fa fa-power-off fa-lg">关闭</span>
                    </a>
                </li>
                <li id="curPrjli" class="dropdown dropdown-item-slide">
                    <a class="dropdown-toggle pl10 pr10" data-toggle="dropdown" href="#" title="切换工程">
                        <span class="fa fa-history fa-lg">切换</span>
                    </a>
                     <ul  class="dropdown-menu dropdown-hover dropdown-persist pn w350 bg-white animated animated-shorter fadeIn" role="menu">
                        <c:set var="prjCount" value="${fn:length(ccPrjInfoList)}"></c:set>
                        <c:if test="${prjCount>0}">
                     	  <c:forEach items="${ccPrjInfoList}" var="ccPrjInfo">
	                     	 <li class="p10 br-t item-1" style="height:26px;">
	                            <div  class="media" style="margin-top:-9px;">
	                                <div class="media-body va-m">
	                                  <a class="media-left" data-href="${ctx}/projectinfo/ccPrjInfo/chang?id=${ccPrjInfo.id}&status=${ccPrjInfo.status}&prjName=${ccPrjInfo.prjName}" target="mainFrame"><h5 class="media-heading mv5">${ccPrjInfo.prjName}</h5></a>
	                                </div>
	                            </div>
	                         </li>
                          </c:forEach>
                        </c:if>
                        <c:if test="${prjCount==0}">
                             <li class="p10 br-t item-1" style="height:26px;">
	                            <div  class="media" style="margin-top:-9px;">
	                                <div class="media-body va-m">
	                                  <h5 class="media-heading mv5">暂时没有历史工程</h5>
	                                </div>
	                            </div>
	                         </li>
                        </c:if>
                    </ul>
                </li>
                <li>
                 <c:if test="${productSign!=''}">
		            <span style="font-size:16px;">${productSign}</span>
		         </c:if> 
                </li>
            </ul>
           
            <form id="serachForm" class="navbar-form navbar-left navbar-search ml5" role="search" style="display:none;">
                <div class="form-group" style="display:none;">
                    <input type="text" class="form-control" placeholder="搜索清单,材料.." value="搜索清单,材料..">
                </div>
            </form>
            <ul class="nav navbar-nav navbar-right">
               <li class="hidden-xs">
                  <!-- 联系QQ(詹2638582021) -->
                  <a href="tencent://Message/?Uin=2638582021&amp;websiteName=www.holdbuild.com=&amp;Menu=yes" class="btn btn-qq">
                    <span id="XXX" class="fa fa-qq fa-lg"></span>
                  </a>
               </li>
               <li class="hidden-xs menu">
                   <a class="menu topbar-menu-toggle" data-href="${ctx}/custservice/help" target="mainFrame" data-descp = "向导" title="向导">
                        <span id="XXX" class="fa fa-book fa-lg"></span>
                   </a>
                </li>
               <li id="skinLi" class="dropdown dropdown-item-slide">
                  <a class="dropdown-toggle pl10 pr10" data-toggle="dropdown" href="#" title="换肤">
                        <span class="fa fa-leaf"></span>
                  </a>
                  <ul class="dropdown-menu dropdown-hover dropdown-persist pn w350 bg-white animated animated-shorter fadeIn" role="menu">
                      <div class="panel-body pn">
	                    <ul class="nav nav-list nav-list-sm pl15 pt10" role="tablist">
	                       <li class="active">
	                         <a href="#toolbox-header" role="tab" data-toggle="tab">导航栏</a>
	                       </li>
	                       <li>
	                         <a href="#toolbox-sidebar" role="tab" data-toggle="tab">侧边栏</a>
	                       </li>
	                     </ul>
                         <div class="tab-content p20 ptn pb15">
                          <div role="tabpanel" class="tab-pane active" id="toolbox-header">
                            <form id="toolbox-header-skin">
                              <h4 class="mv20">导航栏背景色</h4>                  
                              <div class="skin-toolbox-swatches">
                                <div class="checkbox-custom fill checkbox-primary mb5">
                                    <input type="radio" name="headerSkin" id="headerSkin1" value="bg-primary">
                                    <label for="headerSkin1">Primary</label>
                                </div>
                                <div class="checkbox-custom fill checkbox-info mb5">
                                    <input type="radio" name="headerSkin" id="headerSkin3" value="bg-info">
                                    <label for="headerSkin3">Info</label>
                                </div>
                                <div class="checkbox-custom fill checkbox-warning mb5">
                                    <input type="radio" name="headerSkin" id="headerSkin4" value="bg-warning">
                                    <label for="headerSkin4">Warning</label>
                                </div>
                                <div class="checkbox-custom fill checkbox-danger mb5">
                                    <input type="radio" name="headerSkin" id="headerSkin5" value="bg-danger">
                                    <label for="headerSkin5">Danger</label>
                                </div>
                                <div class="checkbox-custom fill checkbox-alert mb5">
                                    <input type="radio" name="headerSkin" id="headerSkin6" value="bg-alert">
                                    <label for="headerSkin6">Alert</label>
                                </div>
                                <div class="checkbox-custom fill checkbox-system mb5">
                                    <input type="radio" name="headerSkin" id="headerSkin7" value="bg-system">
                                    <label for="headerSkin7">System</label>
                                </div>
                                <div class="checkbox-custom fill checkbox-success mb5">
                                    <input type="radio" name="headerSkin" id="headerSkin2" value="bg-success">
                                    <label for="headerSkin2">Success</label>
                                </div>
                               
                              </div>
                           </form>
                        </div>
                        <div role="tabpanel" class="tab-pane" id="toolbox-sidebar">
                           <form id="toolbox-sidebar-skin">

                            <h4 class="mv20">侧边栏底色</h4>
                            <div class="skin-toolbox-swatches">
                                <div class="checkbox-custom fill checkbox-disabled mb5">
                                    <input type="radio" name="sidebarSkin" id="sidebarSkin1" value="sidebar-light">
                                    <label for="sidebarSkin1">Light</label>
                                </div>
                                <div class="checkbox-custom fill checkbox-light mb5">
                                    <input type="radio" name="sidebarSkin" id="sidebarSkin2" value="sidebar-light light">
                                    <label for="sidebarSkin2">Lighter</label>
                                </div>
                            </div>

                        </form>
                      </div>
                    
                    </div>
                    <!--  2015-12-29 删除。  
                    <div class="form-group mn br-t p15">
                      <a href="#" id="clearLocalStorage" class="btn btn-primary btn-block pb10 pt10">恢复设置</a>
                    </div>
                    -->
                  </div>
                 </ul>
                </li>   
            
                <li class="hidden-xs">
                    <a class="request-fullscreen toggle-active" href="#" title="全屏显示">
                        <span class="octicon octicon-screen-full fs18"></span>
                    </a>
                </li>
                
                <li class="dropdown dropdown-item-slide">
                    <a class="dropdown-toggle pl10 pr10" data-toggle="dropdown" href="#" title="系统信息">
                        <span class="octicon octicon-radio-tower fs18"></span>
                    </a>
                    <ul class="dropdown-menu dropdown-hover dropdown-persist pn w350 bg-white animated animated-shorter fadeIn" role="menu">
                        <li class="bg-light p8">
                            <span class="fw600 pl5 lh30"> 系统通知</span>
                            <span class="label label-warning label-sm pull-right lh20 h-20 mt5 mr5">2</span>
                        </li>
                        <li class="p10 br-t item-1">
                            <div class="media">
                                <a class="media-left" href="#"> <img src="assets/img/avatars/2.jpg" class="mw40" alt="holder-img"> </a>
                                <div class="media-body va-m">
                                    <h5 class="media-heading mv5">众建成本喊你来试用！ <small class="text-muted">- 2016-02-02</small> </h5>
                                    <a class="text-system" href="#"> 系统管理员 </a>
                                </div>
                            </div>
                        </li>
                        <li class="p10 br-t item-2">
                            <div class="media">
                                <a class="media-left" href="#"> <img src="assets/img/avatars/3.jpg" class="mw40" alt="holder-img"> </a>
                                <div class="media-body va-m">
                                    <h5 class="media-heading mv5">号外！众建成本的神器，想知道吗 <small class="text-muted">- 2016-02-23</small> </h5>
                                    <a class="text-system" href="#"> 系统管理员 </a>
                                </div>
                            </div>
                        </li>
                    </ul>
                </li>
                
                <li class="ph10 pv20 hidden-xs"> <i class="fa fa-circle text-tp fs8"></i>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle fw600 p15" data-toggle="dropdown" title="个人中心">
                        <span>${fns:getUser().mobile}</span>
                        <span class="caret caret-tp hidden-xs"></span>
                    </a>
                    <ul class="dropdown-menu dropdown-persist pn w250 bg-white" role="menu">
                        <li class="of-h menu">
                            <a class="fw600 p12 animated animated-short fadeInUp" data-href="${ctx}/act/task/todo" target="mainFrame" data-descp="待办事项">
                                <span class="fa fa-envelope pr5"></span>代办事项
                                <span class="pull-right lh20 h-20 label label-warning label-sm">2</span>
                            </a>
                        </li>
                        <li class="br-t of-h menu">
                            <a class="fw600 p12 animated animated-short fadeInDown" data-href="${ctx}/sys/user/info" target="mainFrame" data-descp="个人信息">
                                <span class="fa fa-gear pr5"></span>个人信息</a>
                        </li>
                        <li class="br-t of-h menu">
                            <a class="fw600 p12 animated animated-short fadeInDown" data-href="${ctx}/sys/user/modifyPwd" target="mainFrame" data-descp="修改密码">
                                <span class="fa fa-key pr5"></span>修改密码</a>
                        </li>
                        <li class="br-t of-h">
                            <a href="${ctx}/logout" class="fw600 p12 animated animated-short fadeInDown">
                                <span class="fa fa-power-off pr5"></span>退出</a>
                        </li>
                    </ul>
                </li>
            </ul>

        </header>
        <!-- End: Header -->

        <!-- Start: Sidebar -->
        <aside id="sidebar_left" class="nano nano-primary">
            <div class="nano-content">

                <!-- Start: Sidebar Header -->
                <header class="sidebar-header">
                    <div class="user-menu">
                        <div class="row text-center mbn">
                            <div class="col-xs-4">
                                <a href="dashboard.html" class="text-primary" data-toggle="tooltip" data-placement="top" title="Dashboard">
                                    <span class="glyphicons glyphicons-home"></span>
                                </a>
                            </div>
                            <div class="col-xs-4">
                                <a href="pages_messages.html" class="text-info" data-toggle="tooltip" data-placement="top" title="Messages">
                                    <span class="glyphicons glyphicons-inbox"></span>
                                </a>
                            </div>
                            <div class="col-xs-4">
                                <a href="pages_profile.html" class="text-alert" data-toggle="tooltip" data-placement="top" title="Tasks">
                                    <span class="glyphicons glyphicons-bell"></span>
                                </a>
                            </div>
                            <div class="col-xs-4">
                                <a href="pages_timeline.html" class="text-system" data-toggle="tooltip" data-placement="top" title="Activity">
                                    <span class="glyphicons glyphicons-imac"></span>
                                </a>
                            </div>
                            <div class="col-xs-4">
                                <a href="pages_profile.html" class="text-danger" data-toggle="tooltip" data-placement="top" title="Settings">
                                    <span class="glyphicons glyphicons-settings"></span>
                                </a>
                            </div>
                            <div class="col-xs-4">
                                <a href="pages_gallery.html" class="text-warning" data-toggle="tooltip" data-placement="top" title="Cron Jobs">
                                    <span class="glyphicons glyphicons-restart"></span>
                                </a>
                            </div>
                        </div>
                    </div>
                </header>
                <!-- End: Sidebar Header -->
                
                <!-- sidebar menu -->
                <ul id="menu" class="nav sidebar-menu">
                		<li class="sidebar-label pt20">项目</li>
                        <c:set var="subMenu" value="false"/>
						<c:forEach items="${fns:getMenuListForPC()}" var="menu" varStatus="idxStatus">
							<c:if test="${menu.isShow eq '1' && menu.media eq 'pc'}"> <!-- 过滤掉隐藏菜单和非PC菜单  -->
							   <!--当前处理数据为1级菜单，并且上一次处理的菜单为子菜单，则关闭ul li -->
							   <c:if test="${menu.parent.id eq '1' && subMenu}">
							      <c:set var="subMenu" value="false"/> <!--清除子菜单的标志-->
							            </ul>
					                    </li>
					                   </li>
							   </c:if> 
							   <!-- 1級菜單的菜单项(有子菜单)显示 -->
							   <c:if test="${menu.parent.id eq '1' && empty menu.href}">
							     <li class="sidebar-label pt15">XX</li>
									<li>
										<a class="accordion-toggle" href="#">
											<span class="fa fa-cog"></span>
				                            <span class="sidebar-title">${menu.name}</span>
				                            <span class="caret"></span>
										</a>
										<ul class="nav sub-nav" style="height:200px;overflow:auto;">
								</c:if>
								<!-- 1級菜單的菜单项(无子菜单)显示 -->
								<c:if test="${menu.parent.id eq '1' && not empty menu.href}">
									<li class="menu">
										<div style="text-align:center;">
										<a class="${menu.icon}" data-href="${fn:indexOf(menu.href, '://') eq -1 ? ctx : ''}${menu.href}" target="mainFrame" data-descp="${menu.name}"></a>
										<div>${menu.name}</div>
										</div>
									</li>
								</c:if>
								<!-- 2級菜單的菜单项显示 -->
								<c:if test="${menu.parent.id ne '1' && not empty menu.href }">
								    <c:set var="subMenu" value="true"/> <!--设置子菜单的标志-->
									<li class="menu">
										<a class="menu" data-href="${fn:indexOf(menu.href, '://') eq -1 ? ctx : ''}${menu.href}" target="mainFrame" data-descp="${menu.name}">
										<span class="${menu.icon}"></span>${menu.name}</a>
									</li>
									<!-- 如果当前二级菜单项为最后1条菜单项，则关闭ul li -->
									<c:if test="idxStatus.last">
										 </ul>
					                    </li>
					                   </li>
									</c:if>
								</c:if>
							</c:if>
						</c:forEach>
					</ul>
                <%--
                <ul class="nav sidebar-menu">
                    <li class="sidebar-label pt20">项目</li>
                    <li class="menu" >
                        <div style="text-align:center;">
                        <a class="fa fa-file-text-o fa-lg menu" data-href="${ctx}/projectinfo/prjvol" target="mainFrame" data-descp="清单"></a>
                        <div>清单</div>
                        </div>
                    </li>
                   
                    <li class="sidebar-label pt15">设置</li>
                    <li>
                        <a class="accordion-toggle" href="#">
                            <span class="fa fa-cog"></span>
                            <span class="sidebar-title">系统设置</span>
                            <span class="caret"></span>
                        </a>
                        <ul class="nav sub-nav">
                            <li class="menu">
                                <a class="menu" data-href="${ctx}/member/memberinfo" target="mainFrame" data-descp="会员信息">
                                    <span class="glyphicons glyphicons-show_big_thumbnails"></span>会员信息</a>
                            </li>
                        </ul>
                    </li>
                    <!-- sidebar resources -->
                    <!-- sidebar bullets -->
                    <!-- sidebar progress bars -->
                </ul>
                 --%>
                <!-- 151214 去除缩拉用的图标。 
                <div class="sidebar-toggle-mini">
                    <a href="#">
                        <span class="fa fa-sign-out"></span>
                    </a>
                </div>
                 -->
            </div>
        </aside>

        <!-- Start: Content-Wrapper -->
        <section id="content_wrapper">

            <!-- Start: Topbar-Dropdown -->
            <div id="topbar-dropmenu">
                <div class="topbar-menu row">
                    <div class="col-xs-4 col-sm-2">
                        <a href="#" class="metro-tile bg-success">
                            <span class="metro-icon glyphicons glyphicons-inbox"></span>
                            <p class="metro-title">Messages</p>
                        </a>
                    </div>
                    <div class="col-xs-4 col-sm-2">
                        <a href="#" class="metro-tile bg-info">
                            <span class="metro-icon glyphicons glyphicons-parents"></span>
                            <p class="metro-title">Users</p>
                        </a>
                    </div>
                    <div class="col-xs-4 col-sm-2">
                        <a href="#" class="metro-tile bg-alert">
                            <span class="metro-icon glyphicons glyphicons-headset"></span>
                            <p class="metro-title">Support</p>
                        </a>
                    </div>
                    <div class="col-xs-4 col-sm-2">
                        <a href="#" class="metro-tile bg-primary">
                            <span class="metro-icon glyphicons glyphicons-cogwheels"></span>
                            <p class="metro-title">Settings</p>
                        </a>
                    </div>
                    <div class="col-xs-4 col-sm-2">
                        <a href="#" class="metro-tile bg-warning">
                            <span class="metro-icon glyphicons glyphicons-facetime_video"></span>
                            <p class="metro-title">Videos</p>
                        </a>
                    </div>
                    <div class="col-xs-4 col-sm-2">
                        <a href="#" class="metro-tile bg-system">
                            <span class="metro-icon glyphicons glyphicons-picture"></span>
                            <p class="metro-title">Pictures</p>
                        </a>
                    </div>
                </div>
            </div>
            <!-- End: Topbar-Dropdown -->

            <!-- Start: Topbar -->
            <!-- End: Topbar -->

            <!-- Begin: Content -->
            <section id ="content" class ="animated fadeIn" style ="padding:0;margin:0;">
                <div id="openClose" style="height:0px;" class="close">&nbsp;</div>
				<div id="right" style="height:650px;padding:0;margin:0;">
					<iframe id="mainFrame" name="mainFrame" src="" style="overflow:visible;" scrolling="yes" frameborder="no" width="100%" height="650px"></iframe>
				</div>
            </section>
            <!-- End: Content -->
            
           
        </section>
        <!-- End: Content-Wrapper -->

        <!-- Start: Right Sidebar -->
        <aside id="sidebar_right" class="nano">
            <div class="sidebar_right_content nano-content">
                <div class="tab-block sidebar-block br-n">
                    <ul class="nav nav-tabs tabs-border nav-justified hidden">
                        <li class="active">
                            <a href="#sidebar-right-tab1" data-toggle="tab">Tab 1</a>
                        </li>
                        <li>
                            <a href="#sidebar-right-tab2" data-toggle="tab">Tab 2</a>
                        </li>
                        <li>
                            <a href="#sidebar-right-tab3" data-toggle="tab">Tab 3</a>
                        </li>
                    </ul>
                    
                    <!-- end: .tab-content -->
                </div>
            </div>
        </aside>
        <!-- End: Right Sidebar -->
    </div>
    <!-- End: Main -->
	

    <!-- BEGIN: PAGE SCRIPTS -->

    <!-- jQuery -->
    <script type="text/javascript" src="${ctxStatic}/vendor/jquery/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="${ctxStatic}/vendor/jquery/jquery_ui/jquery-ui.min.js"></script>

    <!-- Bootstrap -->
    <script type="text/javascript" src="${ctxStatic}/jqGrid5.0/js/bootstrap.min.js"></script>

    <!-- Sparklines CDN 
    <script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/jquery-sparklines/2.1.2/jquery.sparkline.min.js"></script>
    -->
    <!-- Chart Plugins 
    <script type="text/javascript" src="${ctxStatic}/vendor/plugins/highcharts/highcharts.js"></script>
    <script type="text/javascript" src="${ctxStatic}/vendor/plugins/circles/circles.js"></script>
    <script type="text/javascript" src="${ctxStatic}/vendor/plugins/raphael/raphael.js"></script>
    -->

    <!-- Holder js  
    <script type="text/javascript" src="${ctxStatic}/assets/js/bootstrap/holder.min.js"></script>
    -->

    <!-- Theme Javascript -->
    <script type="text/javascript" src="${ctxStatic}/assets/js/utility/utility.js"></script>
    <script type="text/javascript" src="${ctxStatic}/assets/js/main.js?version=201601071011"></script>
    <script type="text/javascript" src="${ctxStatic}/assets/js/demo.js?version=201601071011"></script>

    <!-- Admin Panels  -->
    <script type="text/javascript" src="${ctxStatic}/assets/admin-tools/admin-plugins/admin-panels/json2.js"></script>
    <script type="text/javascript" src="${ctxStatic}/assets/admin-tools/admin-plugins/admin-panels/jquery.ui.touch-punch.min.js"></script>
    <script type="text/javascript" src="${ctxStatic}/assets/admin-tools/admin-plugins/admin-panels/adminpanels.js"></script>
    
    <!-- Page Javascript -->
    <script type="text/javascript" src="${ctxStatic}/assets/js/pages/widgets.js"></script>
    
    <!-- TAB页签  -->
    <script type="text/javascript" src="${ctxStatic}/jerichotab/js/jquery.jerichotab.js"></script>
    <!-- JBox插件引入  -->
    <link href="${ctxStatic}/jquery-jbox/2.3/Skins/Bootstrap/jbox.min.css" rel="stylesheet" />
    <script type="text/javascript">
        // jbox2.3使用Jquery 1.9以下。修正代码适应Jquery 1.11.1。 
    	$.browser=$.browser||{};
    	$.browser.msie = /msie/.test(navigator.userAgent.toLowerCase());
    </script>
	<script src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.min.js" type="text/javascript"></script>
	
    <script type="text/javascript" >
     var leftWidth = 0;       // 左边内容的宽度。
     var tabTitleHeight = 34; // 页签标题的高度
     
     // 自动调整窗口大小。被wsize.min.js的windows.resize()等调用。
     function wSize(){
    	 $(".jericho_tab iframe").height($("#right").height() - tabTitleHeight);
        	if (!$("#openClose").is(":hidden")){
				$("#right").width($("#content").width()- $("#openClose").width() -5);
			}else{
				$("#right").width("100%");
			}
    }
    </script>
    <script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
    
    <script type="text/javascript">
        var prjStatus="${sessionScope.curProjectStatus}";//切换工程时用来储存工程状态,登录页面初次加载时候从session获取当前工程的状态
		var mapHref = new Map();
		// 自定义Map对象。
		function Map(){
			this.container = new Object();
		}
		Map.prototype.put = function(key, value){
			this.container[key] = value;
		}
		Map.prototype.get = function(key){
			return this.container[key];
		}
		// 把页签的信息放入到map中，供页签函数addTabFmPage()调用。
		// 注意:${ctx}/XXX/XXX 不要用斜线结尾, 如${ctx}/XXX/XXX/
		var ctx = "${ctx}";
		// 从后台获取菜单的信息
		var menuModel = eval("(" + '${menuListForPC}' + ")");
		var menuList = menuModel.rows;
		// 根据菜单数据，设置到页签函数用的map上。
		for(var i=0;i < menuList.length;i++){
			var menu = menuList[i];
			var name = menu.name;
			var href = menu.href;
			// 对于http,https的链接，不能增加 ctx的前缀。http://localhost:6001/XXXXXX
			if(href.substring(0,4)=="http"){
				href = href;
			}else{ // ConstructCost/a/XXX/XXX
				href = ctx + href;
			}
			mapHref.put(name,href);
		}  
		
		/* mapHref.put("报告期间"     ,"${ctx}/projectinfo/rptcycle" );
		mapHref.put("相关单位信息" ,"${ctx}/projectinfo/partner"  );
		mapHref.put("合同"         ,"${ctx}/realcost/subcontract" );
		mapHref.put("合同明细"     ,"${ctx}/realcost/realcost"    );
		mapHref.put("清单"         ,"${ctx}/projectinfo/prjvol"   );
		mapHref.put("材料"         ,"${ctx}/prjstdinfo/material" );
		mapHref.put("代办事项"     ,"${ctx}/workflow/todo"        );
		mapHref.put("向导"        ,"${ctx}/custservice/help"     );
		mapHref.put("新建工程操作指导"        ,"${ctx}/custservice/help/newProject"     );
		mapHref.put("导入清单操作指导"        ,"${ctx}/custservice/help/listImport"     );
		mapHref.put("建立目标操作指导"        ,"${ctx}/custservice/help/targetBuild"     );
		mapHref.put("目标分解操作指导"        ,"${ctx}/custservice/help/targetDepart"     );
		mapHref.put("分包合同操作指导"        ,"${ctx}/custservice/help/subContract"     );
		mapHref.put("分包计量操作指导"        ,"${ctx}/custservice/help/subAccount"     );
		mapHref.put("收付款操作指导"        ,"${ctx}/custservice/help/pay"     );
		mapHref.put("业主计量操作指导"        ,"${ctx}/custservice/help/executiveAccount"     );
		mapHref.put("成本分析操作指导"        ,"${ctx}/custservice/help/analyse"     );
		mapHref.put("报表打印","http://localhost:6001/quiee/reportJsp/showReport.jsp");  */
		
		
        jQuery(document).ready(function() {

            "use strict";

            // Init Theme Core      
            Core.init({
                sbm: "sb-l-c",
            });

            // Init Demo JS
            Demo.init();
			
            // 初始化页签。
            $.fn.initJerichoTab({
                renderTo: '#right', uniqueId: 'jerichotab',
                contentCss: { 'height': $('#right').height() - tabTitleHeight },
                tabs: [], loadOnce: true, tabWidth: 120, titleHeight: tabTitleHeight
            });

            // 调整TAB页签中，Iframe与页签的距离。
            $("#jerichotab_contentholder").css("margin-top","-6px");
            
            // Init Widget Demo JS
            // demoHighCharts.init();

            // Because we are using Admin Panels we use the OnFinish 
            // callback to activate the demoWidgets. It's smoother if
            // we let the panels be moved and organized before 
            // filling them with content from various plugins

            // Init plugins used on this page
            // HighCharts, JvectorMap, Admin Panels

            // Init Admin Panels on widgets inside the ".admin-panels" container
            //$('.admin-panels').adminpanel({
            
            $("li.menu").bind("click",function(){
                // 增加页签，并自动刷新页面。
                var link = $(this).find("a");
            	addTab(link,true);
			});
			
			// 切换工程的代码。
			$("a.media-left").bind("click",function(){
				var curPorjectName = $(this).text();
				
				$.ajax({
					async:true,
					type:'post',
					url:encodeURI(encodeURI($(this).attr("data-href"))),
					success:function(data){//切换工程刷新当前页签
					    
						var flag = eval("(" + data + ")");
						if(flag.result == 0){
							$("#curProjectName").text(curPorjectName);
							prjStatus = flag.userdata.msg;
							// 关闭 dropdown menu的下拉状态。
							$("#curPrjli").removeClass("open");
							$('.jericho_tabs').loadData(true);
							addTabFmPage($(this),"清单",true);
						}else{
							alertx("切换工程失败");
						}
					}
				});
			});

			// 2016/1/7 mlg. 启动定时器(100毫秒)，主页进入后自动加载 工程清单 页签。  
			setTimeout(function(){ 
				     addTabFmPage($(this),"向导",true,"");
			         },100);
			
        });

        // 首先检测是否页签已经打开，如果已经打开，则重新激活，不重复打开。
        // 参数：pageName 页签名 如 相关单位信息
        //     needReload 是否需要重新加载数据
        //     newUrl     新加载数据的URL.
		function activateOpenTab(pageName,needReload,newUrl){
            var page = $(".tabs li[name='" + pageName + "']");
            if(page.length > 0){
                // 页签已经存在。
                var id = page.attr("id"); // id属性的值为 "jerichotab_12" (12为页签OrderKey)
                id = id.split("_"); 
                 
                // 判断是否需要重新加载数据（如在带URL参数时),
                if(needReload){
                	// 重新设置iframe的src(即用新url参数代替旧参数)
                	$("#jerichotabiframe_"+id[1]).attr("src",newUrl+"&tabPageId=jerichotabiframe_" + id[1]);
                	$("#jerichotab_" + id[1]).attr("datalink",newUrl);
                	// 激活该页签。(页面数据需要刷新)
                    $.fn.setTabActive(id[1]).loadData(true);
                }
                else{
                	 // 激活该页签。(页面数据并没有刷新)
                    $.fn.setTabActive(id[1]).loadData();
                }
        
                return "1"; // 重新激活。
            }
            return "0"; // 需要新开页签
		}
		
        // 从左侧菜单栏增加页签调用
        // $this   : 调用者(一般为 <a 标签。 
		// refresh : 是否刷新页面。
        function addTab($this, refresh){
            
			$(".jericho_tab").show();
		    $("#mainFrame").hide();

		    // 如果已经打开，则需要激活即可。
		    // data-descp:为页签名字。
		    var activateFlag;
		    activateFlag = activateOpenTab($this.attr('data-descp'));
		    if(activateFlag=="1"){
               // 重新激活，则无需继续。
	           return false;
			}
			
			$.fn.jerichoTab.addTab({
                tabFirer: $this,
                title: $this.attr('data-descp'),
                closeable: true,
                data: {
                    dataType: 'iframe',
                    dataLink: $this.attr('data-href')
                }
            }).loadData(refresh);
			return false;
		}
		
		// 从页面中增加页签调用
		// $this   : 调用者(一般为 <a 标签。 
		// name    ：希望打开的页面的名称，注意要在 mapHref变量中已存在。
		// refresh : 是否刷新页面。
		// urlparam: 无参数时，不传。有参数时，传 "dfg=233" 或 "dfg=233&id=099"
    	function addTabFmPage($this,name,refresh,urlparam){

        	$(".jericho_tab").show();
		    $("#mainFrame").hide();

		    // 获取页签链接的参数
		    var parm;
		    if(urlparam == undefined|| urlparam == null || urlparam == "" )
		    {  parm = "";
			}
		    else{
		       parm = "?" + urlparam;
			}
		    // 判断是否需要关闭已存在的页签。
		    var needReload = false;
		    if(parm!=""){
		    	needReload = true;
		    }
		    // 如果已经打开，则激活或者重新加载数据。
		    var activateFlag;
		    activateFlag =   activateOpenTab(name,needReload,mapHref.get(name) + parm);
		    if(activateFlag=="1"){
                   return false;
	        }
		        
			$.fn.jerichoTab.addTab({
                tabFirer: $this,
                title:name,
                closeable: true,
                data: {
                    dataType: 'iframe',
                    dataLink: mapHref.get(name) + parm
                }
            }).loadData(refresh);
			return false;
		}
        
        function openCloseClickCallBack(b){
			$.fn.jerichoTab.resize();
		} 

		$("#closePrj").click(function(){//关闭工程按钮绑定点击事件
			if($("#curProjectName").text() == ""){
				return;
			}
			if(prjStatus == "3"){
				alertx($("#curProjectName").text() + "已经关闭");
				return;
			}
			var r = confirm("确定关闭当前工程（"+$("#curProjectName").text()+"）吗？");
			if(r == true){
				$.post("${ctx}/projectinfo/ccPrjInfo/closePrj", function(data) {
					var flag = data;
					if(flag == 1){
						$("#curProjectName").text("");
						$.fn.jerichoTab.closeAllTab();//关闭所有页签
						showTip("关闭成功"); //后期改造成J-box提示
					}else{
						return;
					}
				});
			}
		});
    </script>

    <!-- END: PAGE SCRIPTS -->

</body>

</html>
