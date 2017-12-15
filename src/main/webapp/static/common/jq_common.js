/**
 * JqGrid的common函数。
 * */
"use strict";

$(document).ready(function() {
	$.jgrid.defaults.width = 1024;
	$.jgrid.defaults.responsive = true;
	$.jgrid.defaults.styleUI = 'Bootstrap';
	// 设置登录超时后，探出提示窗，让用户在此登录。
	// 注意:sessionstatus=="timeout"的赋值是在MobileInterceptor的preHandle函数中执行。
	// response.setHeader("sessionstatus", "timeout");
	$.ajaxSetup({ 
        complete:function(XMLHttpRequest,textStatus){ 
          var sessionstatus=XMLHttpRequest.getResponseHeader("sessionstatus");
              //通过XMLHttpRequest取得响应头，sessionstatus，  
              if(sessionstatus=="timeout"){ 
                 alert("登录超时,请重新登录！");
                 //如果超时就处理 ，指定要跳转的页面  
                 top.window.location.href = "hihunan/a/login";   
              }   
           }   
      });
});

/**
function getGridWidth() {
	var offset = 5;
	//var w = $(".content-detail").width() - offset;
	return 800;
}

function getGridHeight() {
	var offset = 58;
	var bodyHeight = $(window).height() - offset;
	//var h = bodyHeight - $(".content-detail").offset().top;
	return bodyHeight;
}
*/

//验证输入内容是否必输，输入是否正确（清单层）
function checkInputLis(tableId,rowId,array){
		for(var i = 0;i<array.length;i++){
		var regFloat = /^[-+]?\d+(\.\d+)?$/;//实数
		//var regInt = /^[1-9]\d*$/;//整数
		var value = $(tableId+" #"+rowId+"_"+array[i][0]).val();
		//文本类型
		if(array[i][2] == "text"){
			if(array[i][3] == true){
				if(value == null || value == ""){
					alert("请输入"+array[i][1]+"！");
					$(tableId + " #"+rowId+"_"+array[i][0]).focus();
					return false;
					}
				var length = $(tableId+" #"+rowId+"_"+array[i][0]).val().length;
				if(length > array[i][4]){
					alert(array[i][1]+"长度超出限制！");
					$(tableId + " #"+rowId+"_"+array[i][0]).focus();
					return false;
					}
				}
		}
		//数字类型
		if(array[i][2] == "number"){
			var valueNum = parseInt(value);
			if(array[i][3] == true){
				if(value == null || value == ""){
					alert("请输入"+array[i][1]+"！");
					$(tableId +" #"+rowId+"_"+array[i][0]).focus();
					return false;
				}
			}
			//var valueFloat = parseFloat(value);
			if(!regFloat.test(value)){
				alert("您的输入包含非数字字符，请修改！");
				$(tableId + " #"+rowId+"_"+array[i][0]).focus();
				return false;
			}
			if(valueNum < 0){
				alert("不能输入负数！");
				$(tableId + " #"+rowId+"_"+array[i][0]).focus();
				return false;
			}
		}
		//日期类型
		if(array[i][2]== "date"){
			if(array[i][3] == true){
				if(value == null || value == ""){
					alert("请输入"+array[i][1]+"！");
					$(tableId+" #"+rowId+"_"+array[i][0]).focus();
					return false;
				}
			}
		}
		//日期是否有效待实现
		}
	return true;
}
//在Jqgrid上，当在某一输入框回车后，光标自动跳到右侧的第一个可输入框。
// 参数 e:DataEvents事件中的参数, gridId:表格的ID，nextColName:光标转移行的名字。
function setFocusToNextColModel(e,gridId,nextColName)
{    var controlId = e.target.id;
     var arr = controlId.split('_'); 
     var rowid = arr[0];
     // 例如 '#mainTable #2_modifyFlag'
     var str = gridId+' #' + rowid + '_' + nextColName;
     $(str).focus();
     return false;
}
// 设置画面字段修改的标志。
// 参数 e:DataEvents事件中的参数, gridId:表格的ID
function setModifyFlag(e,gridId){
	var controlId = e.target.id;
    var arr = controlId.split('_'); 
    var rowid = arr[0];
    // 例如 '#mainTable #2_modifyFlag'
    var str = gridId+' #' + rowid + '_modifyFlag';
    $(str).val('1'); // 设置成修改过的状态。
    return false;
}
// 保存某一Grid的某行数据。适用于点击另一行或点击[新增]按钮或body上点击 时，保存先前行，并使新行进入编辑状态。
// 参数 gridId:表格的ID,newRowId：新行id,当newRowId传入'0'时，代表着单纯保存旧行数据，不处理新行。
// lastSelId:为最后选择行。注意:最后选择行不一定会改变，如输入验证失败时。
// callBackFunc:回调函数。调用方式  saveGridRow('#mainTable','0',function(rowId，updRowId，response){lastSelList = rowId;});
// lastSelList=rowId; 的含义：对本页面上的最后选择行变量lastSelList进行重新赋值,即使lastSelList值不变。
// gridCheckArray: 该Grid使用的验证数组。
// ??? setTgtSubRowId(updRowId,response.responseText);

