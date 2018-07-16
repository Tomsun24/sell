package com.tomsun.service.impl;

import com.tomsun.dao.ProductInfoRepository;
import com.tomsun.dto.CartDTO;
import com.tomsun.enums.ProductStatus;
import com.tomsun.enums.ResultEnum;
import com.tomsun.exception.SellException;
import com.tomsun.pojo.ProductInfo;
import com.tomsun.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Created by jd on 2018/4/18.
 */
@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoRepository repository;

    @Override
    public ProductInfo findOne(String productId) {
        Optional<ProductInfo> productInfo = repository.findById(productId);
        return productInfo.get();
    }

    @Override
    public List<ProductInfo> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatus.UP.getCode());
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO:cartDTOList){
            ProductInfo info = findOne(cartDTO.getProductId());
            if(info == null){
                new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = info.getProductStock() + cartDTO.getProductQuantity();
            info.setProductStock(result);
            save(info);
        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList){
            ProductInfo info = findOne(cartDTO.getProductId());
            if(info == null){
                new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = info.getProductStock() - cartDTO.getProductQuantity();
            if(result<0){
                new SellException(ResultEnum.PRODUCT_STOCK_NOTENOUGH);
            }
            info.setProductStock(result);
            save(info);
        }
    }
}
