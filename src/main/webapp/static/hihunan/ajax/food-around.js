/* 
* @Author: hjk
* @Date:   2016-10-25 14:27:31
* @Last Modified by:   anchen
* @Last Modified time: 2016-11-03 09:02:45
*/
/*
 * 功能：计算两地具体
 * 参数：纬度、精度
 */
var search = new UrlSearch();
var id = search.id;

var getDistance=function(lat1Str, lng1Str, lat2Str, lng2Str) {
    var lat1 = parseFloat (lat1Str);
    var lng1 = parseFloat (lng1Str);
    var lat2 = parseFloat (lat2Str);
    var lng2 = parseFloat (lng2Str);
     
    var radLat1 = lat1 * Math.PI / 180.0;
    var radLat2 = lat2 * Math.PI / 180.0;
    var difference = radLat1 - radLat2;
    var mdifference = lng1 * Math.PI / 180.0 - lng2 * Math.PI / 180.0 ;
    var distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(difference / 2), 2)
            + Math.cos(radLat1) * Math.cos(radLat2)
            * Math.pow(Math.sin(mdifference / 2), 2)));
    distance = distance * 6378137;
    distance = parseInt(Math.round(distance * 10000) / 10000);
     
    return distance;
};

$(document).ready(function(){
    
});

$('.con-tit div').on('click', function() {
var index = $(this).index();
$('.con-tit div').eq(index).addClass('select');
$('.con-tit div').eq(index).siblings().removeClass('select');
$('.con-around>div').eq(index).addClass('show');
$('.con-around>div').eq(index).siblings().removeClass('show');
})


//获取坐标
var loc_x = getCookie("loc_x");
var loc_y = getCookie("loc_y");


var points = [new BMap.Point(loc_x,loc_y)];
    //地图初始化
