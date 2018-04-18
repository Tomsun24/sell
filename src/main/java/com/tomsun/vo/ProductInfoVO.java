package com.tomsun.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by jd on 2018/4/18.
 */
@Data
public class ProductInfoVO {

    @JsonProperty("id")
    private String  productId;

    @JsonProperty("name")
    private String productName;

    @JsonProperty("price")
    private BigDecimal productPrice;

    @JsonProperty("description")
    private String productDescription;

    @JsonProperty("icon")
    private String  productIcon;

}
