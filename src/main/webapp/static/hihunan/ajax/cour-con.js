/* 
* Created by hjk on 2016/10/11
*/
var search = new UrlSearch();
var courName = search.courName;
var courNum = search.courNum;
$('.order-num span').html(courNum);
var Kreset = $('.rolute-l').html();
var resetCon = '';
$('.rolute-l').html('');
$.ajax({
    url: Domain1+'andlife/base?action=getExpress',
    type: 'post',
    dataType: 'json',
    data: {
        version : '2.0',
        code: courName,
        postid: courNum
    },
    success : function(data) {
        $('.status span').html(data.state);
        $('.cour-con span').html(data.com);
        for (var i = 0; i < data.message.length; i++) {
            resetCon += Kreset;
            $('.rolute-l').html(resetCon);
        };
        for (var i = 0; i < data.message.length; i++) {              
            $('.rolute-l li').eq(i).find('span').html(data.message[i].context);
            $('.rolute-l li').eq(i).find('strong').html(data.message[i].ftime);
        }; 
        $('.rolute-l li:last-child').css({
            'background' : "url('../images/courier-3.png') left center no-repeat;",
            'background-size': '0.83rem'
        })
    }
})