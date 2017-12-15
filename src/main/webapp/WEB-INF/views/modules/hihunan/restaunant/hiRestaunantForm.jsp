<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>餐厅管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					var lat  = $("#inputForm #latitude").val();
					var lng  = $("#inputForm #longitude").val();
					if(lat == null|| lng==null||lat==0||lng==0){
						alertx("请在地图上标注餐厅地点:");
						return;
					}
						
					loading('正在提交，请稍等...');
					form.submit();
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
			// 地址获取坐标
			$("#aGetLatLngDailog").click(function(){
				var address = $("#inputForm #address").val();
				var oldLat  = $("#inputForm #latitude").val();
				var oldLng  = $("#inputForm #longitude").val();
				// 初始化地图信息
				initMap(address,oldLat,oldLng);
			});
			
			// 选中坐标后的处理 
			$("#btnSetLatLng").click(function(){
				var lat = document.getElementById("getLat").value;
				// 保持6位小数
				if(lat){
					lat = Math.round(lat*1000000)/1000000;
				}
				// 保持6位小数
				var lng = document.getElementById("getLng").value;
				if(lng){
					lng = Math.round(lng*1000000)/1000000;
				}
				$("#inputForm #latitude").val(lat);
				$("#inputForm #longitude").val(lng);
				$("#latlngstr").html(lng + "  " + lat);
				// 如果用户通过定位来获取到地址，并且原地址为空
				var oldAddress = $("#inputForm #address").val();
				if(oldAddress == null|| oldAddress==""){
					var getAddress = $("#getAddr").val();
					$("#inputForm #address").val(getAddress);
				}
				closeDailog();
			});
			
			// 修改时，显示 经纬度
			var lngOld = '${hiRestaunant.longitude}';
			var latOld = '${hiRestaunant.latitude}';
			if(lngOld != "" && latOld != ""){
			   $("#latlngstr").html(lngOld + "  " + latOld);
			}
		});
		
	</script>
	
	<link href="${ctxStatic}/jqGrid5.0/css/ui.jqgrid-bootstrap.css" rel="stylesheet" type="text/css"  />
  	<style type="text/css">
  		#mainTable table{
  			border-right:1px solid #ddd;
  			border-bottom:1px solid #ddd;
  			border-left:1px solid #ddd;
  			border-top:1px solid #ddd
  		}
  		#mainTable td{
  		
  		border-bottom: 1px solid #ddd;
    	border-right: 1px solid #ddd;
  		
  		}
  		#mainTableArtical table{
  			border-right:1px solid #ddd;
  			border-bottom:1px solid #ddd;
  			border-left:1px solid #ddd;
  			border-top:1px solid #ddd
  		}
  		#mainTableArtical td{
  		
  		border-bottom: 1px solid #ddd;
    	border-right: 1px solid #ddd;
  		
  		}
  		.pagination ul>li>a, .pagination ul>li>span {
	    float: left;
	    padding: 4px 12px;
	    line-height: 20px;
	    text-decoration: none;
	    background-color: #e8e1e1;
	    border-left-width: 0;
	    color: #00a1ff;
	    border-left:5px solid #fff;
		}
  	.ui-jqgrid-hbox th{border-right: 1px solid #ddd;}
  		
  	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/hihunan/restaunant/hiRestaunant/">餐厅列表</a></li>
		<li class="active"><a href="${ctx}/hihunan/restaunant/hiRestaunant/form?id=${hiRestaunant.id}">餐厅<shiro:hasPermission name="hihunan:restaunant:hiRestaunant:edit">${not empty hiRestaunant.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="hihunan:restaunant:hiRestaunant:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="hiRestaunant" action="${ctx}/hihunan/restaunant/hiRestaunant/save" method="post" class="form-horizontal">
		
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">题头：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">题头图片：</label>
			<div class="controls">
				<form:hidden id="nameImage" path="titlePhoto" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:ckfinder input="nameImage" type="images" uploadPath="/photo" selectMultiple="false" maxWidth="100" maxHeight="100"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">地点：</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<a id="aGetLatLngDailog" href="#getLatLngDailog" data-toggle='modal'><img style="height:26px;width:26px;" src="${ctxStatic}/images/maker.jpg"/></a>
				<%-- 经纬度：<span id="latlngstr"></span>
				     <form:input path="longitude" style="" htmlEscape="false" class="input-small required" />
				     <form:input path="latitude" style="" htmlEscape="false" class="input-small required" /> --%>
				<span class="help-inline"><font color="red">*</font> </span> 
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">经度：</label>
			<div class="controls">
				<form:input path="longitude" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">纬度：</label>
			<div class="controls">
				<form:input path="latitude" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">菜系：</label>
			<div class="controls">
				<form:select path="cookingStyle" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('hi_cooking_style')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">评级：</label>
			<div class="controls">
			<form:select path="star" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('hi_star_evaluate')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商圈：</label>
			<div class="controls">
				<form:select path="tradingArea" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('hi_trading_area')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电话：</label>
			<div class="controls">
				<form:input path="tel" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">说明：</label>
			<div class="controls">
				<form:textarea path="description" htmlEscape="false" rows="8" maxlength="512" class="input-xlarge" style="width: 600px;" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">顺序：</label>
			<div class="controls">
				<form:input path="seq" htmlEscape="false" maxlength="11" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否推荐：</label>
			<div class="controls">
			<form:select path="isRecommend" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('hi_yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="hihunan:restaunant:hiRestaunant:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>&nbsp;&nbsp;
			<input type="button" value="添加文章 " class="btn btn-primary" href="#addHiArticalDailog" data-toggle='modal' onclick="addHiArtical(this);return false;"/>
		</div>
	</form:form>
	<!--以关联的文章jqGrid-->
	<div><h4>文章列表</h4></div>
	<div style="width: 98%;margin-left:5px;">
		<table id="mainTable"></table>
		<div id="page"> </div>
	</div>
	
	<!--添加文章弹框 -->
	<div id="addHiArticalDailog" class="modal hide fade" style="width:90%;margin-left:-45%;margin-top:-2%;background-color:white;height:90%;overflow:auto;">
		<div class="modal-header" style="height:4%;margin-top: 10px;">
				<div id = "hintMsg" style="width:80%">
					<h4 style="margin-top:2px;">
						<span id = "volOrMaterial">
						添加文章
						</span>
					</h4>
				</div>
				<div>
		        	<button type="button" name="btnClose" data-dismiss="modal" aria-hidden="true" class="close" style="margin-top:-2%;">&times;</button>
		        </div>
	     </div>
		<form:form id="searchForm" modelAttribute="hiArtical" action="" method="post" class="breadcrumb form-search">
			<ul class="ul-form">
				<li><label>题头：</label>
					<form:input path="title" htmlEscape="false" maxlength="255" class="input-medium"/>
				</li>
				<li><label>类型：</label>
					<form:select path="articalType" class="input-xlarge ">
						<form:option value="" label="">
						<form:options items="${fns:getDictList('hi_list_type')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:option>
				</form:select> 
				<li class="btns"><input id="articalSearch" class="btn btn-primary" type="button" value="查询" onclick="searchArtical();" /></li>
				<li class="clearfix"></li>
			</ul>
		</form:form>
	     <div style="width: 98%;margin-left:5px;">
			<table id="mainTableArtical"></table>
			<div id="pageArtical"> </div>
		</div>
	     
		<div style="text-align: center;margin-top: 20px;">
			<input  class="btn btn-primary" type="button" value="保存" onclick="saveMap(this);"/>
			<input  class="btn" type="button" value="返 回" onclick="closeDailog();" style="border: 1px solid #969696;"/>
		</div> 
	</div>
	
	<!--地图获取弹框 -->
	<div id="getLatLngDailog" class="modal hide fade" style="width:60%;margin-left:-45%;margin-top:-2%;background-color:white;height:90%;overflow:auto;">
		<div class="modal-header" style="height:4%;margin-top: 10px;">
			<div id = "hintMsg" style="width:80%">
				<h4 style="margin-top:2px;">
					<span id = "volOrMaterial">
					设置坐标
					</span>
				</h4>
			</div>
			<div>
	        	<button type="button" name="btnClose" data-dismiss="modal" aria-hidden="true" class="close" style="margin-top:-2%;">&times;</button>
	        </div>
	    </div>
	    <input id="getLat" type="hidden"/> <!--  -->
	    <input id="getLng" type="hidden"/> <!--  -->
	    <input id="getAddr" type="hidden"/> <!--解析 -->
	    <script >
	        // init参数
	        // oldAddress 用户已经输入的地址.可以为空
	        // oldLat     用户原来的纬度，可以为空或0
	        // oldLng     用户原来的经度，可以为空或0
			function initMap(oldAddress,oldLat,oldLng) {
	        	
	        	var oldLatLngExist = "1"; // 默认原来的经纬度没已经设置
	        	if(oldLat == null||oldLng==null||oldLat==0||oldLng==0){
	        		//如果没有原来的经纬度，则设成湖南省的经纬度
	        		oldLat = 28.113520;
	        		oldLng = 112.983300;
	        		oldLatLngExist = "0";
	        	}
	        	
	        	var geocoderCallBack = "getLoc"; //默认获取经纬度
	        	
	            var center = new qq.maps.LatLng(oldLat, oldLng);
	            var map = new qq.maps.Map(document.getElementById("container"), {
	                center: center,
	                zoom: 16
	            });
	        	//设置定位好的坐标到隐藏字段。
				document.getElementById("getLat").value = oldLat;
				document.getElementById("getLng").value = oldLng;
				
	            var marker; //标注对象
	            //地址和经纬度之间进行转换服务
	            var geocoder = new qq.maps.Geocoder();
	            /*
	            var geocoderToAddr = new qq.maps.Geocoder(
	            	// 腾讯地图的地址逆解析的回调函数。即使用 geocoder.getAddress后的自动回调。
	            	complete:function(result){
	            	   document.getElementById('getAddr').value = result.detail.address;	
	            }); */
	            // 设置地址解析成功时的回调函数
	            geocoder.setComplete(function(result) {
	            	  
	            	  if(geocoderCallBack != 'getAddr'){ //解析地址
		                  // 设置地图的中心位置
		                  map.setCenter(result.detail.location);
		                  // 根据解析出来的经纬度，设置成标注点
		                  marker = new qq.maps.Marker({
		                      map: map,
		                      position: result.detail.location
		                  });
		                  
			              //设置定位好的坐标到隐藏字段。
			  			  document.getElementById("getLat").value = result.detail.location.getLat();
			  			  document.getElementById("getLng").value = result.detail.location.getLng();
			  			  
						  doMark();
	            	  }else{ //解析经纬度
		                  document.getElementById('getAddr').value = result.detail.address;
	            	      //恢复成 获取经纬度
		                  geocoderCallBack = 'getLoc';
	            	  }
	            });
				// 设置地址解析失败时的回调函数
				geocoder.setError(function(result) {
	                 marker = new qq.maps.Marker({
	                    position: center, //设置Marker的位置坐标
	                    map: map //设置显示Marker的地图
	              	 });
					 doMark();
	            });
				
	            // 如果原来的经纬度没设好，并且地址有传入。
	            if(oldLatLngExist == "0" && oldAddress != null && oldAddress != ""){
		            // 对指定地址进行解析
		            geocoder.getLocation(oldAddress);
		            
		            document.getElementById('getAddr').value = oldAddress;
	            }
	            else{
	                 //没有输入地址的话，创建一个默认的Marker
	                 marker = new qq.maps.Marker({
	                    position: center, //设置Marker的位置坐标          
	                    map: map //设置显示Marker的地图
	              	 });
					 doMark();
	            }
				
				// 用于标记的内部函数 
	            function doMark(){
					//设置Marker的可见性，为true时可见,false时不可见，默认属性为true
					marker.setVisible(true);
					//设置Marker的动画属性为从落下
					marker.setAnimation(qq.maps.MarkerAnimation.DOWN);
					//设置Marker是否可以被拖拽，为true时可拖拽，false时不可拖拽，默认属性为false
					marker.setDraggable(true);
					////设置Marker自定义图标的属性，size是图标尺寸，该尺寸为显示图标的实际尺寸，origin是切图坐标，该坐标是相对于图片左上角默认为（0,0）的相对像素坐标，anchor是锚点坐标，描述经纬度点对应图标中的位置
					var anchor = new qq.maps.Point(0, 39),
						size = new qq.maps.Size(42, 68),
						origin = new qq.maps.Point(0, 0),
						icon = new qq.maps.MarkerImage(
							"http://lbs.qq.com/doc_v2/img/nilt.png",
							size,
							origin,
							anchor
						);
					marker.setIcon(icon);
					
					//添加信息窗口
					var info = new qq.maps.InfoWindow({
						map: map
					});
					//获取标记的可拖动属性
					info.setPosition(marker.getPosition());
					//标记Marker点击事件
					qq.maps.event.addListener(marker, 'click', function() {
						info.setPosition(marker.getPosition());
					});
					//设置Marker停止拖动事件
					qq.maps.event.addListener(marker, 'dragend', function() {
						info.setPosition(marker.getPosition());
						// 设置定位好的坐标到隐藏字段。
						document.getElementById("getLat").value = marker.getPosition().getLat();
						document.getElementById("getLng").value = marker.getPosition().getLng();
						// 把经纬度逆解析成地址
						geocoderCallBack = 'getAddr';
						
						geocoder.getAddress(marker.getPosition());
					});
				}	
	        }	
	    </script>
	    		
		<div style="width:100%;height:300px;" id="container"></div>
		<div class="form-actions">
			<input id="btnSetLatLng" class="btn btn-primary" type="button" value="保存坐标"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="closeDailog()"/>
		</div>
	</div>
	
	<%@ include file="/WEB-INF/views/modules/hihunan/restaunant/hiRestaunantFormJs.jsp"%>
	
	<!-- JQgrid的中文语言包 -->
	<script src="${ctxStatic}/jqGrid5.0/js/grid.locale-cn.js" type="text/ecmascript" ></script>
	<script src="${ctxStatic}/jqGrid5.0/js/jquery.jqGrid.min.js" type="text/ecmascript" ></script>
	<!-- JQgrid的中文语言包 -->
	<script src="${ctxStatic}/jqGrid5.0/js/grid.locale-cn.js" type="text/ecmascript" ></script>
	<script src="${ctxStatic}/jqGrid5.0/js/jquery.jqGrid.min.js" type="text/ecmascript" ></script>
	<!-- 腾讯地图用 -->
	<script charset="utf-8" src="http://map.qq.com/api/js?v=2.exp"></script>
</body>
</html>