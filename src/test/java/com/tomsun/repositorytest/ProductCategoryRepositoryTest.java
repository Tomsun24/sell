package com.tomsun.repositorytest;


import com.tomsun.dao.ProductCategoryRepository;
import com.tomsun.pojo.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by jd on 2018/4/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest{
    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public  void findOneTest(){
        Optional<ProductCategory> repositoryById = repository.findById(1);
        ProductCategory productCategory = repositoryById.get();
        repository.saveAndFlush(productCategory);
        System.out.println(productCategory.toString()+"-----------------------");
    }

    @Test
    public void saveTest(){
        ProductCategory productCategory = new ProductCategory("food7",5);
        productCategory.setCategoryId(8);
       // productCategory.setCategoryType(3);
        ProductCategory result = repository.save(productCategory);
        Assert.assertNotNull(result);
    }

    @Test
    @Transactional    //不论做什么操作数据库都会回滚
    public void findAll(){
        List<ProductCategory> list = repository.findAll();
        System.out.println(list.size());
        for (int i=0;i<list.size();i++){
            System.out.println(list.get(i).toString());
        }
    }

    @Test
    public void findByCategoryType(){
        List<Integer> list = Arrays.asList(1,2,5);
        List<ProductCategory> categoryList = repository.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0,categoryList.size());
        System.out.println(categoryList.size()+"------------");
    }

}
