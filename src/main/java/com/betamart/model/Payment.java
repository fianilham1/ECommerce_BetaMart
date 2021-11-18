package com.betamart.model;

import com.betamart.model.auditable.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "payment")
@Data
public class Payment extends BaseModel {

    //id -> payment Id

    @Column(name = "order_id", nullable = false)
    private String orderId;

    @Column(name = "total_price", nullable = false)
    private long totalPrice;

    @Column(name = "payment_type", nullable = false)
    private String paymentType;

    @Column(name = "status", nullable = false)
    private String status;
}
