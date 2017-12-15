    window.onerror = function(err) {
        log('window.onerror: ' + err)
    }

    function setupWebViewJavascriptBridge(callback) {
        if (window.WebViewJavascriptBridge) { return callback(WebViewJavascriptBridge); }
        if (window.WVJBCallbacks) { return window.WVJBCallbacks.push(callback); }
        window.WVJBCallbacks = [callback];
        var WVJBIframe = document.createElement('iframe');
        WVJBIframe.style.display = 'none';
        WVJBIframe.src = 'wvjbscheme://__BRIDGE_LOADED__';
        document.documentElement.appendChild(WVJBIframe);
        setTimeout(function() { document.documentElement.removeChild(WVJBIframe) }, 0)
    }

    setupWebViewJavascriptBridge(function(bridge) {
       


        $(document).on('ajaxBeforeSend', function(e, xhr, options){
           
        
            
            bridge.callHandler('testObjcCallback', {'foo': 'bar'}, function(response) {
				$('.cho-tit>div').eq(0).html(response[0]); 
				$('.cho-tit>div').eq(1).html(response[1]); 
				$('.sea-city').css({
					background: 'blue'
				}) 
                
                // options.data = options.data+'&timestamp=1476771753124&sign=EACDEE957F1D9299D49725B015CAAF59';
                // console.log(options.data);
            }) 
                      
        })
    })