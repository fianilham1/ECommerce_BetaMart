package com.betamart.common.exception;

import com.betamart.model.Product;

public class OutOfStockProductException extends Exception {

    private static final String DEFAULT_MESSAGE = "Not enough products in stock";

    public OutOfStockProductException() {
        super(DEFAULT_MESSAGE);
    }

    public OutOfStockProductException(Product product) {
        super(String.format("%s products is out of stock.", product.getName()));
    }
}