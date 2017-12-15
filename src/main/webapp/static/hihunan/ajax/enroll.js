/* 
* Created by hjk on 2016/9/10
*/

$(document).ready(function(){
    
});
var search = new UrlSearch();
var determine = search.determine;
var hospital = search.hospital;
var doctor = search.doctor;
var department = search.department;
//获取验证码
var Judge = false;
$('.get-tel span').on("click", function() {
    if (Judge == true) {
        return
    }else {
        if ($('.get-tel input').val().length == 0) {
            Show('.show-int');
            $('.show-int').html('请输入手机号码');
        }else {    
            $.ajax({
                url: Domain1+'andlife/hospital',
                type: 'post',
                dataType: 'json',
                data: {
                    action: 'get_code',
                    version: '2.0',
                    username: $('.get-tel input').val(),
                    type: '0'
                },
                success: function (data) {
                    if (data.flag == 1) {
                        Show('.show-int');
                        $('.show-int').html('发送成功');
                    };
                    if (data.flag == 2) {
                        Show('.show-int');
                        $('.show-int').html('发送失败');
                    };
                    Judge = true;
                    setTimeout(function() {
                        Judge = false;
                    }, 5000);
                }
            }) 
        }     
    }
})

//注册是否成功
$('.regist-b').on('click', function() {
   if ($('.get-tel input').val().length == 0 || $('.get-pass input').val().length == 0 || $('.order-code input').val().length == 0) {
        if ($('.get-tel input').val().length == 0) {
            Show('.show-int');
            $('.show-int').html('请输入手机号码');
            return
        };
        if ($('.get-pass input').val().length == 0) {
            Show('.show-int');
            $('.show-int').html('请输入密码');
            return
        };
        if ($('.order-code input').val().length == 0) {
            Show('.show-int');
            $('.show-int').html('请输入验证码');
            return
        };
   }else {
        key = getCookie("key");
        $.ajax({
            url: Domain1+'andlife/hospital',
            type: 'post',
            dataType: 'json',
            data: {
                action: 'register_sef',
                version: '2.0',
                username: $('.get-tel input').val(),
                password: $('.get-pass input').val(),
                code: $('.order-code input').val(),
                key: key,
            },
            success : function(data) {
                if (data.flag == 1) {
                    if (determine == 1) { 
                        window.location.href = 'city-hosp.html';        
                    };
                    if (determine == 2) {
                        window.location.href = 'cho-time.html?hospital='+ hospital +'&doctor='+doctor+'&department='+department;               
                    };                  
                };
                if (data.flag == 2) {
                    Show('.show-int');
                    $('.show-int').html('该手机号码已经注册');
                };
                if (data.flag == 3) {
                    Show('.show-int');
                    $('.show-int').html('注册失败');
                };
                if (data.flag == 4) {
                    Show('.show-int');
                    $('.show-int').html('没有此用户id');
                };
                if (data.flag == 0) {
                    Show('.show-int');
                    $('.show-int').html('输入参数不能为空');
                };
            },
        })  
   }
})
$('.t-login').on('click', function() {
    window.location.href = 'login.html?determine='+ determine+'&doctor='+doctor+'&hospital='+hospital+'&department='+department; 
})
