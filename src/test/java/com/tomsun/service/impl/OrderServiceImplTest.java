package com.tomsun.service.impl;

import com.tomsun.dto.OrderDTO;
import com.tomsun.enums.OrderStatusEnum;
import com.tomsun.enums.PayStatusEnum;
import com.tomsun.pojo.OrderDetail;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2018/7/10.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    private static  final String  BUYER_OPENID = "2132142314";

    @Test
    public void create() throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("斗地主2");
        orderDTO.setBuyerAddress("南京2");
        orderDTO.setBuyerPhone("143244231");
        orderDTO.setBuyerOpenid(BUYER_OPENID);

        List<OrderDetail> orderDetailList = new ArrayList<>();

        OrderDetail detail = new OrderDetail();
        detail.setProductId("31423443");
        detail.setProductQuantity(1);

        OrderDetail detail2 = new OrderDetail();
        detail2.setProductId("31423423");
        detail2.setProductQuantity(3);
        orderDetailList.add(detail);
        orderDetailList.add(detail2);

        orderDTO.setOrderDetailList(orderDetailList);
        OrderDTO dto = orderService.create(orderDTO);
        log.info("[创建订单] result ={}",dto);
        Assert.assertNotNull(dto);
    }

    @Test
    public void findOne() throws Exception {
        OrderDTO dto = orderService.findOne("1531213677858372809");
        log.info("[查询单个订单] result = {}",dto);
        Assert.assertNotNull(dto);

    }

    @Test
    public void findList() throws Exception {
        Page<OrderDTO> list = orderService.findList("21321423141", new PageRequest(0, 2));
        log.info("[查询一批订单] result = {}",list.getTotalPages());
        Assert.assertNotEquals(0,list.getTotalElements());
    }

    @Test
    public void cancel() throws Exception {
        OrderDTO dto = orderService.findOne("1531214700157942924");
        OrderDTO orderDTO = orderService.cancel(dto);
        Assert.assertEquals(orderDTO.getOrderStatus(), OrderStatusEnum.CANCEL.getCode());

    }

    @Test
    public void finish() throws Exception {
        OrderDTO dto = orderService.findOne("1531214700157942924");
        OrderDTO orderDTO = orderService.finish(dto);
        Assert.assertEquals(orderDTO.getOrderStatus(), OrderStatusEnum.FINISHED.getCode());
    }

    @Test
    public void paid() throws Exception {
        OrderDTO dto = orderService.findOne("1531214700157942924");
        OrderDTO orderDTO = orderService.paid(dto);
        Assert.assertEquals(orderDTO.getPayStatus(), PayStatusEnum.SUCCESS.getCode());


    }

}