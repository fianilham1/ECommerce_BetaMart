package com.betamart.model;

import com.betamart.enumeration.OrderStatusEnum;
import com.betamart.model.auditable.BaseModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "orderr")
@Data
public class Order extends BaseModel {

    //id -> order Id

    @OneToMany(fetch = FetchType.LAZY, mappedBy="order", cascade = CascadeType.ALL)
    private List<OrderProductDetails> orderProductDetailsList;

    @ManyToOne
    @JoinColumn(name = "fk_user_id", nullable = false)
    private User user;

    @Column(name = "customer_note")
    private String customerNote;

    @Column(name = "total_all_quantity", nullable = false)
    private int totalAllQuantity;

    @Column(name = "total_quantity_by_product", nullable = false)
    private int totalQuantityByProduct;

    @Column(name = "total_shipping_weights", nullable = false)
    private int totalShippingWeights;

    @Column(name = "total_products_price", nullable = false)
    private long totalProductsPrice;

    @Column(name = "shipping_price", nullable = false)
    private long shippingPrice;

    @Column(name = "total_price", nullable = false)
    private long totalPrice;

    @Column(name = "shipping_Date")
    private Date shippingDate;

    @Column(name = "delivered_Date")
    private Date deliveredDate;

    @OneToOne(fetch = FetchType.LAZY, mappedBy="order", cascade = CascadeType.ALL)
    private OrderDestinationLocationDetails orderDestinationLocationDetails;

    @Column(name = "shipping_service_name")
    private String shippingServiceName;

    @Column(name = "shipping_service_id")
    private long shippingServiceId;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum orderStatus;
}
