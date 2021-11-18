package com.betamart.repository;

import com.betamart.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCreatedBy(String createdBy);
    Order findByCreatedByAndId(String createdBy, Long Id);
}
