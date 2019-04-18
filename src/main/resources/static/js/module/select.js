layui.define(['table','laytpl','tool'],function(exports){
    var tool = layui.tool,laytpl=layui.laytpl,
        defConfig = {

        },
        tpl = '<input type="checkbox" name="{{d.prop}}" value="{{d.code}}" lay-skin="primary" title="{{d.name}}">'
    var Select = function(){

    }
    Select.prototype.render = function(options){
        if(options.url && options.prop && options.code && options.name && options.elem){
            //初始化角色
            tool.http.ajax({
                url:options.url,
                method:'get',
                success:function (res) {
                    var opts = [];
                    for(i in res.result){
                        var data = {
                            prop:options.prop,
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