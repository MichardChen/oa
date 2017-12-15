<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <%@include file="/WEB-INF/views/include/commonCss.jsp"%>
  <!--(步骤3)本页面专用的式样开始 -->
</head>
<body>
	<div style="margin-top:20px;">
		<form:form id="inputForm" modelAttribute="memberModel" class="form-horizontal">
			<form:hidden path="id"/>
			<div class="form-group">
				<label for="memberName" class="col-sm-2 control-label"><fmt:message key="memberName"/></label>
				<div class="col-sm-10" style="width:25%;">
					<form:input id="memberName" path="memberName" class="form-control" htmlEscape="false" maxlength="100"/>
				</div>
			</div>
			<div class="form-group">
				<label for="areaId" class="col-sm-2 control-label"><fmt:message key="areaId"/></label>
				<div class="col-sm-10" style="width:25%;">
					<form:select path="areaId">
						<form:options id="areaId" items="${areaList}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
				</div>
			</div>
			<div class="form-group">
				<label for="addr" class="col-sm-2 control-label"><fmt:message key="addr"/></label>
				<div class="col-sm-10" style="width:25%;">
					<form:input id="addr" path="addr" class="form-control" htmlEscape="false" maxlength="600"/>
				</div>
			</div>
			<div class="form-group">
				<label for="tel" class="col-sm-2 control-label"><fmt:message key="tel"/></label>
				<div class="col-sm-10" style="width:25%;">
					<form:input id="tel" path="tel" class="form-control" htmlEscape="false" maxlength="600"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label"><fmt:message key="deadline"/></label>
				<div style="margin-top: 8px;">
					<fmt:formatDate value="${memberModel.memberStartDate}" type="date" pattern="yyyy/MM/dd"/>—<fmt:formatDate value="${memberModel.memberValidDate}" type="date" pattern="yyyy/MM/dd"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label"><fmt:message key="concurrentPrjNum"/></label>
				<div style="margin-top: 8px;">
					${memberModel.concurrentPrjNum}
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label"><fmt:message key="memberStatus"/></label>
				<div style="margin-top: 8px;">
					${memberModel.memberStatus}
				</div>
			</div>
			<div class="form-group">
			    <div class="col-sm-offset-2 col-sm-10">
				   <input id="btnSubmit" class="btn btn-primary" type="submit" value="保存"/>&nbsp;
			 	   <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
				</div>
			</div>
		</form:form>
	</div>
</body>
<%@include file="/WEB-INF/views/include/commonJs.jsp"%>
<!--(步骤2)页面逻辑 开始 --> 
<%@include file="/WEB-INF/views/modules/constructcost/member/memberInfoJs.jsp"%>
</html>