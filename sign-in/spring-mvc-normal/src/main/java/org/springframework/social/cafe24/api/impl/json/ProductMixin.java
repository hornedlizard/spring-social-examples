package org.springframework.social.cafe24.api.impl.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.cafe24.api.Product;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class ProductMixin extends Cafe24ObjectMixin {

    private static final Logger logger = LoggerFactory.getLogger(ProductMixin.class);
    static {
        logger.info("ProductMixin  called");
    }
    @JsonCreator
    ProductMixin(@JsonProperty("shop_no") Long shopId,
                 @JsonProperty("product_no") String productNo,
                 @JsonProperty("product_code") String productCode,
                 @JsonProperty("product_name") String productName,
                 @JsonProperty("list_image") List<String> listImage ){}
}
