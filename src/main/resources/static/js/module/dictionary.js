/**
 * layui._idata:{'group':[{code:'',caption:''}...]}
 * layui._icode:{'group':{k1:v1,k2:v2...}}
 */
layui.define(['tool'],function(exports){
    layui._idata = layui._idata ||{};
    layui._icode = layui._icode ||{};
    var config = {
        URL:'/dictionary',
        METHOD_TYPE:'GET',
        GROUP:'group',
        DATA:'data'
    }
    var $ = layui.$, tool = layui.tool;
    var Dictionary = function(){
        tool.http.ajax({
            url:config.URL,
            method:config.METHOD_TYPE,
            success:function(res){//res:[{'group':[{code:'',caption:''}...]}]
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
    Dictionary.prototype.render = function(options){

    }

    exports('dictionary',new Dictionary)
})