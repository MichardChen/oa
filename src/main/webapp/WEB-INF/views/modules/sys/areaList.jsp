<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>区域管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		//2016/5/16添加，巡检系统弹框返回按钮调用
		function closeDailog(){
			$("[name=btnClose]")[0].click();
		}
	
		function addUnderArea(id,name){
			$("#volOrMaterial").text("添加下级区域");
			$('label.error[for]').remove();
			$("#areaId").val(id);
			$("#areaName").val(name);
		}
		
		function updateArea(id){
			$("#volOrMaterial").text("修改区域");
			$('label.error[for]').remove();
			$.ajax({
				async:false,
				type:"post",
				data:{'id':id},
				url: ctx + "/sys/area/updateArea",
				success:function(data){
					var area = eval('(' + data + ')');
					$("#id").val(area.id);
					$("#areaId").val(area.parent.id);
					$("#areaName").val(area.parent.name);
					$("#name").val(area.name);
					$("#code").val(area.code);
					$("#type").select2('val',area.type);
					$("#remarks").val(area.remarks);
				}
			});
		}
		
		function addArea(){
			$("#volOrMaterial").text("添加区域");
			$("#id").val('');
			$("#areaId").val('');
			$("#areaName").val('');
			$("#name").val('');
			$("#code").val('');
			$("#type").select2('val','');
			$("#remarks").val('');
		}
		
		$(document).ready(function() {
			var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
			var data = ${fns:toJson(list)}, rootId = "0";
			addRow("#treeTableList", tpl, data, rootId, true);
			$("#treeTable").treeTable({expandLevel : 5});
			
			$("#name").focus();
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
		function addRow(list, tpl, data, pid, root){
			for (var i=0; i<data.length; i++){
				var row = data[i];
				if ((${fns:jsGetVal('row.parentId')}) == pid){
					$(list).append(Mustache.render(tpl, {
						dict: {
							type: getDictLabel(${fns:toJson(fns:getDictList('sys_area_type'))}, row.type)
						}, pid: (root?0:pid), row: row
					}));
					addRow(list, tpl, data, row.id);
				}
			}
		}
	</script>
</head>
<body>
<!-- 	<ul class="nav nav-tabs"> -->
<%-- 		<li class="active"><a href="${ctx}/sys/area/">区域列表</a></li> --%>
<%-- 		<shiro:hasPermission name="sys:area:edit"><li><a href="${ctx}/sys/area/form">区域添加</a></li></shiro:hasPermission> --%>
<!-- 	</ul> -->
		<input type="button" value="添加区域" class="btn btn-primary" href="#addAreaDailog" data-toggle='modal' onclick="addArea()" style="margin-left: 5px;"/>
	<!-- <button class="btn btn-primary" href="#addAreaDailog" data-toggle='modal' onclick="addArea()" style="margin-left: 5px;">添加区域</button>-->
	<sys:message content="${message}"/>
	<table id="treeTable" class="table table-striped table-bordered table-condensed" style="margin-left:5px; margin-top:5px;">
		<thead><tr><th>区域名称</th><th>区域编码</th><th>区域类型</th><th>备注</th><shiro:hasPermission name="sys:area:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody id="treeTableList" ></tbody>
	</table>
	<div id="addAreaDailog" class="modal hide fade" style="width: 95%;margin-left: -50%;margin-top:5px;background-color:white;height:85%;">
		<div class="modal-header" style="height:40px;">
			<div id = "hintMsg" style="width:80%">
				<h4 style="margin-top:2px;">
					<span id = "volOrMaterial">
						添加区域
					</span>
				</h4>
			</div>
			<div>
	        	<button type="button" name="btnClose" data-dismiss="modal" aria-hidden="true" class="close" >&times;</button>
	        </div>
	     </div>
	     <div style="height:90%;overflow:auto;margin-left:10px;">
		    <form:form id="inputForm" modelAttribute="area" action="${ctx}/sys/area/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>
				<div class="control-group">
					<label class="control-label">上级区域:</label>
					<div class="controls">
						<sys:treeselect id="area" name="parent.id" value="${area.parent.id}" labelName="parent.name" labelValue="${area.parent.name}"
							title="区域" url="/sys/area/treeData" extId="${area.id}" cssClass="" allowClear="true" containerId="inputForm"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">区域名称:</label>
					<div class="controls">
						<form:input path="name" htmlEscape="false" maxlength="50" class="required"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">区域编码:</label>
					<div class="controls">
						<form:input path="code" htmlEscape="false" maxlength="50"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">区域类型:</label>
					<div class="controls">
						<form:select path="type" class="input-medium">
							<form:options items="${fns:getDictList('sys_area_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">备注:</label>
					<div class="controls">
						<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"/>
					</div>
				</div>
				<div class="form-actions">
					<shiro:hasPermission name="sys:area:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="closeDailog()" style="border: 1px solid #969696;"/>
				</div>
			</form:form>
		</div>
	</div>
	<script type="text/template" id="treeTableTpl">
		<tr id="{{row.id}}" pId="{{pid}}">
			<td><a href="${ctx}/sys/area/form?id={{row.id}}">{{row.name}}</a></td>
			<td>{{row.code}}</td>
			<td>{{dict.type}}</td>
			<td>{{row.remarks}}</td>
			<shiro:hasPermission name="sys:area:edit"><td>
				<a type="button" href="#addAreaDailog" data-toggle='modal' onclick="updateArea('{{row.id}}')">修改</a>
				<a href="${ctx}/sys/area/delete?id={{row.id}}" onclick="return confirmx('要删除该区域及所有子区域项吗？', this.href)">删除</a>
				<a type="button" href="#addAreaDailog" data-toggle='modal' onclick="addUnderArea('{{row.id}}','{{row.name}}')">添加下级区域</a>
			</td></shiro:hasPermission>
		</tr>
	</script>
</body>
</html>