/* 
* Created by hjk on 2016/9/10
*/

var search = new UrlSearch();
var determine = search.determine;
var hospital = search.hospital;
var doctor = search.doctor;
var department = search.department;
var deterLogin = 1;
$('.regist-b').on('click', function() {
    if ($('.get-tel input').val().length == 0 || $('.get-pass input').val().length == 0) {
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
    }else {
        $.ajax({
            url: Domain1+'andlife/hospital',
            type: 'post',
            dataType: 'json',
            data: {
                action: 'login_sef',
                version: '2.0',
                username: $('.get-tel input').val(),
                password: $('.get-pass input').val(),
            },
            success : function(data) {
                uid = data.message.uid;
                random = data.message.random;
                username = data.message.username;
                if (data.flag == 1) {
                    if (determine == 1) {
                        window.location.href = 'city-hosp.html?username='+username+'&random='+random+'&uid='+uid+'&deterLogin='+deterLogin;    
                    };
                    if (determine == 2) {
                        window.location.href = 'cho-time.html?hospital='+ hospital +'&doctor='+doctor+'&department='+department+'&username='+username+'&deterLogin='+deterLogin+'&uid='+uid+'&random='+random;                
                    };                  
                };
                if (data.flag == 2) {
                    Show('.show-int');
                    $('.show-int').html('登录失败');                
                };
            },
        }) 
    }
})