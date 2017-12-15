<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<script type="text/javascript">

//全局引用ID
var referenceId;
function closeDailog(){
	$("[name=btnClose]").click();
}

//清空操作
function clearUpForm() {
	//清空grid内容
	$("#mainTableArtical").jqGrid("clearGridData");
}

function addHiArtical(con){
	var id = $("#inputForm #id").val();
	if(id != ""){
		//$(con).addattr("href")
		$("#addHiArticalDailog").removeClass("hide");
		//清空
		$("#searchForm #title").val("");
		$("#searchForm #type").select2('val',''); 
	}else{
		$(con).removeAttr("href");
		alert("请先添加旅游教育文化")
		return false;
	}
}

//获取旅游教育文化关联的文章
function getPostURL(referenceId){
    var url = ctx + "/hihunan/restaunant/hiRestaunant/getListByReferenceId";
    return url + "?referenceId=" + referenceId;
};
//获取旅游教育文化关联所有文章  
function doSearch(referenceId){
    $("#mainTable").jqGrid('setGridParam',{datatype:"json",url:getPostURL(referenceId)}).trigger("reloadGrid");
};

//获取所有文章
function getArticalPostURL(title,articalType){
    var url = ctx + "/hihunan/restaunant/hiRestaunant/getList";
    return url + "?title=" + encodeURIComponent(encodeURIComponent(title)) + "&articalType=" + articalType;
};
//获取所有文章 
function doArticalSearch(title,articalType){
    $("#mainTableArtical").jqGrid('setGridParam',{datatype:"json",url:getArticalPostURL(title,articalType)}).trigger("reloadGrid");
};


//文章查询过滤
function searchArtical(){
	var title = $("#searchForm #title").val();
	var articalType = $("#searchForm #type").val();
	$("#mainTableArtical").clearGridData();
	doArticalSearch(title,articalType)
};


