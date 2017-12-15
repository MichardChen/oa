<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>机构管理</title>
	<style>
		.btn-primary {
	    margin-bottom: 9px;
	}
	</style>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		// 获取微信企业号的部门列表。
	    // var wxqyDeptList = '';
	    
		//2016/5/16添加，巡检系统弹框返回按钮调用
		function closeDailog(){
			$("[name=btnClose]")[0].click();
		}
		
		$(document).ready(function() {
			var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
			var data = ${fns:toJson(list)}, rootId = "${not empty office.id ? office.id : '0'}";
			addRow("#treeTableList", tpl, data, rootId, true);
			$("#treeTable").treeTable({expandLevel : 5});
			
			$("#name").focus();
			$("#inputForm").validate({
				rules: {
					name:{
						remote:{
							type:"POST",
							url:"${ctx}/sys/office/checkName",
							data:{
								oldName:function(){return $("#oldName").val();}
							}
						}
					},
					type:{
						required:true
					},
					grade:{
						required:true
					}
				},
				messages: {
					name:{remote:"机构名称重复"},
					type:{required:"请选择机构类型"},
					grade:{required:"请选择机构级别"}
				},
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
							type: getDictLabel(${fns:toJson(fns:getDictList('sys_office_type'))}, row.type)
						}, pid: (root?0:pid), row: row
					}));
					addRow(list, tpl, data, row.id);
				}
			}
		}
		
		function updateOffice(id){
			$("#volOrMaterial").text("修改机构");
			$('label.error[for]').remove();
			$.ajax({
				async:false,
				type:"post",
				data:{'id':id},
				url: ctx + "/sys/office/updateOffice",
				success:function(data){
					var office = eval('(' + data + ')');
					$("#id").val(office.id);
					$("#businessType").select2('val',office.businessType);
					$("#officeId").val(office.parent.id);
					$("#officeName").val(office.parent.name);
					$("#areaId").val(office.area.id);
					$("#areaName").val(office.area.name);
					$("#name").val(office.name);
					$("#oldName").val(office.name);
					$("#code").val(office.code);
					$("#type").select2('val',office.type);
					$("#grade").select2('val',office.grade);
					$("#useable").select2('val',office.useable);
					$("#address").val(office.address);
					$("#zipCode").val(office.zipCode);
					$("#master").val(office.master);
					$("#phone").val(office.phone);
					$("#fax").val(office.fax);
					$("#email").val(office.email);
					$("#remarks").val(office.remarks);
					if(office.wxqyDept == null || office.wxqyDept == undefined){
						// $("#wxDeptId").select2('val','0'); //注意：0代表空数据 废弃
						$("#wxqyDeptId").val("");
						$("#wxqyDeptName").val("");
					}
					else{
						$("#wxqyDeptId").val(office.wxqyDept.id);
						$("#wxqyDeptName").val(office.wxqyDept.name);
						// $("#wxDeptId").select2('val',office.wxDeptId); 废弃
					}
					if(office.primaryPerson == null || office.primaryPerson == undefined){ 
						$("#primaryPersonId").val("");
						$("#primaryPersonName").val("");
					}
					else{
						$("#primaryPersonId").val(office.primaryPerson.id);
						$("#primaryPersonName").val(office.primaryPerson.name);
					}
				}
			});
		}
		
		function deleteOffice(id){
			$.ajax({
				async:false,
				type:"post",
				data:{'id':id},
				url: ctx + "/sys/office/delete",
				success:function(data){
					
				},
				error:function(x,h,r){
					alertx(x);
				}
			});
		}
		
		function addUnderOffice(id,name){
			$("#volOrMaterial").text("添加下级机构");
			$('label.error[for]').remove();
			$("#officeId").val(id);
			$("#officeName").val(name);
		}
		
		function addOffice(){
			$("#volOrMaterial").text("添加机构");
			$('label.error[for]').remove();
			$("#id").val('');
			$("#officeId").val('');
			$("#officeName").val('');
			$("#areaId").val('10');
			$("#areaName").val('');
			$("#name").val('');
			$("#oldName").val('');
			$("#code").val('');
			$("#businessType").select2('val','');
			$("#type").select2('val','');
			$("#grade").select2('val','');
			$("#useable").select2('val','1');
			$("#address").val('');
			$("#zipCode").val('');
			$("#master").val('');
			$("#phone").val('');
			$("#fax").val('');
			$("#email").val('');
			$("#remarks").val('');
			//$("#wxDeptId").select2('val','0'); //注意：0代表空数据. 废弃
			$("#wxqyDeptId").val("");
			$("#wxqyDeptName").val("");
			$("#primaryPersonId").val("");
			$("#primaryPersonName").val("");
		}
	</script>
