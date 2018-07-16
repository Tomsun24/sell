package com.tomsun.exception;

import com.tomsun.enums.ResultEnum;

/**
 * Created by Administrator on 2018/7/10.
 */
public class SellException extends RuntimeException{

    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public SellException(Integer code,String message) {
        super(message);
        this.code=code;

    }
}
