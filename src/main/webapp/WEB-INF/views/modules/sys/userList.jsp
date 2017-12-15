<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<style>
	.pagination ul>li>a, .pagination ul>li>span {
	    float: left;
	    padding: 4px 12px;
	    line-height: 20px;
	    text-decoration: none;
	    background-color: #e8e1e1;
	    border-left-width: 0;
	    color: #00a1ff;
	    border-left:5px solid #fff;
   }
	
	</style>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	
	    var isAdmin = '${isAdmin}';
	    
		//2016/5/16添加，巡检系统弹框返回按钮调用
		function closeDailog(){
			$("[name=btnClose]")[0].click();
		}
	
		function addUser(){
			$("#mSpan").hide();
			$("#volOrMaterial").text("添加用户");
			$('label.error[for]').remove();
			$("#id").val("");
			$("#member").select2('val','');
			
			if( isAdmin == '1'){  // 2016-7-13 管理员
				$("#inputForm #companyId").val("");
				$("#inputForm #companyName").val("");
				$("#inputForm #officeId").val("");
				$("#inputForm #officeName").val("");
			}
			else{  // 2016-7-13  非管理员
				$("#inputForm #companyId").val("${user.company.id}");
				$("#inputForm #companyName").val("${user.company.name}");
				$("#inputForm #officeId").val("${user.office.id}");
				$("#inputForm #officeName").val("${user.office.name}");
			}
			
			$("#no").val("");
			$("#inputForm #name").val("");
			$("#inputForm #loginName").val("");
			$("#oldLoginName").val("");
			$("#oldMobile").val(""); // 2016-7-5 增加手机号重复的判断。
			$("#email").val("");
			$("#phone").val("");
			$("#mobile").val("");
			$("#qq").val("");
			$("#wechatOpenId").val("否"); // 默认是没有绑定微信公众号。
			$("#loginFlag").select2('val','');
			$("#userType").select2('val','');
			$('input:checkbox').each(function(){
				this.checked = false;
			})
			// 默认是绑定到微信企业号。
			document.getElementsByName("addToWxUserId")[0].checked=true;
			
			$("#remarks").val("");
			
			$("#wxUserId").val('');
			// 清空密码框
			$("#inputForm #newPassword").val('');
			$("#inputForm #confirmNewPassword").val('');
		};
		
		function updateUser(id){
			$("#volOrMaterial").text("修改用户");
			$('label.error[for]').remove();
			$("#id").val('');
			$("#member").select2('val','');
			$("#inputForm #companyId").val('');
			$("#inputForm #companyName").val('');
			$("#inputForm #officeId").val('');
			$("#inputForm #officeName").val('');
			$("#no").val('');
			$("#inputForm #name").val('');
			$("#inputForm #loginName").val('');
			$("#oldLoginName").val('');
			$("#oldMobile").val(''); // 2016-7-5 增加手机号重复的判断。
			
			$("#email").val('');
			$("#phone").val('');
			$("#mobile").val('');
			$("#wxUserId").val('');
			$("#oldWxUserId").val('');
			$("#inputForm #newPassword").val('');
			$("#inputForm #confirmNewPassword").val('');
			$("#wechatOpenId").html("否");
			$("#loginFlag").select2('val','');
			$("#userType").select2('val','');
			$('input:checkbox').each(function(){
				this.checked = false;
			})
			// 根据绑定情况，设置CheckBox.
			document.getElementsByName("addToWxUserId")[0].checked = false;
			$("#remarks").val('');
			$("#newPassword").removeClass('required');
			$("#inputForm #loginName").addClass("valid");
			$("#npSpan").css("display","none");
			$("#cnpSpan").css("display","none");
			$("#mSpan").show();
			$("#mmSpan").show();
// 			var roleList = document.getElementById('roleListDiv');
// 			roleList.innerHTML = "";
			$.ajax({
				async:true,
				type:"post",
				data:{'id':id},
				url: ctx + "/sys/user/updateUser",
				success:function(data){
					var con = eval('(' + data + ')');
// 					var roleArray = con.rows;
// 					var roleHtml = "";
// 					for(var i = 0;i < roleArray.length;i++){
// 						var num = parseInt(i) + 1;
// 						var role = roleArray[i];
// 						roleHtml += '<span>' + 
// 									 	'<input id="roleIdList' + num + '" name="roleIdList" class="required" type="checkbox" value="' + role.id + '">' + 
// 									 	'<label for="roleIdList' + num + '">' + role.name + '</label>' + 
// 									 '</span>';
// 					}
// 					roleHtml += '<input type="hidden" name="_roleIdList" value="on">' + 
// 								 '<span class="help-inline">' +
// 								 	'<font color="red">*</font>' + 
// 								 '</span>';
// 					roleList.innerHTML = roleHtml;
					var user = con.userdata;
					$("#id").val(user.id);
					$("#member").select2('val',user.member.id);
					$("#inputForm #companyId").val(user.company.id);
					$("#inputForm #companyName").val(user.company.name);
					$("#inputForm #officeId").val(user.office.id);
					$("#inputForm #officeName").val(user.office.name);
					$("#no").val(user.no);
					$("#inputForm #name").val(user.name);
					$("#inputForm #loginName").val(user.loginName);
					$("#oldLoginName").val(user.loginName);
					$("#oldMobile").val(user.mobile); // 2016-7-5 增加手机号重复的判断。
					
					$("#email").val(user.email);
					$("#phone").val(user.phone);
					$("#mobile").val(user.mobile);
					
					// 画面控制。
					if(user.wxUserId == null || user.wxUserId == ""){
						$("#wxUserId").val(''); // ''代表没有设置。
					}
					else{
						$("#wxUserId").val(user.wxUserId); // 微信企业号UserId.
						$("#oldWxUserId").val(user.wxUserId);
					}
					// 清空密码框
					$("#inputForm #newPassword").val('');
					$("#inputForm #confirmNewPassword").val('');
					
					$("#qq").val(user.qq);
					if(user.wechatOpenId !=null && user.wechatOpenId !=""){
						$("#wechatOpenId").html("是"); // 已绑定(微信公众号)
					}else{
						$("#wechatOpenId").html("否"); // 未绑定(微信公众号)
					}
					$("#loginFlag").select2('val',user.loginFlag);
					$("#userType").select2('val',user.userType);
					
					$('input:checkbox').each(function(){
						this.checked = false;
					})
					var checkbox = $('input:checkbox');
					for(var i = 0;i < user.roleIdList.length;i++){
						for(var j = 0;j < checkbox.length;j++){
							if(checkbox[j].value == user.roleIdList[i]){
								checkbox[j].checked = true;
							}
						}
					}
					// 根据绑定情况，设置CheckBox.
					if(user.wxUserId==null || user.wxUserId==""){
						document.getElementsByName("addToWxUserId")[0].checked = false;
					}else{
						document.getElementsByName("addToWxUserId")[0].checked = true;
					}
					
					$("#remarks").val(user.remarks);
					$("#newPassword").removeClass('required');
					$("#inputForm #loginName").addClass("valid");
					$("#npSpan").css("display","none");
					$("#cnpSpan").css("display","none");
					$("#mSpan").show();
					$("#mmSpan").show();
				}
			});
		};
		
