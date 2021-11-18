package com.betamart.module.order.payload.request;

import com.betamart.model.OrderProduct;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;


@Data
public class OrderRequest {

    @JsonProperty(value = "order_product_list")
    private List<OrderProductRequest> orderProductRequestList;

    @JsonProperty(value = "total_price")
    private long totalPrice;

    @JsonProperty(value = "total_quantity")
    private int totalQuantity;

    private String status;

}
