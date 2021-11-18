package com.betamart.service.impl;

import com.betamart.base.constant.CommonMessage;
import com.betamart.base.payload.response.BaseResponse;
import com.betamart.base.util.MapperUtil;
import com.betamart.model.Product;
import com.betamart.model.ProductCategory;
import com.betamart.module.product.payload.request.ProductListRequest;
import com.betamart.module.product.payload.request.ProductRequest;
import com.betamart.module.product.payload.response.ProductResponse;
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
    public BaseResponse<?> addProduct(ProductListRequest productListRequest, String username){
        try{
            productListRequest.getProductList().forEach(productRequest -> {
                Product product = MapperUtil.parse(productRequest, Product.class, MatchingStrategies.STRICT);
                ProductCategory productCategory = productCategoryRepository.findById(productRequest.getProductCategoryId()).get();

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
    public BaseResponse<?> updateProduct (ProductRequest editProductRequest, Long id, String username){
        try{
            Product product = productRepository.findById(id).get();
            ProductCategory productCategory = productCategoryRepository.findById(editProductRequest.getProductCategoryId()).get();

            product.setProductCategory(productCategory);
            product.setName(editProductRequest.getName());
            product.setBrand(editProductRequest.getBrand());
            product.setQuantityInStock(editProductRequest.getQuantityInStock());
            product.setCode(editProductRequest.getCode());
            product.setProductSeries(editProductRequest.getProductSeries());
            product.setSize(editProductRequest.getSize());
            product.setNetWeight(editProductRequest.getNetWeight());
            product.setPrice(editProductRequest.getPrice());
            product.setImage(editProductRequest.getImage());
            product.setExpDate(editProductRequest.getExpDate());
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
