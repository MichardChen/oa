<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <%@include file="/WEB-INF/views/include/commonCss.jsp"%>
  <!--(步骤3)本页面专用的式样开始 -->	
</head>
<body>
	<div style="margin-top: -20px;width:100%">
		<form id="searchForm" method="get" >
		   	<input type="hidden" id = "id" name="id" />
		   	<input type="hidden" id = "myProjectId" name="myProjectId" value="${myProjectId}"/>
		</form>
	</div>	
	<div id="title" style="height:20px;border-bottom: 1px solid #cccccc">
		<label><fmt:message key="memberuser.userInfo"/></label>
	</div>
	<div style="width:100%;">
		<div style="width:100%;height:20px;text-align: right;margin-top: 3px;">
			<div style="text-align:center;float:right;margin-left:10px;">
				<a type="button" id="deleteButton" class="fa fa-trash fa-lg" title="<fmt:message key="memberuser.deleteSelectRow"/>"></a>
				<div style="font-size:10px;">删除</div>
			</div>
			<div style="text-align:center;float:right;margin-left:10px;">
				<a type="button" id="addButton" class="fa fa-plus fa-lg" title="<fmt:message key="memberuser.insertOneRow"/>"></a>
				<div style="font-size:10px;">新增</div>
			</div>
			<div style="text-align:center;float:right;margin-left:10px;">
				<a type="button" id="btnSave" class="fa fa-save fa-lg" title="保存"></a>
				<div style="font-size:10px;">保存</div>
			</div>
		</div>
		<div style="width: 100%;height:500px;margin-top:25px;margin-left: 25px;">
			<table id="mainTable" style="width:100%;height:100%;"></table>
		</div>
	</div>
</body>
<%@include file="/WEB-INF/views/include/commonJs.jsp"%>
<!--(步骤2)页面逻辑 开始 --> 
<%@ include file="/WEB-INF/views/modules/constructcost/member/memberListJs.jsp"%>
</html>