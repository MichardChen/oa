<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>头部轮播管理</title>
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
		<li class="active"><a href="${ctx}/hihunan/headershow/hiHeaderShow/">头部轮播列表</a></li>
		<shiro:hasPermission name="hihunan:headershow:hiHeaderShow:edit"><li><a href="${ctx}/hihunan/headershow/hiHeaderShow/form">头部轮播添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="hiHeaderShow" action="${ctx}/hihunan/headershow/hiHeaderShow/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>题头：</label>
				<form:input path="title" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>题头</th>
				<th>更新日期</th>
				<shiro:hasPermission name="hihunan:headershow:hiHeaderShow:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="hiHeaderShow">
			<tr>
				<td><a href="${ctx}/hihunan/headershow/hiHeaderShow/form?id=${hiHeaderShow.id}">
					${hiHeaderShow.title}
				</a></td>
				<td>
					<fmt:formatDate value="${hiHeaderShow.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="hihunan:headershow:hiHeaderShow:edit"><td>
    				<a href="${ctx}/hihunan/headershow/hiHeaderShow/form?id=${hiHeaderShow.id}">修改</a>
					<a href="${ctx}/hihunan/headershow/hiHeaderShow/delete?id=${hiHeaderShow.id}" onclick="return confirmx('确认要删除该头部轮播吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>