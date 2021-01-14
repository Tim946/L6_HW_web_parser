package ua.mainacademy.service;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ua.mainacademy.model.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemPageParsingService {
    public static Item getItemFromPage(String url) {
        Document document = DocumentExtractorService.getDocument(url);
        Element element = document.getElementById("ppd");
        Element elementImage = document.getElementById("imgTagWrapperId");
        Element elementCategory = document.getElementById("wayfinding-breadcrumbs_feature_div");

//        Item item = new Item();
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
        Elements elements = element.getAllElements();
        String str ="";
        for (Element elementEach: elements) {
            Elements elementsInside = elementEach.getElementsByTag("img");
            System.out.println("STR " + elementsInside.text());
                    }
        //        System.out.println("LINK : " +  element.getElementsByTag("img").text());
//        return element.getElementsByTag("img").text();
        return str;
    }

    private static int extractInitPrice(Element element) {
        List<Element> elementList = element.getElementsByAttributeValueStarting("class", "priceBlockStrikePriceString");
        if (elementList.isEmpty()) {
            return 0;
        }
        return Integer.valueOf(elementList.get(0).text().replaceAll("\\D", ""));
    }

    private static int extractPrice(Element element) {
        String row = element.getElementById("priceblock_ourprice").text();
        return Integer.valueOf(row.replaceAll("\\D",""));

    }

    private static String extractSeller(Element element){
        List<Element> elementList = element.getElementsByAttributeValueStarting("class", "tabular-buybox-text");
        if (elementList.isEmpty()) {
            return "";
        }
        System.out.println("SELLER : " + elementList.get(0).text());
        return elementList.get(0).text();
    }


    private static String extractGroup(Element element) {
        List<Element> elementList = element.getElementsByAttributeValueStarting("class", "a-list-item");
        if (elementList.isEmpty()) {
            return "";
        }
        System.out.println("GROUP : " + elementList.get(elementList.size()-1).text());
        return elementList.get(elementList.size()-1).text();
    }


    private static String extractCode(String url) {
        return StringUtils.substringAfterLast(url, "/");
    }

    private static String extractName(Element element) {
        return element.getElementById("productTitle").text();
    }
}
