<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<script type="text/javascript">



function addHiArtical(con){
	var id = $("#inputForm #id").val();
	if(id != ""){
		//$(con).addattr("href")
		$("#addHiArticalDailog").removeClass("hide");
	}else{
		$(con).removeAttr("href");
		alert("请先添加美食")
		return false;
	}
}



//获取所有未审批新闻
function getArticalPostURL(title,auditState){
    var url = ctx + "/hihunan/news/hiNews/getListByAuditState";
    return url + "?title=" + encodeURIComponent(encodeURIComponent(title)) +"&auditState=" + auditState;
};
//获取所有未审批新闻 
function doArticalSearch(title,auditState){
    $("#mainTableArtical").jqGrid('setGridParam',{datatype:"json",url:getArticalPostURL(title,auditState)}).trigger("reloadGrid");
};

//获取所有已审批新闻 
function getPostURL(title,auditState){
    var url = ctx + "/hihunan/news/hiNews/getListByAuditState";
    return url + "?title=" + encodeURIComponent(encodeURIComponent(title)) +"&auditState=" + auditState;
};
//获取所有已审批新闻 
function doSearch(title,auditState){
    $("#mainTable").jqGrid('setGridParam',{datatype:"json",url:getPostURL(title,auditState)}).trigger("reloadGrid");
};

//$("#mainTableArtical").clearGridData();
//文章查询过滤
function searchArtical(){
	var title = $("#searchForm #title").val();
	var auditState = $('input:radio:checked').val();
	if(auditState == '0'){
		doArticalSearch(title,auditState)
	}else if(auditState == '1'){
		doSearch(title,auditState)
	}
};


//审批 上架
function fuPutaway(){
	//获取mainTableArtical Grid中的所有文章IDS
	var ids = $("#mainTableArtical").jqGrid("getGridParam","selarrrow");
	var auditState = "1";
	var title = $("#searchForm #title").val();
	if(ids == null || ids.length == 0){
		alert("请选择新闻！");
		return;
	}
	$.ajax({
		async:false,
		type:"post",
		data:{"ids":JSON.stringify(ids),"auditState":auditState},
		url: ctx + "/hihunan/news/hiNews/updateAuditState",
		success:function(data){
				var data = eval("(" + data + ")");
				if(data.result == 0){//保存成功
					showTip(data.msg);
					doArticalSearch(title,"0")
				}else{
					alertx(data.msg);
					return;
				}
		},
		error:function(x,h,r){
			alertx(x);
		}
	})
}

//审批  下架
function fuSoldOut(){
	//获取mainTableArtical Grid中的所有文章IDS
	var ids = $("#mainTable").jqGrid("getGridParam","selarrrow");
	var auditState = "0";
	var title = $("#searchForm #title").val();
	if(ids == null || ids.length == 0){
		alert("请选择新闻！");
		return;
	}
	$.ajax({
		async:false,
		type:"post",
		data:{"ids":JSON.stringify(ids),"auditState":auditState},
		url: ctx + "/hihunan/news/hiNews/updateAuditState",
		success:function(data){
				var data = eval("(" + data + ")");
				if(data.result == 0){//保存成功
					showTip(data.msg);
					doSearch(title,"1")
				}else{
					alertx(data.msg);
					return;
				}
		},
		error:function(x,h,r){
			alertx(x);
		}
	})
}

//删除
function del(id){
	var delFunc = function(){
		$.ajax({
			async:false,
			type:"post",
			data:{'id':id},
			url: ctx + "/hihunan/restaunant/hiRestaunant/deleteHiArticalMap",
			success:function(rtnData){
				var data = eval('(' + rtnData + ')');
				if(data.result == 0){
					showTip(data.msg);
					$("#mainTable").trigger('reloadGrid');
				}else{
					alertx(data.msg);
					return;
				}
			},
			error:function(x,h,r){
				alertx(x);
			}
		})
	};
	confirmx('确认要删除吗？',delFunc);
}

