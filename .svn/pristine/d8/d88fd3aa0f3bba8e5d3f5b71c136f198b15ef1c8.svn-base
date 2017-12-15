<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>应用管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/hihunan/hih5app/hiH5app/">应用列表</a></li>
		<shiro:hasPermission name="hihunan:hih5app:hiH5app:edit"><li><a href="${ctx}/hihunan/hih5app/hiH5app/form">应用添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="hiH5app" action="${ctx}/hihunan/hih5app/hiH5app/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>一级分类：</label>
				<form:select path="type1" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('hi_app_type1')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>二级分类：</label>
				<form:select path="type2" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('hi_app_type2')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>名称</th>
				<th>一级分类</th>
				<th>二级分类</th>
				<th>更新日期</th>
				<shiro:hasPermission name="hihunan:hih5app:hiH5app:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="hiH5app">
			<tr>
				<td><a href="${ctx}/hihunan/hih5app/hiH5app/form?id=${hiH5app.id}">
					${hiH5app.name}
					</a>
				</td>
				<td>
					${fns:getDictLabel(hiH5app.type1, 'hi_app_type1', '')}
				</td>
				<td>
					${fns:getDictLabel(hiH5app.type2, 'hi_app_type2', '')}
				</td>
				<td>
					<fmt:formatDate value="${hiH5app.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="hihunan:hih5app:hiH5app:edit"><td>
    				<a href="${ctx}/hihunan/hih5app/hiH5app/form?id=${hiH5app.id}">修改</a>
					<a href="${ctx}/hihunan/hih5app/hiH5app/delete?id=${hiH5app.id}" onclick="return confirmx('确认要删除该应用吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>