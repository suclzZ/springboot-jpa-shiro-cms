layui.define(['table','laytpl','tool'],function(exports){
    var config = {
        URL:'/codeitem',
        METHOD_TYPE:'GET'
    }
    var tool = layui.tool,laytpl=layui.laytpl;
    var CodeItem = function(){
        tool.http.ajax({
            url:config.URL,
            method:config.METHOD_TYPE,
            success:function (res) {
                if(res && res.result){//[{group :'',data: {code: caption}}]
                    var _idata = {};
                    for(var i in res.result){
                        _idata[res.result[i]['group']] = res.result[i]['data'];
                    }
                    layui._idata = _idata;
                }
            }
        });
    }
    CodeItem.prototype.render = function(options){

    }

    exports('codeitem',new CodeItem)
})