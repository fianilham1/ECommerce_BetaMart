package com.betamart.module.product.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProductListRequest {

    @JsonProperty(value = "product_list")
    private List<ProductRequest> productList;
}
