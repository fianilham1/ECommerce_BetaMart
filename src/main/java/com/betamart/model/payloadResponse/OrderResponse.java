package com.betamart.model.payloadResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;


@Data
public class OrderResponse {

    private Long id;

    private List<OrderProductDetailsResponse> orderProductDetailsResponseList;

    private long totalPrice;

    private int totalQuantity;

    private String status;

}
