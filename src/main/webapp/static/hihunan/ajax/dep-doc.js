/*
* Created by hjk on 2016/9/7
*/
var search = new UrlSearch();
var hospital = search.hospital;
var department = search.department;
var deterLogin = search.deterLogin;
var username = search.username;
var random = search.random;
var uid = search.uid;
//科室信息
$.ajax({
    url: Domain1+'andlife/hospital',
    type: 'post',
    dataType: 'json',
    data: {
        action : 'get_subset',
        version : '2.0',
        hospitalid : hospital,
        subsetid : department,
    },
    success:function(data) {
        if (data.message[0].description.length == 0) {
            $('.more').css({
                display: 'none'
            })
            $('.doc-int').css({
               ' padding-top' : '2.25rem'
            })
        };
        $('.introduce p').html(data.message[0].description)
    },
})

$.ajax({
    url: Domain1+'andlife/hospital',
    type: 'post',
    dataType: 'json',
    data: {
        action : 'get_sub_date',
        version : '2.0',
        hospitalid : hospital,
        subsetid : department,
    },
    success:function(data) {
        $('.introduce strong').html(data.message[0].countopen)
    },
})

// 医生信息
var drr=[];
var resetNum = $('.doc-int').html();
$('.doc-int').html('');
$.ajax({
    url: Domain1+'andlife/hospital',
    type: 'post',
    dataType: 'json',
    data : {
        action: 'get_doctor',
        version : '2.0',
        subsetid : department
    },
    success :function(data) {
        Hnum = '';
        for(var j=0; j<data.message.length; j++) {
            Hnum += resetNum;
            $('.doc-int').html(Hnum);
        }
        $.each(data.message, function(i) { 
            $('.introduce span').html(data.message[i].subsetname+'简介');
            $('.doc-int dl').eq(i).find('img').attr("src",data.message[i].photo);
            $('.doc-int dl').eq(i).find('.doc-name').html(data.message[i].name);
            $('.doc-int dl').eq(i).find('.doc-pos').html(data.message[i].rank);
            $('.doc-int dl').eq(i).find('.sour-num').html(data.message[i].countopen)  
            drr.push(data.message[i].id);  
            // console.log(data)                
        });
        $('.doc-int>a').on('click', function() {
            var index = $(this).index();
            docNum = drr[index];
            window.location.href = 'cho-time.html?hospital='+ hospital +'&doctor='+docNum+'&department='+department+'&deterLogin='+deterLogin+'&username='+username+'&uid='+uid+'&random='+random;
        })         
    }
})

  
//显示隐藏内容
    var Judge = false;
    $('.introduce>div').on('click', function() {
        if (Judge == false) {
            $('.introduce p').css({
                height : 'auto',
            })
            $('.introduce').css({
                height : 'auto'
            })
            Judge = true;
            var cheight = $('.introduce p').height()/24;
            $('.doc-int').css({
                'padding-top' :  cheight + 'rem'
            })
            $('.more').css({
                'background': "url('../images/shang.png') center no-repeat",
                'background-size': '1.88rem'
            })
        }else {
            $('.introduce p').css({
                height : '5rem',
            })
            $('.introduce').css({
                height : '14.04rem',
            })
            $('.doc-int').css({
                'padding-top' : 5.04 + 'rem'
            })
            $('.more').css({
                'background': "url('../images/xia.png') center no-repeat",
                'background-size': '1.88rem'
            })
            Judge = false;
        }   
    })
