/**
 * Created by Abel on 2016/9/12.
 */
window.onload=function(){
    if (location.href.indexOf("?xyz=")<0)
    {
        location.href=location.href+"?xyz="+Math.random();
    }
}