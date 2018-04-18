package com.tomsun.controller;

import com.tomsun.vo.ProductInfoVO;
import com.tomsun.vo.ProductVO;
import com.tomsun.vo.ResultVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * Created by jd on 2018/4/18.
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {
    @GetMapping("/list")
    public ResultVO list(){
        ResultVO vo = new ResultVO<>();
        vo.setCode(0);
        vo.setMessage("成功");
        ProductVO productVO = new ProductVO();
        ProductInfoVO productInfoVO = new ProductInfoVO();
        productVO.setProductInfoVOList(Arrays.asList(productInfoVO));
        vo.setData(Arrays.asList(productVO));
        return vo;
    }
}
