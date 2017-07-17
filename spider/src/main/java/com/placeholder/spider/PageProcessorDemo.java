package com.placeholder.spider;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.io.File;
import java.util.List;

/**
 * Created by l on 17-7-12.
 */
public class PageProcessorDemo implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100).setCharset("UTF-8");

    @Override
    public void process(Page page) {
        List<String> all = page.getHtml().xpath("//h3/a[@class='v-align-middle']/text()").all();
        page.putField("pageNo", page.getUrl().regex("&p=(\\d+)").toString());
        page.putField("name", all);

        if (page.getResultItems().get("name")==null){
            //skip this page
            page.setSkip(true);
        }

        Selectable links = page.getHtml().css("div.pagination").links();
        page.putField("links", links.regex("&p=(\\d+)").all());
        page.addTargetRequests(links.all());
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        String baseUrl = "https://github.com/search?l=Java&p=1&q=stars%3A%3E1&s=stars&type=Repositories";
//        baseUrl = "https://www.baidu.com";
//        String absolutePath = new File("result/").getAbsolutePath();
        Spider.create(new PageProcessorDemo()).addUrl(baseUrl).thread(5).run();
    }
}
