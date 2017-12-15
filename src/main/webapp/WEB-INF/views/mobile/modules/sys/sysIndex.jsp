<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">
var menuUrl = 'menu.html?v=20160712'; // 手机菜单页面的url

function setUserInfo(){
   
   // 1)设置用户属性
   var mUserInfo = new Object;
   mUserInfo.error = "${mUserInfo.error}";
   mUserInfo.errorCode = "${mUserInfo.errorCode}";
   mUserInfo.errCode = "${mUserInfo.errCode}";
   mUserInfo.url     = "${mUserInfo.url}";
   mUserInfo.UserCode= "${mUserInfo.UserCode}";
   mUserInfo.LoginName= "${mUserInfo.LoginName}";
   mUserInfo.UserId  = "${mUserInfo.UserId}";
   mUserInfo.UserName= "${mUserInfo.UserName}";
   //mUserInfo.weixinBind = "${mUserInfo.weixinBind}";
   mUserInfo.wxAccessToken = "${mUserInfo.wxAccessToken}";
   mUserInfo.wxUserId = "${mUserInfo.wxUserId}";
   mUserInfo.roleNames = "${mUserInfo.roleNames}";
   // 2)设置用户登录后显示的菜单信息
   var userMenuList = new Array();
   <c:forEach items="${mUserInfo.menuList}" var="menu">
   var tmp = new Object;
     tmp.href = "${menu.href}";
     tmp.icon = "${menu.icon}";
     tmp.name = "${menu.name}";
     userMenuList.push(tmp);
   </c:forEach>
   // 3)设置所有手机菜单信息，用于获取最新版本号
   var allMenuList = new Array();
   <c:forEach items="${mUserInfo.allMenuList}" var="menu">
      var tmpAll = new Object;
      tmpAll.href = "${menu.href}";
      // 获取menu.html的最新版本，以便于跳转到menu.html的最新版本号
      if(tmpAll.href.indexOf('menu.html') > -1){
    	 menuUrl = tmpAll.href;
      }
      allMenuList.push(tmpAll);
   </c:forEach>
   
   // 4)设置到localStorage. 
   localStorage.removeItem("allMenuList");
   localStorage.removeItem("mUserInfo");
   localStorage.removeItem("menuList");
   
   localStorage.setItem("allMenuList",JSON.stringify(allMenuList));
   localStorage.setItem("mUserInfo",JSON.stringify(mUserInfo));
   localStorage.setItem("menuList",JSON.stringify(userMenuList));
   return;
}

window.onload = function(){
   // 1)通过获取到的${mUserInfo}，设置到localStorage中。这一点和login.html页面上通过Ajax登录接口
   // 获取用户信息后设置到localStorage中一样的方法。 必须放在menu.html的重定向之前。
   setUserInfo();
   
   // 2)自动跳转到手机网页的主菜单menu.html?v=XXXX
   window.location = "${ctxStatic}/hihunan/index.html";     
}
</script>

</head>
<body>
</body>
</html>