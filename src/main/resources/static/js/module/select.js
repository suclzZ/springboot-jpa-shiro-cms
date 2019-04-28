/**
 * options:code name url convert elem
 * $elem: data-convert
 * lay-data: options
 */
layui.define(['form','laytpl','tool'],function(exports){
    var config = {

    }
    var tool = layui.tool, laytpl = layui.laytpl, $ = layui.$, form = layui.form,
        tpl = '<option value="{{d.value}}">{{d.name}}</option>',
        emptyOpt = '<option value="" selected>请选择</option>';

    var Select = function(){

    }
    //convet > lay-data > options
    Select.prototype.render = function(options){
        if(!options || !options.elem) return;
        //通过lay-data 由构建构造
        var initOpt = tool.object.strToObject(options.elem);
        $.extend(options,initOpt);

        var opts = [],$elem = options.elem,convert = $elem.data('convert')||options.convert;
        if(!($elem.attr('required')!= void 0 || $elem.attr('lay-verify')=='required')){
            opts.push(emptyOpt);
        }
        if(convert && layui._idata){
            var codeItems = layui._idata[convert];
            for(var prop in codeItems){//数组
                var data = {code:codeItems[prop].code, name:codeItems[prop].caption}
                laytpl(tpl).render(data, function(html){
                    opts.push(html)
                });
            }
            $elem.html(opts.join(''))
            return;
        }
        var opt = {url:$elem.data('url'),code:$elem.data('code'),name:$elem.code};
        options = $.extend(opt ,options);
        if((options.url && options.code && options.name)||(options.url&& options.code=='#')){
            var data = {},$form = $elem.closest('form');
            if(options.parent){
                options.parentCode = options.parentCode|| options.parent;
                var pvalue = $form.find('[name="'+options.parent+'"]').val();
                data[options.parentCode] = pvalue;
            }
            tool.http.ajax({
                url:options.url,
                method:'get',
                async:false,
                data:data,
                success:function (res) {
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
                    $elem.html(opts.join(''));
                    if($elem.data('cacheValue')!= void 0){
                        // $elem.val($elem.data('cacheValue'));
                        var value = {};value[$elem.attr('name')] = $elem.data('cacheValue');
                        form.val($form.attr('lay-filter'),value);//有病的写法，但是框架只能这样赋值啊
                    }
                }
            })
        }
    }

    exports('select',new Select)
})