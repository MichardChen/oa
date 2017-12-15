/* 
* Created by hjk on 2016/9/10
*/

$(document).ready(function(){
    
});
//获取订单信息
$.ajax({
    url: Domain1+'andlife/hospital',
    type: 'post',
    dataType: 'json',
    data: {
        action: 'get_order',
        version: '2.0',
        orderid: '必填，订单号码'
    },
    success : function(data) {
        console.log(data)
    }
})