<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>应用管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			 $("#inputForm").validate({
				rules: {
					listMaxCount:{
						digits:true
					},
					seq:{
						digits:true
					}
					/* isEffect:{
					  required:true
					},
					isNeedLogin:{
				      required:true
					} */
				},
				messages: {
					listMaxCount:{
						 digits: "只能输入整数",
					},
					seq:{
						digits: "只能输入整数",
					}
					/* isEffect:{
						required:"请选择生效与否"
					},
					isNeedLogin:{
						required:"请选择是否登录"
					} */
				}, 
				submitHandler: function(form){
					var seq = $("#inputForm #seq").val();
					if(seq==null||seq=="")
					{
						$("#inputForm #seq").val("0");
					}
					
					loading('正在提交，请稍等...');
					form.submit();
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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/hihunan/hih5app/hiH5app/">应用列表</a></li>
		<li class="active"><a href="${ctx}/hihunan/hih5app/hiH5app/form?id=${hiH5app.id}">应用<shiro:hasPermission name="hihunan:hih5app:hiH5app:edit">${not empty hiH5app.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="hihunan:hih5app:hiH5app:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="hiH5app" action="${ctx}/hihunan/hih5app/hiH5app/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">一级分类：</label>
			<div class="controls">
				<form:select path="type1" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('hi_app_type1')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">二级分类：</label>
			<div class="controls">
				<form:select path="type2" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('hi_app_type2')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">三级分类：</label>
			<div class="controls">
				<form:input path="type3" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">图标：</label>
			<div class="controls">
				<form:hidden id="nameImage" path="icon" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:ckfinder input="nameImage" type="images" uploadPath="/photo" selectMultiple="false" maxWidth="100" maxHeight="100"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">链接路径：</label>
			<div class="controls">
				<form:input path="url" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否有效：</label>
			<div class="controls">
				<form:select path="isEffect" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('hi_is_effect')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否需要登录：</label>
			<div class="controls">
			    <form:select path="isNeedLogin" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('hi_is_effect')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">顺序：</label>
			<div class="controls">
				<form:input path="seq" htmlEscape="false" maxlength="11" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最大显示记录数：</label>
			<div class="controls">
				<form:input path="listMaxCount" htmlEscape="false" maxlength="11" class="required input-xlarge"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否置顶：</label>
			<div class="controls">
				<form:select path="isTop" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('hi_yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开始日期：</label>
			<div class="controls">
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${hiH5app.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结束日期：</label>
			<div class="controls">
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${hiH5app.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">版本号：</label>
			<div class="controls">
				<form:input path="versionNo" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">首页一览展示类型：</label>
			<div class="controls">
				<form:select path="listType" class="required input-xlarge">
					<form:option value="" label=""/>
					<form:options items="${listType}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="hihunan:hih5app:hiH5app:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>