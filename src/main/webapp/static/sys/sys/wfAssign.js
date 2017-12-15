
	"use strict";
	
	var findFlag = '1';// 查阅标志
	
    $(function(){
		mainTableInit();
	});
    
    //点击"流程人员配置"，
	function viewDtl(procDefId,processDefName){
	   
	   $("#configDailog").removeClass("hide");
	   //重设JqGrid的宽度。(需要在检索之前)
	   $("#wfAssign").setGridWidth($("#configDailog").width()*0.95);
	   $("#relationcostItemList").html("配置流程人员[" + processDefName + "]");
	   var url = ctx + "/sys/wfAssign/searchListWfAssign?procDefId=" + procDefId;
	   $("#wfAssign").jqGrid('setGridParam',{datatype:"json",url:url}).trigger("reloadGrid");
       
       return true;
	}
	
    function mainTableInit(){
    	// var lastsel; 2016-1-1 del.
    	
		$("#mainTable").jqGrid({
			url : ctx + "/sys/wfAssign/searchList",//获取数据的地址
			datatype : "local",//从服务器端返回的数据类型，默认是xml
			//列显示名称，是一个数组对象
		    colNames : [ '流程定义id','流程定义名','流程定义key','流程版本','操作'],
			//常用到的属性：name 列显示的名称；index 传到服务器端用来排序用的列名称；width 列宽度；align 对齐方式； sortable 是否可以排序
		    colModel : [  
		                   {name : 'id',key:true,index :'processDefId',hidden:true,editable:true,edittype:'text'},
		                   {name : 'processDefName',index : 'processDefName',width :150,editable:false,edittype:'text',align:"left"},
                           {name : 'processDefKey',index : 'processDefKey',width :120,editable:false,edittype:'text',align:"left"},
                           {name : 'processDefVer',index : 'processDefVer',width :80,editable:false,edittype:'text',align:"left"},
			               {name : 'link',index : 'link',width :60,editable:false,edittype:'text',align:"center"}
		                 ],
			rownumbers:true,//如果为true则会在表格左边新增一列，显示行顺序号，从1开始递增。此列名为'rn'
			rowNum:-1,
			height:300,
			width: $(window).width() - 140, //宽度
			autowidth: false, // 如果设为true，则Grid的宽度会根据父容器的宽度自动重算。重算仅发生在Grid初始化的阶段；
			                  // 如果当父容器尺寸变化了，同时也需要变化Grid的尺寸的话，则需要在自己的代码中调用setGridWidth方法来完成。  
			shrinkToFit:true, //此选项用于根据width计算每列宽度的算法。如果shrinkToFit为true且设置了width值，则每列宽度会根据width成比例缩放
			sortname : 'id',//默认的排序列。可以是列名称或者是一个数字，这个参数会被提交到后台
			loadonce: true, //是否只从服务器获取一次数据。
		    recordpos : 'left',//定义了记录信息的位置： left, center, right
		    multiselect: false,//定义是否可以多选
			sortorder : "desc",//排序顺序，升序或者降序（asc or desc）
			// gridComplete : function(){} 当表格所有数据都加载完成而且其他的处理也都完成时触发此事件，排序，翻页同样也会触发此事件
			editurl : ctx + "/sys/wfAssign/edit",
			gridComplete : function() {
				var ids = jQuery("#mainTable").jqGrid('getDataIDs');
	            for ( var i = 0; i < ids.length; i++) {
	            	var cl = ids[i];
	            	var rowData = jQuery("#mainTable").jqGrid('getRowData',cl);
	            	
                    //动态增加带参数的A链接
                    var dtlLink = "<a data-toggle='modal' href='#configDailog' id= 'alink"+ cl + "' rowindex='"+cl+"' onclick=\"viewDtl('" + cl + "','" + rowData.processDefName +"')\">配置流程人员</a>";
                
	                jQuery("#mainTable").jqGrid('setRowData', ids[i],
	                { link: dtlLink
	                });
	            }
			},
			onSelectRow : function(id){
			    
		    }		
		});
		// 绑定键盘导航(使用上下箭头)
		$("#mainTable").jqGrid('bindKeys');
		
		// 初始化设定窗口大小。 
		$(window).resize(function(){
			//当窗口变化时，自适应宽度。
			$("#mainTable").setGridWidth($(window).width() - 140); 
		});
		// 强制触发窗口自适应。
		$(window).resize();
		
		$("#wfAssign").jqGrid({
			url : ctx + "/sys/wfAssign/searchListWfAssign",//获取数据的地址
			datatype : "local",//从服务器端返回的数据类型，默认是xml
			//列显示名称，是一个数组对象
		    colNames : [ 'id','idFmDB','流程定义id','流程节点key','上环节节点','角色列表','用户列表','部门列表','备注','',''],
			//常用到的属性：name 列显示的名称；index 传到服务器端用来排序用的列名称；width 列宽度；align 对齐方式； sortable 是否可以排序
		    colModel : [ 
		                   {name : 'id',key:true,index :'id',width :25,hidden:true},
		                   {name : 'idFmDB',index :'',hidden:true,editable:true,edittype:'text'},
		                   {name : 'processDefId',index :'processDefId',hidden:true,editable:true,edittype:'text'},
		                   {name : 'taskDefKey',index : 'taskDefKey',width :80,editable:false,edittype:'text',align:"left",editoptions:{
		                	   dataEvents: [{   type: 'change',     // blur,focus,change.............
	                               fn: function(e) {
	                             	  setModifyFlag(e,'#mainTableSub');
	                           }}] }},
                           {name : 'prevTaskDefNames',index :'',width:120,editable:true,edittype:'text'},
		                   {name : 'roleIds',index : 'roleIdList',width :160,editable:false,align:"left",edittype:'text',editoptions:{ 
			                   dataEvents: [{   type: 'change',     // blur,focus,change.............
	                               fn: function(e) {
	                             	  setModifyFlag(e,'#mainTableSub');
	                           }}] }},
                           {name : 'userIds',index : 'userIdList',width:160,editable:false,align:"left",edittype:'text',editoptions:{
		                	   dataEvents: [{   type: 'change',     // blur,focus,change.............
	                               fn: function(e) {
	                             	  setModifyFlag(e,'#mainTableSub');
	                           }}] }},
                           {name : 'officeIds',index : 'officeIdList',width:160,editable:false,align:"left",edittype:'text',editoptions:{
		                	   dataEvents: [{   type: 'change',     // blur,focus,change.............
	                               fn: function(e) {
	                             	  setModifyFlag(e,'#mainTableSub');
	                           }}] }},
			               {name : 'remarks',index : 'remarks',width :140,editable:true,edittype:'text',align:"right",editoptions:{
		                	   dataEvents: [{   type: 'change',     // blur,focus,change.............
                                   fn: function(e) {
                                 	  setModifyFlag(e,'#mainTableSub');
                               }}] }},
		                   {name : 'insertFlag',index :'',hidden:true,editable:true,edittype:'text'},
		                   {name : 'modifyFlag',index :'',hidden:true,editable:true,edittype:'text'}
		                 ],
			rownumbers:true,//如果为true则会在表格左边新增一列，显示行顺序号，从1开始递增。此列名为'rn'
			rowNum:-1,
			height:300,
			width: $(window).width() - 140, //宽度
			autowidth: false, // 如果设为true，则Grid的宽度会根据父容器的宽度自动重算。重算仅发生在Grid初始化的阶段；
			                  // 如果当父容器尺寸变化了，同时也需要变化Grid的尺寸的话，则需要在自己的代码中调用setGridWidth方法来完成。  
			shrinkToFit:true, //此选项用于根据width计算每列宽度的算法。如果shrinkToFit为true且设置了width值，则每列宽度会根据width成比例缩放
			sortname : 'id',//默认的排序列。可以是列名称或者是一个数字，这个参数会被提交到后台
			loadonce: true, //是否只从服务器获取一次数据。
		    recordpos : 'left',//定义了记录信息的位置： left, center, right
		    multiselect: false,//定义是否可以多选
			sortorder : "desc",//排序顺序，升序或者降序（asc or desc）
			// gridComplete : function(){} 当表格所有数据都加载完成而且其他的处理也都完成时触发此事件，排序，翻页同样也会触发此事件
			editurl : ctx + "/sys/wfAssign/edit",
			gridComplete : function() {
				
			},
			onSelectRow : function(id){
			    
		    }		
		});
		// 页面加载后，自动查询。模拟点击事件。
		setTimeout(doSearch,300);
 }
 //点击保存按钮保存选中行
function getPostURL(){
	 var url = ctx + "/sys/wfAssign/searchList";
	 return url + "?" + $("#searchForm").serialize();
};

function doSearch(){
    $("#mainTable").jqGrid('setGridParam',{datatype:"json",url:getPostURL()}).trigger("reloadGrid");
};
	
