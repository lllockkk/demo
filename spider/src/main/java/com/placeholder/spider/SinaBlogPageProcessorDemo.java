package com.placeholder.spider;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

/**
 * Created by l on 17-7-13.
 */
public class SinaBlogPageProcessorDemo implements PageProcessor {
    private Site site = Site
            .me()
            .setDomain("blog.sina.com.cn")
            .setRetryTimes(3)
            .setSleepTime(3000)
            .setCharset("UTF-8")
            .setUserAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");

    private static final String BLOG_URL_REGEX = "http://blog.sina\\.com\\.cn/s/blog_\\w+\\.html";
    private static final String LIST_URL_REGEX= "http://blog\\.sina\\.com\\.cn/s/articlelist_1487828712_0_\\d+\\.html";

    @Override
    public void process(Page page) {
        // 列表页
        if(page.getUrl().regex(LIST_URL_REGEX).match()) {
            page.addTargetRequests(page.getHtml().links().regex(LIST_URL_REGEX).all());
            page.addTargetRequests(page.getHtml().css("div.articleList").links().regex(BLOG_URL_REGEX).all());

            // 文章页
        } else {
            page.putField("title", page.getHtml().xpath("//h2[@class='titName SG_txta']/text()"));
            page.putField("content", page.getHtml().xpath("//div[@class='articalContent']"));
            page.putField("time", page.getHtml().xpath("//span[@class='time SG_txtc']").regex("\\((.*)\\)"));
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        String baseUrl = "http://blog.sina.com.cn/s/articlelist_1487828712_0_1.html";
        baseUrl = "http://blog.sina.com.cn/s/articlelist_1487828712_0_1.html";
        Spider.create(new SinaBlogPageProcessorDemo()).addUrl(baseUrl).thread(5).run();
    }
}









