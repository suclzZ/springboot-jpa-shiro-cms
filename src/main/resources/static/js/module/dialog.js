layui.define([ 'element','layer'], function(exports) {
    var layer = layui.layer,$ = layui.$,
    defConfig = {
        type:1,
        area: ['600px', '500px'],
        shade: 0.5,
        maxmin: true,
        fixed: true,
        skin: 'custom-layer-class',
        offset: 'auto',
        success: function (layero, index) {
            //将 form重置，form如果有赋值一定要在打开后
            layero.find('form')[0].reset();
        },
        end:function () {
            
        }
    }

    var Dialog = function () {
        
    }
    Dialog.prototype.open = function (title,elem,options) {
        var opt = $.extend({},defConfig,{
            title: title,
            content: elem,
            btn: ['提交', '取消'],
            yes: function (index, layero) {
                layero.find('form').find('button[lay-submit]').trigger('click');
                layero.find('form').data('layer-index', index);
            },
            btn2: function (index, layero) {
                layer.close(index);
            }
        },options)
        layer.open(opt);
    }

    exports('dialog',new Dialog);
});