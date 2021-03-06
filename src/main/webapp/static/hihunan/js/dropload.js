(function($) {
  'use strict';
  var win = window;
  var doc = document;
  var $win = $(win);
  var $doc = $(doc);
  $.fn.dropload = function(options) {
    return new MyDropLoad(this, options);
  };
  var MyDropLoad = function(element, options) {
    var me = this;
    me.$element = $(element);
    me.upInsertDOM = false;
    me.loading = false;
    me.isLockUp = false;
    me.isLockDown = false;
    me.isData = true;
    me._scrollTop = 0;
    me.init(options);
  };
  MyDropLoad.prototype.init = function(options) {
    var me = this;
    me.opts = $.extend({}, {
      scrollArea: me.$element,
      domUp: {
        domClass: 'dropload-up',
        domRefresh: '<div class="dropload-refresh"><img class="drop-down-icon" src="../images/dropload_down.png"><span>下拉刷新</span></div>',
        domUpdate: '<div class="dropload-update"><img class="drop-up-icon" src="../images/dropload_up.png"><span>释放更新</span></div>',
        domLoad: '<div class="dropload-load"><img class="loading-icon" src="../images/droploading.gif"></div>'
      },
      domDown: {
        domClass: 'dropload-down',
        domRefresh: '<div class="dropload-refresh"><img class="drop-up-icon" src="../images/dropload_up.png"><span>上拉加载更多</span></div>',
        domLoad: '<div class="dropload-load"><img class="loading-icon" src="../images/droploading.gif"></div>',
        domNoData: ''
          //domNoData  : '<div class="dropload-noData"><span>暂无数据</span></div>'
      },
      distance: 50, // 拉动距离
      threshold: '', // 提前加载距离
      loadUpFn: '', // 上方function
      loadDownFn: '' // 下方function
    }, options);

    if (me.opts.loadDownFn != '') {
      me.$element.append('<div class="' + me.opts.domDown.domClass + '">' + me.opts.domDown.domRefresh + '</div>');
      me.$domDown = $('.' + me.opts.domDown.domClass);
    }

    if (me.opts.scrollArea == win) {
      me.$scrollArea = $win;
      me._scrollContentHeight = $doc.height();
      me._scrollWindowHeight = doc.documentElement.clientHeight;
    } else {
      me.$scrollArea = me.opts.scrollArea;
      me._scrollContentHeight = me.$element[0].scrollHeight;
      me._scrollWindowHeight = me.$element.height();
    }

    $win.on('resize', function() {
      if (me.opts.scrollArea == win) {
        me._scrollWindowHeight = win.innerHeight;
      } else {
        me._scrollWindowHeight = me.$element.height();
      }
    });

    me.$element.on('touchstart', function(e) {
      if (!me.loading) {
        fnTouches(e);
        fnTouchstart(e, me);
      }
    });
    me.$element.on('touchmove', function(e) {
      if (!me.loading) {
        fnTouches(e, me);
        fnTouchmove(e, me);
      }
    });
    me.$element.on('touchend', function() {
      if (!me.loading) {
        fnTouchend(me);
      }
    });

    me.$scrollArea.on('scroll', function() {
      me._scrollTop = me.$scrollArea.scrollTop();
      fnRecoverContentHeight(me)
      if (me.opts.threshold === '') {
        me._threshold = Math.floor(me.$domDown.height() * 1 / 3);
      } else {
        me._threshold = me.opts.threshold;
      }
      if (me.opts.loadDownFn != '' && !me.loading && !me.isLockDown && me._threshold != 0 && (me._scrollContentHeight - me._threshold) <= (me._scrollWindowHeight + me._scrollTop)) {
        fnLoadDown();
      }
    });

    function fnLoadDown() {
      me.direction = 'up';
      me.$domDown.html(me.opts.domDown.domLoad);
      me.loading = true;
      me.opts.loadDownFn(me);
    }
  };

  function fnTouches(e) {
    if (!e.touches) {
      e.touches = e.originalEvent.touches;
    }
  }

  function fnTouchstart(e, me) {
    me._startY = e.touches[0].pageY;
    me.touchScrollTop = me.$scrollArea.scrollTop();
  }

  function fnTouchmove(e, me) {
    me._curY = e.touches[0].pageY;
    me._moveY = me._curY - me._startY;

    if (me._moveY > 0) {
      me.direction = 'down';
    } else if (me._moveY < 0) {
      me.direction = 'up';
    }

    var _absMoveY = Math.abs(me._moveY);

    if (me.opts.loadUpFn != '' && me.touchScrollTop <= 0 && me.direction == 'down' && !me.isLockUp) {
      e.preventDefault();

      me.$domUp = $('.' + me.opts.domUp.domClass);
      if (!me.upInsertDOM) {
        me.$element.prepend('<div class="' + me.opts.domUp.domClass + '"></div>');
        me.upInsertDOM = true;
      }
      fnTransition(me.$domUp, 0);
      if (_absMoveY <= me.opts.distance) {
        me._offsetY = _absMoveY;
        me.$domUp.html(me.opts.domUp.domRefresh);
      } else if (_absMoveY > me.opts.distance && _absMoveY <= me.opts.distance * 2) {
        me._offsetY = me.opts.distance + (_absMoveY - me.opts.distance) * 0.5;
        me.$domUp.html(me.opts.domUp.domUpdate);
      } else {
        me._offsetY = me.opts.distance + me.opts.distance * 0.5 + (_absMoveY - me.opts.distance * 2) * 0.2;
      }
      me.$domUp.css({ 'height': me._offsetY });
    }
  }

  // touchend
  function fnTouchend(me) {
    var _absMoveY = Math.abs(me._moveY);
    if (me.opts.loadUpFn != '' && me.touchScrollTop <= 0 && me.direction == 'down' && !me.isLockUp) {
      fnTransition(me.$domUp, 300);

      if (_absMoveY > me.opts.distance) {
        me.$domUp.css({ 'height': me.$domUp.children().height() });
        me.$domUp.html(me.opts.domUp.domLoad);
        me.loading = true;
        me.opts.loadUpFn(me);
      } else {
        me.$domUp.css({ 'height': '0' }).on('webkitTransitionEnd transitionend', function() {
          me.upInsertDOM = false;
          $(this).remove();
        });
      }
      me._moveY = 0;
    }
  }

  // 重新获取文档高度
  function fnRecoverContentHeight(me) {
    if (me.opts.scrollArea == win) {
      me._scrollContentHeight = $doc.height();
    } else {
      me._scrollContentHeight = me.$element[0].scrollHeight;
    }
  }

  MyDropLoad.prototype.lock = function(direction) {
    var me = this;
    if (direction === undefined) {
      if (me.direction == 'up') {
        me.isLockDown = true;
      } else if (me.direction == 'down') {
        me.isLockUp = true;
      } else {
        me.isLockUp = true;
        me.isLockDown = true;
      }
    } else if (direction == 'up') {
      me.isLockUp = true;
    } else if (direction == 'down') {
      me.isLockDown = true;
    }
  };

  MyDropLoad.prototype.unlock = function() {
    var me = this;
    me.isLockUp = false;
    me.isLockDown = false;
  };

  MyDropLoad.prototype.setHasData = function(ishasData) {
    var me = this;
    if (ishasData) {
      me.isData = true;
      me.$domDown.html(me.opts.domDown.domRefresh);
      fnRecoverContentHeight(me);
      //
      me.opts.loadDownFn(me);
    } else {
      me.isData = false;
      me.$domDown.html(me.opts.domDown.domNoData);
      fnRecoverContentHeight(me);
    }
  };

  MyDropLoad.prototype.resetload = function() {
    var me = this;
    if (me.direction == 'down' && me.upInsertDOM) {
      me.$domUp.css({ 'height': '0' }).on('webkitTransitionEnd mozTransitionEnd transitionend', function() {
        me.loading = false;
        me.upInsertDOM = false;
        $(this).remove();
        fnRecoverContentHeight(me);
      });
    } else if (me.direction == 'up') {
      me.loading = false;
      if (me.isData) {
        me.$domDown.html(me.opts.domDown.domRefresh);
        fnRecoverContentHeight(me);
      } else {
        me.$domDown.html(me.opts.domDown.domNoData);
      }
    }
  };

  function fnTransition(dom, num) {
    dom.css({
      '-webkit-transition': 'all ' + num + 'ms',
      'transition': 'all ' + num + 'ms'
    });
  }
})(window.Zepto || window.jQuery);