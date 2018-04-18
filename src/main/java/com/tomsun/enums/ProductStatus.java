package com.tomsun.enums;

import lombok.Getter;

/**
 * Created by jd on 2018/4/18.
 */
@Getter
public enum ProductStatus {
    UP(0,"上架"),
    DOWN(1,"下架");

    private Integer code;
    private String message;

    ProductStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
