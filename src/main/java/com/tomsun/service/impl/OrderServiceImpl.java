package com.tomsun.service.impl;

import com.tomsun.convert.OrderMaster2OrderDTOConvert;
import com.tomsun.dao.OrderDetailRepository;
import com.tomsun.dao.OrderMasterRepository;
import com.tomsun.dao.ProductInfoRepository;
import com.tomsun.dto.CartDTO;
import com.tomsun.dto.OrderDTO;
import com.tomsun.enums.OrderStatusEnum;
import com.tomsun.enums.PayStatusEnum;
import com.tomsun.enums.ResultEnum;
import com.tomsun.exception.SellException;
import com.tomsun.pojo.OrderDetail;
import com.tomsun.pojo.OrderMaster;
import com.tomsun.pojo.ProductInfo;
import com.tomsun.service.OrderService;
import com.tomsun.service.ProductInfoService;
import com.tomsun.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2018/7/10.
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMasterRepository repository;

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Transactional
    @Override
    public OrderDTO create(OrderDTO orderDTO) {

        //总价
        BigDecimal orderAmount = new BigDecimal(0);
        String orderId = KeyUtil.getUniqueKey();

        //1、查询商品（数量、单价）
        for (OrderDetail orderDetail:orderDTO.getOrderDetailList()){
            ProductInfo productInfo = productInfoService.findOne(orderDetail.getProductId());
            if(productInfo == null){
                new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //每一件商品的总价
            orderAmount = productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())).add(orderAmount);
            orderDetail.setOrderId(orderId);
            orderDetail.setDetailId(KeyUtil.getUniqueKey());
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetailRepository.save(orderDetail);

            //2、计算总价
        }

        //3、写入订单数据库（orderMaster和orderDetail）
        OrderMaster master = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO,master);
        master.setOrderAmount(orderAmount);
        master.setOrderStatus(OrderStatusEnum.NEW.getCode());
        master.setPayStatus(PayStatusEnum.WAIT.getCode());

        orderMasterRepository.save(master);

        //4、扣库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e ->
                new CartDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productInfoService.decreaseStock(cartDTOList);

        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        Optional<OrderMaster> byId = orderMasterRepository.findById(orderId);
        OrderMaster master = byId.get();
        if (master == null){
            new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(master.getOrderId());
        if (orderDetailList!= null && orderDetailList.size()>0){
            new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }

        OrderDTO dto = new OrderDTO();
        BeanUtils.copyProperties(master,dto);
        dto.setOrderDetailList(orderDetailList);

        return dto;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterpage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConvert.convert(orderMasterpage.getContent());
        return new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterpage.getTotalPages());
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster master = new OrderMaster();


        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("[取消订单]取消订单不正确, orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO,master);
        OrderMaster saveresult = orderMasterRepository.save(master);
        if(saveresult == null){
            log.error("[取消订单]取消订单更新失败, master={},",master);
            throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }
        //修改库存
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("[取消订单]订单中无商品详情, orderDTO={},",orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList=orderDTO.getOrderDetailList().stream().map(e->
                new CartDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productInfoService.increaseStock(cartDTOList);

        //如果已支付需要退款
        if(orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            //TODO 111
        }

        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {

        //判断订单状态
        if(!OrderStatusEnum.NEW.getCode().equals(orderDTO.getOrderStatus())){
            log.error("[完结订单]订单状态不正确, orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster master = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,master);
        OrderMaster saveresult = orderMasterRepository.save(master);
        if (saveresult == null){
            log.error("[完结订单]更新失败, master={},",master);
            throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }

        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        //判断订单状态
        if(!OrderStatusEnum.NEW.getCode().equals(orderDTO.getOrderStatus())){
            log.error("[订单支付完成]订单状态不正确, orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //判断支付状态
        if(!PayStatusEnum.WAIT.getCode().equals(orderDTO.getPayStatus())){
            log.error("[订单支付完成]支付状态不正确, orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }

        //修改支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster master = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,master);
        OrderMaster saveresult = orderMasterRepository.save(master);
        if (saveresult == null){
            log.error("[订单支付完成]更新失败, master={},",master);
            throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }
        return orderDTO;
    }
}
