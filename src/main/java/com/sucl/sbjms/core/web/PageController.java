/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.sucl.sbjms.core.web;

import com.sucl.sbjms.core.service.CodeItemService;
import com.sucl.sbjms.core.service.DataDictionaryService;
import com.sucl.sbjms.core.util.ApplicationContextUtils;
import com.sucl.sbjms.core.view.Dictionary;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 系统页面视图
 *
 * @author Mark sunlightcs@gmail.com
 */
@Controller
public class PageController {
	@Autowired(required = false)
	private DataDictionaryService dataDictionaryService;
	
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

	@GetMapping("/codeitem")
	public List<Dictionary> getDictionary(@RequestParam(value = "group[]",required = false) String[] group){
		List<Dictionary> dictionaries = new ArrayList<>();
		if(dataDictionaryService!=null){
			if(group==null)
				dictionaries.addAll( dataDictionaryService.getDictionaries() );
			else
				dictionaries.addAll( dataDictionaryService.getDictionaries(group) );
		}
		dictionaries.addAll(initDictionary());
		return dictionaries;
	}

	private List<Dictionary> initDictionary() {
		Map<String, CodeItemService> codeitem = BeanFactoryUtils.beansOfTypeIncludingAncestors(ApplicationContextUtils.getApplicationContext(), CodeItemService.class, true, false);
		List<Dictionary> dictionaries = new ArrayList<>();
		if(codeitem!=null){
			codeitem.values().stream().forEach(e->{
				dictionaries.add(e.getDictionary());
			});
		}
		return dictionaries;
	}

}