var bm = new BMap.Map("container");
//坐标转换完之后的回调函数
translateCallback = function (data){   
   

    if(data.status === 0) {
    //正确的地点

        bm.centerAndZoom(new BMap.Point(data.points[0].lng,data.points[0].lat), 15);
        


        //创建小狐狸
        var pt = new BMap.Point(data.points[0].lng,data.points[0].lat)
        // var myIcon = new BMap.Icon("../images/btn1.png", new BMap.Size(300,157));
        var marker2 = new BMap.Marker(pt);  // 创建标注
        bm.addOverlay(marker2); 
		marker2.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画



        $.ajax({
            url: Domain2+'shop/listShopByTypeAndLoaction',
            type: 'get',
            dataType: 'json',
            data: {
                type : id,
                lng : data.points[0].lng,
                lat : data.points[0].lat,
            },
            success : function(data) {
                if (data.code == '200') {
                    var d = new Array();
                    var eatId;
                    for(var j=0; j<data.shops.length; j++) {
                        var name = data.shops[j].name;
                        var addre = data.shops[j].address;
                        var lng = data.shops[j].lng;
                        var lat = data.shops[j].lat;
                        var id = data.shops[j].id;
                        var temp = [lng,lat,name,addre,id];
                        d.push(temp);
                    }
                    //地图         
                    var data_info = d;
                    for(var i=0;i<data_info.length;i++){
                        var marker = new BMap.Marker(new BMap.Point(data_info[i][0],data_info[i][1]));  // 创建标注
                        var content = data_info[i][2];//店铺名
                        var conbtn = data_info[i][3];//店铺地址
                        var eatId = data_info[i][4];//店铺ID

                        bm.addOverlay(marker);               // 将标注添加到地图中
                        addClickHandler(content,marker,conbtn,eatId);
                       
                    }
                    function addClickHandler(content,marker,conbtn,eatId){
                        marker.addEventListener("click",function(e){
                            bm.centerAndZoom(new BMap.Point(e.target.getPosition().lng,e.target.getPosition().lat), 15);

                            bm.enableScrollWheelZoom();
                            // 复杂的自定义覆盖物
                            function ComplexCustomOverlay(point, text){
                                this._point = point;
                                this._text = text;
                            }
                            ComplexCustomOverlay.prototype = new BMap.Overlay();
                            ComplexCustomOverlay.prototype.initialize = function(map){
                                this._map = map;
                                bm.getPanes().labelPane.innerHTML = '';
                                var div = this._div = document.createElement("div");

                                div.style.position = "absolute";
                                div.style.zIndex = BMap.Overlay.getZIndex(this._point.lat);
                                div.style.border = "1px solid #BC3B3A";
                                div.style.height = "36px";
                                div.style.width = "200px";
                                div.style.lineHeight = "18px";
								div.style.background = 'white';
                                div.style.MozUserSelect = "none";
                                div.style.fontSize = "12px";
                                div.style.zIndex = "10000"
                                var span = this._span = document.createElement("span");
                                span.className = 'detail-spa';
                                div.appendChild(span);
                                span.appendChild(document.createTextNode(this._text));      
                                var that = this;
                              
                                var Astrong = document.createElement("strong");
                                Astrong.innerHTML = '了解详情';
                                Astrong.className = 'detail-str';
                                div.appendChild(Astrong);
                                

                                var hTit = document.createElement("h2");
                                hTit.innerHTML = conbtn;
                                hTit.className = 'detail-h';
                                div.appendChild(hTit);

                                
                                //添加事件
                                function showInfo(e){  
                                    window.location.href = 'food-pic.html?eatId='+eatId;                
                                }
                                Astrong.addEventListener("touchstart", showInfo);



                                var arrow = this._arrow = document.createElement("div");
                                arrow.style.background = "url(http://map.baidu.com/fwmap/upload/r/map/fwmap/static/house/images/label.png) no-repeat";
                                arrow.style.position = "absolute";
                                arrow.style.width = "11px";
                                arrow.style.height = "10px";
                                arrow.style.top = "37px";
                                arrow.style.left = "10px";
                                arrow.style.overflow = "hidden";
                                div.appendChild(arrow);
                                bm.getPanes().labelPane.appendChild(div);    
                                return div;
                            }
                            ComplexCustomOverlay.prototype.draw = function(){
                                var map = this._map;
                                var pixel = map.pointToOverlayPixel(this._point);
                                this._div.style.left = pixel.x - parseInt(this._arrow.style.left) + "px";
                                this._div.style.top  = pixel.y - 66 + "px";
                            }
                                
                            var myCompOverlay = new ComplexCustomOverlay(new BMap.Point(e.target.getPosition().lng,e.target.getPosition().lat), content);

                            bm.addOverlay(myCompOverlay);
                            
                        });
                    }

                    
                    var resetList = $('#con-list').html();
                    var resetCon = '';
                    $('#con-list').html('');
                    var Iarr = [];
                    console.log(data);
                    //列表
                    for(var j=0; j<data.shops.length; j++) {
                        resetCon += resetList;
                        $('#con-list').html(resetCon);
                    }
                    $.each(data.shops, function(i) {  
                        var dis = getDistance(data.shops[i].lng,data.shops[i].lat,data.points[0].lng,data.points[0].lat); 
                        $('#con-list dl').eq(i).find('img').attr('src', data.shops[i].icon);  
                        $('#con-list dl').eq(i).find('h2').html(data.shops[i].name);
                        $('#con-list dl').eq(i).find('div span').html(data.shops[i].descb);  
                        $('#con-list dl').eq(i).find('div strong').html('人均'+data.shops[i].percapita+'元'); 
                        $('#con-list dl').eq(i).find('section span').html(data.shops[i].address);                 
                        $('#con-list dl').eq(i).find('section strong').html(dis + 'm');
                        Iarr.push(data.shops[i].id);
                    });
                    $('#con-list dl').on('click', function() {
                        var index = $(this).index();
                        var eatId = Iarr[index];
                        window.location.href = 'food-pic.html?eatId='+eatId;
                    })
                };               
            }
        })
    }
    
}
setTimeout(function(){
    var convertor = new BMap.Convertor();
    convertor.translate(points, 1, 5, translateCallback);        
}, 1000);







