package com.betamart.service;

import com.betamart.base.payload.response.BaseResponse;
import com.betamart.module.productCategory.payload.request.ProductCategoryListRequest;
import com.betamart.module.productCategory.payload.request.ProductCategoryRequest;

public interface ProductCategoryService {

    BaseResponse<?> addAllProductCategory(ProductCategoryListRequest productCategoryListRequest, String username);

    BaseResponse<?> getAllProductCategory();

    BaseResponse<?> getProductCategory(Long id);

    BaseResponse<?> deleteProductCategory (Long id, String username);

    BaseResponse<?> updateProductCategory (ProductCategoryRequest editProductCategoryListRequest, Long id, String username);

}
