<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>历史任务</title>
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
		$(document).ready(function() {
			
		});
		function page(n,s){
        	location = '${ctx}/act/task/historic/?pageNo='+n+'&pageSize='+s;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/act/task/todo/">待办任务</a></li>
		<li><a href="${ctx}/act/task/completeList/">已办任务</a></li>
		<li class="active"><a href="${ctx}/act/task/historic/">历史任务</a></li>
		<li><a href="${ctx}/act/task/process/">新建任务</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="act" action="${ctx}/act/task/historicList/" method="get" class="breadcrumb form-search" style="background-color: #ffffff;">
		<div>
			<label>流程名称：&nbsp;</label>
			<form:select path="procDefKey" class="input-medium">
				<form:option value="" label="全部流程"/>
				<form:options items="${fns:getDictList('act_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
			<label>完成时间：</label>
			<input id="beginDate"  name="beginDate"  type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" style="width:163px;"
				value="<fmt:formatDate value="${act.beginDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
				　--　
			<input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" style="width:163px;"
				value="<fmt:formatDate value="${act.endDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
			&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
		</div>
		
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed" style=" margin-left: 5px;" >
		<thead>
			<tr>
				<th style="text-align: center;">标题</th>
				<th style="text-align: center;">当前环节</th><%--
				<th style="text-align: center;">任务内容</th> --%>
				<th style="text-align: center;">流程名称</th>
				<th style="text-align: center;">发起人</th>
<!-- 				<th style="text-align: center;">流程版本</th> -->
				<th style="text-align: center;">完成时间</th>
<!-- 				<th style="text-align: center;">操作</th> -->
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="act">
				<c:set var="task" value="${act.histTask}" />
				<c:set var="vars" value="${act.vars}" />
				<c:set var="procDef" value="${act.procDef}" /><%--
				<c:set var="procExecUrl" value="${act.procExecUrl}" /> --%>
				<c:set var="status" value="${act.status}" />
				<tr>
					<td>
<%-- 						<a href="${ctx}/act/task/form?taskId=${task.id}&taskName=${fns:urlEncode(task.name)}&taskDefKey=${task.taskDefinitionKey}&procInsId=${task.processInstanceId}&procDefId=${task.processDefinitionId}&status=${status}">${fns:abbr(not empty vars.map.title ? vars.map.title : task.id, 60)}</a> --%>
						${fns:abbr(not empty vars.map.title ? vars.map.title : task.id, 60)}
					</td>
					<td>
						${task.name}
<%-- 						<a target="_blank" href="${pageContext.request.contextPath}/act/rest/diagram-viewer?processDefinitionId=${task.processDefinitionId}&processInstanceId=${task.processInstanceId}">${task.name}</a> --%>
						<%--<a target="_blank" href="${ctx}/act/task/trace/photo/${task.processDefinitionId}/${task.executionId}">${task.name}</a>
						<a target="_blank" href="${ctx}/act/task/trace/info/${task.processInstanceId}">${task.name}</a> --%>
					</td><%--
					<td>${task.description}</td> --%>
					<td>${procDef.name}</td>
					<td>${act.initiatorName}</td>
<%-- 					<td><b title='流程版本号'>V: ${procDef.version}</b></td> --%>
					<td><fmt:formatDate value="${task.endTime}" type="both"/></td>
<!-- 					<td> -->
<%-- 						<a href="${ctx}/act/task/form?taskId=${task.id}&taskName=${fns:urlEncode(task.name)}&taskDefKey=${task.taskDefinitionKey}&procInsId=${task.processInstanceId}&procDefId=${task.processDefinitionId}&status=${status}">详情</a> --%>
<!-- 					</td> -->
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination" style="border: 5px solid #fff;color: white;">${page}</div>
</body>
</html>
