layui.define(['layer'], function (exports) {
        var $ = layui.jquery;
        var layer = layui.layer;
        var dialog = {
            /*确认框*/
            confirm: function (jsonData) {
                layer.confirm(jsonData.message, {
                    btn: ['确定', '取消'],
                    shade: [0.1, '#fff']
                }, function () {
                    jsonData.success && jsonData.success();
                }, function () {
                    jsonData.cancel && jsonData.cancel();
                });
            },
            page: function (title, url, w, h) {
                if (title == null || title == '') {
                    title = false;
                };
                if (url == null || url == '') {
                    url = "404.html";
                };
                if (w == null || w == '') {
                    w = '600px';
                };
                if (h == null || h == '') {
                    h = '500px';
                };
                var index = layer.open({
                    type: 2,
                    title: title,
                    area: [w, h],
                    fixed: false, //不固定
                    skin: 'custom-layer-class',
                    maxmin: true,
                    content: url,
                    btn: ['提交', '重置', '取消'],
                    yes: function (index, layero) {
                        layero.find('form').find('button').trigger('click');
                        layero.find('form').data('layer-index', index);
                    },
                    btn2: function (index, layero) {
                        layero.find('form')[0].reset()
                        return false;
                    },
                    btn3: function (index, layero) {
                        layer.close(index);
                    },
                    success: function (layero, index) {
                        layero.find('form')[0].reset();
                    }
                });
            },
            /**
			 * 提示
			 * @param title
			 * @param obj
			 */
			tips: function(title, obj) {
				layer.tips(title, obj, {
					tips: [1, '#444c63'], //还可配置颜色
					time: 1000
				});
			}
        };
        //输出test接口
        exports('dialog', dialog);
    }
);
