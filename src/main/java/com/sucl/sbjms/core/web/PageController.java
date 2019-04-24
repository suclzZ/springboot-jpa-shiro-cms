package com.sucl.sbjms.core.web;

import com.sucl.sbjms.core.service.CodeItemService;
import com.sucl.sbjms.core.service.DataDictionaryService;
import com.sucl.sbjms.core.util.ApplicationContextUtils;
import com.sucl.sbjms.core.view.Dictionary;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author sucl
 * @date 2019/4/3
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

    /**
     * 数据字典
     * @param group
     * @return
     */
	@GetMapping("/dictionary")
	@ResponseBody
	public List<Dictionary> getDictionaries(@RequestParam(name = "group[]",required = false) String[] group){
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
