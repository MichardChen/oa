
$(window).resize(initFont);
function initFont(){	
	var Wind =  $(window).width();
	if (Wind >= 640) {
		$('html').css({
			'font-size' : '100px'
		})	
	}else{
		$('html').css({
			'font-size' :100/ 640 * Wind + 'px'
		})				
	}	
	
}initFont();		

//作为全站都需要使用的JS，需要提取到JS文件当中，不要书写在HTML当中
//为了防止全局作用域被污染，会使用$(function(){}或匿名函数进行封装)