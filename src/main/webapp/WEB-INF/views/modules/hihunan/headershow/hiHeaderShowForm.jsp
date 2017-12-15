<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>头部轮播管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
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
		<li><a href="${ctx}/hihunan/headershow/hiHeaderShow/">头部轮播列表</a></li>
		<li class="active"><a href="${ctx}/hihunan/headershow/hiHeaderShow/form?id=${hiHeaderShow.id}">头部轮播<shiro:hasPermission name="hihunan:headershow:hiHeaderShow:edit">${not empty hiHeaderShow.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="hihunan:headershow:hiHeaderShow:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="hiHeaderShow" action="${ctx}/hihunan/headershow/hiHeaderShow/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<div class="control-group">
			<label class="control-label">题头：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">题头图片：</label>
			<div class="controls">
				<form:hidden id="nameImage" path="titlePhoto" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:ckfinder input="nameImage" type="images" uploadPath="/photo" selectMultiple="false" maxWidth="100" maxHeight="100"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">顺序：</label>
			<div class="controls">
				<form:input path="seq" htmlEscape="false" maxlength="11" class="required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">链接路径：</label>
			<div class="controls">
				<form:input path="url" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否生效：</label>
			<div class="controls">
				<form:select path="isEffect" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('hi_is_effect')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所属栏位类别：</label>
			<div class="controls">
				<form:select path="h5appId" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${H5appList}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="hihunan:headershow:hiHeaderShow:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>