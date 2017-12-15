<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>文章管理</title>
	<meta name="decorator" content="default"/>
	 
	<script type="text/javascript">
	
		function noEscapeHtml(html) {  
	        return html.replace(/(\&|\&)gt;/g, ">")  
	                    .replace(/(\&|\&)lt;/g, "<")  
	                    .replace(/(\&|\&)quot;/g, "\"");  
	    }  
	
		//转意符换成普通字符
		function escape2Html(str) {
			 var arrEntities={'lt':'<','gt':'>','nbsp':' ','amp':'&','quot':'"'};
			 return str.replace(/&(lt|gt|nbsp|amp|quot);/ig,function(all,t){return arrEntities[t];});
		}
		
	    var ue;
		
	    $(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					var content = ue.getContent();
					$("#inputForm #content").val(content);
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
			
			//实例化编辑器
		    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
		    ue = UE.getEditor('editor');
			// 画面打开时，如果是从修改界面过来，则设置到富文本框。
		    
		    // 当加载完毕后，显示内容。
		    ue.ready(function(){
				ue.setContent(escape2Html(noEscapeHtml($("#contentTmp").html())));
		    }); 
		});
	</script>
</head>
<body>
    <!--富文本框的加载-->
	<script type="text/javascript">
	    <!--注意：这里格式为   /项目名/static/ueditor/ -->
	    window.UEDITOR_HOME_URL = "/hihunan/static/ueditor/";
	</script>
	<script type="text/javascript" charset="utf-8" src="${ctxStatic}/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${ctxStatic}/ueditor/ueditor.all.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="${ctxStatic}/ueditor/lang/zh-cn/zh-cn.js"></script>
    
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/hihunan/artical/hiArtical/">文章列表</a></li>
		<li class="active"><a href="${ctx}/hihunan/artical/hiArtical/form?id=${hiArtical.id}">文章<shiro:hasPermission name="hihunan:artical:hiArtical:edit">${not empty hiArtical.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="hihunan:artical:hiArtical:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="hiArtical" action="${ctx}/hihunan/artical/hiArtical/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="parentId"/>
		<div id="contentTmp" style="display:none;">
			${hiArtical.content}
		</div>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">题头：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="255" class="required input-xlarge"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">题头图片：</label>
			<div class="controls">
				<form:hidden id="nameImage" path="titlePhoto" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:ckfinder input="nameImage" type="images" uploadPath="/photo" selectMultiple="false" maxWidth="100" maxHeight="100"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发表日期：</label>
			<div class="controls">
				<input name="publishDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${hiArtical.publishDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">简介：</label>
			<div class="controls">
				<form:input path="brief" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">关键字：</label>
			<div class="controls">
				<form:input path="keyword" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">作者：</label>
			<div class="controls">
				<form:input path="actor" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">文章类型：</label>
			<div class="controls">
				<%-- <form:input path="articalType" htmlEscape="false" maxlength="20" class="input-xlarge "/> --%>
				<form:select path="articalType" class="required input-medium">
				   <form:option value="" label="请选择" />
				   <form:options items="${listType}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
				<span class="help-inline">如美食，旅游等 </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">明细：</label>
			<script id="editor" type="text/plain" style="height:400px;"></script>
			<form:input path="content" htmlEscape="true" style="display:none;"/>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="hihunan:artical:hiArtical:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>