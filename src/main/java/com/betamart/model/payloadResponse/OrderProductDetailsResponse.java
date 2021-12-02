package com.betamart.model.payloadResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class OrderProductDetailsResponse extends ProductResponse {

    private int productQuantity;

    private long productPrice;

}
