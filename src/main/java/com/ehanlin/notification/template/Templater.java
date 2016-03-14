package com.ehanlin.notification.template;

import java.util.Map;

public interface Templater<T> {

    void apply(T o, Map<String, Object> replace);

}
