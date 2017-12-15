<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!doctype html>
<html>
<head>
<title>
</title>
<script type="text/javascript" src="${ctxStatic}/jweixin-1.0.0.js">
</script>
<script type="text/javascript">
  var wxJSSDKReady = false; //默认为JSSDK没准备好。
  
  wx.ready(function(){
    // wx.config()调用成功后，自动调用wx.ready()，才能设ready标志。
    wxJSSDKReady = true;
    // 画面打开自动打开扫描功能。
    scan();

    // 定义选图接口.
    var images = { 
            localId: [], 
            serverId: [] 
     };
    
   }
  );
  
  function scan(){
    // 检查jsSDK是否可用。
    if(!wxJSSDKReady){ 
	  alert("微信扫描功能还没准备好,请稍后再扫描！");
	  return;
	}
	
	// 调用jsSDK 返回扫描结果。
	wx.scanQRCode({ 
	   needResult: 1,        // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
       scanType: ["qrCode"], // 可以指定扫二维码还是一维码，默认二者都有	   
	   desc: 'scanQRCode desc',      
	   success: function (res) {
		 // 弹出扫描结果（调试用） 
	     alert(JSON.stringify(res));
         
         // ajax调用,判断扫描的Qrcode(取值为JSON.stringify(res))是否合法。
         // 如果不合法，提示信息【扫描的QrCode不是合法的司机QrCode,请确认后再次扫描！】。	 
		 alert("扫描的QrCode不是合法的司机QrCode,请确认后再次扫描！");
		 
	   }  
	});
  };
</script>
</head>

<button style="width:100px;height:50px;" id="btnScan" value="重新扫码" onClick="scan();"/>

<script type="text/javascript">

  // 获取签名用本网址的URL(调试时使用)。
  //alert(location.href.split('#')[0]);
  
  // 获取后台的参数，配置JS-SDK。配置成功后，执行wx.ready()函数。
  wx.config({
      debug: true,   // 生产环境下，设成false。
      appId: '${wxSignModel.appid}',
      timestamp: '${wxSignModel.timestamp}',
      nonceStr: '${wxSignModel.nonceStr}',
      signature: '${wxSignModel.signature}',
      jsApiList: [
        'checkJsApi',
        'scanQRCode',
        'chooseImage',
        'previewImage', 
        'uploadImage',
        'downloadImage'
      ]
  });

</script>

</html>