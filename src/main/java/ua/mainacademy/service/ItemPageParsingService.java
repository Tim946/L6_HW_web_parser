package ua.mainacademy.service;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ua.mainacademy.model.Item;

import java.util.List;

@AllArgsConstructor
public class ItemPageParsingService extends Thread {

    private List<Item> items;
    private Document document;
    private String url;

    public static boolean isItemPage(String url) {
        return url.contains("/dp/");
    }


    @Override
    public void run() {
        try {
            Thread.sleep((int) (Math.random() * 2000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        items.add(getItemFromPage(url));

    }

    public Item getItemFromPage(String url) {
        Element element = document.getElementById("ppd");
        Element elementImage = document.getElementById("imgTagWrapperId");
        Element elementCategory = document.getElementById("wayfinding-breadcrumbs_feature_div");

        String name = extractName(element);
        String code = extractCode(url);
        int price = extractPrice(element);
        int initPrice = extractInitPrice(element) == 0 ? price : extractInitPrice(element);
        String imageUrl = extractImageUrl(elementImage);
        String group = extractGroup(elementCategory);
        String seller = extractSeller(element);

        return Item.builder()
                .code(code)
                .name(name)
                .price(price)
                .initPrice(initPrice)
                .group(group)
                .url(url)
                .imageUrl(imageUrl)
                .seller(seller)
                .build();
    }

    private static String extractImageUrl(Element element) {
        if((element==null)||(element.getElementsByTag("img").attr("data-old-hires")==null)){
            return "NO IMAGE URL";
        }
        return element.getElementsByTag("img").attr("data-old-hires");
    }

    private static int extractInitPrice(Element element) {
        List<Element> elementList = element.getElementsByAttributeValueStarting("class", "priceBlockStrikePriceString");
        if (elementList.isEmpty()) {
            return 0;
        }
        return Integer.valueOf(elementList.get(0).text().replaceAll("\\D", ""));
    }

    private static int extractPrice(Element element) {
        if((element==null)||(element.getElementById("priceblock_ourprice")==null)){
            return 0;
        }
        String row = element.getElementById("priceblock_ourprice").text();
//        System.out.println("extractPrice : " + row);
        return Integer.valueOf(row.replaceAll("\\D",""));

    }

    private static String extractSeller(Element element){
        List<Element> elementList = element.getElementsByAttributeValueStarting("class", "tabular-buybox-text");
        if (elementList.isEmpty()) {
            return "";
        }
        return elementList.get(0).text();
    }


    private static String extractGroup(Element element) {
        if (element==null){
            return "NO GROUP";
        }
        List<Element> elementList = element.getElementsByAttributeValueStarting("class", "a-list-item");
        if (elementList.isEmpty()) {
            return "";
        }
        return ((Elements) elementList).text();
    }


    private static String extractCode(String url) {
        return StringUtils.substringAfterLast(url, "/dp/").split("/")[0];
    }

    private static String extractName(Element element) {
        if((element==null)||(element.getElementById("productTitle")==null)){
            return "NO NAME";
        }
        return element.getElementById("productTitle").text();
    }
}
