package com.betamart.controller;

import com.betamart.common.constant.CommonMessage;
import com.betamart.common.payload.response.BaseResponse;
import com.betamart.common.util.JwtUtil;
import com.betamart.model.Product;
import com.betamart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BaseResponse<?> addProduct(@RequestBody List<Product> productListRequest, @RequestHeader("Authorization") String token){
        System.out.println(productListRequest);
        String username;
        try {
            System.out.println("token "+token);
            username = jwtUtil.extractUsername(token.substring(7));
        }catch (Exception e){
            System.out.println(e);
            return new BaseResponse<>(CommonMessage.ERROR);
        }
        return productService.addProduct(productListRequest, username);
    }

    @GetMapping("/all")
    public BaseResponse<?> getAllProduct() {
        return productService.getAllProduct();
    }

    @GetMapping("/{id}")
    public BaseResponse<?> getProduct(@PathVariable("id") Long id) {
        return productService.getProduct(id);
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BaseResponse<?> deleteProduct(@PathVariable("id") Long id, @RequestHeader("Authorization") String token) {
        System.out.println(id);
        String username;
        try {
            System.out.println("token "+token);
            username = jwtUtil.extractUsername(token.substring(7));
        }catch (Exception e){
            System.out.println(e);
            return new BaseResponse<>(CommonMessage.ERROR);
        }
        return productService.deleteProduct(id, username);
    }
    
    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BaseResponse<?> updateProduct(@PathVariable("id") Long id, @RequestBody Product updatedProductRequest, @RequestHeader("Authorization") String token) {
        System.out.println(updatedProductRequest);
        String username;
        try {
            System.out.println("token "+token);
            username = jwtUtil.extractUsername(token.substring(7));
        }catch (Exception e){
            System.out.println(e);
            return new BaseResponse<>(CommonMessage.ERROR);
        }
        return productService.updateProduct(updatedProductRequest, id, username);
    }

    @PostMapping("/update-quantity/{id}")
    public BaseResponse<?> updateProductQuantity(@PathVariable("id") Long id, @RequestBody int newQuantity, @RequestHeader("Authorization") String token) {
        System.out.println(newQuantity);
        String username;
        try {
            System.out.println("token "+token);
            username = jwtUtil.extractUsername(token.substring(7));
        }catch (Exception e){
            System.out.println(e);
            return new BaseResponse<>(CommonMessage.ERROR);
        }
        return productService.updateProductQuantity(newQuantity, id, username);
    }


}
