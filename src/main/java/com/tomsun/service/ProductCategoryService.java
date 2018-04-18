package com.tomsun.service;

import com.tomsun.pojo.ProductCategory;

import java.util.List;

/**
 * Created by jd on 2018/4/17.
 */
public interface ProductCategoryService {

    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);

}
