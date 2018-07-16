package com.tomsun.dto;

import lombok.Data;

/**
 * Created by Administrator on 2018/7/10.
 */
@Data
public class CartDTO {

    /*商品id*/
    private String productId;

    /*商品数量*/
    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }

    public CartDTO() {
    }
}


