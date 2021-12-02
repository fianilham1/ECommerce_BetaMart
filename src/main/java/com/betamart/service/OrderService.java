package com.betamart.service;

import com.betamart.common.payload.response.BaseResponse;
import com.betamart.enumeration.OrderStatusEnum;
import com.betamart.model.Order;

public interface OrderService {

    BaseResponse<?> addNewOrderCustomer(Order orderRequest, String username);

    BaseResponse<?> getAllOrderCustomer(String username);

    BaseResponse<?> getOrderCustomer(String username, Long id);

    BaseResponse<?> updateOrderStatusCustomer(Long id, OrderStatusEnum orderStatus, String username);

}
