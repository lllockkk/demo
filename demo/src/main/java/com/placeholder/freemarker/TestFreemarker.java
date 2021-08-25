package com.placeholder.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class TestFreemarker {
    public static void main(String[] args) throws Exception {
        // Create your Configuration instance, and specify if up to what FreeMarker
// version (here 2.3.22) do you want to apply the fixes that are not 100%
// backward-compatible. See the Configuration JavaDoc for details.
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);

// Specify the source where the template files come from. Here I set a
// plain directory for it, but non-file-system sources are possible too:

        URL url = TestFreemarker.class.getClassLoader().getResource(".");
        cfg.setDirectoryForTemplateLoading(new File(url.toURI()));

// Set the preferred charset template files are stored in. UTF-8 is
// a good choice in most applications:
        cfg.setDefaultEncoding("UTF-8");

// Sets how errors will appear.
// During web page *development* TemplateExceptionHandler.HTML_DEBUG_HANDLER is better.
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        Template template = cfg.getTemplate("a.ftl");
        Map<String, Object> root = new HashMap<>();

        /*
        <h1>Welcome ${user}!</h1>
<p>Our latest product:
    <a href="${latestProduct.url}">${latestProduct.name}</a>!
        * */
        root.put("user", "link");
        Map<String, Object> latestProduct = new HashMap<>();
        root.put("latestProduct", latestProduct);
        latestProduct.put("url", "www.baidu.com");
        latestProduct.put("name", "this is name");

        template.process(root, new OutputStreamWriter(System.out));
    }
}
