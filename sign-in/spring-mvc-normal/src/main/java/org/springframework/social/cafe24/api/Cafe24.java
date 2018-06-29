package org.springframework.social.cafe24.api;

import org.springframework.social.ApiBinding;

import java.util.List;

public interface Cafe24 extends ApiBinding {


    <T> List<T> fetchObjects(String connectionType, Class<T> type, String... fields);

    String getMallId();

    ProductOperations productOperations();
}
