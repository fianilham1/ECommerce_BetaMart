package com.betamart.model;

import com.betamart.model.auditable.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "orderr")
@Data
public class Order extends BaseModel {

    //id -> order Id

    @OneToMany(fetch = FetchType.LAZY, mappedBy="order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProductList;

    @Column(name = "total_price", nullable = false)
    private long totalPrice;

    @Column(name = "total_quantity", nullable = false)
    private int totalQuantity;

    @Column(name = "status", nullable = false)
    private String status;
}
