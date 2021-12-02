package com.betamart.controller;

import com.betamart.common.constant.CommonMessage;
import com.betamart.common.payload.response.BaseResponse;
import com.betamart.common.util.JwtUtil;
import com.betamart.model.ProductCategory;
import com.betamart.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/product-category")
public class ProductCategoryController {

    @Autowired
    ProductCategoryService productCategoryService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BaseResponse<?> addAllProductCategory(@RequestBody List<String> productCategoryListRequest, @RequestHeader("Authorization") String token){
        System.out.println(productCategoryListRequest);
        String username;
        try {
            System.out.println("token "+token);
            username = jwtUtil.extractUsername(token.substring(7));
        }catch (Exception e){
            System.out.println(e);
            return new BaseResponse<>(CommonMessage.ERROR);
        }
        return productCategoryService.addAllProductCategory(productCategoryListRequest, username);
    }

    @GetMapping("/all")
    public BaseResponse<?> getAllProductCategory() {
        return productCategoryService.getAllProductCategory();
    }

    @GetMapping("/{id}")
    public BaseResponse<?> getProductCategory(@PathVariable("id") Long id) {
        return productCategoryService.getProductCategory(id);
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BaseResponse<?> deleteProductCategory(@PathVariable("id") Long id,  @RequestHeader("Authorization") String token) {
        System.out.println(id);
        String username;
        try {
            System.out.println("token "+token);
            username = jwtUtil.extractUsername(token.substring(7));
        }catch (Exception e){
            System.out.println(e);
            return new BaseResponse<>(CommonMessage.ERROR);
        }
        return productCategoryService.deleteProductCategory(id, username);
    }
    
    @PostMapping("/update/{id}")
    public BaseResponse<?> updateProductCategory(@PathVariable("id") Long id, @RequestBody ProductCategory productCategoryRequest, @RequestHeader("Authorization") String token) {
        String username;
        try {
            System.out.println("token "+token);
            username = jwtUtil.extractUsername(token.substring(7));
        }catch (Exception e){
            System.out.println(e);
            return new BaseResponse<>(CommonMessage.ERROR);
        }
        System.out.println(productCategoryRequest);
        return productCategoryService.updateProductCategory(productCategoryRequest, id, username);
    }

}
