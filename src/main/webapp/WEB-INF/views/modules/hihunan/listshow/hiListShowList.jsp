<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>旅游管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
			$("#btnNew").click(function(){
				var name = "概要添加";
				var urlparam = "${ctx}/hihunan/listshow/hiListShow/form?type=" + $("#type").val(); 
				// 跳转到新建页面
				top.addTabFmPage($("#addLink"),name,true,urlparam);
			});
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
		<li class="active"><a href="${ctx}/hihunan/listshow/hiListShow/">概要列表</a></li>
		<li style=""><a id="addLink" href="${ctx}/hihunan/listshow/hiListShow/form">概要添加</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="hiListShow" action="${ctx}/hihunan/listshow/hiListShow/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>题头：</label>
				<form:input path="title" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>分类：</label>
			<form:select id="type" path="type" class="input-medium">
			   <form:option value="" label="请选择" />
			   <form:options items="${listType}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
			
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
			<!-- <input id="btnNew" class="btn btn-primary" type="button" value="增加"/> -->
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>题头</th>
				<th>分类</th>
				<th>地名</th>
				<th>更新日期</th>
				<shiro:hasPermission name="hihunan:listshow:hiListShow:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="hiListShow">
			<tr>
				<td><a href="${ctx}/hihunan/listshow/hiListShow/form?id=${hiListShow.id}">
					${hiListShow.title}
				</a></td>
				<th>${hiListShow.type}</th>
				<th>${hiListShow.address}</th>
				<td>
					<fmt:formatDate value="${hiListShow.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="hihunan:listshow:hiListShow:edit"><td>
    				<a href="${ctx}/hihunan/listshow/hiListShow/form?id=${hiListShow.id}">修改</a>
					<a href="${ctx}/hihunan/listshow/hiListShow/delete?id=${hiListShow.id}" onclick="return confirmx('确认要删除该其他吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>