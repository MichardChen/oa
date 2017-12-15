<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>文章管理</title>
	<meta name="decorator" content="default"/>
	
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/hihunan/artical/hiArtical/">文章列表</a></li>
		<shiro:hasPermission name="hihunan:artical:hiArtical:edit"><li style=""><a href="${ctx}/hihunan/artical/hiArtical/form">文章添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="hiArtical" action="${ctx}/hihunan/artical/hiArtical/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			 <li><label>状态：</label>
			        <form:radiobutton path="auditState" value="1" />&nbsp;已上架&nbsp;&nbsp;
			        <form:radiobutton path="auditState" value="0"/>&nbsp;未上架
				</li> 
			<li><label>题头：</label>
				<form:input path="title" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>日期范围：</label>
				<input id="startTime" name="startTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${hiArtical.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>—
					<input id="endTime" name="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${hiArtical.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
			</li>
			<li><label>分类：</label>
			<form:select path="articalType" class="required input-small">
				   <form:option value="" label="请选择" />
				   <form:options items="${listType}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
					<input id="soldOut" hidden="true" style="display:none;" type="button" value="下架" class="btn btn-primary" onclick="fuSoldOut();"/>
			        <input id="putaway" hidden="true" style="display:none;" class="btn btn-primary" type="button" value="上架"  onclick="fuPutaway();"/>
					
					<input id="scrapyNews" type="button" value="新闻抓取" class="btn btn-primary"/>
					<input id="deleteNews" type="button" value="新闻删除" class="btn btn-primary"/>
				</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input name="checkAll" type="checkbox" onChange="isAllCheck(this);"/></th>
				<th>题头</th>
				<th>发表日期</th>
				<shiro:hasPermission name="hihunan:artical:hiArtical:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="hiArtical">
			<tr>
				<td><input name="checkData" type="checkbox" value="${hiArtical.id}"/></td>
				<c:if test="${hiArtical.articalType eq 'advisory'}">
				<td><a href="${ctx}/hihunan/artical/hiArtical/formView?id=${hiArtical.id}">
					${hiArtical.title}
				</a></td>
				</c:if>
				 <c:if test="${hiArtical.articalType ne 'advisory'}">
				  <td><a href="${ctx}/hihunan/artical/hiArtical/form?id=${hiArtical.id}">
					${hiArtical.title}
				</a></td>
				</c:if>
				<td>
					<fmt:formatDate value="${hiArtical.publishDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<c:if test="${hiArtical.articalType eq 'advisory'}">
				<td>
    				<a href="${ctx}/hihunan/artical/hiArtical/formView?id=${hiArtical.id}">查看</a>
				</td>
				</c:if>
				<c:if test="${hiArtical.articalType ne 'advisory'}">
				<shiro:hasPermission name="hihunan:artical:hiArtical:edit"><td>
    				<a href="${ctx}/hihunan/artical/hiArtical/form?id=${hiArtical.id}">修改</a>
					<a href="${ctx}/hihunan/artical/hiArtical/delete?id=${hiArtical.id}" onclick="return confirmx('确认要删除该artical吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
				</c:if>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
	<%@ include file="/WEB-INF/views/modules/hihunan/artical/hiArticalListJs.jsp"%>
</body>
</html>