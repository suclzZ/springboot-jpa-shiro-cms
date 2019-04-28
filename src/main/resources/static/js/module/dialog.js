layui.define(['element','layer','component'], function(exports) {
    var layer = layui.layer,$ = layui.$, component = layui.component,
    defConfig = {
        type:1,
        // area: ['600px', '500px'],
        shade: 0.5,
        maxmin: true,
        fixed: true,
        skin: 'custom-layer-class',
        offset: 'auto'
    },DEF_WIDTH=600,DEF_HEIGHT=500;

    var Dialog = function () {
        var area = [];
        var width = window.innerWidth,height = window.innerHeight;
        width<DEF_WIDTH?area.push(width+'px'):area.push(DEF_WIDTH+'px');
        height<DEF_HEIGHT?area.push(height+'px'):area.push(DEF_HEIGHT+'px');
        this.area = area;
    }
    Dialog.prototype.open = function (title,elem,options) {
        component.init();//
        var opt = $.extend({},defConfig,{
            title: title,
            content: elem,
            area:this.area,
            btn: ['提交', '取消'],
            yes: function (index, layero) {
                layero.find('form').find('button[lay-submit]').trigger('click');//从弹窗获取submit按钮并点击
                layero.find('form').data('layer-index', index);
            },
            btn2: function (index, layero) {
                layer.close(index);
            },
            success: function (layero, index) {
                layero.find('form')[0].reset();
                options && options.afterOpen && options.afterOpen.call(layero);
            },
            end:function () {

            }
        },options)
        layer.full(layer.open(opt));
    }

    exports('dialog',new Dialog);
});