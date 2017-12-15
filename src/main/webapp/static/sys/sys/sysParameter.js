"use strict";

$("#clearUserToken").click(function(){
	var loginName = $("#loginName").val().trim();
	$.ajax({
		async:false,
		type:"post",
		data:{'loginName':loginName},
		url: ctx + "/sys/sysParameter/clearUserToken",
		success:function(rtnData){
			var data = eval('(' + rtnData + ')');
			if(data.result == 0){
				showTip(data.msg);
			}else{
				alertx(data.msg);
				return;
			}
		},
		error:function(x,h,r){
			alertx(x);
		}
	})
});

function updateParameter(keyword){
	$("#volOrMaterial").text("修改参数值");
	$("#addCompanyDailog").removeClass('hide');
	$('label.error[for]').remove();
	$.ajax({
		async:false,
		type:"post",
		data:{'keyword':keyword},
		url: ctx + "/sys/sysParameter/getByKeyword",
		success:function(rtnData){
			var data = eval('(' + rtnData + ')');
			if(data.result == 0){
				var sysParameter = data.userdata;
				$("#id").val(sysParameter.id);
				$("#keyword").val(sysParameter.keyword);
				$("#value1").val(sysParameter.value1);
				$("#value2").val(sysParameter.value2);
				$("#value3").val(sysParameter.value3);
				$("#value4").val(sysParameter.value4);
				$("#value5").val(sysParameter.value5);
				$("#value6").val(sysParameter.value6);
				$("#value7").val(sysParameter.value7);
				$("#value8").val(sysParameter.value8);
				$("#value9").val(sysParameter.value9);
				$("#value10").val(sysParameter.value10);
				$("#remarks").val(sysParameter.remarks);
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

function getPostURL(type){
    var url = ctx + "/sys/sysParameter/list";
    return url;
};
    
function doSearch(type){
    $("#mainTable").jqGrid('setGridParam',{datatype:"json",url:getPostURL(type)}).trigger("reloadGrid");
};

$(function(){
	
	$("#mainTable").jqGrid({
		url:ctx + "/sys/sysParameter/list",
		datatype:"json",
		colNames : ['id','关键字','参数1','参数2','参数3','参数4','参数5','参数6','参数7','参数8','参数9','参数10','备注','操作'],
        colModel : [ 
                     {name : 'id',key:true,index : 'id',width :25,hidden:true},               
                     {name : 'keyword',index : 'name',width :80,editable:true,edittype:'text',align:'left'},
                     {name : 'value1',index : '',width :100,editable:false,align:'left',edittype:'text'}, 
                     {name : 'value2',index : '',width :30,editable:false,align:'left',edittype:'text'}, 
                     {name : 'value3',index : '',width :10,editable:true,align:'left',edittype:'text'}, 
                     {name : 'value4',index : '',width :10,editable:true,align:'left',edittype:'text'},
                     {name : 'value5',index : '',width :10,editable:true,align:'left',edittype:'text'},
                     {name : 'value6',index : '',width :10,editable:true,align:'left',edittype:'text'},
                     {name : 'value7',index : '',width :10,editable:true,align:'left',edittype:'text'},
                     {name : 'value8',index : '',width :10,editable:true,align:'left',edittype:'text'},
                     {name : 'value9',index : '',width :10,editable:true,align:'left',edittype:'text'},
                     {name : 'value10',index : '',width :10,editable:true,align:'left',edittype:'text'},
                     {name : 'remarks',index:'',editable:true,edittype:'text',width:150,align:'left'},
   		          	 {name : 'act',index:'',width :30,sortable:false,align:'center'}
                   ],
		rownumbers:true,
		autoScroll:true,
		rowNum:15,
		rowList:[ 15, 30, 45 ],
		pager:'#page',
		height:300,
		width: $(window).width() - 140,
		autowidth: false, // 如果设为true，则Grid的宽度会根据父容器的宽度自动重算。重算仅发生在Grid初始化的阶段；
        // 如果当父容器尺寸变化了，同时也需要变化Grid的尺寸的话，则需要在自己的代码中调用setGridWidth方法来完成。  
        shrinkToFit:true, //此选项用于根据width计算每列宽度的算法。如果shrinkToFit为true且设置了width值，则每列宽度会根据width成比例缩放
		sortname:'id',
		recordpos:'left',
		viewrecords:true,
		multiselect:false,
		sortorder:'desc',
		gridComplete:function(){
			var ids = $("#mainTable").jqGrid('getDataIDs');
			for(var i = 0;i < ids.length;i++){
				var cl = ids[i];
				var rowData = $("#mainTable").jqGrid("getRowData",cl);
				var modify = "<a type='button' href='#addCompanyDailog' data-toggle='modal' onclick=\"updateParameter('" + rowData.keyword + "')\">修改</a>";
				$("#mainTable").jqGrid('setRowData',ids[i],{act:modify});
			}
		}
	});
	
	// 初始化设定窗口大小。 
	$(window).resize(function(){
		//当窗口变化时，自适应宽度。
		$("#mainTable").setGridWidth($(window).width() - 140); 
	});
	
	// 初始化设定窗口大小。 
	$(window).resize();
	
	var ajax_option={
			url:ctx + "/sys/sysParameter/edit",
			success:function(rtnData){
				var data = eval("(" + rtnData + ")");
				if(data.result == 0){//保存成功
					$("[name=btnClose]")[0].click();
					$("#addCompanyDailog").addClass("hide");
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
	};
	
	$("#inputForm").validate({
		rules: 
		{		
		},
		messages:
		{	
		},
		submitHandler: function(){
			$('#inputForm').ajaxSubmit(ajax_option);
			return false;
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
	
	/*$("#btnSearch").click(function(){//查询
		var type = $("#typeList").val();
		doSearch(type);
	});*/
})