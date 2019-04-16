package com.sucl.sbjms.system.service;

import com.sucl.sbjms.core.service.BaseService;
import com.sucl.sbjms.system.dao.CodeitemDao;
import com.sucl.sbjms.system.entity.Codeitem;
import org.springframework.data.rest.webmvc.RepositoryRestController;

/**
 * @author sucl
 * @date 2019/4/2
 */
@RepositoryRestController
public interface CodeitemService extends BaseService<CodeitemDao,Codeitem>{

}
