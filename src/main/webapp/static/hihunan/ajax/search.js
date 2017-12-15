/*
* Created by hjk on 2016/9/8.
*/
// 搜索路线
var search = new UrlSearch();
var deper = search.deper;
var strh = location.href; //取得整个地址栏
strh = decodeURI(strh); //解码
var numH = strh.indexOf("?"); //取得?后面的参数
strh = strh.substring(0,numH); //取得所有参数   stringvar.substr(start [, length ])
if (parseInt(deper)) {
    Tab(deper);   
}else {
    Tab(0);
}
function Tab(Tnum) {
    $('.con-tit>div').eq(Tnum).siblings().removeClass('select');
    $('.con-tit>div').eq(Tnum).addClass('select');
    $('.wrap-con>div').eq(Tnum).css({
        display: 'block'
    })
    $('.wrap-con>div').eq(Tnum).siblings().css({
        display: 'none'
    })   
}
$('.con-tit>div').on('click', function() {
    var index = $(this).index();
    Tab(index)    
})

var rHeight = $(window).height() - $('.con-tit').height();
$('.route-chart').css({
    height: rHeight
})
    

var deterCho;
$('#picker').on('click',function() {
    deterCho = 1;
})
var t = new Date();


// ajax部分
$(document).ready(function(){
    $('.sea-route span').click(function(){
        window.history.replaceState(null, null,strh+'?deper=0');   
        if (deterCho == 1) {
            ntd = $('#picker').val().substr(0, 2) +':' + $('#picker').val().substr(5,2);            
        }else {
            ntd = t.getHours() + ':' + t.getMinutes();; 
        }  
        //提示内容
        if ($('#site_start').val().length== 0 || $('#site_end').val().length == 0) {
            Show('.show-int');
            if ($('#site_start').val().length== 0) {
                $('.show-int').html('请输入起点');
                return
            };
            if ($('#site_end').val().length == 0) {
                $('.show-int').html('请输入终点');
                return
            };        
        }else {
            $.ajax({
                url: Domain1+'andlife/base?action=queryTransfer_new',
                type: 'post',
                dataType: 'json',
                data : {
                    version : '2.0',
                    site_start : $('#site_start').val(),
                    site_end : $('#site_end').val(),
                    site_time : ntd,
                },
                success :function(data) {  
                   if (data.flag == 1) {
                        window.location.href = 'search-result.html?nsd='+$('#site_start').val()+'&ned='+$('#site_end').val()+'&ntd=' + ntd;  
                   };
                   if (data.flag == 2) {
                        Show('.show-int');
                        $('.show-int').html('请输入正确的站点');
                   };
                   if (data.flag == 3) {
                        Show('.show-int');
                        $('.show-int').html('没有车了');
                   };
                }
            })

                   
        }
    });
});

$('.route-list div').on('click', function() {
    var index = $(this).index();
    window.history.replaceState(null, null,strh+'?deper=1')
    window.location.href = 'timetable.html?num='+index;
})



var suo = 0;
$('.con-tit>div').eq(2).on('click', function() {
    if (suo == 0) {
        $(function () {
            $('div.pinch-zoom').each(function () {
                new RTP.PinchZoom($(this), {});
            });
        }) 
        suo++;    
    };
})

//地点交换
$('.exchange').on('click', function() {
    var area = $('#site_start').val();
    $('#site_start').val($('#site_end').val());
    $('#site_end').val(area);
})



$("#picker").picker({
        toolbarTemplate: '<header class="bar bar-nav">\
  <button class="button button-link pull-right close-picker">确定</button>\
  <h1 class="title">选择时间</h1>\
  </header>',
        cols: [
            {
                textAlign: 'center',
                values: [
                    '01', '02', '03', '04', '05', '06', '07', '08',
                    '09', '10', '11', '12', '13', '14', '15', '16',
                    '17', '18', '19', '20', '21', '22', '23', 
                ]
            },
            {
                textAlign: 'center',
                values: ':'
            },
            {
                textAlign: 'center',
                values: [
                    '01', '02', '03', '04', '05', '06', '07', '08', '09',
                    '10','11','12','13','14','15','16','17','18','19',
                    '20', '21', '22', '23', '24', '25', '26', '27', '28',
                    '29', '30', '31', '32', '33', '34', '35', '36', '37',
                    '38', '39', '40', '41', '42', '43', '44', '45', '46',
                    '47', '48', '49', '50', '51', '52', '53', '54', '55', '56', '57', '58', '59'

                ]
            }
        ]
    });
$('*').css({
    'box-sizing' : 'content-box'
});