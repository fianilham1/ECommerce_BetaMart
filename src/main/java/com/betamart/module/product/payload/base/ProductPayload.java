package com.betamart.module.product.payload.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ProductPayload {

    private String name;

    @JsonProperty(value = "quantity_in_stock")
    private int quantityInStock;

    private String brand;

    private String code;

    @JsonProperty(value = "product_series")
    private String productSeries;

    private String size;

    @JsonProperty(value = "net_weight")
    private int netWeight;

    private long price;

    private String image;

    @JsonProperty(value = "exp_date")
    private Date expDate;

    @JsonProperty(value = "product_category_id")
    private Long productCategoryId;

    private boolean isReady;
    private boolean isDeleted;

}
