]<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>角色管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<script type="text/javascript">
	
	//2016/5/16添加，巡检系统弹框返回按钮调用
	function closeDailog(){
		$("[name=btnClose]")[0].click();
	}
	
	var setting;
	var zNodes;
	var tree;
		$(document).ready(function(){
			$("#name").focus();
			$("#inputForm").validate({
				rules: {
					//name: {remote: "${ctx}/sys/role/checkName?oldName=" + encodeURIComponent("${role.name}")},
					//enname: {remote: "${ctx}/sys/role/checkEnname?oldEnname=" + encodeURIComponent("${role.enname}")}
					name: {remote:{
								type:'POST',
								url:'${ctx}/sys/role/checkName',
								data:{
									oldName:function(){return $("#oldName").val();}
								}
								}
							},
					enname: {remote:{
								type:'POST',
								url:'${ctx}/sys/role/checkEnname',
								data:{
									oldEnname:function(){return $("#oldEnname").val();}
								}
								}
							}
				},
				messages: {
					name: {remote: "角色名已存在"},
					enname: {remote: "英文名已存在"}
				},
				submitHandler: function(form){
					var ids = [];
					var nodes = tree.getCheckedNodes(true);
					for(var i=0; i<nodes.length; i++) {
						ids.push(nodes[i].id);
					}
					$("#menuIds").val(ids);
					var ids2 = [];
					var nodes2 = tree2.getCheckedNodes(true);
					for(var i=0; i<nodes2.length; i++) {
						ids2.push(nodes2[i].id);
					}
					$("#officeIds").val(ids2);
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

			 setting = {check:{enable:true,nocheckInherit:true},view:{selectedMulti:false},
					data:{simpleData:{enable:true}},callback:{beforeClick:function(id, node){
						tree.checkNode(node, !node.checked, true, true);
						return false;
					}}};
			
			// 用户-菜单
			 zNodes=[
					<c:forEach items="${menuList}" var="menu">{id:"${menu.id}", pId:"${not empty menu.parent.id?menu.parent.id:0}", name:"${not empty menu.parent.id?menu.name:'权限列表'}"},
		            </c:forEach>];
			// 初始化树结构
			 tree = $.fn.zTree.init($("#menuTree"), setting, zNodes);
			// 不选择父节点
			tree.setting.check.chkboxType = { "Y" : "ps", "N" : "s" };
			// 默认选择节点
			
			// 默认展开全部节点
			tree.expandAll(true);
			
			// 用户-机构
			var zNodes2=[
					<c:forEach items="${officeList}" var="office">{id:"${office.id}", pId:"${not empty office.parent?office.parent.id:0}", name:"${office.name}"},
		            </c:forEach>];
			// 初始化树结构
			var tree2 = $.fn.zTree.init($("#officeTree"), setting, zNodes2);
			// 不选择父节点
			tree2.setting.check.chkboxType = { "Y" : "ps", "N" : "s" };
			// 默认选择节点
			var ids2 = "${role.officeIds}".split(",");
			for(var i=0; i<ids2.length; i++) {
				var node = tree2.getNodeByParam("id", ids2[i]);
				try{tree2.checkNode(node, true, false);}catch(e){}
			}
			// 默认展开全部节点
			tree2.expandAll(true);
			// 刷新（显示/隐藏）机构
			refreshOfficeTree();
			$("#dataScope").change(function(){
				refreshOfficeTree();
			});
			
			//点击新增按钮事件
			$("#roleInsert").click(function(){
				clearUpForm();
			});
		});
		
		//清空弹框数据
		function clearUpForm(){
			
			$("#id").val("");
			//$("#officeId").val("");   20160630 删除
			//$("#officeName").val(""); 20160630 删除
			$("#oldName").val("");
			$("#name").val("");
			$("#oldEnname").val("");
			$("#enname").val("");
			$("#remarks").val("");
			var menyIds=$("#ids").val();
			if(menyIds != null && menyIds != ""){
			// 默认不选择节点
			var ids2 = menyIds.split(",");
			for(var i=0; i<ids2.length; i++) {
				var node = tree.getNodeByParam("id", ids2[i]);
				try{tree.checkNode(node, false, false);}catch(e){}
			}
			}
		}
		//通过id获取数据明细
		function viewDetail(id){
			$.ajax({
				async:false,
				type:"get",
				data:{"id":id},
				url:ctx + "/sys/role/edit",
				success:function(data){
					var role = JSON.parse(data);
					$("#id").val(role.id);
					//$("#officeId").val(role.officeId);     20160630 删除
					//$("#officeName").val(role.officeName); 20160630 删除
					$("#oldName").val(role.name);
					$("#name").val(role.name);
					$("#oldEnname").val(role.enname);
					$("#enname").val(role.enname);
					$("#roleType").select2().val(role.roleType);
					$("#sysData").select2().val(role.sysData);
					$("#useable").select2().val(role.useable);
					$("#dataScope").select2().val(role.dataScope);
					$("#remarks").val(role.remarks);
					var menuIds = role.menuIds;
					$("#ids").val(menuIds);
					// 默认选择节点
					if(menuIds.length>0){ // 如果已经配置了菜单，则选中个菜单在tree上。
						var ids2 = menuIds.split(",");
						for(var i=0; i<ids2.length; i++) {
							var node = tree.getNodeByParam("id", ids2[i]);
							try{tree.checkNode(node, true, false);}catch(e){}
						}
					}
				},error:function(x,h,r){
					alertx("error");
				}
			});
		      return false;
		}
		
		function refreshOfficeTree(){
			if($("#dataScope").val()==9){
				$("#officeTree").show();
			}else{
				$("#officeTree").hide();
			}
		}
	</script>
</head>
<body>
		<input type="button" value="新增角色" id="roleInsert" data-toggle="modal" class="btn btn-primary" href='#menuEditDialog' style="margin-bottom: 8px; margin-left: 5px;"/>
	 <!--<button id="roleInsert" data-toggle="modal" class="btn btn-primary" href='#menuEditDialog' style="margin-bottom: 8px; margin-left: 5px;" >新增角色</button>-->
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed" style="margin-left: 5px;">
		<tr><th style="text-align: center;">角色名称</th><th style="text-align: center;">角色key</th><th style="text-align: center;">数据范围</th><shiro:hasPermission name="sys:role:edit"><th style="text-align: center;">操作</th></shiro:hasPermission></tr>
		<c:forEach items="${list}" var="role">
			<tr>
			    <!-- 2016-7-12去除  role.name和role.enname处的链接 -->
				<td>${role.name}</td>
				<td>${role.enname}</td>
				<!-- 20160630 删除  <td>${role.office.name}</td> -->
				<td>${fns:getDictLabel(role.dataScope, 'sys_data_scope', '无')}</td>
				<shiro:hasPermission name="sys:role:edit"><td>
				    <!-- 20160712 去除 分配的功能 -->
					<!-- <a href="${ctx}/sys/role/assign?id=${role.id}">分配</a>  -->
					<!-- 20160702 去除 fns:getUser().admin -->
					<c:if test="${(role.sysData eq fns:getDictValue('是', 'yes_no', '1'))||!(role.sysData eq fns:getDictValue('是', 'yes_no', '1'))}">
						<a id="${role.id}" data-toggle="modal" href='#menuEditDialog' onclick="viewDetail(${role.id})";>修改</a>
					</c:if>
					<%-- <a href="${ctx}/sys/role/form?id=${role.id}">修改</a> --%>
					<a href="${ctx}/sys/role/delete?id=${role.id}" onclick="return confirmx('确认要删除该角色吗？', this.href)">删除</a>
				</td></shiro:hasPermission>	
			</tr>
		</c:forEach>
	</table>
	<input id="ids" name="ids" type="hidden" />
	<!-- 角色添加修改弹框 -->
	<div id="menuEditDialog" class="modal hide fade" style="width:60%;margin-left: -40%;background-color:white;height:80%;">
		<div class="modal-header" style="height:40px;">
			<div id = "hintMsg" style="width:80%">
				<h4 style="margin-top:2px;">
					<span id = "volOrMaterial">
						新增/修改角色
					</span>
				</h4>
			</div>
			<div style="margin-top:-35px;">
	        	<button type="button" name="btnClose" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
	        </div>
	     </div>
	     <div style="height:90%;overflow:auto;margin-left:10px;">
			<form:form id="inputForm" modelAttribute="role" action="${ctx}/sys/role/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>
				<div class="control-group" style="display:none;">
					<label class="control-label">归属机构:</label>
					<div class="controls">
		                <sys:treeselect id="office" name="office.id" value="${role.office.id}" labelName="office.name" labelValue="${role.office.name}"
							title="机构" url="/sys/office/treeData" cssClass="required" containerId="inputForm"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">角色名称:</label>
					<div class="controls">
						<input id="oldName" name="oldName" type="hidden" value="${role.name}">
						<form:input path="name" htmlEscape="false" maxlength="50" class="required"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">角色KEY:</label>
					<div class="controls">
						<input id="oldEnname" name="oldEnname" type="hidden" value="${role.enname}">
						<form:input path="enname" htmlEscape="false" maxlength="50" class="required"/>
						<span class="help-inline"><font color="red">*</font>角色的识别关键字,确定后请勿随意修改</span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">角色类型:</label>
					<div class="controls"><%--
						<form:input path="roleType" htmlEscape="false" maxlength="50" class="required"/>
						<span class="help-inline" title="activiti有3种预定义的组类型：security-role、assignment、user 如果使用Activiti Explorer，需要security-role才能看到manage页签，需要assignment才能claim任务">
							工作流组用户组类型（security-role：管理员、assignment：可进行任务分配、user：普通用户）</span> --%>
						<form:select path="roleType" class="input-medium">
							<form:option value="assignment">任务分配</form:option>
							<form:option value="security-role">管理角色</form:option>
							<form:option value="user">普通角色</form:option>
						</form:select>
						<span class="help-inline" title="activiti有3种预定义的组类型：security-role、assignment、user 如果使用Activiti Explorer，需要security-role才能看到manage页签，需要assignment才能claim任务">
							工作流组用户组类型（任务分配：assignment、管理角色：security-role、普通角色：user）</span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">是否系统数据:</label>
					<div class="controls">
						<form:select path="sysData">
							<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
						<span class="help-inline">“是”代表此数据只有超级管理员能进行修改，“否”则表示拥有角色修改人员的权限都能进行修改</span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">是否可用</label>
					<div class="controls">
						<form:select path="useable">
							<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
						<span class="help-inline">“是”代表此数据可用，“否”则表示此数据不可用</span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">数据范围:</label>
					<div class="controls">
						<form:select path="dataScope" class="input-medium">
							<form:options items="${fns:getDictList('sys_data_scope')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
						<span class="help-inline">特殊情况下，设置为“按明细设置”，可进行跨机构授权</span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">角色授权:</label>
					<div class="controls">
						<div id="menuTree" class="ztree" style="margin-top:3px;float:left;"></div>
						<form:hidden path="menuIds"/>
						<div id="officeTree" class="ztree" style="margin-left:100px;margin-top:3px;float:left;"></div>
						<form:hidden path="officeIds"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">备注:</label>
					<div class="controls">
						<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"/>
					</div>
				</div>
				<div class="form-actions">
					<%-- <c:if test="${(role.sysData eq fns:getDictValue('是', 'yes_no', '1') && fns:getUser().admin)||!(role.sysData eq fns:getDictValue('是', 'yes_no', '1'))}">
						<shiro:hasPermission name="sys:role:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
					</c:if> --%>
					<shiro:hasPermission name="sys:role:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="closeDailog()" style="border: 1px solid #969696;"    />
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>