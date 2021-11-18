package com.betamart.service;

import com.betamart.base.payload.response.BaseResponse;
import com.betamart.module.product.payload.request.ProductListRequest;
import com.betamart.module.product.payload.request.ProductRequest;

public interface ProductService {
    BaseResponse<?> addProduct(ProductListRequest productListRequest, String username);

    BaseResponse<?> getAllProduct();

    BaseResponse<?> getProduct(Long id);

    BaseResponse<?> deleteProduct (Long id, String username);

    BaseResponse<?> updateProduct (ProductRequest editProductRequest, Long id, String username);

    BaseResponse<?> updateProductQuantity (int newQuantity, Long id, String username);

}
