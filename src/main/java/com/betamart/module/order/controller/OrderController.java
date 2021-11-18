package com.betamart.module.order.controller;

import com.betamart.base.constant.CommonMessage;
import com.betamart.base.payload.response.BaseResponse;
import com.betamart.base.util.JwtUtil;
import com.betamart.module.order.payload.request.OrderRequest;
import com.betamart.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/user/add")
    public BaseResponse<?> addOrderUser(@RequestBody OrderRequest orderRequest, @RequestHeader("Authorization") String token){
        System.out.println(orderRequest);
        String username;
        try {
            System.out.println("token "+token);
            username = jwtUtil.extractUsername(token.substring(7));
        }catch (Exception e){
            System.out.println(e);
            return new BaseResponse<>(CommonMessage.ERROR);
        }
        return orderService.addOrderUser(orderRequest, username);
    }

    @GetMapping("/user/all")
    public BaseResponse<?> getAllOrderUser(@RequestHeader("Authorization") String token) {
        String username;
        try {
            System.out.println("token "+token);
            username = jwtUtil.extractUsername(token.substring(7));
        }catch (Exception e){
            System.out.println(e);
            return new BaseResponse<>(CommonMessage.ERROR);
        }
        return orderService.getAllOrderUser(username);
    }

    @PostMapping("/user/cancel/{id}")
    public BaseResponse<?> cancelOrder(@PathVariable("id") Long id, @RequestHeader("Authorization") String token){
        System.out.println("order id: "+id);
        String username;
        try {
            System.out.println("token "+token);
            username = jwtUtil.extractUsername(token.substring(7));
        }catch (Exception e){
            System.out.println(e);
            return new BaseResponse<>(CommonMessage.ERROR);
        }
        return orderService.updateOrderStatusUser(id,"cancel", username);
    }

    @PostMapping("/user/finish/{id}")
    public BaseResponse<?> finishOrder(@PathVariable("id") Long id, @RequestHeader("Authorization") String token){
        System.out.println("order id: "+id);
        String username;
        try {
            System.out.println("token "+token);
            username = jwtUtil.extractUsername(token.substring(7));
        }catch (Exception e){
            System.out.println(e);
            return new BaseResponse<>(CommonMessage.ERROR);
        }
        return orderService.updateOrderStatusUser(id,"payment_success", username);
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
