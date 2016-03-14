package com.ehanlin.notification.template.manager;

import com.ehanlin.notification.template.Template;

/**
 * <h1>樣板管理員的界面規範<h1/>
 *
 * @author rodick_huang
 *
 * @param <T>
 */
public interface TemplateManager<T> {

    /**
     * 取得樣板
     *
     * @param key 取得樣板的鍵值：如何看待鍵值則交給實作決定
     * @return 樣板
     */
    Template<T> get(String key);

}
