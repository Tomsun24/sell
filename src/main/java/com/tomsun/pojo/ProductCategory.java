package com.tomsun.pojo;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by jd on 2018/4/16.
 */
@Entity
@DynamicUpdate
@Data
public class ProductCategory {

    /*类目id*/
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)//由数据库而改变的主键
    private Integer categoryId;

    /*类目名字*/
    private String  categoryName;

    /*类目编号*/
    private Integer  categoryType;



    public ProductCategory() {
    }

    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }


}
