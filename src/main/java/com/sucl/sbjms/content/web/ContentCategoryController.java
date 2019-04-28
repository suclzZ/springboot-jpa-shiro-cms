package com.sucl.sbjms.content.web;

import com.sucl.sbjms.content.entity.ContentCategory;
import com.sucl.sbjms.content.service.ContentCategoryService;
import com.sucl.sbjms.core.web.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sucl
 * @date 2019/4/3
 */
@RestController
@RequestMapping("/contentcategory")
public class ContentCategoryController extends BaseController<ContentCategoryService,ContentCategory> {

}
