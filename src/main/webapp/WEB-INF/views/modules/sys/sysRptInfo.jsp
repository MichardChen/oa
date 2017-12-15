<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <%@include file="/WEB-INF/views/include/commonCss.jsp"%>
  <!--(步骤3)本页面专用的式样开始 -->		
</head>
<body>
	<div style="width: 100%;height: 60px;margin-left: 25px;">
	    <div style="height:20px;"> <!-- 增加div，增大查询条件处的top间距 -->
	    </div>
	    
	   <form:form id="searchForm" modelAttribute="sysRptInfoModel"  action="" method="post" >
	            <div style="float:left;width:25%;">        
					<label  for="rptName">报表名称:</label>
					<form:input path="rptName" />
				</div>
				
				<div style="float:left;width:60%;">
				
					 <div style="text-align:center;float:left;width:10%;" >
						<a type="button" id="btnSearch" class ="fa fa-search fa-lg" title="查询" ></a>
					 	<div style="font-size:12px;">查询</div>
					 </div>
					 
					 <div style="text-align:center;float:left;width:10%;">
						<a type="button" id="excelImport" data-toggle='modal' class="fa fa-hand-pointer-o fa-lg" title="报表上传"></a>
						<div style="font-size:12px;">上传报表</div>
					</div>
					
					 <div style="text-align:center;float:left;width:10%;" >
					 	<a type="button" id="btnSave" class ="fa fa-save fa-lg" title="保存" ></a>
						<div style="font-size:12px;">保存</div>
					 </div>
					 
					 <div style="text-align:center;float:left;width:10%;">
						<a type="button" id="delData" class="fa fa-trash fa-lg" title="删除数据" ></a>
					 	<div style="font-size:12px;">删除</div>
					 </div>
					 
					 <div style="text-align:center;float:left;width:10%;">
					 	<a type="button" id="addRow" class="fa fa-plus fa-lg" title="新增" ></a>
					 	<div style="font-size:12px;">新增</div>
					 </div>
					 
				</div>
		</form:form>
	
		<div style="margin-left: 2%;margin-top: 3%;">
			<table id="rptInfoTable"></table>
			<!-- <div id="page"></div> -->
		</div>		
	<!--Excel导入清单的弹窗  -->
		 <div id="excelImpFormDailog" class="modal hide fade" style="width:50%;margin-left:50px;margin-top:5px;background-color:white;height:80%;">
			<div class="modal-header" style="height:40px;">
				<div id = "hintMsg" style="width:30%">
				</div>
				<div style="margin-top:2px;">
		        	<button type="button" name="btnClose" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
		        </div>
	     	</div>
			 <div class="form-group" style="float:left;">
				 <form id="importExcelForm" enctype="multipart/form-data" class="form-horizontal">
					 <input id="uploadFile" name="file" type="file" accept="*.xls,"/>
					
					 <div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<input id="btnSubmit" class="btn btn-primary" type="submit"
								value="确定" />&nbsp;
							 <input id="btnCancel" class="btn"
								type="button" value="返 回" onclick="history.go(-1)" />
						</div>
					 </div>
				 </form>
		  	</div>
		  	
		</div>
	</div>
</body>
<%@include file="/WEB-INF/views/include/commonJs.jsp"%>
<!--(步骤2)页面逻辑 开始 --> 
<%@include file="/WEB-INF/views/modules/sys/sysRptInfoJs.jsp"%>
</html>