package com.betamart.module.order.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrderProductRequest {

    @JsonProperty(value = "product_id")
    private Long productId;

    @JsonProperty(value = "product_quantity")
    private int productQuantity;

}
