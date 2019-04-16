package com.sucl.sbjms.system.web;

import com.sucl.sbjms.core.web.BaseController;
import com.sucl.sbjms.system.entity.Codegroup;
import com.sucl.sbjms.system.service.CodegroupService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sucl
 * @date 2019/4/3
 */
@RestController
@RequestMapping("/codegroup")
public class CodegroupController extends BaseController<CodegroupService,Codegroup> {

}
