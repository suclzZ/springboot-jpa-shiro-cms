layui.define(['table','laytpl','tool'],function(exports){
    var tool = layui.tool,laytpl=layui.laytpl,$=layui.$,
        defConfig = {

        },
        tpl = '<option value="{{d.value}}">{{d.name}}</option>',
        emptyOpt = '<option value="" selected>请选择</option>';
    var Select = function(){

    }
    Select.prototype.render = function(options){
        if(!options || !options.elem) return;
        if(options.convert && layui._idata){
            var opts = [];
            for(var prop in layui._idata[options.convert]){
                var data = {
                    prop:options.elem.attr('name'),
                    code:prop,
                    name:layui._idata[options.convert][prop]
                }
                laytpl(tpl).render(data, function(html){
                    opts.push(html)
                });
            }
            options.elem.html(opts.join(''))
            return;
        }
        var opt = {url:options.elem.data('url'),code:options.elem.data('code'),name:options.elem.code};
        options = $.extend(opt ,options);
        if((options.url&& options.code && options.name)||(options.url&& options.code=='#')){
            var data = {};
            if(options.parent){
                options.parentCode = options.parentCode|| options.parent;
                var pvalue = options.elem.closest('form').find('[name="'+options.parent+'"]').val();
                data[options.parentCode] = pvalue;
            }
            //初始化角色
            tool.http.ajax({
                url:options.url,
                method:'get',
                async:false,
                data:data,
                success:function (res) {
                    var opts = [];
                    if(!(options.elem.attr('required')!=void 0 || options.elem.attr('lay-verify')=='required')){
                        opts.push(emptyOpt);
                    }
                    for(i in res.result){
                        var data = options.code=='#'?
                                {
                                    value:res.result[i],
                                    name:res.result[i]
                                }:{
                                    value:res.result[i][options.code],
                                    name:res.result[i][options.name]
                                }
                        laytpl(tpl).render(data, function(html){
                            opts.push(html)
                        });
                    }
                    options.elem.html(opts.join(''));
                    if(options.elem.data('cacheValue')!= void 0){
                        options.elem.val(options.elem.data('cacheValue'));
                    }
                }
            })
        }
    }

    exports('select',new Select)
})