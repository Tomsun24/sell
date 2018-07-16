package com.tomsun.dao;

import com.tomsun.pojo.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2018/7/10.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Test
    public void  save(){
        OrderMaster master = new OrderMaster();
        master.setOrderId("1234567");
        master.setBuyerName("张三");
        master.setBuyerPhone("1234545435");
        master.setBuyerAddress("巢湖");
        master.setBuyerOpenid("001");
        master.setOrderAmount(new BigDecimal(1.7));
        OrderMaster order=orderMasterRepository.save(master);
        Assert.assertNotNull(order);
    }

    @Test
    public void findByBuyerOpenid(){
        PageRequest request = new PageRequest(0,3);
        Page<OrderMaster> orderMasterList = orderMasterRepository.findByBuyerOpenid("001", request);
        System.out.print(orderMasterList.getTotalElements());
    }
}