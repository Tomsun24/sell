package com.tomsun.service.impl;

import com.tomsun.pojo.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created by jd on 2018/4/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest {

    @Autowired
    private ProductInfoServiceImpl productInfoService;


    @Test
    public void findOne() throws Exception {
        ProductInfo productInfo = productInfoService.findOne("31323423");
        Assert.assertNotNull(productInfo);
    }

    @Test
    public void findAll() throws Exception {
        List<ProductInfo> productInfoList = productInfoService.findAll();
        Assert.assertNotEquals(0,productInfoList);
    }

    @Test
    public void findAll1() throws Exception {
        PageRequest pageRequest = new PageRequest(0, 5);
        Page<ProductInfo> productInfoPage = productInfoService.findAll(pageRequest);
        System.out.println(productInfoPage.getTotalPages());
        productInfoPage.getTotalElements();
        Assert.assertNotEquals(0,productInfoPage);
    }

    @Test
    public void findUpAll() throws Exception {
        List<ProductInfo> productInfoList = productInfoService.findUpAll();
        Assert.assertNotEquals(0,productInfoList);
    }

    @Test
    public void save() throws Exception {
        ProductInfo info = new ProductInfo("123432435","火烧",new BigDecimal(54.5),34,"香甜可口","http://2314.jpg",0,1);
        ProductInfo productInfo = productInfoService.save(info);
        Assert.assertNotNull(productInfo);
    }

}