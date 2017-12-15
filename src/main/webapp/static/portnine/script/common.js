function checkMsg(resultModelStr) {
	if (!(typeof(resultModelStr) == "undefined" || resultModelStr == "")) {
		var resultModel = eval("(" + resultModelStr + ")");
		if (resultModel.resultType == "-1") {
			showError(resultModel.resultMsg);
		} else if (resultModel.resultType == "3") {
			showError(resultModel.resultMsg);
		} else if (resultModel.resultType == "2") {
			showWarning(resultModel.resultMsg);
		}
	}
}

function showSuccess(msg, callback) {
	BootstrapDialog.show({
		type: BootstrapDialog.TYPE_SUCCESS,
		title: "成功",
		message: msg,
		buttons: [{
			label: "OK",
			action: function(self) {
				self.close();
				if (callback) {
					callback();
				}
			}
		}]
	});
}

function showWarning(msg) {
	BootstrapDialog.show({
		type: BootstrapDialog.TYPE_WARNING,
		title: "警告",
		message: msg,
		buttons: [{
			label: "OK",
			action: function(self) {
				self.close();
				if (callback) {
					callback();
				}
			}
		}]
	});
}

function showError(msg) {
	BootstrapDialog.show({
		type: BootstrapDialog.TYPE_DANGER,
		title: "错误",
		message: msg,
		buttons: [{
			label: "OK",
			action: function(self) {
				self.close();
				if (callback) {
					callback();
				}
			}
		}]
	});
}

//设置下拉框只读
function dropdownRo(obj){
	obj.attr("readonly", true);
	obj.attr("onfocus","this.defaultIndex=this.selectedIndex;");
	obj.attr("onchange","this.selectedIndex=this.defaultIndex;");
}

//设置下拉框读写
function dropdownRoRemove(obj){
	obj.attr("readonly", false);
	obj.removeAttr("onfocus");
	obj.removeAttr("onchange");
}
