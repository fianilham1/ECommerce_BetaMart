package com.betamart.service.impl;

import com.betamart.common.constant.CommonMessage;
import com.betamart.common.payload.response.BaseResponse;
import com.betamart.common.util.MapperUtil;
import com.betamart.enumeration.OrderStatusEnum;
import com.betamart.model.Order;
import com.betamart.model.OrderProductDetails;
import com.betamart.model.Product;
import com.betamart.model.User;
import com.betamart.model.payloadResponse.OrderProductDetailsResponse;
import com.betamart.model.payloadResponse.OrderResponse;
import com.betamart.repository.OrderProductRepository;
import com.betamart.repository.OrderRepository;
import com.betamart.repository.ProductRepository;
import com.betamart.repository.UserRepository;
import com.betamart.service.OrderService;
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
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderProductRepository orderProductRepository;

    @Override
    public BaseResponse<?> addNewOrderCustomer(Order orderRequest, String username){
        try{
            Order order = new Order();
            order.setOrderStatus(OrderStatusEnum.PENDING_PAYMENT);
            order.setCreatedBy(username);
            order.setCreatedDate(new Date());
            order.setCustomerNote(orderRequest.getCustomerNote());
            User user = userRepository.findByUsername(username);
            order.setUser(user);

            orderRepository.save(order);

            //check quantity per product FIRST in Order >>>>
            for (OrderProductDetails orderProductDetails : orderRequest.getOrderProductDetailsList()){
                Product product = productRepository.findById(orderProductDetails.getProduct().getId()).get();
                int newQuantity = product.getQuantityInStock() - orderProductDetails.getProductQuantity();
                if(newQuantity<0){
                    return new BaseResponse<>(CommonMessage.ORDER_OUT);
                }
            }

            AtomicReference<Long> totalProductsPrice = new AtomicReference<>(0L);
            AtomicInteger totalQuantity = new AtomicInteger();
            List<OrderProductDetails> orderProductDetailsList = new ArrayList<>();

            System.out.println("Quantity Per Product OK........");
            System.out.println("cek qty "+ orderRequest);
            orderRequest.getOrderProductDetailsList().forEach(orderProductDetailsRequest -> {
                Product product = productRepository.findById(orderProductDetailsRequest.getProduct().getId()).get();
                OrderProductDetails orderProductDetails = new OrderProductDetails();
                orderProductDetails.setOrder(order);
                orderProductDetails.setProduct(product);
                orderProductDetails.setProductQuantity(orderProductDetailsRequest.getProductQuantity());
                orderProductDetails.setProductPrice(product.getPrice()* orderProductDetailsRequest.getProductQuantity());
                orderProductDetails.setCreatedBy(username);
                orderProductDetails.setCreatedDate(new Date());
                orderProductRepository.save(orderProductDetails);

                orderProductDetailsList.add(orderProductDetails);
                totalProductsPrice.set(totalProductsPrice.get() + orderProductDetails.getProductPrice());
                totalQuantity.addAndGet(orderProductDetails.getProductQuantity());

                //update inStock Quantity Product
                int newQuantity = product.getQuantityInStock()- orderProductDetailsRequest.getProductQuantity();
                product.setQuantityInStock(newQuantity);
                productRepository.save(product);
            });

            //add shipping detail
            order.setShippingServiceId(1);
            order.setShippingServiceName("TIKI");
            order.setOrderDestinationLocationDetails(orderRequest.getOrderDestinationLocationDetails());

            int shippingPrice = 10000;
            order.setShippingPrice(shippingPrice);

            //re-calculate the price of order
            order.setTotalProductsPrice(totalProductsPrice.get());
            order.setTotalPrice(totalProductsPrice.get()+shippingPrice);
            order.setTotalAllQuantity(totalQuantity.get());
            orderRepository.save(order); //update total price and total qty

            return new BaseResponse<>(CommonMessage.ORDER_OK, "Success");
        }catch (Exception ex){
            System.out.println("error "+ex);
            return new BaseResponse<>(CommonMessage.ORDER_ERROR);
        }
    }

    @Override
    public BaseResponse<?> getAllOrderCustomer(String username){
        try{
            List<Order> orderList = orderRepository.findByCreatedBy(username);
            List<OrderResponse> orderResponseList = new ArrayList<>();
            orderList.forEach(order -> {
                List<OrderProductDetails> orderProductDetailsList = order.getOrderProductDetailsList();
                List<OrderProductDetailsResponse> orderProductDetailsResponseList = new ArrayList<>();
                orderProductDetailsList.forEach(orderProductDetails -> {
                    OrderProductDetailsResponse orderProductDetailsResponse = MapperUtil.parse(orderProductDetails.getProduct(), OrderProductDetailsResponse.class, MatchingStrategies.STRICT);
                    orderProductDetailsResponse.setProductPrice(orderProductDetails.getProductPrice());
                    orderProductDetailsResponse.setProductQuantity(orderProductDetails.getProductQuantity());
                    orderProductDetailsResponseList.add(orderProductDetailsResponse);
                });

                OrderResponse orderResponse = MapperUtil.parse(order, OrderResponse.class, MatchingStrategies.STRICT);
                orderResponse.setOrderProductDetailsResponseList(orderProductDetailsResponseList);
                orderResponseList.add(orderResponse);
            });
            return new BaseResponse<>(CommonMessage.FOUND, orderResponseList);
        }catch (Exception e){
            System.out.println("error "+e);
            return new BaseResponse<>(CommonMessage.NOT_FOUND);
        }
    }

    @Override
    public BaseResponse<?> getOrderCustomer(String username, Long id){
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
    public BaseResponse<?> updateOrderStatusCustomer(Long id, OrderStatusEnum orderStatus, String username) {
        try{
            Order order = orderRepository.findByCreatedByAndId(username, id);
            order.setOrderStatus(orderStatus);
            orderRepository.save(order);
            return new BaseResponse<>(CommonMessage.UPDATED, "Success");
        }catch (Exception e){
            System.out.println("error "+e);
            return new BaseResponse<>(CommonMessage.NOT_UPDATED);
        }
    }

}
