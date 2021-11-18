package com.betamart.module.productCategory.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;


@Data
public class ProductCategoryListRequest {

    @JsonProperty(value = "name_list")
    private List<String> nameList;

}
