package com.ehanlin.notification.template.manager;

import java.io.InputStream;

/**
 * 以 Classpath 為 / 的相對路徑解析器
 *
 * @author rodick_huang
 *
 */
public class ClassPathResourceResolver implements ResourceResolver<String, InputStream> {

    /**
     * 從類別路徑讀取檔案
     */
    public InputStream resolve(String filePath) {
        return getClass().getClassLoader().getResourceAsStream(filePath);

    }

}
