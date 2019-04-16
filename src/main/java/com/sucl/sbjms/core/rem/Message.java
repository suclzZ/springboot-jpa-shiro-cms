package com.sucl.sbjms.core.rem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author sucl
 * @date 2019/4/2
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    public static final String SUCCESS_CODE = "0000";
    public static final String FAILURE_CODE = "9999";

    private String code;
    private String info;
}
