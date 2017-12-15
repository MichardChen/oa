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
var dir = search.dir;
var zid = search.zid;
var lineN = search.lineN;
if (lineN == 1) {
   $('.num-route').css({
       ' background': "url('../images/green-1.png') no-repeat",
        'background-size': '100% 100%'
   }) 
}
if (lineN == 2) {
    $('.num-route').css({
       ' background': "url('../images/green-2.png') no-repeat",
        'background-size': '100% 100%'
   })      
};


var con = $('.list').html();
$('.list').html('');
var box = '';
var conD = '<span></span>';  
var boxD = '';

var numCon = $('.num-list').html();
var numBox = '';

$.ajax({
    url: Domain1+'andlife/base?action=querySubwayTime',
    type: 'post',
    dataType: 'json',
    data : {
        version : '2.0',
        site_id : zid,
        type : dir
    },
    success :function(data) {
        // 将数据按对象顺序排序
        console.log(data)
        data.message.sort(function(a,b) {
            var i = parseInt(a.hour);
            var j = parseInt(b.hour);
            if(i>j) {
                return 1;
            } else {
                return -1;
            }         
        });
        // 创建Li
        for (var i = 0; i < data.message.length; i++) {
            box += con;
            $('.list').html(box);
            numBox += numCon;
            $('.num-list').html(numBox);
            boxD = ''; 

        };
        // 创建span
        for (var i = 0; i < data.message.length; i++) {     
            $('.list li').eq(i).find('h2').html(data.message[i].hour); 
            $('.num-list li').eq(i).html(data.message[i].hour);   
            for (var j = 0; j < data.message[i].listtime.length; j++) {
                boxD += conD;
                $('.list li').eq(i).find('.time-num').html(boxD);
            };
        };
        for (var i = 0; i < data.message.length; i++) {        
            for (var j = 0; j < data.message[i].listtime.length; j++) {
                $('.list li').eq(i).find('.time-num span').eq(j).html(data.message[i].listtime[j])
            };
        };
        //点击快速定位
        $('.num-list li').on('click', function() {
            var index = $(this).index();
            var kHeight = 0;
            for (var i = 0; i < index; i++) {
                kHeight += $('.list li').eq(i).height();
            };
            var dis = kHeight + $('.num-route').height();
            $('body').scrollTop(dis)
        }) 
    }
})

    