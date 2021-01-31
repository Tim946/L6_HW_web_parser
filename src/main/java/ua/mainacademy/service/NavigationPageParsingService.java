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
        try {
            Thread.sleep((int) (Math.random() * 2000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        parsePage(url);
    }

    private void parsePage(String url) {
        System.out.println("Try to parse page " + url);
        List<String> itemLinks = new ArrayList<>();
        List<Element> elementList = document.getElementsByTag("h2");
        for (Element element : elementList) {
            String fullItemLink = url.split("/")[0] + "//" + url.split("/")[2] + element.getElementsByClass("a-link-normal a-text-normal").attr("href");
            if (fullItemLink.contains("/dp/")) {
                System.out.println("LINK ITEM  fullItemLink :" + fullItemLink.split("/ref=")[0]);
                itemLinks.add(fullItemLink.split("/ref=")[0]);
            }
        }

        for (String itemLink : itemLinks) {
            if (threads.size() > 400) {
                return;
            }
            try {
                Thread.sleep((int) (Math.random() * 2000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            RouterService routerService = new RouterService(items, threads, itemLink);
            threads.add(routerService);
            routerService.start();

        }
        System.out.println(threads.size() );
        if ((url.contains("&page="))&(itemLinks.size()>3)) {
            if (threads.size() >400) {
                return;
            }
            try {
                Thread.sleep((int) (Math.random() * 2000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String nextPageUrl= url.substring(0,url.lastIndexOf("="))+"="+
                    (Integer.valueOf(url.substring(url.lastIndexOf("=")+1,url.length()))+1);
            System.out.println("nextPageUrl " + nextPageUrl);
            RouterService routerService = new RouterService(items, threads, nextPageUrl);
            threads.add(routerService);
            routerService.start();
        }
    }

    public static boolean isNavigationPage(String url) {
        return !url.contains("/dp/");
    }
}
