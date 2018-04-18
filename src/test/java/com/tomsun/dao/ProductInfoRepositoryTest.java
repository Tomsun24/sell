package com.tomsun.dao;

import com.tomsun.pojo.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by jd on 2018/4/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {
    @Autowired
    private ProductInfoRepository repository;

    @Test
    public void save(){
        ProductInfo info = new ProductInfo();
        info.setProductId("31423443");
        info.setProductName("烤全羊");
        info.setProductPrice(new BigDecimal(134.4));
        info.setProductStock(103);
        info.setProductDescription("非常的好吃5");
        info.setProductIcon("http://xyy.jpg");
        info.setCategoryType(1);
        info.setProductStatus(0);
        ProductInfo productInfo = repository.save(info);
        Assert.assertNotNull(productInfo);

    }

    @Test
    public void findByProductStatus() throws Exception {
        List<ProductInfo> infoList = repository.findByProductStatus(0);
        Assert.assertNotEquals(0,infoList);
    }

}