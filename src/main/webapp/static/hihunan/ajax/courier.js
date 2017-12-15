/* 
* Created by hjk on 2016/10/11
*/
var Kreset = '<option></option>';
var resetCon = '<option value="" disabled selected>请选择快递</option> ';
var courId;
var courName;
$(document).ready(function(){
    $.ajax({
        url: Domain1+'andlife/base?action=expressCompany',
        type: 'post',
        dataType: 'json',
        data: {
            version : '2.0'
        },
        success : function(data) {
            if (data.flag == 1) {
                for (var i = 0; i < data.message.length; i++) {
                    resetCon += Kreset;
                    $('.cho-cour').html(resetCon);
                };
                for (var i = 0; i < data.message.length; i++) {              
                    $('.cho-cour option').eq(i+1).html(data.message[i].name);
                };  
                $('.cho-cour').on('change', function() {           
                    courId = $(".cho-cour").get(0).selectedIndex;  
                    if (courId != 0) {
                        courId = courId-1;
                        courName = data.message[courId].code;
                        $('select').css({
                            color: 'black'
                        })                      
                    }; 
                })
            };
        }
    })
});



$('.sear-line').on('click', function() {
    if ($('.cho-cour').val().length == 0 || $('.get-num input').val().length == 0) {
        if ($('.cho-cour').val().length == 0) {
            Show('.show-int');
            $('.show-int').html('请选择快递');
            return
        };
        if ($('.get-num input').val().length == 0) {
            Show('.show-int');
            $('.show-int').html('请输入快递单号');
            return
        };
    }else {
        $.ajax({
            url: Domain1+'andlife/base?action=getExpress',
            type: 'post',
            dataType: 'json',
            data: {
                version : '2.0',
                code: courName,
                postid: $('.get-num input').val()
            },
            success : function(data) {
                console.log(data.message)
                courNum = $('.get-num input').val();
                if (data.flag == 1) {
                    window.location.href = 'cour-con.html?courName='+courName+'&courNum='+courNum;
                }else {
                    Show('.show-int');
                    $('.show-int').html(data.message);
                }
            }
        })
    }
})
