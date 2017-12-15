<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <%@include file="/WEB-INF/views/include/commonCss.jsp"%>
  <!--(步骤3)本页面专用的式样开始 -->	
</head>
<body>
		
		<div style="width:100%;height: 35px;">
			<div style="text-align:center;float: right;margin-right: 4%;">
	 			<a type="button" id = "deleteSelectRow" class="fa fa-trash fa-lg"  title="删除选中行"></a>
	 			<div style="font-size:10px;">删除</div>
	 		</div>
	 	</div>
		<div style="border-radius:10px;width: 100%;height:480px;border:2px solid #cccccc;margin-top: 3px;">
		 	<div style="float:left;width:19%;padding-left: 1%;">
		 		<table id="projectTable" style="text-align: left"></table>
		 	</div>
		 	<div style="float:left;width:39%;padding-left: 1%;" >
			 	<table id="prjUserTable" style="text-align: center"></table>
		 	</div>
		 	<div style="float:left;width:36%;padding-left: 1%;" >
		 		<table id="prjUserMapTable" style="text-align: center"></table>
		 		<div style="margin-top: 5px;width:100%;text-align: center">
			 	<button type="button" id="btnSave"  class="BUTTON"><fmt:message key="ccPrjTgt.save"/></button>
		 		</div>
		 	</div> 
		</div>
	
</body>
<%@include file="/WEB-INF/views/include/commonJs.jsp"%>
<!--(步骤2)页面逻辑 开始 --> 
<%@ include file="/WEB-INF/views/modules/constructcost/member/prjmemberJs.jsp"%>
</html>