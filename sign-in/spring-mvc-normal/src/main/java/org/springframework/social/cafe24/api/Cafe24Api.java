package org.springframework.social.cafe24.api;

import java.util.List;

interface Cafe24Api {


    <T> List<T> fetchObjects(String connectionType, Class<T> type, String... fields);
}
