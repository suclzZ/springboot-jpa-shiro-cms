/**
 * 菜单、导航、tab切换
 * 没有form菜单出现问题
 * 如何通过hash保证刷新
 */
layui.define([ 'form', 'element','laydate'], function(exports) {
	var element = layui.element,
        $ = layui.jquery,
        laydate = layui.laydate,
		TAB_OFFSET = 1,//主页占用tab
		hideBtn = $('#hideBtn'),//隐藏菜单按钮
		mainLayout = $('#main-layout'),//主区域
        mainMask = $('.main-mask');//遮挡层
        
    (function(){
        laydate.render({
            elem: '.layui-date' //指定元素
            , trigger: 'click'//默认的会出问题，说是必选是focus，然而并不是
        });
    })();

	//菜单选中记录
    var Tab = function(el){
        this.el = el;//layui-tab  lay-filter="tab"
        this.urls = [];
    }

    Tab.prototype.content = function(src,id) {
        if(src && !(src.indexOf('http://')==0 || src.indexOf("/")==0)){
            src = 'views/'+src;
        }
        var iframe = document.createElement("iframe");
        iframe.setAttribute("frameborder", "0");
        iframe.setAttribute("src", src);
        iframe.setAttribute("width", '100%');
        iframe.setAttribute("height", '100%');
        iframe.setAttribute("scrolling", 'auto');
        iframe.setAttribute("name", id);
        iframe.setAttribute("data-id", id);
        return iframe.outerHTML;
    };

    Tab.prototype.is = function(url) {
        return (this.urls.indexOf(url) !== -1);
    };

    Tab.prototype.add = function(title, url, id) {//注意这里用id进行定位，其他所有地方都要一致
        if(this.is(url)) return false;
        this.urls.push(url);
        element.tabAdd(this.el, {
            title : title
            ,content : this.content(url,id)
            ,id : url//url.substring(0,url.indexOf('.html'))
        });
        this.change(url);//url和上面的要一致
    };

    Tab.prototype.change = function(url) {
        element.tabChange(this.el, url);
    };

    Tab.prototype.delete = function(url) {
        element.tabDelete(this.el, url);
    };

    Tab.prototype.onChange = function(callback){
        element.on('tab('+this.el+')', callback);
    };

    Tab.prototype.onDelete = function(callback) {
        var self = this;
        element.on('tabDelete('+this.el+')', function(data){
            var i = data.index;
            self.urls.splice(i-TAB_OFFSET,1);
            callback && callback(data);
        });
    };
    var tabs;
    var Main = function(){
        tabs = new Tab('tab'), navItems = [];
        tabs.onChange(function(data){
            var i = data.index-TAB_OFFSET, $this = navItems[i];
            if($this && typeof $this === 'object') {
                if($this.parent().is('dd')){//左侧菜单
                    $('#Nav dd').removeClass('layui-this');
                    $this.parent('dd').addClass('layui-this');
                    $this.closest('li.layui-nav-item')
                        .addClass('layui-nav-itemed')
                        .siblings().removeClass('layui-nav-itemed');
								}else if($this.parent().is('li')){//导航菜单
				             // $this.parent('li').removeClass('layui-this');
								}
            }
        });
        tabs.onDelete(function(data){
            var i = data.index-TAB_OFFSET;
            navItems[i].parent('li').removeClass('layui-this');
            navItems.splice(i,1);
        });

        //监听菜单点击
        element.on('nav(leftNav)', function(elem) {
            event.preventDefault();
            var navA = tabSelect(elem);
            // navA.closest('li.layui-nav-item').addClass('layui-nav-itemed').siblings().removeClass('layui-nav-itemed');
            // mainLayout.removeClass('hide-side');
        });
        // $('#Nav li.layui-nav-item:eq(0) > dl.layui-nav-child > dd > a:eq(0)').trigger('click');//默认第一条菜单

        //监听导航点击
        element.on('nav(rightNav)', function(elem) {
            var navA = tabSelect(elem);
        });
        //菜单隐藏显示
        hideBtn.on('click', function() {
            if(!mainLayout.hasClass('hide-side')) {
                mainLayout.addClass('hide-side');
            } else {
                mainLayout.removeClass('hide-side');
            }
        });
        //遮罩点击隐藏
        mainMask.on('click', function() {
            mainLayout.removeClass('hide-side');
        })
        function tabSelect(elem){
            var navA = $(elem).is('a')?$(elem): $(elem).find('a');
            var id = navA.attr('data-id'), url = navA.attr('data-url'), text = navA.attr('data-text');
            url && (window.location.hash = '#' + url);
            if( url && url!=='javascript:;' ){
                if(tabs.is(url)){//
                    tabs.change(url);
                } else {
                    navItems.push(navA);
                    tabs.add(text, url,id);
                }
            }
            return navA;
        }
        // element.render();
	}
	Main.prototype.addTab=function(url){
        tabs.change(url)
    }

    exports('router',new Main);
});