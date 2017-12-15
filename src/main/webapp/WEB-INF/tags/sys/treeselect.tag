<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="id" type="java.lang.String" required="true" description="编号"%>
<%@ attribute name="name" type="java.lang.String" required="true" description="隐藏域名称（ID）"%>
<%@ attribute name="value" type="java.lang.String" required="true" description="隐藏域值（ID）"%>
<%@ attribute name="labelName" type="java.lang.String" required="true" description="输入框名称（Name）"%>
<%@ attribute name="labelValue" type="java.lang.String" required="true" description="输入框值（Name）"%>
<%@ attribute name="title" type="java.lang.String" required="true" description="选择框标题"%>
<%@ attribute name="url" type="java.lang.String" required="true" description="树结构数据地址"%>
<%@ attribute name="checked" type="java.lang.Boolean" required="false" description="是否显示复选框，如果不需要返回父节点，请设置notAllowSelectParent为true"%>
<%@ attribute name="extId" type="java.lang.String" required="false" description="排除掉的编号（不能选择的编号）"%>
<%@ attribute name="isAll" type="java.lang.Boolean" required="false" description="是否列出全部数据，设置true则不进行数据权限过滤（目前仅对Office有效）"%>
<%@ attribute name="notAllowSelectRoot" type="java.lang.Boolean" required="false" description="true代表不允许选择根节点"%>
<%@ attribute name="notAllowSelectParent" type="java.lang.Boolean" required="false" description="true代表不允许选择父节点"%>
<%@ attribute name="module" type="java.lang.String" required="false" description="过滤栏目模型（只显示指定模型，仅针对CMS的Category树）"%>
<%@ attribute name="selectScopeModule" type="java.lang.Boolean" required="false" description="选择范围内的模型（控制不能选择公共模型，不能选择本栏目外的模型）（仅针对CMS的Category树）"%>
<%@ attribute name="allowClear" type="java.lang.Boolean" required="false" description="是否允许清除"%>
<%@ attribute name="allowInput" type="java.lang.Boolean" required="false" description="文本框可填写"%>
<%@ attribute name="cssClass" type="java.lang.String" required="false" description="css class"%>
<%@ attribute name="cssStyle" type="java.lang.String" required="false" description="css 样式style"%>
<%@ attribute name="smallBtn" type="java.lang.Boolean" required="false" description="缩小按钮显示"%>
<%@ attribute name="hideBtn" type="java.lang.Boolean" required="false" description="是否显示按钮"%>
<%@ attribute name="disabled" type="java.lang.String" required="false" description="是否限制选择，如果限制，设置为disabled"%>
<%@ attribute name="dataMsgRequired" type="java.lang.String" required="false" description=""%>
<%@ attribute name="containerId" type="java.lang.String" required="true" description="调用画面上的控件(如公司)所在的容器id,如XXdiv,searchForm,inputForm。如果无需限定，请传入空串"%>
<div class="input-append">
	<input id="${id}Id" name="${name}" class="${cssClass}" type="hidden" value="${value}"/>
	<input id="${id}Name" name="${labelName}" ${allowInput?'':'readonly="readonly"'} type="text" value="${labelValue}" data-msg-required="${dataMsgRequired}"
		class="${cssClass}" style="${cssStyle}"/><a id="${id}Button" href="javascript:" class="btn ${disabled} ${hideBtn ? 'hide' : ''}" style="${smallBtn?'padding:4px 2px;':''}">&nbsp;<i class="icon-search"></i>&nbsp;</a>&nbsp;&nbsp;
</div>
<script type="text/javascript">
	// 根据外层的容器（如inputForm,searchForm)来设定。2016-7-2
	var containerId = "${containerId}";
	if(containerId == null || containerId == ""){
		containerId = ""; // 无需容器限定	
	}
	else{
		containerId = "#" + containerId + " "; // 需要容器限定，如serachForm,inputForm  
	}

	$(containerId+"#${id}Button," + containerId +" #${id}Name").click(function(){
		// 是否限制选择，如果限制，设置为disabled
		if ($(containerId+"#${id}Button").hasClass("disabled")){
			return true;
		}
		// 正常打开  2018-8-25 修改弹窗框 jBox。open()函数中 iframe的 滚动条属性iframeScrolling 为no
		$.jBox.open("iframe:${ctx}/tag/treeselect?url="+encodeURIComponent("${url}")+"&module=${module}&checked=${checked}&extId=${extId}&isAll=${isAll}", "选择${title}", 300, 390, {
			ajaxData:{selectIds: $(containerId+"#${id}Id").val()},buttons:{"确定":"ok", ${allowClear?"\"清除\":\"clear\", ":""}"关闭":true},iframeScrolling:'auto',submit:function(v, h, f){
				// 2016-6-30 增加对控件是否存在某容器(如form)中的判断.注意：如果画面上有2个company元素，则需要限定该元素的容器，比如div,比如form。否则可能设置到另一元素上。
				var containerId = "${containerId}";
				if(containerId == null || containerId == ""){
					containerId = ""; // 无需容器限定	
				}
				else{
					containerId = "#" + containerId + " "; // 需要容器限定，如serachForm,inputForm  
				}
				if (v=="ok"){ // 选择【确定】按钮
					var tree = h.find("iframe")[0].contentWindow.tree;//h.find("iframe").contents();
					var ids = [], names = [], nodes = [];
					if ("${checked}" == "true"){
						nodes = tree.getCheckedNodes(true);
					}else{
						nodes = tree.getSelectedNodes();
					}
					for(var i=0; i<nodes.length; i++) {//<c:if test="${checked && notAllowSelectParent}">
						if (nodes[i].isParent){
							continue; // 如果为复选框选择，则过滤掉父节点
						}//</c:if><c:if test="${notAllowSelectRoot}">
						if (nodes[i].level == 0){
							top.$.jBox.tip("不能选择根节点（"+nodes[i].name+"）请重新选择。");
							return false;
						}//</c:if><c:if test="${notAllowSelectParent}">
						if (nodes[i].isParent){
							top.$.jBox.tip("不能选择父节点（"+nodes[i].name+"）请重新选择。");
							return false;
						}//</c:if><c:if test="${not empty module && selectScopeModule}">
						if (nodes[i].module == ""){
							top.$.jBox.tip("不能选择公共模型（"+nodes[i].name+"）请重新选择。");
							return false;
						}else if (nodes[i].module != "${module}"){
							top.$.jBox.tip("不能选择当前栏目以外的栏目模型，请重新选择。");
							return false;
						}//</c:if>
						ids.push(nodes[i].id);
						names.push(nodes[i].name);//<c:if test="${!checked}">
						break; // 如果为非复选框选择，则返回第一个选择  </c:if>
					}
					$(containerId + "#${id}Id").val(ids.join(",").replace(/u_/ig,""));
					$(containerId + "#${id}Name").val(names.join(","));
				}//<c:if test="${allowClear}">
				else if (v=="clear"){ // 选择【清除】按钮
					$(containerId + "#${id}Id").val("");
					$(containerId + "#${id}Name").val("");
                }//</c:if>
                // 如果定义了回调函数，则自动执行回调函数(如 compnayTreeselectCallBack)。
				if(typeof ${id}TreeselectCallBack == 'function'){
					${id}TreeselectCallBack(v, h, f,containerId);
				}
			}, loaded:function(h){
				//$(".jbox-content",top.document).css("overflow-y","hidden");
				//删除top.document解决jbox弹框滚动条隐藏
				$(".jbox-content").css("overflow-y","hidden");
			}
		});
	});
</script>