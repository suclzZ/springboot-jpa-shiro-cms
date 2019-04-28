package com.sucl.sbjms.content.web;

import com.sucl.sbjms.content.entity.ContentType;
import com.sucl.sbjms.content.service.ContentTypeService;
import com.sucl.sbjms.core.web.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sucl
 * @date 2019/4/3
 */
@RestController
@RequestMapping("/contenttype")
public class ContentTypeController extends BaseController<ContentTypeService,ContentType> {

}