$(document).ready(function(){	
	
	//所有未审批新闻信息
	$("#mainTableArtical").jqGrid({
		url:ctx + "/hihunan/news/hiNews/getListByAuditState?auditState=0",
		datatype:"json",
		colNames:['id','题头','发表日期','简介','关键字','作者'],
		colModel:[
		          {name:'id',key:true,index:'',width:25,hidden:true},
		          {name:'title',index:'title',editable:true,edittype:'text',width:100,align:'left'},
		          {name:'publishDate',index:'publish_date',editable:true,edittype:'text',width:60,align:'center'},
		          {name:'brief',index:'brief',editable:true,edittype:'text',width:60,align:'left'},
		          {name:'keyword',index:'keyword',editable:true,edittype:'text',width:40,align:'left'},
		          {name:'actor',index:'actor',editable:true,edittype:'text',width:60,align:'left'}
		          ],
		rownumbers:true,
		autoScroll:true,
		rowNum:30,
		rowList:[ 30, 60, 90 ],
		pager:'#Articalpage',
		height:320,
		width: $(window).width() - 120, //宽度
		autowidth: false, // 如果设为true，则Grid的宽度会根据父容器的宽度自动重算。重算仅发生在Grid初始化的阶段；
		                  // 如果当父容器尺寸变化了，同时也需要变化Grid的尺寸的话，则需要在自己的代码中调用setGridWidth方法来完成。  
		shrinkToFit:true, //此选项用于根据width计算每列宽度的算法。如果shrinkToFit为true且设置了width值，则每列宽度会根据width成比例缩放
		sortname:'id',
		recordpos:'left',
		viewrecords:true,
		multiselect:true,
		sortorder:'desc',
		gridComplete:function(){
			var ids = $("#mainTableArtical").jqGrid('getDataIDs');
			for(var i = 0;i < ids.length;i++){
				var cl = ids[i];
			}
		}
	});
	
	//所有已审批新闻信息
	$("#mainTable").jqGrid({
		url:ctx + "/hihunan/news/hiNews/getListByAuditState?auditState=1",
		datatype:"json",
		colNames:['id','题头','发表日期','简介','关键字','作者'],
		colModel:[
		          {name:'id',key:true,index:'',width:25,hidden:true},
		          {name:'title',index:'title',editable:true,edittype:'text',width:100,align:'left'},
		          {name:'publishDate',index:'publish_date',editable:true,edittype:'text',width:60,align:'center'},
		          {name:'brief',index:'brief',editable:true,edittype:'text',width:60,align:'left'},
		          {name:'keyword',index:'keyword',editable:true,edittype:'text',width:40,align:'left'},
		          {name:'actor',index:'actor',editable:true,edittype:'text',width:60,align:'left'}
		          ],
		rownumbers:true,
		autoScroll:true,
		rowNum:30,
		rowList:[ 30, 60, 90 ],
		pager:'#page',
		height:320,
		width: $(window).width() - 120, //宽度
		autowidth: false, // 如果设为true，则Grid的宽度会根据父容器的宽度自动重算。重算仅发生在Grid初始化的阶段；
		                  // 如果当父容器尺寸变化了，同时也需要变化Grid的尺寸的话，则需要在自己的代码中调用setGridWidth方法来完成。  
		shrinkToFit:true, //此选项用于根据width计算每列宽度的算法。如果shrinkToFit为true且设置了width值，则每列宽度会根据width成比例缩放
		sortname:'id',
		recordpos:'left',
		viewrecords:true,
		multiselect:true,
		sortorder:'desc',
		gridComplete:function(){
			var ids = $("#mainTable").jqGrid('getDataIDs');
			for(var i = 0;i < ids.length;i++){
				var cl = ids[i];
			}
		}
	});

	$('input:radio[name="radio"]').change(function(){
		var con = $('input:radio:checked').val();
		var title = $("#searchForm #title").val();
		if(con == '0'){
			document.getElementById("unApproved").style.display = 'block';
			document.getElementById("Approved").style.display = 'none';
			$("#putaway").show();
	        $("#soldOut").hide();
	        doArticalSearch(title,con)
		}else if(con == '1'){
			document.getElementById("Approved").style.display = 'block';
			document.getElementById("unApproved").style.display = 'none';
			$("#soldOut").show();
	        $("#putaway").hide();
	        doSearch(title,con)
		}
	});
	
	// 初始化设定窗口大小。 
	$(window).resize(function(){
		//当窗口变化时，自适应宽度。
		$("#mainTable").setGridWidth($(window).width() - 140); 
	});
	// 强制触发窗口自适应。
	$(window).resize();
	
	
});

</script>