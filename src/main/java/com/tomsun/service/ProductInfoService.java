package com.tomsun.service;

import com.tomsun.pojo.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by jd on 2018/4/18.
 */
public interface ProductInfoService {

    ProductInfo findOne(String productId);

    List<ProductInfo> findAll();

    /*分页查询全部*/
    Page<ProductInfo> findAll(Pageable pageable);

    /*全部的上架商品*/
    List<ProductInfo> findUpAll();

    ProductInfo save(ProductInfo productInfo);


    /*加库存*/

    /*减库存*/
}
