<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>待办任务</title>
	<meta name="decorator" content="default"/>
  	<!-- Bootstrap风格的JqGrid -->
  	<link href="${ctxStatic}/jqGrid5.0/css/ui.jqgrid-bootstrap.css" rel="stylesheet" type="text/css"  />
  	<style type="text/css">
  		#detailTable table{
  			border-right:1px solid #ddd;
  			border-bottom:1px solid #ddd;
  			border-left:1px solid #ddd;
  			border-top:1px solid #ddd
  		}
  		#detailTable td{
  		
  		border-bottom: 1px solid #ddd;
    	border-right: 1px solid #ddd;
  		
  		}
  		.pagination ul>li>a, .pagination ul>li>span {
	    float: left;
	    padding: 4px 12px;
	    line-height: 20px;
	    text-decoration: none;
	    background-color: #e8e1e1;
	    border-left-width: 0;
	    color: #00a1ff;
	    border-left:5px solid #fff;
		}
  	.ui-jqgrid-hbox th{border-right: 1px solid #ddd;}
  		
  	</style>
	<script type="text/javascript">
	
		function closeDailog(){
			$("[name=btnClose]")[0].click();
		};
		
// 		function setProcInsId(procInsId,taskId){
// 			$("#procInsId").val(procInsId);
// 			$("#taskId").val(taskId);
// 		};

		function isAllCheck(con){
			var value = con.checked;
			var checkArray = $('input[type="checkbox"][name="checkData"]');
			if(value == true){
				for(var i = 0;i < checkArray.length;i++){
					var checkSingle = checkArray[i];
					checkSingle.checked = true;
				}
			}else{
				for(var i = 0;i < checkArray.length;i++){
					var checkSingle = checkArray[i];
					checkSingle.checked = false;
				}
			}
		}
		
		function getCheckBoxData(){//获取选择行的数据
			var trValue = '';
			var checkArray = $('input[type="checkbox"][name="checkData"]:checked');
			for(var i = 0;i < checkArray.length;i++){
				trValue += checkArray[i].value + ",";
			}
			return trValue;
		}
		
		function getDetailPostURL(procInsId){
		    var url = ctx + "/inspection/faultInq/historicDetail";
		    return url + "?procInsId=" + procInsId;
		};
		    
		function doDetailSearch(procInsId){
			$("#historicDetail").removeClass('hide');
			
			//重设JqGrid的宽度。(需要在检索之前)
			$("#detailTable").setGridWidth($("#historicDetail").width()*0.9);
			
		    $("#detailTable").jqGrid('setGridParam',{datatype:"json",url:getDetailPostURL(procInsId)}).trigger("reloadGrid");
		    
			// 检索流转信息
		    $("#detailTable").jqGrid('setGridParam',{datatype:"json",url:getDetailPostURL(procInsId)}).trigger("reloadGrid");
		    // 设置流程图片
		    $("#tracePhoto").attr('src',ctx + "/inspection/faultInq/tracePhoto?procInsId=" + procInsId + "&v=" + Math.random());
		};
		
		$(document).ready(function() {
			
			$("#mainTable").jqGrid({
				url:ctx + "/inspection/faultInq/historicDetail",
				datatype:"local",
				colNames:['执行环节','执行人','开始时间','结束时间','任务历时','备注'],
				colModel:[
				          {name:'activityName',index:'',editable:false,edittype:'text',width:50,align:'left'},
				          {name:'assigneeName',index:'',editable:false,edittype:'text',width:40,align:'left'},
				          {name:'startTime',index:'',editable:false,edittype:'text',width:65,align:'left'},
				          {name:'endTime',index:'',editable:false,edittype:'text',width:65,align:'left'},
				          {name:'durationTime',index:'',editable:false,edittype:'text',width:40,align:'left'},
				          {name:'comment',index:'',editable:false,edittype:'text',width:40,align:'left'}
				          ],
				rownumbers:false,
				autoScroll:true,
				height:300,
				width: $("#historicDetail").width()*0.9, //宽度
				autowidth: false, // 如果设为true，则Grid的宽度会根据父容器的宽度自动重算。重算仅发生在Grid初始化的阶段；
				                  // 如果当父容器尺寸变化了，同时也需要变化Grid的尺寸的话，则需要在自己的代码中调用setGridWidth方法来完成。  
				shrinkToFit:true, //此选项用于根据width计算每列宽度的算法。如果shrinkToFit为true且设置了width值，则每列宽度会根据width成比例缩放
				sortname:'id',
				recordpos:'left',
				viewrecords:true,
				multiselect:false,
				sortorder:'desc'
			});
			
			//流程明细jqGrid初始化
			$("#detailTable").jqGrid({
				url:ctx + "/inspection/faultInq/historicDetail",
				datatype:"local",
				colNames:['执行环节','执行人','开始时间','结束时间','任务历时','备注'],
				colModel:[
				          {name:'activityName',index:'',editable:false,edittype:'text',width:50,align:'left'},
				          {name:'assigneeName',index:'',editable:false,edittype:'text',width:40,align:'left'},
				          {name:'startTime',index:'',editable:false,edittype:'text',width:65,align:'left'},
				          {name:'endTime',index:'',editable:false,edittype:'text',width:65,align:'left'},
				          {name:'durationTime',index:'',editable:false,edittype:'text',width:40,align:'left'},
				          {name:'comment',index:'',editable:false,edittype:'text',width:40,align:'left'}
				          ],
				rownumbers:false,
				autoScroll:true,
				height:300,
				width: $("#historicDetail").width()*0.9, //宽度
				autowidth: false, // 如果设为true，则Grid的宽度会根据父容器的宽度自动重算。重算仅发生在Grid初始化的阶段；
				                  // 如果当父容器尺寸变化了，同时也需要变化Grid的尺寸的话，则需要在自己的代码中调用setGridWidth方法来完成。  
				shrinkToFit:true, //此选项用于根据width计算每列宽度的算法。如果shrinkToFit为true且设置了width值，则每列宽度会根据width成比例缩放
				sortname:'id',
				recordpos:'left',
				viewrecords:true,
				multiselect:false,
				sortorder:'desc'
			});
			
			$("#sendAppoint").click(function(){//委托提交
				var taskData = getCheckBoxData();
				if(taskData == ''){
					alertx("请选择任务");
					return;
				}
				var toUserId = $("#name").val();//获取被委托人的ID
				if(toUserId == ''){
					alertx("请选择被委托人");
					return;
				};
				$("#sendAppoint").val("提交中");
				$("#sendAppoint").attr("disabled", true);
				$.ajax({
					async:true,
					type:"post",
					data:{'toUserId':toUserId,
						'taskData':taskData},
					url: ctx + "/act/task/appoint",
					success:function(rtnData){
						var data = eval('(' + rtnData + ')');
						if(data.result == 0){
							top.$.jBox.tip(data.msg);
							top.$('.tab_selected').loadData(true);
						}else{
							alertx(data.msg);
							$("#sendAppoint").val("确认");
							$("#sendAppoint").attr("disabled",false);
							return;
						}
					},
					error:function(x,h,r){
						alertx(x);
					}
				});
			});
		});
		/**
		 * 签收任务
		 */
		function claim(taskId) {
			$.get('${ctx}/act/task/claim' ,{taskId: taskId}, function(data) {
				if (data == 'true'){
		        	top.$.jBox.tip('签收完成');
		            location = '${ctx}/act/task/todo/';
				}else{
		        	alertx('签收失败');
				}
		    });
		};
		
		/**
		 * 办理任务
		 */
		function handle(taskId,taskName,taskDefKey,procInsId,procDefId,status){
			$.get('${ctx}/act/task/handle' ,{taskId: taskId,taskName:taskName,taskDefKey:taskDefKey,procInsId:procInsId,procDefId:procDefId,status:status}, function(rtnData) {
				var data = eval('(' + rtnData + ')');
				if (data.result == -1){
		        	alertx(data.msg);
				}else{
					top.addTabFmPage($("#"+ procInsId), "任务办理",true,data.userdata);
				}
		    });
		};
		
		/**
		 * 撤回任务
		 */
/* 		function recall(taskId,procInsId,procDefId,taskDefKey,initiatorUserId,title){
			
			$.get('${ctx}/act/task/recall',{taskId:taskId,procInsId:procInsId,procDefId:procDefId,taskDefKey:taskDefKey,initiatorUserId:initiatorUserId,title:title},function(data) {
				data = eval("("+data+")"); // 转成JSON对象
				if (data.result == '1'){
		        	top.$.jBox.tip('回退完成');
		            location = '${ctx}/act/task/todo/';
				}else{
		        	alertx('回退失败,错误信息' + data.msg);
				}
		    });
			//confirmx('即将回退到上一环节,继续吗？',recallFunc(procInsId,procDefId,taskDefKey,curUserId,title));
		}; */
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/act/task/todo/");
			$("#searchForm").submit();
	    	return false;
	    };
	</script>
