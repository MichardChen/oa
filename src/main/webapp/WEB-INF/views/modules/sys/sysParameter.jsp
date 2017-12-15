<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<%@include file="/WEB-INF/views/include/commonCss.jsp"%>
</head>
<body>
<!-- 	 <div class="form-horizontal"> -->
<!-- 		<table class="table-form"> -->
<!-- 			<tr> -->
<!-- 				<td class="tit" rowspan="">登录名:</td> -->
<!-- 				<td colspan="" rowspan=""> -->
<!-- 					<input id="loginName" maxlength="50" class="input-medium"/> -->
<!-- 				</td> -->
<!-- 				<td colspan="" rowspan=""> -->
<!-- 					<button id="clearUserToken" class="btn btn-primary">一键清除用户登录信息</button> -->
<!-- 						<input type="button" value="一键清除用户登录信息" id="clearUserToken" class="btn btn-primary"/> -->
<!-- 				</td> -->
<!-- 			</tr> -->
<!-- 		</table> -->
<!-- 	</div> -->
<!--     <div style="margin-left:1%;"> -->
<!--     	<span>注:修改系统参数后会立即生效，请谨慎操作</span> -->
<!-- 	</div> -->
	<div style="width:98%;margin-left:5px;">
		<table id="mainTable"></table>
		<div id="page"></div>
	</div>
	<div id="addCompanyDailog" class="modal hide fade" style="width:80%;margin-left:-45%;margin-top:-2%;background-color:white;height:90%;overflow: auto;">
		<div class="modal-header" style="height:40px;">
			<div id = "hintMsg" style="width:80%">
				<h4 style="margin-top:2px;">
					<span id = "volOrMaterial">
					</span>
				</h4>
			</div>
			<div>
	        	<button type="button" name="btnClose" data-dismiss="modal" aria-hidden="true" class="close" style="margin-top:-2%;">&times;</button>
	        </div>
	     </div>
	     <form:form id="inputForm" modelAttribute="sysParameter" class="form-horizontal">
			<form:hidden path="id"/>
			<div class="control-group">
				<label class="control-label">关键字:</label>
				<div class="controls">
					<form:input path="keyword" htmlEscape="false" maxlength="200" readonly="true" class="required" style="height:28px;"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">参数1:</label>
				<div class="controls">
					<form:input path="value1" htmlEscape="false" maxlength="300" class="required input-xlarge" style="height:28px;"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">参数2:</label>
				<div class="controls">
					<form:input path="value2" htmlEscape="false" maxlength="300" class="input-xlarge" style="height:28px;"/>
					<span class="help-inline"></span> 
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">参数3:</label>
				<div class="controls">
					<form:input path="value3" htmlEscape="false" maxlength="300" class="input-xlarge" style="height:28px;"/>
					<span class="help-inline"></span> 
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">参数4:</label>
				<div class="controls">
					<form:input path="value4" htmlEscape="false" maxlength="300" class="input-xlarge" style="height:28px;"/>
					<span class="help-inline"></span> 
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">参数5:</label>
				<div class="controls">
					<form:input path="value5" htmlEscape="false" maxlength="300" class="input-xlarge" style="height:28px;"/>
					<span class="help-inline"></span> 
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">参数6:</label>
				<div class="controls">
					<form:input path="value6" htmlEscape="false" maxlength="300" class="input-xlarge" style="height:28px;"/>
					<span class="help-inline"></span> 
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">参数7:</label>
				<div class="controls">
					<form:input path="value7" htmlEscape="false" maxlength="300" class="input-xlarge" style="height:28px;"/>
					<span class="help-inline"></span> 
				</div>
			</div>
			
			<div class="control-group">
				<label class="control-label">参数8:</label>
				<div class="controls">
					<form:input path="value8" htmlEscape="false" maxlength="300" class="input-xlarge" style="height:28px;"/>
					<span class="help-inline"></span> 
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">参数9:</label>
				<div class="controls">
					<form:input path="value9" htmlEscape="false" maxlength="300" class="input-xlarge" style="height:28px;"/>
					<span class="help-inline"></span> 
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">参数10:</label>
				<div class="controls">
					<form:input path="value10" htmlEscape="false" maxlength="300" class="input-xlarge" style="height:28px;"/>
					<span class="help-inline"></span> 
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">备注:</label>
				<div class="controls">
					<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="255" class="input-xlarge"/>
				</div>
			</div>
			<div class="form-actions">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
				<input id="btnCancel" class="btn" type="button" value="返 回" onclick="closeDailog()"/>
			</div>
		</form:form>
	</div>
</body>
<%@include file="/WEB-INF/views/include/commonJs.jsp"%>
<!--(步骤2)页面逻辑 开始 -->
<%@include file="/WEB-INF/views/modules/sys/sysParameterJs.jsp"%>
</html>