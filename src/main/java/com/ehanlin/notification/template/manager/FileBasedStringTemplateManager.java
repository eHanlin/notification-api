package com.ehanlin.notification.template.manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.ehanlin.notification.template.StringTemplate;
import com.ehanlin.notification.template.Template;

/**
 * <h1>簡單的檔案式字串樣板管理員</h1>
 * <p>功能為傳入一個檔案路徑以取得樣板</p>
 *
 * @author rodick_huang
 *
 */
public class FileBasedStringTemplateManager implements TemplateManager<String> {

    public final static String DEFAULT_FILE_CHARSET = "UTF-8";

    /**
     * 樣板快取
     */
    private Map<String, Template<String>> cache = new HashMap<>();

    /**
     * 將字串解析為輸入串流的解析器
     */
    private ResourceResolver<String, InputStream> resolver;

    /**
     * <p>傳入一個能將字串解析為輸入串流的解析器以建構一個樣板管理員</p>
     * @param resolver 管理員將會透過這個資源解析器來準備樣板
     */
    public FileBasedStringTemplateManager(ResourceResolver<String, InputStream> resolver) {
        this.resolver = resolver;
    }

    /**
     * <p>取得樣板：將傳入的鍵值視為檔案名稱</p>
     * @param templateFileName 樣板的檔名
     */
    @Override
    public Template<String> get(String templateFileName) {
        Template<String> template = cache.get(templateFileName);
        if (template == null) {
            template = prepare(templateFileName);
            cache.put(templateFileName, template);
        }

        return template;
    }

    /**
     * <p>傳入一個樣板檔案的名稱，讀取檔案的輸入串流製作成樣板</p>
     * @param templateFileName
     * @return 字串樣板
     */
    private Template<String> prepare(String templateFileName) {
        StringTemplate template = null;
        InputStream input = resolver.resolve(templateFileName);
        try (BufferedReader reader =
                new BufferedReader(new InputStreamReader(input, DEFAULT_FILE_CHARSET)))
        {
            template = new StringTemplate(readAsString(reader));
        } catch (IOException e) {
            // God damn io ex!
            e.printStackTrace();
        }

        return template;
    }

    /**
     * <p>工具方法：將輸入串流的內容讀出成一個字串</p>
     * @return 內容字串
     * @throws IOException 讀取檔案串流時發生的IO例外
     */
    private String readAsString(BufferedReader reader) throws IOException {
        StringBuilder builder = new StringBuilder();
        String temp;
        while ((temp = reader.readLine()) != null) {
            builder.append(temp);
        }
        return builder.toString();
    }

}
