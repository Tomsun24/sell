package com.tomsun.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * Created by jd on 2018/4/18.
 */
@Entity
@Data
public class ProductInfo {
        /*产品信息id*/
        @Id
        private String  productId;
        /*产品名称*/
        private String productName;
        /*产品单价*/
        private BigDecimal productPrice;
        /*产品库存*/
        private Integer productStock;
        /*产品描述*/
        private String productDescription;
        /*产品小图*/
        private String  productIcon;
        /*商品状态*/
        private Integer productStatus;
        /*类目编号*/
        private Integer categoryType;

        public ProductInfo() {
        }

        public ProductInfo(String productId,String productName, BigDecimal productPrice, Integer productStock, String productDescription, String productIcon, Integer productStatus, Integer categoryType) {
            this.productId = productId;
            this.productName = productName;
            this.productPrice = productPrice;
            this.productStock = productStock;
            this.productDescription = productDescription;
            this.productIcon = productIcon;
            this.productStatus = productStatus;
            this.categoryType = categoryType;
        }
}
