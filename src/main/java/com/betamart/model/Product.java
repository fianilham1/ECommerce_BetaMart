package com.betamart.model;

import com.betamart.model.auditable.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "product_stock")
@Data
public class Product extends BaseModel {

    //id -> product Id

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "quantity_in_stock", nullable = false)
    private int quantityInStock;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "product_series")
    private String productSeries;

    @Column(name = "size")
    private String size;

    @Column(name = "net_weight")
    private int netWeight;

    @Column(name = "price", nullable = false)
    private long price;

    @Column(name = "image")
    private String image;

    @Column(name = "exp_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expDate;

    @Column(name = "is_ready", nullable = false)
    private boolean isReady;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "fk_product_category_id", nullable = false)
    private ProductCategory productCategory;

}
