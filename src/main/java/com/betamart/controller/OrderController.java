package com.betamart.controller;

import com.betamart.common.constant.CommonMessage;
import com.betamart.common.payload.response.BaseResponse;
import com.betamart.common.util.JwtUtil;
import com.betamart.enumeration.OrderStatusEnum;
import com.betamart.model.Order;
import com.betamart.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/add-new")
    public BaseResponse<?> addOrderCustomer(@RequestBody Order orderRequest, @RequestHeader("Authorization") String token){
        System.out.println(orderRequest);
        String username;
        try {
            System.out.println("token "+token);
            username = jwtUtil.extractUsername(token.substring(7));
        }catch (Exception e){
            System.out.println(e);
            return new BaseResponse<>(CommonMessage.ERROR);
        }
        return orderService.addNewOrderCustomer(orderRequest, username);
    }

    @GetMapping("/all")
    public BaseResponse<?> getAllOrderCustomer(@RequestHeader("Authorization") String token) {
        String username;
        try {
            System.out.println("token "+token);
            username = jwtUtil.extractUsername(token.substring(7));
        }catch (Exception e){
            System.out.println(e);
            return new BaseResponse<>(CommonMessage.ERROR);
        }
        return orderService.getAllOrderCustomer(username);
    }

    @PostMapping("/cancel/{id}")
    public BaseResponse<?> cancelOrderCustomer(@PathVariable("id") Long id, @RequestHeader("Authorization") String token){
        System.out.println("order id: "+id);
        String username;
        try {
            System.out.println("token "+token);
            username = jwtUtil.extractUsername(token.substring(7));
        }catch (Exception e){
            System.out.println(e);
            return new BaseResponse<>(CommonMessage.ERROR);
        }
        return orderService.updateOrderStatusCustomer(id, OrderStatusEnum.CANCELED, username);
    }

    @PostMapping("/payment/success/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BaseResponse<?> updateOrderPaymentStatus(@PathVariable("id") Long id, @RequestHeader("Authorization") String token){
        System.out.println("order id: "+id);
        String username;
        try {
            System.out.println("token "+token);
            username = jwtUtil.extractUsername(token.substring(7));
        }catch (Exception e){
            System.out.println(e);
            return new BaseResponse<>(CommonMessage.ERROR);
        }
        return orderService.updateOrderStatusCustomer(id,OrderStatusEnum.PAYMENT_APPROVED, username);
    }


    @PostMapping("/shipping")
    public BaseResponse<?> shippingOrderCustomer(@RequestBody Order orderRequest, @RequestHeader("Authorization") String token){
        System.out.println(orderRequest);
        String username;
        try {
            System.out.println("token "+token);
            username = jwtUtil.extractUsername(token.substring(7));
        }catch (Exception e){
            System.out.println(e);
            return new BaseResponse<>(CommonMessage.ERROR);
        }
        return orderService.addNewOrderCustomer(orderRequest, username);
    }

//
//    @GetMapping("/{id}")
//    public BaseResponse<?> getProduct(@PathVariable("id") Long id) {
//        return productService.getProduct(id);
//    }
//
//    @PostMapping("/delete/{id}")
//    public BaseResponse<?> deleteProduct(@PathVariable("id") Long id) {
//        System.out.println(id);
//        return productService.deleteProduct(id);
//    }
//
//    @PostMapping("/update/{id}")
//    public BaseResponse<?> updateProduct(@PathVariable("id") Long id, @RequestBody ProductRequest editProductRequest) throws ParseException, InvocationTargetException, IllegalAccessException {
//        System.out.println(editProductRequest);
//        return productService.updateProduct(editProductRequest, id);
//    }
//
//    @PostMapping("/update-quantity/{id}")
//    public BaseResponse<?> updateProductQuantity(@PathVariable("id") Long id, @RequestBody int newQuantity) throws ParseException, InvocationTargetException, IllegalAccessException {
//        System.out.println(newQuantity);
//        return productService.updateProductQuantity(newQuantity, id);
//    }


}
