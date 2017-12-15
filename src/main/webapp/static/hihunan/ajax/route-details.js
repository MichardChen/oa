/*
*Created by hjk on 2016/9/7
*/
//获取URL地址，取出索引值
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
var nsd = search.nsd;
var ned = search.ned
$(document).ready(function(){
    $.ajax({
        type:'post',
        url:Domain1+'/andlife/base?action=queryTransfer',
        data:{
            version:'2.0',
            site_start: nsd,
            site_end: ned,
        },
        dataType:'json',
        success:function(data){
            var cont = '<li><span></span></li>';
            var con = $('.rolute-l').html();
            for (var i=0; i<data.message.list.length; i++) {
                con += cont;
                $('.rolute-l').html(con);
            }
            for (var i=0; i<data.message.list.length; i++) {
                $('.rolute-l li').eq(i).html(data.message.list[i]);
            }
            $('.time span').html(data.message.time);
            $('.transfer span').html(data.message.huancheng +'次');
            $('.price span').html(data.message.price);
            var len = $('.rolute-l li').length;
            $('.rolute-l li').eq(len-1).css({
                'background' : "url('../images/bus-3.png') left -1.4rem no-repeat",
            })
            $('.rolute-l li').css({
                'background-size': '0.5rem'
            })
        },
    })

});

