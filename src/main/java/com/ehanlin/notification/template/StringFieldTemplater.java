package com.ehanlin.notification.template;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ehanlin.notification.template.annotation.TemplateBy;

/**
 * <h1>萬用樣板套用器，字串樣板</h1>
 * <p>將欄位的值本身就當成樣板</p>
 *
 * @author rodick_huang
 */
public class StringFieldTemplater implements Templater<Object> {

    private Map<Class<?>, List<Field>> cache;

    public StringFieldTemplater() {
        cache = new HashMap<>();
    }

    /**
     * <p>樣板套用邏輯</p>
     * <p>需要被樣板化的字串欄位值本身就被視為樣板</p>
     *
     */
    @Override
    public void apply(Object obj, Map<String, Object> replacement) {
        Class<? extends Object> c = obj.getClass();

        List<Field> fields = cache.get(c);
        if (fields != null && fields.size() > 0) {
            for (Field f : fields) {
                doTemplate(obj, f, replacement);
            }

            return;
        }

        List<Field> temp = new ArrayList<>();
        for (Field f : c.getDeclaredFields()) {
            // 不是字串就跳過
            if (!String.class.equals(f.getType())) {
                continue;
            }

            if (f.getAnnotation(TemplateBy.class) != null) {
                temp.add(f);
                doTemplate(obj, f, replacement);
            }
        }
        cache.put(c, temp);
    }

    /**
     * 把物件欄位的值當成樣板並塞值回去
     *
     * @param obj         任意物件
     * @param field       物件的欄位
     * @param replacement 替換的值
     */
    private void doTemplate(Object obj, Field field, Map<String, Object> replacement) {
        String templateText = getFieldValueAsString(obj, field);
        if (templateText != null) {
            setFieldValue(obj, field, new StringTemplate(templateText).parse(replacement));
        }
    }

    private String getFieldValueAsString(Object obj, Field field) {
        field.setAccessible(true);
        try {
            return (String) field.get(obj);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void setFieldValue(Object obj, Field field, String value) {
        field.setAccessible(true);
        try {
            field.set(obj, value);

        } catch (IllegalArgumentException e) {
            e.printStackTrace();

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
