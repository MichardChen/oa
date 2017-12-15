/*---------------------------
图库调用
----------------------------*/
(function ($) {
    $.fn.pictable = function (options) {
        var defaults = {
            image: '#pic_show', //绑定返回图片地址
            input: '#imageurl', //绑定返回表单控件
            reveal: '#myModal',	//要打开的窗口
            classurl: 'http://www.xmsme.gov.cn/interface/pic_class.asp', //图片分类jsonp url
            listurl: 'http://www.xmsme.gov.cn/interface/pic.asp' //图片jsonp url
        }

        var options = $.extend({}, defaults, options);

        load = function () {
            var id;

            $(options.reveal).reveal();

            $.ajax({
                url: options.classurl,
                type: 'post',
                dataType: 'jsonp',
                success: function (data) {
                    $(options.reveal + " .pic_class").find("ul").empty();

                    for (key in data.rows) {
                        if (key == 0) {
                            list(data.rows[key].id, 1);
                        }

                        $(options.reveal + " .pic_class").find("ul").append('<li' + (key == 0 ? " class='on'" : "") + ' data-id=' + data.rows[key].id + '>' + data.rows[key].classname + '</li>');
                    }
                }
            });
        }

        list = function (id, page) {
            $(".pic_list ul").empty();

            $.ajax({
                url: options.listurl,
                type: 'post',
                dataType: 'jsonp',
                data: { id: id, page: page, rnd: Math.random() },
                success: function (data) {
                    for (key in data.rows) {
                        $(options.reveal + " .pic_list").find("ul").append('<li><img src=' + data.rows[key].imageurl + '></li>');
                    }

                    $(options.reveal + " .pic_page").attr({
                        "data-page": page,
                        "data-pagecount": data.total
                    });
                }
            });
        }

        $(options.reveal).find(".pic_return").live("click", function () {
            if ($(options.reveal).find(".pic_list .on").length == 0) {
                alert("请选择图片！");
            }
            else {
                $(options.image).attr("src", $(options.reveal).find(".pic_list .on").attr("src"));
                $(options.input).val($(options.reveal).find(".pic_list .on").attr("src"));
                $(options.reveal).trigger('reveal:close');
            }
        });

        $(options.reveal + " .pic_class li").live("click", function () {
            $(options.reveal + " .pic_class li").attr("class", "");
            $(this).attr("class", "on");

            list($(this).attr("data-id"), 1);
        });

        $(options.reveal + " .pic_list img").live("click", function () {
            $(options.reveal + " .pic_list img").attr("class", "");
            $(this).attr("class", "on");
        });

        $(options.reveal + " .pic_page .prev").live("click", function () {
            if ($(this).parent("div").attr("data-page") <= 1) {
                alert("已经是第一页了！");
            }

            if ($(this).parent("div").attr("data-page") > 1) {
                list($(options.reveal + " .pic_class .on").attr("data-id"), parseInt($(this).parent("div").attr("data-page")) - 1)
            }
        });

        $(options.reveal + " .pic_page .next").live("click", function () {
            if ($(this).parent("div").attr("data-pagecount") <= $(this).parent("div").attr("data-page")) {
                alert("已经是最后一页了！");
            }

            if ($(this).parent("div").attr("data-page") < $(this).parent("div").attr("data-pagecount")) {
                list($(options.reveal + " .pic_class .on").attr("data-id"), parseInt($(this).parent("div").attr("data-page")) + 1)
            }
        });

        $(this).click(function () {
            load();
        });
    }
})(jQuery);