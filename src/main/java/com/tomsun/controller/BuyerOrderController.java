package com.tomsun.controller;

import com.tomsun.convert.OrderForm2OrderDTOConverter;
import com.tomsun.dto.OrderDTO;
import com.tomsun.enums.ResultEnum;
import com.tomsun.exception.SellException;
import com.tomsun.form.OrderForm;
import com.tomsun.pojo.OrderDetail;
import com.tomsun.service.OrderService;
import com.tomsun.utils.ResultVOUtil;
import com.tomsun.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/7/11.
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {
        //创建订单
        @Autowired
        private OrderService orderService;

        @PostMapping("/create")
        public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm, BindingResult bindingResult){

                if(bindingResult.hasErrors()){
                        log.error("[创建订单]参数不正确, orderForm = {}",orderForm);
                        throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                                bindingResult.getFieldError().getDefaultMessage());

                }
            OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
            if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
                log.error("[创建订单]购物车不能为空");
                throw new SellException(ResultEnum.CART_EMPTY);
            }
            OrderDTO createdto = orderService.create(orderDTO);

            Map<String, String> resultMap = new HashMap<>();

            resultMap.put("orderId",createdto.getOrderId());
            return ResultVOUtil.success(resultMap);

        }
        //订单列表
        @GetMapping("/list")
        public ResultVO<List<OrderDetail>> list(@RequestParam("openid")String openid,
                                                @RequestParam(value = "value = page",defaultValue = "0")Integer page,
                                                @RequestParam(value = "value = size",defaultValue = "10")Integer size){
            if(StringUtils.isEmpty(openid)){
                log.error("[查询订单列表]openid为空");
                throw new SellException(ResultEnum.PARAM_ERROR);
            }
            PageRequest request = new PageRequest(page, size);
            Page<OrderDTO> orderDTOList = orderService.findList(openid, request);


            return ResultVOUtil.success(orderDTOList.getContent());
        }


        //订单详情
         @GetMapping("/detail")
        public ResultVO<OrderDTO> detail(@RequestParam("openid")String Openid,
                                         @RequestParam("orderId")String orderId){

            //TODO 不安全的做法
            OrderDTO dto = orderService.findOne(orderId);
            return ResultVOUtil.success(dto);


         }


        //取消订单
        @PostMapping("/cancel")
        public ResultVO cancel(@RequestParam("openid")String openid,
                               @RequestParam("orderId")String orderId){
            OrderDTO dto = orderService.findOne(orderId);
            orderService.cancel(dto);
            return ResultVOUtil.success();
        }

}
