/*
*Created by hjk on 2016/9/7
*/
var search = new UrlSearch();
var nsd = search.nsd;
var ned = search.ned;
var ntd = search.ntd;
var resetNum = $('.wrap').html();
$('.wrap').html('');
$.ajax({
    url: Domain1+'andlife/base?action=queryTransfer_new',
    type: 'post',
    dataType: 'json',
    data : {
        version : '2.0',
        site_start : nsd,
        site_end : ned,
        site_time : ntd,
    },
    success :function(data) {
        Hnum = '';
        for(var j=0; j<data.message.length; j++) {
            Hnum += resetNum;
            $('.wrap').html(Hnum);
        }      
        $.each(data.message, function(i) {
            $('.wrap a').eq(i).find('.route span').html(nsd); 
            $('.wrap a').eq(i).find('.route strong').html(ned);
            $('.wrap a').eq(i).find('.time span').html(data.message[i].time_start);
            $('.wrap a').eq(i).find('.time strong').html(data.message[i].time_end);
            $('.wrap a').eq(i).find('.r-details span').html(data.message[i].line); 
            $('.wrap a').eq(i).find('.r-details strong').html(data.message[i].time);  
            $('.wrap a').eq(i).find('.price span').html(data.message[i].price);              
        });   
        $('.wrap a').on('click', function() {
            var start = $('.wrap').eq(0).find('.route span').html();
            var end = $('.wrap').eq(0).find('.route strong').html();
            window.location.href = 'route-details.html?nsd='+ start +'&ned=' +end;         
        })   
    }
})
