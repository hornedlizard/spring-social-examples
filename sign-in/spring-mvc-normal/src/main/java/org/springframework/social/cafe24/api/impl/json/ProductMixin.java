package org.springframework.social.cafe24.api.impl.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.social.cafe24.api.Product;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class ProductMixin extends Cafe24ObjectMixin {


    @JsonCreator
    ProductMixin(@JsonProperty("shop_id") Long shopId,
                 @JsonProperty("product_no") String productNo,
                 @JsonProperty("product_code") String productCode,
                 @JsonProperty("product_name") String productName,
                 @JsonProperty("list_image") String imgPath ){}
}
