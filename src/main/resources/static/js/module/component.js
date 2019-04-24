layui.define(['laydate'],function(exports){
    var $ = layui.$, laydate=layui.laydate;

    var Component = function(){
        //初始化日期
        laydate.render({
            elem: '.layui-date', //指定元素
            trigger: 'click'//默认的会出问题，说是必选是focus，然而并不是
        });
    }

    Component.prototype.init = function(selector){
        var $elem = !selector?$(document):$(selector);
        $elem.find('[lay-comp]').each(function(comp,i){
            var _comp = $(this).attr('lay-comp');
            if(layui[_comp] && layui[_comp]['render']){
                layui[_comp]['render'].call($(this),{elem:$(this)});
            }
        })
    }

    exports('component',new Component)
})