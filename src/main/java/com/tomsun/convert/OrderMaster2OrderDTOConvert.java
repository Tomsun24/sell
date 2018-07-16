package com.tomsun.convert;

import com.tomsun.dto.OrderDTO;
import com.tomsun.pojo.OrderMaster;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2018/7/10.
 */
public class OrderMaster2OrderDTOConvert {

    public static OrderDTO convert(OrderMaster orderMaster){
        OrderDTO dto = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,dto);
        return dto;
    }
    public static List<OrderDTO> convert(List<OrderMaster> orderMasterList){
        List<OrderDTO> orderDTOList = orderMasterList.stream().map(e ->convert(e)).collect(Collectors.toList());
        return orderDTOList;
    }

}
