/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.sucl.sbjms.core.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 系统页面视图
 *
 * @author Mark sunlightcs@gmail.com
 */
@Controller
public class PageController {
	
	@RequestMapping("views/{url}.html")
	public String view(@PathVariable("url") String url){
		return "views/" + url;
	}

	@RequestMapping("views/{module}/{url}.html")
	public String module(@PathVariable("module") String module, @PathVariable("url") String url){
		return "views/" + module + "/" + url;
	}

	@RequestMapping(value = {"/", "index.html"})
	public String index(){
		return "index";
	}

	@RequestMapping("login.html")
	public String login(){
		return "login";
	}

}
