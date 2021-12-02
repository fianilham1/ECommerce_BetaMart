package com.betamart.service.impl;

import com.betamart.common.constant.CommonMessage;
import com.betamart.common.payload.response.BaseResponse;
import com.betamart.common.util.JwtUtil;
import com.betamart.common.util.MapperUtil;
import com.betamart.model.ProductCategory;
import com.betamart.model.payloadResponse.ProductCategoryResponse;
import com.betamart.repository.ProductCategoryRepository;
import com.betamart.service.ProductCategoryService;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public BaseResponse<?> addAllProductCategory(List<String> productCategoryListRequest, String username){
        try{
            AtomicBoolean doesExistAll = new AtomicBoolean(true);
            productCategoryListRequest.forEach(name->{
                if(!productCategoryRepository.existsByName(name)){
                    ProductCategory productCategory = new ProductCategory();
                    productCategory.setName(name);
                    productCategory.setDeleted(false);
                    productCategory.setCreatedBy(username);
                    productCategory.setCreatedDate(new Date());
                    productCategoryRepository.save(productCategory);
                    doesExistAll.set(false);
                }
            });
            if(doesExistAll.get()){
                return new BaseResponse<>(CommonMessage.NOT_SAVED);
            }
            return new BaseResponse<>(CommonMessage.SAVED, "Success");
        }catch (Exception ex){
            System.out.println("error "+ex);
            return new BaseResponse<>(CommonMessage.NOT_SAVED);
        }
    }

    @Override
    public BaseResponse<?> getAllProductCategory(){
        try{
            List<ProductCategory> productCategoryList = productCategoryRepository.findByIsDeleted(false);
            List<ProductCategoryResponse> productCategoryResponseList = new ArrayList<>();
            productCategoryList.forEach(productCategory -> {
                ProductCategoryResponse productCategoryResponse = MapperUtil.parse(productCategory, ProductCategoryResponse.class, MatchingStrategies.STRICT);
                productCategoryResponseList.add(productCategoryResponse);
            });
            return new BaseResponse<>(CommonMessage.FOUND, productCategoryResponseList);
        }catch (Exception e){
            System.out.println("error "+e);
            return new BaseResponse<>(CommonMessage.NOT_FOUND);
        }
    }

    @Override
    public BaseResponse<?> getProductCategory(Long id){
        try{
            ProductCategory productCategory = productCategoryRepository.findById(id).get();

            ProductCategoryResponse productCategoryResponse = MapperUtil.parse(productCategory, ProductCategoryResponse.class, MatchingStrategies.STRICT);
            return new BaseResponse<>(CommonMessage.FOUND, productCategoryResponse);
        }catch (Exception e){
            System.out.println("error "+e);
            return new BaseResponse<>(CommonMessage.NOT_FOUND);
        }
    }

    @Override
    public BaseResponse<?> deleteProductCategory (Long id, String username){
       try{
           ProductCategory productCategory = productCategoryRepository.findById(id).get();
           productCategory.setDeleted(true);
           productCategory.setUpdatedBy(username);
           productCategory.setUpdatedDate(new Date());
           productCategoryRepository.save(productCategory);
           return new BaseResponse<>(CommonMessage.DELETED, "Success");
       }catch (Exception e){
           System.out.println("error "+e);
           return new BaseResponse<>(CommonMessage.NOT_DELETED);
       }
    }

    @Override
    public BaseResponse<?> updateProductCategory (ProductCategory updatedProductCategory, Long id, String username) {
        try{
            ProductCategory productCategory = productCategoryRepository.findById(id).get();
            productCategory.setName(updatedProductCategory.getName());
            productCategory.setUpdatedBy(username);
            productCategory.setUpdatedDate(new Date());
            productCategoryRepository.save(productCategory);
            return new BaseResponse<>(CommonMessage.UPDATED, "Success");
        }catch (Exception e){
            System.out.println("error "+e);
            return new BaseResponse<>(CommonMessage.NOT_UPDATED);
        }
    }


}
