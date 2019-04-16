package com.sucl.sbjms.system.web;

import com.sucl.sbjms.core.web.BaseController;
import com.sucl.sbjms.system.entity.Agency;
import com.sucl.sbjms.system.service.AgencyService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sucl
 * @date 2019/4/3
 */
@RestController
@RequestMapping("/agency")
public class AgencyController extends BaseController<AgencyService,Agency> {

}
