package com.betamart.module.productCategory.controller;

import com.betamart.base.constant.CommonMessage;
import com.betamart.base.payload.response.BaseResponse;
import com.betamart.base.util.JwtUtil;
import com.betamart.module.productCategory.payload.request.ProductCategoryListRequest;
import com.betamart.module.productCategory.payload.request.ProductCategoryRequest;
import com.betamart.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;


@RestController
@RequestMapping("/product-category")
public class ProductCategoryController {

    @Autowired
    ProductCategoryService productCategoryService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BaseResponse<?> addAllProductCategory(@RequestBody ProductCategoryListRequest productCategoryListRequest, @RequestHeader("Authorization") String token){
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
    public BaseResponse<?> updateProductCategory(@PathVariable("id") Long id, @RequestBody ProductCategoryRequest editProductCategoryListRequest,  @RequestHeader("Authorization") String token) {
        String username;
        try {
            System.out.println("token "+token);
            username = jwtUtil.extractUsername(token.substring(7));
        }catch (Exception e){
            System.out.println(e);
            return new BaseResponse<>(CommonMessage.ERROR);
        }
        System.out.println(editProductCategoryListRequest);
        return productCategoryService.updateProductCategory(editProductCategoryListRequest, id, username);
    }

}
