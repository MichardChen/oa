<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<script type="text/javascript">

var content=new UE.ui.Editor();
$(document).ready(function(){	
	//<!--添加编辑器-->
    content = new UE.ui.Editor();
    content.render("content");
    content.ready(function () {
        //设置编辑器的内容
        //address.setContent("你好");
    })
	//接收对象实体
	var areaList = "${content}";
});

</script>