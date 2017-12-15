<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>运行中的流程</title>
	<style>
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
	
	</style>
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
		$(document).ready(function(){
			top.$.jBox.tip.mess = null;
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
				          {name:'durationTime',index:'',editable:false,edittype:'text',width:50,align:'left'},
				          {name:'comment',index:'',editable:false,edittype:'text',width:40,align:'left'}
				          ],
				rownumbers:false,
				autoScroll:true,
				height:180,
				width:$("#historicDetail").width()*0.9,
				autowidth: false, // 如果设为true，则Grid的宽度会根据父容器的宽度自动重算。重算仅发生在Grid初始化的阶段；
		        // 如果当父容器尺寸变化了，同时也需要变化Grid的尺寸的话，则需要在自己的代码中调用setGridWidth方法来完成。  
				shrinkToFit:true, //此选项用于根据width计算每列宽度的算法。如果shrinkToFit为true且设置了width值，则每列宽度会根据width成比例缩放
				sortname:'id',
				recordpos:'left',
				viewrecords:true,
				multiselect:false,
				sortorder:'desc'
			});
		});
// 		function page(n,s){
//         	location = '${ctx}/act/process/running/?pageNo='+n+'&pageSize='+s;
//         }
		function updateCategory(id, category){
			$.jBox($("#categoryBox").html(), {title:"设置分类", buttons:{"关闭":true}, submit: function(){}});
			$("#categoryBoxId").val(id);
			$("#categoryBoxCategory").val(category);
		}
		function getDetailPostURL(procInsId){
		    var url = ctx + "/inspection/faultInq/historicDetail";
		    return url + "?procInsId=" + procInsId;
		};
		    
		function doDetailSearch(procInsId){
			$("#historicDetail").removeClass('hide');
			
			//重设JqGrid的宽度。(需要在检索之前)
			$("#detailTable").setGridWidth($("#historicDetail").width()*0.9);
			
			$("#detailTable").jqGrid("clearGridData");
		    
			// 检索流转信息
		    $("#detailTable").jqGrid('setGridParam',{datatype:"json",url:getDetailPostURL(procInsId)}).trigger("reloadGrid");
		    // 设置流程图片
		    $("#tracePhoto").attr('src',ctx + "/inspection/faultInq/tracePhoto?procInsId=" + procInsId + "&v=" + Math.random());
		};
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/act/process/running/");
			$("#searchForm").submit();
	    	return false;
	    };
	</script>
	<script type="text/template" id="categoryBox">
		<form id="categoryForm" action="${ctx}/act/process/updateCategory" method="post" enctype="multipart/form-data"
			style="text-align:center;" class="form-search" onsubmit="loading('正在设置，请稍等...');"><br/>
			<input id="categoryBoxId" type="hidden" name="procDefId" value="" />
			<select id="categoryBoxCategory" name="category">
				<c:forEach items="${fns:getDictList('act_category')}" var="dict">
					<option value="${dict.value}">${dict.label}</option>
				</c:forEach>
			</select>
			<br/><br/>　　
			<input id="categorySubmit" class="btn btn-primary" type="submit" value="   保    存   "/>　　
		</form>
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/act/process/">流程管理</a></li>
		<li><a href="${ctx}/act/process/deploy/">部署流程</a></li>
		<li class="active"><a href="${ctx}/act/process/running/">运行中的流程</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="act" action="${ctx}/act/process/running/" method="get" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<div>
			<label>流程类型：&nbsp;</label>
			<form:select path="procDefKey" class="input-medium">
				<form:option value="" label="全部流程"/>
				<form:options items="${fns:getDictList('act_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
			&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
		</div>
	</form:form>
	<sys:message content="${message}"/>
	<table class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
<!-- 				<th>执行ID</th> -->
<!-- 				<th>流程实例ID</th> -->
<!-- 				<th>流程定义ID</th> -->
<!-- 				<th>当前环节</th> -->
<!-- 				<th>是否挂起</th> -->
				<th>发起时间</th>
				<th>发起人</th>
				<th>流程类型</th>
				<th>当前环节</th>
				<th>当前处理人</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="procIns">
				<tr>
					<td>${procIns.startTime}</td>
					<td>${procIns.startUser}</td>
					<td>${procIns.actType}</td>
					<td>${procIns.activityName}</td>
<%-- 					<td>${procIns.id}</td> --%>
<%-- 					<td>${procIns.processInstanceId}</td> --%>
<%-- 					<td>${procIns.processDefinitionId}</td> --%>
<%-- 					<td>${procIns.activityId}</td> --%>
<%-- 					<td>${procIns.suspended}</td> --%>
					<td>${procIns.activityUser}</td>
					<td>
						<shiro:hasPermission name="act:process:edit">
							<a type="button" href="#historicDetail" data-toggle="modal"  onclick="doDetailSearch('${procIns.procInsId}')">流程详情</a>
							<a href="${ctx}/act/process/deleteProcIns?procInsId=${procIns.procInsId}&reason=" onclick="return promptx('删除流程','删除原因',this.href);">删除流程</a>
						</shiro:hasPermission>&nbsp;
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination"  style="border: 5px solid #fff;color: white;">${page}</div>
<%-- 	<div class="pagination" style="border: 5px solid #fff;color: white;">${page}</div> --%>
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
</body>
</html>
