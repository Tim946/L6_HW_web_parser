package ua.mainacademy.service;

import lombok.AllArgsConstructor;
import org.jsoup.nodes.Element;
import ua.mainacademy.model.Item;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class NavigationPageParsingService extends Thread {
    private List<Item> items;
    private List<Thread> threads;
    private Document document;
    private String url;

    @Override
    public void run() {
        parsePage(url);
    }

    private void parsePage(String url) {
        System.out.println("Try to parse page " + url );
        List<String> itemLinks = new ArrayList<>();
        List<Element> elementList = document.getElementsByTag("h2");
        for (Element element: elementList ) {
            String fullItemLink=url.split("/")[0]+"//"+url.split("/")[2]+element.getElementsByClass("a-link-normal a-text-normal").attr("href");
            System.out.println("LINK fullItemLink :" + fullItemLink);
            if(fullItemLink.contains("/dp/")){
                itemLinks.add(fullItemLink);
            }
        }

                /*
        TODO: extract item links
         */

int j=1;
        for (String itemLink : itemLinks) {
            if (threads.size() > 16) {
                return;
            }
            try {
                Thread.sleep((int) (Math.random() * 2000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Link number "+ j + " added to thread");
            j++;
            RouterService routerService = new RouterService(items, threads, itemLink);
            threads.add(routerService);
            routerService.start();

        }
        if (url.contains("&page=")) {
            // TODO: extract last page number
//            if (threads.size() > 3) {
//                return;
//            }
            try {
                Thread.sleep((int) (Math.random() * 2000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int lastPge = 7;
            for (int i = 2; i < lastPge; i++) {
                String nextPageUrl = url.substring(url.lastIndexOf("=")) + "=" + i;
                System.out.println("nextPageUrl " +nextPageUrl);
                RouterService routerService = new RouterService(items, threads, nextPageUrl);
                threads.add(routerService);
                routerService.start();
            }
        }
    }
    //extract item links
    // pagination

    public static boolean isNavigationPage(String url) {
        return !url.contains("/dp/");
    }
}
