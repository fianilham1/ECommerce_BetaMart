package com.betamart.model;

import com.betamart.model.auditable.BaseModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "cart")
@Data
public class Cart extends BaseModel {

    //id -> cart Id

    @ManyToOne
    @JoinColumn(name = "fk_user_id", nullable = false)
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="cart", cascade = CascadeType.ALL)
    private List<CartProductDetails> cartProductDetailsList;

    @Column(name = "total_products_price", nullable = false)
    private long totalProductsPrice;

}
