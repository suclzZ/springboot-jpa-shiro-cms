package com.sucl.sbjms.core.config;

import com.sucl.sbjms.core.service.FreemarkerVariable;
import lombok.Data;

import java.util.List;

/**
 * @author sucl
 * @date 2019/4/16
 */
@Data
public class FreemarkerVariablesHelper {
    private List<FreemarkerVariable> variables;

    public FreemarkerVariablesHelper(List<FreemarkerVariable> variables) {
        this.variables = variables;
    }
}
