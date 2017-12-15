/*
* Created by hjk on 2016/9/7.
*/
var search = new UrlSearch();
var hospital = search.hospital;
var doctor = search.doctor;
var department = search.department;
var username = search.username;
var random = search.random;
var uid = search.uid;
var deterLogin = search.deterLogin;
$('.mask').css({
    display: 'none',
});
// 医生信息
$.ajax({
    url: Domain3,
    type: 'post',
    dataType: 'json',
    data : {
        action: 'get_doctor',
        version : '2.0',
        doctorid : doctor
    },
    success :function(data) {  
        $('.doc-con h3').html(data.message[0].name);
        $('.doc-con span').html(data.message[0].speciality)
        $('.doc-pic img').attr('src', data.message[0].photo);
    }
})
// 排班信息
var resetNum = $('.doc-time').html();
$('.doc-time').html('');

var docpid;
var tagdate;
var tagtime;
var tagid;
var sourceid;

$.ajax({
    url:Domain3,
    type: 'post',
    dataType: 'json',
    data : {
        action: 'get_plan',
        version: '2.0',
        hospitalid : hospital,
        doctorid : doctor,
    },
    success :function(data) {
        Hnum = '';
        for(var j=0; j<data.message.length; j++) {
            Hnum += resetNum;
            $('.doc-time').html(Hnum);
        }
        $.each(data.message, function(i) { 
            $('.doc-time li').eq(i).find('.order-time span').html(data.message[i].tagdate); 
            $('.doc-time li').eq(i).find('.reg-pirce strong').html('¥' +data.message[i].fee);
            $('.doc-time li').eq(i).find('.source span').html(data.message[i].countopen);
            $('.doc-time li').eq(i).find('.order-time strong').html(data.message[i].stime);
            $('.doc-time li').eq(i).find('.order-time em').html(data.message[i].etime);                
        });   
        $('.doc-time .order').on('click', function() {
            var docNum;
            docNum = $(this).parent().index();
            docpid = data.message[docNum].pid;
            tagdate = data.message[docNum].tagdate;

            //获取号源详情
            $.ajax({
                url: Domain3,
                type: 'post',
                dataType: 'json',
                data: {
                    action:'get_source',
                    version: '2.0',
                    doctorid: doctor,
                    pid: docpid,
                    tagdate: tagdate,
                },
                success: function(data) {
                    for (var i = 0; i < data.message.length; i++) {
                        if (data.message[i].status == 1) {
                            tagtime = data.message[i].tagtime;
                            tagid = data.message[i].tagid;
                            sourceid = data.message[i].id;
                            break
                        };
                    };    
                    if (parseInt(tagid)) {
                        if (parseInt(deterLogin)) {
                            $('.mask').css({
                                display: 'block'
                            })
                            window.location.href = 'book-infor.html?uid='+uid+'&random='+random+'&docpid='+docpid+'&tagdate='+tagdate+'&tagtime='+tagtime+'&tagid='+tagid+'&doctor='+doctor+'&hospital='+hospital+'&sourceid='+sourceid+'&department='+department+'&username='+username+'&deterLogin='+deterLogin;
                        }else {
                            key = getCookie("key")
                            checkLoginThenSkip(window.location);  
                        }                  
                    }else {
                        Show('.show-int');
                        $('.show-int').html('已无号源');
                        
                    }           
                }
            })                          
        })
    }
}) 

//点击预约
var key = getCookie("key");    
function checkLoginThenSkip(url) {
    var key = getCookie("key");
    if (!key) {
        window.location.href ="locahost/index.html#act=login##checkLoginThenSkip('"+url+"')";
        return false;
    }else{
        registere(key);
        return true;
    }
} 

//判断用户是否注册
function registere(key) {
    $('.mask').css({
        display: 'block'
    }) 
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
            $('.mask').css({
                display: 'none',
            });
            //该用户未注册
            if (data.flag == 3) {
                window.location.href = 'enroll.html?determine=2&doctor='+doctor+'&hospital='+hospital+'&department='+department;
            }
            if (data.flag == 2) {
                Show('.show-int');
                $('.show-int').html('登录失败');
            };
            //该用户已注册
            if (data.flag == 1) {
                $('.show-int').html('登录成功');
                var uid = data.message.uid;
                var random = data.message.random;
                window.location.href = 'book-infor.html?uid='+uid+'&random='+random+'&docpid='+docpid+'&tagdate='+tagdate+'&tagtime='+tagtime+'&tagid='+tagid+'&doctor='+doctor+'&hospital='+hospital+'&sourceid='+sourceid+'&department='+department;
            };
        },
    })    
};
;
function afterLogin(e,url) {
    var key = getCookie("key");
    if(url.indexOf('##') != - 1){
        var index = url.indexOf('##')+2;
    }else{
        var index = url.indexOf('%23%23')+6;
    }
    if(url != null && url != ''){
        eval(url.substr(index));
    }
}


