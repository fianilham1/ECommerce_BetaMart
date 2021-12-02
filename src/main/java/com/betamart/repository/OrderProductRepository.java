package com.betamart.repository;

import com.betamart.model.Order;
import com.betamart.model.OrderProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProductDetails, Long> {
    List<OrderProductDetails> findByOrder(Order order);
}
