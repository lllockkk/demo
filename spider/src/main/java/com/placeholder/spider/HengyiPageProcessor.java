package com.placeholder.spider;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.HtmlNode;
import us.codecraft.webmagic.selector.Selectable;

import java.time.LocalDateTime;

/**
 * Created by l on 17-7-12.
 */
public class HengyiPageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100).setCharset("UTF-8");

    /**
     *　@see Spider
     * @param page 页面
     */
    @Override
    public void process(Page page) {
        Selectable xpath = page.getHtml().xpath("//script").regex("src=\"([\\w/\\.]+)\"");
        page.putField("js", xpath.all());
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new HengyiPageProcessor()).addUrl("http://192.168.0.180/#/").addPipeline(new JsonFilePipeline("spider/result")).thread(5).run();
    }
}
