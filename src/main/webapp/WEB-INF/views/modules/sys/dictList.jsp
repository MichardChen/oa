<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>字典管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	
		//2016/5/16添加，巡检系统弹框返回按钮调用
		function closeDailog(){
			$("[name=btnClose]")[0].click();
		}
	
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
	    	return false;
	    }
		
		function updateDict(id){
			$("#volOrMaterial").text("修改字典");
			$('label.error[for]').remove();
			$.ajax({
				async:false,
				type:"post",
				data:{'id':id},
				url: ctx + "/sys/dict/updateDict",
				success:function(data){
					var dict = eval('(' + data + ')');
					$("#id").val(dict.id);
					$("#value").val(dict.value);
					$("#label").val(dict.label);
					$("#inputForm #type").val(dict.type);
					$("#inputForm #description").val(dict.description);
					$("#sort").val(dict.sort);
					$("#remarks").val(dict.remarks);
				}
			});
		}
		
		function addKey(type,description,sort){
			$("#volOrMaterial").text("添加键值");
			$('label.error[for]').remove();
			$("#inputForm #type").val(type);
			$("#inputForm #description").val(description);
			$("#sort").val(sort);
		}
		
		function addDict(){
			$("#volOrMaterial").text("添加字典");
			$('label.error[for]').remove();
			$("#id").val('');
			$("#value").val('');
			$("#label").val('');
			$("#inputForm #type").val('');
			$("#inputForm #description").val('');
			$("#sort").val('');
			$("#remarks").val('');
		}
		
		$(document).ready(function() {
			//$("#type").select2('val','');
			$("#value").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
<!-- 	<ul class="nav nav-tabs"> -->
<%-- 		<li class="active"><a href="${ctx}/sys/dict/">字典列表</a></li> --%>
<%-- 		<shiro:hasPermission name="sys:dict:edit"><li><a href="${ctx}/sys/dict/form?sort=10">字典添加</a></li></shiro:hasPermission> --%>
<!-- 	</ul> -->
	<form:form id="searchForm" modelAttribute="dict" action="${ctx}/sys/dict/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>类型：</label><form:select id="type" path="type" class="input-medium" style="width:300px;"><form:option value="" label="请选择"/><form:options items="${typeList}" itemValue="type" itemLabel="description" htmlEscape="false"/></form:select>
		&nbsp;&nbsp;<label>描述 ：</label><form:input path="description" htmlEscape="false" maxlength="50" class="input-medium"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>&nbsp;
			<input type="button" value="字典添加" class="btn btn-primary" href="#addDictDailog" data-toggle='modal' onclick="addDict()"/>
		<!-- <button class="btn btn-primary" href="#addDictDailog" data-toggle='modal' onclick="addDict()">字典添加</button>-->
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed" style="margin-left: 5px;">
		<thead><tr><th>键值</th><th>标签</th><th>类型</th><th>描述</th><th>排序</th><shiro:hasPermission name="sys:dict:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="dict">
			<tr>
				<td>${dict.value}</td>
				<td><a href="${ctx}/sys/dict/form?id=${dict.id}">${dict.label}</a></td>
				<td><a href="javascript:" onclick="$('#type').val('${dict.type}');$('#searchForm').submit();return false;">${dict.type}</a></td>
				<td>${dict.description}</td>
				<td>${dict.sort}</td>
				<shiro:hasPermission name="sys:dict:edit"><td>
    				<a type="button" href="#addDictDailog" data-toggle='modal' onclick="updateDict('${dict.id}')">修改</a>
					<a href="${ctx}/sys/dict/delete?id=${dict.id}&type=${dict.type}" onclick="return confirmx('确认要删除该字典吗？', this.href)">删除</a>
					<a type="button" href="#addDictDailog" data-toggle='modal' onclick="addKey('${dict.type}','${dict.description}','${dict.sort+10}')">添加键值</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<div id="addDictDailog" class="modal hide fade" style="width: 95%;margin-left: -50%;margin-top:5px;background-color:white;height:85%;">
		<div class="modal-header" style="height:40px;">
			<div id = "hintMsg" style="width:80%">
				<h4 style="margin-top:2px;">
					<span id = "volOrMaterial">
						添加字典
					</span>
				</h4>
			</div>
			<div>
	        	<button type="button" name="btnClose" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
	        </div>
	     </div>
	     <div style="height:90%;overflow:auto;margin-left:10px;">
		     <form:form id="inputForm" modelAttribute="dict" action="${ctx}/sys/dict/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>
				<div class="control-group">
					<label class="control-label">键值:</label>
					<div class="controls">
						<form:input path="value" htmlEscape="false" maxlength="50" class="required"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">标签:</label>
					<div class="controls">
						<form:input path="label" htmlEscape="false" maxlength="200" class="required"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">类型:</label>
					<div class="controls">
						<form:input path="type" htmlEscape="false" maxlength="50" class="required abc"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">描述:</label>
					<div class="controls">
						<form:input path="description" htmlEscape="false" maxlength="50" class="required"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">排序:</label>
					<div class="controls">
						<form:input path="sort" htmlEscape="false" maxlength="11" class="required digits"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">备注:</label>
					<div class="controls">
						<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"/>
					</div>
				</div>
				<div class="form-actions">
					<shiro:hasPermission name="sys:dict:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="closeDailog();"/>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>