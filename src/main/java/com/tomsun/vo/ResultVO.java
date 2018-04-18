package com.tomsun.vo;

import lombok.Data;

/**
 * http请求返回的最外层对面
 * Created by jd on 2018/4/18.
 */
@Data
public class ResultVO<T>{

    private  Integer code;
    private  String message;
    private  T data;

}