// 返回：true : 跳转 。false：失败，不跳转。
function saveGridRow(gridId,newRowId,lastSelId,gridCheckArray,callBackFunc){
	var aftersaveSign = '0'; //'1':调用了saveRow并保存成功。'0': 其他场合。
	// (1). 判断前次是否有选中行。
	if(lastSelId == "0"){
		if(newRowId != '0'){
		   $(gridId).jqGrid('editRow',newRowId); // 新行进入编辑状态。
		   lastSelId = newRowId; // 最后一次选中行的变量刷新。
		}
		callBackFunc(lastSelId,aftersaveSign,'',''); // 把刷新后的最后选中行回传到调用端。
		return;  // 跳转（返回true）。
	}
	//(2) 判断上次编辑行lastsel是否为编辑状态(.length)。如不是，则返回。
	if($(gridId + ' #'+ lastSelId + '_modifyFlag').length <= 0){
	    if(newRowId != '0'){
		   $(gridId).jqGrid('editRow',newRowId); // 新行进入编辑状态。
		   lastSelId = newRowId; // 最后一次选中行的变量刷新。
	    }
	    callBackFunc(lastSelId,aftersaveSign,'',''); // 把刷新后的最后选中行回传到调用端。
	    return;
	}
	//(3) 判断上次编辑行的值是否有变化。即选中行的modifyFlag是否为0(没变化)。
	if( $(gridId + ' #'+ lastSelId + '_modifyFlag').val() == '0' ) {
		$(gridId).jqGrid('restoreRow',lastSelId); //把原选中行恢复浏览状态。
		if(newRowId != '0'){
		   $(gridId).jqGrid('editRow',newRowId); // 新选中行进入编辑状态。
		   lastSelId = newRowId; // 最后一次选中行的变量刷新。
		}
		callBackFunc(lastSelId,aftersaveSign,'',''); // 把刷新后的最后选中行回传到调用端。
		return;  // 跳转（返回true）。
	}
	//(4) 前一行的保存前数据验证
	if(!checkInputLis(gridId,lastSelId,gridCheckArray))
	{   // 验证失败时，跳转（返回false）。
		// 保持最后一次选中行的变量不变。
		callBackFunc(lastSelId,aftersaveSign,'',''); // 把刷新后的最后选中行回传到调用端。
	    return;
	}
	
	//(5) 保存前一行数据。成功后，跳转。失败时，也跳转。
	jQuery(gridId).jqGrid("saveRow",lastSelId,{
		successfunc:function(response){
		  // 判断是否保存成功，如果返回数字(即表的id字段值),则表示成功。
		  if(isNaN(response.responseText)) {
			// lastSelId = newRowId 保持最后一次选中行的变量不变。
			alert(response.responseText); // 显示后台返回的错误信息。如:服务器忙，请。。
			
			// 当需要回调时，调用回调函数。
			callBackFunc(lastSelId,aftersaveSign,'',''); // 把刷新后的最后选中行回传到调用端。
			return false; // false时，不执行aftersavefunc的函数。
		  } else {  
		    return true;  // 注意：true时，往下继续执行aftersavefunc的函数。
		  }
		},
		aftersavefunc:function(updRowId,response){
		  if(newRowId != '0'){
		     // 新行进入编辑状态。
		     $(gridId).jqGrid('editRow',newRowId);
		     // 最后一次选中行的变量刷新。
		     lastSelId = newRowId;
		  }
		  aftersaveSign = '1'; // 调用了saveRow并保存成功。调用者根据此标识来调用setTgtSubRowId等函数。
		  // updRowId:更新行id,为 数字或 '23A'等。 response.responseText:为aftersavefunc的参数，如 '39','处理错误,请联系系统管理员'等.
		  callBackFunc(lastSelId,aftersaveSign,updRowId,response.responseText); // 把刷新后的最后选中行回传到调用端。
		  return;
	    }
	});
	return;
}

//2016/5/16添加，巡检系统弹框返回按钮调用
function closeDailog(){
	$("[name=btnClose]").click();
}
