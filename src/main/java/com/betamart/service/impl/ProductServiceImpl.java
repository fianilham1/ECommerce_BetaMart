package com.betamart.service.impl;

import com.betamart.common.constant.CommonMessage;
import com.betamart.common.payload.response.BaseResponse;
import com.betamart.common.util.MapperUtil;
import com.betamart.model.Product;
import com.betamart.model.ProductCategory;
import com.betamart.model.payloadResponse.ProductResponse;
import com.betamart.repository.ProductCategoryRepository;
import com.betamart.repository.ProductRepository;
import com.betamart.service.ProductService;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Override
    public BaseResponse<?> addProduct(List<Product> productListRequest, String username){
        try{
            productListRequest.forEach(productRequest -> {
                Product product = MapperUtil.parse(productRequest, Product.class, MatchingStrategies.STRICT);
                ProductCategory productCategory = productCategoryRepository.findById(productRequest.getProductCategory().getId()).get();

                product.setProductCategory(productCategory);
                product.setReady(true);
                product.setDeleted(false);
                product.setCreatedBy(username);
                product.setCreatedDate(new Date());
                productRepository.save(product);
            });
            return new BaseResponse<>(CommonMessage.SAVED, "Success");
        }catch (Exception ex){
            System.out.println("error "+ex);
            return new BaseResponse<>(CommonMessage.NOT_SAVED);
        }
    }

    @Override
    public BaseResponse<?> getAllProduct(){
        try{
            List<Product> productList = productRepository.findByIsDeleted(false);
            List<ProductResponse> productResponseList = new ArrayList<>();

            productList.forEach(product -> {
                ProductResponse productResponse = MapperUtil.parse(product, ProductResponse.class, MatchingStrategies.STRICT);
                productResponseList.add(productResponse);
            });
            return new BaseResponse<>(CommonMessage.FOUND, productResponseList);
        }catch (Exception e){
            System.out.println("error "+e);
            return new BaseResponse<>(CommonMessage.NOT_FOUND);
        }
    }

    @Override
    public BaseResponse<?> getProduct(Long id){
        try{
            Product product = productRepository.findById(id).get();

            ProductResponse productResponse = MapperUtil.parse(product, ProductResponse.class, MatchingStrategies.STRICT);
            return new BaseResponse<>(CommonMessage.FOUND, productResponse);
        }catch (Exception e){
            System.out.println("error "+e);
            return new BaseResponse<>(CommonMessage.NOT_FOUND);
        }
    }

    @Override
    public BaseResponse<?> deleteProduct (Long id, String username){
       try{
           Product product = productRepository.findById(id).get();
           product.setDeleted(true);
           product.setUpdatedBy(username);
           product.setUpdatedDate(new Date());
           productRepository.save(product);
           return new BaseResponse<>(CommonMessage.DELETED, "Success");
       }catch (Exception e){
           System.out.println("error "+e);
           return new BaseResponse<>(CommonMessage.NOT_DELETED);
       }
    }

    @Override
    public BaseResponse<?> updateProduct (Product updatedProductRequest, Long id, String username){
        try{
            Product product = productRepository.findById(id).get();
            ProductCategory productCategory = productCategoryRepository.findById(updatedProductRequest.getProductCategory().getId()).get();

            product.setProductCategory(productCategory);
            product.setName(updatedProductRequest.getName());
            product.setBrand(updatedProductRequest.getBrand());
            product.setQuantityInStock(updatedProductRequest.getQuantityInStock());
            product.setCode(updatedProductRequest.getCode());
            product.setProductSeries(updatedProductRequest.getProductSeries());
            product.setSize(updatedProductRequest.getSize());
            product.setNetWeight(updatedProductRequest.getNetWeight());
            product.setPrice(updatedProductRequest.getPrice());
            product.setImage(updatedProductRequest.getImage());
            product.setExpDate(updatedProductRequest.getExpDate());
            product.setUpdatedBy(username);
            product.setUpdatedDate(new Date());

            productRepository.save(product);
            return new BaseResponse<>(CommonMessage.UPDATED, "Success");
        }catch (Exception e){
            System.out.println("error "+e);
            return new BaseResponse<>(CommonMessage.NOT_UPDATED);
        }
    }

    @Override
    public BaseResponse<?> updateProductQuantity (int newQuantity, Long id, String username){
        try{
            Product product = productRepository.findById(id).get();
            product.setQuantityInStock(newQuantity);
            product.setUpdatedBy(username);
            product.setUpdatedDate(new Date());
            productRepository.save(product);
            return new BaseResponse<>(CommonMessage.UPDATED, "Success");
        }catch (Exception e){
            System.out.println("error "+e);
            return new BaseResponse<>(CommonMessage.NOT_UPDATED);
        }
    }


}
