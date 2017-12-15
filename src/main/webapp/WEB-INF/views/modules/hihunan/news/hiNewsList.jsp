<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>新闻管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#scrapyNews").click(function(){
				var con = this;
				$(con).val("抓取中...");
				$(con).attr("disabled", true);
				$.ajax({
					async:true,
					type:"post",
					url: ctx + "/hihunan/news/hiNews/scrapyNews",
					success:function(rtnData){
						var data = eval('(' + rtnData + ')');
						if(data.result == 0){
							top.$.jBox.tip(data.msg);
						}else{
							alertx(data.msg);
							return;
						}
						$(con).val("新闻抓取");
						$(con).attr("disabled",false);
					},
					error:function(x,h,r){
						$(con).val("新闻抓取");
						$(con).attr("disabled",false);
						alertx(x);
					}
				});
			});
			
		});
	</script>
	<link href="${ctxStatic}/jqGrid5.0/css/ui.jqgrid-bootstrap.css" rel="stylesheet" type="text/css"  />
  	<style type="text/css">
  		#mainTable table{
  			border-right:1px solid #ddd;
  			border-bottom:1px solid #ddd;
  			border-left:1px solid #ddd;
  			border-top:1px solid #ddd
  		}
  		#mainTable td{
  		
  		border-bottom: 1px solid #ddd;
    	border-right: 1px solid #ddd;
  		
  		}
  		
  		#mainTableArtical table{
  			border-right:1px solid #ddd;
  			border-bottom:1px solid #ddd;
  			border-left:1px solid #ddd;
  			border-top:1px solid #ddd
  		}
  		#mainTableArtical td{
  		
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
  		.ui-jqgrid-labels{
  		border: 1px solid #DDDDDD;
  		}
  	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/hihunan/news/hiNews/">新闻列表</a></li>
		<shiro:hasPermission name="hihunan:news:hiNews:edit"><li><a style="display: none" href="${ctx}/hihunan/news/hiNews/form">新闻添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="hiNews" action="" method="post" class="breadcrumb form-search">
			<ul class="ul-form">
				<li><label>状态：</label>
					<input type="radio" id = "approval" name ="radio" value="1" />&nbsp;已上架&nbsp;&nbsp;
					<input type="radio" id = "notApproved" name ="radio" value="0" />&nbsp;未上架
				</li>
				<li><label>题头：</label>
					<form:input path="title" htmlEscape="false" maxlength="255" class="input-medium"/>
				</li>
				<li class="btns"><input class="btn btn-primary" type="button" value="查询" onclick="searchArtical();"/>
					<input id="putaway" style="display: none" class="btn btn-primary" type="button" value="上架"  onclick="fuPutaway();"/>
					<input id="soldOut"  type="button" value="下架" class="btn btn-primary" onclick="fuSoldOut();"/>
					<input id="scrapyNews" type="button" value="新闻抓取" class="btn btn-primary"/>
				</li>
				<li class="clearfix"></li>
			</ul>
		</form:form>
		<!--已审批新闻 jqGrid-->
		<div id="Approved" style="width: 98%;margin-left:5px;">
			<table id="mainTable"></table>
			<div id="page"> </div>
		</div>
		<!--未审批新闻 jqGrid-->
		<div id="unApproved" style="width: 98%;margin-left:5px;display:none;">
			<table id="mainTableArtical"></table>
			<div id="Articalpage"> </div>
		</div>
	
	<%@ include file="/WEB-INF/views/modules/hihunan/news/hiNewsFormJs.jsp"%>
	
	<!-- JQgrid的中文语言包 -->
	<script src="${ctxStatic}/jqGrid5.0/js/grid.locale-cn.js" type="text/ecmascript" ></script>
	<script src="${ctxStatic}/jqGrid5.0/js/jquery.jqGrid.min.js" type="text/ecmascript" ></script>
	<!-- JQgrid的中文语言包 -->
	<script src="${ctxStatic}/jqGrid5.0/js/grid.locale-cn.js" type="text/ecmascript" ></script>
	<script src="${ctxStatic}/jqGrid5.0/js/jquery.jqGrid.min.js" type="text/ecmascript" ></script>
</body>
</html>