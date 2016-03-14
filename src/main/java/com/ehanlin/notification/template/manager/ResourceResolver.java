package com.ehanlin.notification.template.manager;

/**
 * <h1>資源解析器</h1>
 * <p>負責決定如何找出所需的資源(例如檔案)</p>
 *
 * @author rodick_huang
 *
 */
public interface ResourceResolver<F, T> {

    T resolve(F from);

}
