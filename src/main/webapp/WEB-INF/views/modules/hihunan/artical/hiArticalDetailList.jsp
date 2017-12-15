<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>文章管理</title>
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
		<li class="active"><a href="${ctx}/hihunan/artical/hiArticalDetail/">文章列表</a></li>
		<shiro:hasPermission name="hihunan:artical:hiArticalDetail:edit"><li><a href="${ctx}/hihunan/artical/hiArticalDetail/form">文章添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="hiArticalDetail" action="${ctx}/hihunan/artical/hiArticalDetail/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>文件类型：</label>
				<form:select path="fileType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('hi_file_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="20" class="input-medium"/>
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
				<th>更新日期</th>
				<shiro:hasPermission name="hihunan:artical:hiArticalDetail:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="hiArticalDetail">
			<tr>
				<td><a href="${ctx}/hihunan/artical/hiArticalDetail/form?id=${hiArticalDetail.id}">
					${hiArticalDetail.name}
				</a></td>
				<td>
					<fmt:formatDate value="${hiArticalDetail.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="hihunan:artical:hiArticalDetail:edit"><td>
    				<a href="${ctx}/hihunan/artical/hiArticalDetail/form?id=${hiArticalDetail.id}">修改</a>
					<a href="${ctx}/hihunan/artical/hiArticalDetail/delete?id=${hiArticalDetail.id}" onclick="return confirmx('确认要删除该文章吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>