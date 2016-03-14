package com.ehanlin.notification.template;

import java.util.Map;

public interface Template<T> {

    T parse(Map<String, Object> replace);

}
