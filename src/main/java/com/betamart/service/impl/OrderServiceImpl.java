package com.betamart.service.impl;

import com.betamart.base.constant.CommonMessage;
import com.betamart.base.payload.response.BaseResponse;
import com.betamart.base.util.MapperUtil;
import com.betamart.model.Order;
import com.betamart.model.OrderProduct;
import com.betamart.model.Product;
import com.betamart.module.order.payload.request.OrderProductRequest;
import com.betamart.module.order.payload.request.OrderRequest;
import com.betamart.module.order.payload.response.OrderProductResponse;
import com.betamart.module.order.payload.response.OrderResponse;
import com.betamart.repository.OrderProductRepository;
import com.betamart.repository.OrderRepository;
import com.betamart.repository.ProductRepository;
import com.betamart.service.OrderService;
import org.aspectj.weaver.ast.Or;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderProductRepository orderProductRepository;

    @Override
    public BaseResponse<?> addOrderUser(OrderRequest orderRequest, String username){
        try{
            Order order = new Order();
            order.setStatus("waiting_payment");
            order.setCreatedBy(username);
            order.setCreatedDate(new Date());
            orderRepository.save(order);

            //check quantity per product FIRST in Order >>>>
            for (OrderProductRequest orderProductRequest : orderRequest.getOrderProductRequestList()){
                Product product = productRepository.findById(orderProductRequest.getProductId()).get();
                int newQuantity = product.getQuantityInStock()-orderProductRequest.getProductQuantity();
                if(newQuantity<0){
                    return new BaseResponse<>(CommonMessage.ORDER_OUT);
                }

            }

            AtomicReference<Long> totalPrice = new AtomicReference<>(0L);
            AtomicInteger totalQuantity = new AtomicInteger();
            List<OrderProduct> orderProductList = new ArrayList<>();

            System.out.println("Quantity Per Product OK........");
            orderRequest.getOrderProductRequestList().forEach(orderProductRequest -> {
                Product product = productRepository.findById(orderProductRequest.getProductId()).get();
                OrderProduct orderProduct = new OrderProduct();
                orderProduct.setOrder(order);
                orderProduct.setProduct(product);
                orderProduct.setProductQuantity(orderProductRequest.getProductQuantity());
                orderProduct.setProductPrice(product.getPrice()*orderProductRequest.getProductQuantity());
                orderProduct.setCreatedBy(username);
                orderProduct.setCreatedDate(new Date());
                orderProductRepository.save(orderProduct);

                orderProductList.add(orderProduct);
                totalPrice.set(totalPrice.get() + orderProduct.getProductPrice());
                totalQuantity.addAndGet(orderProduct.getProductQuantity());

                //update inStock Quantity Product
                int newQuantity = product.getQuantityInStock()-orderProductRequest.getProductQuantity();
                product.setQuantityInStock(newQuantity);
                productRepository.save(product);
            });

            order.setTotalPrice(totalPrice.get());
            order.setTotalQuantity(totalQuantity.get());
            orderRepository.save(order); //update total price and total qty

            return new BaseResponse<>(CommonMessage.ORDER_OK, "Success");
        }catch (Exception ex){
            System.out.println("error "+ex);
            return new BaseResponse<>(CommonMessage.ORDER_ERROR);
        }
    }

    @Override
    public BaseResponse<?> getAllOrderUser(String username){
        try{
            List<Order> orderList = orderRepository.findByCreatedBy(username);
            List<OrderResponse> orderResponseList = new ArrayList<>();
            orderList.forEach(order -> {
                List<OrderProduct> orderProductList = order.getOrderProductList();
                List<OrderProductResponse> orderProductResponseList = new ArrayList<>();
                orderProductList.forEach(orderProduct -> {
                    OrderProductResponse orderProductResponse = MapperUtil.parse(orderProduct.getProduct(), OrderProductResponse.class, MatchingStrategies.STRICT);
                    orderProductResponse.setProductPrice(orderProduct.getProductPrice());
                    orderProductResponse.setProductQuantity(orderProduct.getProductQuantity());
                    orderProductResponseList.add(orderProductResponse);
                });

                OrderResponse orderResponse = MapperUtil.parse(order, OrderResponse.class, MatchingStrategies.STRICT);
                orderResponse.setOrderProductResponseList(orderProductResponseList);
                orderResponseList.add(orderResponse);
            });
            return new BaseResponse<>(CommonMessage.FOUND, orderResponseList);
        }catch (Exception e){
            System.out.println("error "+e);
            return new BaseResponse<>(CommonMessage.NOT_FOUND);
        }
    }

    @Override
    public BaseResponse<?> getOrderUser(String username, Long id){
        try{
            Order order = orderRepository.findByCreatedByAndId(username, id);
            OrderResponse orderResponse = MapperUtil.parse(order, OrderResponse.class, MatchingStrategies.STRICT);

            return new BaseResponse<>(CommonMessage.FOUND, orderResponse);
        }catch (Exception e){
            System.out.println("error "+e);
            return new BaseResponse<>(CommonMessage.NOT_FOUND);
        }
    }

    @Override
    public BaseResponse<?> updateOrderStatusUser(Long id, String status, String username) {
        try{
            Order order = orderRepository.findByCreatedByAndId(username, id);
            order.setStatus(status);
            orderRepository.save(order);
            return new BaseResponse<>(CommonMessage.UPDATED, "Success");
        }catch (Exception e){
            System.out.println("error "+e);
            return new BaseResponse<>(CommonMessage.NOT_UPDATED);
        }
    }

}
