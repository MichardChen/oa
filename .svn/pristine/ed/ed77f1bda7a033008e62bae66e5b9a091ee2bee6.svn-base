<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<script type="text/javascript">



//全选按钮
function isAllCheck(con){
	var value = con.checked;
	var checkArray = $('input[type="checkbox"][name="checkData"]');
	if(value == true){
		for(var i = 0;i < checkArray.length;i++){
			var checkSingle = checkArray[i];
			checkSingle.checked = true;
		}
	}else{
		for(var i = 0;i < checkArray.length;i++){
			var checkSingle = checkArray[i];
			checkSingle.checked = false;
		}
	}
}

//获取选择行的数据
function getCheckBoxData(){
	var trValue = '';
	var checkArray = $('input[type="checkbox"][name="checkData"]:checked');
	for(var i = 0;i < checkArray.length;i++){
		trValue += checkArray[i].value + ",";
	}
	return trValue;
}

//文章查询过滤
function searchArtical(){
	var title = $("#searchForm #title").val();
	var articalType = $("#searchForm #articalType").val();
	var auditState = $('input:radio:checked').val();
	
	$.ajax({
		async:true,
		type:"post",
		data:{"title":title,"articalType":articalType,"auditState":auditState},
		url: ctx + "/hihunan/artical/hiArtical/list",
		success:function(rtnData){
			//重新加载刷新
			top.$('.tab_selected').loadData(true);
		},
		error:function(x,h,r){
			
		}
	});
};

//审批 上架
function fuPutaway(){
	//获取所有文章IDS
	var taskData = getCheckBoxData();
	if(taskData == ''){
		alert("请选择要上架的数据！");
		return;
	}
	var auditState = "1";
	var title = $("#searchForm #title").val();
	$.ajax({
		async:false,
		type:"post",
		data:{"ids":JSON.stringify(taskData),"auditState":auditState},
		url: ctx + "/hihunan/artical/hiArtical/updateAuditState",
		success:function(data){
				var data = eval("(" + data + ")");
				if(data.result == 0){//保存成功
					top.$.jBox.tip(data.msg);
					top.$('.tab_selected').loadData(true);
				}else{
					top.$.jBox.tip(data.msg);
					return;
				}
		},
		error:function(x,h,r){
			
		}
	})
}

//审批  下架
function fuSoldOut(){
	//获取所有文章IDS
	var taskData = getCheckBoxData();
	if(taskData == ''){
		alert("请选择要下架的数据！");
		return;
	}
	var auditState = "0";
	var title = $("#searchForm #title").val();
	$.ajax({
		async:false,
		type:"post",
		data:{"ids":JSON.stringify(taskData),"auditState":auditState},
		url: ctx + "/hihunan/artical/hiArtical/updateAuditState",
		success:function(data){
				var data = eval("(" + data + ")");
				if(data.result == 0){//保存成功
					top.$.jBox.tip(data.msg);
					top.$('.tab_selected').loadData(true);
				}else{
					top.$.jBox.tip(data.msg);
					return;
				}
		},
		error:function(x,h,r){
			
		}
	})
}

$(document).ready(function() {
	
	//时间范围限制
	$("#btnSubmit").click(function(){
		var startTime = $("#searchForm #startTime").val();
		var endTime = $("#searchForm #endTime").val();
		if(endTime!= null && endTime != ""){
			//将字符型的日期转换成date类型
			var start = new Date(startTime);
			var timeStart = start.getTime();
			var end = new Date(endTime);
			var timeEnd = end.getTime();
			if(timeStart > timeEnd){
				alert("时间的范围不正确！");
				return false;
			}			
		}
	});
	//新闻抓取
	$("#scrapyNews").click(function(){
		var con = this;
		$(con).val("抓取中...");
		$(con).attr("disabled", true);
		$.ajax({
			async:true,
			type:"post",
			url: ctx + "/hihunan/artical/hiArtical/scrapyNews",
			success:function(rtnData){
				var data = eval('(' + rtnData + ')');
				if(data.result == 0){
					top.$.jBox.tip(data.msg);
					$(con).val("读取图片中...");
					$.ajax({
						async:true,
						type:"post",
						url: ctx + "/hihunan/artical/hiArtical/loadNewsPhoto",
						success:function(rtnData){
							var data = eval('(' + rtnData + ')');
							if(data.result == 0){
								$(con).val("新闻抓取");
								$(con).attr("disabled",false);
								//重新刷新
								$("#btnSubmit").trigger("click");
							}else{
								alertx(data.msg);
								return;
							}
						},
						error:function(x,h,r){
							$(con).val("新闻抓取");
							$(con).attr("disabled",false);
							alertx(x);
						}
					});
				}else{
					alertx(data.msg);
					return;
				}
			},
			error:function(x,h,r){
				$(con).val("新闻抓取");
				$(con).attr("disabled",false);
				alertx(x);
			}
		});
	});
	//新闻删除
	$("#deleteNews").click(function(){
		var con = this;
		$(con).val("删除中...");
		$(con).attr("disabled", true);
		$.ajax({
			async:true,
			type:"post",
			url: ctx + "/hihunan/artical/hiArtical/deleteNews",
			success:function(rtnData){
				var data = eval('(' + rtnData + ')');
				if(data.result == 0){
					top.$.jBox.tip(data.msg);
					//重新刷新
					$("#btnSubmit").trigger("click");
				}else{
					alertx(data.msg);
					return;
				}
				$(con).val("新闻删除");
				$(con).attr("disabled",false);
			},
			error:function(x,h,r){
				$(con).val("新闻删除");
				$(con).attr("disabled",false);
				alertx(x);
			}
		});
	});
	//切换 模拟点击事件
	$('input:radio[name="auditState"]').change(function(){
		var con = $('input:radio:checked').val();
		if(con == '0'){
			$("#putaway").show();
	        $("#soldOut").hide();
		}else if(con == '1'){
			$("#soldOut").show();
	        $("#putaway").hide();
		}
		$("#btnSubmit").trigger("click");
	}); 
	
	
	var auditState = '${hiArtical.auditState}';
	if(auditState == "1"){
		$("#soldOut").show();
        $("#putaway").hide();
	}else if(auditState == "0"){
		$("#putaway").show();
        $("#soldOut").hide();
	}else if(auditState == ""){
		$("#putaway").hide();
        $("#soldOut").hide();
	}
	
	
});
function page(n,s){
	$("#pageNo").val(n);
	$("#pageSize").val(s);
	$("#searchForm").submit();
	return false;
}
</script>