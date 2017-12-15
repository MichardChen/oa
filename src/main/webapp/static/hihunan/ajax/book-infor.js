/* 
* Created by hjk on 2016/9/10
*/
var search = new UrlSearch();
var hospital = search.hospital;
var doctor = search.doctor;
var uid = search.uid;
var random = search.random;
var docpid = search.docpid;
var tagdate = search.tagdate;
var tagtime = search.tagtime;
var tagid = search.tagid;
var department = search.department;
var sourceid = search.sourceid;
var username = search.username;
var deterLogin = search.deterLogin;
var sexN = 1; 
$('.gender span').on('click', function() {
    sexN = $(this).index();
    $(this).addClass('cho-s');
    $(this).siblings().removeClass('cho-s')
})
//给后台传入预约人信息
var Judge = false;
$('.add-used').on('click', function() {
    if ($('.name input').val().length == 0 || $('.id-card input').val().length == 0 || $('.tel input').val().length == 0) {
        if ($('.name input').val().length == 0) {
            Show('.show-int');
            $('.show-int').html('请输入姓名')
            return
        };
        if ($('.tel input').val().length == 0) {
            Show('.show-int');
            $('.show-int').html('请输入手机号码')
            return
        };
        if ($('.id-card input').val().length == 0) {
            Show('.show-int');
            $('.show-int').html('请输入身份证')
            return
        };
    }else {
        if ($('.cho-peo>section').length >= 7) {
            ShowF('.show-f');
            $('.show-f').html('添加联系人失败，最多只能添加7个联系人');
        }else {
            if (Judge == false) {
                Judge = true;
                $.ajax({
                    url: Domain3,
                    type: 'post',
                    dataType: 'json',
                    data: {
                        action: 'add_family',
                        version: '2.0',
                        uid: uid,
                        random: random,
                        name: $('.name input').val(),
                        sex: sexN,
                        idcard: $('.id-card input').val(),
                        phone: $('.tel input').val(),
                    },
                    success : function(data) {
                        if (data.flag == 1) {
                            Show('.show-int');
                            $('.show-int').html('添加成功')
                            add();
                            setTimeout(function(){
                                Judge = false;
                            }, 3000)
                            
                        }else {
                            Show('.show-int');
                            $('.show-int').html('添加失败');
                            setTimeout(function(){
                                Judge = false;
                            }, 3000)
                        }       
                    },
                })              
            };        
        }
    }
})
//获取我的就诊人
var resrtN = '<section><div>姓名：<span></span></div><p></p></section>';
var ind = '';
var yid = [];
var phone = [];
var choIndex;//选中人
var shenfen = [];
var mname=[];
var sex =[];
$('.cho-peo').html('');
function add() {
    ind = '';
    $('.cho-peo').html('');
    $.ajax({
        url: Domain3,
        type: 'post',
        dataType: 'json',
        data: {
            action: 'family_list',
            version: '2.0',
            uid: uid,
            random: random,
        },
        success : function(data) {
            if (data.flag == 1) {
                for (var i = 0; i < data.message.length; i++) {
                    ind += resrtN;
                    $('.cho-peo').html(ind);
                };
                $('.cho-peo section:last-child').css({
                    'box-shadow': 'none'
                })
                $('.cho-peo section').on('click', function() {
                    choIndex = $(this).index();
                    $(this).addClass('cho-color');
                    $(this).siblings().removeClass('cho-color')
                })
                for (var i = 0; i < data.message.length; i++) {              
                    $('.cho-peo section').eq(i).find('span').html(data.message[i].name);
                    $('.cho-peo section').eq(i).find('p').html(data.message[i].phone);
                    yid.push(data.message[i].id);
                    phone.push(data.message[i].phone);
                    shenfen.push(data.message[i].idcard);
                    mname.push(data.message[i].name);
                    sex.push(data.message[i].sex);
                };               
            };
        }
    })   
}add()
 
//确定预约
var orderid;
var ispay;
var fee;
var msg;
var status;
var subject;
var show_url;
$('.order-peo').on("click", function() {
    if (parseInt(choIndex+1)) {       
        var send_data = {
            action:'order',
            version: '2.0',
            uid: uid,
            random: random,
            yid: yid[choIndex],
            phone: phone[choIndex],
            hospitalid: hospital,
            doctorid: doctor,
            pid: docpid,
            tagdate: tagdate,
            tagtime: tagtime,
            tagid: tagid,
            subsetid: department,
            sourceid: sourceid,
            idcard: shenfen[choIndex],
            name: mname[choIndex],
            sex: sex[choIndex],
        };
        $.ajax({
            url: Domain3,
            type: 'post',
            dataType: 'json',
            data: send_data,
            success: function(eata) {
                if (eata.flag == 1) {
                    Show('.show-y');
                    $('.show-y').html('预约成功');
                    orderid = eata.message.orderid;
                    ispay = eata.message.ispay;
                    fee = eata.message.fee;
                    msg = eata.message.msg;
                    status = eata.message.status;
                    subject = eata.message.subject;
                    show_url = eata.message.show_url;
                    if (ispay == 1) {
                        ShowF('.show-f');
                        $('.show-f').html('您需要关注微信悦健康关注公众号支付预约费用');
                    }
                }else {
                    Show('.show-y');
                    $('.show-y').html('预约失败');
                }
            }
        })     
    }else {
        Show('.show-y');
        $('.show-y').html('请选择预约人');
    }
})
var conindex;
$('.check-or').on('click', function() {
    window.location.href = 'city-hosp.html?username='+username+'&random='+random+'&deterLogin='+deterLogin+'&uid='+conindex+'&conindex=1'; 
})


function ShowF(className) {
    $(className).css({
        'display': 'block',
    })
    setTimeout(function() {
        $(className).css({
            'display': 'none',
        })    
    }, 5000)
}