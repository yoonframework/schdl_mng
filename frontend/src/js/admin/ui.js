import $ from "jquery";

$(function () {
    $("#fouc").css("opacity", "1");

    $(".control").on("click", function () {
        $(this).toggleClass("active");
        $(".content_wrap").toggleClass("wide");
    });

    $(".left_menu ul  p.main_menui ").on("click", function () {
        $(".sub_menu").slideUp();
        $(this).next("ul").stop().slideToggle();
    });

    $(".depth3").parent("li").addClass("have");

    //submenu toggle
    var acodian = {
        click: function (target) {
            var _self = this,
                $target = $(target);
            $target.on("click", function () {
                var $this = $(this);
                if ($this.children("ul.depth3").css("display") === "none") {
                    $(".sub_menu li .depth3").slideUp();
                    _self.onremove($target);

                    $this.addClass("on");
                    $this.children().slideDown();
                } else {
                    $(".sub_menu li .depth3").slideUp();
                    _self.onremove($target);
                }
            });
        },
        onremove: function ($target) {
            $target.removeClass("on");
        },
    };
    acodian.click(".sub_menu > li");

    //gnb 설정
    $(".sub_menu li .actived")
        .parent("li")
        .parent(".sub_menu")
        .parent("li")
        .addClass("active");
});
