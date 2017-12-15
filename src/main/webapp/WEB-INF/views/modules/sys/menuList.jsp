<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>菜单管理</title>
	<style>
	.btn-primary {
     margin-bottom: 8px;
   	 margin-left: 5px
	}
	
	</style>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
	
		//2016/5/16添加，巡检系统弹框返回按钮调用
		function closeDailog(){
			$("[name=btnClose]")[0].click();
		}
	
		$(document).ready(function() {
			$("#treeTable").treeTable({expandLevel : 3}).show();
			
			//点击新增按钮事件
			$("#menuInsert").click(function(){
				clearUpForm();
			});
		});
		
		//清空弹框函数
		function clearUpForm() {
			//$("#inputForm input").val("");
			//$("#inputForm select").val(""); 
			$("#id").val("");
			$("#name").val("");
			$("#href").val("");
			$("#wxSignUrl").val("");
			$("#jsFileName").val("");
			$("#version").val("");
			$("#target").val("");
			$("#icon").val("");
			$("#sort").val("30");
			$("#menuId").val("");
			$("#menuName").val("");
			$("#permission").val("");
			$("#remarks").val("");
			$("#media2").attr("checked",true);
		}
		
		//通过id获取数据明细
		function viewDetail(id){
			$.ajax({
				async:false,
				type:'post',
				url:ctx+"/sys/menu/edit?id=" + id,
				success:function(data){
					var menu = JSON.parse(data);
					$("#id").val(menu.id);
					$("#name").val(menu.name);
					$("#href").val(menu.href);
					$("#wxSignUrl").val(menu.wxSignUrl);
					$("#jsFileName").val(menu.jsFileName);
					$("#version").val(menu.version);
					$("#target").val(menu.target);
					$("#icon").val(menu.icon);
					$("#sort").val(menu.sort);
					$("#isShow").val(menu.isShow);
					$("#menuId").val(menu.parent.id);
					$("#menuName").val(menu.parent.name);
					$("#permission").val(menu.permission);
					$("#remarks").val(menu.remarks);
					$("input[name=media]").each(function(){
						if($(this).val() == menu.media){
							$(this).attr("checked",true);
						}else{
							$(this).attr("checked",false);
						}
								
					});
					$("input[name=isShow]").each(function(){
						if($(this).val() == menu.isShow){
							$(this).attr("checked",true);
						}else{
							$(this).attr("checked",false);
						}
								
					})
				},error:function(x,h,r){
					alertx("error");
				}
			});
		      return false;
		}
		
		function addMenu(id){
			$.ajax({
				async:false,
				type:'post',
				url:ctx+"/sys/menu/edit?parent.id=" + id,
				success:function(data){
					var menu = JSON.parse(data);
					$("#id").val(menu.id);
					$("#name").val(menu.name);
					$("#href").val(menu.href);
					$("#wxSignUrl").val(menu.wxSignUrl);
					$("#jsFileName").val(menu.jsFileName);
					$("#version").val(menu.version);
					$("#target").val(menu.target);
					$("#icon").val(menu.icon);
					$("#sort").val(menu.sort);
					$("#isShow").val(menu.isShow);
					$("#menuId").val(menu.parent.id);
					$("#menuName").val(menu.parent.name);
					$("#permission").val(menu.permission);
					$("#remarks").val(menu.remarks);
					$("input[name=media]").each(function(){
						if($(this).val() == menu.media){
							$(this).attr("checked",true);
						}else{
							$(this).attr("checked",false);
						}
								
					});
					$("input[name=isShow]").each(function(){
						if($(this).val() == menu.isShow){
							$(this).attr("checked",true);
						}else{
							$(this).attr("checked",false);
						}
								
					})
				},error:function(x,h,r){
					alertx("error");
				}
			});
		      return false;
		}
		
    	function updateSort() {
			loading('正在提交，请稍等...');
	    	$("#listForm").attr("action", "${ctx}/sys/menu/updateSort");
	    	$("#listForm").submit();
    	}
	</script>
