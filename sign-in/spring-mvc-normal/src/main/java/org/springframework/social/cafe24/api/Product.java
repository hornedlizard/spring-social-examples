package org.springframework.social.cafe24.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

public class Product extends Cafe24Object {
    private static final Logger logger = LoggerFactory.getLogger(Product.class);

    private final Long shopNo;

    private final String productNo;

    private final  String productCode;

    private final String productName;

    private final List<String> listIamge;
    static {
        logger.info("Product.class static block");
    }


    public Product(Long shopNo, String productNo, String productCode, String productName, List<String> listIamge) {
        logger.info("api.Product class constructor called");

        this.shopNo = shopNo;
        this.productNo = productNo;
        this.productCode = productCode;
        this.productName = productName;
        this.listIamge = listIamge;
        logger.info("api.Product instance constructed");

    }

    public Long getShopNo() {
        return shopNo;
    }

    public String getProductNo() {
        return productNo;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getProductName() {
        return productName;
    }

    public List<String> getListIamge() {
        return listIamge;
    }

    @Override
    public String toString() {
        return "Product{" +
                "shopNo=" + shopNo +
                ", productNo='" + productNo + '\'' +
                ", productCode='" + productCode + '\'' +
                ", productName='" + productName + '\'' +
                ", listIamge=" + listIamge +
                '}';
    }
}
