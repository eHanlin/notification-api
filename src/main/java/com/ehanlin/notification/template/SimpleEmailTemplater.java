package com.ehanlin.notification.template;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.ehanlin.notification.message.Email;
import com.ehanlin.notification.template.annotation.TemplateBy;
import com.ehanlin.notification.template.manager.TemplateManager;

/**
 * <h1>Email樣板套用器，字串樣板</h1>
 *
 * @author rodick_huang
 */
public class SimpleEmailTemplater implements Templater<Email> {

    /**
     * 字串式的樣板管理員
     */
    private final TemplateManager<String> templateManager;

    /**
     * 檔案路徑快取
     */
    private final Map<String, Cache> filePathCache = new HashMap<>();

    public SimpleEmailTemplater(TemplateManager<String> templateManager) {
        this.templateManager = templateManager;
    }

    /**
     * 樣板套用邏輯
     */
    @Override
    public void apply(Email obj, Map<String, Object> replace) {
        Class<? extends Email> c = obj.getClass();
        Cache cache = filePathCache.get(c.getName());
        if (cache == null) {
            cache = new Cache();
            Annotation ann = c.getAnnotation(TemplateBy.class);
            String filePathPrefix = getFilePathFromAnnotation(ann);
            for (Field f : c.getDeclaredFields()) {
                TemplateBy annOnField = f.getAnnotation(TemplateBy.class);
                if (annOnField == null) {
                    continue;
                }

                String fieldName = f.getName();
                String filePath = getFilePathFromAnnotation(annOnField, fieldName);
                if (filePathPrefix != null) {
                    filePath = filePathPrefix + "/" + filePath;
                }

                Template<String> template = getTemplate(filePath);
                if (template != null) {
                    String templateText = template.parse(replace);
                    setFieldValue(obj, f, templateText);
                    cache.map.put(fieldName, filePath);
                }
            }

            if (cache.map.size() > 0) {
                filePathCache.put(c.getName(), cache);
            }

        } else {
            for (Map.Entry<String, String> entry : cache.map.entrySet()) {
                Template<String> template = getTemplate(entry.getValue());
                if (template != null) {
                    String templateText = template.parse(replace);
                    try {
                        Field f = c.getDeclaredField(entry.getKey());
                        setFieldValue(obj, f, templateText);
                    } catch (NoSuchFieldException e) {
                        // The fucking exception that should never happened.
                    }
                }
            }
        }
    }

    private Template<String> getTemplate(String filePath) {
        Template<String> template = null;
        try {
            template = templateManager.get(filePath);
        } catch (Exception e) {
            e.printStackTrace();
            // File not found exception.
        }

        return template;
    }

    private void setFieldValue(Email obj, Field field, String value) {
        field.setAccessible(true);
        try {
            field.set(obj, value);

        } catch (IllegalArgumentException e) {
            e.printStackTrace();

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private String getFilePathFromAnnotation(Annotation obj, String defaultFileName) {
        String filePath = null;
        if (obj instanceof TemplateBy) {
            TemplateBy ann = (TemplateBy) obj;
            String result = ann.value();
            filePath = "".equals(result) ? defaultFileName : result;
        }

        return filePath;
    }

    private String getFilePathFromAnnotation(Annotation obj) {
        return getFilePathFromAnnotation(obj, null);
    }

    private class Cache {

        Map<String, String> map = new HashMap<>();

    }

}