</head>
<body>
	<!-- 页面按钮区域 -->
	<input type="button" value="添加机构" class="btn btn-primary" href="#addOfficeDailog" data-toggle='modal' onclick="addOffice()"/>
	<sys:message content="${message}"/>
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th style=" text-align: center;">机构名称</th><th style=" text-align: center;">机构类别</th><th style=" text-align: center;">归属区域</th><th style=" text-align: center;">企业号对应机构</th><th style=" text-align: center;">负责人</th><th style=" text-align: center;">机构类型</th><th style=" text-align: center;">备注</th><shiro:hasPermission name="sys:office:edit"><th style=" text-align: center;">操作</th></shiro:hasPermission></tr></thead>
		<tbody id="treeTableList"></tbody>
	</table>
	<div id="addOfficeDailog" class="modal hide fade" style="width:95%;margin-left:-50%;margin-top:5px;background-color:white;height:85%;">
		<div class="modal-header" style="height:40px;">
			<div id = "hintMsg" style="width:80%">
				<h4 style="margin-top:2px;">
					<span id="volOrMaterial">
						添加机构
					</span>
				</h4>
			</div>
			<div style="margin-top:-35px;">
	        	<button type="button" name="btnClose" data-dismiss="modal" aria-hidden="true" class="close" style="margin-top:3%;">&times;</button>
	        </div>
	     </div>
	     <div style="height:90%;overflow:auto;margin-left:10px;">
			<form:form id="inputForm" modelAttribute="office" action="${ctx}/sys/office/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>
				<div class="control-group">
					<label class="control-label">上级机构:</label>
					<div class="controls">
		                <sys:treeselect id="office" name="parent.id" value="${office.parent.id}" labelName="parent.name" labelValue="${office.parent.name}"
							title="机构" url="/sys/office/treeData" extId="${office.id}" cssClass="" allowClear="true" containerId="inputForm" />
					</div>
				</div>
				<div class="control-group" style="display:none;">
					<label class="control-label">归属区域:</label>
					<div class="controls">
		                <sys:treeselect id="area" name="area.id" value="${office.area.id}" labelName="area.name" labelValue="${office.area.name}"
							title="区域" url="/sys/area/treeData" cssClass="required" containerId="inputForm" dataMsgRequired="必填信息"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">机构类别:</label>
					<div class="controls">
		                <form:select path="businessType" class="input-medium">
							<form:option value="" label="请选择"/>
							<form:options items="${fns:getDictList('PartnerType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">机构名称:</label>
					<div class="controls">
						<input id="oldName" name="oldName" type="hidden"/>
						<form:input path="name" htmlEscape="false" maxlength="50" class="required"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">微信企业号对应机构:</label>
					<div class="controls">
						<sys:treeselect id="wxqyDept" name="wxqyDept.id" value="${office.wxqyDept.id}" labelName="wxqyDept.name" labelValue="${office.wxqyDept.name}"
							title="微信企业号部门" url="/sys/office/wxqyTreeData" cssClass="required" containerId="inputForm" dataMsgRequired="必填信息"/>
						<!--
						<form:select path="wxDeptId" class="input-medium">
							<form:options items="${wxqyDeptList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
						</form:select> -->
						<span class="help-inline"><font color="red">*</font> </span>
						<form:hidden path="wxDeptParentId"/>
					</div>
				</div>
				
				<div class="control-group" style="display:none;">
					<label class="control-label">机构编码:</label>
					<div class="controls">
						<form:input path="code" htmlEscape="false" maxlength="50"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">机构类型:</label>
					<div class="controls">
						<form:select path="type" class="input-medium">
							<form:options items="${fns:getDictList('sys_office_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">机构级别:</label>
					<div class="controls">
						<form:select path="grade" class="input-medium">
							<form:options items="${fns:getDictList('sys_office_grade')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">是否可用:</label>
					<div class="controls">
						<form:select path="useable">
							<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
						<span class="help-inline">“是”代表此账号允许登陆，“否”则表示此账号不允许登陆</span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">主负责人:</label>
					<div class="controls">
						 <sys:treeselect id="primaryPerson" name="primaryPerson.id" value="${office.primaryPerson.id}" labelName="office.primaryPerson.name" labelValue="${office.primaryPerson.name}"
							title="用户" url="/sys/office/treeData?type=3" allowClear="true" notAllowSelectParent="true" containerId="inputForm"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">副负责人:</label>
					<div class="controls">
						 <sys:treeselect id="deputyPerson" name="deputyPerson.id" value="${office.deputyPerson.id}" labelName="office.deputyPerson.name" labelValue="${office.deputyPerson.name}"
							title="用户" url="/sys/office/treeData?type=3" allowClear="true" notAllowSelectParent="true" containerId="inputForm"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">联系地址:</label>
					<div class="controls">
						<form:input path="address" htmlEscape="false" maxlength="50"/>
					</div>
				</div>
				<div class="control-group" style="display:none;">
					<label class="control-label">邮政编码:</label>
					<div class="controls">
						<form:input path="zipCode" htmlEscape="false" maxlength="50"/>
					</div>
				</div>
				<div class="control-group" style="display:none;">
					<label class="control-label">负责人:</label>
					<div class="controls">
						<form:input path="master" htmlEscape="false" maxlength="50"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">电话:</label>
					<div class="controls">
						<form:input path="phone" htmlEscape="false" maxlength="50"/>
					</div>
				</div>
				<div class="control-group" style="display:none;">
					<label class="control-label">传真:</label>
					<div class="controls">
						<form:input path="fax" htmlEscape="false" maxlength="50"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">邮箱:</label>
					<div class="controls">
						<form:input path="email" htmlEscape="false" maxlength="50"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">备注:</label>
					<div class="controls">
						<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"/>
					</div>
				</div>
	<%-- 			<c:if test="${empty office.id}"> --%>
	<!-- 				<div class="control-group"> -->
	<!-- 					<label class="control-label">快速添加下级部门:</label> -->
	<!-- 					<div class="controls"> -->
	<%-- 						<form:checkboxes path="childDeptList" items="${fns:getDictList('sys_office_common')}" itemLabel="label" itemValue="value" htmlEscape="false"/> --%>
	<!-- 					</div> -->
	<!-- 				</div> -->
	<%-- 			</c:if> --%>
				<div class="form-actions">
					<shiro:hasPermission name="sys:office:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="closeDailog()" style="border: 1px solid #969696;"/>
				</div>
			</form:form>
		</div>
	</div>
	<script type="text/template" id="treeTableTpl">
		<tr id="{{row.id}}" pId="{{pid}}">
			<td>{{row.name}}</td>
			<td>{{row.businessTypeName}}</td>
			<td>{{row.area.name}}</td>
			<td>{{row.wxqyDept.name}}</td>
            <td>{{row.primaryPerson.name}}</td>
            <td>{{dict.type}}</td>
			<td>{{row.remarks}}</td>
			<shiro:hasPermission name="sys:office:edit"><td>
				<a type="button" data-toggle="modal" href="#addOfficeDailog" onclick="updateOffice('{{row.id}}')">修改</a>
                <a href="${ctx}/sys/office/delete?id={{row.id}}" onclick="return confirmx('要删除{{row.name}}和其子机构吗？',this.href)">删除</a>
				<%--<a type="button" data-toggle="modal" href="#addOfficeDailog" onclick="addUnderOffice('{{row.id}}','{{row.name}}')">添加下级机构</a>--%>  
			</td></shiro:hasPermission>
		</tr>
	</script>
</body>
</html>