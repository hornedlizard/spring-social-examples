package org.springframework.social.cafe24.api;

import org.springframework.social.ApiBinding;

import java.util.List;

public interface Cafe24 extends Cafe24Api, ApiBinding {


    String getMallId();

    ProductOperations productOperations();
}
