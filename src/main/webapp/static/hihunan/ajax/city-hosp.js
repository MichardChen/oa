/*
* Created by hjk on 2016/9/7.
*/
var search = new UrlSearch();
var username = search.username;
var conindex = search.conindex;
var random = search.random;
var uid = search.uid;
var deterLogin = search.deterLogin;
if (conindex == 1) {
    qTab(1);
}else {
    $('.cho-tit>div').eq(0).addClass('cho-tit-c');
    $('.cho-tit>div').eq(0).siblings().removeClass('cho-tit-c');
    $('.cho-con>div').eq(0).css({
        display: 'block'
    });
    $('.cho-con>div').eq(0).siblings().css({
        display: 'none'
    })
} 
$('.cho-tit div').on('click', function() {
    conindex = $(this).index();
    if (conindex == 1) {
        qTab(conindex);
    }else {
        $('.cho-tit>div').eq(conindex).addClass('cho-tit-c');
        $('.cho-tit>div').eq(conindex).siblings().removeClass('cho-tit-c');
        $('.cho-con>div').eq(conindex).css({
            display: 'block'
        });
        $('.cho-con>div').eq(conindex).siblings().css({
            display: 'none'
        })
    } 
})

function qTab(conindex) {
    var key = getCookie("key");
    key = getCookie("key");
    if (parseInt(deterLogin)) {
        getInd();
        $('.cho-tit>div').eq(conindex).addClass('cho-tit-c');
        $('.cho-tit>div').eq(conindex).siblings().removeClass('cho-tit-c');
        $('.cho-con>div').eq(conindex).css({
            display: 'block'
        });
        $('.cho-con>div').eq(conindex).siblings().css({
            display: 'none'
        })
    }else {
        if (!key) {
            checkLoginThenSkip(window.location);
        }else {
            $.ajax({
                url: Domain3,
                type: 'post',
                dataType: 'json',
                data : {
                    action: 'login',
                    version : '2.0',
                    key : key,
                },
                success :function(data) {
                    //该用户未注册
                    if (data.flag == 3) {
                        window.location.href = 'enroll.html?determine=1';
                    }
                    //该用户已注册
                    if (data.flag == 1) {
                        username = data.message.username;
                        random = data.message.random;
                        getInd();
                        $('.cho-tit>div').eq(conindex).addClass('cho-tit-c');
                        $('.cho-tit>div').eq(conindex).siblings().removeClass('cho-tit-c');
                        $('.cho-con>div').eq(conindex).css({
                            display: 'block'
                        });
                        $('.cho-con>div').eq(conindex).siblings().css({
                            display: 'none'
                        })
                    };
                }
            }) 
        }   
    }
}

