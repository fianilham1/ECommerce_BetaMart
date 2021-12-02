package com.betamart.model;

import com.betamart.model.auditable.BaseModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "order_product_details")
@Data
public class OrderProductDetails extends BaseModel {

    //id -> order product Id

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "fk_order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "fk_product_id", nullable = false)
    private Product product;

    @Column(name = "product_quantity", nullable = false)
    private int productQuantity;

    @Column(name = "product_price", nullable = false)
    private long productPrice;
}
