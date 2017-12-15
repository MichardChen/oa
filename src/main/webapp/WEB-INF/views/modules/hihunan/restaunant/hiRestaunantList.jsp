<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>餐厅管理</title>
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
		<li class="active"><a href="${ctx}/hihunan/restaunant/hiRestaunant/">餐厅列表</a></li>
		<shiro:hasPermission name="hihunan:restaunant:hiRestaunant:edit"><li><a href="${ctx}/hihunan/restaunant/hiRestaunant/form">餐厅添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="hiRestaunant" action="${ctx}/hihunan/restaunant/hiRestaunant/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>题头：</label>
				<form:input path="title" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>菜系：</label>
				<form:select path="cookingStyle" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('hi_cooking_style')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>商圈：</label>
				<form:select path="tradingArea" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('hi_trading_area')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
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
				<th>地点</th>
				<th>电话</th>
				<th>更新日期</th>
				<shiro:hasPermission name="hihunan:restaunant:hiRestaunant:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="hiRestaunant">
			<tr>
				<td><a href="${ctx}/hihunan/restaunant/hiRestaunant/form?id=${hiRestaunant.id}">
					${hiRestaunant.title}
				</a></td>
				<td>
					${hiRestaunant.address}
				</td><%-- 
				<td>
					${fns:getDictLabel(hiRestaunant.tradingArea, 'hi_trading_area', '')}
				</td> --%>
				<td>
					${hiRestaunant.tel}
				</td>
				<td>
					<fmt:formatDate value="${hiRestaunant.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="hihunan:restaunant:hiRestaunant:edit"><td>
    				<a href="${ctx}/hihunan/restaunant/hiRestaunant/form?id=${hiRestaunant.id}">修改</a>
					<a href="${ctx}/hihunan/restaunant/hiRestaunant/delete?id=${hiRestaunant.id}" onclick="return confirmx('确认要删除该餐厅吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>