//保存提交
function saveMap(con){
	con.value = '提交中';
	con.disabled = true;
	//获取mainTableArtical Grid中的所有文章IDS
	var ids = $("#mainTableArtical").jqGrid("getGridParam","selarrrow");
	//获取已关联的文章id 如果有则不能添加
	var relevanceId = $("#mainTable").jqGrid('getDataIDs');
	if( relevanceId.length != 0){
		alert("已有关联文章，请删除再操作！");
		con.value = '保存';
		con.disabled = false;
		return;
	}
	if(ids == null || ids.length == 0){
		alert("请选择要关联文章！");
		con.value = '保存';
		con.disabled = false;
		return;
	}
	if( ids.length > 1){
		alert("请选择要关联文章！");
		con.value = '保存';
		con.disabled = false;
		return;
	}
	$.ajax({
		async:true,
		type:"post",
		data:{"referenceId":referenceId,"articalId":JSON.stringify(ids)},
		url: ctx + "/hihunan/restaunant/hiRestaunant/addHiArticalMap",
		success:function(data){
				con.value = '保存';
				con.disabled = false;
				var data = eval("(" + data + ")");
				if(data.result == 0){//保存成功
					showTip(data.msg);
					referenceId = data.userdata;
					doSearch(referenceId);
					closeDailog();
					clearUpForm();
				}else{
					alertx(data.msg);
					return;
				}
		},
		error:function(x,h,r){
			con.value = '保存';
			con.disabled = false;
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

function beforeSelectRow()  
{  
    $("#jqgridId").jqGrid('resetSelection');  
    return(true);  
}

$(document).ready(function(){	
	//旅游教育文化的ID
	referenceId = "${hiListShow.id}";
	if(referenceId == null || referenceId==""){
		// 新增情况下
		$("#mainTable").jqGrid({
			url:ctx + "/hihunan/restaunant/hiRestaunant/getListByReferenceId?referenceId=null",
			datatype:"local",
			colNames:['id','题头','发表日期','简介','关键字','作者','操作'],
			colModel:[
			          {name:'id',key:true,index:'',width:25,hidden:true},
			          {name:'title',index:'',editable:true,edittype:'text',width:60,align:'left'},
			          {name:'publishDate',index:'publish_date',editable:true,edittype:'text',width:60,align:'left'},
			          {name:'brief',index:'brief',editable:true,edittype:'text',width:60,align:'left'},
			          {name:'keyword',index:'keyword',editable:true,edittype:'text',width:60,align:'left'},
			          {name:'actor',index:'actor',editable:true,edittype:'text',width:60,align:'left'},
			          {name:'act',index:'',sortable:false,width:60,align:'center'}
			          ],
			rownumbers:true,
			autoScroll:true,
			rowNum:10,
			rowList:[ 10, 20, 30 ],
			pager:'#page',
			height:130,
			width: $(window).width() - 140, //宽度
			autowidth: false, // 如果设为true，则Grid的宽度会根据父容器的宽度自动重算。重算仅发生在Grid初始化的阶段；
			                  // 如果当父容器尺寸变化了，同时也需要变化Grid的尺寸的话，则需要在自己的代码中调用setGridWidth方法来完成。  
			shrinkToFit:true, //此选项用于根据width计算每列宽度的算法。如果shrinkToFit为true且设置了width值，则每列宽度会根据width成比例缩放
			sortname:'id',
			recordpos:'left',
			viewrecords:true,
			//multiselect:true,
			sortorder:'desc',
			gridComplete:function(){
				var ids = $("#mainTable").jqGrid('getDataIDs');
				for(var i = 0;i < ids.length;i++){
					var cl = ids[i];
					var del = "<a type='button' style='margin-left:15%;' onclick=\"del('" + cl + "')\">删除</a>";
					$("#mainTable").jqGrid('setRowData',ids[i],{act:del});
				}
			}
		});
	}
	else{
		// 概要修改的情况下
		$("#mainTable").jqGrid({
			url:ctx + "/hihunan/restaunant/hiRestaunant/getListByReferenceId?referenceId=" + referenceId,
			datatype:"json",
			colNames:['id','题头','发表日期','简介','关键字','作者','操作'],
			colModel:[
			          {name:'id',key:true,index:'',width:25,hidden:true},
			          {name:'title',index:'',editable:true,edittype:'text',width:60,align:'left'},
			          {name:'publishDate',index:'publish_date',editable:true,edittype:'text',width:60,align:'left'},
			          {name:'brief',index:'brief',editable:true,edittype:'text',width:60,align:'left'},
			          {name:'keyword',index:'keyword',editable:true,edittype:'text',width:60,align:'left'},
			          {name:'actor',index:'actor',editable:true,edittype:'text',width:60,align:'left'},
			          {name:'act',index:'',sortable:false,width:60,align:'center'}
			          ],
			rownumbers:true,
			autoScroll:true,
			rowNum:10,
			rowList:[ 10, 20, 30 ],
			pager:'#page',
			height:130,
			width: $(window).width() - 140, //宽度
			autowidth: false, // 如果设为true，则Grid的宽度会根据父容器的宽度自动重算。重算仅发生在Grid初始化的阶段；
			                  // 如果当父容器尺寸变化了，同时也需要变化Grid的尺寸的话，则需要在自己的代码中调用setGridWidth方法来完成。  
			shrinkToFit:true, //此选项用于根据width计算每列宽度的算法。如果shrinkToFit为true且设置了width值，则每列宽度会根据width成比例缩放
			sortname:'id',
			recordpos:'left',
			viewrecords:true,
			//multiselect:true,
			sortorder:'desc',
			gridComplete:function(){
				var ids = $("#mainTable").jqGrid('getDataIDs');
				for(var i = 0;i < ids.length;i++){
					var cl = ids[i];
					var del = "<a type='button' style='margin-left:15%;' onclick=\"del('" + cl + "')\">删除</a>";
					$("#mainTable").jqGrid('setRowData',ids[i],{act:del});
				}
			}
		});	
		
	}
	
	//所有文章信息
	$("#mainTableArtical").jqGrid({
		url:ctx + "/hihunan/restaunant/hiRestaunant/getList",
		datatype:"json",
		colNames:['id','题头','发表日期','简介','关键字','作者','类型'],
		colModel:[
		          {name:'id',key:true,index:'',width:25,hidden:true},
		          {name:'title',index:'title',editable:true,edittype:'text',width:60,align:'left'},
		          {name:'publishDate',index:'publish_date',editable:true,edittype:'text',width:60,align:'left'},
		          {name:'brief',index:'brief',editable:true,edittype:'text',width:60,align:'left'},
		          {name:'keyword',index:'keyword',editable:true,edittype:'text',width:60,align:'left'},
		          {name:'actor',index:'actor',editable:true,edittype:'text',width:60,align:'left'},
		          {name:'articalType',index:'artical_type',editable:true,edittype:'text',width:60,align:'left'}
		          ],
		rownumbers:true,
		autoScroll:true,
		rowNum:10,
		rowList:[ 10, 20, 30 ],
		pager:'#pageArtical',
		height:300,
		width: $(window).width() - 140, //宽度
		autowidth: false, // 如果设为true，则Grid的宽度会根据父容器的宽度自动重算。重算仅发生在Grid初始化的阶段；
		                  // 如果当父容器尺寸变化了，同时也需要变化Grid的尺寸的话，则需要在自己的代码中调用setGridWidth方法来完成。  
		shrinkToFit:true, //此选项用于根据width计算每列宽度的算法。如果shrinkToFit为true且设置了width值，则每列宽度会根据width成比例缩放
		sortname:'id',
		recordpos:'left',
		viewrecords:true,
		multiselect:true, 
		multiboxonly:true,  
		beforeSelectRow: beforeSelectRow,//js方法 
		sortorder:'desc',
		gridComplete:function(){
			var ids = $("#mainTableArtical").jqGrid('getDataIDs');
			for(var i = 0;i < ids.length;i++){
				var cl = ids[i];
			}
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