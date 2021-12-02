package com.betamart.model.payloadResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class ProductResponse {

    private String name;

    private String brand;

    private String code;

    private String productSeries;

    private String size;

    private int netWeight;

    private long price;

    private String image;

    private Date expDate;

    private Long productCategoryId;

}
