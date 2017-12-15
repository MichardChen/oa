<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>概要管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		
	   function checkURL(URL){
			var str=URL;
			//判断URL地址的正则表达式为:http(s)?://([\w-]+\.)+[\w-]+(/[\w- ./?%&=]*)?
			//下面的代码中应用了转义字符"\"输出一个字符"/"
			var Expression=/http(s)?:\/\/([\w-]+\.)+[\w-]+(\/[\w- .\/?%&=]*)?/;
			var objExp=new RegExp(Expression);
			if(objExp.test(str)==true){
				return true;
			}else{
				return false;
			}
		} 
	
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				rules: {
					type:{
				       required:true
					},
					title:{
					   required:true
					},
					titlePhoto:{
					  required:true
					},
					address:{
				      required:true
					}
				},
				messages: {
					type:{
						required:"分类不能为空"
					},
					title:{
						required:"标题不能为空"
					},
					titlePhoto:{
						required:"请选择图片"
					},
					address:{
						required:"请输入地址"
					}
				},
				submitHandler: function(form){
					//如果选择外部链接的方式，则判断外部链接是否输入。
				    var sourceFrom =  $('#inputForm input[name="sourceFrom"]:checked').val();
					if(sourceFrom=="2"){
						//检查url是否输入。
						var url = $("#inputForm #url").val();
						if(url==null || url==""){
							alert("请输入外部链接的URL");
							$("#inputForm #url").foucs();
							return false;
						}
						if(!checkURL(url)){
							alert("外部链接URL不合法！");
							$("#inputForm #url").foucs();
							return false;
						}
					}
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
			
			//对于文章来源的radiobutton
			$("input[name='sourceFrom']").click(function(){
				var sourceFrom = $("input[name='sourceFrom']:checked").val();
				if(sourceFrom=="1"){
					// 添加文章
					$("#btnAddArtical").show();
					$("#relArtical").css("display","block");
					$("#aritcalList").css("display","block");
				}
				if(sourceFrom=="2"){
					// 外部链接
					$("#btnAddArtical").hide();
					$("#relArtical").css("display","none");
					$("#aritcalList").css("display","none");
				}
			});
			
			/* // 修改模式下，设置[分类]
			<c:if test="${not empty hiListShow.type}">
			    $("#type").select2().val('${hiListShow.type}');
			</c:if>
			//如果参数【类别】存在(通常从主题的一览界面过来)，则设到画面上
			
			// 控制[增加文章]的按钮显示有否
			var hiListShowId  = "${hiListShow.id}";
			var articalExists = '0'; //默认没有关联文章
			
			<c:if test="${articalList!=null}">
			    articalExists = '1'; //如果有关联到文章，则设标志。
			</c:if>
			// 如果修改状态并且没有关联到文章，才显示[添加文章]按钮
			if(hiListShowId != null && hiListShowId != "" && articalExists=='0'){
				$("#btnAddArtical").css("display","inline");
			}
			// 添加文章的链接
			$("#btnAddArtical").click(function(){
				var name = "概要添加";
				var urlparam = "${ctx}/hihunan/artical/hiArtical/form?parentId=" + hiListShowId; 
				// 跳转到新建页面
				top.addTabFmPage($("#addArticalLink"),name,true,urlparam);
			}); */
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
  		
  	</style>
	
</head>
<body>
	<ul class="nav nav-tabs">
		<li style=""><a href="${ctx}/hihunan/listshow/hiListShow/">概要列表</a> <%-- <a id="addArticalLink" href="${ctx}/hihunan/listshow/hiListShow/">概要列表</a></li> --%>
		<li class="active"><a href="${ctx}/hihunan/listshow/hiListShow/form?id=${hiListShow.id}">概要<shiro:hasPermission name="hihunan:listshow:hiListShow:edit">${not empty hiListShow.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="hihunan:listshow:hiListShow:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<sys:message content="${message}"/>
	<form:form id="inputForm" modelAttribute="hiListShow" action="${ctx}/hihunan/listshow/hiListShow/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<div class="control-group">
			<label class="control-label">分类：</label>
			<div class="controls">
				<%-- <form:input path="type" htmlEscape="false" maxlength="20" class="input-xlarge "/> --%>
				<%-- <form:select path="type" class="input-xlarge">
					<form:option value="" label="请选择" />
					<form:options items="${listType}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select> --%>
				<form:select path="type" class="input-xlarge ">
					<form:option value="" label="">
					<form:options items="${fns:getDictList('hi_list_type')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:option>
				</form:select> 
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>	
		<div class="control-group">
			<label class="control-label">题头：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">图片：</label>
			<div class="controls">
				<form:hidden id="nameImage" path="titlePhoto" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:ckfinder input="nameImage" type="images" uploadPath="/photo" selectMultiple="false" maxWidth="100" maxHeight="100"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">地名：</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				<span class="help-inline"><font color="red">*</font> </span>
				<!-- <script  id="address" type="text/plain" name="ServiceProcess" style="height:350px;width:590px"></script> -->
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">类型：</label>
			<div class="controls">
			    <form:radiobutton path="sourceFrom" value="1" checked="true"/>添加文章
				<form:radiobutton path="sourceFrom" value="2"/>引用外部链接
				<span class="help-inline"><font color="red">*</font> </span>
				<!-- <script  id="address" type="text/plain" name="ServiceProcess" style="height:350px;width:590px"></script> -->
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">URL：</label>
			<div class="controls">
				<form:input path="url" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				<!-- <script  id="address" type="text/plain" name="ServiceProcess" style="height:350px;width:590px"></script> -->
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="hihunan:listshow:hiListShow:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
		<!-- 	<input id="btnAddArtical" style="display:none;" class="btn btn-primary" type="button" value="增加文章"/> -->
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>&nbsp;&nbsp;
			<input id="btnAddArtical" type="button" value="添加文章 " class="btn btn-primary" href="#addHiArticalDailog" data-toggle='modal' onclick="addHiArtical(this);return false;"/>
		</div>
	</form:form>
	<!--以关联的文章jqGrid-->
	<div id="relArtical"><h4>关联文章</h4></div>
	<div id="aritcalList" style="width: 98%;margin-left:5px;">
		<table id="mainTable"></table>
		<div id="page"> </div>
	</div>
	<!--添加文章弹框 -->
	<div id="addHiArticalDailog" class="modal hide fade" style="width:90%;margin-left:-45%;margin-top:-2%;background-color:white;height:90%;overflow:auto;">
		<div class="modal-header" style="height:4%;margin-top: 10px;">
				<div id = "hintMsg" style="width:80%">
					<h4 style="margin-top:2px;">
						<span id = "volOrMaterial">
						添加文章
						</span>
					</h4>
				</div>
				<div>
		        	<button type="button" name="btnClose" data-dismiss="modal" aria-hidden="true" class="close" style="margin-top:-2%;">&times;</button>
		        </div>
	     </div>
		<form:form id="searchForm" modelAttribute="hiListShow" action="" method="post" class="breadcrumb form-search">
			<ul class="ul-form">
				<li><label>题头：</label>
					<form:input path="title" htmlEscape="false" maxlength="255" class="input-medium"/>
				</li>
				<li><label>类型：</label>
					<form:select path="type" class="input-xlarge ">
						<form:option value="" label="">
						<form:options items="${fns:getDictList('hi_list_type')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:option>
				</form:select> 
				<li class="btns"><input class="btn btn-primary" type="button" value="查询" onclick="searchArtical();" /></li>
				<li class="clearfix"></li>
			</ul>
		</form:form>
	     <div style="width: 98%;margin-left:5px;">
			<table id="mainTableArtical"></table>
			<div id="pageArtical"> </div>
		</div>
	     
		<div style="text-align: center;margin-top: 20px;">
			<input  class="btn btn-primary" type="button" value="保存" onclick="saveMap(this);"/>
			<input  class="btn" type="button" value="返 回" onclick="closeDailog();" style="border: 1px solid #969696;"/>
		</div> 
	</div>
	<%-- <!-- 文章列表 -->
	<c:if test="${articalList!=null}">
		<table id="contentTable" class="table table-striped table-bordered table-condensed" style="width:100%;">
			<thead>
				<tr>
					<th>文章标题</th>
					<th>更新日期</th>
					<shiro:hasPermission name="hihunan:listshow:hiListShow:edit"><th>操作</th></shiro:hasPermission>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${articalList}" var="hiArtical">
				<tr>
					<td><a href="${ctx}/hihunan/artical/hiArtical/form?id=${hiArtical.id}">
						${hiArtical.title}
					</a></td>
					<td>
						<fmt:formatDate value="${hiArtical.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<shiro:hasPermission name="hihunan:artical:hiArtical:edit"><td>
	    				<a href="${ctx}/hihunan/artical/hiArtical/form?id=${hiArtical.id}">修改</a>
						<a href="${ctx}/hihunan/artical/hiArtical/delete?id=${hiArtical.id}" onclick="return confirmx('确认要删除该artical吗？', this.href)">删除</a>
					</td></shiro:hasPermission>
			</tr>
			</c:forEach>
			</tbody>
		</table>
	</c:if> --%>
	
	<%@ include file="/WEB-INF/views/modules/hihunan/listshow/hiListShowFormJs.jsp"%> 
	
	<!-- JQgrid的中文语言包 -->
	<script src="${ctxStatic}/jqGrid5.0/js/grid.locale-cn.js" type="text/ecmascript" ></script>
	<script src="${ctxStatic}/jqGrid5.0/js/jquery.jqGrid.min.js" type="text/ecmascript" ></script>
	<!-- JQgrid的中文语言包 -->
	<script src="${ctxStatic}/jqGrid5.0/js/grid.locale-cn.js" type="text/ecmascript" ></script>
	<script src="${ctxStatic}/jqGrid5.0/js/jquery.jqGrid.min.js" type="text/ecmascript" ></script>
</body>
</html>