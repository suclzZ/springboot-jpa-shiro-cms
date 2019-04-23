layui.define(['table','laytpl','tool'],function(exports){
    var config = {
        PROP:'prop',
        CODE:'code',
        NAME:'name'
    }
    var tool = layui.tool,laytpl=layui.laytpl,
        tpl = '<input type="checkbox" name="{{d.prop}}" value="{{d.code}}" lay-skin="primary" title="{{d.name}}">'
    var Select = function(){

    }
    Select.prototype.render = function(options){
        if(!options.elem) return;
        if(options.convert && layui._idata){
            var opts = [],item = layui._idata[options.convert];
            for(var prop in item){
                var code,name;
                if($.type(item)=='array'){
                    code = item[prop]['code'],name = item[prop]['caption'];
                }else{
                    code = prop,item = item[prop];
                }
                var data = {
                    prop:options.elem.attr('name'),
                    code:code,
                    name:name
                }
                laytpl(tpl).render(data, function(html){
                    opts.push(html)
                });
            }
            options.elem.html(opts.join(''))
            return;
        }
        if((options.url && options.code && options.name)||(options.url && options.code=='#')){
            tool.http.ajax({
                url:options.url,
                method:'get',
                success:function (res) {
                    var opts = [];
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
                    options.elem.html(opts.join(''))
                }
            })
        }
    }

    exports('select',new Select)
})