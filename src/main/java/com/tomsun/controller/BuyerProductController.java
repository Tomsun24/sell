package com.tomsun.controller;

import com.tomsun.pojo.ProductCategory;
import com.tomsun.pojo.ProductInfo;
import com.tomsun.service.ProductCategoryService;
import com.tomsun.service.ProductInfoService;
import com.tomsun.utils.ResultVOUtil;
import com.tomsun.vo.ProductInfoVO;
import com.tomsun.vo.ProductVO;
import com.tomsun.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by jd on 2018/4/18.
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/list")
    public ResultVO list(){
        //1、查询所有的上架的商品
        List<ProductInfo> productInfoList = productInfoService.findUpAll();
        /*java8特有lambda表达式*/
        List<Integer> categoryTypeList = productInfoList.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());

        //2、查询类目（一次性查询）
        List<ProductCategory> categoryList = productCategoryService.findByCategoryTypeIn(categoryTypeList);


        ProductVO productVO = new ProductVO();
        ProductInfoVO productInfoVO = new ProductInfoVO();
        List<ProductVO> productVOS = new ArrayList<>();
        List<ProductInfoVO> productInfoVOS = new ArrayList<>();
        for (ProductCategory productCategory:categoryList) {
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());
            for (ProductInfo productInfo:productInfoList) {
                    if(productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                        BeanUtils.copyProperties(productInfo,productInfoVO);
                        productInfoVOS.add(productInfoVO);

                }
            }
            productVO.setProductInfoVOList(productInfoVOS);
            productVOS.add(productVO);

        }
        return ResultVOUtil.success(productVOS);
    }
}
