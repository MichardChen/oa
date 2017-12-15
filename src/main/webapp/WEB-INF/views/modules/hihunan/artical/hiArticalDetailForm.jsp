<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>文章管理</title>
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
	
	<!-- 富文本编辑控件-->
	<script>window.UEDITOR_HOME_URL = "${ctxStatic}/hihunan/ueditor/"</script>
	<script type="text/javascript" src="${ctxStatic}/hihunan/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" src="${ctxStatic}/hihunan/ueditor/ueditor.all.js"></script>
	<script type="text/javascript" src="${ctxStatic}/hihunan/ueditor/contentblocks.js"></script>
	<script type="text/javascript" src="${ctxStatic}/hihunan/artdialog/artDialog.js?skin=blue"></script>
	<script type="text/javascript" src="${ctxStatic}/hihunan/artdialog/plugins/iframeTools.js"></script>


	<script type="text/javascript" src="${ctxStatic}/hihunan/jquery.pictable/jquery.reveal.js"></script>
	<script type="text/javascript" src="${ctxStatic}/hihunan/jquery.pictable/jquery.pictable.js"></script>
	
	
	<%@ include file="/WEB-INF/views/modules/hihunan/artical/hiArticalDetailFormJs.jsp"%>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/hihunan/artical/hiArticalDetail/">文章列表</a></li>
		<li class="active"><a href="${ctx}/hihunan/artical/hiArticalDetail/form?id=${hiArticalDetail.id}">文章<shiro:hasPermission name="hihunan:artical:hiArticalDetail:edit">${not empty hiArticalDetail.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="hihunan:artical:hiArticalDetail:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="hiArticalDetail" action="${ctx}/hihunan/artical/hiArticalDetail/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">文件类型：</label>
			<div class="controls">
				<form:select path="fileType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('hi_file_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">文本：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="4" maxlength="2048" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">文件路径：</label>
			<div class="controls">
				<form:input path="fileUrl" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">顺序：</label>
			<div class="controls">
				<form:input path="seq" htmlEscape="false" maxlength="11" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所属文章：</label>
			<div class="controls">
			<form:select path="articalId" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${HiArticalList}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="hihunan:artical:hiArticalDetail:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>