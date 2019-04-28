/**
 * options: url 、code、 name、prop、elem
 * $elem: convert
 */
layui.define(['table','laytpl','tool'],function(exports){
    var config = {
        PROP:'prop',
        CODE:'code',
        NAME:'name'
    }
    var tool = layui.tool,laytpl=layui.laytpl,$ = layui.$,
        tpl = '<input type="checkbox" name="{{d.prop}}" value="{{d.code}}" lay-skin="primary" title="{{d.name}}">'
    var Checkbox = function(){

    }
    Checkbox.prototype.render = function(options){
        if(!options || !options.elem) return;
        var initOpt = tool.object.strToObject(options.elem);
        $.extend(options,initOpt);

        var opts = [],$elem = options.elem,convert = $elem.data('convert')||options.convert;
        if(convert && layui._idata){
            for(var prop in layui._idata[convert]){
                var data = {
                    prop:$elem.attr('name'),
                    code:layui._idata[convert][prop].code,
                    name:layui._idata[convert][prop].caption
                }
                laytpl(tpl).render(data, function(html){
                    opts.push(html)
                });
            }
            $elem.html(opts.join(''))
            return;
        }
        if((options.url && options.code && options.name)||(options.url && options.code=='#')){
            tool.http.ajax({
                url:options.url,
                method:'get',
                success:function (res) {
                    for(i in res.result){
                        var data = options.code=='#'?
                                    {
                                        prop:options.prop||options.code,
                                        code:res.result[i],
                                        name:res.result[i]
                                    }:{
                                        prop:options.prop || options.code,
                                        code:res.result[i][options.code],
                                        name:res.result[i][options.name]
                                    }
                        laytpl(tpl).render(data, function(html){
                            opts.push(html)
                        });
                    }
                    $elem.html(opts.join(''))
                }
            })
        }
    }
    exports('checkbox',new Checkbox)
})