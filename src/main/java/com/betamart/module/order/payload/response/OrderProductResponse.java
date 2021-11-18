package com.betamart.module.order.payload.response;

import com.betamart.module.product.payload.response.ProductResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class OrderProductResponse extends ProductResponse {

    @JsonProperty(value = "product_quantity")
    private int productQuantity;

    @JsonProperty(value = "product_price")
    private long productPrice;

}
