package com.sucl.sbjms.system.web;

import com.sucl.sbjms.core.web.BaseController;
import com.sucl.sbjms.system.entity.Rperm;
import com.sucl.sbjms.system.service.RpermService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sucl
 * @date 2019/4/3
 */
@RestController
@RequestMapping("/rperm")
public class RpermController extends BaseController<RpermService,Rperm> {

}