</head>
<body>
	<sys:message content="${message}"/>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/act/task/todo/">待办任务</a></li>
		<li><a href="${ctx}/act/task/completeList/">已办任务</a></li>
		<li><a href="${ctx}/act/task/historic/">历史任务</a></li>
		<li><a href="${ctx}/act/task/process/">新建任务</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="act" action="${ctx}/act/task/todo/" method="get" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<div>
			<label>流程类型：&nbsp;</label>
			<form:select path="procDefKey" class="input-medium">
				<form:option value="" label="全部流程"/>
				<form:options items="${fns:getDictList('act_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
<!-- 			<label>业务片区：&nbsp;</label> -->
<%-- 			<form:select path="nsAreaId" class="input-medium"> --%>
<%-- 				<form:option value="" label="全部片区"/> --%>
<%-- 				<form:options items="${nsAreaList}" itemLabel="name" itemValue="id" htmlEscape="false"/> --%>
<%-- 			</form:select> --%>
			<label>创建时间：</label>
			<input id="beginDate"  name="beginDate"  type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" style="width:163px;"
				value="<fmt:formatDate value="${act.beginDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
				　--　
			<input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" style="width:163px;"
				value="<fmt:formatDate value="${act.endDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
			&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
			<input type="button" value="委托" class="btn btn-primary" href="#selectUser" data-toggle='modal'/>
		</div>
	</form:form>
	<table id="contentTable" class="table table-striped table-bordered table-condensed" style="margin-left: 5px;">
		<thead>
			<tr>
				<th style="text-align: center;"><input name="checkAll" type="checkbox" onChange="isAllCheck(this);"/></th>
				<th style="text-align: center;">创建时间</th>
				<th style="text-align: center;">片区</th>
				<th style="text-align: center;">标题</th>
				<th style="text-align: center;">当前环节</th><%--
				<th style="text-align: center;">任务内容</th> --%>
				<th style="text-align: center;">流程类型</th>
				<th style="text-align: center;">发起人</th>
<!-- 				<th style="text-align: center;">流程版本</th> -->
				<th style="text-align: center;">操作</th>
				<th style="text-align: center;">备注</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="act">
				<c:set var="task" value="${act.task}" />
				<c:set var="vars" value="${act.vars}" />
				<c:set var="procDef" value="${act.procDef}" /><%--
				<c:set var="procExecUrl" value="${act.procExecUrl}" /> --%>
				<c:set var="status" value="${act.status}" />
				<tr>
					<td><input name="checkData" type="checkbox" value="${task.processInstanceId}/${task.id}"/></td>
					<td><fmt:formatDate value="${task.createTime}" type="both"/></td>
					<td>${act.nsAreaName}</td>
					<td>
						<c:if test="${empty task.assignee}">
<%-- 							<a href="javascript:claim('${task.id}');" title="签收任务">${fns:abbr(not empty act.vars.map.title ? act.vars.map.title : task.id, 60)}</a> --%>
							${fns:abbr(not empty act.vars.map.title ? act.vars.map.title : task.id, 60)}
						</c:if>
						<c:if test="${not empty task.assignee}">
<%-- 							<a href="${ctx}/act/task/form?taskId=${task.id}&taskName=${fns:urlEncode(task.name)}&taskDefKey=${task.taskDefinitionKey}&procInsId=${task.processInstanceId}&procDefId=${task.processDefinitionId}&status=${status}">${fns:abbr(not empty vars.map.title ? vars.map.title : task.id, 60)}</a> --%>
							${fns:abbr(not empty vars.map.title ? vars.map.title : task.id, 60)}
						</c:if>
					</td>
					<td>
<%-- 						<a target="_blank" href="${pageContext.request.contextPath}/act/rest/diagram-viewer?processDefinitionId=${task.processDefinitionId}&processInstanceId=${task.processInstanceId}">${task.name}</a> --%>
						${task.name}
					</td><%--
					<td>${task.description}</td> --%>
					<td>${procDef.name}</td>
<%-- 					<td><b title='流程版本号'>V: ${procDef.version}</b></td> --%>
					<td>${act.initiatorName}</td>
					<td>
						<c:if test="${empty task.assignee}">
							<a href="javascript:claim('${task.id}');">签收任务</a>
						</c:if>
						<c:if test="${not empty task.assignee}">
<%-- 							<a href="${ctx}/act/task/form?taskId=${task.id}&taskName=${fns:urlEncode(task.name)}&taskDefKey=${task.taskDefinitionKey}&procInsId=${task.processInstanceId}&procDefId=${task.processDefinitionId}&status=${status}">任务办理</a> --%>
							<a id="${task.processInstanceId}" href="javascript:handle('${task.id}','${task.name}','${task.taskDefinitionKey}','${task.processInstanceId}','${task.processDefinitionId}','${status}');">办理</a>
<%-- 							<a type="button" href="#selectUser" data-toggle='modal' onclick="setProcInsId('${task.processInstanceId}','${task.id}');">委托</a> --%>
						</c:if>
<%-- 						<c:if test="${act.task.processVariables.get('startFlag') ne 'Y'}"> --%>
<%-- 							<a href="javascript:recall('${task.processInstanceId}');">撤回</a> --%>
<%-- 						</c:if> --%>
							<a href="${ctx}/act/task/recall?taskId=${task.id}&procInsId=${task.processInstanceId}&procDefId=${task.processDefinitionId}&taskDefKey=${task.taskDefinitionKey}&initiatorUserId=${act.initiatorUserId}&title=${vars.map.title}&comment=" onclick="return promptx('撤回任务','撤回原因',this.href);">撤回任务</a>
<%-- 							<a href="javascript:recall('${task.id}','${task.processInstanceId}','${task.processDefinitionId}','${task.taskDefinitionKey}','${act.initiatorUserId}','${vars.map.title}');">撤回</a> --%>
<%-- 						<shiro:hasPermission name="act:process:edit"> --%>
<%-- 							<c:if test="${empty task.executionId}"> --%>
<%-- 								<a href="${ctx}/act/task/deleteTask?taskId=${task.id}&reason=" onclick="return promptx('删除任务','删除原因',this.href);">删除任务</a> --%>
<%-- 							</c:if> --%>
<%-- 						</shiro:hasPermission> --%>
<%-- 						<a target="_blank" href="${pageContext.request.contextPath}/act/rest/diagram-viewer?processDefinitionId=${task.processDefinitionId}&processInstanceId=${task.processInstanceId}">跟踪(节点详情)</a> --%>
<%-- 						<a target="_blank" href="${ctx}/act/task/trace/photo/${task.processDefinitionId}/${task.executionId}">跟踪(流程图)</a> --%>
<%-- 						<a type="button" href="#historicDetail" data-toggle='modal' onclick="doDetailSearch('${task.processInstanceId}');">跟踪信息</a> --%>
							<a type="button" href="#historicDetail" data-toggle="modal"  onclick="doDetailSearch('${task.processInstanceId}')">流程详情</a>
							<c:if test="${act.initiatorUserId eq task.assignee}">
								<a href="${ctx}/act/task/deleteProcIns?procInsId=${task.processInstanceId}&reason=" onclick="return promptx('删除流程','删除原因',this.href);">删除流程</a>
							</c:if>
					</td>
					<td>${act.comment}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination"  style="border: 5px solid #fff;color: white;">${page}</div>
	<!--委托弹框 -->
	<div id="selectUser" class="modal hide fade" style="width:40%;margin-top:2%;background-color:white;height:80%;">
		<div class="modal-header" style="height:8%;">
				<div id = "hintMsg" style="width:80%">
					<h4 style="margin-top:2px;">
						<span>选择被委托人</span>
					</h4>
				</div>
				<div>
		        	<button type="button" name="btnClose" data-dismiss="modal" aria-hidden="true" class="close" style="margin-top:-2%;">&times;</button>
		        </div>
	     </div>
		<div style="height:85%;overflow: auto;">
			<input type="hidden" id="procInsId" value="">
			<input type="hidden" id="taskId" value="">
			<form:form id="inputForm" modelAttribute="userModel" class="form-horizontal">
		     	<div class="control-group">
					<label class="control-label">被委托人:</label>
					<div class="controls">
						<form:select id="name" path="name" class="input-medium"><form:option value="" label=""/><form:options items="${userList}" itemLabel="name" itemValue="id" htmlEscape="false"/></form:select>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="form-actions" style="margin-top:20%;">
					<input id="sendAppoint" class="btn btn-primary" type="submit" value="确认"/>
					<input class="btnCancel" type="button" value="返 回" onclick="closeDailog();"/>
				</div>
		     </form:form>
		</div>
	</div>
	<!--详情弹框-->
	<div id="historicDetail" class="modal hide fade" style="margin-left:-30%;width:70%;margin-top:2%;background-color:white;height:80%;overflow:hidden;">
		<div class="modal-header" style="height:8%;">
				<div style="width:80%">
					<h4 style="margin-top:2px;">
						<span>流程详情</span>
					</h4>
				</div>
				<div>
		        	<button type="button" name="btnClose" data-dismiss="modal" aria-hidden="true" class="close" style="margin-top:-2%;">&times;</button>
		        </div>
	     </div>
	     <div style="height:90%;overflow:auto;margin-left:10px;">
		    <div style="width:90%;margin-left:2%;">
		      <img id="tracePhoto"/>
		    </div>
			<div style="margin-left:2%;margin-bottom:2%;">
				<table id="detailTable"></table>
			</div>
		</div>
	</div>
	<!-- JQgrid的中文语言包 -->
	<script src="${ctxStatic}/jqGrid5.0/js/grid.locale-cn.js" type="text/ecmascript" ></script>
	<script src="${ctxStatic}/jqGrid5.0/js/jquery.jqGrid.min.js" type="text/ecmascript" ></script>
	<!-- JQgrid的中文语言包 -->
	<script src="${ctxStatic}/jqGrid5.0/js/grid.locale-cn.js" type="text/ecmascript" ></script>
	<script src="${ctxStatic}/jqGrid5.0/js/jquery.jqGrid.min.js" type="text/ecmascript" ></script>
</body>
</html>