// 		function officeTreeselectCallBack(v, h, f,containerId){
// 			if (v=="ok"){ // 选择【确定】按钮
// 				if(containerId.trim() != "#inputForm"){
// 					return;
// 				}
// 				var roleList = document.getElementById('roleListDiv');
// 				roleList.innerHTML = "";
// 				var officeId = $("#inputForm #officeId").val();
// 				$.ajax({
// 					async:false,
// 					type:"post",
// 					data:{'officeId':officeId},
// 					url: ctx + "/sys/user/findRoleList",
// 					success:function(rtnData){
// 						var data = eval('(' + rtnData + ')');
// 						if(data.result == 0){
// 							var roleArray = data.rows;
// 							var roleHtml = "";
// 							for(var i = 0;i < roleArray.length;i++){
// 								var num = parseInt(i) + 1;
// 								var role = roleArray[i];
// 								roleHtml += '<span>' + 
// 											 	'<input id="roleIdList' + num + '" name="roleIdList" class="required" type="checkbox" value="' + role.id + '">' + 
// 											 	'<label for="roleIdList' + num + '">' + role.name + '</label>' + 
// 											 '</span>';
// 							}
// 							roleHtml += '<input type="hidden" name="_roleIdList" value="on">' + 
// 										 '<span class="help-inline">' +
// 										 	'<font color="red">*</font>' + 
// 										 '</span>';
// 							roleList.innerHTML = roleHtml;
// 						}else{
// 							alertx(data.msg);
// 							return;
// 						}
// 					},
// 					error:function(x,h,r){
// 						alertx(x);
// 					}
// 				})
// 			}
// 		}
		
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出用户数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/sys/user/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			$("#btnImport").click(function(){
				$.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true}, 
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
			});
			
			// 绑定事件
			$("#send").click(function(){
				
				$.ajax({
					async:false,
					type:"post",
					data:{},
					url: ctx + "/sys/user/sendQyMsg",
					success:function(data){
							var tn=data;
								showTip("同步成功");
							
					}
				});	
				
			});
			
			$("#no").focus();
			$("#inputForm").validate({
				rules: {
					//loginName: {remote: "${ctx}/sys/user/checkLoginName?oldLoginName=" + encodeURIComponent()}
					loginName:{
						remote:{
							type:"POST",
							url:"${ctx}/sys/user/checkLoginName",
							data:{
								oldLoginName:function(){return $("#oldLoginName").val();}
							}
						}
					},
					// 2016-7-5 增加手机号重复的判断。
					mobile:{
						remote:{
							type:"POST",
							url:"${ctx}/sys/user/checkMobile",
							data:{
								oldMobile:function(){return $("#oldMobile").val();}
							}
						}
					}
				},
				messages: {
					loginName:{remote:"用户登录名已经存在"},
					mobile: {remote: "手机号已注册"},
					confirmNewPassword: {equalTo: "输入与上面相同的密码"}
				},
				submitHandler: function(form){
					var wxUserId = $("#wxUserId").val();
					var oldWxUserId = $("#oldWxUserId").val();
					if(wxUserId == '' || wxUserId == oldWxUserId){
						loading('正在提交，请稍等...');
						form.submit();
					}else{
						$.ajax({
							async:false,
							type:"post",
							data:{'wxUserId':wxUserId
								},
							url: ctx + "/sys/user/checkWxUserId",
							success:function(rtnData){
								var data = eval('(' + rtnData + ')');
								if(data.result == 0){
									top.$.jBox.confirm(data.msg,"系统提示",function(v,h,f){
										if(v=="ok"){
											loading('正在提交，请稍等...');
											form.submit();
										}
									},{buttonsFocus:1});
									top.$('.jbox-body .jbox-icon').css('top','55px');
								}else{
									loading('正在提交，请稍等...');
									form.submit();
								}
							},
							error:function(x,h,r){
								alertx(x);
							}
						})
					}
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
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/sys/user/list");
			$("#searchForm").submit();
	    	return false;
	    };
	</script>
</head>
<body>
	<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/sys/user/import" method="post" enctype="multipart/form-data"
			class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="导 入 "/>
			<a href="${ctx}/sys/user/import/template">下载模板</a>
		</form>
	</div>
	<!-- <ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/user/list">用户列表</a></li>
		<shiro:hasPermission name="sys:user:edit"><li><a href="${ctx}/sys/user/form">用户添加</a></li></shiro:hasPermission>
	</ul>  -->
	<!-- 页面按钮区域 -->
	<form:form id="searchForm" modelAttribute="user" action="${ctx}/sys/user/list" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li ><label >归属公司：</label><sys:treeselect id="company" name="company.id" value="${user.company.id}" labelName="company.name" labelValue="${user.company.name}" 
				title="公司" url="/sys/office/treeData?type=1" cssClass="input-small" allowClear="true" containerId="searchForm" /></li>
			<li><label>登录名：</label><form:input path="loginName" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li class="clearfix"></li>
			<li><label>归属部门：</label><sys:treeselect id="office" name="office.id" value="${user.office.id}" labelName="office.name" labelValue="${user.office.name}" 
				title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true" containerId="searchForm"/></li>
			<li><label>姓&nbsp;&nbsp;&nbsp;名：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li class="btns">
			    &nbsp;&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>&nbsp;&nbsp;
				<input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
				<input id="btnImport" class="btn btn-primary" type="hidden" value="导入"/>
				<c:if test="${modifyUserSign eq '1'}"> <!--系统管理员或部门负责人时,允许编辑 -->
				&nbsp;&nbsp;
					<input type="button" value="添加用户" class="btn btn-primary" href="#addUserDailog" data-toggle='modal' onclick="addUser()"/>
				<!--<button class="btn btn-primary" href="#addUserDailog" data-toggle='modal' onclick="addUser()">添加用户</button>-->
				</c:if>
				<input type="hidden" id="send" class="btn btn-primary" value="发送消息"/>
				</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th style="text-align: center;">归属公司</th><th style="text-align: center;">归属部门</th><th class="sort-column login_name">登录名</th><th class="sort-column name">姓名</th><th style="text-align: center;">企业号对应账号</th><th style="text-align: center;">企业号账号状态</th><th style="text-align: center;">手机</th><%--<th>角色</th> --%><c:if test="${modifyUserSign eq '1'}"><shiro:hasPermission name="sys:user:edit"><th style="text-align: center;">操作</th></shiro:hasPermission></c:if></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="user">
			<tr>
				<td  >${user.company.name}</td>
				<td >${user.office.name}</td>
				<td >${user.loginName}</td>
				<td >${user.name}</td>
				<td style="text-align: right;">${user.wxUserId}</td>
				<td style="text-align: center;">${user.wxUserStatusDesc}</td>
				<td style="text-align: right;">${user.mobile}</td><%--
				<td>${user.roleNames}</td> --%>
				<c:if test="${modifyUserSign eq '1'}"> <!--系统管理员或部门负责人时,允许编辑  -->
				<shiro:hasPermission name="sys:user:edit"><td>
				    <a id="${user.id}" href="#addUserDailog"data-toggle='modal'onclick="updateUser('${user.id}')">修改</a>
					<a href="${ctx}/sys/user/delete?id=${user.id}" onclick="return confirmx('确认要删除该用户吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
				</c:if>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination"  style="border: 5px solid #fff;color: white;">${page}</div>
	<!-- 添加用户 弹出框部分 -->
	<div id="addUserDailog" class="modal hide fade" style="width:95%;margin-left: -50%;margin-top:5px;background-color:white;height:85%;">
		<div class="modal-header" style="height:40px;">
			<div id = "hintMsg" style="width:80%">
				<h4 style="margin-top:2px;">
					<span id = "volOrMaterial">
						添加用户
					</span>
				</h4>
			</div>
			<div style="margin-top:-35px;">
	        	<button type="button" name="btnClose" data-dismiss="modal" aria-hidden="true" class="close" style="margin-top:3%;">&times;</button>
	        </div>
	     </div>
	     <div style="height:90%;overflow:auto;margin-left:10px;">
			 <form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>
				<div class="control-group" style="display:none;">
					<label class="control-label">头像:</label>
					<div class="controls">
						<form:hidden id="nameImage" path="photo" htmlEscape="false" maxlength="255" class="input-xlarge"/>
						<sys:ckfinder input="nameImage" type="images" uploadPath="/photo" selectMultiple="false" maxWidth="100" maxHeight="100"/>
					</div>
				</div>
				<div class="control-group" style="display:none;">
					<label class="control-label">归属会员:</label>
					<div class="controls">
		               <form:select path="member" class="input-xlarge">
							<form:option value="" label="请选择"/>
							<form:options items="${memberList}" itemLabel="memberName" itemValue="id" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">归属公司:</label>
					<div class="controls">
		                <sys:treeselect id="company" name="company.id" value="${user.company.id}" labelName="company.name" labelValue="${user.company.name}"
							title="公司" url="/sys/office/treeData?type=1" cssClass="required" dataMsgRequired="必填信息" containerId="inputForm" disabled="${isAdmin eq '1' ? '':'disabled'}"/>
						<span id="cnpSpan" class="help-inline"><font color="red">*</font></span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">归属部门:</label>
					<div class="controls">
		                <sys:treeselect id="office" name="office.id" value="${user.office.id}" labelName="office.name" labelValue="${user.office.name}"
							title="部门" url="/sys/office/treeData?type=2" cssClass="required" dataMsgRequired="必填信息" notAllowSelectParent="true" containerId="inputForm" disabled="${isAdmin eq '1' ? '':'disabled'}"/>
						<span id="cnpSpan" class="help-inline"><font color="red">*</font></span>
					</div>
				</div>
	 			<div class="control-group">
	 				<label class="control-label">工号:</label>
	 				<div class="controls">
	 					<form:input path="no" htmlEscape="false" maxlength="50"/>
	 					<span class="help-inline">部门(公司)内部使用的识别信息</span>
	 				</div>
	 			</div>
				<div class="control-group">
					<label class="control-label">姓名:</label>
					<div class="controls">
						<form:input path="name" htmlEscape="false" maxlength="50" class="required"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">手机号:</label>
					<div class="controls">
					    <input id="oldMobile" name="oldMobile" type="hidden"/>
						<form:input path="mobile" htmlEscape="false" maxlength="30" class="required userName"/>
						<span class="help-inline"><font color="red">*</font> </span>
						<span id="mmSpan" class="help-inline" >登录用,系统内必须唯一</span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">登录名:</label>
					<div class="controls">
						<input id="oldLoginName" name="oldLoginName" type="hidden"/>
						<form:input path="loginName" htmlEscape="false" maxlength="50" class="required userName"/>
						<span class="help-inline"><font color="red">*</font></span>
						<span id="mmSpan" class="help-inline" >登录用,系统内必须唯一</span>
					</div>
				</div>
				<div class="control-group">
					<label id="lblWxqy" class="control-label">绑定企业号成员账号:</label>
					<div class="controls">
						<form:checkbox path="addToWxUserId" value="1"/>  <!-- 选中时，传值1 -->
						<input id="oldWxUserId" name="oldWxUserId" type="hidden"/>
					    <form:input path="wxUserId" class="input-medium"/>
						<span class="help-inline">推荐使用手机号。勾选后自动同步到企业号通讯录。此账号在企业号通讯录中唯一。<font color="red">*</font></span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">密码:</label>
					<div class="controls">
						<input id="newPassword" name="newPassword" type="password" value="" maxlength="50" minlength="3" class="required"/>
	<%-- 					<input id="newPassword" name="newPassword" type="password" value="" maxlength="50" minlength="3" class="${empty user.id?'required':''}"/> --%>
	<%-- 					<c:if test="${empty user.id}"><span class="help-inline"><font color="red">*</font> </span></c:if> --%>
	<%-- 					<c:if test="${not empty user.id}"><span class="help-inline">若不修改密码，请留空。</span></c:if> --%>
						<span id="npSpan" class="help-inline" ><font color="red">*</font> </span>
						<span id="mSpan" class="help-inline" >若不修改密码，请留空。</span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">确认密码:</label>
					<div class="controls">
						<input id="confirmNewPassword" name="confirmNewPassword" type="password" value="" maxlength="50" minlength="3" equalTo="#newPassword"/>
	<%-- 					<c:if test="${empty user.id}"><span class="help-inline"><font color="red">*</font> </span></c:if> --%>
						<span id="cnpSpan" class="help-inline"><font color="red">*</font></span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">邮箱:</label>
					<div class="controls">
						<form:input path="email" htmlEscape="false" maxlength="100" class="email"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">电话:</label>
					<div class="controls">
						<form:input path="phone" htmlEscape="false" maxlength="100"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">QQ:</label>
					<div class="controls">
						<form:input path="qq" htmlEscape="false" maxlength="100"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">已绑定微信公众号:</label>
					<div class="controls">
						<label id="wechatOpenId" class="control-label" style="text-align:left;">否</label>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">是否允许登录:</label>
					<div class="controls">
						<form:select path="loginFlag">
							<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
						<span class="help-inline"><font color="red">*</font> “是”代表此账号允许登录，“否”则表示此账号不允许登录</span>
					</div>
				</div>
				<div class="control-group" style="display:none;">
					<label class="control-label">用户类型:</label>
					<div class="controls">
						<form:select path="userType" class="input-xlarge">
							<form:option value="" label="请选择"/>
							<form:options items="${fns:getDictList('sys_user_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
<!-- 				<div class="control-group"> -->
<!-- 					<label class="control-label">身份:</label> -->
<!-- 					<div class="controls"> -->
<%-- 						<form:select path="nsPosition" class="input-medium"> --%>
<%-- 							<form:options items="${fns:getDictList('ns_position')}" itemLabel="label" itemValue="value" htmlEscape="false"/> --%>
<%-- 						</form:select> --%>
<!-- 						<span class="help-inline">用户身份标识</span> -->
<!-- 					</div> -->
<!-- 				</div> -->
				<div class="control-group">
					<label class="control-label">用户角色:</label>
					<div class="controls" id="roleListDiv">
						<form:checkboxes path="roleIdList" items="${allRoles}" itemLabel="name" itemValue="id" htmlEscape="false" class="required"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">备注:</label>
					<div class="controls">
						<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"/>
					</div>
				</div>
	<%-- 			<c:if test="${not empty user.id}"> --%>
	<!-- 				<div class="control-group"> -->
	<!-- 					<label class="control-label">创建时间:</label> -->
	<!-- 					<div class="controls"> -->
	<%-- 						<label class="lbl"><fmt:formatDate value="${user.createDate}" type="both" dateStyle="full"/></label> --%>
	<!-- 					</div> -->
	<!-- 				</div> -->
	<!-- 				<div class="control-group"> -->
	<!-- 					<label class="control-label">最后登陆:</label> -->
	<!-- 					<div class="controls"> -->
	<%-- 						<label class="lbl">IP: ${user.loginIp}&nbsp;&nbsp;&nbsp;&nbsp;时间：<fmt:formatDate value="${user.loginDate}" type="both" dateStyle="full"/></label> --%>
	<!-- 					</div> -->
	<!-- 				</div> -->
	<%-- 			</c:if> --%>
				<div class="form-actions">
					<shiro:hasPermission name="sys:user:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="closeDailog()" style="border: 1px solid #969696;"/>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>