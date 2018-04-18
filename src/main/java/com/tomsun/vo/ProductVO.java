package com.tomsun.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**商品包含类目
 * Created by jd on 2018/4/18.
 */
@Data
public class ProductVO {

    @JsonProperty("name")//序列化字段的名字
    private  String categoryName;

    @JsonProperty("type")
    private  Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVO>  productInfoVOList;


}
