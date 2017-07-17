package com.placeholder.spider;

import org.apache.commons.collections.CollectionUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

/**
 * Created by l on 17-7-13.
 */
public class AngularJSPageProcessor implements PageProcessor {
    private Site site = Site
            .me()
            .setDomain("blog.sina.com.cn")
            .setRetryTimes(3)
            .setSleepTime(3000)
            .setCharset("UTF-8")
            .setUserAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");

    private static final String LIST_URL_REGEX = "http://angularjs.cn/api/article/latest\\?(p=\\d+&)?s=20";
    private static final String DETAIL_URL_REGEX = "http://angularjs.cn/api/article/\\w+";


    @Override
    public void process(Page page) {
        if (page.getUrl().regex(LIST_URL_REGEX).match()) {
//            List<String> ids = new JsonPathSelector("$.data[*]._id").selectList(page.getRawText());
            List<String> ids = page.getJson().jsonPath("$.data[*]._id").all();
            if(CollectionUtils.isNotEmpty(ids)) {
                for (String id : ids) {
                    page.addTargetRequest("http://angularjs.cn/api/article/" + id);
                }
            }
        } else {
//            page.putField("title", new JsonPathSelector("$.data.title").select(page.getRawText()));
//            page.putField("content", new JsonPathSelector("$.data.content").select(page.getRawText()));
            page.putField("title", page.getJson().jsonPath("$.data.title").get());
            page.putField("content", page.getJson().jsonPath("$.data.content").get());
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        String baseUrl = "http://angularjs.cn/api/article/latest?s=20";
        Spider.create(new AngularJSPageProcessor()).addUrl(baseUrl).thread(5).run();
    }
}
