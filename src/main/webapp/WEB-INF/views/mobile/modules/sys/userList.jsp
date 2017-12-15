<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<style type="text/css">

body > .toolbar_header{
	position: fixed;
	width: 100%;
	top: 0;
	left: 0;
	z-index: 1;
}
body > .toolbar_header .home{
	position: absolute;
	top: 5px;
	left: 10px;
	width: 60px;
	
}
body > .toolbar_header .home .btn_text{
	padding:3px 2px 3px 2px; 
	
}
body > .toolbar_header .next{
	position: absolute;
	top: 15px;
	right: 10px;
}
body > .toolbar_footer{
	position: fixed;
	width: 100%;
	bottom: 0;
	left: 0;
	z-index: 1;
}
#swipe > .wrap > div > div{
	height: 100%;
	line-height: 16px;
	text-align: left;
	color: black;
	font-size: 16px;
	font-weight: normal;
	background: none;
	padding: 10px;
	background: rgb(245,245,245);

}
#swipe > .wrap > div:first-child > div{
	padding: 0;
}
#swipe > .wrap > div:first-child .list>li{
	-webkit-border-radius: 0;
	-moz-border-radius: 0;
	-ms-border-radius: 0;
}
.btn{
	margin: 0 auto;
	width: 80%;
}
.btn_group{
	width: 80%;
	margin: 0 auto;
}
.toolbar_header ~ p{
	background: rgb(245,245,245);
	line-height: 28px;

}
</style>
 
<section id="user_section">
    <header>
        <nav class="left">
            <a href="#" data-icon="previous" data-target="back">返回</a>
        </nav>
        <h1 class="title">用户列表</h1>
    </header>
    <article class="active">
    	<div style="line-height:50px;padding:10px;">
    		手机端功能没有开发，请继续完善。<br/>
    		你如果有比较好的想法或扩展，也希望您共享出自己的一份代码。
    		请联系 thinkgem@163.com 谢谢！<br/>
    	
    	</div>
    	<div>
    	<button><a href="${ctx}/mobile/summary/fundingCost">资金成本报表</a></button>
    	<button><a href="${ctxStatic}/mui/examples/hello-mui/examples/numbox.html"> MUI </a> </button>
    	</div>
    </article>
    
    <script type="text/javascript">
	$('body').delegate('#user_section','pageinit',function(){
	});
	$('body').delegate('#user_section','pageshow',function(){
        var hash = J.Util.parseHash(location.hash);
        console.log(hash.param);
	});
	</script>
</section>