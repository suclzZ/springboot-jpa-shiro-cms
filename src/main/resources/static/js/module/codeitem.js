layui.define(['table','laytpl','tool'],function(exports){
    var config = {
        URL:'/codeitem',
        METHOD_TYPE:'GET'
    }
    var tool = layui.tool,laytpl=layui.laytpl;
    var CodeItem = function(){
        layui._idata = layui._idata ||{};
        layui._icode = layui._icode ||{};
        tool.http.ajax({
            url:config.URL,
            method:config.METHOD_TYPE,
            success:function(res){
                var idata = {},icode={};
                if(res && res.result){
                    for(var i in res.result){
                        idata[res.result[i]['group']] = res.result[i]['data'];
                        var code = {};
                        res.result[i]['data'].forEach(function(e){
                            code[e.code] = e.caption;
                        })
                        icode[res.result[i]['group']] = code;
                    }
                }
                $.extend(layui._idata,idata);//构建下拉时用
                $.extend(layui._icode,icode);//填值用
            }
        })
    }
    CodeItem.prototype.render = function(options){

    }

    exports('codeitem',new CodeItem)
})