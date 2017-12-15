<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="${ctxStatic}/mui/js/mui.min.js"></script>
<script type="text/javascript">
window.onload = function(){   
   var loginUrl = 'login.html?v=20160723'; // 手机登录页面的url
   
   mui.ajax({
       //提交数据的类型 POST GET
       type:"POST",
       headers: {
           "Accept": 'application/json'
       },
       //提交的网址
       url:"${ctxRoot}/webservice/rest/login/getMobileMenu",
       //提交的数据
       data:{"token":"3434KSSIIS78JSSSSWEEEDDdddddddf23"},
       //返回数据的格式
       dataType: "json",//"xml", "html", "script", "json", "jsonp", "text".
       //成功返回之后调用的函数             
       success:function(data){
     		// 获取菜单数据
     		if(data.result != undefined && data.result == -1){
     		}
     		else{
     			for(var i = 0; i < data.length;i++){
	     			 // 获取login.html的最新版本，以便于跳转到login.html的最新版本号
	     		     if(data[i].href.indexOf('login.html') > -1){
	     		    	 loginUrl = data[i].href;
	     		    	 break;
	     		     }
     			}
     		}
     	    // 3)设置到localStorage.
     	    localStorage.removeItem("allMenuList");
     	    localStorage.setItem("allMenuList",JSON.stringify(data));
     	  
     	    // 4)自动跳转到登录页面login.html
     	    window.location = "${ctxStatic}/hihunan/index.html";
       },
       //调用出错执行的函数
       error: function(){
    	   // 4)自动跳转到登录页面
    	   window.location = "${ctxStatic}/hihunan/index.html";
       }         
    });  
}
</script>
</head>
<body>
</body>
</html>