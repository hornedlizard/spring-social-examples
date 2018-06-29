package org.springframework.social.cafe24.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class Product {
    private static final Logger logger = LoggerFactory.getLogger(Product.class);

    private final Long shopId;

    private final String productNo;

    private final  String productCode;

    private final String productName;

    private final String imgPath;

    public Product(Long shopId, String productNo, String productCode, String productName, String imgPath) {
        this.shopId = shopId;
        this.productNo = productNo;
        this.productCode = productCode;
        this.productName = productName;
        this.imgPath = imgPath;
        logger.info("api.Product instance constructed");
    }

    public Long getShopId() {
        return shopId;
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

    public String getImgPath() {
        return imgPath;
    }
}
