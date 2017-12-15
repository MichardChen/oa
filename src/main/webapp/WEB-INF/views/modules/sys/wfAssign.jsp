<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <%@include file="/WEB-INF/views/include/commonCss.jsp"%>
  <!--(步骤3)本页面专用的式样开始 -->	
</head>
<body>
	<div style="width:100%;">
		<div style="width:95%;margin-top:25px;margin-left:5px;">
			<table id="mainTable"></table>
		</div>
	</div>
	 <!--弹窗  -->
	 <div id="configDailog" class="modal hide fade" style="width:80%;margin-left:-35%;margin-top:40px;background-color:white;height:60%">
		 <div class="modal-header" style="height:50px;">
	        <button type="button" name="btnClose" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
	        <label id="relationcostItemList" style="float: left;">配置流程人员</label>
	     </div>
		 <div class="modal-body" style="width:100%">
		 	<div style="width:100%">
		 		<div style="width:100%;float:left;margin-top: -5px;">
		 		    <table id="wfAssign" style="width:90%;"></table>
		 		</div>
		 	</div>
		 </div>
	 </div>
</body>
<%@include file="/WEB-INF/views/include/commonJs.jsp"%>
<!--(步骤2)页面逻辑 开始 --> 
<%@ include file="/WEB-INF/views/modules/sys/wfAssignJs.jsp"%>
</html>