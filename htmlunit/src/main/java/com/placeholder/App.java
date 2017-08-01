package com.placeholder;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebClientOptions;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        try (final WebClient webClient = new WebClient()) {
            final HtmlPage htmlPage = webClient.getPage("http://angularjs.cn/");
            System.out.println(1);
        }
    }

    @Test
    public void homePage() throws Exception {
        try (final WebClient webClient = new WebClient(BrowserVersion.CHROME)) {
            WebClientOptions options = webClient.getOptions();
            options.setUseInsecureSSL(true);
            options.setJavaScriptEnabled(true);
            options.setCssEnabled(true);
            options.setThrowExceptionOnScriptError(true);
            options.setTimeout(10000);
            options.setDoNotTrackEnabled(false);
            final HtmlPage page = webClient.getPage("http://angularjs.cn/");
            webClient.waitForBackgroundJavaScript(10000);
            String xml = page.asXml();
            System.out.println(1);

           /* final String pageAsXml = page.asXml();
            Assert.assertTrue(pageAsXml.contains("<body class=\"composite\">"));

            final String pageAsText = page.asText();
            Assert.assertTrue(pageAsText.contains("Support for the HTTP and HTTPS protocols"));*/
        }
    }
}