var brr = [];
var resetNum = $('.hosp-con').html();
$('.hosp-con').html('');
var Hnum;
window.onload=function(){
    colorNum = 2;
    $.ajax({
        url: Domain3,
        type: 'post',
        dataType: 'json',
        data: {
            action : 'list_hospital',
            version : '2.0',
            city : '0731',
        },
        success:function(data) {
            Hnum = ''
            for(var j=0; j<data.message.length; j++) {
                Hnum += resetNum;
                $('.hosp-con').html(Hnum);
            }
            $.each(data.message, function(i) {                      
                $('.hosp-con a').eq(i).find('span').html(data.message[i].name);
                $('.hosp-con a').eq(i).find('strong').html(data.message[i].level);
                brr.push(data.message[i].id)
            });    
            $('.hosp-con a').on('click', function() {
                var index = $(this).index();
                hostNum = brr[index];
                window.location.href = 'list-depat.html?hospital='+ hostNum+'&deterLogin='+deterLogin+'&username='+username+'&uid='+uid+'&random='+random;         
            })
        },
    })     
};


        function afterLogin(e, url) {
            var key = getCookie("key");
            if (url.indexOf('##') != -1) {
                var index = url.indexOf('##') + 2;
            } else {
                var index = url.indexOf('%23%23') + 6;
            }  
            enchage();  
            qTab(1);       
            if (url != null && url != '') {
                eval(url.substr(index));
            }
        }

    
        //点击预约
        var key = getCookie("key");    
        function checkLoginThenSkip(url) {
            var key = getCookie("key");
            if (!key) {
                window.location.href ="locahost/index.html#act=login##checkLoginThenSkip('"+url+"')";
                return false;
            }else{
                return true;
            }
        } 
        //登录后运行
        function enchage() {
            key = getCookie("key");          
            $.ajax({
                url: Domain3,
                type: 'post',
                dataType: 'json',
                data : {
                    action: 'login',
                    version : '2.0',
                    key : key,
                },
                success :function(data) {
                    //该用户未注册
                    if (data.flag == 3) {
                        window.location.href = 'enroll.html?determine=1';
                    }
                    //该用户已注册
                    if (data.flag == 1) {
                        username = data.message.username;
                        random = data.message.random;
                        getInd();
                    };
                }
            })             
        }

        //获取预约信息
        var reCho;
        var registH = '';
        var orderid = [];
        var mobile = [];
        function getInd() {
            $.ajax({
                url: Domain3,
                type: 'post',
                dataType: 'json',
                data: {
                    action: 'list_order',
                    version: '2.0',
                    username: username,
                    random: random,
                    f: 'a',
                },
                success: function(data) {
                  
                    reset = '<ul><li><strong>挂号时间:</strong><span class="time"></span></li><li><strong>就诊人:</strong><span class="name"></span></li><li><strong>医院:</strong><span class="hosp"></span></li><li><strong>科室:</strong><span class="depa"></span></li><div class="remove-re">取消预约</div></ul>' ;
                    $('.regist-ind').html('');
                    registH = '';
                    data = JSON.parse(data.message.yuyue);
                    for (var i = 0; i < data.length; i++) {
                        registH+=reset;
                        $('.regist-ind').html(registH);
                    };
                    for (var i = 0; i < data.length; i++) {
                        $('.regist-ind ul').eq(i).find('.time').html(data[i].reg_datetime);
                        $('.regist-ind ul').eq(i).find('.name').html(data[i].name);
                        $('.regist-ind ul').eq(i).find('.hosp').html(data[i].hospital);
                        $('.regist-ind ul').eq(i).find('.depa').html(data[i].subset);    
                        orderid.push(data[i].orderid);  
                        mobile.push(data[i].mobile);        
                    };

                    $('.remove-re').on('click', function() {
                        reCho = $(this).parent().index();                     
                        ASSS();
                    })
                }
            })             
        }
        //删除预约
        function ASSS() {
            $.ajax({
                url: Domain3,
                type: 'post',
                dataType: 'json',
                data: {
                    action: 'cancel_order',
                    version: '2.0',
                    username: username,
                    random: random,
                    orderid: orderid[reCho],
                },
                success: function(data) {
                    if (data.flag == 1) {
                        Show('.show-int');
                        $('.show-int').html('取消成功');
                        $('.regist-ind ul').eq(reCho).remove();
                    }else {
                        Show('.show-int');
                        $('.show-int').html('取消失败');
                    }
                },
            })      
        }


//模糊搜索
$('.sea-city span').on('click', function() {
    tan();
})


function tan() {
    if ($('#keyword-input').val().length != 0) {
        document.activeElement.blur();
        $.ajax({
            url: Domain3,
            type: 'post',
            dataType: 'json',
            data: {
                action : 'list_hospital',
                version : '2.0',
                hname : $('#keyword-input').val(),
            },
            success:function(data) {
                brr = [];
                Hnum = '';
                $('.hosp-con').html('');   
                if (data.flag == 1) {
                    for(var j=0; j<data.message.length; j++) {
                        Hnum += resetNum;
                        $('.hosp-con').html(Hnum);
                    }
                    $.each(data.message, function(i) {                      
                        $('.hosp-con a').eq(i).find('span').html(data.message[i].name);
                        $('.hosp-con a').eq(i).find('strong').html(data.message[i].level);
                        brr.push(data.message[i].id)
                    });    
                    $('.hosp-con a').on('click', function() {
                        var index = $(this).index();
                        hostNum = brr[index];
                        window.location.href = 'list-depat.html?hospital='+ hostNum+'&deterLogin='+deterLogin+'&username='+username+'&uid='+uid+'&random='+random;         
                    })                               
                };
            },
        })          
    };
    event.preventDefault ? event.preventDefault() : event.returnValue = false;
    return false;
}

$('#keyword-input').on('input', function() {
    if ($('#keyword-input').val().length != 0) {
        $('.sea-city p').css({
            display: 'block'
        })
    }else {
        $('.sea-city p').css({
            display: 'none'
        })
    }
})
$('.sea-city p').on('click', function() {
    $('#keyword-input').val('');
    $('.sea-city p').css({
        display: 'none'
    })
})