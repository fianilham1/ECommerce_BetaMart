package com.betamart.service;

import com.betamart.base.payload.response.BaseResponse;
import com.betamart.module.order.payload.request.OrderRequest;

public interface OrderService {

    BaseResponse<?> addOrderUser(OrderRequest orderRequest, String username);

    BaseResponse<?> getAllOrderUser(String username);

    BaseResponse<?> getOrderUser(String username, Long id);

    BaseResponse<?> updateOrderStatusUser(Long id, String status, String username);

}
