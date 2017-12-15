/*
*Created by hjk on 2016/9/8
*/
function UrlSearch() {
    var name,value; 
    var str = location.href; //取得整个地址栏
    str = decodeURI(str); //解码
    var num = str.indexOf("?"); //取得?后面的参数
    str = str.substr(num + 1); //取得所有参数   stringvar.substr(start [, length ])
    var arr = str.split("&"); //各个参数放到数组里
    for(var i = 0;i < arr.length;i++){ 
        num = arr[i].indexOf("="); 
        if(num > 0){ 
            name = arr[i].substring(0,num);
            value = arr[i].substr(num + 1);
            this[name] = value;
        }
    }
}
var search = new UrlSearch();
var lineN = search.lineN;
var zid = search.zid;
if (lineN == 1) {
   $('.num-route').css({
       ' background': "url('../images/green-1.png') no-repeat",
        'background-size': '100% 100%'
   }) 
   $('.rolute-l li').eq(0).find('a').html('尚双塘方向');
   $('.rolute-l li').eq(1).find('a').html('开福区政府方向');
}
if (lineN == 2) {
    $('.num-route').css({
       ' background': "url('../images/green-2.png') no-repeat",
        'background-size': '100% 100%'
   }) 
    $('.rolute-l li').eq(0).find('a').html('光大方向');
    $('.rolute-l li').eq(1).find('a').html('梅溪湖西方向');       
};
$('.rolute-l li a').on('click', function() {
    //方向
    var dir = $(this).parent().index()+1;
    window.location.href = 'timetable-s.html?dir='+ dir +'&zid='+zid +'&lineN='+lineN;
})  
