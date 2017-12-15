/*
 * Created by hjk on 2016/9/7
 /*
 * 获取本地地址
 */
function UrlSearch() {
    var name, value;
    var str = location.href; //取得整个地址栏
    str = decodeURI(str); //解码
    var num = str.indexOf("?"); //取得?后面的参数
    str = str.substr(num + 1); //取得所有参数   stringvar.substr(start [, length ])
    var arr = str.split("&"); //各个参数放到数组里
    for (var i = 0; i < arr.length; i++) {
        num = arr[i].indexOf("=");
        if (num > 0) {
            name = arr[i].substring(0, num);
            value = arr[i].substr(num + 1);
            this[name] = value;
        }
    }
}

/**
 * 获取参数值
 */
function UrlParam(con) {
    var str = location.href; //取得整个地址栏
    str = decodeURI(str); //解码
    var num = str.indexOf("?"); //取得?后面的参数
    str = str.substr(num + 1); //取得所有参数   stringvar.substr(start [, length ])
    var arr = str.split("&"); //各个参数放到数组里
    for (var i = 0; i < arr.length; i++) {
        var param = arr[i].split("=");
        if(con == param[0]){
        	return param[1];
        }
    }
    return "";
}
/**
 * 读取cookie
 */
function getCookie(e) {
    var t = document.cookie;
    var a = t.split("; ");
    for (var n = 0; n < a.length; n++) {
        var r = a[n].split("=");
        if (r[0] == e) return unescape(r[1])
    }
    return null
}

function clearCookie(name) {  
    setCookie(name, "", -1);  
}  
function checkCookie() {
    var user = getCookie("username");
    if (user != "") {
        alert("Welcome again " + user);
    } else {
        user = prompt("Please enter your name:", "");
        if (user != "" && user != null) {
            setCookie("username", user, 365);
        }
    }
}
 

var Domain1 = "http://www.xmethink.com:28080/";
var Domain2 = "http://www.xmethink.com:26080/msgentry/";
var Domain3 = "http://www.xmethink.com:28080/andlife/hospital";


//显示隐藏
function Show(className) {
    $(className).css({
        'display': 'block',
    })
    setTimeout(function () {
        $(className).css({
            'display': 'none',
        })
    }, 3000)
}

Date.prototype.Format = function(fmt)   
{ //author: meizz   
  var o = {   
    "M+" : this.getMonth()+1,                 //月份   
    "d+" : this.getDate(),                    //日   
    "h+" : this.getHours(),                   //小时   
    "m+" : this.getMinutes(),                 //分   
    "s+" : this.getSeconds(),                 //秒   
    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
    "S"  : this.getMilliseconds()             //毫秒   
  };   
  if(/(y+)/.test(fmt))   
    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
    if(new RegExp("("+ k +")").test(fmt))   
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;   
};

window.onerror = function(err) {
        log('window.onerror: ' + err)
    }
var apptype = getCookie("apptype");
    function setupWebViewJavascriptBridge(callback) {
        // if (apptype == 'ios') {
            if (window.WebViewJavascriptBridge) { return callback(WebViewJavascriptBridge); }
            if (window.WVJBCallbacks) { return window.WVJBCallbacks.push(callback); }
            window.WVJBCallbacks = [callback];
            var WVJBIframe = document.createElement('iframe');
            WVJBIframe.style.display = 'none';
            WVJBIframe.src = 'wvjbscheme://__BRIDGE_LOADED__';
            document.documentElement.appendChild(WVJBIframe);
            setTimeout(function() { document.documentElement.removeChild(WVJBIframe) }, 0)      
        // };
    }


 
    var apphaha = '';
    apptype = getCookie("apptype");
    $(document).on('ajaxBeforeSend', function(e, xhr, options){
        apptype = getCookie("apptype");
        if (apptype == 'ios') {
            setupWebViewJavascriptBridge(function(bridge) {      
                bridge.callHandler('testObjcCallback', {'foo': 'bar'}, function(response) {});

				
            })
            while(true) {
                apphaha = getCookie("apphaha");
                if(!(apphaha == undefined ||apphaha == 'undefined' || apphaha == null ||apphaha == '')) {
                    break;
                }
            }
            //alert(apphaha)
            var timestamp = apphaha.split('#')[0];
            var sign = apphaha.split('#')[1];
            options.data = options.data+'&timestamp='+timestamp+'&sign='+sign;
            //alert( options.data)
            checkCookie(apphahas);
        };
    })
    

    

    $(document).on('ajaxBeforeSend', function(e, xhr, options){
        apptype = getCookie("apptype");
        if (apptype == 'android') {
            window.android.getDigist(1);
            var td =window.android.getDigist(1);
            var timestamp = td.split('@')[0];
            var sign = td.split('@')[1];
            options.data = options.data+'&timestamp='+timestamp+'&sign='+sign;
		
           
        };
    })
    
    String.prototype.replaceAll = function(s1,s2){
　　		return this.replace(new RegExp(s1,"gm"),s2);
　　}
    
    function html2Escape(sHtml) {
    	 return sHtml.replace(/[<>&"]/g,function(c){return {'<':'&lt;','>':'&gt;','&':'&amp;','"':'&quot;'}[c];});
    }
    function escape2Html(str) {
    	 var arrEntities={'lt':'<','gt':'>','nbsp':' ','amp':'&','quot':'"'};
    	 return str.replace(/&(lt|gt|nbsp|amp|quot);/ig,function(all,t){return arrEntities[t];});
    }
    
    function afterLogin(e, url) {
        var key = getCookie("key");
        if (url.indexOf('##') != -1) {
            var index = url.indexOf('##') + 2;
        } else {
            var index = url.indexOf('%23%23') + 6;
        }
        if (url != null && url != '') {
            eval(url.substr(index));
        }
    }
	
	
	function checkLoginThenSkip(url) {
        var key = getCookie("key");
        if (!key) {
            window.location.href = "localhost/index.html#act=login##checkLoginThenSkip('" + url + "')";
            return false;
        } else {
            window.location.href = url;
            return true;
        }
    }