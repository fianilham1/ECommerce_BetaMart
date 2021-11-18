package com.betamart.model;

import com.betamart.model.auditable.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "cart")
@Data
public class Cart extends BaseModel {

    //id -> cart Id

    @Column(name = "order_id", nullable = false)
    private long orderID;

    @Column(name = "total_price", nullable = false)
    private long totalPrice;

    @Column(name = "quantity_product", nullable = false)
    private int quantityProduct;

}
