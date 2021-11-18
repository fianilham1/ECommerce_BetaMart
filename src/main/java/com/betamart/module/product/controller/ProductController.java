package com.betamart.module.product.controller;

import com.betamart.base.constant.CommonMessage;
import com.betamart.base.payload.response.BaseResponse;
import com.betamart.base.util.JwtUtil;
import com.betamart.module.product.payload.request.ProductListRequest;
import com.betamart.module.product.payload.request.ProductRequest;
import com.betamart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BaseResponse<?> addProduct(@RequestBody ProductListRequest productListRequest, @RequestHeader("Authorization") String token){
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
    public BaseResponse<?> updateProduct(@PathVariable("id") Long id, @RequestBody ProductRequest editProductRequest, @RequestHeader("Authorization") String token) {
        System.out.println(editProductRequest);
        String username;
        try {
            System.out.println("token "+token);
            username = jwtUtil.extractUsername(token.substring(7));
        }catch (Exception e){
            System.out.println(e);
            return new BaseResponse<>(CommonMessage.ERROR);
        }
        return productService.updateProduct(editProductRequest, id, username);
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
