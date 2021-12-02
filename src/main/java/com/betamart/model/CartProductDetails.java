package com.betamart.model;

import com.betamart.model.auditable.BaseModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "cart_product_details")
@Data
public class CartProductDetails extends BaseModel {

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "fk_cart_id", nullable = false)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "fk_product_id", nullable = false)
    private Product product;

    @Column(name = "product_quantity", nullable = false)
    private int productQuantity;

    @Column(name = "product_price", nullable = false)
    private long productPrice;

}
