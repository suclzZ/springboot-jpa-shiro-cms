package com.sucl.sbjms.core.service.impl;

import com.sucl.sbjms.core.service.PasswordDegelate;
import com.sucl.sbjms.core.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author sucl
 * @date 2019/4/11
 */
@Service
public class GenericPasswordService implements PasswordService {
    @Autowired(required = false)
    private PasswordDegelate passwordDegelate;

    @Override
    public String encode(String password) {
        if(passwordDegelate!=null){
            return passwordDegelate.encode(password);
        }
        return password;
    }
}
