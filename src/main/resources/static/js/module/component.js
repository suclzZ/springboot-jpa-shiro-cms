/**
 * 对标记lay-comp的组件进行自动构建
 * 默认从dialog.open时执行
 */
var  comp = ['select','checkbox'];//注册组件
layui.define(['laydate'].concat(comp),function(exports){
    var $ = layui.$, laydate=layui.laydate,hint = layui.hint();

    var Component = function(){
        //初始化日期
        laydate.render({
            elem: '.layui-date', //指定元素
            trigger: 'click'//默认的会出问题，说是必选是focus，然而并不是
        });
    }

    //组件初始化
    Component.prototype.init = function(selector){
        var $elem = !selector?$(document):$(selector);
        $elem.find('[lay-comp]').each(function(comp,i){
            var _comp = $(this).attr('lay-comp');
            if(layui[_comp] && layui[_comp]['render']){
                layui[_comp]['render'].call($(this),{elem:$(this)});
            }else{
                hint.warn('layui no '+_comp+'  or no method render!');
            }
        })
    }

    exports('component',new Component)
})