</head>
<body>
	<div style="margin-top: ">
			<input type="button" value="新增菜单" id="menuInsert" type="button" data-toggle="modal" class="btn btn-primary" href='#menuEditDialog'/>
		<!-- <button id="menuInsert" type="button" data-toggle="modal" class="btn btn-primary" href='#menuEditDialog' >新增菜单</button>-->
	</div>
	<sys:message content="${message}"/>
	<form id="listForm" method="post">
		<table id="treeTable" class="table table-striped table-bordered table-condensed hide" style="margin-left: 5px;">
			<thead><tr><th style="text-align:center;">名称</th><th style="text-align:center;">链接</th><th style="text-align:center;">终端</th><th style="text-align:center;">排序</th><th style="text-align:center;">可见</th><th style="text-align:center;">权限标识</th><shiro:hasPermission name="sys:menu:edit"><th style="text-align:center;">操作</th></shiro:hasPermission></tr></thead>
			<tbody><c:forEach items="${list}" var="menu">
				<tr id="${menu.id}" pId="${menu.parent.id ne '1'?menu.parent.id:'0'}">
					<td nowrap><i class="icon-${not empty menu.icon?menu.icon:' hide'}"></i><a href="${ctx}/sys/menu/form?id=${menu.id}">${menu.name}</a></td>
					<td title="${menu.href}">${fns:abbr(menu.href,30)}</td>
					<td>${menu.media eq 'pc'?'电脑':'手机'}</td>
					<td style="text-align:center;">
						<shiro:hasPermission name="sys:menu:edit">
							<input type="hidden" name="ids" value="${menu.id}"/>
							<input name="sorts" type="text" value="${menu.sort}" style="width:50px;margin:0;padding:0;text-align:center;">
						</shiro:hasPermission><shiro:lacksPermission name="sys:menu:edit">
							${menu.sort}
						</shiro:lacksPermission>
					</td>
					<td>${menu.isShow eq '1'?'显示':'隐藏'}</td>
					<td title="${menu.permission}">${fns:abbr(menu.permission,30)}</td>
					<shiro:hasPermission name="sys:menu:edit"><td nowrap>
						<a id="${menu.id}" data-toggle="modal" href='#menuEditDialog' onclick="viewDetail('${menu.id}')";>修改</a>
						<a href="${ctx}/sys/menu/delete?id=${menu.id}" onclick="return confirmx('要删除该菜单及所有子菜单项吗？', this.href)">删除</a>
						<%-- <a href="${ctx}/sys/menu/form?parent.id=${menu.id}">添加下级菜单</a>  --%>
						<a id="${menu.id}" data-toggle="modal" href='#menuEditDialog' onclick="addMenu(${menu.id})";>添加下级菜单</a>
					</td></shiro:hasPermission>
				</tr>
			</c:forEach></tbody>
		</table>
		<shiro:hasPermission name="sys:menu:edit"><div class="form-actions pagination-left">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="保存排序" onclick="updateSort();"/>
		</div></shiro:hasPermission>
	 </form>
	 
	<!-- 菜单添加修改弹框 -->
	<div id="menuEditDialog" class="modal hide fade" style="width:70%;margin-left: -40%;background-color:white;height:80%;">
		<div class="modal-header" style="height:40px;">
			<div id = "hintMsg" style="width:80%">
				<h4 style="margin-top:2px;">
					<span id = "volOrMaterial">
						新增/修改菜单
					</span>
				</h4>
			</div>
			<div style="margin-top:-35px;">
	        	<button type="button" name="btnClose" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
	        </div>
	     </div>
	     <div style="height:90%;overflow:auto;margin-left:10px;">
			<form:form id="inputForm" modelAttribute="menu" action="${ctx}/sys/menu/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>
				<div class="control-group">
					<label class="control-label">上级菜单:</label>
					<div class="controls">
		                <sys:treeselect id="menu" name="parent.id" value="${menu.parent.id}" labelName="parent.name" labelValue="${menu.parent.name}"
							title="菜单" url="/sys/menu/treeData" extId="${menu.id}" cssClass="required" containerId="inputForm"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">名称:</label>
					<div class="controls">
						<form:input path="name" htmlEscape="false" maxlength="50" class="required input-xlarge"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">链接:</label>
					<div class="controls">
						<form:input path="href" htmlEscape="false" maxlength="2000" class="input-xxlarge"/>
						<span class="help-inline">点击菜单跳转的页面(PC端为RESTFUL地址，手机端为html文件)</span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">PC页面对应js及版本号:</label>
					<div class="controls">
					    <form:input path="jsFileName" htmlEscape="false" maxlength="400" class="input-small"/>
						<form:input path="version" htmlEscape="false" maxlength="400" class="input-small"/>
						<span class="help-inline">此菜单为PC端页面时,对应的js版本号</span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">微信签名URL:</label>
					<div class="controls">
						<form:input path="wxSignUrl" htmlEscape="false" maxlength="400" class="input-xxlarge"/>
						<span class="help-inline">当此页面使用微信js-sdk时的签名URL,必须和链接的文件名和版本号相同。如insR.html?v=20160812</span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">目标:</label>
					<div class="controls">
						<form:input path="target" htmlEscape="false" maxlength="10" class="input-small"/>
						<span class="help-inline">链接地址打开的目标窗口，默认：mainFrame</span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">图标:</label>
					<div class="controls">
					    <!-- 2016-7-7 去除图标选择的标签，改成自由输入 
						<sys:iconselect id="icon" name="icon" value="${menu.icon}"/> -->
						<form:input path="icon" htmlEscape="false" maxlength="100" class="input-xxlarge"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">终端:</label>
					<div class="controls">
						 <form:radiobuttons path="media" items="${fns:getDictList('media')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required" />  
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">排序:</label>
					<div class="controls">
						<form:input path="sort" htmlEscape="false" maxlength="50" class="required digits input-small"/>
						<span class="help-inline">排列顺序，升序。</span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">可见:</label>
					<div class="controls">
						<form:radiobuttons path="isShow" items="${fns:getDictList('show_hide')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
						<span class="help-inline">该菜单或操作是否显示到系统菜单中</span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">权限标识:</label>
					<div class="controls">
						<form:input path="permission" htmlEscape="false" maxlength="100" class="input-xxlarge"/>
						<span class="help-inline">控制器中定义的权限标识，如：@RequiresPermissions("权限标识")</span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">备注:</label>
					<div class="controls">
						<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="input-xxlarge"/>
					</div>
				</div>
				<div class="form-actions" >
					<shiro:hasPermission name="sys:menu:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="closeDailog();" style="border: 1px solid #969696;"/>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>