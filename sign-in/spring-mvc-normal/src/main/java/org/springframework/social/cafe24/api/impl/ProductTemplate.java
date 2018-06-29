package org.springframework.social.cafe24.api.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.cafe24.api.Cafe24;
import org.springframework.social.cafe24.api.Product;
import org.springframework.social.cafe24.api.ProductOperations;

import java.util.ArrayList;
import java.util.List;

public class ProductTemplate implements ProductOperations {
    private static final Logger logger = LoggerFactory.getLogger(ProductTemplate.class);

    private Cafe24 cafe24;

    public ProductTemplate(Cafe24 cafe24) {
        this.cafe24 = cafe24;
    }

    @Override
    public List<Product> getProducts() {
        logger.info("getProducts called...");
        List<Product> products;
        products = cafe24.fetchObjects("products", Product.class, null);
        if (products == null) {
            logger.info("getProducts products empty...");

        }
        return products;
    }
}
