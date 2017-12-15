<%@ page contentType="text/html;charset=UTF-8" %>
<!--(步骤1)引入插件的开始 --> 

<script src="${ctxStatic}/jquery/jquery-1.9.1.min.js" type="text/ecmascript" ></script>
<!-- <script src="${ctxStatic}/jqGrid5.0/js/jquery.min.js" type="text/ecmascript" ></script> --> 
<script src="${ctxStatic}/jqGrid5.0/js/bootstrap.min.js" type="text/ecmascript" ></script>
<!-- IE6 的bootstrap兼容性 -->
<!--[if lte IE 6]>
<script src="${ctxStatic}/bootstrap/bsie/js/bootstrap-ie.min.js" type="text/javascript"></script>
<![endif]-->
<script src="${ctxStatic}/jquery-select2/3.4/select2.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.js" type="text/javascript"></script>
<script type="text/javascript">
    // jbox2.3使用Jquery 1.9以下。修正代码适应Jquery 1.11.1。 
    $.browser=$.browser||{};
    $.browser.msie = /msie/.test(navigator.userAgent.toLowerCase());
</script>
<script src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="${ctxStatic}/common/mustache.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.min.js" type="text/javascript" ></script>
<script src="${ctxStatic}/jquery-validation/1.11.0/lib/jquery.form.js" type="text/javascript" ></script>
<!-- JQgrid的中文语言包 -->
<script src="${ctxStatic}/jqGrid5.0/js/grid.locale-cn.js" type="text/ecmascript" ></script>
<script src="${ctxStatic}/jqGrid5.0/js/jquery.jqGrid.min.js" type="text/ecmascript" ></script>
<script src="${ctxStatic}/common/jeesite.js" type="text/javascript"></script>
<script type="text/javascript">var ctx = '${ctx}', ctxStatic='${ctxStatic}';</script>
<script src="${ctxStatic}/common/jq_common.js?version=201601060940" type="text/javascript" ></script>