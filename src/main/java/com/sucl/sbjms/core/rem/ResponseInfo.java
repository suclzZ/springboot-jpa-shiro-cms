package com.sucl.sbjms.core.rem;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author sucl
 * @date 2019/4/2
 */
@Getter
@Setter
@NoArgsConstructor
public class ResponseInfo extends Message{
    private HttpStatus status;
    private Object result;

    public ResponseInfo(HttpStatus status,Object result,String msg){
        super(status == HttpStatus.OK? SUCCESS_CODE:FAILURE_CODE,msg);
        this.result = result;
        this.status = status;
    }

    public ResponseInfo(HttpStatus status,Object result){
        this(status,result,"");
    }

    public ResponseInfo(Object result){
        this(HttpStatus.OK,result);
    }
}
