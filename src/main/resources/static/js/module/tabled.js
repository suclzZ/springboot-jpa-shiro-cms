layui.define(['table'],function(exports){
    var table = layui.table,$ = layui.$;

    var defConfig = {
        page:true,
        limit:10,
        request: {
            pageName: 'pager:pageIndex', //页码的参数名称，默认：page
            limitName: 'pager:pageSize' //每页数据量的参数名，默认：limit
        },
        response: {
            // statusName: 'status', //规定数据状态的字段名称，默认：code
            statusCode: 'OK', //规定成功的状态码，默认：0
            // msgName: 'hint', //规定状态信息的字段名称，默认：msg
            // countName: 'total', //规定数据总数的字段名称，默认：count
            // dataName: 'rows', //规定数据列表的字段名称，默认：data
        },
        parseData: function(res){ //res 即为原始返回的数据
            return {
                "code": res.status, //解析接口状态
                "msg": res.info, //解析提示文本
                "count": res.result.total, //解析数据长度
                "data": res.result.result //解析数据列表
            };
        }
    }

    var Tabled = function(){

    }
    $.extend(Tabled.prototype,table);

    Tabled.prototype.render = function(options){
        var options = options ||  {};
        options = $.extend({},defConfig,options);
        return table.render(options);
    }

    exports('tabled',new Tabled)
});