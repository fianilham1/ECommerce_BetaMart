package com.betamart.service;

import com.betamart.common.payload.response.BaseResponse;
import com.betamart.model.Product;

import java.util.List;

public interface ProductService {
    BaseResponse<?> addProduct(List<Product> productListRequest, String username);

    BaseResponse<?> getAllProduct();

    BaseResponse<?> getProduct(Long id);

    BaseResponse<?> deleteProduct (Long id, String username);

    BaseResponse<?> updateProduct (Product updatedProductRequest, Long id, String username);

    BaseResponse<?> updateProductQuantity (int newQuantity, Long id, String username);

}
