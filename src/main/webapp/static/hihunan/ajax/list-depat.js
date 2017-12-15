/*
* Created by hjk on 2016/9/7.
*/
var search = new UrlSearch();
var hospital = search.hospital;
var deterLogin = search.deterLogin;
var username = search.username;
var random = search.random;
var uid = search.uid;
//医院具体信息
$.ajax({
    url: Domain1+'andlife/hospital',
    type: 'post',
    dataType: 'json',
    data : {
        action: 'hospital_info',
        version : '2.0',
        hospitalid : hospital,
    },
    success :function(data) {
        var con = data.message.description;                  
        $('.host-int-c p').html(con);
        if (con.length == 0) {
            $('.more').css({
                display: 'none'
            })
        };
        $('.host-pic').attr('src', data.message.photo);
        $('.host-add span').html(data.message.level)
        $('.host-add strong').html(data.message.address) 
        $('.host-add em').html(data.message.name)      
    }
})

// 获取科室
var crr = [];
var resetNum='<a></a>';
var Hnum;
$.ajax({
    url: Domain1+'andlife/hospital',
    type: 'post',
    dataType: 'json',
    data: {
        action : 'get_subset',
        version : '2.0',
        hospitalid : hospital,
    },
    success:function(data) {
        if (data.flag == 1) {
            Dnum = '';
            for(var j=0; j<data.message.length; j++) {
                Dnum += resetNum;
                $('.depat').html(Dnum);
            }
            $('.depat a:last').css({
                'box-shadow': 'none'
            })
            $.each(data.message, function(i) {                       
                $('.depat a').eq(i).html(data.message[i].name);
                crr.push(data.message[i].subsetid);
            });  
            $('.depat a').on('click', function() {
                var index = $(this).index();
                deparNum = crr[index];
                window.location.href = 'dep-doc.html?department='+ deparNum+'&hospital='+hospital+'&deterLogin='+deterLogin+'&username='+username+'&uid='+uid+'&random='+random;         
            })              
        }
    },
})

//显示隐藏内容
var Judge = false;
$('.more').on('click', function() {
    if (Judge == false) {
        $('.host-int-c p').css({
            height : 'auto',
        })
        $('.more').css({
            'background': "url('../images/shang.png') center no-repeat",
            'background-size': '1.88rem'
        })
        Judge = true;
    }else {
        $('.host-int-c p').css({
            height : '5rem',
        })
        $('.more').css({
            'background': "url('../images/xia.png') center no-repeat",
            'background-size': '1.88rem'
        })
        Judge = false;
    } 
})
  