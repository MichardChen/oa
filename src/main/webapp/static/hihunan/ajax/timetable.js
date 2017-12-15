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
var cnum = search.num;
var lineN = parseInt(cnum) + 1;
var resetNum = $('.rolute-l').html();
$('.rolute-l').html('');
// 获取站点列表
var irr=[];//站点id
$.ajax({
    url: Domain1+'andlife/base?action=getLine',
    type: 'post',
    dataType: 'json',
    data : {
        version : '2.0',
        line_id : lineN,
    },
    success :function(data) {
        Hnum = '';
        for(var j=0; j<data.message.length; j++) {
            Hnum += resetNum;
            $('.rolute-l').html(Hnum);
        }
        $.each(data.message, function(i) { 
            $('.rolute-l li').eq(i).find('a').html(data.message[i].site);
            irr.push(data.message[i].id);          
        });  
        var len = $('.rolute-l li').length;
        $('.rolute-l li').eq(len-1).css({
            background : "url('../images/bus-3.png') left -1.5rem no-repeat",
            'background-size': '0.5rem'

        })
        $('.rolute-l li a').on('click', function() {
            var index = $(this).parent().index();
            var zid = irr[index]
            window.location.href = 'timetable-c.html?lineN='+ lineN +'&zid='+zid;
        })
    }
})
if (cnum == 0) {
   $('.num-route').css({
       ' background': "url('../images/green-1.png') no-repeat",
        'background-size': '100% 100%'
   }) 
}else {
    $('.num-route').css({
       ' background': "url('../images/green-2.png') no-repeat",
        'background-size': '100% 100%'
   }) 
}
$('.rolute-l li:last-child').css({
    'background' : "url('../images/bus-3.png') left -1.3rem no-repeat;",
    'background-size': '0.5rem'
})