package com.tomsun.dao;

import com.tomsun.pojo.OrderDetail;
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
 * Created by Administrator on 2018/7/10.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {
    @Autowired
    private OrderDetailRepository repository;

    @Test
    public void save(){
        OrderDetail detail = new OrderDetail();
        detail.setDetailId("1234324342");
        detail.setOrderId("1324341324");
        detail.setProductIcon("http://xxjj.jpg");
        detail.setProductId("32514342");
        detail.setProductName("烤鱼");
        detail.setProductPrice(new BigDecimal(15.4));
        detail.setProductQuantity(4);
        OrderDetail save = repository.save(detail);
        Assert.assertNotNull(save);
    }

    @Test
    public void findByOrderId() throws Exception {
        List<OrderDetail> result = repository.findByOrderId("1324341324");
        Assert.assertNotEquals(0,result);
    }

}