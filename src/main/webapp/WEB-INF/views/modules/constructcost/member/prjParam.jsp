<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/WEB-INF/views/include/commonCss.jsp"%>
<!--(步骤3)本页面专用的式样开始 -->
</head>
<body>
	<div style="margin-top: 20px;">
		<form:form id="inputForm" modelAttribute="ccPrjParameterModel" class="form-horizontal">
			<form:hidden path="id" value="${id}"/><!--keyword 为 “decimalPlace” 数据的ID -->
			<form:hidden path="idRealcost" value="${idRealcost}"/><!--keyword 为 “assingRole4Realcost” 数据的ID -->
			<form:hidden path="idIncome" value="${idIncome}"/><!--keyword 为 “assingRole4Income” 数据的ID -->
			<form:hidden path="idPayment" value="${idPayment}"/><!--keyword 为 “assingRole4Payment” 数据的ID -->
			<div class="form-group">
				<div style="float: left;width: 98%;padding-left: 1%;">
			    <label >期次分包结算完结后，提交时发送微信消息给:</label>
				<form:select path="value1" class="input-xxlarge" cssStyle="width:15%">   
					<form:option value=""></form:option>
					<form:options items="${roleList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
				<form:select path="value2" class="input-xxlarge" cssStyle="width:15%">   
					<form:option value=""/>  
					<form:options items="${roleList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
				<form:select path="value3" class="input-xxlarge" cssStyle="width:15%">   
					<form:option value=""/>  
					<form:options items="${roleList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
				<form:select path="value4" class="input-xxlarge" cssStyle="width:15%">   
					<form:option value=""/>  
					<form:options items="${roleList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
				</div>
			</div>
			<div class="form-group">
				<div style="float: left;width: 98%;padding-left: 1%;">
			    <label >期次收入计量完结后，提交时发送微信消息给:</label>
				<form:select path="value5" class="input-xxlarge" cssStyle="width:15%">   
					<form:option value=""/>  
					<form:options items="${roleList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
				<form:select path="value6" class="input-xxlarge" cssStyle="width:15%">   
					<form:option value=""/>  
					<form:options items="${roleList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
				<form:select path="value7" class="input-xxlarge" cssStyle="width:15%">   
					<form:option value=""/>  
					<form:options items="${roleList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
				<form:select path="value8" class="input-xxlarge" cssStyle="width:15%">   
					<form:option value=""/>  
					<form:options items="${roleList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
				</div>
			</div>
			<div class="form-group">
				<div style="float: left;width: 98%;padding-left: 1%;">
			    <label >期次收付录入完结后，提交时发送微信消息给:</label>
				<form:select path="value9" class="input-xxlarge" cssStyle="width:15%">   
					<form:option value=""/>  
					<form:options items="${roleList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
				<form:select path="value10" class="input-xxlarge" cssStyle="width:15%">   
					<form:option value=""/>  
					<form:options items="${roleList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
				<form:select path="value11" class="input-xxlarge" cssStyle="width:15%">   
					<form:option value=""/>  
					<form:options items="${roleList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
				<form:select path="value12" class="input-xxlarge" cssStyle="width:15%">   
					<form:option value=""/>  
					<form:options items="${roleList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
				</div>
			</div>
			
			<div class="form-group">
				<label for="" class="col-sm-2 control-label"><fmt:message key="decimalPlace"/></label>
				<div class="col-sm-10" >
					<form:select path="value13" class="input-xxlarge" cssStyle="width:10%">
							<form:option value=""/>
							<form:option value="0">0位</form:option>
							<form:option value="1">1位</form:option>
							<form:option value="2">2位</form:option>
							<form:option value="3">3位</form:option>
							<form:option value="4">4位</form:option>
							<form:option value="5">5位</form:option>
							<form:option value="6">6位</form:option>
					</form:select> 
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<input id="btnSubmit" class="btn btn-primary" type="submit"
						value="保存" />&nbsp;
					 <input id="btnCancel" class="btn"
						type="button" value="返 回" onclick="history.go(-1)" />
				</div>
			</div>
		</form:form>
	</div>
</body>
<%@include file="/WEB-INF/views/include/commonJs.jsp"%>
<!--(步骤2)页面逻辑 开始 -->
<%@include
	file="/WEB-INF/views/modules/constructcost/member/prjParamJs.jsp"%>
</html>