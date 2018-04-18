package com.tomsun.service.impl;

import com.tomsun.pojo.ProductCategory;
import com.tomsun.service.ProductCategoryService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by jd on 2018/4/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryServiceImplTest {
    @Autowired
    private ProductCategoryServiceImpl productCategoryService;

    @Test
    public void findOne() throws Exception {
        ProductCategory productCategory = productCategoryService.findOne(1);
        System.out.println(productCategory.toString());
    }

    @Test
    public void findAll() throws Exception {
        List<ProductCategory> list = productCategoryService.findAll();
        System.out.println(list.size());
        for (int i=0;i<list.size();i++){
            System.out.println(list.get(i).toString());
        }
    }

    @Test
    public void findByCategoryTypeIn() throws Exception {
        List<Integer> list = Arrays.asList(1,2,5);
        List<ProductCategory> categoryList = productCategoryService.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0,categoryList.size());
        System.out.println(categoryList.size()+"------------");
    }

    @Test
    public void save() throws Exception {
        ProductCategory productCategory = new ProductCategory("food10",5);
        productCategory.setCategoryId(8);
        // productCategory.setCategoryType(3);
        ProductCategory result = productCategoryService.save(productCategory);
        Assert.assertNotNull(result);
    }

}