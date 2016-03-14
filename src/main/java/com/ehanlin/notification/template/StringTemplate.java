package com.ehanlin.notification.template;

import java.util.Map;

/**
 * <h1>簡單字串樣板</h1>
 * <p>樣板的格式： "Hello ${name}!" 以 ${key} 作為替代記號</p>
 *
 * @author rodick_huang
 *
 */
public class StringTemplate implements Template<String> {

    private String template;

    public StringTemplate(String template) {
        this.template = template;
    }

    /**
     * 將 ${key} 替換成 Map 中對應的值
     */
    @Override
    public String parse(Map<String, Object> replace) {
        String temp = template;
        for (Map.Entry<String, Object> entry : replace.entrySet()) {
            String key = entry.getKey();
            temp = temp.replaceAll("\\$\\{" + key + "\\}", entry.getValue().toString());
        }

        return temp;
    }

}
