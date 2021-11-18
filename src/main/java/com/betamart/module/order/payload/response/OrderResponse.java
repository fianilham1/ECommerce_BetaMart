package com.betamart.module.order.payload.response;

import com.betamart.model.OrderProduct;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;


@Data
public class OrderResponse {

    private Long id;

    @JsonProperty(value = "order_product_list")
    private List<OrderProductResponse> orderProductResponseList;

    @JsonProperty(value = "total_price")
    private long totalPrice;

    @JsonProperty(value = "total_quantity")
    private int totalQuantity;

    private String status;

}
