

$('#content').css('minHeight',window.innerHeight);

window.publicInfo = {
    htmlFontSize : 0
};

    
//////////////////////////////////////////
(function () {
    

        (function(){
            var doc = document,
                win = window,
                docEl = doc.documentElement,
                resizeEvt = 'onorientationchange' in window ? 'orientationchange' : 'resize',
                bodyEle = document.getElementsByTagName('body')[0],
                recalc = function () {
                    var winH = window.innerHeight,
                        winW = window.innerWidth;
                    
                    var sizeV = 100 * (winW / 640);
                    
                    sizeV = sizeV>100?100:sizeV;
                    sizeV = Math.round(sizeV*10000)/10000;
                    
                    docEl.style.fontSize = sizeV + 'px';
                    bodyEle.style.fontSize = '24px';
                    window.publicInfo.htmlFontSize = sizeV;
                };
            recalc();
            
            if (!doc.addEventListener) return;
            win.addEventListener(resizeEvt, function(){
                if(resizeEvt==='orientationchange'){
                    setTimeout(recalc,300);
                }else{
                    recalc();
                }
            }, false);


        }());
})();

