package com.tomsun.dao;

import com.tomsun.pojo.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by jd on 2018/4/18.
 */
public interface ProductInfoRepository extends JpaRepository <ProductInfo,String>{

    List<ProductInfo> findByProductStatus(Integer productStatus);

}
