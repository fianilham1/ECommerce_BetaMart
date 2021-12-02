package com.betamart.service;

import com.betamart.common.payload.response.BaseResponse;
import com.betamart.model.ProductCategory;

import java.util.List;

public interface ProductCategoryService {

    BaseResponse<?> addAllProductCategory(List<String> productCategoryListRequest, String username);

    BaseResponse<?> getAllProductCategory();

    BaseResponse<?> getProductCategory(Long id);

    BaseResponse<?> deleteProductCategory (Long id, String username);

    BaseResponse<?> updateProductCategory (ProductCategory updatedProductCategory, Long id, String username);

}
